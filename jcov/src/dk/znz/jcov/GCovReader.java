package dk.znz.jcov;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

public class GCovReader {
	public enum Filetype {
		FILETYPE_DATA, FILETYPE_NOTES
	}

	public static final int GCOV_DATA_MAGIC_BIG = 0x67636461;
	public static final int GCOV_NOTE_MAGIC_BIG = 0x67636e6f;
	public static final int GCOV_DATA_MAGIC_LITTLE = 0x61646367;
	public static final int GCOV_NOTE_MAGIC_LITTLE = 0x6f6e6367;

	private Filetype filetype;
	private long bytesRead;
	private int bytesRemainingInBuffer;
	
	public Filetype getFiletype() {
		return filetype;
	}
	
	public long getBytesRead() {
		return bytesRead;
	}
	
	public int getBytesRemainingInBuffer() {
		return bytesRemainingInBuffer;
	}

	private ByteBuffer buffer = ByteBuffer.allocate(4 * 1024); // 4k buffer
	private final Charset CHARSET_ASCII = Charset.forName("US-ASCII");
	private ReadableByteChannel channel;

	public GCovReader(File file) throws FileNotFoundException, IOException {
		FileInputStream stream = new FileInputStream(file);
		DataInputStream dataStream = new DataInputStream(stream);
		int magic = dataStream.readInt();
		switch (magic) {
		case GCOV_DATA_MAGIC_LITTLE:
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			filetype = Filetype.FILETYPE_DATA;
			break;
		case GCOV_NOTE_MAGIC_LITTLE:
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			filetype = Filetype.FILETYPE_NOTES;
			break;
		case GCOV_DATA_MAGIC_BIG:
			buffer.order(ByteOrder.BIG_ENDIAN);
			filetype = Filetype.FILETYPE_DATA;
			break;
		case GCOV_NOTE_MAGIC_BIG:
			buffer.order(ByteOrder.BIG_ENDIAN);
			filetype = Filetype.FILETYPE_NOTES;
			break;
		default:
			throw new IOException("This file is not a gcov data/notes file!");
		}

		channel = stream.getChannel();
		try {
			bytesRead += channel.read(buffer);
			buffer.rewind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Header getHeader() throws IOException {
		return new Header(this);
	}

	public Record getRecord() throws IOException {
		return new Record(this);
	}

	public UInt32 getUInt32() throws IOException {
		if (!buffer.hasRemaining()) {
			bytesRead += channel.read(buffer);
			buffer.rewind();
		}
		return new UInt32(buffer.getInt());
	}

	public String getFourChars() throws IOException {
		byte[] bytes = getUInt32().toBytes();
		return new String(bytes, CHARSET_ASCII);
	}

	public String getString() throws IOException {
		long blocks = getUInt32().longValue();
		if(blocks == 0)
			return "";
		long longLength = blocks * 4;
		if (longLength > Integer.MAX_VALUE)
			throw new IOException("The string is to long to be loaded: " + longLength
					+ " characters!");
		int length = (int)longLength;
		ByteBuffer bytes = ByteBuffer.allocate(length);
		byte[] smallBuffer = new byte[4];
		for (int i = 0; i < blocks; i++)
		{
			if (!buffer.hasRemaining()) {
				bytesRead += channel.read(buffer);
				buffer.rewind();
			}
			buffer.get(smallBuffer);
			bytes.put(smallBuffer);
		}
		byte[] byteString = bytes.array();
		for(int i = length-4; i < length; i++)
		{
			if(byteString[i] == 0x00)
			{
				length = i;
				break;
			}
		}
		return new String(bytes.array(), 0, length, CHARSET_ASCII);
	}

	// private static String convertByteToBits(byte b) {
	// StringBuilder sb = new StringBuilder(8);
	// for (int i = 7; i >= 0; i--) {
	// sb.append(((b & (1 << i)) != 0) ? '1' : '0');
	// }
	// return sb.toString();
	// }
}
