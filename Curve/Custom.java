package Curve;

import java.math.BigInteger;
import java.security.spec.ECPoint;

/**
 * Enables to create custom curves
 */
public class Custom extends Curve
{

	/**
	 * Empty curve for filling-in purposes
	 */
	public Custom()
	{
		super();
		this.set_author("");
		this.set_name("");
		this.set_a(BigInteger.ZERO);
		this.set_b(BigInteger.ZERO);
		this.set_p(BigInteger.ZERO);
		this.set_G(ECPoint.POINT_INFINITY);
		this.set_n(BigInteger.ZERO);
		this.set_h(0);
		this.set_L(0);
	}
	
	
	/**
	 * Custom curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param a Elliptic Curve's first parameter
	 * @param b Elliptic Curve's second parameter
	 * @param p The field's characteristic
	 */
	public Custom(String author, String curveName, BigInteger a, BigInteger b, BigInteger p)
	{
		super(author, curveName, a, b, p);
	}
	
	
	/**
	 * Custom curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param a Elliptic Curve's first parameter
	 * @param b Elliptic Curve's second parameter
	 * @param p The field's characteristic
	 * @param G Generator point
	 * @param n Order of G
	 * @param h Cofactor
	 * @param L Security Level
	 */
	public Custom(String author, String curveName, BigInteger a, BigInteger b, BigInteger p, ECPoint G, BigInteger n, int h, int L)
	{
		super(author, curveName, a, b, p, G, n, h, L);
	}
}
