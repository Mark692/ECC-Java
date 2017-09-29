package Crypto;

import java.math.BigInteger;
import java.security.spec.ECPoint;

import Curve.Curve;
import Utils.Strings;

public final class ECIES
{

	/**
	 * Encrypts a text
	 * 
	 * @param Message Data to be encrypted
	 * @param Alice_privateEPH This user's ephemeral private key
	 * @param Alice_publicEPH This user's ephemeral public key
	 * @param Bob_public Recipient's public key (aka Bob's)
	 * @param c The curve in use for the computations
	 * @param SharedData Optional. Data shared by the two parties
	 * @return The encrypted text 
	 */
	public static String encryptES(String Message, BigInteger Alice_privateEPH, ECPoint Alice_publicEPH, ECPoint Bob_public, Curve c, String SharedData)
	{
		if(Message == "")
		{
			return "";
		}
		String A_pubEPH_x = Strings.comprHEX(Alice_publicEPH);
		A_pubEPH_x = Strings.baseSwap(A_pubEPH_x, 16, 2);
		
		ECPoint secret = c.PointMultiplication(Alice_privateEPH, Bob_public);
		String secret_x = Strings.comprHEX(secret);
		secret_x = Strings.string2binary(secret_x);

		Message = Strings.string2binary(Message);
		int mess_len = Message.length();
		SharedData = Strings.string2binary(SharedData);
		String EncKey = ECIES.keyDerivation(secret_x, mess_len, SharedData);

		String encryptedMessage = Strings.xor(Message, EncKey);

		return A_pubEPH_x + encryptedMessage;
	}
	
	
	/**
	 * Decrypts a text into its original form
	 * 
	 * @param encrypted The encrypted text
	 * @param Bob_private This user's private key
	 * @param Alice_publicEPH Sender's public ephemeral key
	 * @param c The curve in use for the computations
	 * @param SharedData Optional. Data shared by the two parties
	 * @return The decrypted text
	 */
	public static String decryptES(String encrypted, BigInteger Bob_private, ECPoint Alice_publicEPH, Curve c, String SharedData) 
	{
		if(encrypted == "" || encrypted == null)
		{
			return "";
		}
		String A_pubEPH_x = Strings.comprHEX(Alice_publicEPH);
		A_pubEPH_x = Strings.baseSwap(A_pubEPH_x, 16, 2);
		int x_len = A_pubEPH_x.length();
		
		String ApEx_received = encrypted.substring(0, x_len);
		if(ApEx_received.equalsIgnoreCase(A_pubEPH_x) == false)
		{
			return null;
		}
		
		int encrypted_len = encrypted.length() - x_len;
		
		ECPoint secret = c.PointMultiplication(Bob_private, Alice_publicEPH);
		String secret_x = Strings.comprHEX(secret);
		secret_x = Strings.string2binary(secret_x);
		
		SharedData = Strings.string2binary(SharedData);
		String EncKey = ECIES.keyDerivation(secret_x, encrypted_len, SharedData);
		String decrypted = Strings.xor(encrypted.substring(x_len), EncKey);
		
		return Strings.base2string(decrypted, 2);
	}
	

	/**
	 * Encrypts a text
	 * 
	 * @param Message Data to be encrypted
	 * @param A_privEPH This user's ephemeral private key
	 * @param A_pubEPH This user's ephemeral public key
	 * @param B_public Recipient's public key (aka Bob's)
	 * @param c The curve in use for the computations
	 * @param MAC_len MacTag's length
	 * @param SharedData1 Optional. Data shared by the two parties
	 * @param SharedData2 Optional. Data shared by the two parties
	 * @return The encrypted text 
	 */
	public static String encryptAES(String Message, BigInteger A_privEPH, ECPoint A_pubEPH, ECPoint B_public, Curve c, int MAC_len, String SharedData1, String SharedData2) 
	{
		if(Message == "")
		{
			return "";
		}
		Message = Strings.string2binary(Message);
		int mess_len = Message.length();
		String A_pubEPH_x = Strings.comprHEX(A_pubEPH);
		A_pubEPH_x = Strings.baseSwap(A_pubEPH_x, 16, 2);
		
		ECPoint secret = c.PointMultiplication(A_privEPH, B_public);
		String secret_x = Strings.comprHEX(secret);
		secret_x = Strings.string2binary(secret_x);
		
		SharedData1 = Strings.string2binary(SharedData1);
		String KeyData = ECIES.keyDerivation(secret_x, mess_len + MAC_len, SharedData1);
		String EncKey = KeyData.substring(0, mess_len);
		String MacKey = KeyData.substring(mess_len);
		String MaskedEncData = Strings.xor(Message, EncKey);
		SharedData2 = Strings.string2binary(SharedData2);
		String MacData = MaskedEncData + SharedData2;
		String MacTag = ECIES.MessageAuthCode_TAG(MacData, MacKey);

		return A_pubEPH_x + MaskedEncData + MacTag;
	}
	
	
	/**
	 * Decrypts a text into its original form
	 * 
	 * @param encrypted The encrypted text
	 * @param Bob_private This user's private key
	 * @param A_pubEPH This user's public ephemeral key
	 * @param c The curve in use for the computations
	 * @param MAC_len MacTag's length
	 * @param SharedData1 Optional. Data shared by the two parties
	 * @param SharedData2 Optional. Data shared by the two parties
	 * @return The decrypted text
	 */
	public static String decryptAES(String encrypted, BigInteger Bob_private, ECPoint A_pubEPH, Curve c, int MAC_len,String SharedData1, String SharedData2) 
	{
		if(encrypted == "")
		{
			return "";
		}
		String A_pubEPH_x = Strings.comprHEX(A_pubEPH);		
		A_pubEPH_x = Strings.baseSwap(A_pubEPH_x, 16, 2);

		String ApEx_received = encrypted.substring(0, A_pubEPH_x.length());
		if(ApEx_received.equalsIgnoreCase(A_pubEPH_x) == false)
		{
			return null;
		}
		
		int pub_x_len = A_pubEPH_x.length();
		String MaskedEncData = encrypted.substring(pub_x_len, (encrypted.length() - 160)); //160 = TAG length
		int MaskedEncData_len = MaskedEncData.length();
		
		ECPoint secret = c.PointMultiplication(Bob_private, A_pubEPH);
		String secret_x = Strings.comprHEX(secret);
		secret_x = Strings.string2binary(secret_x);
		
		SharedData1 = Strings.string2binary(SharedData1);
		String KeyData = ECIES.keyDerivation(secret_x, MaskedEncData_len + MAC_len, SharedData1);
		String EncKey = KeyData.substring(0, MaskedEncData_len);
		String MacKey = KeyData.substring(MaskedEncData_len);
		String EncData = Strings.xor(MaskedEncData, EncKey);
		SharedData2 = Strings.string2binary(SharedData2);
		String checkTag = ECIES.MessageAuthCode_TAG(MaskedEncData + SharedData2, MacKey);
		String receivedTag = encrypted.substring((encrypted.length() - 160)); //160 = TAG length

		if(receivedTag.compareTo(checkTag) == 0)
		{
			return Strings.base2string(EncData, 2);
		}
		return null;
	}
	
	
	/**
	 * According to ANSI X9.63 this function is "used to derive keying data from a shared secret bit string".
	 * Here the bit string "SharedData" is optional.
	 * 
	 * @param Secret_K A bit string representing the secret key shared by the two parties.
	 * @param keydatalen Length of the output string key
	 * @return A bit string key to be used to crypt and decrypt data
	 */
	public static String keyDerivation(String Secret_K, int keydatalen) 
	{
		return ECIES.keyDerivation(Secret_K, keydatalen, "");
	}
	
	
	/**
	 * According to ANSI X9.63 this function is "used to derive keying data from a shared secret bit string".
	 *  
	 * @param Secret_K A bit string representing the secret key shared by the two parties.
	 * @param keydatalen Length of the output string key. It must be less than 160*2^32
	 * @param entropy A bit string representing some data already shared between the two parties
	 * @return A bit string key to be used to crypt and decrypt data
	 */
	public static String keyDerivation(String Secret_K, long keydatalen, String entropy)
	{
		String counter;
		
		long max = (long) (Math.pow(2, 32) * 160);
		if(keydatalen > max)
		{
			keydatalen = max;
		}
		
		entropy = Strings.string2binary(entropy);
		String keyData = "";
		int i = 0;
		while(keyData.length() < keydatalen)
		{
			i++;
			counter = Strings.counterPad32(i);
			keyData += Strings.hashIt(Secret_K+counter+entropy);
		}
		return keyData.substring(0, (int) keydatalen);


		//ORIGINAL USELESS CODE
//		long quitLoop = (long) Math.ceil(keydatalen/160.0); //160.0 is due to correctly round up the result
//		for(int i = 1; i < quitLoop; i++)
//		{
//			counter = Strings.counterPad(i);
//			keyData += Strings.hashIt(Secret_K+counter+entropy); 
//		}
//		
//		//Last iteration
//		counter = Strings.counterPad(quitLoop); 
//		String last2add = Strings.hashIt(Secret_K+counter+entropy);
//		
//		if(keydatalen % 160 == 0)
//		{
//			keyData += last2add;
//		}
//		else
//		{   
//			int end = (int) ((keydatalen) - (160 * (Math.floor(keydatalen/160.0))));
//			System.out.println("end = "+end);
//			keyData += last2add.substring(0, end);
//		}
//		
//		return keyData;
	}
	
	
	/**
	 * Generates a tag for the Message Authentication Code
	 * 
	 * @param data The bit string to be MACed
	 * @param key The string's key
	 * @return The 160bit long tag of the string given
	 */
	public static String MessageAuthCode_TAG(String data, String key)
	{
		String paddedKey = Strings.leftPad(key, 512);
		
		String pad1 = Strings.hex_to512bits("5C");
		String pad2 = Strings.hex_to512bits("36");

		String keypad1 = Strings.xor(paddedKey, pad1);
		String keypad2 = Strings.xor(paddedKey, pad2);

		String hash = Strings.hashIt(keypad1 + data);
		return Strings.hashIt(keypad2 + hash); //The TAG of the MAC
	}
}