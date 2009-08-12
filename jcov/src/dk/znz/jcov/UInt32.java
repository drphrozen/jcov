package dk.znz.jcov;

public class UInt32 extends Number implements Comparable<UInt32> {

	private static final long serialVersionUID = -6717966716922305289L;

	private final Long value;
	public static final long MAX_VALUE = 4294967295L;
	public static final long MIN_VALUE = 0L;
	
	public UInt32(int value)
	{
		this.value = ((long)value & 0xffffffffL);
	}
	
	public UInt32(long value) throws NumberFormatException {
		this.value = value;
		if(value > MAX_VALUE || value < MIN_VALUE)
			throw new NumberFormatException("The number must be from " + MIN_VALUE + " - " + MAX_VALUE + "!");
	}
	
	public UInt32(String s) throws NumberFormatException {
		value = Long.parseLong(s);
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
	public int compareTo(UInt32 anotherUInt32) {
		return value.compareTo(anotherUInt32.value);
	}
	
	public long value()
	{
		return value;
	}
	
	public byte[] toBytes()
	{
		byte[] bytes = new byte[4];
		for(int i = 0; i < 4; i++)
			bytes[3-i] = (byte) ((value >> i*8) & 0xFF);
		return bytes;
	}

}
