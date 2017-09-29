package DigitalSign;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;
import java.util.TreeMap;

import Utils.Strings;

/**
 * This class contains basic function to be used in child classes
 */
public abstract class funs
{
	/**
	 * Hashes a string with an appropriate hash algorithm chosen accordingly to the seedLength output
	 * 
	 * @param k The string to hash
	 * @return A bit-string of the hash of k. Its length is seedLength
	 */
	protected String hashIt(String k, int seedLength)
	{
		String hashFunction = "SHA-512";
		if(seedLength <= 160)
		{
			hashFunction = "SHA-1";
		}
		else if(seedLength <= 256)
		{
			hashFunction = "SHA-256";
		}
		
        String seed = Strings.hashIt(k, hashFunction);
        
        return seed.substring(0, seedLength);
    }
	

	/**
	 * Converts a bit string into a BitSet
	 * 
	 * @param s A bit string to convert into a set of bits
	 * @param bitsetLEN The length of the final bit set to return
	 * @return The bit set of the string given
	 */
	public BitSet string2bitset(String s, int bitsetLEN)
	{
		BitSet output = new BitSet(bitsetLEN);
		int offset = bitsetLEN - s.length();
		
		for(int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i) == '1')
			{
				output.set(offset + i);
			}
		}
		return output;
	}
	
	
	/**
	 * Converts a bit set into a string
	 * 
	 * @param bs The bit set to convert
	 * @return The bit string representing the bit set
	 */
	public String bitset2string(BitSet bs, int out_length)
	{
		StringBuffer buff = new StringBuffer();
		for(int i = 0; i < bs.size(); i++)
		{
			buff.append(bs.get(i) ? '1' : '0');
		}
		return buff.toString().substring(0, out_length);
	}
	
	
	/**
	 * Generates a seed with a given length for the initial entropy
	 * 
	 * @param hashLength Entropy's length for the hash and final bit length for the output
	 * @return A bit string hash of desired bit length
	 */
	protected String generateSEED(int hashLength)
	{
		Random rnjesus = new Random();
		BigInteger numSEED = new BigInteger(hashLength, rnjesus);
		return this.hashIt(numSEED.toString(), hashLength);
	}
	
	
	/**
	 * This function enables to determine the factorization of a BigInteger "n".
	 * I.E. 
	 * n=2175030 = 2*3*3*5*11*13*13*13, the output shall be 
	 * output=[2][1], [3][2], [5][1], [11][1], [13][3] as a TreeMap
	 * 
	 * @param n The number to factorise
	 * @return A TreeMap with factors as keys and their powers as values 
	 */
	public TreeMap<BigInteger, BigInteger> factor_it(BigInteger n)
	{
		TreeMap<BigInteger, BigInteger> prods = new TreeMap<>();
		
		//Test 1 - Using Java a function
		int probability = 4000;
		if(n.isProbablePrime(probability)) // % of correctness = 1 - (2^(-probability)). The higher the probability, the better
		{
			prods.put(n, BigInteger.ONE);
			return prods;
		}

		//Test 2 - Version 2 as of http://www.javascripter.net/faq/numberisprime.htm last algorithm
		BigInteger factor = BigInteger.valueOf(2); //Calculates the powers of 2
		n = this.module_it(n, factor, prods);

		factor = BigInteger.valueOf(3); //Calculates the powers of 3
		n = this.module_it(n, factor, prods);

		factor = BigInteger.valueOf(5); //Calculates the powers of 5
		n = this.module_it(n, factor, prods);

		factor = BigInteger.valueOf(7); //Enters the cycle
		
		while(n.compareTo(BigInteger.ONE) > 0)
		{
			n = this.module_it(n, factor, prods); //n % factor

			factor = factor.add(BigInteger.valueOf(4)); //n % (factor + 4)
			n = this.module_it(n, factor, prods);

			factor = factor.add(BigInteger.valueOf(2)); //n % (factor + 6)
			n = this.module_it(n, factor, prods);

			factor = factor.add(BigInteger.valueOf(4)); //n % (factor + 10)
			n = this.module_it(n, factor, prods);

			factor = factor.add(BigInteger.valueOf(2)); //n % (factor + 12)
			n = this.module_it(n, factor, prods);

			factor = factor.add(BigInteger.valueOf(4)); //n % (factor + 16)
			n = this.module_it(n, factor, prods);

			factor = factor.add(BigInteger.valueOf(6)); //n % (factor + 22)
			n = this.module_it(n, factor, prods);
			
			factor = factor.add(BigInteger.valueOf(2)); //n % (factor + 24)
			n = this.module_it(n, factor, prods);

			factor = factor.add(BigInteger.valueOf(6)); //factor + 30
			
			//New test at the end. This will speed up the cycle
			if(n.isProbablePrime(probability))
			{
				System.out.println("Yeaaah, sono un dannato primo: "+n);
				prods.put(n, BigInteger.ONE);
				return prods;
			}
		}
		
		return prods;
	}
	
	
	/**
	 * Enables to take note of each factor and their powers for the number n
	 * 
	 * @param n The number to factorise
	 * @param modulo Determines the factorisation
	 * @param myMap A map that will take note of n's factors (keys) and their powers (values)
	 * @return The number n divided by the current factor^power
	 */
	private BigInteger module_it(BigInteger n, BigInteger modulo, TreeMap<BigInteger, BigInteger> myMap)
	{
		int check = (n.remainder(modulo)).compareTo(BigInteger.ZERO); //n % modulo == 0
		if(check == 0) //remainder <-> mod. The "mod" function always returns NON-Negative
		{
			BigInteger exp = BigInteger.ZERO;

			while(check == 0) //while n can be divided by the modulo
			{
				exp = exp.add(BigInteger.ONE); // = exp++;
				n = n.divide(modulo);
				check = (n.remainder(modulo)).compareTo(BigInteger.ZERO); //Changes at each iteration
			}
			System.out.println("Divisore = "+modulo+"^"+exp);
			myMap.put(modulo, exp);
		}
		return n;
	}
}
