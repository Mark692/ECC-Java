package Utils;

import java.math.BigInteger;
import java.util.Random;

/**
 * This class enables some of the most important functions shown in 
 * 
 * ANSI X9.62-1998, Public Key Cryptography For The Financial Services Industry, The ECDSA
 * SEC 1: Elliptic Curve Cryptography - Certicom Research. May 21, 2009. Version 2.0
 */
public class Standards
{
	/**
	 * ANSI X9.62-1998
	 * Computes the square root of a number mod(p).
	 * If it doesn't exists, it returns null
	 * 
	 * If toRoot = 0, then there is one square root mod(modulo), namely y = 0. 
	 * If toRoot != 0, then toRoot has either 0 or 2 square roots mod(modulo).
	 * If y is one square root, then the other is "modulo-y".
	 * 
	 * @param toRoot The number to square root
	 * @param modulo Field's cardinality
	 * @return The sqrt(toRoot) mod(modulo)
	 */
	public static BigInteger modular_sqrt(BigInteger toRoot, BigInteger modulo)
	{
		if(modulo.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0)
		{
			BigInteger u = (modulo.subtract(BigInteger.valueOf(3))).divide(BigInteger.valueOf(4));
			BigInteger y = toRoot.modPow(u.add(BigInteger.ONE), modulo);
			BigInteger z = y.modPow(BigInteger.valueOf(2), modulo);

			if(z.compareTo(toRoot) == 0)
			{
				return z;
			}
			return null;
		}
		
		else if(modulo.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(5)) == 0)
		{
			BigInteger u = (modulo.subtract(BigInteger.valueOf(5))).divide(BigInteger.valueOf(8));
			BigInteger gamma = (toRoot.multiply(BigInteger.valueOf(2))).modPow(u, modulo);
			BigInteger i = ((toRoot.multiply(BigInteger.valueOf(2))).multiply(gamma.pow(2))).mod(modulo);
			BigInteger y = toRoot.multiply(gamma).multiply(i).subtract(BigInteger.ONE).mod(modulo);
			BigInteger z = y.modPow(BigInteger.valueOf(2), modulo);

			if(z.compareTo(toRoot) == 0)
			{
				return z;
			}
			return null;
		}
		
		else if(modulo.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(1)) == 0)
		{
			BigInteger Q = toRoot;
			BigInteger u = (modulo.subtract(BigInteger.valueOf(1))).divide(BigInteger.valueOf(4));
			BigInteger k = u.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
			while(true)
			{
				//Generates a random number 0 <= P < p
				Random entropy = new Random();
				int bitsNumber; 
				BigInteger P;
				do
				{
					//This will generate a random number between [0, n.bitLength()]
					bitsNumber = entropy.nextInt(modulo.bitLength() + 1); 
					
					//This will generate a random BigInteger with a random number of bytes...
					P = new BigInteger(bitsNumber, entropy);
				}
				while(P.compareTo(modulo) >= 0); //... and will stop when 0 <= P < p

				BigInteger[] lucas = Standards.LucasSequences(modulo, P, Q, k);
				BigInteger U = lucas[0];
				BigInteger V = lucas[1];
				if(V.pow(2).mod(modulo).compareTo(Q.multiply(BigInteger.valueOf(4)).mod(modulo)) == 0)
				{
					return V.divide(BigInteger.valueOf(2)).mod(modulo);
				}
				if(U.mod(modulo).compareTo(BigInteger.ONE) != 0)
				{
					return null;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * ANSI X9.62-1998
	 * Computes the Lucas Sequences with the given parameters
	 * 
	 * @param p Field's cardinality
	 * @param P A number
	 * @param Q A number
	 * @param k1 A positive number representing the number of iterations to compute
	 * @return Two numbers
	 */
	private static BigInteger[] LucasSequences(BigInteger p, BigInteger P, BigInteger Q, BigInteger k1)
	{
		BigInteger delta = p.pow(2).subtract(Q.multiply(BigInteger.valueOf(4)));
		String k = k1.toString(2);
		BigInteger U = BigInteger.ONE;
		BigInteger V = P;
		for(int i = k.length() - 2; i >= 0; i--)
		{
			U = U.multiply(V).mod(p);
			V = ((V.pow(2).add(delta.multiply(U.pow(2)))).divide(BigInteger.valueOf(2))).mod(p);
			if(k.charAt(i) == 1)
			{
				U = ((U.multiply(P).add(V)).divide(BigInteger.valueOf(2))).mod(p);
				V = ((U.multiply(P).subtract(delta.multiply(U))).divide(BigInteger.valueOf(2))).mod(p);
			}
		}
		return new BigInteger[] {U, V};
	}
	
	
//	/**
//	 * ANSI X9.62-1998 - DOESN'T WORK. USELESS.
//	 * It should compute base^exponent mod(modulo) but it doesn't return the correct value.
//	 * 
//	 * @param base
//	 * @param exponent
//	 * @param modulo
//	 * @return base^exponent mod(modulo)
//	 */
//	private static BigInteger power(BigInteger base, BigInteger exponent, BigInteger modulo)
//	{
//		BigInteger e = exponent.mod(modulo.subtract(BigInteger.ONE));
//		if(e.compareTo(BigInteger.ZERO) == 0)
//		{
//			return BigInteger.ONE;
//		}
//		
//		String binary = e.toString(2); //Little endian
//		
//		int bin_len = binary.length();
//		BigInteger out = base;
//		for(int i = bin_len - 2; i >= 0; i--)
//		{
//			out = out.pow(2);
//			if(binary.charAt(i) == 1)
//			{
//				out = out.multiply(base);
//			}
//		}
//		return out.mod(modulo); //This is my idea but the problem is how we enter into the "for" loop
////		return out; //Original from ANSI X9.62
//	}
}
