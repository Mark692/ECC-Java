package Utils;

import java.math.BigInteger;

public class Maths
{
	/**
	 * Checks whether a Mersenne number is prime. These numbers are written in the form:
	 * (2^exponent) - 1 = ?
	 * Given the exponent, this function will tell whether the number (?) is prime or not
	 * 
	 * @param exponent The exponent in the Mersenne number form: (2^exponent) - 1 = ?
	 * @return Whether the (2^exponent) - 1 is prime or not
	 */
	public static boolean LucasLehmerPrimalityCheck(int exponent)
	{
		BigInteger LLnumber = BigInteger.valueOf(4);
		BigInteger two = BigInteger.valueOf(2);
		BigInteger MersenneNumber = two.pow(exponent).subtract(BigInteger.ONE);
		
		if(MersenneNumber.mod(BigInteger.valueOf(5)).compareTo(BigInteger.ZERO) == 0)
		{
			System.out.println("2^"+exponent+" -1 = "+MersenneNumber+" NON è primo");
			return false;
		}
		
		for(int i = 1; i < exponent - 1; i++) 
		{
			LLnumber = LLnumber.pow(2).subtract(two).mod(MersenneNumber);
		}
		if(LLnumber.compareTo(BigInteger.ZERO) == 0)
		{
			System.out.println("2^"+exponent+" -1 = "+MersenneNumber+" è PRIMO");
			return true;
		}
		System.out.println("2^"+exponent+" -1 = "+MersenneNumber+" NON è primo");
		return false;
	}

}
