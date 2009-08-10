package dk.znz.jcov;

public class Version {
	int major;
	int minor;
	char status;

	public Version(GCovReader reader) {
		String version = reader.getFourChars();
		if (major >= '0' && major <= '9')
			major = version.charAt(0) - '0';
		else
			major = version.charAt(0) - 'A';
		try {
			minor = Integer.parseInt(version.substring(1, 2));
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
}
