package cmd;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Menu_Main
{
	protected final int back = -1;
	protected final String Help = "h";
	protected final String Back = "b";
	protected final String Save = "s";
	protected final String Use = "u";
	
	protected final String savingDirectory = "src\\..\\Saved Curves\\";
	
	private enum cats 
	{
		EC(1), 
		ECustomVR(2), 
		RSA(3),
		DigitalSign(4),
		Benchmark(5),
		Utils(6);
		
		private int number;
		private cats(int input)
		{
			this.number = input;
		}
	};
		
	
	public Menu_Main() {}

	
	/**
	 * Starts the app
	 */
	public void start_me()
	{
		System.out.println("Welcome! This app is about... variuos stuff.");
		System.out.println("");
		System.out.println("For starters, you can smash buttons all over your keyboard and see what happens");
		System.out.println("otherwise just enter these simple keys:");
		System.out.println("");
		
		this.menuStructure();
	}
		
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println(cats.EC.number+" - Standard Elliptic Curves");
		System.out.println(cats.ECustomVR.number+" - Create Your own elliptic curves!");
		System.out.println(cats.RSA.number+" - RSA menu");
		System.out.println(cats.DigitalSign.number+" - Create digital signature");
		System.out.println(cats.Benchmark.number+" - Perform Benchmark tests");
		System.out.println(cats.Utils.number+" - More stuff");
		System.out.println("");

		this.handleInput();
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
		}
		else
		{
			try
			{
				inputCMD = Integer.parseInt(inputString);
			}
			catch (NumberFormatException e) //In case the string is not an integer
			{
//				 System.exit(0); //Terminates the app
				 this.menuStructure(); //Take care of this in the children classes!!
			}
		}

		this.switchUserInput(inputCMD);
	}
	
	
	/**
	 * Takes the user input and switches it into multiple options
	 * 
	 * @param c The user input to switch into this menu's functions
	 */
	public void switchUserInput(int c)
	{
		if(c != back)
		{
			if(c == cats.EC.number) 
			{
				this.ECmenu();
			}
			else if(c == cats.ECustomVR.number) 
			{
				this.ECustomVR();
			}
			else if(c == cats.RSA.number) 
			{
				this.RSAmenu();
			}
			else if(c == cats.DigitalSign.number) 
			{
				this.DigitalSignMenu();
			}
			else if(c == cats.Benchmark.number) 
			{
				this.BenchmarkMenu();
			}
			else if(c == cats.Utils.number) 
			{
				this.UtilsMenu();
			}
		}
		this.menuStructure();
	}


	public void help(String s)
	{
		System.out.println("The inserted command is: "+s.toLowerCase());
		//SPIEGA I VARI VALORI DI INPUT E A COSA CONDUCONO
		this.menuStructure(); //Comes back to the selection menu
	}
	
	
	/**
	 * Open the Elliptic curve menu
	 */
	private void ECmenu()
	{
		Menu_EC ec = new Menu_EC(); //Opens the EC menu
		ec.menuStructure();
	}
	
	
	/**
	 * Open the menu about Custom and VR elliptic curves
	 */
	private void ECustomVR()
	{
		Menu_EC_submenu ecvr = new Menu_EC_submenu(); //Opens the EC Custom-VR menu
		ecvr.menuStructure();
	}
	
	
	/**
	 * Open the RSA menu
	 */
	private void RSAmenu()
	{
		Menu_RSA rsa = new Menu_RSA(); //Opens the RSA menu
		rsa.menuStructure();
	}


	/**
	 * Open the Digital Signature menu
	 */
	private void DigitalSignMenu()
	{
		Menu_DigitalSign ds = new Menu_DigitalSign(); //Opens the Digital Signature menu
		ds.menuStructure();
	}


	/**
	 * Open the Benchmark menu
	 */
	private void BenchmarkMenu()
	{
		Menu_Benchmark bm = new Menu_Benchmark(); //Opens the Digital Signature menu
		bm.menuStructure();
	}
	
	
	private void UtilsMenu()
	{
		System.out.println("Other functions are...");
		new Menu_Utilities(); //Opens the Utils menu
	}
}
