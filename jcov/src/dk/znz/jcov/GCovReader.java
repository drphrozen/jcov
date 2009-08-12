package dk.znz.jcov;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.BitSet;

public class GCovReader {
	public static final long GCOV_DATA_MAGIC = 0x67636461L;
	public static final long GCOV_NOTE_MAGIC = 0x67636e6fL;
	private byte[] fourBytesBuffer = new byte[4];
	private ByteBuffer buffer = ByteBuffer.allocate(Short.MAX_VALUE);
	private final Charset ascii = Charset.forName("US-ASCII");
	private final BigInteger UINT32_MAX_VALUE = BigInteger.valueOf(4294967295L);
	private ReadableByteChannel channel;
	
	public GCovReader(File file) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(file);
		channel = stream.getChannel();
		
		buffer.order(ByteOrder.nativeOrder());
		try {
			channel.read(buffer);
			buffer.rewind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Header getHeader()
	{
		return new Header(this);
	}
	
	public Record getRecord()
	{
		return new Record(this);
	}
	
	public byte[] getUInt32ToBytes()
	{
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(0, getUInt32());
		byte[] tmp = new byte[4];
		bb.position(4);
		bb.get(tmp);
		return tmp;
	}
	
	public String getFourChars()
	{
		byte[] bytes = getFourBytes();
		return new String(bytes, ascii);
	}
	
	public UInt8 getUInt8()
	{
		return ((short)(buffer.get() & 0xff));
	}
	
	public UInt32 getUInt32()
	{
		return ((long)buffer.getInt() & 0xffffffffL);
	}
	
	public UInt64 getUInt64()
	{
		UInt32 low = getUInt32();
		UInt32 high = getUInt32();
		Long bla = high.longValue();
		BigInteger bigInt = BigInteger.valueOf(bla);
		bigInt.multiply(UINT32_MAX_VALUE);
		bigInt.add(BigInteger.valueOf(low));
		return bigInt;
	}
	
	public String getString() throws Exception
	{
		long length = getUInt32();
		if(length*4 > Integer.MAX_VALUE)
			throw new Exception("The string is to long to be loaded: " + length + " characters!");
		byte[] str = new byte[(int) (length*4)];
		for(int i = 0; i<length; i++)
			str[i] = buffer.get();
		return new String(str, ascii);
	}
	
//    private static String convertByteToBits(byte b) {
//        StringBuilder sb = new StringBuilder(8);
//        for (int i = 7; i >= 0; i--) {
//        	sb.append(((b & (1 << i)) != 0) ? '1' : '0');
//        }
//        return sb.toString();
//    }

	public static void main(String[] arguments)
	{
		try {
			FileInputStream stream = new FileInputStream(new File("c:\\tmp.dat"));
			ReadableByteChannel channel = stream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.order(ByteOrder.BIG_ENDIAN);
			try {
				channel.read(buffer);
				buffer.rewind();
				long b = ((long)buffer.getInt() & 0xffffffffL);
				//System.out.println("0x" + convertByteToBits(b));
				System.out.println(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		if(arguments.length == 1)
//		{
//			try {
//				GCovReader gcov = new GCovReader(new File(arguments[0]));
//				Header header = gcov.getHeader();
//				System.out.println("gcda:    " + GCOV_DATA_MAGIC);
//				System.out.println("gcno:    " + GCOV_NOTE_MAGIC);
//				System.out.println("Magic:   " + header.getMagic());
//				System.out.println("Stamp:   " + header.getStamp());
//				System.out.println("Version: " + header.getVersion());
//				Record record = gcov.getRecord();
//				System.out.println("Length:  " + record.getHeader().getLength());
//				System.out.println("Tag:     " + record.getHeader().getTag());
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Wrong number of arguments, got " + arguments.length);
//			for (String argument: arguments) {
//				System.out.println("  " + argument);
//			}
//		}
	}
}
