package DigitalSign;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

/**
 * Notes taken from 
 * 1) NIST: FIPS 186-2 - Implementation details
 * 2) NIST: FIPS 186-4 - Better understanding of the parameters
 */
public class DSA extends funs
{
	private int p_len;
	private final int P_LEN_LOW = 1024;
	private final int P_LEN_MID = 2048;
	private final int P_LEN_HIGH = 3072;

	private int q_len;
	private final int Q_LEN_LOW = 160;
	private final int Q_LEN_MID = 224;
	private final int Q_LEN_HIGH = 256;
	
	/** Public parameter 2^(L-1) < p < 2^L */
	private BigInteger p;
	
	/** 
	 * Public parameter 2^(N-1) < p < 2^N 
	 * It must be a prime divisor of (p-1)
	 */
	private BigInteger q;
	
	/** Public parameter, generator of a multiplicative group in F(p)
	 * such as g^q = 1 mod(p)
	 */
	private BigInteger g;
	
	/** User's private key. The formula 0 < x < q must hold */
	private BigInteger x;

	/** User's public key. Corresponds to y = g^x mod(p) */
	private BigInteger y;

	/** 
	 * Private parameter. The formula 0 < x < q must hold 
	 * This must be regenerated at each signature
	 */
	private BigInteger k;
	
	
	/**
	 * Instantiates a new DSA protocol. The parameters to give represent the bit length of p and q <p>
	 * Possible input pairs: <p>
	 * 1024, 160 <p>
	 * 2048, 224 <p>
	 * 2048, 256 <p>
	 * 3072, 256 <p>
	 * In case of an unknown input parameter the pair (3072, 256) will be used as default
	 * 
	 * @param p_len Bit length of p
	 * @param q_len Bit length of q
	 */
	public DSA(int p_len, int q_len)
	{
		if(p_len == P_LEN_LOW)
		{
			this.setP_len(p_len);
			this.setQ_len(Q_LEN_LOW);
		}
		else if(p_len == P_LEN_MID)
		{
			this.setP_len(p_len);
			if(q_len == Q_LEN_MID)
			{
				this.setQ_len(q_len);
			}
			else
			{
				this.setQ_len(Q_LEN_HIGH);
			}
		}
		else
		{
			this.setP_len(P_LEN_HIGH);
			this.setQ_len(Q_LEN_HIGH);
		}
		
		//Sets the initial parameters for the sign
		this.generate_pq();
		System.out.println("1");
		this.generate_g();
		System.out.println("2");
		this.setX(this.generate_secret());
		System.out.println("3");
		this.generate_y();
		System.out.println("4");
		this.setK(this.generate_secret());
		System.out.println("5");
	}
	
	
	/**
	 * Generates a prime divisor of (p-1) having a bit length in range (2^(q_len-1), 2^q_len)
	 * Note: p and q are public parameters
	 * 
	 * Follows FIPS 186-2, 27 January 2000
	 */
	private void generate_pq()
	{
		int counter = 0;
		BigInteger q = null;
		BigInteger p = null;
		outerloop:
		do
		{
			String seed = "";
			do
			{
				seed = this.generateSEED(this.getQ_len());
				q = this.compute_q(seed);
			}
			while(q.isProbablePrime(2017) == false);
			
			counter = 0;
			int offset = 2;
			int n = Math.floorDiv((this.getP_len() - 1), 160);
			int b = Math.floorMod((this.getP_len() - 1), 160);

			while(counter < 4096)
			{
				BigInteger W = this.compute_W(seed, n, b, offset);
				BigInteger X = W.add(BigInteger.valueOf(2).pow(this.getP_len() - 1));
				
				BigInteger c = X.mod(q.multiply(BigInteger.valueOf(2)));
				p = X.subtract(c).add(BigInteger.ONE);

				while(p.compareTo(BigInteger.valueOf(2).pow(this.getP_len() - 1)) >= 0)
				{
					if(p.isProbablePrime(2017) == true)
					{
						break outerloop;
					}
					p = p.nextProbablePrime();
				}
//				if(p.compareTo(BigInteger.valueOf(2).pow(this.getP_len() - 1)) >= 0)
//				{
//					if(p.isProbablePrime(2017) == true)
//					{
//						break outerloop;
//					}
//				}
				counter++;
				offset += n + 1;
			}
		}
		while(counter >= 4096);
		
		this.setP(p);
		this.setQ(q);
	}

	
	/**
	 * Calculates the generator g of a multiplicative field F(p).
	 * The formula g^q = 1 mod(p) must hold to declare g a generator element of F(p)
	 * Note: g is a public parameter
	 * 
	 * FIPS 186-2
	 */
	private void generate_g()
	{
		BigInteger min = BigInteger.valueOf(2);
		BigInteger max = this.getP().subtract(BigInteger.ONE);
		BigInteger g = null;

		Random rnjesus = new Random();
		BigInteger exp = max.divide(this.getQ());
		BigInteger h = null;
		do
		{
			h = new BigInteger(max.bitLength(), rnjesus);
			while(h.compareTo(min) < 0 || h.compareTo(max) >= 0)
			{
				h = new BigInteger(max.bitLength(), rnjesus);
			}
			g = h.modPow(exp, this.getP());
		}
		while(g.compareTo(BigInteger.ONE) <= 0);
		
		this.setG(g);
	}
	
	
	/**
	 * Generates the parameters x and k accordingly to "0 < (x, k) < q"
	 * Note: x is the user's private key
	 * Note: k is a secret one-per-message parameter, hence it must be regenerated at each new sign
	 * 
	 * @return A secret/private parameter
	 */
	private BigInteger generate_secret()
	{
		int maxbit = this.getQ_len();
		Random rng = new Random();
		BigInteger secret = null;
		do
		{
			secret = new BigInteger(maxbit, rng);
		}
		while(secret.compareTo(this.getQ()) >= 0); //exit when 0 < secret < Q
		
		return secret;
	}

	
	/**
	 * Generates the parameter y as g^x mod(p)
	 * Note: y is the user's public key
	 */
	private void generate_y()
	{
		this.setY(this.getG().modPow(this.getX(), this.getP()));
	}
	
	
	/**
	 * Generates a bit string U = SHA-1[SEED] XOR SHA-1[(SEED+1) mod 2^g]
	 * from which q is calculated as q = U OR 2^159 OR 2^0
	 * 
	 * @param seed The random seed to use to generate a number q
	 * @return A number q
	 */
	private BigInteger compute_q(String seed)
	{
		int q_len = this.getQ_len();
		
		String part1 = this.hashIt(seed, q_len);
		String part2 = new BigInteger(seed, 2)
				.add(BigInteger.ONE)
				.mod(BigInteger.valueOf(2).pow(q_len))
				.toString(2);
		
		BitSet bs1 = this.string2bitset(part1, q_len);
		BitSet bs2 = this.string2bitset(part2, q_len);
		bs1.xor(bs2);
		bs1.set(0); //Ensures the result is 160 bit long
		bs1.set(q_len-1); //Enables ODD results only
		
		return new BigInteger(this.bitset2string(bs1, q_len), 2);
	}
	
	
	/**
	 * Generates a W number. It will range in 0 <= W < 2^(L-1)
	 * 
	 * @param seed The initial seed
	 * @param n from L - 1 = n*160 + b
	 * @param b from L - 1 = n*160 + b
	 * @param offset Initial offset for the computation
	 * @return A number W
	 */
	private BigInteger compute_W(String seed, int n, int b, int offset) 
	{
		BigInteger SEED_base10 = new BigInteger(seed, 2);
		BigInteger W = BigInteger.ZERO;
		for(int k = 0; k <= n; k++)
		{
			SEED_base10 = SEED_base10.add(BigInteger.valueOf(offset)).add(BigInteger.valueOf(k));
			SEED_base10 = SEED_base10.mod(BigInteger.valueOf(2).pow(seed.length()));
			BigInteger V_k = new BigInteger(this.hashIt(SEED_base10.toString(2), this.getQ_len()), 2);
			
			if(k == n)
			{
				V_k = V_k.mod(BigInteger.valueOf(2).pow(b));
			}

			int exponent = k*this.getQ_len();
			BigInteger factor = BigInteger.valueOf(2).pow(exponent);
			W = W.add(V_k.multiply(factor));
		}
		return W;
	}
	
	
	/**
	 * Generates the signature of the message
	 * 
	 * @param message The message to sign
	 */
	public BigInteger[] signature(String message)
	{
		BigInteger hash = new BigInteger(this.hashIt(message, this.getQ_len()), 2);
		BigInteger k_inv = this.getK().modInverse(this.getQ());
		
		BigInteger r = this.getG().modPow(this.getK(), this.getP()).mod(this.getQ());
		if(r.compareTo(BigInteger.ZERO) == 0)
		{
			this.setK(this.generate_secret());
			this.signature(message);
		}
		
		BigInteger add = this.getX().multiply(r);
		hash = hash.add(add);
		BigInteger s = k_inv.multiply(hash).mod(this.getQ());
		if(s.compareTo(BigInteger.ZERO) == 0)
		{
			this.setK(this.generate_secret());
			this.signature(message);
		}
		
		return new BigInteger[] {r, s};
	}
	
	
	/**
	 * Verifies whether the signature received is valid or not
	 * 
	 * @param hash The message's hash
	 * @param sign The generated sign
	 * @return Whether the sign is valid
	 */
	public boolean sign_isValid(String hash, BigInteger[] sign)
	{
		BigInteger r = sign[0], s = sign[1], hash_base10 = new BigInteger(hash, 2);
		if(r.compareTo(BigInteger.ZERO) == 0 || s.compareTo(BigInteger.ZERO) == 0 )
		{
			return false;
		}
		
		BigInteger w = s.modInverse(this.getQ());
		BigInteger exp1 = (hash_base10.multiply(w)).mod(this.getQ());
		BigInteger exp2 = r.multiply(w).mod(this.getQ());

		BigInteger factor1 = this.getG().modPow(exp1, this.getP());
		BigInteger factor2 = this.getY().modPow(exp2, this.getP());
		BigInteger v = (factor1.multiply(factor2)).mod(this.getP()).mod(this.getQ());
		
		return v.compareTo(r) == 0;
	}
		
	
	/**
	 * @return the p_len
	 */
	public int getP_len()
	{
		return p_len;
	}

	/**
	 * @param p_len the p_len to set
	 */
	private void setP_len(int p_len)
	{
		this.p_len = p_len;
	}

	/**
	 * @return the q_len
	 */
	public int getQ_len()
	{
		return q_len;
	}

	/**
	 * @param q_len the q_len to set
	 */
	private void setQ_len(int q_len)
	{
		this.q_len = q_len;
	}



	/**
	 * @return the p
	 */
	public BigInteger getP()
	{
		return p;
	}



	/**
	 * @param p the p to set
	 */
	private void setP(BigInteger p)
	{
		this.p = p;
	}



	/**
	 * @return the q
	 */
	public BigInteger getQ()
	{
		return q;
	}



	/**
	 * @param q the q to set
	 */
	private void setQ(BigInteger q)
	{
		this.q = q;
	}



	/**
	 * @return the g
	 */
	public BigInteger getG()
	{
		return g;
	}



	/**
	 * @param g the g to set
	 */
	private void setG(BigInteger g)
	{
		this.g = g;
	}



	/**
	 * @return the x
	 */
	public BigInteger getX()
	{
		return x;
	}



	/**
	 * @param x the x to set
	 */
	private void setX(BigInteger x)
	{
		this.x = x;
	}



	/**
	 * @return the y
	 */
	public BigInteger getY()
	{
		return y;
	}



	/**
	 * @param y the y to set
	 */
	private void setY(BigInteger y)
	{
		this.y = y;
	}



	/**
	 * @return the k
	 */
	public BigInteger getK()
	{
		return k;
	}



	/**
	 * @param k the k to set
	 */
	private void setK(BigInteger k)
	{
		this.k = k;
	}
	

	/**
	 * This function is just a test of the other functions.
	 */
	public void prova()
	{
		System.out.println("p: 2^(L-1) < p < 2^L");
		System.out.println("L = "+this.getP_len());
		System.out.println("2^(L-1) = "+BigInteger.valueOf(2).pow(this.getP_len() - 1));
		System.out.println("p       = "+this.getP());
		System.out.println("2^L     = "+BigInteger.valueOf(2).pow(this.getP_len()));
		System.out.println();
		
		System.out.println("q: 2^(N-1) < q < 2^N");
		System.out.println("N = "+this.getQ_len());
		System.out.println("2^(N-1) = "+BigInteger.valueOf(2).pow(this.getQ_len() - 1));
		System.out.println("q       = "+this.getQ());
		System.out.println("2^N     = "+BigInteger.valueOf(2).pow(this.getQ_len()));
		System.out.println();
		
		System.out.println("g: g^q = 1 mod(p) -> "+this.getG().modPow(this.getQ(), this.getP()));
		System.out.println();
		
		System.out.println("x: 0 < x < q -> x = "+this.getX());
		System.out.println();

		System.out.println("y = g^x mod(p) -> "+this.getG().modPow(this.getX(), this.getP()));
		System.out.println("y = "+this.getY());
		System.out.println();

		System.out.println("k: 0 < k < q -> k = "+this.getK());
		System.out.println();
		

		System.out.println("Digital Signature: ");
		int length = 150;
		String m = this.generateSEED(length);
		String h = this.hashIt(m, this.getQ_len());

		System.out.println("Message = "+m);
		System.out.println("With length = "+length);
		System.out.println("Hash(m) = "+h);
		System.out.println("Hash(m)10 = "+new BigInteger(h, 2));
		
		System.out.println();
		BigInteger[] sign = this.signature(m);
		System.out.println("Sign: r = "+sign[0]);
		System.out.println("Sign: s = "+sign[1]);
		System.out.println();
		
		System.out.println("Sign verify: "+this.sign_isValid(h, sign));
	}
}
