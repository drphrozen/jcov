package dk.znz.jcov;

import java.io.IOException;

public class AnnounceFunction {
	private RecordHeader header;
	private UInt32 ident;
	private UInt32 checksum;
	private String name;
	private String source;
	private UInt32 lineno;
	
	public RecordHeader getHeader() {
		return header;
	}
	
	public UInt32 getIdent() {
		return ident;
	}
	
	public UInt32 getChecksum() {
		return checksum;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSource() {
		return source;
	}
	
	public UInt32 getLineno() {
		return lineno;
	}

	public AnnounceFunction(GCovReader reader) throws IOException {
		header = new RecordHeader(reader);
		ident = reader.getUInt32();
		checksum = reader.getUInt32();
		name = reader.getString();
		source = reader.getString();
		lineno = reader.getUInt32();
	}
}
