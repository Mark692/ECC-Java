package Curve;

import java.math.BigInteger;
import java.security.spec.ECPoint;

public class Microsoft extends Curve
{
	/** The author of these curves */
	private final String authority = "Microsoft";
	
	/**
	 * Instantiates m80 curve.
	 */
	public Microsoft()
	{
		super();
		this.set_author(authority);
		this.set_name("m80"); //Totally fiction name. I baptised it.
		this.unknown();
	}
	
	
	/**
	 * HANDCOPIED and checked thereafter. Taken from http://wstein.org/edu/124/lectures/lecture29/lecture29/node2.html
	 */
	private void unknown()
	{
		BigInteger a = new BigInteger("317689081251325503476317476413827693272746955927");
		BigInteger b = new BigInteger("79052896607878758718120572025718535432100651934");
		BigInteger p = new BigInteger("785963102379428822376694789446897396207498568951");

		BigInteger x = new BigInteger("771507216262649826170648268565579889907769254176");
		BigInteger y = new BigInteger("390157510246556628525279459266514995562533196655");
		ECPoint G = new ECPoint(x, y);
		
		BigInteger n = new BigInteger("785963102379428822376693024881714957612686157429");
		int h = 1;
		int L = Curve.SECURITY_LEVEL_80;

		this.set_a(a);
		this.set_b(b);
		this.set_p(p);
		this.set_G(G);
		this.set_n(n);
		this.set_h(h);
		this.set_L(L);
	}

}
