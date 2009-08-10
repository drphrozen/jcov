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
	
	public String getFourChars()
	{
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(0, getInt32());
		byte[] tmp = new byte[4];
		bb.position(4);
		bb.get(tmp);
		return new String(tmp, ascii);
	}
	
	public long getInt32()
	{
		return ((long)buffer.getInt() & 0xffffffffL);
	}
	
	public BigInteger getInt64()
	{
		long low = getInt32();
		long high = getInt32();
		BigInteger bigInt = BigInteger.valueOf(high);
		bigInt.multiply(UINT32_MAX_VALUE);
		bigInt.add(BigInteger.valueOf(low));
		return bigInt;
	}
	
	public String getString() throws Exception
	{
		long length = getInt32();
		if(length*4 > Integer.MAX_VALUE)
			throw new Exception("The string is to long to be loaded: " + length + " characters!");
		byte[] str = new byte[(int) (length*4)];
		for(int i = 0; i<length; i++)
			str[i] = buffer.get();
		return new String(str, ascii);
	}
	
	public static void main(String[] arguments)
	{
		if(arguments.length == 1)
		{
			try {
				GCovReader gcov = new GCovReader(new File(arguments[0]));
				Header header = gcov.getHeader();
				System.out.println("gcda:    " + GCOV_DATA_MAGIC);
				System.out.println("gcno:    " + GCOV_NOTE_MAGIC);
				System.out.println("Magic:   " + header.getMagic());
				System.out.println("Stamp:   " + header.getStamp());
				System.out.println("Version: " + header.getVersion());
				Record record = gcov.getRecord();
				System.out.println("Length:  " + record.getHeader().getLength());
				System.out.println("Tag:     " + record.getHeader().getTag());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Wrong number of arguments, got " + arguments.length);
			for (String argument: arguments) {
				System.out.println("  " + argument);
			}
		}
	}
}
