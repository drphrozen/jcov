package dk.znz.jcov;

import java.io.IOException;

public class Note {
	private Unit unit;
	private FunctionGraph[] functionGraphs;
	
	public Unit getUnit() {
		return unit;
	}
	
	public FunctionGraph[] getFunctionGraphs() {
		return functionGraphs;
	}
	
	public Note(GCovReader reader) throws IOException {
		unit = new Unit(reader);
		int length = unit.getHeader().getIntLength();
		functionGraphs = new FunctionGraph[length];
		for(int i = 0; i < length; i++)
		{
			functionGraphs[i] = new FunctionGraph(reader);
		}
	}
}
