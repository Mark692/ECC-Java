package Hashes;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.BitSet;

public abstract class Fun
{

	/** Represents the length of a binary number */
	protected final int bin_len = 8;
	
	protected int word_len;
	protected int blockSize;
	
	
	public Fun(){}

	
	/**
	 * Converts a number into its binary representation and then pads it into a word
	 * 
	 * @param n The decimal number to convert and pad
	 * @param padUpTo Length of the pad
	 * @return A word representing n
	 */
	public String dec2bin(int n, int padUpTo)
	{
		String N = Integer.toBinaryString(n);
		int pad = padUpTo - (N.length() % padUpTo); //Adapts its length to a multiple of padUpTo
		
		if(pad > 0)
		{
			return String.format("%0" + pad + "d", 0) + N;
		}
		return N;
	}
	
	
	/**
	 * Converts a bit string into a BitSet
	 * 
	 * @param s A bit string to convert into a set of bits
	 * @param bitsetLEN The length of the final bit set to return
	 * @return The bit set of the string given
	 */
	public BitSet string2bitset(String s, int bitsetLEN)
	{
		BitSet output = new BitSet(bitsetLEN);
		int offset = bitsetLEN - s.length();
		
		for(int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i) == '1')
			{
				output.set(offset + i);
			}
		}
		return output;
	}
	
	
	/**
	 * Converts a bit set into a string
	 * 
	 * @param bs The bit set to convert
	 * @return The bit string representing the bit set
	 */
	public String bitset2string(BitSet bs)
	{
		StringBuffer buff = new StringBuffer();
		for(int i = 0; i < bs.size(); i++)
		{
			buff.append(bs.get(i) ? '1' : '0');
		}
		return buff.toString();
	}
	
	
	/**
	 * Performs bitwise AND operation between the two bit sets
	 * 
	 * @param x First bit set 
	 * @param y Second bit set
	 * @return x AND y
	 */
	public BitSet AND(BitSet x, BitSet y)
	{
		BitSet fake = (BitSet) x.clone(); //This will preserve x's bits
		fake.and(y);
		return fake;
	}
	
	
	/**
	 * Performs bitwise OR operation between the two bit sets
	 * 
	 * @param x First bit set 
	 * @param y Second bit set
	 * @return x OR y
	 */
	public BitSet OR(BitSet x, BitSet y)
	{
		BitSet fake = (BitSet) x.clone();
		fake.or(y);
		return fake;
	}
	
	
	/**
	 * Performs bitwise XOR operation between the two bit sets
	 * 
	 * @param x First bit set 
	 * @param y Second bit set
	 * @return x XOR y
	 */
	public BitSet XOR(BitSet x, BitSet y)
	{
		BitSet fake = (BitSet) x.clone();
		fake.xor(y);
		return fake;
	}
	
	
	/**
	 * Complements a bit set
	 * 
	 * @param x The bit set to complement 
	 * @return -x that is x's bits complemented
	 */
	public BitSet negate(BitSet x)
	{
		BitSet fake = (BitSet) x.clone();
		fake.flip(0, this.getWord_len());
		return fake;
	}
	
	
	/**
	 * Shifts all the bits left by "shift" amount
	 * 
	 * @param x The bit set to shift
	 * @return A bit set with shifted bits
	 */
	public BitSet shiftLEFT(BitSet x, int shift)
	{
		if(shift < 0)
		{
			return this.shiftRIGHT(x, -shift);
		}
		else if(shift == 0)
		{
			return x;
		}
		BitSet zeros = new BitSet(this.getWord_len());
		int i = shift;
		while(i < this.getWord_len())
		{
			i = x.nextSetBit(i);
			if(i == -1)
			{
				break;
			}
			zeros.set(i - shift);
			i++;
		}
		return zeros;
	}
	
	
	/**
	 * Shifts all the bits right by "shift" amount
	 * 
	 * @param x The bit set to shift
	 * @return A bit set with shifted bits
	 */
	public BitSet shiftRIGHT(BitSet x, int shift)
	{
		if(shift < 0)
		{
			return this.shiftLEFT(x, -shift);
		}
		else if(shift == 0)
		{
			return x;
		}
		BitSet zeros = new BitSet(this.getWord_len());
		int i = 0;
		while(i < this.getWord_len() - shift)
		{
			i = x.nextSetBit(i);
			if(i == -1)
			{
				break;
			}
			zeros.set(i + shift);
			i++;
		}
		return zeros;
	}
	
	
	/**
	 * Shifts left the bit set and places back on the right side left out bits
	 * 
	 * @param x The bit set to rotate
	 * @param shift How many positions to rotate
	 * @return A rotated bit set
	 */
	public BitSet rotateLEFT(BitSet x, int shift)
	{
		if(shift < 0)
		{
			return this.rotateRIGHT(x, -shift);
		}
		else if(shift == 0)
		{
			return x;
		}
		return this.OR(this.shiftLEFT(x, shift), this.shiftRIGHT(x, this.getWord_len() - shift));
	}
	

	/**
	 * Shifts right the bit set and places back on the left side left out bits
	 * 
	 * @param x The bit set to rotate
	 * @param shift How many positions to rotate
	 * @return A rotated bit set
	 */
	public BitSet rotateRIGHT(BitSet x, int shift)
	{
		if(shift < 0)
		{
			return this.rotateLEFT(x, -shift);
		}
		else if(shift == 0)
		{
			return x;
		}
		return this.OR(this.shiftRIGHT(x, shift), this.shiftLEFT(x, this.getWord_len() - shift));
	}
	
	
	/**
	 * Performs the addition modulo 2^w with the given bit sets. 
	 * This addition follows the following rules:
	 * 1) Convert each bit set into their integer value
	 * 2) Add them together
	 * 3) Compute their sum modulo 2^w
	 * The result will always be within the w range of bits
	 * 
	 * @param one The first bit set to add
	 * @param two The second bit set to add
	 * @return The sum of the two bit sets
	 */
	public BitSet addMod2w(BitSet one, BitSet two)
	{
		String s_one = this.bitset2string(one);
		String s_two = this.bitset2string(two);
		BigInteger b_one = new BigInteger(s_one.substring(0, getWord_len()), 2);
		BigInteger b_two = new BigInteger(s_two.substring(0, getWord_len()), 2);

		String res = ((b_one.add(b_two)).mod(BigInteger.valueOf(2).pow(getWord_len()))).toString(2);
		return this.string2bitset(res, getWord_len());
	}
	
	
	/**
	 * Encodes a message into a bit set. 
	 * The n-th element of the array holds the binary value of the n-th char of the message
	 * 
	 * @param message The message to convert into bits
	 * @return A bit set with each message's char converted into binary notation
	 */
	public BitSet encodeMessage(String message)
	{
		byte[] b = null;
		
		try
		{
			b = message.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) { e.printStackTrace(); } //It won't happen. I hope.

		int last1 = this.getBin_len() * b.length;
		BitSet out = new BitSet(last1);
		for(int i = 0; i < b.length; i++)
		{
			String N = Integer.toBinaryString(b[i]);
			int n_len = N.length();
			int offset = this.getBin_len()*i + this.getBin_len() - n_len; //char's offset + (max length for representing a char - char length)
			
			for(int j = 0; j < n_len; j++)
			{
				if(N.charAt(j) == '1')
				{
					out.set(offset + j);
				}
			}
			if(b[i] < 0) //If the char has a negative representation...
			{
				out.set(this.getBin_len()*i); //...set its first bit to 1
			}
		}
		
		/*
		 * From here a long series of zeroes will follow
		 * The position of this final "1" will be out.length()
		 * The actual message length will then be "out.length() - 1"
		 */
		out.set(last1);
		this.encodePad1024(out, last1, this.getBlockSize());
		return out;
	}
	
	
	/**
	 * Pads an encoded message to a multiple of the block size (either 512 or 1024 depending on the algorithm)
	 * 
	 * @param encoded The encoded message
	 * @param len The actual length of the bit set
	 * @param blockSize The block size for this hash. It will grant the final bit set to be a multiple of 512 or 1024 bits
	 */
	private void encodePad1024(BitSet encoded, int len, int blockSize)
	{
		int padUpTo = 0;
		int pad0s = 0;
		if(blockSize == 512)
		{
			padUpTo = 64; // 1/8 of the block size
			pad0s = 448; // 87.5% of the block size
		}
		else if(blockSize == 1024)
		{
			padUpTo = 128; //Same as above
			pad0s = 896; //Same as above
		}
			
		int trailing0s = (pad0s - len - 1) % blockSize;
		String binLen = this.dec2bin(len, padUpTo);
		for(int i = 0; i < padUpTo; i++)
		{
			if(binLen.charAt(i) == '1')
			{
				encoded.set(len + 1 + trailing0s + i);
			}
		}
	}
	
	
	/**
	 * To be overridden by child classes
	 */
	public void compute() {}
	
	
	/**
	 * Retrieves the length in bit for any binary number 
	 * 
	 * @return The length of any binary number representable
	 */
	protected int getBin_len()
	{
		return bin_len;
	}


	protected int getWord_len()
	{
		return word_len;
	}


	protected void setWord_len(int word_len)
	{
		this.word_len = word_len;
	}


	protected int getBlockSize()
	{
		return blockSize;
	}


	protected void setBlockSize(int blockSize)
	{
		this.blockSize = blockSize;
	}
}