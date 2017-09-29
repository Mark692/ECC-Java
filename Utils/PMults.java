package Utils;

import java.math.BigInteger;
import java.security.spec.ECPoint;

import Curve.Curve;

public class PMults extends Curve
{

	/**
	 * Instantiates a curve for the next operations
	 */
	public PMults(Curve c)
	{
		super();

		this.set_author(c.get_author());
		this.set_name(c.get_name());
		this.set_p(c.get_p());
		this.set_b(c.get_b());
		this.set_a(c.get_a());
		this.set_G(c.get_G());
		this.set_n(c.get_n());
		this.set_h(c.get_h());
		this.set_L(c.get_L());
	}

	
	/**
	 * "Advances in Elliptic Curve Cryptography" - Page 99
	 * 
	 * @param k The factor
	 * @return k*G
	 */
	public ECPoint DoubleAddSubtract(BigInteger k)
	{
		return this.DoubleAddSubtract(k, get_G());
	}

	
	/**
	 * "Advances in Elliptic Curve Cryptography" - Page 99
	 * 
	 * @param k The factor
	 * @param P The point
	 * @return k*P
	 */
	public ECPoint DoubleAddSubtract(BigInteger k, ECPoint P)
	{
		String k_2 = k.toString(2); //Produces the binary notation of k
		ECPoint R0 = ECPoint.POINT_INFINITY;
		ECPoint R1 = P;
		int switcher = 0;

		for(int i = k_2.length() - 1; i >= 0; i--)
		{
			if(k_2.charAt(i) == '0')
			{
				if(switcher == 11)
				{
					R0 = this.PointAdd(R0, R1);
				}
				R1 = this.PointDouble(R1);
				switcher = 0;
			}

			if(k_2.charAt(i) == '1')
			{

				if(switcher == 0)
				{
					R0 = this.PointAdd(R0, R1);
					R1 = this.PointDouble(R1);
					switcher = 1;
				}
				else if(switcher == 1)
				{
					R0 = this.PointAdd(R0, this.negate_y(R1));
					R1 = this.PointDouble(R1);
					switcher = 11;
				}
				else if(switcher == 11)
				{
					R1 = this.PointDouble(R1);
				}
			}
		}
		
		if(switcher == 11)
		{
			R0 = this.PointAdd(R0, R1);
		}
		
		return R0;
	}


	
	/**
	 * "Advances in Elliptic Curve Cryptography" - Page 102
	 * 
	 * @param k The factor
	 * @return k*P
	 */
	public ECPoint DoubleAddAlways(BigInteger k)
	{
		return this.DoubleAddAlways(k, get_G());
	}
	
	
	/**
	 * "Advances in Elliptic Curve Cryptography" - Page 102
	 * 
	 * @param k The factor
	 * @param P The point
	 * @return k*P
	 */
	public ECPoint DoubleAddAlways(BigInteger k, ECPoint P)
	{
		String k_2 = k.toString(2); //Produces the binary notation of k
		ECPoint R0 = P;
		ECPoint R1 = ECPoint.POINT_INFINITY;

		for(int i = 1; i < k_2.length(); i++)
		{
			R0 = this.PointDouble(R0);
			
			if(k_2.charAt(i) == '1')
			{
				R0 = this.PointAdd(R0, P);
			}
			else
			{
				R1 = this.PointAdd(R1, P);
			}
		}
		return R0;
	}
}
