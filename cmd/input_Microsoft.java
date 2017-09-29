package cmd;

import Curve.Curve;
import Curve.Microsoft;

public class input_Microsoft extends Menu_EC
{
	private boolean saveCurve;
	private Zfunctions z = new Zfunctions();
	

	public input_Microsoft(boolean saveCurve) 
	{
		this();
		this.saveCurve = saveCurve;
	}
	
	public input_Microsoft() 
	{
		System.out.println("Microsoft                              (remember Windows? That's it)");
		System.out.println("You don't have much of a choice here... but you know, it's Microsoft");
		System.out.println();
	}
	

	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		z.microsoftCurves();
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
			Curve c = new Microsoft();
			this.printCurve(c, saveCurve);
		}
		//Else terminate this class and go back to the previous menu
	}
}