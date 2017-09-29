package cmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import Curve.Curve;

public class Menu_EC_submenu extends Menu_Main
{
	private enum choose
	{
		custom(1),
		vrandom(2);
		
		private int subMenu;
		private choose(int input)
		{
			this.subMenu = input;
		}
	};
	

	public Menu_EC_submenu() {}
	
		
//	@Override
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("Here you can create your own elliptic curves!");
		System.out.println();
		System.out.println(choose.custom.subMenu+" - Lets you input all the parameters with a check for every entry");
		System.out.println(choose.vrandom.subMenu+" - Starting from a random seed you'll be able to generate a new curve");
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
			if(c == choose.custom.subMenu) 
			{
				this.customMenu();
			}
			else if(c == choose.vrandom.subMenu) 
			{
				this.VRMenu();
			}
			this.menuStructure();
		}
		//Else terminate this class and go back to the previous menu
	}


	private void customMenu()
	{
		input_Custom c = new input_Custom();
		c.menuStructure();
	}

	private void VRMenu()
	{
		input_VRandom vr = new input_VRandom();
		vr.menuStructure();
	}
	
	
	/**
	 * Handles user input, exceptions and redirects
	 */
	protected void handleInput(Curve c)
	{
		InputStreamReader inputSR = new InputStreamReader(System.in);
		BufferedReader buffRead = new BufferedReader(inputSR);
		String inputString = "";
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
			//This will exit from this menu
		}
		else if(inputString.compareToIgnoreCase(Save) == 0)
		{
			this.saveCurve(c);
		}
		this.menuStructure();
	}


	/**
	 * Creates a .txt file in which the curve parameters will be saved
	 * 
	 * @param c The curve to save
	 */
	private void saveCurve(Curve c)
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
}