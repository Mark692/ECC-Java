package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECPoint;
import java.util.Random;

public class Strings
{
	/**
	 * Converts a string into its binary representation
	 * 
	 * @param s The string to convert
	 * @return The binary representation of the parameter given 
	 */
	public static String string2binary(String s)
	{
		return Strings.string2base(s, 2);
	}
	
	
	/**
	 * Converts a string into its "base" notation. 
	 * 
	 * @param s The string to convert
	 * @param base The base to be used for the conversion
	 * @return The string converted in "base" notation
	 */
	public static String string2base(String s, int base)
	{
		if(s == "") { return ""; }
		return new BigInteger(s.getBytes()).toString(base);
	}
	
	
	/**
	 * Converts a string from "base" notation into text
	 * 
	 * @param s The string to convert
	 * @param base The string's actual base notation
	 * @return The original string
	 */
	public static String base2string(String s, int base)
	{
		return new String(new BigInteger(s, base).toByteArray());
	}

	
	/**
	 * Swaps the string's base to another
	 * 
	 * @param s The string to convert into another base
	 * @param inputBase The actual base of the string
	 * @param outputBase The final base
	 * @return A string converted in outputBase notation
	 */
	public static String baseSwap(String s, int inputBase, int outputBase)
	{
		if(s == "") { return ""; }
		return new BigInteger(s, inputBase).toString(outputBase);
	}
	

	/**
	 * Converts a point into its hexadecimal notation with coordinate compression
	 * 
	 * @param P The point whose coordinates has to be compressed
	 * @return The point's hexadecimal compressed representation
	 */
	public static String comprHEX(ECPoint P)
	{
		if(P.equals(ECPoint.POINT_INFINITY))
		{
			return "00";
		}
		
		BigInteger y = P.getAffineY();
		if(y.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0) // if(y % 2 == 0)
		{
			String even = new String(P.getAffineX().toByteArray());
			String evenBig2str = new BigInteger(even.getBytes()).toString(16);
			return "02" + evenBig2str;
		}
		String odd = new String(P.getAffineX().toByteArray());
		String oddBig2str = new BigInteger(odd.getBytes()).toString(16);
		return "03" + oddBig2str;
	}
	
	
	/**
	 * Updates the counter and pads it to 32 bits.
	 * 
	 * @param i The counter to pad
	 * @return The given number padded to 32 bits and in BigEndian notation
	 */
	public static String counterPad32(long i)
	{
		return Strings.counterPad(i, 32);
	}
	
	
	/**
	 * Updates the counter and pads it to 32 bits.
	 * 
	 * @param i The counter to pad
	 * @return The given number padded to 32 bits and in BigEndian notation
	 */
	public static String counterPad(long i, int padUpTo)
	{
		String binaryString = Long.toBinaryString(i);
		return String.format("%0" + (padUpTo - binaryString.length()) + "d", 0) + binaryString;
	}
	
	
	
	/**
	 * Hashes a string with SHA1 algorithm
	 * 
	 * @param k The string to hash
	 * @return A 160 bit length bit-string of the hash of k
	 */
	public static String hashIt(String k)
	{
		return Strings.hashIt(k, "SHA-1").substring(0, 160); //The sub string up to 160 chars ensures correct results for ECIES
    }

	
	/**
	 * Hashes a string with the given hash algorithm.
	 * Allowed hash functions are: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
	 * If the requested hash function does not exist, SHA1 will be used instead
	 * 
	 * @param k The string to hash
	 * @return A bit-string of the hash of k
	 */
	public static String hashIt(String k, String hashFunction)
	{
        MessageDigest m_digest = null;
		try
		{
			m_digest = MessageDigest.getInstance(hashFunction);
		}
		catch (NoSuchAlgorithmException e)
		{
			try { m_digest = MessageDigest.getInstance("SHA-1"); }
			catch (NoSuchAlgorithmException e1) { e1.printStackTrace(); } //Will not happen since SHA1 is a correct name for a known hash
		}
        byte[] result = m_digest.digest(k.getBytes());
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < result.length; i++) 
        {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return Strings.string2binary(sb.toString());
    }
	
	
	/**
	 * Pads a string on the right up to a specified number of bits
	 * 
	 * @param s The key for the Message Authentication Code
	 * @param padUpTo The number of bits requested for the output
	 * @return The string padded to right with 0s
	 */
	public static String rightPad(String s, int padUpTo)
	{
		return s + String.format("%0" + (padUpTo - s.length()) + "d", 0);
	}
	

	/**
	 * Pads a string on the left up to a specified number of bits
	 * 
	 * @param s The key for the Message Authentication Code
	 * @param padUpTo The number of bits requested for the output
	 * @return The string padded to left with 0s
	 */
	public static String leftPad(String s, int padUpTo)
	{
		return String.format("%0" + (padUpTo - s.length()) + "d", 0) + s;
	}
	
	
	/**
	 * Generates a string of 512 bits from a hexadecimal value. Pads left is necessary.
	 * 
	 * @param hex_val The hexadecimal number to convert
	 * @return A string of 512 bits representing the hex value
	 */
	public static String hex_to512bits(String hex_val)
	{
		String binary_val = Strings.baseSwap(hex_val, 16, 2);
		binary_val = Strings.leftPad(binary_val, 8);
		return String.format("%0" + 64 + "d", 0).replace("0", binary_val);
	}
	
	
	/**
	 * Performs the bitwise exclusive or between two bit strings
	 * 
	 * @param one The first string
	 * @param two The second string
	 * @return The XOR string of one^two strings
	 */
	public static String xor(String one, String two)
	{
		StringBuilder sb = new StringBuilder();

	    for (int i = 0; i < one.length(); i++) 
	    {
	        sb.append(charOf(boolval(one.charAt(i)) ^ boolval(two.charAt(i))));
	    }
	    return sb.toString();
	}
	
	
	private static boolean boolval(char c) 
	{
	    return c == '1'; //if c=1 -> return true, else return false
	}

	
	private static char charOf(boolean boo) 
	{
	    return (boo) ? '1' : '0';
	}
	
	
	/**
	 * Generates a random 160 bit string
	 * 
	 * @return A bit string of 160 bits
	 */
	public static String seed()
	{
		String s = "";
		Random r = new Random();
		do
		{
			s = s + new BigInteger(16, r).toString(2);
		}
		while(s.length() < 160);
		
		return s.substring(0, 160);
	}
}
