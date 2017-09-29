package cmd;

import Curve.Curve;
import Curve.Brainpool;

public class input_Brainpool extends Menu_EC
{
	private boolean saveCurve;
	private Zfunctions z = new Zfunctions();
	
	
	public input_Brainpool(boolean saveCurve) 
	{
		this();
		this.saveCurve = saveCurve;
	}
	
	
	public input_Brainpool() 
	{
		System.out.println("Brainpool    (Never heard of it? Me neither.)");
		System.out.println("Select a security level to check its relative curve's parameters");
		System.out.println();
	}
	

	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		z.brainpoolCurves();
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
			Curve c = new Brainpool(input);
			this.printCurve(c, saveCurve);
		}
		//Else terminate this class and go back to the previous menu
	}
}