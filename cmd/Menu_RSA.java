package cmd;

public class Menu_RSA extends Menu_Main
{

	private enum choice
	{
		example(1),
		cryptDecrypt(2),
		digitalSign(3);
		
		private int opt;
		private choice(int input)
		{
			this.opt = input;
		}
	};
	
	
	public Menu_RSA() {}
	
	
	/**
	 * Shows user choices for this menu
	 */
	public void menuStructure()
	{
		System.out.println("This menu is about RSA.");
		System.out.println("RSA is an (old) algorithm used in cryptography. \r\n"
				+ "Initially used for Asymmetric Cryptography, it is now applied to digital signature \r\n"
				+ "The reasons behind this follow new mathematical discoveries (Index Calculus and Lattices mostly) \r\n"
		+ "and the arrival of Elliptic Curves in the cryptographic world.");

		System.out.println();
		System.out.println(choice.example.opt+" - Generate RSA parameters");
		System.out.println(choice.cryptDecrypt.opt+" - Encrypt and decrypt messages");
		System.out.println(choice.digitalSign.opt+" - Digital Signature");
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
			if(c == choice.example.opt) 
			{
				this.RSA_exampleMenu();
			}
			else if(c == choice.cryptDecrypt.opt) 
			{
				this.RSA_DEcrypt();
			}
			else if(c == choice.digitalSign.opt) 
			{
				this.RSA_DigitalSign();
			}
			this.menuStructure();
		}
		//Else terminate this class and go back to the previous menu
	}


	private void RSA_exampleMenu()
	{
		input_RSA_Params rsa = new input_RSA_Params();
		rsa.menuStructure();
	}

	private void RSA_DEcrypt()
	{
		input_RSA_Crypt dc = new input_RSA_Crypt();
		dc.menuStructure();
	}

	private void RSA_DigitalSign()
	{
		input_RSA_DigitalSignature dc = new input_RSA_DigitalSignature();
		dc.menuStructure();
	}
}
