package cmd;

import Curve.Curve;
import Curve.NIST;

public class input_Nist extends Menu_EC
{
	private boolean saveCurve;
	private Zfunctions z = new Zfunctions();
	

	/**
	 * Instantiates a NIST sub menu
	 * @param saveCurve Sets whether to save or select the curve
	 */
	public input_Nist(boolean saveCurve) 
	{
		this();
		this.saveCurve = saveCurve;
	}
	
	
	public input_Nist() 
	{
		System.out.println("NIST - Nation Institute of Standards and Technology");
		System.out.println("Select a security level to check its relative curve's parameters");
		System.out.println();
		this.saveCurve = true;
	}
	
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		z.nistCurves();
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
			Curve c = new NIST(input);
			this.printCurve(c, saveCurve);
		}
		//Else terminate this class and go back to the previous menu
	}
}
