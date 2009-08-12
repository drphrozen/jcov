package dk.znz.jcov;

import java.io.IOException;

public class RecordHeader {
	private long tag;
	private long length;

	public long getTag() {
		return tag;
	}

	public long getLength() {
		return length;
	}

	public RecordHeader(GCovReader reader) throws IOException {
		tag = reader.getUInt32().value();
		length = reader.getUInt32().value();
	}
}
