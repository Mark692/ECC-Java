package RivShaAdle;

import java.math.BigInteger;
import Utils.Strings;

public class RSACrypt
{
	
	/** 
	 * This is the text to encrypt in base 10 notation.
	 * It is fundamental to check whether the RSA modulo is greater than the message's length
	 * otherwise RSA is not suitable for the purpose.
	 */
	private BigInteger message_base10;
	private RSA rsa;
	
	
	/**
	 * Instantiates an object which will create an RSA object. 
	 * The modulo's bit length will be adjusted to fit the message if the modulo_bitlen input is not big enough
	 * 
	 * @param m The message to encrypt
	 * @param modulo_bitlen RSA modulo's length
	 */
	public RSACrypt(String m, int modulo_bitlen)
	{
		this.message_base10 = new BigInteger(Strings.string2base(m, 10));
		int min_bitlen = this.message_base10.bitLength() + 1; //Message's bit length plus 1
		if(modulo_bitlen < min_bitlen)
		{
			modulo_bitlen = min_bitlen;
		}
		this.rsa = new RSA(modulo_bitlen);
	}
	
	
	/**
	 * Encrypts the message: c = m^e mod(n)
	 * 
	 * @return The decimal encrypted hash of the message
	 */
	public BigInteger crypt_message()
	{
		return message_base10.modPow(rsa.get_e(), rsa.get_n());
	}
	
	
	/**
	 * Decrypts a text into the original message: m = c^d mod(n)
	 * 
	 * @param crypted The encrypted text
	 * @return The decimal hash of the original message
	 */
	public String decrypt_message(BigInteger crypted)
	{
		String original10 = crypted.modPow(rsa.get_d(), rsa.get_n()).toString();
		return Strings.base2string(original10, 10);
	}
	
	
	/**
	 * Checks whether the input text (the decrypted text) is coherent with the initial message
	 * 
	 * @param textToCheck The decrypted text
	 * @return Whether the input text is equal to the initial message
	 */
	public boolean checkCorrectness(String textToCheck)
	{
		BigInteger check10 = new BigInteger(Strings.string2base(textToCheck, 10));
		if(message_base10.equals(check10))
		{
			return true;
		}
		return false;
	}
	
	
	public void get_rsa()
	{
		System.out.println("p = " + this.rsa.get_p());
		System.out.println("q = " + this.rsa.get_q());
		System.out.println("n = " + this.rsa.get_n());
		System.out.println("e = " + this.rsa.get_e());
		System.out.println("d = " + this.rsa.get_d());
	}
}
