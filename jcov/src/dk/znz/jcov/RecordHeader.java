package dk.znz.jcov;

import java.io.IOException;

public class RecordHeader {
	private UInt32 tag;
	private UInt32 length;

	public UInt32 getTag() {
		return tag;
	}

	public UInt32 getLength() {
		return length;
	}

	/**
	 * This will return the length as an int, only if the long value is < Integer.MAX_VALUE.
	 * @return The length specified in this header.
	 * @throws IOException An exception if the original length is to big to be converted to an integer.
	 */
	public int getIntLength() throws IOException {
		long longLength = length.longValue();
		if (longLength > Integer.MAX_VALUE)
			throw new IOException("The string is to long to be loaded: " + longLength
					+ " characters!");
		int length = (int)longLength;
		return length;
	}

	public RecordHeader(GCovReader reader) throws IOException {
		tag = reader.getUInt32();
		length = reader.getUInt32();
	}
}
