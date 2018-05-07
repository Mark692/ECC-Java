package my_Crypto;

import java.security.spec.ECPoint;
import Curve.Curve;

public abstract class CodeDecode
{
	/** Curve to be used for encoding operations */
	private Curve c;

	/** Symmetric key used to crypt the message */
	private ECPoint K;
	
	/** Characters to use in decoding operations */
	private String chars2use;

	/** Divides each point during Encoding and Decoding */
	private String breakpoint;
	

	public CodeDecode(Curve c, ECPoint K, String chars2use)
	{
		this.set_c(c);
		this.set_K(K);
		this.set_chars2use(chars2use);
		this.set_breakpoint(this.get_c().get_breakpoint());
	}
	

	protected Curve get_c()
	{
		return c;
	}

	protected void set_c(Curve c)
	{
		this.c = c;
	}

	protected ECPoint get_K()
	{
		return K;
	}

	protected void set_K(ECPoint k)
	{
		this.K = k;
	}

	protected String get_chars2use()
	{
		return chars2use;
	}

	protected void set_chars2use(String chars2use)
	{
		this.chars2use = chars2use;
	}


	protected String get_breakpoint()
	{
		return breakpoint;
	}


	protected void set_breakpoint(String breakpoint)
	{
		this.breakpoint = breakpoint;
	}

}
