package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import Curve.Curve;

public class Menu_DigitalSign extends Menu_Main
{
	private Zfunctions z;
	private boolean goBack = true;
	protected Curve selectedCurve;
	private enum Curve_type
	{
		nist(1),
		certicom(2),
		brainpool(3),
		microsoft(4), 
		custom(5),
		vrandom(6);
		
		private int chosen;
		private Curve_type(int input)
		{
			this.chosen = input;
		}
	};
	
	
//	@Override
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("Here you can generate digital signatures with an Elliptic Curve of your choice");
		System.out.println("If you wish to continue, select a curve from this menu, \r\n"
				+ "otherwise press b to come back to the previous menu");
		System.out.println();

		System.out.println(Curve_type.nist.chosen+" - NIST standardized curves");
		System.out.println(Curve_type.certicom.chosen+" - Certicom curves");
		System.out.println(Curve_type.brainpool.chosen+" - Brainpool curves");
		System.out.println(Curve_type.microsoft.chosen+" - Microsoft curve");
		System.out.println(Curve_type.custom.chosen+" - Lets you input all the parameters with a check for every entry");
		System.out.println(Curve_type.vrandom.chosen+" - Starting from a random seed you'll be able to generate a new curve");
		System.out.println("");
		
		System.out.println("SONO IN DS");
		Zfunctions z = new Zfunctions();
		this.z = z;
		this.handleInput(); //After completing we have our Curve c
		System.out.println("MA QUI QUANDO CAZZO CI ARRIVI?!?!?!?!");
		System.out.println("Autore di questa minchia di curva: "+z.c.get_author());
		if(selectedCurve != null)
		{
			if(selectedCurve.get_a().compareTo(BigInteger.ZERO) != 0  //Case for an invalid VRandom 
					&& selectedCurve.get_b().compareTo(BigInteger.ZERO) != 0)
			{
				System.out.println("PROVA: GUARDA QUA -> "+selectedCurve.get_author());
			}
		}
		else
		{
			System.out.println("Do you wish to go back to the previous menu? \r\n"
					+ "Press any key to continue with Digital Signature or 'b' to go back");
			boolean goBack = this.want2continue();
			if(goBack == true)
			{
				this.menuStructure();
			}
		}
	}
	
	
	/**
	 * Handles user input, exceptions and redirects
	 */
	protected void handleInput()
	{
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		int inputCMD = 0;
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { e.printStackTrace(); }
		
		if(inputString.compareToIgnoreCase(Help) == 0)
		{
			this.help(inputString);
		}
		else if(inputString.compareToIgnoreCase(Back) == 0)
		{
			inputCMD = back;
			goBack = true;
		}
		else
		{
			try
			{
				inputCMD = Integer.parseInt(inputString);
			}
			catch (NumberFormatException e) //In case the string is not an integer
			{
				 this.menuStructure();
			}
		}
		this.switchUserInput(inputCMD);
	}
	
	
	/**
	 * Takes the user input and switches it into multiple options
	 * 
	 * @param cmd The user input to switch into this menu's functions
	 */
	public void switchUserInput(int cmd)
	{
		if(cmd != back)
		{
			goBack = false;
			
			if(cmd == Curve_type.nist.chosen) 
			{
				input_NistDS n = new input_NistDS();
				n.menuStructure();
			}
//			else if(cmd == Curve_type.certicom.chosen) 
//			{
//				input_CerticomDS c = new input_CerticomDS();
//				c.menuStructure();
//			}
//			else if(cmd == Curve_type.brainpool.chosen) 
//			{
//				input_BrainpoolDS b = new input_BrainpoolDS();
//				b.menuStructure();
//			}
//			else if(cmd == Curve_type.microsoft.chosen) 
//			{
//				input_MicrosoftDS m = new input_MicrosoftDS();
//				m.menuStructure();
//			}
//			else if(cmd == Curve_type.custom.chosen) 
//			{
//				input_CustomDS c = new input_CustomDS();
//				c.menuStructure();
//			}
//			else if(cmd == Curve_type.vrandom.chosen) 
//			{
//				input_VRandomDS vr = new input_VRandomDS();
//				vr.menuStructure();
//			}
		}
		else
		{
			goBack = true;
		}
	}

	
	/**
	 * Prints the curve's parameters and lets chose between save/select the curve for the next operation
	 * 
	 * @param c The curve to show
	 */
	public void printCurve(Curve c)
	{
		System.out.println("Author: "+c.get_author());
		System.out.println("Curve name: "+c.get_name());
		System.out.println("Security Level = "+c.get_L()+" bits");
		System.out.println("");
		System.out.println("p = "+c.get_p());
		System.out.println("a = "+c.get_a());
		System.out.println("b = "+c.get_b());
		System.out.println("x = "+c.get_G().getAffineX());
		System.out.println("y = "+c.get_G().getAffineY());
		System.out.println("n = "+c.get_n());
		System.out.println("h = "+c.get_h());

		System.out.println();
		System.out.println("Enter '"+Use+"' to use this curve");
		System.out.println("Enter '"+Back+"' to go back to the previous menu");
		this.handleInput(c);
	}

	
	/**
	 * Handles user inputs, exceptions and redirects
	 */
	protected Curve handleInput(Curve c)
	{
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		int inputCMD = 0;
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = Back; }
		
		if(inputString.compareToIgnoreCase(Help) == 0)
		{
			this.help(inputString);
		}
		else if(inputString.compareToIgnoreCase(Back) == 0)
		{
			inputCMD = back;
		}
		else if(inputString.compareToIgnoreCase(Use) == 0)
		{
			return c;
		}
		this.switchUserInput(inputCMD);
		return null;
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
	

	class myCurve
	{
		Curve c;
	}
}
