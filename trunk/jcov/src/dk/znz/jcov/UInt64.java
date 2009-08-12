package dk.znz.jcov;

import java.math.BigInteger;

public class UInt64 extends Number implements Comparable<UInt64> {

	private static final long serialVersionUID = 8663821253565166565L;
	
	private BigInteger value;
	
	private static final BigInteger UINT32_MAX_VALUE = BigInteger.valueOf(UInt32.MAX_VALUE);
	
	public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615");
	public static final BigInteger MIN_VALUE = new BigInteger("0");
	
	public UInt64(UInt32 low, UInt32 high)
	{
		value = BigInteger.valueOf(high.value());
		value.multiply(UINT32_MAX_VALUE);
		value.add(BigInteger.valueOf(low.value()));
	}
	
	public UInt64(BigInteger value) throws NumberFormatException {
		this.value = value;
		if(value.compareTo(MAX_VALUE) > 0 || value.compareTo(MIN_VALUE) < 0)
			throw new NumberFormatException("The number must be from " + MIN_VALUE + " - " + MAX_VALUE + "!");
	}
	
	public UInt64(String s) throws NumberFormatException {
		value = new BigInteger(s);
		if(value.compareTo(MAX_VALUE) > 0 || value.compareTo(MIN_VALUE) < 0)
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
	public int compareTo(UInt64 anotherUInt64) {
		return value.compareTo(anotherUInt64.value);
	}
	
	public BigInteger value()
	{
		return value;
	}
	
}
