package my_Crypto;

import java.security.spec.ECPoint;
import java.util.TreeMap;
import Curve.Curve;

public class Decoding extends CodeDecode
{
	private String unidentified_Char;
	
	/**
	 * Instantiates an object to take care of decoding operations
	 * 
	 * @param c The curve to use
	 * @param K The symmetric key to crypt points
	 * @param chars2use A string of characters allowed for the encoding
	 */
	public Decoding(Curve c, ECPoint K, String chars2use)
	{
		super(c, K, chars2use);
		this.set_Unidentified_Char("*");
	}
	

	/**
	 * Decodes a message into a string
	 * 
	 * @param Message The string of curve points to decode
	 * @return A String representing the original message
	 */
	public String DecodeMessage(String encodedMessage)
	{
		String original = "";
		TreeMap<String, Character> decodingTable = this.decodingTable();
		
		int breakIndex = encodedMessage.indexOf(this.get_breakpoint());
		int breakLength = this.get_breakpoint().length();
		String codedPoint = "";
		while(encodedMessage.length() > breakLength && breakIndex > 0) //Consider putting breakLength instead of 0
		{
			codedPoint = encodedMessage.substring(0, breakIndex);
			encodedMessage = encodedMessage.substring(breakIndex+breakLength, encodedMessage.length());
			
			if(decodingTable.containsKey(codedPoint) == true)
			{
				original += decodingTable.get(codedPoint);
			}
			else
			{
				original += this.get_Unidentified_Char();
			}
			breakIndex = encodedMessage.indexOf(this.get_breakpoint());
		}
		return original;
	}
	

	/**
	 * Generates a table to look up for encoding points into chars
	 * 
	 * @param allowed A string of allowed chars
	 * @return TreeMap<String, Character> : Point on the curve -> Char
	 */
	private TreeMap<String, Character> decodingTable()
	{
		Curve c = this.get_c();
		ECPoint decodingPoint = c.PointAdd(this.get_K(), c.get_G()); // = K + G
		String key;
		char value;
	
		TreeMap<String, Character> table = new TreeMap<>();
		for(int i = 0; i < this.get_chars2use().length(); i++)
		{
			decodingPoint = c.PointAdd(c.get_G(), decodingPoint); // = G + encodingPoint = (i+2)*G
			key = c.comprHEX(decodingPoint);
			value = this.get_chars2use().charAt(i);
			
			table.put(key, value); //NOTE: If the key already exists, its new value will be saved over the old one 
		}
		return table;
	}


	public String get_Unidentified_Char()
	{
		return unidentified_Char;
	}


	private void set_Unidentified_Char(String unidentified_Char)
	{
		this.unidentified_Char = unidentified_Char;
	}
}
