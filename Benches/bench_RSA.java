package Benches;

public class bench_RSA
{
	/**
	 * Performs a benchmark test of RSA
	 * 
	 * @param nBitLen Modulo n's bit length
	 * @param KeyGen Number of cryptographic keys to generate for this test
	 * @return The total time needed to generate the KeyGen keys
	 */
	public long bench_me(int nBitLen, int KeyGen)
	{
		RivShaAdle.RSA rsa = new RivShaAdle.RSA(14, 15);
		long start = System.nanoTime();
		for(int i = 0; i < KeyGen; i++)
		{
			rsa.bench(nBitLen);
		}
		return System.nanoTime() - start;
	}
	
	
	/**
	 * Performs the Benchmark test with a warm up function (depending upon the number of keys to generate) to enhance results
	 * 
	 * @param nBitLen Modulo n's bit length
	 * @param totIter Number of times to repeat this test
	 * @param KeyGen Number of cryptographic keys to generate for this test
	 */
	public void test_me(int nBitLen, int totIter, int KeyGen)
	{
		System.out.println("RSA");
		System.out.println("Rodaggio iniziale per avviare i meccanismi di Cache");
		this.warmUp(nBitLen, KeyGen);
		System.out.println("Fine rodaggio. Inizio del vero test: ");
		
		long startEC = System.nanoTime();
		for(int i = 0; i < totIter; i++)
		{
			System.out.println(this.bench_me(nBitLen, KeyGen));
		}
		long endEC = System.nanoTime() - startEC;
		
		System.out.println("Totale impegato: "+endEC);
		System.out.print("Media: ");
		System.out.printf("%.2f", (float) (endEC / totIter)/1000000000); //Converts nano to seconds
		System.out.println(" secondi");
		System.out.println();
	}
	
	
	/**
	 * Performs an initial warm up "exercise" in order to trigger cache optimisation and more
	 * 
	 * @param nBitLen Modulo n's bit length
	 * @param KeyGen Number of cryptographic keys to generate for this test
	 */
	private void warmUp(int nBitLen, int KeyGen)
	{
		int rodaggio = 2*KeyGen; //Since RSA needs too much time to generate the requested keys, a LITTLE warm up is performed
		this.bench_me(nBitLen, rodaggio);
	}
} 
