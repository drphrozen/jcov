package dk.znz.jcov;

import java.io.File;

public class Program {
	public static void main(String[] arguments) {
		if (arguments.length == 1) {
			GCovReader reader;
			try {
				reader = new GCovReader(new File(arguments[0]));
				try {
					GCovNote note = new GCovNote(reader);
//					Header header = gcov.getHeader();
//					System.out.println("Stamp:   " + header.getStamp());
//					System.out.println("Version: " + header.getVersion());
//					Record record = gcov.getRecord();
//					System.out
//							.println("Length:  " + record.getHeader().getLength());
//					System.out.println("Tag:     " + record.getHeader().getTag());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Bytes processed: " + (reader.getBytesRead() - reader.getBytesRemainingInBuffer()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Wrong number of arguments, got "
					+ arguments.length);
			for (String argument : arguments) {
				System.out.println("  " + argument);
			}
		}
	}
}
