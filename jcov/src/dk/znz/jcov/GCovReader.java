package dk.znz.jcov;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
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
	
	public GCovReader(File file) {
		FileInputStream stream = new FileInputStream(file);
		
		buffer.order(ByteOrder.nativeOrder());
		this.channel = channel;
		try {
			channel.read(buffer);
			buffer.rewind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public getHeader()
	{
		
	}
	
	public String getFourChars()
	{
		buffer.get(fourBytesBuffer);
		return new String(fourBytesBuffer, ascii);
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
			FileInputStream input;
			try {
				input = new FileInputStream(new File(arguments[0]));
				FileChannel channel = input.getChannel();
				GCovReader gcov = new GCovReader(channel);
				System.out.println("Read: " + Long.toHexString(gcov.getInt32()));
				System.out.println("Expe: " + Long.toHexString(GCovReader.GCOV_DATA_MAGIC));
				System.out.println("Version: " + buffer);
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
