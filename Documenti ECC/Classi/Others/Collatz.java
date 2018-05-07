package Others;

import java.math.BigInteger;
import java.util.TreeMap;

public class Collatz
{

	/**
	 * Applies the Collatz conjecture to a number n returning its Total Stopping Time
	 * 
	 * @param n The number chosen for the sequence
	 * @return The number of iterations before n = 1. Also known as "n's total stopping time"
	 */
	public int n3_p1(BigInteger n)
	{
		BigInteger two = BigInteger.valueOf(2);
		BigInteger three = BigInteger.valueOf(3);
		int totalStoppingTime = 0;

		while(n.compareTo(BigInteger.ONE) != 0)
		{
			if(n.mod(two).compareTo(BigInteger.ZERO) == 0)
			{
				n = n.divide(two); 
			}
			else
			{
				n = ((n.multiply(three)).add(BigInteger.ONE)).divide(two); //Here we take 2 steps if we do (3n+1) / 2
				totalStoppingTime++; //hence we increase the tst b one more step
			}
			totalStoppingTime++;
		}
		return totalStoppingTime;
	}
	
	
	/**
	 * Calculates the total stopping time of a number in the generic problem "n*x + 1"
	 * 
	 * @param n The number to use for the chain
	 * @return The Total Stopping Time for the problem "nx+1"
	 */
	public int opt(BigInteger n)
	{
		BigInteger two = BigInteger.valueOf(2);
		BigInteger three = BigInteger.valueOf(3);
		BigInteger four = BigInteger.valueOf(4);
		int totalStoppingTime = 0;

		while(n.compareTo(BigInteger.ONE) != 0)
		{
			BigInteger mod = n.mod(four);
			if(mod.compareTo(BigInteger.ZERO) == 0)
			{
				n = n.divide(four); 
				totalStoppingTime++;
			}
			else if(mod.compareTo(BigInteger.ONE) == 0)
			{
				TreeMap<BigInteger, Integer> tm = new TreeMap<>();
				tm.put(n, totalStoppingTime);
				this.g_fun(tm);
				n = tm.lastKey();
				totalStoppingTime = tm.get(n);
				n = n.multiply(three).add(BigInteger.ONE);
			}
			else if(mod.compareTo(two) == 0)
			{
				n = n.divide(two); 
			}
			else if(mod.compareTo(three) == 0)
			{
				n = ((n.multiply(three)).add(BigInteger.ONE)).divide(two);
				totalStoppingTime++;
			}
			totalStoppingTime++;
		}
		return totalStoppingTime;
	}

	
	/**
	 * Computes (n-1)/4 when n = 1 mod(4), recursively.
	 * Exits when n is no more n = 1 mod(4)
	 * 
	 * @param tm The tree map containing the number n and the total stopping time
	 */
	private void g_fun(TreeMap<BigInteger, Integer> tm)
	{
		BigInteger n = tm.lastKey();
		int iters = tm.get(n);
		tm.clear();
		
		BigInteger four = BigInteger.valueOf(4);
		while(n.mod(four).compareTo(BigInteger.ONE) == 0)
		{
			n = n.subtract(BigInteger.ONE).divide(four);
			iters += 2;
			tm.put(n, iters);
		}
	}
		
}
