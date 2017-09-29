package cmd;

import Curve.Curve;
import Curve.NIST;

public class input_NistDS extends Menu_DigitalSign
{
	private Zfunctions z = new Zfunctions();
	
	public input_NistDS() 
	{
		System.out.println("NIST - Nation Institute of Standards and Technology");
		System.out.println("Select a security level to check its relative curve's parameters");
		System.out.println();
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
			this.selectedCurve = new NIST(input);
			this.printCurve(selectedCurve);
		}
		//Else terminate this class and go back to the previous menu
	}
}
