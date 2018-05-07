package Benches;

import Curve.Curve;

public class bench_ECDH
{
	private long bench_me(Curve c, int KeyGen)
	{
		Crypto.ECDH e = new Crypto.ECDH(c);
		long start = System.nanoTime();
		for(int i = 0; i < KeyGen; i++)
		{
			e.generatePublicKey_P(e.generatePrivateKey_d());
		}
		return System.nanoTime() - start;
	}
	
	public void test_me(Curve c, int totIter, int KeyGen)
	{
		System.out.println("Curva "+c.get_name()+", proprietario: "+c.get_author());
		System.out.println("Rodaggio iniziale per avviare i meccanismi di Cache");
		this.rodaggio(c, KeyGen);
		System.out.println("Fine rodaggio. Inizio del vero test: ");
		
		long startEC = System.nanoTime();
		for(int i = 0; i < totIter; i++)
		{
			System.out.println(this.bench_me(c, KeyGen));
		}
		long endEC = System.nanoTime() - startEC;
		
		System.out.println("Totale impegato: "+endEC);
		System.out.print("Media: ");
		System.out.printf("%.2f", (float) (endEC / totIter)/1000000000); //Converts nano to seconds
		System.out.println(" secondi");
		System.out.println();
	}
	
	private void rodaggio(Curve c, int KeyGen)
	{
		int rodaggio = 5*KeyGen;
		this.bench_me(c, rodaggio);
	}
}
