package CaseUse;

import java.math.BigInteger;
import Curve.Curve;
import Curve.VRandom;

public class CU_VRandom
{
	
	public static void generateMe()
	{
		String author = "Marco";
		String curveName = "ECC Presentation";
		BigInteger p = new BigInteger("7241650129125985396994653764617241650129125115125175143652468275136514263275465376461");
		int securityLevel = 512;
		
		String seed = "One must acknowledge with cryptography no amount of violence will ever solve a math problem.";
		
		Curve vr;
//		vr = (Curve) new VRandom(author, curveName, seed);
		vr = (Curve) new VRandom(author, curveName, p, seed);
//		vr = (Curve) new VRandom(author, curveName, securityLevel, seed);
		
		System.out.println("Curve generated from the seed: \"" + seed + "\"");
		System.out.println("");
		System.out.println("Curve's equation: y^2 = x^3 + ax + b mod(p)");
		System.out.println("a = " + vr.get_a());
		System.out.println("b = " + vr.get_b());
		System.out.println("p = " + vr.get_p());
		System.out.println("Security Level = " + vr.get_L());

		System.out.println("");
		System.out.println("This curve has a number of points ranging in the following interval:");
		BigInteger[] hasse = vr.Hasse_Interval();
		System.out.println("Lower Bound = " + hasse[0]);
		System.out.println("Upper Bound = " + hasse[1]);

		System.out.println("Which are roughly "
				+ "10^" + hasse[1].toString().length()
				+ " points"
				);
	}
}
