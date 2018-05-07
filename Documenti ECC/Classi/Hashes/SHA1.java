package Hashes;

import java.util.BitSet;

public final class SHA1 extends Fun
{
	private final String k0_19  = "01011010100000100111100110011001"; // "5a827999";
	private final String k20_39 = "01101110110110011110101110100001"; // "6ed9eba1";
	private final String k40_59 = "10001111000110111011110011011100"; // "8f1bbcdc"; 
	private final String k60_79 = "11001010011000101100000111010110"; // "ca62c1d6"; 

	private final String h0 = "01100111010001010010001100000001"; // "67452301"; 
	private final String h1 = "11101111110011011010101110001001"; // "efcdab89"; 
	private final String h2 = "10011000101110101101110011111110"; // "98badcfe";
	private final String h3 = "00010000001100100101010001110110"; // "10325476"; 
	private final String h4 = "11000011110100101110000111110000"; // "c3d2e1f0"; 

	private BitSet K0_19;
	private BitSet K20_39;
	private BitSet K40_59;
	private BitSet K60_79;
	
	private BitSet H0;
	private BitSet H1;
	private BitSet H2;
	private BitSet H3;
	private BitSet H4;
	
	
	/**
	 * Instantiates a SHA1 object.
	 * SHA1 uses 32-bit words and a message block of 512-bit length
	 */
	public SHA1()
	{
		super();
		this.setWord_len(32);
		this.setBlockSize(512);

		K0_19  = this.string2bitset(k0_19,  32);
		K20_39 = this.string2bitset(k20_39, 32);
		K40_59 = this.string2bitset(k40_59, 32);
		K60_79 = this.string2bitset(k60_79, 32);
		
		H0 = this.string2bitset(h0, 32);
		H1 = this.string2bitset(h1, 32);
		H2 = this.string2bitset(h2, 32);
		H3 = this.string2bitset(h3, 32);
		H4 = this.string2bitset(h4, 32);
	}

	
	/**
	 * Computes the sha1 of the message
	 * 
	 * @param bs The bit set representing the message
	 * @return The message's hash
	 */
	public String compute(BitSet bs)
	{
		int N = bs.size() / this.getBlockSize();

		for(int i = 1; i <= N; i++)
		{
			//2. Initialize the five working variables
			BitSet a = (BitSet) H0.clone();
			BitSet b = (BitSet) H1.clone();
			BitSet c = (BitSet) H2.clone();
			BitSet d = (BitSet) H3.clone();
			BitSet e = (BitSet) H4.clone();
			BitSet T = null;

			BitSet[] word = new BitSet[16];

			for(int t = 0; t < 80; t++)
			{
				//1. Prepare the message schedule W
				if(t < 16)
				{
//					int offset = t * this.getWord_len(); // The total range is [0, 16*32] = [0, 512]
//					int offset = t * this.getWord_len() + (this.getWord_len() * (i - 1)); // The total range is [512i+0, 16*32 + 512i] = [0, 512] for each iteration i
					int offset = this.getWord_len() * (t + i - 1); // Same as above
					word[t] = (BitSet) bs.get(offset, offset + this.getWord_len()).clone(); //MAY GIVE PROBLEMS IF IT'S NOT WELL WRITTEN
				}
				else
				{
					BitSet rotate = this.XOR(
											this.XOR(
													 this.XOR(word[(t-3)%16], 
															  word[(t-8)%16]), 
													  word[(t-14)%16]), 
											 word[t%16]);
					word[t%16] = this.rotateLEFT(rotate, 1);
				}

				//3. For t=0 to 79
				T = this.addMod2w(
						this.addMod2w(
								this.addMod2w(
										this.addMod2w(this.rotateLEFT(a, 5), 
													  this.f_t(b, c, d, t)),
										e),
								this.k_t(t)),
						word[t%16]);

//				BitSet t1 = this.rotateLEFT(a, 5);
//				BitSet t2 = this.f_t(b, c, d, t);
//				BitSet tsum1 = this.addMod2w(t1, t2);
//				BitSet tsum2 = this.addMod2w(tsum1, e);
//				BitSet tsum3 = this.addMod2w(tsum2, this.k_t(t));
//				BitSet tsum4 = this.addMod2w(tsum3, word[t%16]);
//
//				System.out.println("t = "+t);
//				System.out.println("a                         = "+this.bitset2string(a).substring(0, 32));
//				System.out.println("b                         = "+this.bitset2string(b).substring(0, 32));
//				System.out.println("c                         = "+this.bitset2string(c).substring(0, 32));
//				System.out.println("d                         = "+this.bitset2string(d).substring(0, 32));
//				System.out.println("e                         = "+this.bitset2string(e).substring(0, 32));
//				System.out.println("this.k_t(1)               = "+this.bitset2string(this.k_t(t)).substring(0, 32));
//				System.out.println("word[1]                   = "+this.bitset2string(word[t%16]).substring(0, 32));
//				System.out.println("t1 rot left a <- 5        = "+this.bitset2string(t1).substring(0, 32));
//				System.out.println("t2 t=1 (b, c, d)          = "+this.bitset2string(t2).substring(0, 32));
//				System.out.println("tsum1 (t1+t2)             = "+this.bitset2string(tsum1).substring(0, 32));
//				System.out.println("tsum2 (tsum1, e)          = "+this.bitset2string(tsum2).substring(0, 32));
//				System.out.println("tsum3 (tsum2, this.k_t(t) = "+this.bitset2string(tsum3).substring(0, 32));
//				System.out.println("tsum4 (tsum3, word[t%16]) = "+this.bitset2string(tsum4).substring(0, 32));
//				System.out.println();
				
				e = (BitSet) d.clone();
				d = (BitSet) c.clone();
//				c = this.rotateLEFT(b, 30);
				c = this.rotateRIGHT(b, 2); //Same as above
				b = (BitSet) a.clone();
				a = (BitSet) T.clone();
			}
			
			//4. Compute the i-th intermediate hash value H(i)
			H0 = this.addMod2w(a, H0);
			H1 = this.addMod2w(b, H1);
			H2 = this.addMod2w(c, H2);
			H3 = this.addMod2w(d, H3);
			H4 = this.addMod2w(e, H4);
		}

		String hash0 = this.bitset2string(H0).substring(0, getWord_len());
		String hash1 = this.bitset2string(H1).substring(0, getWord_len());
		String hash2 = this.bitset2string(H2).substring(0, getWord_len());
		String hash3 = this.bitset2string(H3).substring(0, getWord_len());
		String hash4 = this.bitset2string(H4).substring(0, getWord_len());
		
		return hash0 +  hash1 + hash2 + hash3 + hash4;
	}
	

	/**
	 * Computes the sha1 of the message
	 * 
	 * @param m The message to hash
	 * @return The message's hash
	 */
	public String compute(String m)
	{
		return this.compute(this.encodeMessage(m));
	}
	
	
	/**
	 * Switches between the inner sha1 functions based on the value of t
	 * 
	 * @param x Bit set 1
	 * @param y Bit set 2
	 * @param z Bit set 3
	 * @param t The iterator t
	 * @return A specific function according to t
	 */
	private BitSet f_t(BitSet x, BitSet y, BitSet z, int t)
	{
		if(t <= 19)
		{
			return this.Ch(x, y, z);
		}
		
		else if((t >= 20 && t <= 39)
				|| (t >= 60 && t <= 79))
		{
			return this.Parity(x, y, z);
		}
		
		else if(t >= 40 && t <= 59)
		{
			return this.Maj(x, y, z);
		}
		
		else
		{
			//This is really bad :/
			return null;
		}
	}
	
	
	/**
	 * Switches between the inner sha1 constants based on the value of t
	 * 
	 * @param t The iterator t
	 * @return A specific constant according to t
	 */
	private BitSet k_t(int t)
	{

		if(t <= 19)
		{
			return K0_19;
		}
		
		else if(t >= 20 && t <= 39)
		{
			return K20_39;
		}
		
		else if(t >= 40 && t <= 59)
		{
			return K40_59;
		}
		
		else if(t >= 60 && t <= 79)
		{
			return K60_79;
		}
		
		else
		{
			//This is really bad :/
			return null;
		}
	}
	
	
	/**
	 * Performs operations with the bit sets given
	 * To be used as function for 0 <= t <= 19
	 * 
	 * @param x Bit set 1
	 * @param y Bit set 2
	 * @param z Bit set 3
	 * @return (x AND y) XOR (-x AND z)
	 */
	public BitSet Ch(BitSet x, BitSet y, BitSet z)
	{
		BitSet and1 = this.AND(x, y);

		BitSet _x = this.negate(x);
		BitSet and2 = this.AND(_x, z);
		
		
		return this.XOR(and1, and2);
	}
	
	
	/**
	 * Performs operations with the bit sets given
	 * To be used as function for 20 <= t <= 39, and 60 <= t <= 79
	 * 
	 * @param x Bit set 1
	 * @param y Bit set 2
	 * @param z Bit set 3
	 * @return x XOR y XOR z
	 */
	public BitSet Parity(BitSet x, BitSet y, BitSet z)
	{
		return this.XOR(this.XOR(x, y), z);
	}
	
	
	/**
	 * Performs operations with the bit sets given
	 * To be used as function for 40 <= t <= 59
	 * 
	 * @param x Bit set 1
	 * @param y Bit set 2
	 * @param z Bit set 3
	 * @return (x AND y) XOR (x AND z) XOR (y AND z)
	 */
	public BitSet Maj(BitSet x, BitSet y, BitSet z)
	{
		return this.Parity(this.AND(x, y), this.AND(x, z), this.AND(y, z));
	}
}
