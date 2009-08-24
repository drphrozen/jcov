package dk.znz.jcov;

import java.io.IOException;

public class Record {
	private RecordHeader header;
	private RecordItem[] data;

	public RecordHeader getHeader() {
		return header;
	}

	public RecordItem[] getData() {
		return data;
	}

	public Record(GCovReader reader) throws IOException {
		header = new RecordHeader(reader);
		int length = header.getIntLength();
		data = new RecordItem[length];
		for (int i = 0; i < length; i++) {
			data[i] = new RecordItem(reader);
		}
	}
}
