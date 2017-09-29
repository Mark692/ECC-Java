package Crypto;

import java.math.BigInteger;
import Crypto.ECIES;

import java.security.spec.ECPoint;
import java.util.Random;
import Curve.Curve;
import Utils.Strings;

/** Enables to chose a symmetric key between two users */
public class ECDH
{
	private Curve c;
	
	
	/**
	 * Sets the curve for key generation functions
	 * 
	 * @param c The curve chosen for according a key
	 */
	public ECDH(Curve c)
	{
		this.set_c(c);
	}
	
	
	/**
	 * Generates a random Private key.
	 * bitsNumber is the number of bits to be used in order to generate an adequate BigInteger.
	 * The private key d is then computed as a random BigInteger whose bit length ranges from 1 to n's.
	 * In case d's bit length is the same as n's we may have d > n hence we repeat the computation
	 * 
	 * @return The user's private key
	 */
	public BigInteger generatePrivateKey_d()
	{
		Random entropy = new Random();
		BigInteger n = this.get_c().get_n();

		int bitsNumber; 
		BigInteger d;
		
		do
		{
			//This will generate a random number between [1, n.bitLength()]
			bitsNumber = 1 + entropy.nextInt(n.bitLength() + 1); 
			
			//This will generate a random BigInteger with a random number of bytes...
			d = new BigInteger(bitsNumber, entropy);
		}
		while(d.compareTo(n) >= 0 || d.compareTo(BigInteger.ZERO) == 0); //... and will stop when 1 <= d < n-1
		return d;
	}
	
	
	/**
	 * Generates a public key based on the private key d
	 * 
	 * @param d The private key
	 * @return The public key as P = d*G
	 */
	public ECPoint generatePublicKey_P(BigInteger d)
	{
		return this.get_c().PointMultiplication(d);
	}
	
	
	/**
	 * Generates a symmetric key K which will be used to encrypt data
	 * 
	 * @param d_A Alice's private key
	 * @param P_Bob Bob's public key
	 * @return The symmetric key K
	 */
	public ECPoint generateSecret_K(BigInteger d_A, ECPoint P_Bob)
	{
		return this.get_c().PointMultiplication(d_A, P_Bob);
	}
	

	/**
	 * Encrypts a text
	 * 
	 * @param Message Data to be encrypted
	 * @param A_privEPH This user's ephemeral private key
	 * @param A_pubEPH This user's ephemeral public key
	 * @param B_public Recipient's public key (aka Bob's)
	 * @param SharedData Optional. Data shared by the two parties
	 * @return The encrypted text 
	 */
	public String encrypt(String Message, BigInteger A_privEPH, ECPoint A_pubEPH, ECPoint B_public, String SharedData)
	{
		String crypted = ECIES.encryptES(Message, A_privEPH, A_pubEPH, B_public, this.get_c(), SharedData);
		return Strings.baseSwap(crypted, 2, 16);
	}
	

	/**
	 * Encrypts a text
	 * 
	 * @param Message The hexadecimal notation message to be encrypted
	 * @param A_privEPH This user's ephemeral private key
	 * @param A_pubEPH This user's ephemeral public key
	 * @param B_public Recipient's public key (aka Bob's)
	 * @return The encrypted text 
	 */
	public String encrypt(String Message, BigInteger A_privEPH, ECPoint A_pubEPH, ECPoint B_public)
	{
		String crypted = ECIES.encryptES(Message, A_privEPH, A_pubEPH, B_public, this.get_c(), "");
		return Strings.baseSwap(crypted, 2, 16);
	}
	
	
	/**
	 * Decrypts a text into its original form
	 * 
	 * @param encrypted The encrypted text
	 * @param Bob_private This user's private key
	 * @param Alice_publicEPH Sender's public ephemeral key
	 * @param SharedData Optional. Data shared by the two parties
	 * @return The decrypted text
	 */
	public String decrypt(String encrypted, BigInteger Bob_private, ECPoint Alice_publicEPH, String SharedData)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptES(encrypted, Bob_private, Alice_publicEPH, this.get_c(), SharedData);
	}
	
	
	/**
	 * Decrypts a text into its original form
	 * 
	 * @param encrypted The encrypted text
	 * @param Bob_private This user's private key
	 * @param Alice_publicEPH Sender's public ephemeral key
	 * @return The decrypted text
	 */
	public String decrypt(String encrypted, BigInteger Bob_private, ECPoint Alice_publicEPH)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptES(encrypted, Bob_private, Alice_publicEPH, this.get_c(), "");
	}
	
	
	/**
	 * Encrypts a message in a more secure way than the standard encryption
	 * 
	 * @param Message The message to encrypt
	 * @param A_privEPH This user's private key
	 * @param A_pubEPH This user's public key
	 * @param B_public The recipient's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @param SharedData1 Optional. Data shared by the two parties
	 * @param SharedData2 Optional. Data shared by the two parties
	 * @return The encrypted message
	 */
	public String encryptAugmented(String Message, BigInteger A_privEPH, ECPoint A_pubEPH, ECPoint B_public, int MAC_len, String SharedData1, String SharedData2)
	{
		String crypted = ECIES.encryptAES(Message, A_privEPH, A_pubEPH, B_public, this.get_c(), MAC_len, SharedData1, SharedData2);
		return Strings.baseSwap(crypted, 2, 16);
	}
	
	
	/**
	 * Encrypts a message in a more secure way than the standard encryption
	 * 
	 * @param Message The message to encrypt
	 * @param A_privEPH This user's private key
	 * @param A_pubEPH This user's public key
	 * @param B_public The recipient's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @return The encrypted message
	 */
	public String encryptAugmented(String Message, BigInteger A_privEPH, ECPoint A_pubEPH, ECPoint B_public, int MAC_len)
	{
		String crypted = ECIES.encryptAES(Message, A_privEPH, A_pubEPH, B_public, this.get_c(), MAC_len, "", "");
		return Strings.baseSwap(crypted, 2, 16);
		
	}
	
	
	/**
	 * Decrypts a text into its original message
	 * 
	 * @param encrypted The encrypted message
	 * @param Bob_private This user's private key
	 * @param A_pubEPH The sender's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @param SharedData1 Optional. Data shared by the two parties
	 * @param SharedData2 Optional. Data shared by the two parties
	 * @return The original message
	 */
	public String decryptAugmented(String encrypted, BigInteger Bob_private, ECPoint A_pubEPH, int MAC_len,String SharedData1, String SharedData2)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptAES(encrypted, Bob_private, A_pubEPH, this.get_c(), MAC_len, SharedData1, SharedData2);
	}
	
	
	/**
	 * Decrypts a text into its original message
	 * 
	 * @param encrypted The encrypted message
	 * @param Bob_private This user's private key
	 * @param A_pubEPH The sender's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @return The original message
	 */
	public String decryptAugmented(String encrypted, BigInteger Bob_private, ECPoint A_pubEPH, int MAC_len)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptAES(encrypted, Bob_private, A_pubEPH, this.get_c(), MAC_len, "", "");
	}
	
	
	public Curve get_c()
	{
		return c;
	}

	private void set_c(Curve c)
	{
		this.c = c;
	}

}
