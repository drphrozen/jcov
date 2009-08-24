package dk.znz.jcov;

import java.io.IOException;

public class BasicBlock {
	private RecordHeader header;
	private UInt32[] flags;
	
	public BasicBlock(GCovReader reader) throws IOException {
		header = new RecordHeader(reader);
		int length = header.getIntLength();
		flags = new UInt32[length]; 
		for (int i = 0; i < length; i++) {
			flags[i] = reader.getUInt32();
		}
	}
	
}
