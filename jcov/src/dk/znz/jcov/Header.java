package dk.znz.jcov;

import java.io.IOException;

import dk.znz.jcov.GCovReader.Filetype;

public class Header {
	
	private Filetype filetype;
	private Version version;
	private UInt32 stamp;
		
	public Filetype getFiletype()
	{
		return filetype;
	}
	
	public Version getVersion() {
		return version;
	}
	
	public UInt32 getStamp() {
		return stamp;
	}
	
	public Header(GCovReader reader) throws IOException {
		filetype = reader.getFiletype();
		version = new Version(reader);
		stamp = reader.getUInt32();
	}
}
