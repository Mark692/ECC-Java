package GUI;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import Curve.Curve;
import Curve.NIST;

import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JSpinner;
import javax.swing.JList;

public class first
{

	private JFrame frame;
//	private int screenWidth;
//	private int screenHeight;
	JTextArea jta;
private String testo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					first window = new first();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public first()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		frame.setUndecorated(true); //Forces a FULL screen, over the Windows' "Menu Start"
		frame.setVisible(true);
		BasicFun baseFunctions = new BasicFun(frame);
		
//		JPanel baseMenu = function.createMenu();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuEllipticCurves = new JMenu("Elliptic Curves");
		menuEllipticCurves.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\ec desmos 3.png"));
		menuBar.add(menuEllipticCurves);
		
		EC_subMenus.add_Nist(menuEllipticCurves, baseFunctions);
		EC_subMenus.add_Certicom(menuEllipticCurves, baseFunctions);
		EC_subMenus.add_Brainpool(menuEllipticCurves, baseFunctions);
		EC_subMenus.add_Microsoft(menuEllipticCurves, baseFunctions);
		EC_subMenus.add_VerifiablyRandom(menuEllipticCurves, baseFunctions);
//		
////		EC_subMenus.add_Custom(menuEllipticCurves, baseFunctions);
//		
//		
//		//Verifiably Random
//		JMenu menuItem_VerifiablyRandom = new JMenu("Verifiably Random");
//		menuItem_VerifiablyRandom.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\vr2.png"));
//		menuEllipticCurves.add(menuItem_VerifiablyRandom);
////		
////		JMenuItem mntmNoInputs = new JMenuItem("No inputs");
////		menuItem_VerifiablyRandom.add(mntmNoInputs);
////		
////		JMenuItem mntmChooseFieldsP = new JMenuItem("Choose field's p");
////		mntmChooseFieldsP.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\field p.png"));
////		menuItem_VerifiablyRandom.add(mntmChooseFieldsP);
////		
////		JMenuItem mntmChooseSecurityLevel = new JMenuItem("Choose Security Level");
////		mntmChooseSecurityLevel.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\Schloss-Sicherheitsstufe-1_img_medium.png"));
////		menuItem_VerifiablyRandom.add(mntmChooseSecurityLevel);
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		JMenu mnRsa = new JMenu("RSA");
//		mnRsa.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\RSA.png"));
//		menuBar.add(mnRsa);
//		
//		JMenu mnUtilities = new JMenu("Utilities");
//		mnUtilities.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\execute-256.png"));
//		menuBar.add(mnUtilities);
//		
//		JMenuItem mntmGenerateCryptographicKeys = new JMenuItem("Generate Cryptographic Keys");
//		mntmGenerateCryptographicKeys.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\data-key-571156_960_720 (1).png"));
//		mnUtilities.add(mntmGenerateCryptographicKeys);
//		
//		JMenuItem mntmEncryptDecrypt = new JMenuItem("Encrypt / Decrypt messages");
//		mntmEncryptDecrypt.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\encript (2).jpg"));
//		mnUtilities.add(mntmEncryptDecrypt);
//		
//		JMenuItem mntmBenchmarkTests = new JMenuItem("Benchmark tests");
//		mntmBenchmarkTests.setIcon(new ImageIcon("C:\\Users\\MARCO\\Desktop\\ECC\\icons\\Inflação.jpg"));
//		mnUtilities.add(mntmBenchmarkTests);
//		frame.getContentPane().setLayout(null);
		
		
//		jta= new JTextArea(10, 20);
//		frame.getContentPane().add(jta, BorderLayout.WEST);
//		
//		JButton jb = new JButton("Cristo");
//		
//		jb.addActionListener(new ActionListener()
//				{
//					@Override
//					public void actionPerformed(ActionEvent e)
//					{
//						testo = jta.getText();
////						System.out.println(testo);
//						curveParams(testo);
//					}
//
//			
//				});
//		frame.getContentPane().add(jb, BorderLayout.EAST);
		
		
		
		
	}
	

	
//	@Override
//	public void actionPerformed(ActionEvent e)
//	{
//		testo = jta.getText();
//		JOptionPane.showMessageDialog(frame, testo);
//	}
}
