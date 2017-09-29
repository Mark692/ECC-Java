package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import RivShaAdle.RSA;

public class input_RSA_Params extends Menu_RSA
{

	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("Insert the modulo's length in bit. The RSA parameters will be generated according to your input. \r\n"
				+ "In order to see fast results a max of 3072 is suggested"
				+ "\r\n"
				+ "If no input is provided a standard setup will be used for generating the private key");
		this.handleInput();
	}
	

	@Override
	/**
	 * Handles user input, exceptions and redirects
	 */
	protected void handleInput()
	{
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = ""; }
		
		if(inputString.compareToIgnoreCase(Help) == 0)
		{
			this.help(inputString);
		}
		else if(inputString.compareToIgnoreCase(Back) == 0)
		{
			//This will exit the menu if 'b' is pressed
		}
		else
		{
			this.generateParams(inputString);
		}
	}
	
	
	private void generateParams(String inputString)
	{
		RSA rsa;
		int modulo_bitlen = 0;
		try
		{
			modulo_bitlen = Integer.valueOf(inputString);
		}
		catch(NumberFormatException e) { inputString = ""; }
		
		if(inputString == "")
		{
			rsa = new RSA();
		}
		else
		{
			rsa = new RSA(modulo_bitlen);
		}

		System.out.println("p = "+rsa.get_p());
		System.out.println("q = "+rsa.get_q());
		System.out.println("n = p*q = "+rsa.get_n());
		System.out.println("Theta(n) = "+rsa.getEulerTheta());
		System.out.println();
		System.out.println("e = K+ = "+rsa.get_e());
		System.out.println("d = K- = "+rsa.get_d());
		System.out.println();
		System.out.println("______________________________________________________");
	}
}
