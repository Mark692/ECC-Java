package cmd;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import Curve.Curve;

/**
 * This class handles the many functions needed to select a curve
 * Its applications are in Digital Signature and in Cryptographic contexts
 */
public class Zfunctions extends Menu_Main
{
	public Curve c;
	
	private int[] nist = 
		{
			Curve.SECURITY_LEVEL_80,
			Curve.SECURITY_LEVEL_112,
			Curve.SECURITY_LEVEL_128, 
			Curve.SECURITY_LEVEL_192, 
			Curve.SECURITY_LEVEL_256
		};
	
	public void nistCurves()
	{
		for(int L : nist)
		{
			System.out.println(L+" - Security Level of "+L+" bits");
		}
		System.out.println();
		System.out.println("If the input is incorrect, security level of 80 bits will be used as default");
	}
	

	private int[] brainpool = 
		{
			Curve.SECURITY_LEVEL_80,
			Curve.SECURITY_LEVEL_96,
			Curve.SECURITY_LEVEL_112,
			Curve.SECURITY_LEVEL_128, 
			Curve.SECURITY_LEVEL_160, 
			Curve.SECURITY_LEVEL_192, 
			Curve.SECURITY_LEVEL_256
		};
	
	public void brainpoolCurves()
	{
		for(int L : brainpool)
		{
			System.out.println(L+" - Security Level of "+L+" bits");
		}
		System.out.println();
		System.out.println("If the input is incorrect, security level of 80 bits will be used as default");
	}
	

	private int[] microsoft = 
		{
			Curve.SECURITY_LEVEL_80
		};
	
	public void microsoftCurves()
	{
		for(int L : microsoft)
		{
			System.out.println(L+" - Security Level of "+L+" bits");
		}
		System.out.println();
		System.out.println("If the input is incorrect, security level of 80 bits will be used as default");
	}
	
	

	/** A map of curve's security level and its version */
	private TreeMap<Integer, ArrayList<Integer>> secLev_Version = new TreeMap<Integer, ArrayList<Integer>>();
	public int secLev_default = 80;
	public int version_default = 3;
	
	public void generateCerticomCurves()
	{
		ArrayList<Integer> v1 = new ArrayList<>();
		v1.add(1);
		
		ArrayList<Integer> v1_2 = new ArrayList<>();
		v1_2.add(1); v1_2.add(2); 
		
		ArrayList<Integer> v1_2_3 = new ArrayList<>();
		v1_2_3.add(1); v1_2_3.add(2); v1_2_3.add(3);
		
		secLev_Version.put(Curve.SECURITY_LEVEL_56, v1_2);
		secLev_Version.put(Curve.SECURITY_LEVEL_64, v1_2);
		secLev_Version.put(Curve.SECURITY_LEVEL_80, v1_2_3);
		secLev_Version.put(Curve.SECURITY_LEVEL_96, v1_2);
		secLev_Version.put(Curve.SECURITY_LEVEL_112, v1_2);
		secLev_Version.put(Curve.SECURITY_LEVEL_128, v1_2);
		secLev_Version.put(Curve.SECURITY_LEVEL_192, v1);
		secLev_Version.put(Curve.SECURITY_LEVEL_256, v1);
	}
	
	public void certicomCurves()
	{
		for(Entry<Integer, ArrayList<Integer>> lv : secLev_Version.entrySet())
		{
			for(Integer version : lv.getValue())
			{
				System.out.println(lv.getKey()+" "+version+" - Security Level of "+lv.getKey()+" bits, version "+version);
			}
		}
		System.out.println();
		System.out.println("If the input is incorrect, security level = "+secLev_default+" and/or version = "+version_default+" will be used as default parameters");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
