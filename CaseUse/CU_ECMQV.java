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
	public static void ecmqv()
	{
		cryptDecrypt();
		System.out.println("\n-------------------------------------\n");
		cryptDecryptAugmented();
	}
	/**
	 * Encryption and decryption with augmented security functions
	 */
	public static void cryptDecryptAugmented()
	{
		System.out.println("ECMQV w/ ECAES\n");
		NIST n = new NIST();
		ECMQV e = new ECMQV(n);
		String Message = "Prova del protocollo ECMQV usando cifratura ECAES";
		BigInteger[] A_privs = e.generatePrivateKeys_d();
		ECPoint[] A_publics = e.generatePublicKeys_P(A_privs);
		BigInteger[] bob_privs = e.generatePrivateKeys_d();
		ECPoint[] B_publics = e.generatePublicKeys_P(bob_privs);
		int MAClen = 300;
		String SharedData = "Random string with some random text ";
		String SharedData2 = "to increase entropy!";

		System.out.println("A calcola le sue chiavi:");
		System.out.println("Privata  statica:  " + A_privs[0]);
		System.out.println("Pubblica statica:  ("+A_publics[0].getAffineX().intValue()+", "+A_publics[0].getAffineY().intValue()+")");
		System.out.println("");
		System.out.println("Privata  effimera: " + A_privs[1]);
		System.out.println("Pubblica effimera: ("+A_publics[1].getAffineX().intValue()+", "+A_publics[1].getAffineY().intValue()+")");
		System.out.println("");
		System.out.println("B calcola le sue chiavi:");
		System.out.println("Privata  statica:  " + bob_privs[0]);
		System.out.println("Pubblica statica:  ("+B_publics[0].getAffineX().intValue()+", "+B_publics[0].getAffineY().intValue()+")");
		System.out.println("");
		System.out.println("Privata  effimera: " + bob_privs[1]);
		System.out.println("Pubblica effimera: ("+B_publics[1].getAffineX().intValue()+", "+B_publics[1].getAffineY().intValue()+")");
		System.out.println("");
		
		ECPoint K_simmA = e.generateSecret_K(A_privs, A_publics, B_publics);
		System.out.println("Chiave simmetrica calcolata da A: ("+K_simmA.getAffineX().intValue()+", "+K_simmA.getAffineY().intValue()+")");
		ECPoint K_simmB = e.generateSecret_K(bob_privs, B_publics, A_publics);
		System.out.println("Chiave simmetrica calcolata da B: ("+K_simmB.getAffineX().intValue()+", "+K_simmB.getAffineY().intValue()+")");
		
		System.out.println("");
		String crypt = e.encryptAugmented(Message, K_simmA, A_publics[1], MAClen, SharedData, SharedData2);
		System.out.println("Messaggio crittografato = "+crypt);

		String ori = e.decryptAugmented(crypt, K_simmB, A_publics[1], MAClen, SharedData, SharedData2);
		System.out.println("Messaggio decifrato = "+ori);
		
	}
	

	/**
	 * Encryption and decryption with standard security functions
	 */
	public static void cryptDecrypt()
	{
		System.out.println("ECMQV w/ ECIES\n");
		NIST n = new NIST();
		ECMQV e = new ECMQV(n);
		String Message = "Prova del protocollo ECMQV usando cifratura ECIES";
		BigInteger[] A_privs = e.generatePrivateKeys_d();
		ECPoint[] A_publics = e.generatePublicKeys_P(A_privs);
		BigInteger[] bob_privs = e.generatePrivateKeys_d();
		ECPoint[] B_publics = e.generatePublicKeys_P(bob_privs);
		String SharedData = "Just a random string...";


		System.out.println("A calcola le sue chiavi:");
		System.out.println("Privata  statica:  " + A_privs[0]);
		System.out.println("Pubblica statica:  ("+A_publics[0].getAffineX().intValue()+", "+A_publics[0].getAffineY().intValue()+")");
		System.out.println("");
		System.out.println("Privata  effimera: " + A_privs[1]);
		System.out.println("Pubblica effimera: ("+A_publics[1].getAffineX().intValue()+", "+A_publics[1].getAffineY().intValue()+")");
		System.out.println("");
		System.out.println("B calcola le sue chiavi:");
		System.out.println("Privata  statica:  " + bob_privs[0]);
		System.out.println("Pubblica statica:  ("+B_publics[0].getAffineX().intValue()+", "+B_publics[0].getAffineY().intValue()+")");
		System.out.println("");
		System.out.println("Privata  effimera: " + bob_privs[1]);
		System.out.println("Pubblica effimera: ("+B_publics[1].getAffineX().intValue()+", "+B_publics[1].getAffineY().intValue()+")");
		System.out.println("");
		
		ECPoint K_simmA = e.generateSecret_K(A_privs, A_publics, B_publics);
		System.out.println("Chiave simmetrica calcolata da A: ("+K_simmA.getAffineX().intValue()+", "+K_simmA.getAffineY().intValue()+")");
		ECPoint K_simmB = e.generateSecret_K(bob_privs, B_publics, A_publics);
		System.out.println("Chiave simmetrica calcolata da B: ("+K_simmB.getAffineX().intValue()+", "+K_simmB.getAffineY().intValue()+")");

		System.out.println("");
		System.out.println("Cifratura e decifratura");
		String crypt = e.encrypt(Message, K_simmA, A_publics[1], SharedData);
		System.out.println("Messaggio cifrato = "+crypt);

		String ori = e.decrypt(crypt, K_simmB, A_publics[1], SharedData);
		System.out.println("Messaggio decifrato = "+ori);
	}
}
