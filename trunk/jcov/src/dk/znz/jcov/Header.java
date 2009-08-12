package dk.znz.jcov;

import java.util.ArrayList;

public class Header {
	private long magic;
	private Version version;
	private long stamp;
	private ArrayList<Record> records;
		
	public long getMagic() {
		return magic;
	}

	public Version getVersion() {
		return version;
	}
	
	public long getStamp() {
		return stamp;
	}
	
	public ArrayList<Record> getRecords() {
		return records;
	}

	public Header(GCovReader reader) {
		
		magic = reader.getUInt32();
		version = new Version(reader);
		stamp = reader.getUInt32();
		Record record = new Record(reader);
		while(record != null) {
			records.add(record);
			record = new Record(reader);
		}
	}
	
}
