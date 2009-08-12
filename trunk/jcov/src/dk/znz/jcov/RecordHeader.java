package dk.znz.jcov;

public class RecordHeader {
	private long tag;
	private long length;

	public long getTag() {
		return tag;
	}

	public long getLength() {
		return length;
	}

	public RecordHeader(GCovReader reader) {
		tag = reader.getUInt32();
		length = reader.getUInt32();
	}
}
