package CaseUse;

import java.math.BigInteger;
import RivShaAdle.RSACrypt;

/**
 * Enable testing of encryption and decryption of text using RSA protocol
 */
public class RSAenc
{
	private String message = "Questo è il messaggio originale inviato da Alice a Bob";
	private int modulo = 3072;
	
	
	/**
	 * Encryption and decryption using RSA
	 */
	public void cryptDecrypt()
	{
		RSACrypt rsa = new RSACrypt(this.message, this.modulo);

		System.out.println("Il messaggio da crittografare è: ");
		System.out.println(this.message);
		System.out.println();
		
		BigInteger encrypted = rsa.crypt_message();
		System.out.println("Il messaggio crittografato è: ");
		System.out.println(encrypted);
		System.out.println();
		
		String decrypted = rsa.decrypt_message(encrypted);
		System.out.println("Il messaggio decifrato è: ");
		System.out.println(decrypted);
		System.out.println();
		

		boolean check = rsa.checkCorrectness(decrypted);
		System.out.println("Un test di correttezza risulta: ");
		System.out.println(check);		
		System.out.println();
		
		//Stampa i dettagli sull'RSA utilizzato	
		System.out.println("Dettagli sui parametri di RSA utilizzati");
		rsa.get_rsa();
	}
}
