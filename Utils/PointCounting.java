package Utils;

import java.math.BigInteger;
import java.util.TreeMap;

import Curve.Curve;

public class PointCounting
{

	public BigInteger divisionPolinomial(Curve c, int l)
	{
		BigInteger a = c.get_a();
		BigInteger b = c.get_b();
		BigInteger x = c.get_G().getAffineX();
		BigInteger y = c.get_G().getAffineY();

		switch (l) 
		{
			case 0:
				return BigInteger.ZERO;

			case 1:
				return BigInteger.ONE;
				
			case 2:
				return y.multiply(BigInteger.valueOf(2)); // = 2y
				
			case 3:
				return x.pow(4).multiply(BigInteger.valueOf(3)) // 3x^4
							.add((a.multiply((x.pow(2))).multiply(BigInteger.valueOf(6)))) // +6ax^2
								.add((x.multiply(b).multiply(BigInteger.valueOf(12)))) // +12bx
									.subtract((a.pow(2))); // -a^2
				

			case 4:
				return y.multiply(BigInteger.valueOf(4)).multiply( // 4y* (...)
							x.pow(6) // x^6
							.add((a.multiply((x.pow(4))).multiply(BigInteger.valueOf(5)))) // +5ax^4
							.add(((x.pow(3)).multiply(b).multiply(BigInteger.valueOf(20)))) // +20bx^3
							.subtract(((a.pow(2)).multiply((x.pow(2))).multiply(BigInteger.valueOf(5)))) // -5 a^2 x^2
							.subtract((a.multiply(b).multiply(x).multiply(BigInteger.valueOf(4)))) // -4abx
							.subtract(((b.pow(3)).multiply(BigInteger.valueOf(8)))) // -8b^3
							.subtract(((a.pow(3)))) // -a^3
						); // End parenthesis
				
			default:
				return BigInteger.ZERO;
		}
				
				
	}

		
	public BigInteger chooseNext_l(Curve c, int l)
	{
		BigInteger y = c.get_G().getAffineY();
			
		TreeMap<Integer, BigInteger> divisors = new TreeMap<>();
		divisors.put(l, BigInteger.ZERO); //Default Value
		
		for(int i = 0; i <= 4; i++)
		{
			divisors.put(i, this.divisionPolinomial(c, i)); //Default Values
		}
		
		int l_valuesToTrack = l;
		while(l_valuesToTrack > 4)
		{
			int isOdd = Math.floorMod(l_valuesToTrack, 2);
			if(isOdd == 1) //It's odd
			{
				l_valuesToTrack = (l_valuesToTrack-1)/2;
			}
			else
			{
				l_valuesToTrack = l_valuesToTrack/2;
			}
			divisors.putIfAbsent(l_valuesToTrack, BigInteger.ZERO); //Default Value
		}
		
		Integer nextLowestKey = divisors.higherKey(4); //Will return null if no higher key exists
		while(nextLowestKey != null)
		{
			int isOdd = Math.floorMod(nextLowestKey, 2);
			BigInteger kValue = null;
			if(isOdd == 1) //It's odd
			{
				System.out.println("DISPARI = "+nextLowestKey);
				int k = (nextLowestKey - 1) / 2;
				System.out.println("k = "+k);
				BigInteger k_plus2 = divisors.get(k+2);
				if(k_plus2 == null)
				{
					k_plus2 = this.chooseNext_l(c, k+2);
				}
				BigInteger k_plus1 = divisors.get(k+1);
				if(k_plus1 == null)
				{
					k_plus1 = this.chooseNext_l(c, k+1);
				}
				BigInteger k_ = divisors.get(k);
				if(k_ == null)
				{
					k_ = this.chooseNext_l(c, k);
				}
				BigInteger k_minus1 = divisors.get(k-1);
				if(k_minus1 == null)
				{
					k_minus1 = this.chooseNext_l(c, k-1);
				}

				kValue = (k_plus2.multiply(k_.pow(3))).subtract((k_minus1.multiply(k_plus1.pow(3))));
			}
			else
			{
				System.out.println("PARI = "+nextLowestKey);
				int k = nextLowestKey/2;
				System.out.println("K = "+k);
				BigInteger y_2 = y.multiply(BigInteger.valueOf(2));
				
				BigInteger k_plus2 = divisors.get(k+2);
				if(k_plus2 == null)
				{
					k_plus2 = this.chooseNext_l(c, k+2);
				}
				BigInteger k_plus1 = divisors.get(k+1);
				if(k_plus1 == null)
				{
					k_plus1 = this.chooseNext_l(c, k+1);
				}
				BigInteger k_ = divisors.get(k);
				if(k_ == null)
				{
					k_ = this.chooseNext_l(c, k);
				}
				BigInteger k_minus1 = divisors.get(k-1);
				if(k_minus1 == null)
				{
					k_minus1 = this.chooseNext_l(c, k-1);
				}
				BigInteger k_minus2 = divisors.get(k-2);
				if(k_minus2 == null)
				{
					k_minus2 = this.chooseNext_l(c, k-2);
				}
				
				kValue = k_.multiply(
								(k_plus2.multiply(k_minus1.pow(2)))
								.subtract(k_minus2.multiply(k_plus1.pow(2)))
						).divide(y_2);
			}
			System.out.println("kValue = "+kValue);
			divisors.put(nextLowestKey, kValue); //Will replace the previous BigInteger.ZERO with the true value
			nextLowestKey = divisors.higherKey(nextLowestKey);
		}
		return divisors.get(l);
	}
	
}
