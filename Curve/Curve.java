package Curve;

import java.security.spec.*;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.math.BigInteger;

import Utils.Strings;

public abstract class Curve
{
	/** Author of this curve */
	protected String author;
	
	/** Curve's name */
	protected String name;
	
	/** The curve's construction parameters */
	protected BigInteger a, b;

	/** Field's cardinality */
	protected BigInteger p;

	/** Curve's generator's point */
	protected ECPoint G;

	/** G's order. This means that n*G = ECPoint.POINT_INFINITY */
	protected BigInteger n;

	/** Curve's cofactor */
	protected int h;
	
	/** Curve's security level (expressed in bit length) */
	protected int L; 
	
	/** Verifiably Random's seed. Used to generate VR curves */
	protected String seed;

	/** Used to divide points from each other during Encoding and Decoding functions */
	private final String breakpoint = "\r\n";
	
	/** Security levels offered by elliptic curves */
	public static final int SECURITY_LEVEL_56 = 56;
	public static final int SECURITY_LEVEL_64 = 64;
	public static final int SECURITY_LEVEL_80 = 80;
	public static final int SECURITY_LEVEL_96 = 96;
	public static final int SECURITY_LEVEL_112 = 112;
	public static final int SECURITY_LEVEL_128 = 128;
	public static final int SECURITY_LEVEL_160 = 160;
	public static final int SECURITY_LEVEL_192 = 192;
	public static final int SECURITY_LEVEL_256 = 256;

	private boolean isStrong; //It has strong cryptographic parameters

	
	/**
	 * Faster constructor for custom curves.
	 * This will not compute n and h with a way faster result.
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param a Curve's first parameter
	 * @param b Curve's second parameter
	 * @param p Field's cardinality
	 */
	public Curve(String author, String curveName, BigInteger a, BigInteger b, BigInteger p)
	{
		this.set_author(author);
		this.set_name(curveName);
		
		//Primality of p test and fix. MUST be the first check. 
		if(!this.isPrime_andPositive(p)) //If p is NOT a prime all the following checks WILL FAIL due to the mod_inverse() exception
		{
			this.set_p(this.nextClosePrime(p)); //Sets p as a close prime number to the given p
		}
		else
		{
			this.set_p(p);
		}
		
		//Smoothness test and fix
		if(!this.is_Smooth(a, b, p))
		{
			this.makeItSmooth(a, b, p);
		}
		else
		{
			this.set_a(a.mod(this.get_p()));
			this.set_b(b.mod(this.get_p()));
		}
		
		this.set_L(this.get_p().bitLength() / 2); //Half p bit length
	}
	
	
	/**
	 * Constructor for custom curves
	 * 
	 * @param author The author of this curve
	 * @param curveName The curve's name
	 * @param a Curve's first parameter
	 * @param b Curve's second parameter
	 * @param p Field's cardinality
	 * @param G Generator Point
	 * @param n Order of G. It means that n*G = ECPoint.POINT_INFINITY
	 * @param h Cofactor. It is h = #E / n, where #E is the curve's cardinality
	 * @param L Security level offered
	 */
	public Curve(String author, String curveName, BigInteger a, BigInteger b, BigInteger p, ECPoint G, BigInteger n, int h, int L)
	{
		this(author, curveName, a, b, p);

		//If the given point doesn't lie on the curve
		if(this.isOnTheCurve(G) == false)
		{
			this.computeNewGenerator();
		}
		else
		{
			BigInteger x = G.getAffineX().mod(this.get_p());
			BigInteger y = G.getAffineY().mod(this.get_p());
			this.set_G(new ECPoint(x, y)); //Avoids negative or OutOfBounds coordinates
		}

		this.set_n(n); //MANDATORY TO GET THINGS WORK FROM NOW ON

		//If n*G is not the Point at Infinity
		if(this.nG_isPointInfinity() == false)
		{
			BigInteger[] nh = this.compute_n_AND_h();
			this.set_n(nh[0]);
			this.set_h(nh[1].intValue());
		}
		else
		{
			this.set_h(h);
		}

		//If the security level is not suitable for the field cardinality chosen
		if(this.suitableSecurityLevel(L) == true)
		{
			this.set_L(L);
		}
		
		//Cryptographic tests
		if(this.h_isSmallEnough() == false
				|| this.isAnomalous() == true
				|| this.MOV_Resistant() == false)
		{
			this.set_isStrong(false);
		}
		else
		{
			this.set_isStrong(true);
		}
	}
	
	
	/**
	 * Common constructor for known curves
	 * No need to test them again
	 */
	public Curve()
	{
		this.set_isStrong(true);
	}


	public BigInteger get_a()
	{
		return a;
	}

	protected void set_a(BigInteger a)
	{
		this.a = a;
	}
	
	public BigInteger get_b()
	{
		return b;
	}

	protected void set_b(BigInteger b)
	{
		this.b = b;
	}

	public BigInteger get_p()
	{
		return p;
	}

	protected void set_p(BigInteger p)
	{
		this.p = p;
	}

	public ECPoint get_G()
	{
		return G;
	}

	protected void set_G(ECPoint g)
	{
		G = g;
	}

	public BigInteger get_n()
	{
		return n;
	}

	protected void set_n(BigInteger n)
	{
		this.n = n;
	}

	public int get_h()
	{
		return h;
	}

	protected void set_h(int h)
	{
		this.h = h;
	}

	public int get_L()
	{
		return L;
	}

	protected void set_L(int l)
	{
		this.L = l;
	}
	

	/**
	 * Retrieves the curve's author
	 * 
	 * @return The curve's author
	 */
	public String get_author()
	{
		return this.author;
	}
	
	
	protected void set_author(String au)
	{
		this.author = au;
	}
	
	
	/**
	 * Retrieves the curve's name
	 * @return The curve's name
	 */
	public String get_name()
	{
		return this.name;
	}
	
	
	protected void set_name(String n)
	{
		this.name = n;
	}


	public String getSeed()
	{
		return seed;
	}
	

	/**
	 * Performs scalar multiplication with the generator point G
	 * 
	 * @param k The scalar number multiplying G
	 * @return The curve point k*G
	 */
	public ECPoint PointMultiplication(BigInteger k)
	{
		return this.PointMultiplication(k, this.get_G());
	}
	
	
	/**
	 * Performs scalar multiplication
	 * 
	 * @param k The scalar number multiplying P
	 * @param P A point of the curve
	 * @return The curve point k*P
	 */
	public ECPoint PointMultiplication(BigInteger k, ECPoint P)
	{
		if(k.compareTo(BigInteger.ZERO) == 0 || k.compareTo(this.get_n()) == 0)
		{
			return ECPoint.POINT_INFINITY; //Returns the point at infinity when trying to do 0*P or n*P
		}

		String k_2 = k.toString(2); //Produces the binary notation of k
		ECPoint P1 = P;
		ECPoint P2 = this.PointDouble(P1);
		
		for(int i = 1; i < k_2.length(); i++) //Starts from the second element because the first is the Declaration of P1 and P2
		{
//			if(Character.getNumericValue(k_2.charAt(i)) == 1)
			if(k_2.charAt(i) == '1')
			{
				P1 = this.PointAdd(P1, P2);
				P2 = this.PointDouble(P2);
			}
			else
			{
				P2 = this.PointAdd(P1, P2);
				P1 = this.PointDouble(P1);
			}
		}
		return P1;
	}
	
	
	/**
	 * Sums two points of the curve 
	 * 
	 * @param A First point
	 * @param B Second point
	 * @return Their sum as EC Group Law "Point Addition" C = -(A+B)
	 */
	public ECPoint PointAdd(ECPoint A, ECPoint B)
	{
		BigInteger p = this.get_p();
		
		if(A.equals(ECPoint.POINT_INFINITY)) //If A = (inf, inf)
		{
			return B;
		}
		else if(B.equals(ECPoint.POINT_INFINITY)) //If B = (inf, inf)
		{
			return A; 
		}

		BigInteger x_A = A.getAffineX();
		BigInteger y_A = A.getAffineY();
		
		BigInteger x_B = B.getAffineX();
		BigInteger y_B = B.getAffineY();
		
		if(x_A.mod(p).compareTo(x_B.mod(p)) == 0) //Same x coordinate
		{
			if(y_A.mod(p).compareTo(y_B.negate().mod(p)) == 0) //Symmetric points A = - B
			{
				return ECPoint.POINT_INFINITY; //Point at Infinity
			}
			else if(y_A.mod(p).compareTo(y_B.mod(p)) == 0) //Same points A = B
			{
				return this.PointDouble(A); //Point Doubling
			}
			else //Three points on a vertical line
			{
				BigInteger x_C = (x_A.add(x_B)).negate().mod(p);
				BigInteger y_C = y_A.negate().mod(p);
				return new ECPoint(x_C, y_C);
			}
		}
		
		BigInteger denom = x_A.subtract(x_B);
		try
		{
			denom = denom.modInverse(p); //If this fails then "denom" and "p" are NOT COPRIME (like 2^-1 mod(8) would fail)
			//This operation will never fail IF p is PRIME.
		}
		catch(java.lang.ArithmeticException e)
		{
			return ECPoint.POINT_INFINITY;
		}
		BigInteger m = ((y_A.subtract(y_B)).multiply(denom)).mod(p);
		BigInteger x_C = ((m.pow(2)).subtract(x_A).subtract(x_B)).mod(p);
		BigInteger y_C = (((x_A.subtract(x_C)).multiply(m)).subtract(y_A)).mod(p);
		//BigInteger y_C = (((x_B.subtract(x_C)).multiply(m)).subtract(y_B)).mod(p); //Result is the same
		
		return new ECPoint(x_C, y_C);
	}


	/**
	 * Particular case of the PointAdd. It doubles a point of the curve
	 * 
	 * @param A The point to double
	 * @return 2A, the point doubled
	 */
	public ECPoint PointDouble(ECPoint A)
	{
		if(A.equals(ECPoint.POINT_INFINITY)) 
		{
			return ECPoint.POINT_INFINITY;
		}
		
		BigInteger x_A = A.getAffineX();
		BigInteger y_A = A.getAffineY();
		BigInteger p = this.get_p();
		
		if(y_A.compareTo(BigInteger.ZERO) == 0) //Doubling a point like (x_A, 0)
		{
			return ECPoint.POINT_INFINITY;
		}

		BigInteger denom = y_A.add(y_A);
		try
		{
			denom = denom.modInverse(p); //If this fails then "denom" and "p" are NOT COPRIME (like 2^-1 mod(8) would fail)
			//This operation will never fail IF p is PRIME.
		}
		catch(java.lang.ArithmeticException e)
		{
			return ECPoint.POINT_INFINITY;
		}
		
		BigInteger a = this.get_a();
		BigInteger num = ((x_A.pow(2)).multiply(BigInteger.valueOf(3))).add(a);
		BigInteger m = (num.multiply(denom)).mod(p);
		
		BigInteger x_C = ((m.pow(2)).subtract(x_A.add(x_A))).mod(p);
		BigInteger y_C = (((x_A.subtract(x_C)).multiply(m)).subtract(y_A)).mod(p);

		return new ECPoint(x_C, y_C);
	}

	
	/**
	 * Used to retrieve the breakpoint.
	 * 
	 * @return The breakpoint
	 */
	public String get_breakpoint()
	{
		return breakpoint;
	}
	
	
	//Checks for the curve\\
	

	/**
	 * Checks whether the curve is smooth
	 * 
	 * @param a The curve's first parameter
	 * @param b The curve's second parameter
	 * @param p The field's cardinality
	 * @return Whether the curve is smooth or not
	 */
	protected boolean is_Smooth(BigInteger a, BigInteger b, BigInteger p)
	{
		BigInteger a_clause = ((a.pow(3)).multiply(BigInteger.valueOf(4))).mod(p);
		BigInteger b_clause = ((b.pow(2)).multiply(BigInteger.valueOf(27))).mod(p);

		if(a_clause.compareTo(b_clause) != 0)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks whether the point given belongs to the curve
	 * 
	 * @param G The generator point
	 * @return Whether the point G belongs to the curve
	 */
	public boolean isOnTheCurve(ECPoint G)
	{
		if(G.equals(ECPoint.POINT_INFINITY))
		{
			return true;
		}
		BigInteger x = G.getAffineX();
		BigInteger y = G.getAffineY();
		BigInteger p = this.get_p();
		BigInteger a = this.get_a();
		BigInteger b = this.get_b();
		
		y = (y.pow(2)).mod(p); // = y^2
		x = (x.pow(3).add(a.multiply(x)).add(b)).mod(p); // = x^3 + ax + b

		if(y.compareTo(x) == 0)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks whether the order n is correct via n*G = Point at infinity
	 * 
	 * @return Whether n*G is the point at infinity or not
	 */
	protected boolean nG_isPointInfinity()
	{
		//This little trick avoids to instantly return the point at infinity when using the PointMultiplication
		if(this.PointMultiplication(this.get_n().add(BigInteger.ONE)).equals(this.get_G()))
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks whether the field cardinality p is enough for the security level L offered
	 * 
	 * @return Whether the field cardinality p is enough for the security level L offered or not
	 */
	public boolean suitableSecurityLevel(int L)
	{
		int binary_p = this.get_p().bitLength();

		boolean cond0 = L < SECURITY_LEVEL_80 && binary_p == 2*L; //Certicom curves
		boolean cond1 = L == SECURITY_LEVEL_80 && binary_p == 192;
		boolean cond2 = (L > SECURITY_LEVEL_80 && L < SECURITY_LEVEL_256) && binary_p == 2*L;
		boolean cond3 = L == SECURITY_LEVEL_256 && binary_p == 521;
		
		if(cond0 || cond1 || cond2 || cond3)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks whether the cofactor is small enough for the security level offered
	 * 
	 * @return Whether the cofactor is small enough for the security level offered or not
	 */
	public boolean h_isSmallEnough()
	{
		int L = this.get_L();
		int h = this.get_h();
		int L_byte = (int) Math.ceil(L/8);
		if(h <= Math.pow(2, L_byte))
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks whether the curve has cardinality #E equal to field cardinality p. 
	 * In this case the curve is said Anomalous and is not suitable for cryptographic purposes
	 * 
	 * @return Whether the curve has cardinality #E equal to field cardinality p or not
	 */
	public boolean isAnomalous()
	{
		BigInteger n = this.get_n();
		BigInteger h = BigInteger.valueOf(this.get_h());
		if((n.multiply(h)).compareTo(this.get_p()) == 0)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks whether the curve is resistant against MOV attacks.
	 * To be resistant, n must satisfy the equation p^i != 1 mod(n) for an i >= 20 (ANSI X9.92)
	 * The choice of i up to 100 is based on Certicom's SEC1 (21/05/2009, version 2.0) 
	 * 
	 * @return Whether the curve is resistant against MOV attacks.
	 */
	public boolean MOV_Resistant()
	{
		BigInteger p = this.get_p();
		BigInteger n = this.get_n();
		
		int i = 1;
		boolean test = true;
		BigInteger notONE;
		while((i < 100) && test == true)
		{
			notONE = (p.pow(i)).mod(n);
			if(notONE.compareTo(BigInteger.ONE) == 0)
			{
				test = false;
			}
			i++;
		}
		return test;
	}
	
	
	/**
	 * Computes the curve order n and cofactor h
	 * 
	 * @return The curve cardinality and G's order. If p is not prime, {0, 0} is returned
	 */
	protected BigInteger[] compute_n_AND_h()
	{
		BigInteger[] interval = this.Hasse_Interval();
		BigInteger x_G = this.get_G().getAffineX();
		BigInteger y_G = this.get_G().getAffineY();
		
		
		BigInteger iter = interval[0];
		ECPoint Q;
		BigInteger E_card = BigInteger.ZERO; //Curve's cardinality
		
		while(iter.compareTo(interval[1]) <= 0) //While inside the allowed values interval:
		{
			Q = this.PointMultiplication(iter);
			
			if(Q.equals(ECPoint.POINT_INFINITY))
			{
				E_card = iter;
				break;
			}
			else if(Q.getAffineX().compareTo(x_G) == 0) //Same x coordinates
				{
					if(Q.getAffineY().compareTo(y_G) == 0)  //Same y coordinates: Q = G
					{
						E_card = iter.subtract(BigInteger.ONE); //Since G+O = G the order will be i-1
						break;
					}
					else //Symmetric y coordinates: Q = -G
					{
						E_card = iter.add(BigInteger.ONE); //Since G+(-G) = O the order will be i+1
						break;
					}
				}
			else //Point not found, add 3 to the iter
			{
				iter = iter.add(BigInteger.valueOf(3));
			}
		}
		
		//Compute the curve's order n
		BigInteger n = E_card;
		loop:
		for(Map.Entry<BigInteger, BigInteger> f : this.factor_it(n).entrySet()) //Finds sub-factors of E_Card which grant R = point at infinity
		{
			if(n.compareTo(f.getKey()) == 0) //If n is Prime
			{
				break loop;
			}
			
			for(int counter = 0; f.getValue().compareTo(BigInteger.valueOf(counter)) > 0; counter++)
			{
				ECPoint R = this.PointMultiplication(n.divide(f.getKey()));
				if(R.equals(ECPoint.POINT_INFINITY)) //If R = point at infinity
				{
					n = n.divide(f.getKey());
				}
			}
		}
		BigInteger h = E_card.divide(n);
		return new BigInteger[] {n, h};
	}
	
	
	/**
	 * Checks whether the parameter given is prime
	 * 
	 * @return Whether the parameter is prime or not
	 */
	protected boolean isPrime_andPositive(BigInteger isPrime)
	{
		if(isPrime.signum() == 1) //If it's a positive number
		{
			return isPrime.isProbablePrime(2017);
		}
		return false;
	}
	
	
	/**
	 * Hasse's theorem says that the Elliptic Curve's order lies in a specific interval.
	 * This function computes the interval's bounds and returns them in a BigInteger array
	 * 
	 * @param p The field's characteristic
	 * @return An array with min and max bounds for EC's order
	 */
	private BigInteger[] Hasse_Interval()
	{
		BigInteger p = this.get_p();
		BigInteger two_squaroot = this.sqrt(p).multiply(BigInteger.valueOf(2)); // = 2*sqrt(p)
		p = p.add(BigInteger.ONE); // = p+1
		
		BigInteger min = p.subtract(two_squaroot);
		BigInteger max = p.add(two_squaroot);
		
		return new BigInteger[] {min, max};
	}
	
	
	/**
	 * Computes the square root of a Big Integer. To be used in the Hasse's theorem.
	 * THANKS: https://stackoverflow.com/questions/42204941/square-root-and-operators-for-biginteger-and-bigdecimal
	 * CAVEAT: Changed the last instruction in order to give a less rounded result. 
	 * 		   The Hasse algorithm would ignore some numbers otherwise
	 * 
	 * @param p The number to be square-rooted
	 * @return The n's square root
	 */
	private BigInteger sqrt(BigInteger p)
	{
		if(p.compareTo(BigInteger.ONE) == 0)
		{
			return BigInteger.ONE;
		}
		BigInteger root = BigInteger.ONE; //BigInt constant value of 1
		BigInteger b = p.shiftRight(5).add(BigInteger.valueOf(8));
		while (b.compareTo(root) >= 0) 
		{
			BigInteger mid = root.add(b).shiftRight(1);
			if (mid.multiply(mid).compareTo(p) > 0) 
			{
				b = mid.subtract(BigInteger.ONE);
			} 
			else 
			{
				root = mid.add(BigInteger.ONE);
			}
		}
		return root;
		//return a.subtract(BigInteger.ONE); //THIS GIVES A TOO MUCH ROUNDED RESULT.
		//Hasse's theorem would be compromised by this last command
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
			myMap.put(modulo, exp);
		}
		return n;
	}
	
	
	/**
	 * Converts a point of the curve into a hexadecimal String of compresses coordinates
	 * 
	 * @param P The point to convert
	 * @return The hexadecimal string representing the point given
	 */
	public String comprHEX(ECPoint P)
	{
		return Strings.comprHEX(P);
	}


	/**
	 * Shows whether the curve has cryptographically strong parameters or not
	 * 
	 * @return Whether the curve is strong in cryptographic terms
	 */
	public boolean isStrong()
	{
		return isStrong;
	}


	/**
	 * Sets whether the parameters (n, h, L) are cryptographically secure
	 * 
	 * @param isCryptographicallyStrong Whether the curve has valid parameters or not
	 */
	private void set_isStrong(boolean isCryptographicallyStrong)
	{
		this.isStrong = isCryptographicallyStrong;
	}
	
	
	//FIXES FOR THE CURVE\\
	
	
	/**
	 * Generates a random prime number of the same bit length of p
	 * 
	 * @param p The base number to find a close prime upon
	 * @return A close prime number to p
	 */
	protected BigInteger nextClosePrime(BigInteger p)
	{
		int p_len = p.bitLength();
		Random rnd = new Random();
		if(p_len >= 3) //Length of 5 and 7. The minimum prime number to obtain an Elliptic Curve is 5 (which has 3 bit length).
		{
			try
			{
				return BigInteger.probablePrime(p_len, rnd);
			}
			catch(ArithmeticException e) //Thrown when p.bitLength() < 2 or when it's too big
			{
				return BigInteger.valueOf(5); 
			}
		}
		return BigInteger.valueOf(5); 
	}
	
	
	/**
	 * Changes the parameters "a" and "b" given in order to have a smooth curve.
	 * Then sets the new values into the curve's parameters
	 * 
	 * @param a The curve's first parameter
	 * @param b The curve's second parameter
	 * @param p The field's cardinality
	 */
	protected void makeItSmooth(BigInteger a, BigInteger b, BigInteger p)
	{
		a = p.subtract(BigInteger.valueOf(3)); //According to Certicom "a = p-3 for efficiency reasons"
		while(this.is_Smooth(a, b, p) == false)
		{
			b = b.add(BigInteger.ONE);
		}
		this.set_a(a);
		this.set_b(b);
	}
	
	
	/**
	 * Computes a generator for the elliptic curve.
	 * 
	 * @return Whether the generator point has been found or not
	 */
	protected boolean computeNewGenerator()
	{
		BigInteger p = this.get_p();
		BigInteger a = this.get_a();
		BigInteger b = this.get_b();

		BigInteger RightHandSide = BigInteger.ZERO;

		BigInteger y = BigInteger.ZERO;
		while(y.compareTo(p.subtract(BigInteger.ONE)) <= 0)
		{
			BigInteger x = BigInteger.ZERO;
			while(x.compareTo(p.divide(BigInteger.valueOf(2))) < 0) //Half p because of the symmetry
			{
				RightHandSide = ((x.pow(3)).add(a.multiply(x))).add(b).mod(p); //x^3 + ax + b
				if(RightHandSide.compareTo(y.pow(2).mod(p)) == 0 
						&& (x.compareTo(BigInteger.ZERO) != 0 && y.compareTo(BigInteger.ZERO) != 0))
				{
					ECPoint G = new ECPoint(x, y);
					this.set_G(G);
					return true;
				}
				x = x.add(BigInteger.ONE);
			}
			y = y.add(BigInteger.ONE);
		}
		ECPoint G = ECPoint.POINT_INFINITY;
		this.set_G(G);
		return false;
	}
	
	
	/**
	 * Computes the symmetric of a given EC Point
	 * 
	 * @param P The point to symmetrise
	 * @return -P
	 */
	protected ECPoint negate_y(ECPoint P)
	{
		return new ECPoint(P.getAffineX(), P.getAffineY().negate().mod(get_p()));
	}
}
