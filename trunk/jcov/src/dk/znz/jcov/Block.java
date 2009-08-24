package dk.znz.jcov;

import java.io.IOException;

public class Block {
	private RecordHeader header; 
	private UInt32 block_no;
	
	public RecordHeader getHeader() {
		return header;
	}

	public UInt32 getBlock_no() {
		return block_no;
	}

	public Block(GCovReader reader) throws IOException {
		header = new RecordHeader(reader);
		block_no = reader.getUInt32();
	}
}
