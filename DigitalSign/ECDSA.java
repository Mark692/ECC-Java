package DigitalSign;

import java.math.BigInteger;
import java.security.spec.ECPoint;
import java.util.Random;

import Crypto.ECDH;
import Curve.Brainpool;
import Curve.Curve;
import Utils.Strings;

/**
 * Based on:
 * 1) Certicom - The Elliptic Curve Digital Signature Algorithm (ECDSA)
 * 2) X9.62, September 20, 1998
 */
public class ECDSA extends funs
{

	private Curve c;
	
	public ECDSA(Curve c)
	{
		this.setC(c);
	}
	
	
	/**
	 * Signs a message with this user's private key using SHA1 as default hash function
	 * 
	 * @param privateKey Sender's private key
	 * @param message The message to hash and sign
	 * @return A triple representing the sign and the hash of the message
	 */
	public BigInteger[] sign(BigInteger privateKey, String message)
	{
		return this.sign(privateKey, message, "SHA-1");
	}
	
	
	/**
	 * Signs a message with this user's private key
	 * 
	 * @param privateKey Sender's private key
	 * @param message The message to hash and sign
	 * @param hashFunction The hash function to use for the digital sign
	 * @return A triple representing the sign and the hash of the message
	 */
	public BigInteger[] sign(BigInteger privateKey, String message, String hashFunction)
	{
		BigInteger n = this.getC().get_n();
		BigInteger k = this.generateSecret_k();
		BigInteger x_kG = this.x_coord(k);
		BigInteger r = x_kG.mod(n);
		if(r.compareTo(BigInteger.ZERO) == 0)
		{
			this.sign(privateKey, message);
		}
		BigInteger k_inv = k.modInverse(n);
		String hash = Strings.hashIt(message, hashFunction);
		BigInteger e = new BigInteger(hash, 2);
		
		BigInteger sum2mult = e.add(privateKey.multiply(r));
		BigInteger s = k_inv.multiply(sum2mult).mod(n);
		if(s.compareTo(BigInteger.ZERO) == 0)
		{
			this.sign(privateKey, message);
		}
		return new BigInteger[] {r, s, e};
	}
	

	/**
	 * Checks whether the sign is valid or not
	 * 
	 * @param sign The generated sign
	 * @param sender_Public This is the ECPoint representing the sender's public key
	 * @return Whether the sign is valid or not
	 */
	public boolean isValid(BigInteger[] sign, ECPoint sender_Public)
	{
		BigInteger r = sign[0];
		BigInteger s = sign[1];
		BigInteger n = this.getC().get_n();
		if((r.compareTo(BigInteger.ONE) < 0 || r.compareTo(n) >= 0)
				|| (s.compareTo(BigInteger.ONE) < 0 || s.compareTo(n) >= 0))
		{
			return false;
		}
		BigInteger e = sign[2];
		BigInteger w = s.modInverse(n);
		BigInteger u1 = e.multiply(w).mod(n);
		BigInteger u2 = r.multiply(w).mod(n);
		ECPoint P1 = this.getC().PointMultiplication(u1);
		ECPoint P2 = this.getC().PointMultiplication(u2, sender_Public);
		ECPoint X = this.getC().PointAdd(P1, P2);
		
		if(X.equals(ECPoint.POINT_INFINITY))
		{
			return false;
		}
		BigInteger v = X.getAffineX().mod(n);

		return v.compareTo(r) == 0;
	}

	
	/**
	 * Generates a random number k.
	 * 
	 * @return A random number k which holds 1 <= k < n
	 */
	public BigInteger generateSecret_k()
	{
		Random entropy = new Random();
		BigInteger n = this.getC().get_n();

		int bitsNumber; 
		BigInteger k;
		do
		{
			bitsNumber = entropy.nextInt(n.bitLength() + 1); 
			k = new BigInteger(bitsNumber, entropy);
		}
		while(k.compareTo(n) >= 0 || k.compareTo(BigInteger.ONE) < 0); //... and will stop when 1 <= k < n-1
		
		return k;
	}
	
	
	/**
	 * Calculates a point of the curve k*G
	 * 
	 * @param d The random number k
	 * @return The x coordinate of k*G
	 */
	public BigInteger x_coord(BigInteger k)
	{
		return this.getC().PointMultiplication(k).getAffineX();
	}


	/**
	 * @return the c
	 */
	public Curve getC()
	{
		return c;
	}


	/**
	 * @param c the c to set
	 */
	public void setC(Curve c)
	{
		this.c = c;
	}
	
	
	/**
	 * Performs a test for the ECDSA signature
	 */
	public static void test(String hashFun)
	{
		Brainpool c = new Brainpool(128);
		ECDSA ec = new ECDSA(c);
		ECDH dh = new ECDH(c);
		BigInteger d = dh.generatePrivateKey_d();
		ECPoint Q = dh.generatePublicKey_P(d);
		
		String message = "Questa è la mia firma";
		BigInteger[] sign = ec.sign(d, message, hashFun);
		boolean isValid = ec.isValid(sign, Q);

		System.out.println("Test della firma digitale con ECDSA");
		System.out.println("");
		System.out.println("Messaggio da firmare: "+message);
		System.out.println("Parametri di Alice:");
		System.out.println("Chiave privata d = "+d);
		System.out.println("Chiave pubblica Q = ("+Q.getAffineX()+", "+Q.getAffineY()+")");
		System.out.println();
		System.out.println("Parametri della firma");
		System.out.println("r = "+sign[0]);
		System.out.println("s = "+sign[1]);
		System.out.println("hash del messaggio = "+sign[2]);
		System.out.println();
		System.out.println("La firma ricevuta è valida? -> "+isValid);
	}
}
