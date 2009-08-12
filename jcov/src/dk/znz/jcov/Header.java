package dk.znz.jcov;

import java.io.IOException;
import java.util.ArrayList;

public class Header {
	
	private Version version;
	private long stamp;
	private ArrayList<Record> records;
		
	public Version getVersion() {
		return version;
	}
	
	public long getStamp() {
		return stamp;
	}
	
	public ArrayList<Record> getRecords() {
		return records;
	}

	public Header(GCovReader reader) throws IOException {
		version = new Version(reader);
		stamp = reader.getUInt32().value();
//		Record record = new Record(reader);
//		while(record != null) {
//			records.add(record);
//			record = new Record(reader);
//		}
	}
	
}
