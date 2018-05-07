package my_Crypto;

import java.security.spec.*;
import java.util.TreeMap;
import Curve.Curve;

public class Encoding extends CodeDecode
{
	private TreeMap<Character, String> table;
	
	/**
	 * Enables to encode a message into a string
	 * 
	 * @param c The curve to use
	 * @param K The symmetric key to crypt points
	 * @param chars2use A string of characters allowed for the encoding
	 */
	public Encoding(Curve c, ECPoint K, String chars2use)
	{
		super(c, K, chars2use);
		this.set_table(new TreeMap<>());
	}
	

	/**
	 * Codes a message into a string
	 * 
	 * @param Message The string to code into curve points
	 * @return A String representing a series of curve points
	 */
	public String CodeMessage(String Message)
	{
		String encoded_Message = "";
		TreeMap<Character, String> encodingTable = this.encodingTable();
		for(int i = 0; i < Message.length(); i++)
		{
			if(encodingTable.containsKey(Message.charAt(i))) //If this character has been coded in the table
			{
				encoded_Message += encodingTable.get(Message.charAt(i)) + this.get_breakpoint();
			}
			else
			{
				encoded_Message += this.get_c().comprHEX(ECPoint.POINT_INFINITY) + this.get_breakpoint();
			}
		}
		return encoded_Message;
	}
	

	/**
	 * Updates the encoding table with pairs (Char -> Point on the curve)
	 * 
	 * @return the table of encoded characters into points of the curve
	 */
	private TreeMap<Character, String> encodingTable()
	{
		if(this.get_table().isEmpty() == true)
		{
			Curve c = this.get_c();
			ECPoint encodingPoint = c.PointAdd(this.get_K(), c.get_G()); // = K + G
			String value;
			char key;
			
			TreeMap<Character, String> table = this.get_table();
			for(int i = 0; i < this.get_chars2use().length(); i++)
			{
				encodingPoint = c.PointAdd(c.get_G(), encodingPoint); // = G + encodingPoint = (i+2)*G + K
				key = this.get_chars2use().charAt(i);
				value = c.comprHEX(encodingPoint);
				
				table.put(key, value); //NOTE: If the key already exists, its new value will be saved over the old one
			}
			this.set_table(table);
		}
		return this.get_table();
	}


	public TreeMap<Character, String> get_table()
	{
		return table;
	}


	public void set_table(TreeMap<Character, String> table)
	{
		this.table = table;
	}
	
	
	/**
	 * Clears the table from any K-V pairs 
	 */
	public void reset_table()
	{
		this.table.clear();
	}
}
