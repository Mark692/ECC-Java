package main;

//import cmd.Menu_Main;
import CaseUse.CU_ECMQV;
import CaseUse.RSAenc;

public class Start_it
{
	
	public static void main(String[] args)
	{
//		Menu_Main m = new Menu_Main();
//		m.start_me();
		
//		 RSAenc r = new RSAenc();
//		 r.cryptDecrypt();
		 
		CU_ECMQV.cryptDecrypt();
//		CU_ECMQV.cryptDecryptAugmented();
		 
	}
}