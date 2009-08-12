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
			channel.read(buffer);
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
			channel.read(buffer);
			buffer.rewind();
		}
		return new UInt32(buffer.getInt());
	}

	public String getFourChars() throws IOException {
		byte[] bytes = getUInt32().toBytes();
		return new String(bytes, CHARSET_ASCII);
	}

	public Filetype getFiletype() {
		return filetype;
	}

	// public String getString() throws Exception
	// {
	// long length = getUInt32().val;
	// if(length*4 > Integer.MAX_VALUE)
	// throw new Exception("The string is to long to be loaded: " + length +
	// " characters!");
	// byte[] str = new byte[(int) (length*4)];
	// for(int i = 0; i<length; i++)
	// str[i] = buffer.get();
	// return new String(str, ascii);
	// }

	// private static String convertByteToBits(byte b) {
	// StringBuilder sb = new StringBuilder(8);
	// for (int i = 7; i >= 0; i--) {
	// sb.append(((b & (1 << i)) != 0) ? '1' : '0');
	// }
	// return sb.toString();
	// }

	public static void main(String[] arguments) {
		if (arguments.length == 1) {
			try {
				GCovReader gcov = new GCovReader(new File(arguments[0]));
				Header header = gcov.getHeader();
				System.out.println("Stamp:   " + header.getStamp());
				System.out.println("Version: " + header.getVersion());
				Record record = gcov.getRecord();
				System.out.println("Length:  " + record.getHeader().getLength());
				System.out.println("Tag:     " + record.getHeader().getTag());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Wrong number of arguments, got "
					+ arguments.length);
			for (String argument : arguments) {
				System.out.println("  " + argument);
			}
		}
	}
}
