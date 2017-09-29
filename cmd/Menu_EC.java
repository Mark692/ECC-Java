package cmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import Curve.Curve;

public class Menu_EC extends Menu_Main
{	
	private Curve c;
	private enum authors
	{
		nist(1),
		certicom(2),
		brainpool(3),
		microsoft(4);
		
		private int auth;
		private authors(int input)
		{
			this.auth = input;
		}
	};
	

	public Menu_EC() {}
	
		
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("This menu is about Elliptic Curves. Here you can check common curves used in cryptography");
		System.out.println("Start selecting which authority you want to know more about");
		System.out.println();
		
		System.out.println(authors.nist.auth+" - NIST standardized curves");
		System.out.println(authors.certicom.auth+" - Certicom curves");
		System.out.println(authors.brainpool.auth+" - Brainpool curves");
		System.out.println(authors.microsoft.auth+" - Microsoft curve");
		System.out.println("");
		
		this.handleInput();
	}
	
	
	@Override
	/**
	 * Takes the user input and switches it into multiple options
	 * 
	 * @param c The user input to switch into this menu's functions
	 */
	public void switchUserInput(int c)
	{
		if(c != back)
		{
			System.out.println("Nope!!!!");
			if(c == authors.nist.auth) 
			{
				System.out.println("1!!!!");
				this.NISTmenu();
			}
			else if(c == authors.certicom.auth) 
			{
				System.out.println("2!!!!");
				this.CerticomMenu();
			}
			else if(c == authors.brainpool.auth) 
			{
				System.out.println("3!!!!");
				this.BrainpoolMenu();
			}
			else if(c == authors.microsoft.auth) 
			{
				System.out.println("4!!!!");
				this.MicrosoftMenu();
			}
			System.out.println("Manda il menustructure di MenuEC!!!!");
			this.menuStructure();
		}
		System.out.println("SHOULD TERMINATE THE CLASS!!!!");
		//Else terminate this class and go back to the previous menu
	}


	private void NISTmenu()
	{
		input_Nist n = new input_Nist();
		n.menuStructure();
	}

	private void CerticomMenu()
	{
		input_Certicom c = new input_Certicom();
		c.menuStructure();
	}
	
	private void BrainpoolMenu()
	{
		input_Brainpool b = new input_Brainpool();
		b.menuStructure();
	}
	
	private void MicrosoftMenu()
	{
		input_Microsoft m = new input_Microsoft();
		m.menuStructure();
	}
	
	
	/**
	 * Handles user inputs, exceptions and redirections
	 */
	protected void handleInput(Curve c)
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
		else if(inputString.compareToIgnoreCase(Save) == 0)
		{
			this.saveCurve(c);
		}
		else if(inputString.compareToIgnoreCase(Use) == 0)
		{
			this.c = c;
			inputCMD = back;
		}
		this.switchUserInput(inputCMD);
	}


	/**
	 * Creates a .txt file in which the curve parameters will be saved
	 * 
	 * @param c The curve to save
	 */
	protected void saveCurve(Curve c)
	{
		try
		{
			FileWriter writer = new FileWriter(savingDirectory+c.get_name());
			BufferedWriter buffer = new BufferedWriter(writer);
			
			String caret = "\r\n";

			buffer.write("Author: "+c.get_author()+caret);
			buffer.write("Curve name: "+c.get_name()+caret);
			buffer.write("Security Level = "+c.get_L()+" bits"+caret);
			buffer.write(caret);
			buffer.write("p = "+c.get_p()+caret);
			buffer.write("a = "+c.get_a()+caret);
			buffer.write("b = "+c.get_b()+caret);
			buffer.write("x = "+c.get_G().getAffineX()+caret);
			buffer.write("y = "+c.get_G().getAffineY()+caret);
			buffer.write("n = "+c.get_n()+caret);
			buffer.write("h = "+c.get_h()+caret);
			buffer.close();
			writer.close();
			System.out.println("Awesome!");
			System.out.println("This curve was correctly saved in this project source directory inside the folder 'Saved Curves'");
		}
		catch (IOException e)
		{
			System.out.println("Something went terribly wrong :)");
			System.out.println("Take this pretty nice error: ");
			e.printStackTrace();
		}
		
		System.out.println();
	}
	

	/**
	 * Prints the curve's parameters and lets chose between save/select the curve for the next operation
	 * 
	 * @param c The curve to show
	 */
	public void printCurve(Curve c, boolean want2save)
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
		if(want2save == true)
		{
			System.out.println("Enter '"+Save+"' to save this curve into a .txt file");
		}
		else
		{
			System.out.println("Enter '"+Use+"' to use this curve");
		}
		System.out.println("Enter '"+Back+"' to go back to the previous menu");
		this.handleInput(c);
	}
}
