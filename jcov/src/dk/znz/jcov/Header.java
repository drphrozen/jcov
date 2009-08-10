package dk.znz.jcov;

public class Header {
	private long magic;
	private Version version;
	private long stamp;
	private Record[] records;
		
	public long getMagic() {
		return magic;
	}

	public Version getVersion() {
		return version;
	}
	
	public long getStamp() {
		return stamp;
	}

	public Header(GCovReader reader) {
		
		magic = reader.getInt32();
		version = new Version(reader);
		stamp = reader.getInt32();
	}
	
}
