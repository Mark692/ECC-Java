package Curve;

import java.math.BigInteger;
import java.util.Random;

import Utils.Standards;
import Utils.Strings;

public class VRandom extends Curve
{
	
	/**
	 * Verifiably Random curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 */
	public VRandom(String author, String curveName)
	{
		super();
		
		this.set_author(author);
		this.set_name(curveName);
		
		this.genSet_pL();
		this.genSet_ab();
	}
	
	
	/**
	 * Verifiably Random curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param p The field's characteristic
	 */
	public VRandom(String author, String curveName, BigInteger p)
	{
		super();
		this.set_author(author);
		this.set_name(curveName);
		p = this.validate_p(p);
		this.set_p(p);
		this.set_L(p.bitLength() / 2);

		this.genSet_ab();
	}
	
	
	/**
	 * Verifiably Random curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param L Curve's security level
	 */
	public VRandom(String author, String curveName, int L)
	{
		super();
		this.set_author(author);
		this.set_name(curveName);
		this.set_L(L);

		this.genSet_p(L);
		this.genSet_ab();
	}
	
	
	/**
	 * Verifiably Random curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param seed A string which will be used to create the curve
	 */
	public VRandom(String author, String curveName, String seed)
	{
		super();
		this.set_author(author);
		this.set_name(curveName);
		this.setSeed(seed);
		
		this.genSet_pL();
		this.ab_ifSeed_isValid(seed);
	}
	
	
	/**
	 * Verifiably Random curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param p The field's characteristic
	 * @param seed A string which will be used to create the curve
	 */
	public VRandom(String author, String curveName, BigInteger p, String seed)
	{
		super();
		this.set_author(author);
		this.set_name(curveName);
		this.setSeed(seed);
		
		p = this.validate_p(p);
		this.set_p(p);
		this.set_L(p.bitLength() / 2);
		
		this.ab_ifSeed_isValid(seed);
	}
	
	
	/**
	 * Verifiably Random curve with own parameters to insert
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param L Curve's security level
	 * @param seed A string which will be used to create the curve
	 */
	public VRandom(String author, String curveName, int L, String seed)
	{
		super();
		this.set_author(author);
		this.set_name(curveName);
		this.setSeed(seed);
		this.set_L(L);
		
		this.genSet_p(L);
		this.ab_ifSeed_isValid(seed);
	}
	
	
	/**
	 * Generate a random field's cardinality with a given Security Level "L". 
	 * 
	 * @param L The security level given for this curve
	 */
	private void genSet_p(int L)
	{
		BigInteger p;

		Random entropy = new Random();

		/** BitLength = 2L, Probability to be prime = 2017, Randomness = entropy */
		p = new BigInteger(L*2, 2017, entropy);
		
		this.set_p(p);
	}
	
	
	/**
	 * Generate field's cardinality "p" at random and computes its Security Level "L". 
	 * The curve's security level will be within 80 and 256 randomly
	 */
	private void genSet_pL()
	{
		BigInteger p;

		Random entropy = new Random();
		int min = 2 * Curve.SECURITY_LEVEL_80;
		int max = 2 * Curve.SECURITY_LEVEL_256;
		BigInteger minval = new BigInteger("2417851639229258349412352"); // 2^80 * 2

		int bitsNumber = entropy.nextInt((max - min) + 1) + min; // [160, 512]
		
		do //Generate a random p in [0, 2^bitsNumber - 1] + a base number of 2^80 to ensure a faster result
		{
			p = new BigInteger(bitsNumber, entropy).add(minval).nextProbablePrime();
		}
		while(p.bitLength() < min); //... and will stop when p > minval & is (probable)prime
		
		// p = new BigInteger(bitsNumber, 2017, entropy) THIS IS ANALOGOUS TO THE CODE ABOVE
		
		this.set_p(p);
		this.set_L(p.bitLength() / 2);
	}
	

	/**
	 * ANSI X9.62, page 32, chapter "A.3.3.2 Elliptic curves over Fp"
	 * 
	 * Generates a Verifiably Random curve based on the field's cardinality p given
	 */
	private void genSet_ab()
	{
		BigInteger p = this.get_p();
		int p_len = p.bitLength();
		int max = Math.floorDiv(p_len - 1, 160);
		int offset = p_len - (160 * max) - 1;
		
		BigInteger c, cond2;
		do
		{
			String seed = Strings.seed();
			String seed_hash = Strings.hashIt(seed);
			String hash_bits = Strings.string2binary(seed_hash);
			int h_bitLen = hash_bits.length();
			String h_final = hash_bits.substring(h_bitLen - offset, h_bitLen); //bit string obtained by taking the "offset" rightmost bits of h.
			BigInteger z = new BigInteger(Strings.string2binary(seed), 2); //the integer whose binary expansion is given by the 160-bit string SEED
		
			BigInteger modulo = BigInteger.valueOf(2).pow(160);
			for(int i = 1; i <= max; i++)
			{
				String s_i = z.add(BigInteger.valueOf(i)).mod(modulo).toString(2);
				String h_i = Strings.hashIt(s_i);
				h_final = h_final + Strings.string2binary(h_i); //bit string obtained by the concatenation of h0 , h1, … , hv
			}
			
			this.setSeed(seed);
			c = new BigInteger(Strings.string2binary(h_final), 2); //integer whose binary expansion is given by the bit string h2
			cond2 = c.multiply(BigInteger.valueOf(4)).add(BigInteger.valueOf(27));
		} 
		while(c.compareTo(BigInteger.ZERO) != 0
				&& cond2.compareTo(BigInteger.ZERO) != 0);

		BigInteger a = p.subtract(BigInteger.valueOf(3));
		BigInteger b_squared = a.multiply(c.modInverse(p)).mod(p);
		BigInteger b = Standards.modular_sqrt(b_squared, p);
		if(b != null)
		{
			this.set_a(a);
			this.set_b(b);
		}
		else //The simplest choice is a = c and b = c
		{
			this.set_a(c.mod(p));
			this.set_b(c.mod(p));
		}
	}
	
	
	/**
	 * Computes a and b if seed is valid
	 * 
	 * @param seed The user defined seed
	 */
	private void ab_ifSeed_isValid(String seed)
	{
		BigInteger p = this.get_p();
		int p_len = p.bitLength();
		int max = Math.floorDiv(p_len - 1, 160);
		int offset = p_len - (160 * max) - 1;
		
		BigInteger c, cond2;

		String seed_hash = Strings.hashIt(seed);
		String hash_bits = Strings.string2binary(seed_hash);
		int h_bitLen = hash_bits.length();
		String h_final = hash_bits.substring(h_bitLen - offset, h_bitLen); //bit string obtained by taking the "offset" rightmost bits of h.
		BigInteger z = new BigInteger(Strings.string2binary(seed), 2); //the integer whose binary expansion is given by the 160-bit string SEED
	
		BigInteger modulo = BigInteger.valueOf(2).pow(160);
		for(int i = 1; i <= max; i++)
		{
			String s_i = z.add(BigInteger.valueOf(i)).mod(modulo).toString(2);
			String h_i = Strings.hashIt(s_i);
			h_final = h_final + Strings.string2binary(h_i); //bit string obtained by the concatenation of h0 , h1, … , hv
		}
		
		c = new BigInteger(Strings.string2binary(h_final), 2); //integer whose binary expansion is given by the bit string h2
		cond2 = c.multiply(BigInteger.valueOf(4)).add(BigInteger.valueOf(27));

		//Here comes the importance of the seed. 
		//If it's invalid this check will set a=0, b=0
		if(c.compareTo(BigInteger.ZERO) != 0
			&& cond2.compareTo(BigInteger.ZERO) != 0)
		{
			BigInteger a = p.subtract(BigInteger.valueOf(3));
			BigInteger b_squared = a.multiply(c.modInverse(p)).mod(p);
			BigInteger b = Standards.modular_sqrt(b_squared, p);
			if(b != null)
			{
				this.set_a(a); // = -3
				this.set_b(b); // = sqrt( a/c mod(p) )
			}
			else //The simplest choice is a = c and b = c
			{
				this.set_a(c.mod(p));
				this.set_b(c.mod(p));
			}
		}
		else
		{
			this.set_a(BigInteger.ZERO);
			this.set_b(BigInteger.ZERO);
		}
	}
	
	
	/**
	 * Checks whether the field's cardinality given is a prime number or not. 
	 * In case of a composite number, returns the next probable prime higher than p
	 * 
	 * @param p The field's cardinality given to be checked whether it's prime or composite
	 * @return A probable prime number which can be p it self or a close, bigger, number in case p is composite
	 */
	private BigInteger validate_p(BigInteger p)
	{
		if(p.isProbablePrime(2017) == true)
		{
			return p;
		}
		return p.nextProbablePrime();
	}
	
	
//	/**
//	 * Computes the generator point IF possible
//	 * 
//	 * @return True only if the generator point has been found.
//	 */
//	public boolean computeG()
//	{
//		BigInteger p = this.get_p();
//		if(this.existsStandardRoot(p) == true)
//		{
//			BigInteger x = p.divide(BigInteger.valueOf(2));
//			while(x.compareTo(p) <= 0)
//			{
//				BigInteger rightHandSide = (x.pow(3)).add(this.get_a().multiply(x)).add(this.get_b()).mod(p);
//				BigInteger y = Standards.modular_sqrt(rightHandSide, p);
//				if(y != null)
//				{
//					ECPoint G = new ECPoint(x, y);
//					if(this.isOnTheCurve(G) == true)
//					{
//						this.set_G(G);
//						return true;
//					}
//				}
//				x = x.add(BigInteger.ONE);
//			}
//		}
//		return false;
//	}
//	
//	
//	/**
//	 * Sets G's order and curve's cofactor IFF G exists. This condition is given by this.computeG() function
//	 */
//	public boolean set_nh()
//	{
//		if(this.get_G() != null)
//		{
//			BigInteger[] nh = this.compute_n_AND_h();
//			this.set_n(nh[0]);
//			this.set_h(nh[1].intValue());
//			return true;
//		}
//		return false;
//	}


	private void setSeed(String seed)
	{
//		this.seed = Strings.hashIt(seed); //MAYBE IT WILL WORK IF SEED HAS 160 BIT LENGTH
		this.seed = seed;
	}
}

