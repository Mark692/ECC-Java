package CaseUse;

import java.math.BigInteger;
import java.security.spec.ECPoint;

import Crypto.ECMQV;
import Curve.NIST;

/**
 * Enable testing of encryption and decryption of text using ECMQV protocol
 */
public class CU_ECMQV
{
	
	/**
	 * Encryption and decryption with augmented security functions
	 */
	public static void cryptDecryptAugmented()
	{
		NIST n = new NIST();
		ECMQV e = new ECMQV(n);
		String Message = "Questo è il messaggio originale mandato da Alice";
		BigInteger[] A_privs = e.generatePrivateKeys_d();
		ECPoint[] A_publics = e.generatePublicKeys_P(A_privs);
		BigInteger[] bob_privs = e.generatePrivateKeys_d();
		ECPoint[] B_publics = e.generatePublicKeys_P(bob_privs);
		int MAClen = 300;
		String SharedData = "Stringa casuale con un qualche testo ";
		String SharedData2 = "giusto per aumentare l'entropia.";
		
	
		String crypt = e.encryptAugmented(Message, A_privs, A_publics, B_publics[0], MAClen, SharedData, SharedData2);
		System.out.println("Messaggio crittografato (hex) = "+crypt);

		String ori = e.decryptAugmented(crypt, bob_privs, A_publics, MAClen, SharedData, SharedData2);
		System.out.println("Messaggio decifrato = "+ori);
		
	}
	

	/**
	 * Encryption and decryption with standard security functions
	 */
	public static void cryptDecrypt()
	{
		NIST n = new NIST();
		ECMQV e = new ECMQV(n);
		String Message = "Questo è il messaggio originale mandato da Alice";
		BigInteger[] A_privs = e.generatePrivateKeys_d();
		ECPoint[] A_publics = e.generatePublicKeys_P(A_privs);
		BigInteger[] bob_privs = e.generatePrivateKeys_d();
		ECPoint[] B_publics = e.generatePublicKeys_P(bob_privs);
		String SharedData = "Stringa casuale con un qualche testo ";
		
		String crypt = e.encrypt(Message, A_privs, A_publics, B_publics[0], SharedData);
		System.out.println("Messaggio crittografato (hex) = "+crypt);

		String ori = e.decrypt(crypt, bob_privs, A_publics, SharedData);
		System.out.println("Messaggio decifrato = "+ori);
		
	}

}
