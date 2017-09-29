package Curve;

import java.math.BigInteger;
import java.security.spec.ECPoint;

/**
 * This class is a collection of all known Brainpool curves.
 * 
 * Check this out: 
 * "ECC Brainpool Standard Curves and Curve Generation, v. 1.0, 19.10.2005"
 */
public final class Brainpool extends Curve
{
	/** The author of these curves */
	private final String authority = "Brainpool";
	
	/**
	 * Instantiates the curve brainpoolP160r1 as default curve
	 */
	public Brainpool()
	{
		super();
		this.set_author(authority);
		this.brainpoolP160r1();
	}
	
	
	/**
	 * Instantiates a NIST curve according to the security level given. <p>
	 * Possible values are 96, 112, 128, 160, 192, 256. <p>
	 * In case of a different value, 80 will be used as default
	 * 
	 * @param securityLevel The security level of the curve to use
	 */
	public Brainpool(int securityLevel)
	{
		super();
		this.set_author(authority);
		
		if(securityLevel == Curve.SECURITY_LEVEL_96)
		{
			this.brainpoolP192r1();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_112)
		{
			this.brainpoolP224r1();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_128)
		{
			this.brainpoolP256r1();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_160)
		{
			this.brainpoolP320r1();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_192)
		{
			this.brainpoolP384r1();
		}
		else if(securityLevel == Curve.SECURITY_LEVEL_256)
		{
			this.brainpoolP512r1();
		}
		else
		{
			this.brainpoolP160r1();
		}
		
	}
	
	
	/**
	 * Low security level curve
	 */
	private void brainpoolP160r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("297190522446607939568481567949428902921613329152"));
		this.set_b(new BigInteger("173245649450172891208247283053495198538671808088"));
		
		this.set_p(new BigInteger("1332297598440044874827085558802491743757193798159"));
		
		BigInteger x_G = new BigInteger("1089473557631435284577962539738532515920566082499");
		BigInteger y_G = new BigInteger("127912481829969033206777085249718746721365418785");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("1332297598440044874827085038830181364212942568457"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_80);
	}
	
	
	/**
	 * Low-mid security level curve
	 */
	private void brainpoolP192r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("2613009377683017747869391908421543348309181741502784219375"));
		this.set_b(new BigInteger("1731160591135112004210203499537764623771657619977468323273"));
		
		this.set_p(new BigInteger("4781668983906166242955001894344923773259119655253013193367"));

		BigInteger x_G = new BigInteger("4723188856514392935399337699153522173525168621081341681622");
		BigInteger y_G = new BigInteger("507884783101387741749746950209061101579755255809652136847");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("4781668983906166242955001894269038308119863659119834868929"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_96);
	}
	
	
	/**
	 * Mid-low security level curve
	 */
	private void brainpoolP224r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("11020725272625742361946480833014344015343456918668456061589001510723"));
		this.set_b(new BigInteger("3949606626053374030787926457695139766118442946052311411513528958987"));
		
		this.set_p(new BigInteger("22721622932454352787552537995910928073340732145944992304435472941311"));

		BigInteger x_G = new BigInteger("1428364927244201726431498207475486496993067267318520844137448783997");
		BigInteger y_G = new BigInteger("9337555360448823227812410753177468631215558779020518084752618816205");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("22721622932454352787552537995910923612567546342330757191396560966559"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_112); 
	}
	
	
	/**
	 * Mid security level curve
	 */
	private void brainpoolP256r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("56698187605326110043627228396178346077120614539475214109386828188763884139993"));
		this.set_b(new BigInteger("17577232497321838841075697789794520262950426058923084567046852300633325438902"));
		
		this.set_p(new BigInteger("76884956397045344220809746629001649093037950200943055203735601445031516197751"));

		BigInteger x_G = new BigInteger("63243729749562333355292243550312970334778175571054726587095381623627144114786");
		BigInteger y_G = new BigInteger("38218615093753523893122277964030810387585405539772602581557831887485717997975");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("76884956397045344220809746629001649092737531784414529538755519063063536359079"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_128); 
	}
	
	
	/**
	 * Mid+ security level curve
	 */
	private void brainpoolP320r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("524709318439392693105919717518043758943240164412117372990311331314771510648804065756354311491252"));
		this.set_b(new BigInteger("684460840191207052139729091116995410883497412720006364295713596062999867796741135919289734394278"));
		
		this.set_p(new BigInteger("1763593322239166354161909842446019520889512772719515192772960415288640868802149818095501499903527"));

		BigInteger x_G = new BigInteger("565203972584199378547773331021708157952136817703497461781479793049434111597020229546183313458705");
		BigInteger y_G = new BigInteger("175146432689526447697480803229621572834859050903464782210773312572877763380340633688906597830369");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("1763593322239166354161909842446019520889512772717686063760686124016784784845843468355685258203921"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_160); 
	}
	
	
	/**
	 * Mid-high security level curve
	 */
	private void brainpoolP384r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("19048979039598244295279281525021548448223459855185222892089532512446337024935426033638342846977861914875721218402342"));
		this.set_b(new BigInteger("717131854892629093329172042053689661426642816397448020844407951239049616491589607702456460799758882466071646850065"));
		
		this.set_p(new BigInteger("21659270770119316173069236842332604979796116387017648600081618503821089934025961822236561982844534088440708417973331"));

		BigInteger x_G = new BigInteger("4480579927441533893329522230328287337018133311029754539518372936441756157459087304048546502931308754738349656551198");
		BigInteger y_G = new BigInteger("21354446258743982691371413536748675410974765754620216137225614281636810686961198361153695003859088327367976229294869");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("21659270770119316173069236842332604979796116387017648600075645274821611501358515537962695117368903252229601718723941"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_192);
	}
	
	
	/**
	 * High security level curve
	 */
	private void brainpoolP512r1()
	{
		this.set_name(Thread.currentThread().getStackTrace()[1].getMethodName());
		this.set_a(new BigInteger("6294860557973063227666421306476379324074715770622746227136910445450301914281276098027990968407983962691151853678563877834221834027439718238065725844264138"));
		this.set_b(new BigInteger("3245789008328967059274849584342077916531909009637501918328323668736179176583263496463525128488282611559800773506973771797764811498834995234341530862286627"));
		
		this.set_p(new BigInteger("8948962207650232551656602815159153422162609644098354511344597187200057010413552439917934304191956942765446530386427345937963894309923928536070534607816947"));

		BigInteger x_G = new BigInteger("6792059140424575174435640431269195087843153390102521881468023012732047482579853077545647446272866794936371522410774532686582484617946013928874296844351522");
		BigInteger y_G = new BigInteger("6592244555240112873324748381429610341312712940326266331327445066687010545415256461097707483288650216992613090185042957716318301180159234788504307628509330");
		this.set_G(new ECPoint(x_G, y_G));
		
		this.set_n(new BigInteger("8948962207650232551656602815159153422162609644098354511344597187200057010413418528378981730643524959857451398370029280583094215613882043973354392115544169"));
		this.set_h(1);
		
		this.set_L(Curve.SECURITY_LEVEL_256); 
	}
}
