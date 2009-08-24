package dk.znz.jcov;

import java.io.IOException;

public class FunctionGraph {
	private AnnounceFunction announceFunction;
	private BasicBlock basicBlock;
	private Block[] blocks;
	
	public AnnounceFunction getAnnounceFunction() {
		return announceFunction;
	}

	public BasicBlock getBasicBlock() {
		return basicBlock;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public FunctionGraph(GCovReader reader) throws IOException {
		announceFunction = new AnnounceFunction(reader);
		basicBlock = new BasicBlock(reader);
	}
}
