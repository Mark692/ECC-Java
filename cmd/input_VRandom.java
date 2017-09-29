package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import Curve.Curve;
import Curve.VRandom;
import Utils.Strings;

public class input_VRandom extends Menu_EC_submenu
{
	
	private String author;
	private String name;
	private BigInteger p;
	private int L;
	private String seed;

	private enum vr
	{
		no_Input(0),
		p_Only(1),
		L_Only(2),
		seed_Only(3),
		p_Seed(4),
		L_Seed(5);
		
		private int selected;
		private vr(int input)
		{
			this.selected = input;
		}
	};
	
	
	public input_VRandom() 
	{
		System.out.println("Verifiably Random curves");
		System.out.println("The idea is to take a string as input and then to generate the curve parameters from it");
		System.out.println("Here you'll have many functions at your disposal to play with");
		System.out.println();
	}
	
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println(vr.no_Input.selected+" - Generates a VR curve with no input (lazy peeps only)");
		System.out.println(vr.p_Only.selected+" - Input a prime number. It will be checked and corrected before generating the curve.");
		System.out.println(vr.L_Only.selected+" - Input the desired security level only");
		System.out.println(vr.seed_Only.selected+" - Input a string. Note that the relative curve may not exist");
		System.out.println(vr.p_Seed.selected+" - Input a prime number p and the string. Same note as above");
		System.out.println(vr.L_Seed.selected+" - Input the security level and the string. Again, note as above");
		System.out.println();
		System.out.println("If the input is incorrect, option "+vr.no_Input.selected+" will be used as default");
		this.handleInput();
	}

	
	@Override
	/**
	 * Takes the user input and switches it into multiple options
	 * 
	 * @param c The user input to switch into different curves
	 */
	public void switchUserInput(int input)
	{
		if(input != back)
		{
			this.askAuthor_Name();
			Curve c = null;
			
			if(input == vr.p_Only.selected) 
			{
				this.input_p();
				c = this.p_Only();
			}
			else if(input == vr.L_Only.selected) 
			{
				this.input_L();
				c = this.L_Only();
			}
			else if(input == vr.seed_Only.selected) 
			{
				this.input_seed();
				c = this.seed_Only();
			}
			else if(input == vr.p_Seed.selected) 
			{
				this.input_p();
				this.input_seed();
				c = this.p_Seed();
			}
			else if(input == vr.L_Seed.selected) 
			{
				this.input_L();
				this.input_seed();
				c = this.L_Seed();
			}
			else // if(input == vr.no_Input.selected) 
			{
				c = this.no_Input();
			}
			
			this.printCurveParams(c);
		}
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
	 * Sets the security level accordingly to the user's input
	 */
	private void input_L()
	{
		System.out.println("Insert the security level in bits. \r\n"
				+ "If you fail to write a number 80 will be used as default \r\n"
				+ "Security levels usually range within [80, 256] bits. ");
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
			this.L = Integer.parseInt(inputString);
		}
		catch (NumberFormatException e) { this.L = 80; }
	}


	/**
	 * Sets the seed accordingly to the user's input
	 */
	private void input_seed()
	{
		System.out.println("Insert a string... just a string. \r\n"
				+ "It may happen that the curve cannot be generated hence the seed is invalid");
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
		try
		{
			inputString = buffRead.readLine();
		}
		catch (IOException e) { inputString = Strings.seed(); }

		this.seed = inputString;
	}
	
	
	private Curve p_Only()
	{
		return new VRandom(
				this.author,
				this.name,
				this.p);
	}


	private Curve L_Only()
	{
		return new VRandom(
				this.author,
				this.name,
				this.L);
	}


	private Curve seed_Only()
	{
		if(this.seed != "")
		{
			return new VRandom(
					this.author,
					this.name,
					this.seed);
		}
		else
		{
			return this.no_Input();
		}
	}


	private Curve p_Seed()
	{
		if(this.seed != "")
		{
			return new VRandom(
					this.author,
					this.name,
					this.p,
					this.seed);
		}
		else
		{
			return this.p_Only();
		}
	}


	private Curve L_Seed()
	{
		if(this.seed != "")
		{
			return new VRandom(
					this.author,
					this.name,
					this.L,
					this.seed);
		}
		else
		{
			return this.L_Only();
		}
	}


	private Curve no_Input()
	{
		return new VRandom(
				this.author,
				this.name);
	}
	
	
	private void printCurveParams(Curve c)
	{
		System.out.println("Author: "+c.get_author());
		System.out.println("Curve name: "+c.get_name());
		System.out.println("Security Level = "+c.get_L()+" bits");
		System.out.println("Seed = "+new BigInteger(c.getSeed(), 2).toString(16) );
		System.out.println("");
		System.out.println("p = "+c.get_p());
		System.out.println("a = "+c.get_a());
		System.out.println("b = "+c.get_b());

		System.out.println();
		System.out.println("If both 'a' and 'b' are 0, the seed is INVALID.");
		System.out.println();
		System.out.println("Enter 's' to save this curve into a .txt file");
		System.out.println("Enter 'b' to go back to the previous menu");
		this.handleInput(c);
	}
}
