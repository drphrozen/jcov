package dk.znz.jcov;

import java.io.IOException;

import dk.znz.jcov.GCovReader.Filetype;

public class GCovNote {
	private Header header;
	private Unit unit;
	private FunctionGraph[] functionGraphs;
	
	public Header getHeader()
	{
		return header;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public FunctionGraph[] getFunctionGraphs() {
		return functionGraphs;
	}

	public GCovNote(GCovReader reader) throws IOException {
		if(reader.getFiletype() != Filetype.FILETYPE_NOTES)
			throw new IOException();
		header = new Header(reader);
		unit = new Unit(reader);
		int length = unit.getHeader().getIntLength();
		functionGraphs = new FunctionGraph[length];
		for (int i = 0; i < length; i++) {
			functionGraphs[i] = new FunctionGraph(reader);
		}
	}
}
