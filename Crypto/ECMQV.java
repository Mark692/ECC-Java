package Crypto;

import java.math.BigInteger;
import Crypto.ECIES;

import java.security.spec.ECPoint;
import java.util.Random;

import Curve.Curve;
import Utils.Strings;

/**
 * This class represents the ECDH protocol with the Law, Menezes, Qu, Solinas and Vanstone variation.
 * In the MQV protocol both parties are assumed to have long-term static public/private key pairs.
 * 
 * Based on the book "Advances in Elliptic Curve Cryptography" by Ian F. Blake, Gadiel Seroussi, Nigel P. Smart
 */
public class ECMQV
{
	private Curve c;
	
	
	/**
	 * Sets the curve for key generation functions
	 * 
	 * @param c The curve chosen for according a key
	 */
	public ECMQV(Curve c)
	{
		this.set_c(c);
	}

	
	/**
	 * Generates the private and the ephemeral private key for an user.
	 * This has to be used for both parties in the communication
	 * 
	 * @return The pair {private key, private ephemeral key}
	 */
	public BigInteger[] generatePrivateKeys_d()
	{
		BigInteger d = this.generatePrivateKey_d();
		BigInteger d_ephemeral = this.generatePrivateKey_d();
		return new BigInteger[] {d, d_ephemeral};
	}
	
	
	/**
	 * Generates the public and the ephemeral public key for an user.
	 * These keys will have to be shared within the two parties
	 * 
	 * @param privateKeys The public and the ephemeral public key of this user
	 * @return The pair {public key, public ephemeral key}
	 */
	public ECPoint[] generatePublicKeys_P(BigInteger[] privateKeys)
	{
		ECPoint P = this.generatePublicKey_P(privateKeys[0]);
		ECPoint P_ephemeral = this.generatePublicKey_P(privateKeys[1]);
		return new ECPoint[] {P, P_ephemeral};
	}
	
	
	/**
	 * Computes the secret symmetric key K for an user. The same function has to be used by the other user 
	 * by swapping the keys used. Bob will have to set his privates, publics and Alice publics keys in the algorithm
	 * 
	 * @param thisUser_privates This user's private key pair. Private and ephemeral private keys
	 * @param thisUser_publics This user's public key pair. Public and ephemeral public keys
	 * @param otherUser_publics The other user's public key pair. Public and ephemeral public keys
	 * @return A symmetric key K to be used for encoding and decoding functions
	 */
	public ECPoint generateSecret_K(BigInteger[] thisUser_privates, ECPoint[] thisUser_publics, ECPoint[] otherUser_publics)
	{
		int n = this.get_c().get_p().bitLength() / 2;
		BigInteger modulo = BigInteger.valueOf(2).pow(n);
		BigInteger pub_eph_xA = thisUser_publics[1].getAffineX();
		BigInteger pub_eph_xB = otherUser_publics[1].getAffineX();
		BigInteger u = (pub_eph_xA.mod(modulo)).add(modulo);
		BigInteger v = (pub_eph_xB.mod(modulo)).add(modulo);

		BigInteger priv_eph_A = thisUser_privates[1];
		BigInteger priv_A = thisUser_privates[0];
		BigInteger s = priv_eph_A.add(priv_A.multiply(u)).mod(this.get_c().get_n());
		
		ECPoint pub_B = otherUser_publics[0];
		ECPoint K = this.get_c().PointMultiplication(v, pub_B);
		K = this.get_c().PointAdd(otherUser_publics[1], K);
		K = this.get_c().PointMultiplication(s, K);

		return K;
	}
	
	
	/**
	 * This enables a party to be offline and only his public key will be used. 
	 * His ephemeral public key will not exists if he/she is offline.
	 * 
	 * @param thisUser_privates This user's private key pair. Private and ephemeral private keys
	 * @param thisUser_publics This user's public key pair. Public and ephemeral public keys
	 * @param otherUser_public The other user's public key. Only the public key can be used
	 * @return A symmetric key K to be used for encoding and decoding functions
	 */
	public ECPoint generateSecret_K_offline(BigInteger[] thisUser_privates, ECPoint[] thisUser_publics, ECPoint otherUser_public)
	{
		ECPoint[] otherUser_publics = new ECPoint[] {otherUser_public, otherUser_public};
		return this.generateSecret_K(thisUser_privates, thisUser_publics, otherUser_publics);
	}

	
	
	/**
	 * Generates a random Private key.
	 * bitsNumber is the number of bits to be used in order to generate an adequate BigInteger.
	 * The private key d is then computed as a random BigInteger whose bit length ranges from 1 to n's.
	 * In case d's bit length is the same as n's we may have d > n hence we repeat the computation
	 * 
	 * @return The user's private key
	 */
	private BigInteger generatePrivateKey_d()
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
	private ECPoint generatePublicKey_P(BigInteger d)
	{
		return this.get_c().PointMultiplication(d);
	}
	

	

	/**
	 * Encrypts a text
	 * 
	 * @param Message Data to be encrypted
	 * @param A_privates This user's ephemeral private keys
	 * @param A_publics This user's ephemeral public keys
	 * @param B_public Recipient's public key (aka Bob's)
	 * @param SharedData Optional. Data shared by the two parties
	 * @return The encrypted text 
	 */
	public String encrypt(String Message, BigInteger[] A_privates, ECPoint[] A_publics, ECPoint B_public, String SharedData)
	{
		String crypted = ECIES.encryptES(Message, A_privates[1], A_publics[1], B_public, this.get_c(), SharedData);
		return Strings.baseSwap(crypted, 2, 16);
	}
	

	/**
	 * Encrypts a text
	 * 
	 * @param Message The hexadecimal notation message to be encrypted
	 * @param A_privates This user's ephemeral private keys
	 * @param A_publics This user's ephemeral public keys
	 * @param B_public Recipient's public key (aka Bob's)
	 * @return The encrypted text 
	 */
	public String encrypt(String Message, BigInteger[] A_privates, ECPoint[] A_publics, ECPoint B_public)
	{
		String crypted = ECIES.encryptES(Message, A_privates[1], A_publics[1], B_public, this.get_c(), "");
		return Strings.baseSwap(crypted, 2, 16);
	}
	
	
	/**
	 * Decrypts a text into its original form
	 * 
	 * @param encrypted The encrypted text
	 * @param Bob_privates This user's private key
	 * @param Alice_publics Sender's public ephemeral key
	 * @param SharedData Optional. Data shared by the two parties
	 * @return The decrypted text
	 */
	public String decrypt(String encrypted, BigInteger[] Bob_privates, ECPoint[] Alice_publics, String SharedData)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptES(encrypted, Bob_privates[0], Alice_publics[1], this.get_c(), SharedData);
	}
	
	
	/**
	 * Decrypts a text into its original form
	 * 
	 * @param encrypted The encrypted text
	 * @param Bob_privates This user's private key
	 * @param Alice_publics Sender's public ephemeral key
	 * @return The decrypted text
	 */
	public String decrypt(String encrypted, BigInteger[] Bob_privates, ECPoint[] Alice_publics)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptES(encrypted, Bob_privates[0], Alice_publics[1], this.get_c(), "");
	}
	
	
	/**
	 * Encrypts a message in a more secure way than the standard encryption
	 * 
	 * @param Message The message to encrypt
	 * @param A_privates This user's private keys
	 * @param A_publics This user's public keys
	 * @param B_public The recipient's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @param SharedData1 Optional. Data shared by the two parties
	 * @param SharedData2 Optional. Data shared by the two parties
	 * @return The encrypted message
	 */
	public String encryptAugmented(String Message, BigInteger[] A_privates, ECPoint[] A_publics, ECPoint B_public, int MAC_len, String SharedData1, String SharedData2)
	{
		String crypted = ECIES.encryptAES(Message, A_privates[1], A_publics[1], B_public, this.get_c(), MAC_len, SharedData1, SharedData2);
		return Strings.baseSwap(crypted, 2, 16);
	}
	
	
	/**
	 * Encrypts a message in a more secure way than the standard encryption
	 * 
	 * @param Message The message to encrypt
	 * @param A_privates This user's private keys
	 * @param A_publics This user's public keys
	 * @param B_public The recipient's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @return The encrypted message
	 */
	public String encryptAugmented(String Message, BigInteger[] A_privates, ECPoint[] A_publics, ECPoint B_public, int MAC_len)
	{
		String crypted = ECIES.encryptAES(Message, A_privates[1], A_publics[1], B_public, this.get_c(), MAC_len, "", "");
		return Strings.baseSwap(crypted, 2, 16);
		
	}
	
	
	/**
	 * Decrypts a text into its original message
	 * 
	 * @param encrypted The encrypted message
	 * @param Bob_privates This user's private keys
	 * @param A_pubEPH The sender's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @param SharedData1 Optional. Data shared by the two parties
	 * @param SharedData2 Optional. Data shared by the two parties
	 * @return The original message
	 */
	public String decryptAugmented(String encrypted, BigInteger[] Bob_privates, ECPoint[] A_publics, int MAC_len,String SharedData1, String SharedData2)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptAES(encrypted, Bob_privates[0], A_publics[1], this.get_c(), MAC_len, SharedData1, SharedData2);
	}
	
	
	/**
	 * Decrypts a text into its original message
	 * 
	 * @param encrypted The encrypted message
	 * @param Bob_privates This user's private keys
	 * @param A_pubEPH The sender's public key
	 * @param MAC_len The length of the MAC tag. It adds a check to the encryption
	 * @return The original message
	 */
	public String decryptAugmented(String encrypted, BigInteger[] Bob_privates, ECPoint A_pubEPH, int MAC_len)
	{
		encrypted = Strings.baseSwap(encrypted, 16, 2);
		return ECIES.decryptAES(encrypted, Bob_privates[0], A_pubEPH, this.get_c(), MAC_len, "", "");
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
