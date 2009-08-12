package dk.znz.jcov;

public class UInt8 extends Number implements Comparable<UInt8> {

	private static final long serialVersionUID = 6603783542856393288L;

	private Short value;
	public static short MAX_VALUE = 255;
	public static short MIN_VALUE = 0;

	public UInt8(short value) throws NumberFormatException {
		this.value = value;
		if(value > MAX_VALUE || value < MIN_VALUE)
			throw new NumberFormatException("The number must be from " + MIN_VALUE + " - " + MAX_VALUE + "!");
	}
	
	public UInt8(String s) throws NumberFormatException {
		value = Short.parseShort(s);
		if(value > MAX_VALUE || value < MIN_VALUE)
			throw new NumberFormatException("The number must be from " + MIN_VALUE + " - " + MAX_VALUE + "!");
	}
	
	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}

	@Override
	public int compareTo(UInt8 anotherUInt8) {
		return value.compareTo(anotherUInt8.value);
	}
	
}
