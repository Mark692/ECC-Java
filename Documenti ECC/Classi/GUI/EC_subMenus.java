package GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class EC_subMenus
{

	/**
	 * Adds the NIST sub-menu to the containerMenu
	 * 
	 * @param containerMenu The menu which will contain this one
	 * @param function A BasicFun object which will handle most of the functions used
	 * @return The NIST sub-menu
	 */
	public static JMenu add_Nist(JMenu containerMenu, BasicFun function)
	{
		JMenu menuNist = new JMenu("NIST");
		menuNist.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\download.png"));
		containerMenu.add(menuNist);
		
		
		JMenu mnNewMenu = new JMenu("Security Level 80");
		mnNewMenu.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\80.png"));
		menuNist.add(mnNewMenu);

		JPanel panel_NIST80 = function.nistPanel(80);
		JMenuItem p192 = new JMenuItem("P-192");
		p192.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(p192, panel_NIST80);
		mnNewMenu.add(p192);
		
		
		JMenu mnSecurityLevel = new JMenu("Security Level 112");
		mnSecurityLevel.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\112.png"));
		menuNist.add(mnSecurityLevel);
		
		JPanel panel_NIST112 = function.nistPanel(112);
		JMenuItem p224 = new JMenuItem("P-224");
		p224.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(p224, panel_NIST112);
		mnSecurityLevel.add(p224);
		

		JMenu mnSecurityLevel_1 = new JMenu("Security Level 128");
		mnSecurityLevel_1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\128.png"));
		menuNist.add(mnSecurityLevel_1);

		JPanel panel_NIST128 = function.nistPanel(128);
		JMenuItem p256 = new JMenuItem("P-256");
		p256.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(p256, panel_NIST128);
		mnSecurityLevel_1.add(p256);
		
		
		JMenu mnSecurityLevel_2 = new JMenu("Security Level 192");
		mnSecurityLevel_2.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\192.png"));
		menuNist.add(mnSecurityLevel_2);
		
		JPanel panel_NIST192 = function.nistPanel(192);
		JMenuItem p384 = new JMenuItem("P-384");
		p384.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(p384, panel_NIST192);
		mnSecurityLevel_2.add(p384);
		
		
		JMenu mnSecurityLevel_3 = new JMenu("Security Level 256");
		mnSecurityLevel_3.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\256.png"));
		menuNist.add(mnSecurityLevel_3);

		JPanel panel_NIST256 = function.nistPanel(256);
		JMenuItem p521 = new JMenuItem("P-521");
		p521.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(p521, panel_NIST256);
		mnSecurityLevel_3.add(p521);
		
		return menuNist;
	}
	

	/**
	 * Adds the Certicom sub-menu to the containerMenu
	 * 
	 * @param containerMenu The menu which will contain this one
	 * @param function A BasicFun object which will handle most of the functions used
	 * @return The Certicom sub-menu
	 */
	public static JMenu add_Certicom(JMenu containerMenu, BasicFun function)
	{
		JMenu menuCerticom = new JMenu("Certicom");
		menuCerticom.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\certicom 2.png"));
		containerMenu.add(menuCerticom);
		

		JMenu Certicom_SecLev56 = new JMenu("Security Level 56");
		Certicom_SecLev56.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\56 v1.png"));
		menuCerticom.add(Certicom_SecLev56);

		JPanel panel_Certicom56v1 = function.certicomPanel(56, 1);
		JMenuItem secp112r1 = new JMenuItem("secp112r1");
		secp112r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(secp112r1, panel_Certicom56v1);
		Certicom_SecLev56.add(secp112r1);

		JPanel panel_Certicom56v2 = function.certicomPanel(56, 2);
		JMenuItem secp112r2 = new JMenuItem("secp112r2"); // = curve.get_name();
		secp112r2.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(secp112r2, panel_Certicom56v2);
		Certicom_SecLev56.add(secp112r2);
		
		JMenu Certicom_SecLev64 = new JMenu("Security Level 64");
		Certicom_SecLev64.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\64.png"));
		menuCerticom.add(Certicom_SecLev64);

		
		JPanel panel_Certicom64v1 = function.certicomPanel(64, 1);
		JMenuItem secp128r1 = new JMenuItem("secp128r1");
		secp128r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(secp128r1, panel_Certicom64v1);
		Certicom_SecLev64.add(secp128r1);

		JPanel panel_Certicom64v2 = function.certicomPanel(64, 2);
		JMenuItem secp128r2 = new JMenuItem("secp128r2");
		secp128r2.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		function.menuItem_changePanel(secp128r2, panel_Certicom64v2);
		Certicom_SecLev64.add(secp128r2);
		
		JMenu Certicom_SecLev80 = new JMenu("Security Level 80");
		Certicom_SecLev80.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\80.png"));
		menuCerticom.add(Certicom_SecLev80);
		
		JMenuItem secp160k1 = new JMenuItem("secp160k1");
		secp160k1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\koblitz.png"));
		JPanel panel_Certicom80v1 = function.certicomPanel(80, 1);
		function.menuItem_changePanel(secp160k1, panel_Certicom80v1);
		Certicom_SecLev80.add(secp160k1);
		
		JMenuItem secp160r1 = new JMenuItem("secp160r1");
		secp160r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom80v2 = function.certicomPanel(80, 2);
		function.menuItem_changePanel(secp160r1, panel_Certicom80v2);
		Certicom_SecLev80.add(secp160r1);
		
		JMenuItem secp160r2 = new JMenuItem("secp160r2");
		secp160r2.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom80v3 = function.certicomPanel(80, 3);
		function.menuItem_changePanel(secp160r2, panel_Certicom80v3);
		Certicom_SecLev80.add(secp160r2);
		
		JMenu Certicom_SecLev96 = new JMenu("Security Level 96");
		Certicom_SecLev96.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\96.png"));
		menuCerticom.add(Certicom_SecLev96);
		
		JMenuItem secp192k1 = new JMenuItem("secp192k1");
		secp192k1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\koblitz.png"));
		JPanel panel_Certicom96v1 = function.certicomPanel(96, 1);
		function.menuItem_changePanel(secp192k1, panel_Certicom96v1);
		Certicom_SecLev96.add(secp192k1);
		
		JMenuItem secp192r1 = new JMenuItem("secp192r1");
		secp192r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom96v2 = function.certicomPanel(96, 2);
		function.menuItem_changePanel(secp192r1, panel_Certicom96v2);
		Certicom_SecLev96.add(secp192r1);
	
		JMenu Certicom_SecLev112 = new JMenu("Security Level 112");
		Certicom_SecLev112.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\112.png"));
		menuCerticom.add(Certicom_SecLev112);
		
		JMenuItem secp224k1 = new JMenuItem("secp224k1");
		secp224k1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\koblitz.png"));
		JPanel panel_Certicom112v1 = function.certicomPanel(112, 1);
		function.menuItem_changePanel(secp224k1, panel_Certicom112v1);
		Certicom_SecLev112.add(secp224k1);
		
		JMenuItem secp224r1 = new JMenuItem("secp224r1");
		secp224r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom112v2 = function.certicomPanel(112, 2);
		function.menuItem_changePanel(secp224r1, panel_Certicom112v2);
		Certicom_SecLev112.add(secp224r1);
		
		JMenu Certicom_SecLev128 = new JMenu("Security Level 128");
		Certicom_SecLev128.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\128.png"));
		menuCerticom.add(Certicom_SecLev128);
		
		JMenuItem secp256k1 = new JMenuItem("secp256k1");
		secp256k1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\koblitz.png"));
		JPanel panel_Certicom128v1 = function.certicomPanel(128, 1);
		function.menuItem_changePanel(secp256k1, panel_Certicom128v1);
		Certicom_SecLev128.add(secp256k1);
		
		JMenuItem secp256r1 = new JMenuItem("secp256r1");
		secp256r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom128v2 = function.certicomPanel(128, 2);
		function.menuItem_changePanel(secp256r1, panel_Certicom128v2);
		Certicom_SecLev128.add(secp256r1);
		
		JMenu Certicom_SecLev192 = new JMenu("Security Level 192");
		Certicom_SecLev192.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\192.png"));
		menuCerticom.add(Certicom_SecLev192);
		
		JMenuItem secp384r1 = new JMenuItem("secp384r1");
		secp384r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom192v1 = function.certicomPanel(192, 1);
		function.menuItem_changePanel(secp384r1, panel_Certicom192v1);
		Certicom_SecLev192.add(secp384r1);
		
		JMenu Certicom_SecLev256 = new JMenu("Security Level 256");
		Certicom_SecLev256.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\256.png"));
		menuCerticom.add(Certicom_SecLev256);
		
		JMenuItem secp521r1 = new JMenuItem("secp521r1");
		secp521r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_Certicom256v1 = function.certicomPanel(256, 1);
		function.menuItem_changePanel(secp521r1, panel_Certicom256v1);
		Certicom_SecLev256.add(secp521r1);
		
		return menuCerticom;
	}

	
	/**
	 * Adds the Brainpool sub-menu to the containerMenu
	 * 
	 * @param containerMenu The menu which will contain this one
	 * @param function A BasicFun object which will handle most of the functions used
	 * @return The Brainpool sub-menu
	 */
	public static JMenu add_Brainpool(JMenu containerMenu, BasicFun function)
	{
		JMenu menuBrainpool = new JMenu("Brainpool");
		menuBrainpool.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\Brainpool_logo.svg.png"));
		containerMenu.add(menuBrainpool);
		

		JMenu Brainpool_SecLev80 = new JMenu("Security Level 80");
		Brainpool_SecLev80.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\80.png"));
		menuBrainpool.add(Brainpool_SecLev80);
		
		JMenuItem brainpoolP160r1 = new JMenuItem("brainpoolP160r1");
		brainpoolP160r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool80 = function.brainpoolPanel(80);
		function.menuItem_changePanel(brainpoolP160r1, panel_brainpool80);
		Brainpool_SecLev80.add(brainpoolP160r1);
		
		JMenu Brainpool_SecLev96 = new JMenu("Security Level 96");
		Brainpool_SecLev96.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\96.png"));
		menuBrainpool.add(Brainpool_SecLev96);
		
		JMenuItem brainpoolP192r1 = new JMenuItem("brainpoolP192r1");
		brainpoolP192r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool96 = function.brainpoolPanel(96);
		function.menuItem_changePanel(brainpoolP192r1, panel_brainpool96);
		Brainpool_SecLev96.add(brainpoolP192r1);
		
		JMenu Brainpool_SecLev112 = new JMenu("Security Level 112");
		Brainpool_SecLev112.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\112.png"));
		menuBrainpool.add(Brainpool_SecLev112);
		
		JMenuItem brainpoolP224r1 = new JMenuItem("brainpoolP224r1");
		brainpoolP224r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool112 = function.brainpoolPanel(112);
		function.menuItem_changePanel(brainpoolP224r1, panel_brainpool112);
		Brainpool_SecLev112.add(brainpoolP224r1);
		
		JMenu Brainpool_SecLev128 = new JMenu("Security Level 128");
		Brainpool_SecLev128.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\128.png"));
		menuBrainpool.add(Brainpool_SecLev128);
		
		JMenuItem brainpoolP256r1 = new JMenuItem("brainpoolP256r1");
		brainpoolP256r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool128 = function.brainpoolPanel(128);
		function.menuItem_changePanel(brainpoolP256r1, panel_brainpool128);
		Brainpool_SecLev128.add(brainpoolP256r1);
		
		JMenu Brainpool_SecLev160 = new JMenu("Security Level 160");
		Brainpool_SecLev160.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\160.png"));
		menuBrainpool.add(Brainpool_SecLev160);
		
		JMenuItem brainpoolP320r1 = new JMenuItem("brainpoolP320r1");
		brainpoolP320r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool160 = function.brainpoolPanel(160);
		function.menuItem_changePanel(brainpoolP320r1, panel_brainpool160);
		Brainpool_SecLev160.add(brainpoolP320r1);
		
		JMenu Brainpool_SecLev192 = new JMenu("Security Level 192");
		Brainpool_SecLev192.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\192.png"));
		menuBrainpool.add(Brainpool_SecLev192);
		
		JMenuItem brainpoolP384r1 = new JMenuItem("brainpoolP384r1");
		brainpoolP384r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool192 = function.brainpoolPanel(192);
		function.menuItem_changePanel(brainpoolP384r1, panel_brainpool192);
		Brainpool_SecLev192.add(brainpoolP384r1);
		
		JMenu Brainpool_SecLev256 = new JMenu("Security Level 256");
		Brainpool_SecLev256.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\256.png"));
		menuBrainpool.add(Brainpool_SecLev256);
		
		JMenuItem brainpoolP512r1 = new JMenuItem("brainpoolP512r1");
		brainpoolP512r1.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_brainpool256 = function.brainpoolPanel(256);
		function.menuItem_changePanel(brainpoolP512r1, panel_brainpool256);
		Brainpool_SecLev256.add(brainpoolP512r1);
		
		return menuBrainpool;
	}

	
	/**
	 * Adds the Microsoft sub-menu to the containerMenu
	 * 
	 * @param containerMenu The menu which will contain this one
	 * @param function A BasicFun object which will handle most of the functions used
	 * @return The Microsoft sub-menu
	 */
	public static JMenu add_Microsoft(JMenu containerMenu, BasicFun function)
	{
		JMenu menuMicrosoft = new JMenu("Microsoft");
		menuMicrosoft.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\micro and soft.png"));
		containerMenu.add(menuMicrosoft);

		JMenu Microsoft_SecLev80 = new JMenu("Security Level 80");
		Microsoft_SecLev80.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\80.png"));
		menuMicrosoft.add(Microsoft_SecLev80);
		
		JMenuItem m80 = new JMenuItem("m80");
		m80.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		JPanel panel_microsoft80 = function.microsoftPanel();
		function.menuItem_changePanel(m80, panel_microsoft80);
		Microsoft_SecLev80.add(m80);
		
		return menuMicrosoft;
	}

	
//	/**
//	 * Adds the Custom sub-menu to the containerMenu
//	 * 
//	 * @param containerMenu The menu which will contain this one
//	 * @param function A BasicFun object which will handle most of the functions used
//	 * @return The Custom sub-menu
//	 */
//	public static JMenu add_Custom(JMenu containerMenu, BasicFun function)
//	{
//		JMenuItem menuItem_Custom = new JMenuItem("Custom");
//		menuItem_Custom.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\LogoSample_ByTailorBrands.jpg"));
//		menuItem_Custom.setSelectedIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\you.png"));
//
//		JPanel panel_Custom = function.customPanel();
//		function.menuItem_changePanel(menuItem_Custom, panel_Custom);
//		containerMenu.add(menuItem_Custom);
//		
//		return containerMenu;
//	}

	
	/**
	 * Adds the VerifiablyRandom sub-menu to the containerMenu
	 * 
	 * @param containerMenu The menu which will contain this one
	 * @param function A BasicFun object which will handle most of the functions used
	 * @return The VerifiablyRandom sub-menu
	 */
	public static JMenuItem add_VerifiablyRandom(JMenu containerMenu, BasicFun function)
	{

		JMenu menuVerifiablyRandom = new JMenu("Verifiably Random");
		menuVerifiablyRandom.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
		containerMenu.add(menuVerifiablyRandom);
		
		
		JMenuItem mntmNoInputs = new JMenuItem("No inputs");
		menuVerifiablyRandom.add(mntmNoInputs);
		JPanel panel_vr = function.showVRCurve_noInput();
		function.menuItem_changePanel(mntmNoInputs, panel_vr);
		menuVerifiablyRandom.add(mntmNoInputs);
		
//		JMenuItem mntmChooseFieldsP = new JMenuItem("Choose field's p");
//		mntmChooseFieldsP.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\field p.png"));
//		JPanel panel_vr_p = function.vr_form_p();
//		function.menuItem_changePanel(mntmChooseFieldsP, panel_vr_p);
//		menuVerifiablyRandom.add(mntmChooseFieldsP);
		
		
		
//		JMenuItem mntmChooseSecurityLevel = new JMenuItem("Choose Security Level");
//		mntmChooseSecurityLevel.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\Schloss-Sicherheitsstufe-1_img_medium.png"));
//		menuVerifiablyRandom.add(mntmChooseSecurityLevel);



		
		return menuVerifiablyRandom;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}