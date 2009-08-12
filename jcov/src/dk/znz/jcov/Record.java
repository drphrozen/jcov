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
		data = new RecordItem[(int) header.getLength()];
		for (int i = 0; i < header.getLength(); i++) {
			data[i] = new RecordItem(reader);
		}
	}
}
