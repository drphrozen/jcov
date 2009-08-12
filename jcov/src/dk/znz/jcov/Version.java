package dk.znz.jcov;

import java.io.IOException;

public class Version {
	int major;
	int minor;
	char status;

	public Version(GCovReader reader) throws IOException {
		String version = reader.getFourChars();
		if (version.charAt(0) >= '0' && version.charAt(0) <= '9')
			major = version.charAt(0) - '0';
		else
			major = version.charAt(0) - 'A';
		try {
			minor = Integer.parseInt(version.substring(1, 3));
		} catch (NumberFormatException e) {
			minor = -1;
			e.printStackTrace();
		}
		status = version.charAt(3);
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public char getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return getMajor() + "." + getMinor() + "." + getStatus();
	}
}
