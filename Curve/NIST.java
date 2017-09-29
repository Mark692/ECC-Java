package Curve;

import java.math.BigInteger;
import java.security.spec.ECPoint;

/**
 * This class is a collection of all known and standardised NIST curves.
 * For efficiency reasons the curve's "a" parameter is always: a = - 3.
 * 
 * Check this out: 
 * "RECOMMENDED ELLIPTIC CURVES FOR FEDERAL GOVERNMENT USE, July 1999"
 */
public final class NIST extends Curve
{

	/** The author of these curves */
	private final String authority = "NIST";
	
	
	/**
	 * Instantiates the curve P-192 as default curve
	 */
	public NIST()
	{
		super();
		this.set_author(authority);
		this.set_a(BigInteger.valueOf(-3));
		
		this.P192();
	}
	
	
	/**
	 * Instantiates a NIST curve according to the security level given. <p>
	 * Possible values are 112, 128, 192, 256. <p>
	 * In case of a different value, 80 will be used as default
	 * 
	 * @param securityLevel The security level of the curve to use
	 */
	public NIST(int securityLevel)
	{
		super();
		this.set_author(authority);
		this.set_a(BigInteger.valueOf(-3));
		
		if(securityLevel == Curve.SECURITY_LEVEL_112)
		{
			this.P224();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_128)
		{
			this.P256();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_192)
		{
			this.P384();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_256)
		{
			this.P521();
		}
		else
		{
			this.P192();
		}
	}
	
	
	/**
	 * Low security level curve
	 */
	private void P192()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_b(new BigInteger("2455155546008943817740293915197451784769108058161191238065"));
		
		this.set_p(new BigInteger("6277101735386680763835789423207666416083908700390324961279"));

		BigInteger x_G = new BigInteger("602046282375688656758213480587526111916698976636884684818");
		BigInteger y_G = new BigInteger("174050332293622031404857552280219410364023488927386650641");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("6277101735386680763835789423176059013767194773182842284081"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_80); //About this security level check "RECOMMENDED ELLIPTIC CURVES FOR FEDERAL GOVERNMENT USE - July 1999"
	}
	

	/**
	 * Mid-low security level curve
	 */
	private void P224()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_b(new BigInteger("18958286285566608000408668544493926415504680968679321075787234672564"));
		
		this.set_p(new BigInteger("26959946667150639794667015087019630673557916260026308143510066298881"));

		BigInteger x_G = new BigInteger("19277929113566293071110308034699488026831934219452440156649784352033");
		BigInteger y_G = new BigInteger("19926808758034470970197974370888749184205991990603949537637343198772");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("26959946667150639794667015087019625940457807714424391721682722368061"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_112);
	}
	

	/**
	 * Mid security level curve
	 */
	private void P256()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_b(new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"));
		
		this.set_p(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951"));

		BigInteger x_G = new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286");
		BigInteger y_G = new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_128);
	}
	

	/**
	 * Mid-high security level curve
	 */
	private void P384()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_b(new BigInteger("27580193559959705877849011840389048093056905856361568521428707301988689241309860865136260764883745107765439761230575"));
		
		this.set_p(new BigInteger("39402006196394479212279040100143613805079739270465446667948293404245721771496870329047266088258938001861606973112319"));

		BigInteger x_G = new BigInteger("26247035095799689268623156744566981891852923491109213387815615900925518854738050089022388053975719786650872476732087");
		BigInteger y_G = new BigInteger("8325710961489029985546751289520108179287853048861315594709205902480503199884419224438643760392947333078086511627871");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("39402006196394479212279040100143613805079739270465446667946905279627659399113263569398956308152294913554433653942643"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_192);
	}
	

	/**
	 * High security level curve
	 */
	private void P521()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_b(new BigInteger("1093849038073734274511112390766805569936207598951683748994586394495953116150735016013708737573759623248592132296706313309438452531591012912142327488478985984"));
		
		this.set_p(new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151"));

		BigInteger x_G = new BigInteger("2661740802050217063228768716723360960729859168756973147706671368418802944996427808491545080627771902352094241225065558662157113545570916814161637315895999846");
		BigInteger y_G = new BigInteger("3757180025770020463545507224491183603594455134769762486694567779615544477440556316691234405012945539562144444537289428522585666729196580810124344277578376784");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397655394245057746333217197532963996371363321113864768612440380340372808892707005449"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_256);
	}


}

