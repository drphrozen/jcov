package dk.znz.jcov;

import java.io.IOException;

public class Unit {
	private RecordHeader header;
	private UInt32 checksum;
	private String source;
	
	public RecordHeader getHeader() {
		return header;
	}
	
	public UInt32 getChecksum() {
		return checksum;
	}
	
	public String getSource() {
		return source;
	}
	
	public Unit(GCovReader reader) throws IOException {
		header = new RecordHeader(reader);
		checksum = reader.getUInt32();
		// TODO: What is this: ?
		reader.getUInt32();
		
		source = reader.getString();
		System.out.println("Length: " + source.length());
	}
}
