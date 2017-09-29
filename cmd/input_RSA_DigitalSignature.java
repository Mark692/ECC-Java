package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import RivShaAdle.RSA;
import RivShaAdle.RSADigitSign;
import Utils.Strings;

public class input_RSA_DigitalSignature extends Menu_RSA
{

	private String text = "no sign :)";
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("Here you can sign a text via RSA. The output you'll receive is your input's hash\r\n"
				+ "Moreover you'll find RSA paramters used for the operations \r\n"
				+ "and a final check which will determine the correctness of the digital sign");

		System.out.println("If you wish to continue press any key. Otherwise just enter 'b' to come back at the previous menu");
		boolean decision = this.want2continue();
		if(decision == true)
		{
			this.cryDEcry();
		}
	}
	
	
	private void cryDEcry()
	{
		System.out.println("First input a text you want to sign using RSA. Default text is: "+text);
		String t = this.manageInput();
		if(t != "") { text = t; }
		
		System.out.println("Now insert the RSA modulo length. Common values range between [1024, 3072].\r\n"
				+ "Your input MAY BE CHANGED if smaller than text's bit length");
		String mbl = this.manageInput();
		int modulo_bitlen = 0;
		if(mbl != "")
		{
			try { modulo_bitlen = Integer.valueOf(mbl); }
			catch(NumberFormatException e) {}
		}

		System.out.println("Finally you have to input which hash function you wish to be used in the digital signature. \r\n"
				+ "Allowed values are (case INsensitive): MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512 \r\n"
				+ "If you fail to insert a correct name, SHA-1 will be used as default");
		String hashFunction = this.manageInput();
		
		RSADigitSign ds = new RSADigitSign(text, modulo_bitlen, hashFunction);
		String hash_base10 = Strings.baseSwap(Strings.hashIt(text, hashFunction), 2, 10);
		BigInteger sign = ds.sign_message();
		String decrypted = ds.decrypt_message(sign);
		
		this.printParams(ds.get_rsa());
		System.out.println("Your input text: "+text);
		System.out.println("Its hash (decimal notation): "+hash_base10);
		System.out.println("Its sign: "+sign);
		System.out.println("Final check. The decryption of the latter string is: "+decrypted);
		System.out.println("Your input text and the decrypted one are the same? : "+ds.checkCorrectness(sign));
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
		return inputString;
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
