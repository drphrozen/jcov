package dk.znz.jcov;

public class Header {
	private String magic;
	private Version version;
	private long stamp;
	private Record[] records;
		
	public String getMagic() {
		return magic;
	}

	public Version getVersion() {
		return version;
	}
	
	public long getStamp() {
		return stamp;
	}

	public Header(GCovReader reader) {
		magic = reader.getFourChars();
		version = new Version(reader);
		stamp = reader.getInt32();
	}
}
