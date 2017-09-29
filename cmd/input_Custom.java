package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import Curve.Curve;
import Curve.Custom;

public class input_Custom extends Menu_EC_submenu
{
	private String author;
	private String name;
	private BigInteger p;
	private BigInteger a;
	private BigInteger b;
	
	
	public input_Custom() 
	{
		System.out.println("Custom curves");
		System.out.println("A custom curve is built on your own parameters only. No seeds involved.");
		System.out.println("By the way, since computing a generator point is a difficul problem, "
				+ "this function will only validate your inputs and create a valid Elliptic Curve.");
		System.out.println();
	}
	
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("Press any key if you want to create Your curve, \r\n"
				+ "otherwise write 'b' to return to the previous menu");
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
			this.generateCustom();
		}
	}
	
	
	/**
	 * Takes the user input and switches it into multiple options
	 */
	public void generateCustom()
	{
		this.askAuthor_Name();
		this.input_p();
		this.input_a();
		this.input_b();
		
		this.printCurveParams(
				new Custom(
					this.author,
					this.name,
					this.a,
					this.b,
					this.p
				));
	}
	

	/**
	 * Sets the author and the curve name accordingly to the user's inputs
	 */
	private void askAuthor_Name()
	{
		System.out.println("First, tell me your name: ");
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = ""; }
		this.author = inputString;
		

		System.out.println("Do you want to give a name to your curve? Yes you want, here it is: ");
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = ""; }
		this.name = inputString;
	}


	/**
	 * Sets the field's cardinality accordingly to the user's input
	 */
	private void input_p()
	{
		System.out.println("Insert a prime number. \r\n"
				+ "If you fail to write a number ~2^80 will be used as default \r\n"
				+ "If your input is not a prime number, it will be changed into a random close prime number");
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = ""; }
		
		try
		{
			this.p = new BigInteger(inputString);
		}
		catch (NumberFormatException e) { this.p =  BigInteger.valueOf(2).pow(80); }
	}


	/**
	 * Sets the curve's parameter 'a' accordingly to the user's input
	 */
	private void input_a()
	{
		System.out.println("Insert the curve's first parameter: a \r\n"
				+ "Note: The EC equation is y^2 = x^3 + ax + b");
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = ""; }

		try
		{
			this.a = new BigInteger(inputString);
		}
		catch (NumberFormatException e) { this.a =  BigInteger.ZERO; }
	}


	/**
	 * Sets the curve's parameter 'a' accordingly to the user's input
	 */
	private void input_b()
	{
		System.out.println("Insert the curve's second parameter: b \r\n"
				+ "Note: The EC equation is y^2 = x^3 + ax + b");
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = ""; }

		try
		{
			this.b = new BigInteger(inputString);
		}
		catch (NumberFormatException e) { this.b =  BigInteger.ZERO; }
	}
	
	
	private void printCurveParams(Curve c)
	{
		System.out.println("Author: "+c.get_author());
		System.out.println("Curve name: "+c.get_name());
		System.out.println("Security Level = "+c.get_L()+" bits");
		System.out.println("");
		System.out.println("p = "+c.get_p());
		System.out.println("a = "+c.get_a());
		System.out.println("b = "+c.get_b());
		System.out.println();

		System.out.println();
		System.out.println("Enter 's' to save this curve into a .txt file");
		System.out.println("Enter 'b' to go back to the previous menu");
		this.handleInput(c);
	}
}