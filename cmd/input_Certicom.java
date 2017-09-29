package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Curve.Certicom;
import Curve.Curve;

public class input_Certicom extends Menu_EC
{	
	private boolean saveCurve;
	private Zfunctions z = new Zfunctions();
	

	public input_Certicom(boolean saveCurve) 
	{
		this();
		this.saveCurve = saveCurve;
	}
	
	
	public input_Certicom() 
	{
		System.out.println("Certicom");
		System.out.println("Select a security level to check its relative curve's parameters");
		System.out.println();

		z.generateCerticomCurves();
	}

	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		z.certicomCurves();
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
		int secLev = z.secLev_default;
		int version = z.version_default;
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
			secLev = back;
		}
		else
		{
			int offset = inputString.indexOf(" ");
			if(offset != -1)
			{
				String SecLev = inputString.substring(0, offset);
				String Version = inputString.substring(offset + 1);
				
				try
				{
					secLev = Integer.parseInt(SecLev);
					version = Integer.parseInt(Version);
				}
				catch (NumberFormatException e) 
				{ 
					this.switchCathegory(z.secLev_default, z.version_default); 
				}
			}
		}

		this.switchCathegory(secLev, version);
	}
	
	
	/**
	 * Takes the user input and switches it into multiple options
	 * 
	 * @param selectedCurve The user input to switch into different curves
	 */
	public void switchCathegory(int secLev, int version)
	{
		if(secLev != back)
		{
			Curve c = new Certicom(secLev, version);
			this.printCurve(c, saveCurve);
		}
		//Else terminate this class and go back to the previous menu
	}
}