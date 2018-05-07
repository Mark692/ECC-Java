package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import RivShaAdle.RSA;
import RivShaAdle.RSACrypt;

public class input_RSA_Crypt extends Menu_RSA
{

	private String text = "no text :)";
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("Here you can encrypt a text via RSA. The output you'll receive should be the same as your input\r\n"
				+ "Moreover you'll find RSA paramters used for the operations \r\n"
				+ "and a final check which is the decryption of the encrypted text");

		System.out.println("If you wish to continue press any key. Otherwise just enter 'b' to come back at the previous menu");
		boolean decision = this.want2continue();
		if(decision == true)
		{
			this.cryDEcry();
		}
	}
	
	
	private void cryDEcry()
	{
		System.out.println("First input a text you want to encrypt using RSA. Default text is: "+text);
		String t = this.manageInput();
		if(t != "") { text = t; }
		
		System.out.println("Now insert the RSA modulo length. Common values range between [1024, 3072].\r\n"
				+ "Your input MAY BE CHANGED if smaller than text's bit length");
		String mbl = this.manageInput();
		int modulo_bitlen = 0;
		if(mbl != "")
		{
			try
			{
				modulo_bitlen = Integer.valueOf(mbl);
			}
			catch(NumberFormatException e) {}
		}
		
		RSACrypt rsa_crypt = new RSACrypt(text, modulo_bitlen);
		BigInteger encrypted = rsa_crypt.crypt_message();
		String decrypted = rsa_crypt.decrypt_message(encrypted);
		
		rsa_crypt.get_rsa();
		System.out.println("Your input text: "+text);
		System.out.println("Its encryption: "+encrypted);
		System.out.println("Final check. The decryption of the latter string is: "+decrypted);
		System.out.println("Your input text and the decrypted one are the same? : "+rsa_crypt.checkCorrectness(decrypted));
		System.out.println();
		System.out.println("______________________________________________________________________");
	}
	

	/**
	 * Handles user input, exceptions and redirects
	 */
	private boolean want2continue()
	{
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { return false; }
		
		if(inputString.compareToIgnoreCase(Back) == 0)
		{
			return false;
		}
		return true;
	}
	

	/**
	 * Handles user input, exceptions and redirects
	 */
	private String manageInput()
	{
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { return ""; }
		
		if(inputString.compareToIgnoreCase("") != 0)
		{
			return inputString;
		}
		return "";
	}
	
	
	
	private void printParams(RSA rsa)
	{
		System.out.println("p = "+rsa.get_p());
		System.out.println("q = "+rsa.get_q());
		System.out.println("n = p*q = "+rsa.get_n());
		System.out.println("Theta(n) = "+rsa.getEulerTheta());
		System.out.println();
		System.out.println("e = K+ = "+rsa.get_e());
		System.out.println("d = K- = "+rsa.get_d());
		System.out.println();
	}
}