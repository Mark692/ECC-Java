package RivShaAdle;

import java.math.BigInteger;

import Utils.Strings;

public class RSADigitSign
{

	/** 
	 * This is the hash of the message
	 * It is fundamental to check whether the RSA modulo is greater than the hash length
	 * otherwise RSA is not suitable for the purpose.
	 */
	private BigInteger hash;
	private RSA rsa;
	
	public RSADigitSign(String m, int modulo_bitlen)
	{
		this(m, modulo_bitlen, "SHA-1");
	}

	
	/**
	 * Hashes a message into the given hash function
	 * It is fundamental to check whether the RSA modulo is greater than the hash length
	 * otherwise RSA is not suitable for the purpose.
	 * 
	 * @param m The message to (hash and) sign
	 * @param modulo_bitlen RSA's modulo bit length. 
	 * @param hashFunction The hash function to use to hash the message. 
	 * 		  			   Allowed hash functions are: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512. 
	 * 					   If the requested hash function does not exist, SHA1 will be used instead
	 */
	public RSADigitSign(String m, int modulo_bitlen, String hashFunction)
	{
		String messageHash = Strings.hashIt(m, hashFunction);
		this.hash = new BigInteger(messageHash, 2); //Converts it from base 2 to base 10
		int min_bitlen = this.hash.bitLength() + 1; //Message's bit length plus 1
		if(modulo_bitlen < min_bitlen)
		{
			modulo_bitlen = min_bitlen;
		}
		this.rsa = new RSA(modulo_bitlen);
	}
	
	
	/**
	 * Signs (Encrypts) the message: c = m^d mod(n)
	 * 
	 * @return The decimal encrypted hash of the message
	 */
	public BigInteger sign_message()
	{
		return hash.modPow(rsa.get_d(), rsa.get_n());
	}
	
	
	/**
	 * Decrypts a text into the original message: m = c^e mod(n)
	 * 
	 * @param crypted The encrypted text
	 * @return The decimal hash of the original message
	 */
	public String decrypt_message(BigInteger crypted)
	{
		return crypted.modPow(rsa.get_e(), rsa.get_n()).toString();
	}
	

	/**
	 * Checks whether the signedMessage is valid or not
	 * 
	 * @param signedMessage The signed hash
	 * @return Whether the sign is the correct or not. 
	 */
	public boolean checkCorrectness(BigInteger signedMessage)
	{
		return this.checkCorrectness(hash, signedMessage);
	}
	
	
	/**
	 * Checks whether the signedMessage corresponds to the given hash
	 * 
	 * @param hash Message hash received to check
	 * @param signedMessage The signed hash
	 * @return Whether the hash is the correct decryption of the sign or not. 
	 */
	public boolean checkCorrectness(BigInteger hash, BigInteger signedMessage)
	{
		String decrypted = this.decrypt_message(signedMessage);
		if(decrypted.compareTo(hash.toString()) == 0)
		{
			return true;
		}
		return false;
	}


	public BigInteger get_Hash()
	{
		return this.hash;
	}
	

	public RSA get_rsa()
	{
		return this.rsa;
	}
}