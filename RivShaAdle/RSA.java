package RivShaAdle;

import java.math.BigInteger;
import java.util.Random;

public class RSA
{
	/** A prime number */
	private BigInteger p;

	/** A prime number */
	private BigInteger q;

	/** Modulo n = p*q */
	private BigInteger n;
	
	/** Theta(n) = Theta(p*q) = (p-1) * (q-1) */
	private BigInteger EulerTheta;
	
	/** 
	 * Factor coprime to EulerTheta 
	 * This is the public key 
	 */
	private BigInteger e;
	
	/**
	 * Private key. The correct choice for this number is any value for which
	 * the equation e*d = 1 mod(EulerTheta(n)) holds.
	 * Therefore d is calculated as d = e^-1 mod(Theta) where e^-1 stands for his multiplicative inverse.
	 * This operation is always possible since "e" is coprime to Theta
	 */
	private BigInteger d;

	
	/**
	 * Generates an RSA object. The modulo bit length represents the security of RSA. Common values range between [1024, 3072]
	 * 
	 * @param modulo_bitlen The length in bit of RSA's modulo
	 */
	public RSA(int modulo_bitlen)
	{
		this.generate_pqn(modulo_bitlen);
		this.EulerTheta();
		this.publicKey_e();
		this.privateKey_d();
	}
	

	/**
	 * Base constructor. Just for lazies and checks.
	 */
	public RSA()
	{
		this.set_p(new BigInteger("6884941678295950232960006864033983012237150636188958928413671409846195433561"));
		this.set_q(new BigInteger("1188918920538774370295070470388128991493457537225749804004299261743826604979659"));
		this.set_n(new BigInteger("8185637428132038708586746003903871163997323618657419154072524071452742153418968354895533446420780132050306309999478755003917162878483529785500229090935699"));
		this.setEulerTheta(new BigInteger("8185637428132038708586746003903871163997323618657419154072524071452742153417772551033316376100252101573054147024973060316055224115550816852346556290522480"));
		this.set_e(new BigInteger("44826824947649"));
		
		this.privateKey_d();
	}
	
	
	/**
	 * Used for benchmarking tests
	 * 
	 * @param dodici It does anything. Just needed a different constructor
	 * @param tredici It does anything. Just needed a different constructor
	 */
	public RSA(int dodici, int tredici) {}

	
	/**
	 * Used for benchmarking tests
	 * 
	 * @param nBitLen Modulo's bit length
	 */
	public void bench(int nBitLen)
	{
		this.generate_pqn(nBitLen);
		this.EulerTheta();
		this.publicKey_e();
		this.privateKey_d();
	}
	
	
	/**
	 * Generates two random numbers p and q. These numbers will have different bit length 
	 * in order to provide a harder factorisation. Then n = p*q will be computed. If n's
	 * length is exactly the same given then p, q, r will be returned.
	 * 
	 * @param n_bitlen The length in bit of the modulo n
	 * @return The parameters p, q, and n
	 */
	private void generate_pqn(int n_bitlen)
	{
		/* p and q must be prime and with a similar, but different, length in bit
		 * Their product, n = p*q, must have the declared n_bitsize length in bit
		 */

		Random entropy = new Random();
		BigInteger p;

		int percent10 = n_bitlen / 10; //10% of n length
		int percent45 = (n_bitlen * 45 ) / 100; //45% of n length. This is better than 40%
		int percent50 = Math.floorDiv(n_bitlen, 2); //50% of n length
		
		BigInteger q;
		BigInteger n;
		do
		{
			do
			{
				int probable_bitlength = entropy.nextInt(percent10) + percent45; // range [45%, 50% - 1] of n's bit length
//				p = new BigInteger(probable_bitlength, entropy).nextProbablePrime();
				
				//I prefer this way because the latter function returns the first prime number higher than "this". 
				//For high bit length numbers will exist a wider range of non-primes, thus it may lead to predictably results
				p = new BigInteger(probable_bitlength, 2017, entropy); //Analogous. This will generate a prime number with % = 1 - 2^(-2017) uncertainty
			}
			while(p.bitLength() == percent50);
			
			int q_len = n_bitlen - p.bitLength(); //This means that "p.bitlen + q.bitlen = n.bitlen"
			q = BigInteger.probablePrime(q_len, entropy);
			
			n = p.multiply(q);
		}
		while(n.bitLength() != n_bitlen);

		this.set_p(p);
		this.set_q(q);
		this.set_n(n);
	}
	
	
	/**
	 * Computes the Euler's T theta function of n. 
	 * T(n) = T(p * q) = T(p) * T(q) = (p - 1)(q - 1)
	 */
	private void EulerTheta()
	{
		BigInteger p_1 = this.get_p().subtract(BigInteger.ONE);
		BigInteger q_1 = this.get_q().subtract(BigInteger.ONE);
		this.setEulerTheta(p_1.multiply(q_1));
	}

	
	/**
	 * Computes the public key "e".
	 * This factor has to be in the range [1, n-1] and coprime to Theta(n)
	 */
	private void publicKey_e()
	{
		Random entropy = new Random();
		BigInteger e, gcd;
		do
		{
			// bit length in range [1, log_2(n)]
			int probable_length = entropy.nextInt(this.get_n().bitLength()) + 1; 
			e = new BigInteger(probable_length, entropy);
			
//			e = e.nextProbablePrime(); //This may be helpful in finding gcd=1 but it will lead to less results because it would consider prime numbers only!
			gcd = e.gcd(this.getEulerTheta());
		}
		while(e.compareTo(this.get_n()) >= 0 			// e >= n
				|| gcd.compareTo(BigInteger.ONE) != 0);	// e has common factors with Theta(n)
		
		this.set_e(e);
	}
	
	
	/**
	 * Computes the private key d
	 * e*d = 1 mod(Theta) -> d = 1/e mod(Theta) = e^(-1) mod(Theta)
	 */
	private void privateKey_d()
	{
		BigInteger theta = this.getEulerTheta();
		BigInteger d = this.get_e().modInverse(theta).mod(theta);
		
		this.set_d(d);
	}
	
	
	public BigInteger get_p()
	{
		return p;
	}


	private void set_p(BigInteger p)
	{
		this.p = p;
	}


	public BigInteger get_q()
	{
		return q;
	}


	private void set_q(BigInteger q)
	{
		this.q = q;
	}


	public BigInteger get_n()
	{
		return n;
	}


	private void set_n(BigInteger n)
	{
		this.n = n;
	}


	public BigInteger getEulerTheta()
	{
		return EulerTheta;
	}


	private void setEulerTheta(BigInteger eulerTheta)
	{
		this.EulerTheta = eulerTheta;
	}


	public BigInteger get_e()
	{
		return e;
	}


	private void set_e(BigInteger e)
	{
		this.e = e;
	}


	public BigInteger get_d()
	{
		return d;
	}


	private void set_d(BigInteger d)
	{
		this.d = d;
	}
}
