package Curve;

import java.math.BigInteger;
import java.security.spec.ECPoint;

/**
 * This class is a collection of all known Certicom curves.
 * For efficiency reasons the curve's "a" parameter always follows the rule: a = p - 3.
 * 
 * Check this out: 
 * "STANDARDS FOR EFFICIENT CRYPTOGRAPHY, 
 *  SEC 2: Recommended Elliptic Curve Domain Parameters, 
 *  Certicom Research, September 20, 2000, Version 1.0"
 */
public final class Certicom extends Curve
{

	/** The author of these curves */
	private final String authority = "Certicom";
	
	/**
	 * Instantiates the Certicom curve secp160r2.
	 */
	public Certicom()
	{
		super();
		this.set_author(authority);
		this.secp160r2();
	}
	
	
	/**
	 * Instantiates a Certicom curve according to the security level given.
	 * The cofactor of each curve is 1 unless where better specified. <p>
	 * Possible value pairs are as follows: <p>
	 * (56, 1) -> Verifiably Random. <p>
	 * (56, 2) -> Verifiably Random, cofactor = 4. <p>
	 * 
	 * (64, 1) -> Verifiably Random. <p>
	 * (64, 2) -> Verifiably Random, cofactor = 4. <p>
	 * 
	 * (80, 1) -> Koblitz / Anomalous. <p>
	 * (80, 2) -> Verifiably Random. <p>
	 * (80, 3) -> Verifiably Random. <p>
	 * 
	 * (96, 1) -> Koblitz / Anomalous.  <p>
	 * (96, 2) -> Verifiably Random.  <p>
	 * 
	 * (112, 1) -> Koblitz / Anomalous.  <p>
	 * (112, 2) -> Verifiably Random.  <p>
	 * 
	 * (128, 1) -> Koblitz / Anomalous.  <p>
	 * (128, 2) -> Verifiably Random.  <p>
	 * 
	 * (192, 1) -> Verifiably Random.  <p>
	 * 
	 * (256, 1) -> Verifiably Random.  <p>
	 * 
	 * In case of a different pair, (80, 3) will be used as default pair
	 * 
	 * @param securityLevel The security level of the curve to use
	 * @param version The protocol version to use
	 */
	public Certicom(int securityLevel, int version)
	{
		super();
		this.set_author(authority);
		
		if(securityLevel == Curve.SECURITY_LEVEL_56)
		{
			if(version == 1)
			{
				this.secp112r1();
			}
			else
			{
				this.secp112r2();
			}
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_64)
		{
			if(version == 1)
			{
				this.secp128r1();
			}
			else
			{
				this.secp128r2();
			}
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_80)
		{
			if(version == 1)
			{
				this.secp160k1();
			}
			else if(version == 2)
			{
				this.secp160r1();
			}
			else
			{
				this.secp160r2();
			}
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_96)
		{
			if(version == 1)
			{
				this.secp192k1();
			}
			else
			{
				this.secp192r1();
			}
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_112)
		{
			if(version == 1)
			{
				this.secp224k1();
			}
			else
			{
				this.secp224r1();
			}
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_128)
		{
			if(version == 1)
			{
				this.secp256k1();
			}
			else
			{
				this.secp256r1();
			}
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_192)
		{
			this.secp384r1();
		}
		
		else if(securityLevel == Curve.SECURITY_LEVEL_256)
		{
			this.secp521r1();
		}
		
		else
		{
			this.secp160r2();
		}
	}
	
	
	/**
	 * Lowest security level curve. Version 1. Verifiably random.
	 */
	private void secp112r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("4451685225093714772084598273548424"));
		this.set_b(new BigInteger("2061118396808653202902996166388514"));
		
		this.set_p(new BigInteger("4451685225093714772084598273548427"));

		BigInteger x_G = new BigInteger("188281465057972534892223778713752");
		BigInteger y_G = new BigInteger("3419875491033170827167861896082688");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("4451685225093714776491891542548933"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_56);
	}
	
	
	/**
	 * Lowest security level curve. Version 2. Verifiably random.
	 */
	private void secp112r2()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("4451685225093714772084598273548427"));
		this.set_b(new BigInteger("1660538572255285715897238774208265"));
		
		this.set_p(new BigInteger("4451685225093714772084598273548427"));

		BigInteger x_G = new BigInteger("1534098225527667214992304222930499");
		BigInteger y_G = new BigInteger("3525120595527770847583704454622871");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("1112921306273428674967732714786891"));
		this.set_h(4);
		
		this.set_L(Curve.SECURITY_LEVEL_56);
	}
	
	
	/**
	 * Very low security level curve. Version 1. Verifiably random.
	 */
	private void secp128r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("340282366762482138434845932244680310780"));
		this.set_b(new BigInteger("308990863222245658030922601041482374867"));
		
		this.set_p(new BigInteger("340282366762482138434845932244680310783"));

		BigInteger x_G = new BigInteger("29408993404948928992877151431649155974");
		BigInteger y_G = new BigInteger("275621562871047521857442314737465260675");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("340282366762482138443322565580356624661"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_64);
	}
	
	
	/**
	 * Very low security level curve. Version 2. Verifiably random.
	 */
	private void secp128r2()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("284470887156368047300405921324061011681"));
		this.set_b(new BigInteger("126188322377389722996253562430093625949"));
		
		this.set_p(new BigInteger("340282366762482138434845932244680310783"));

		BigInteger x_G = new BigInteger("164048790688614013222215505581242564928");
		BigInteger y_G = new BigInteger("52787839253935625605232456597451787076");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("85070591690620534603955721926813660579"));
		this.set_h(4);
		
		this.set_L(Curve.SECURITY_LEVEL_64);
	}
	
	
	/**
	 * Low security level curve. Version 1. Koblitz / Anomalous curve.
	 */
	private void secp160k1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(BigInteger.ZERO);
		this.set_b(new BigInteger("7"));
		
		this.set_p(new BigInteger("1461501637330902918203684832716283019651637554291"));

		BigInteger x_G = new BigInteger("338530205676502674729549372677647997389429898939");
		BigInteger y_G = new BigInteger("842365456698940303598009444920994870805149798382");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("1461501637330902918203686915170869725397159163571"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_80);
	}
	

	/**
	 * Low security level curve. Version 2. Verifiably random.
	 */
	private void secp160r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("1461501637330902918203684832716283019653785059324"));
		this.set_b(new BigInteger("163235791306168110546604919403271579530548345413"));
		
		this.set_p(new BigInteger("1461501637330902918203684832716283019653785059327"));

		BigInteger x_G = new BigInteger("425826231723888350446541592701409065913635568770");
		BigInteger y_G = new BigInteger("203520114162904107873991457957346892027982641970");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("1461501637330902918203687197606826779884643492439"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_80);
	}
	
	
	/**
	 * Low security level curve. Version 3. Verifiably random.
	 */
	private void secp160r2()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("1461501637330902918203684832716283019651637554288"));
		this.set_b(new BigInteger("1032640608390511495214075079957864673410201913530"));
		
		this.set_p(new BigInteger("1461501637330902918203684832716283019651637554291"));

		BigInteger x_G = new BigInteger("473058756663038503608844550604547710019657059949");
		BigInteger y_G = new BigInteger("1454008495369951658060798698479395908327453245230");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("1461501637330902918203685083571792140653176136043"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_80);
	}

	
	/**
	 * Low-mid security level curve. Version 1. Koblitz / Anomalous curve.
	 */
	private void secp192k1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(BigInteger.ZERO);
		this.set_b(new BigInteger("3"));

		this.set_p(new BigInteger("6277101735386680763835789423207666416102355444459739541047"));

		BigInteger x_G = new BigInteger("5377521262291226325198505011805525673063229037935769709693");
		BigInteger y_G = new BigInteger("3805108391982600717572440947423858335415441070543209377693");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("6277101735386680763835789423061264271957123915200845512077"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_96);
	}
	
	
	/**
	 * Low-mid security level curve. Version 2. Verifiably random.
	 */
	private void secp192r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("6277101735386680763835789423207666416083908700390324961276"));
		this.set_b(new BigInteger("2455155546008943817740293915197451784769108058161191238065"));
		
		this.set_p(new BigInteger("6277101735386680763835789423207666416083908700390324961279"));

		BigInteger x_G = new BigInteger("602046282375688656758213480587526111916698976636884684818");
		BigInteger y_G = new BigInteger("174050332293622031404857552280219410364023488927386650641");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("6277101735386680763835789423176059013767194773182842284081"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_96);
	}
	
	
	/**
	 * Mid-low security level curve. Version 1. Koblitz / Anomalous curve.
	 */
	private void secp224k1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(BigInteger.ZERO);
		this.set_b(new BigInteger("5"));	
		
		this.set_p(new BigInteger("26959946667150639794667015087019630673637144422540572481099315275117"));

		BigInteger x_G = new BigInteger("16983810465656793445178183341822322175883642221536626637512293983324");
		BigInteger y_G = new BigInteger("13272896753306862154536785447615077600479862871316829862783613755813");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("26959946667150639794667015087019640346510327083120074548994958668279"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_112);
	}
	
	
	/**
	 * Mid-low security level curve. Version 2. Verifiably random.
	 */
	private void secp224r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("26959946667150639794667015087019630673557916260026308143510066298878"));
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
	 * Mid security level curve. Version 1. Koblitz / Anomalous curve.
	 */
	private void secp256k1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(BigInteger.ZERO);
		this.set_b(new BigInteger("7"));	

		this.set_p(new BigInteger("115792089237316195423570985008687907853269984665640564039457584007908834671663"));

		BigInteger x_G = new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240");
		BigInteger y_G = new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_128);
	}
	
	
	/**
	 * Mid security level curve. Version 2. Verifiably random.
	 */
	private void secp256r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"));
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
	 * Mid-high security level curve. Version 1. Verifiably random.
	 */
	private void secp384r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("39402006196394479212279040100143613805079739270465446667948293404245721771496870329047266088258938001861606973112316"));
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
	 * High security level curve. Version 1. Verifiably random.
	 */
	private void secp521r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057148"));
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

