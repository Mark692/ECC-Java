package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.security.spec.ECPoint;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Curve.Brainpool;
import Curve.Certicom;
import Curve.Curve;
import Curve.Custom;
import Curve.Microsoft;
import Curve.NIST;
import Curve.VRandom;

public class BasicFun
{
	private JFrame f;
	private int screenWidth;
	private int screenHeight;
	private String _authority_ = "";
	private String _name_ = "";
	private int _securityLevel_ = 0;
	private BigInteger _p_ = BigInteger.ONE;
	private BigInteger _a_ = BigInteger.ONE;
	private BigInteger _b_ = BigInteger.ONE;
	private BigInteger _x_ = BigInteger.ONE;
	private BigInteger _y_ = BigInteger.ONE;
	private BigInteger _n_ = BigInteger.ONE;
	private int _h_ = 1;
	
	public Curve c;
	
	/**
	 * Instantiates this class via the GUI frame
	 * @param frame The GUI frame
	 */
	public BasicFun(JFrame frame)
	{
		f = frame;
		
		int[] wh = BasicFun.screenSizes();
		screenWidth = wh[0];
		screenHeight = wh[1];
	}
	
	
	/**
	 * Creates the menu with Absolute layout, fixed width and height
	 * 
	 * @return The menu 
	 */
	public JPanel createMenu()
	{
		JPanel baseMenu = new JPanel();
		baseMenu.setVisible(true);
		baseMenu.setLayout(null);
		baseMenu.setBounds(0, 0, screenWidth, screenHeight);
		f.getContentPane().add(baseMenu, BorderLayout.CENTER);
		
		return baseMenu;
	}
	
	
	/**
	 * Generates a panel to show NIST curves
	 * 
	 * @param secLev Security Level for the curve to show
	 * @return The panel according to the selected curve
	 */
	public JPanel nistPanel(int secLev)
	{
		Curve c = new NIST(secLev);
		return this.genericCurvePanel(c);
	}
	
	
	/**
	 * Generates a panel to show Certicom curves
	 * 
	 * @param secLev Security Level for the curve to show
	 * @param version The protocol version to get
	 * @return The panel according to the selected curve
	 */
	public JPanel certicomPanel(int secLev, int version)
	{
		Curve c = new Certicom(secLev, version);
		return this.genericCurvePanel(c);
	}
	
	
	/**
	 * Generates a panel to show Brainpool curves
	 * 
	 * @param secLev Security Level for the curve to show
	 * @return The panel according to the selected curve
	 */
	public JPanel brainpoolPanel(int secLev)
	{
		Curve c = new Brainpool(secLev);
		return this.genericCurvePanel(c);
	}
	
	
	/**
	 * Generates a panel to show Microsoft curves
	 * 
	 * @return The panel according to the selected curve
	 */
	public JPanel microsoftPanel()
	{
		Curve c = new Microsoft();
		return this.genericCurvePanel(c);
	}
	
	
//	/**
//	 * Generates a panel to show Custom curves
//	 * 
//	 * @return A panel to compile in order to create a custom curve
//	 */
//	public JPanel customPanel()
//	{
//		Curve c = new Custom();
//		boolean editTextAreas = true;
//		JPanel customPanel = this.genericCurvePanel(c, editTextAreas);
//		this.addCustomGeneration(customPanel);
//		
//		return customPanel;
//	}
	
	
	/**
	 * Generates a VR curve
	 */
	public JPanel showVRCurve_noInput()
	{
		_authority_ = "Me";
		_name_ = "My VR Curve";
		
		Curve c = new VRandom(_authority_, _name_);
		return this.printVRParams(c, false);
	}
	
	
//	/**
//	 * Generates a VR curve
//	 */
//	public JPanel showVRCurve_p(BigInteger p)
//	{
//		JPanel panelTarget = new JPanel();
//		panelTarget.setLayout(null);
//		panelTarget.setBounds(0, 0, screenWidth, screenHeight);
//		panelTarget.setVisible(false);
//		f.getContentPane().add(panelTarget);
//		
//		_authority_ = "Me";
//		_name_ = "My VR Curve";
//		
//		Curve c = new VRandom(_authority_, _name_, p);
//		this.printVRParams(c, panelTarget, false);
//		panelTarget.setBounds(0, 0, screenWidth, screenHeight);
//		panelTarget.setVisible(false);
//		f.getContentPane().add(panelTarget);
//		
//		return panelTarget;
//	}
	
	
//	public JPanel vr_form_p()
//	{
//		JPanel formPanel = new JPanel();
//		formPanel.setLayout(null);
//		formPanel.setBounds(0, 0, screenWidth, screenHeight);
//		formPanel.setVisible(false);
//		f.getContentPane().add(formPanel);
//		
//		String pSelection = "Insert a prime number p here: ";
//		int len = this.getTextLength(pSelection);
//		JTextArea authority = new JTextArea();
//		authority.setEditable(false);
//		authority.setText(pSelection);
//		authority.setBounds(
//				(int) (screenWidth * 0.25), //Initial x
//				(int) (screenHeight * 0.3), //Initial y 
//				len, //Max width
//				(int) (screenHeight * 0.025) //Max height
//				);
//		formPanel.add(authority);
//		
//		len = (int) (len + (screenWidth * 0.31)); //Spacing between text areas
//		JTextArea pVAL = new JTextArea();
//		pVAL.setEditable(true);
//		pVAL.setLineWrap(true);
//		pVAL.setText("");
//		pVAL.setBounds(
//				len, //Initial x
//				(int) (screenHeight * 0.3), //Initial y 
//				(int) (screenWidth * 0.15), //Max width
//				(int) (screenHeight * 0.025) //Max height
//				);
//		formPanel.add(pVAL);
//		
//		
//		String text = "Generate this curve!";
//		int textLen = this.getTextLength(text);
//		JButton generateThisCurve = new JButton(text);
//		generateThisCurve.setBounds(
//				(int) (screenWidth * 0.35), 
//				(int) (screenHeight * 0.45), 
//				textLen + (int) (screenWidth * 0.1), 
//				(int) (screenHeight * 0.07)
//				);
//		generateThisCurve.addActionListener(new getText(pVAL));
//		generateThisCurve.addActionListener(new change_Panel(printVRParams(c, false)));
//		formPanel.add(generateThisCurve);
//
//		return formPanel;
//		
////		_authority_ = "Me";
////		_name_ = "My VR Curve";
////		
////		System.out.println("pVAL.getText() = "+pVAL.getText());
////		
////		Curve c = new VRandom(_authority_, _name_, new BigInteger(pVAL.getText()));
////		this.printVRParams(c, panelTarget2, false);
////		panelTarget2.setBounds(0, 0, screenWidth, screenHeight);
////		panelTarget2.setVisible(false);
////		f.getContentPane().add(panelTarget2);
////		
////		return panelTarget2;
//		
////		this.VRCurveParams(generateThisCurve);
////
////		vrPanel.add(generateThisCurve);
//		
//		
//	}
//	
//	
//	/**
//	 * Generates a VR curve
//	 */
//	public JPanel showVRCurve_L(int L)
//	{
//		JPanel panelTarget = new JPanel();
//		panelTarget.setLayout(null);
//		panelTarget.setBounds(0, 0, screenWidth, screenHeight);
//		panelTarget.setVisible(false);
//		f.getContentPane().add(panelTarget);
//		
//		_authority_ = "Me";
//		_name_ = "My VR Curve";
//		
//		Curve c = new VRandom(_authority_, _name_, L);
//		this.printVRParams(c, panelTarget, false);
//		panelTarget.setBounds(0, 0, screenWidth, screenHeight);
//		panelTarget.setVisible(false);
//		f.getContentPane().add(panelTarget);
//		
//		return panelTarget;
//	}
	
	
//	/**
//	 * Enables to check the VR parameters
//	 * 
//	 * @param vrPanel The VR curve's panel
//	 */
//	private void addGenerateButton(JPanel vrPanel)
//	{
//		String text = "Generate this curve!";
//		int textLen = this.getTextLength(text);
//		JButton generateThisCurve = new JButton(text);
//		generateThisCurve.setBounds(
//				(int) (screenWidth * 0.3), 
//				(int) (screenHeight * 0.45), 
//				textLen + (int) (screenWidth * 0.1), 
//				(int) (screenHeight * 0.07)
//				);
//		this.VRCurveParams(generateThisCurve);
//
//		vrPanel.add(generateThisCurve);
//		
//	}
	
	
//	/**
//	 * Generates a VR curve
//	 * @param button
//	 * @return 
//	 */
//	private JPanel VRCurveParams(JButton button)
//	{
//		JPanel validatedPanel = new JPanel();
//		validatedPanel.setLayout(null);
//
//		_authority_ = "Me";
//		_name_ = "My VR Curve";
//		
//		Curve c = new VRandom(_authority_, _name_);
//		this.printVRParams(c, validatedPanel, false);
//		validatedPanel.setBounds(0, 0, screenWidth, screenHeight);
//		validatedPanel.setVisible(false);
//		f.getContentPane().add(validatedPanel);
//		
//		this.button_changePanel(button, validatedPanel);
//		return validatedPanel;
//	}
	
	
	/**
	 * Generates a panel to SHOW ONLY elliptic curves
	 * 
	 * @param c The curve to show
	 * @return The panel according to the selected curve
	 */
	private JPanel genericCurvePanel(Curve c)
	{
		return this.genericCurvePanel(c, false);
	}
	
	
	/**
	 * Generates a panel to show elliptic curves
	 * 
	 * @param c The curve to show
	 * @param editableTextArea Whether the text areas to display have to be editable or not
	 * @return The panel according to the selected curve
	 */
	private JPanel genericCurvePanel(Curve c, boolean editableTextArea)
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		this.printCurveParams(c, panel, editableTextArea);
		panel.setBounds(0, 0, screenWidth, screenHeight);
		panel.setVisible(false);
		f.getContentPane().add(panel);
		
		return panel;
	}
	
	
//	/**
//	 * Enables to generate a custom curve with the given parameters
//	 * 
//	 * @param customPanel The custom curve's panel
//	 */
//	private void addCustomGeneration(JPanel customPanel)
//	{
//		String text = "Generate this curve!";
//		int textLen = this.getTextLength(text);
//		JButton generateThisCurve = new JButton("Generate this curve!");
//		generateThisCurve.setBounds(
//				(int) (screenWidth * 0.5), 
//				(int) (screenHeight * 0.7), 
//				textLen + (int) (screenWidth * 0.1), 
//				(int) (screenHeight * 0.07)
//				);
//		this.validateCustomCurve(generateThisCurve);
//		
//		customPanel.add(generateThisCurve);
//	}
//	
//	
//	private void validateCustomCurve(JButton button)
//	{
//		JPanel validatedPanel = new JPanel();
//		validatedPanel.setLayout(null);
//		ECPoint G = ECPoint.POINT_INFINITY;
//		if(_x_ != null && _y_ != null)
//		{
//			G = new ECPoint(_x_, _y_);
//		}
//		
//		Curve c = new Custom(_authority_, _name_, _a_, _b_, _p_, G, _n_, _h_, _securityLevel_);
//		this.printCurveParams(c, validatedPanel, false);
//		validatedPanel.setBounds(0, 0, screenWidth, screenHeight);
//		validatedPanel.setVisible(false);
////		f.getContentPane().add(validatedPanel);
//		
//		this.button_changePanel(button, validatedPanel);
//	}
	
	
	/**
	 * Enables a button to change panel
	 * 
	 * @param button The button which will change the panel
	 * @param p The panel to show
	 */
	public void button_changePanel(JButton button, JPanel p)
	{
		button.addActionListener(new change_Panel(p));
	}
	
	
	/**
	 * Enables a button to change panel
	 * 
	 * @param button The button which will change the panel
	 * @param p The panel to show
	 */
	public void menuItem_changePanel(JMenuItem button, JPanel p)
	{
		button.addActionListener(new change_Panel(p));
	}
	

	/**
	 * This CLASS enables to change panel
	 */
	private class change_Panel implements ActionListener 
	{
	    private JPanel panel;
	    private change_Panel(JPanel p) 
	    {
	        this.panel = p;
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {
	        switchToPanel(panel);
	    }
	}
	
	/**
	 * and this other thing actually does the trick. It changes the panel to show
	 * 
	 * @param panel The panel to show
	 */
	private void switchToPanel(JPanel panel) 
	{
	    f.getContentPane().removeAll();
	    f.getContentPane().add(panel, BorderLayout.CENTER);
	    f.getContentPane().doLayout();
	    f.update(f.getGraphics());
	    panel.setVisible(true);
	}
	
//	
//	/**
//	 * This CLASS enables to retrieve texts from JTextAreas
//	 */
//	private class getText implements ActionListener 
//	{
//		private JTextArea testo;
//	    private getText(JTextArea t1) 
//	    {
//	        this.testo = t1;
//	    }
//	    
//	    @Override
//	    public void actionPerformed(ActionEvent e) 
//	    {
//	        System.out.println("La mia classe = "+testo.getText());
//
//			_authority_ = "Me";
//			_name_ = "My VR Curve";
//			c = new VRandom(_authority_, _name_, new BigInteger(testo.getText()));
//			switchToPanel(printVRParams(c, false));
//	    }
//	}
	
	
	/**
	 * Returns the main screen sizes in pixels. 
	 * [0] = width, [1] = height
	 * 
	 * @return The screen's sizes in pixels
	 */
	public static int[] screenSizes()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new int[] {(int) screenSize.getWidth(), (int) screenSize.getHeight()};
	}
	
	
	/**
	 * Generates text areas for the defined panel in order to show and edit the curve's parameters
	 * 
	 * @param c The curve to show
	 * @param panelTarget The next panel to show
	 * @param editableTextArea Whether to display editable text areas or not
	 */
	private void printCurveParams(Curve c, JPanel panelTarget, boolean editableTextArea)
	{
		String authorityText = "Authority / Creator: ";
		int len = this.getTextLength(authorityText);
		JTextArea authority = new JTextArea();
		authority.setEditable(false);
		authority.setText(authorityText);
		authority.setBounds(
				(int) (screenWidth * 0.05), //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				len, //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(authority);
		
		len = (int) (len + (screenWidth * 0.06)); //Base spacing between texts
		JTextArea authorityVALUE = new JTextArea();
		authorityVALUE.setEditable(editableTextArea);
		authorityVALUE.setLineWrap(true);
		_authority_ = c.get_author();
		authorityVALUE.setText(_authority_);
		authorityVALUE.setBounds(
				len, //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				(int) (screenWidth * 0.15), //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(authorityVALUE);


		len = (int) (len + (screenWidth * 0.15) + (screenWidth * 0.06)); 
		String nameText = "Curve's name: ";
		int name_len = this.getTextLength(nameText);
		JTextArea name = new JTextArea();
		name.setEditable(false);
		name.setText(nameText);
		name.setBounds(
				len, //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				name_len, //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(name);
		

		len = (int) (len + name_len + (screenWidth * 0.01));
		JTextArea nameVALUE = new JTextArea();
		nameVALUE.setEditable(editableTextArea);
		nameVALUE.setLineWrap(true);
		_name_ = c.get_name();
		nameVALUE.setText(_name_);
		nameVALUE.setBounds(
				len, //Initial x. MUST consider the "Initial x" and "Max width" of AUTHORITY text!
				(int) (screenHeight * 0.03), //Initial y. Same as Authority
				(int) (screenWidth * 0.15), //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(nameVALUE);


		len = (int) (len + (screenWidth * 0.15) + (screenWidth * 0.06)); 
		String securityText = "Security Level = ";
		int secLev_len = this.getTextLength(securityText);
		JTextArea SecLev = new JTextArea();
		SecLev.setEditable(false);
		SecLev.setText(securityText);
		SecLev.setBounds(
				len, //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				secLev_len, //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(SecLev);
		
		
		len = (int) (len + secLev_len + (screenWidth * 0.01));
		JTextArea secLev_VALUE = new JTextArea();
		secLev_VALUE.setEditable(editableTextArea);
		_securityLevel_ = c.get_L();
		secLev_VALUE.setText(Integer.toString(_securityLevel_));
		secLev_VALUE.setBounds(
				len, //Initial x. MUST consider the "Initial x" and "Max width" of NAME text!
				(int) (screenHeight * 0.03), //Initial y. Same as Authority
				(int) (screenWidth * 0.05), //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(secLev_VALUE);
		
		

		int widthOneLetter = this.getTextLength("p = ");
		int startingPoint = (int) (screenWidth * 0.05);
		
		int x = startingPoint + widthOneLetter + (int) (screenWidth * 0.01);
		int maxWidth = (int) (screenWidth * 0.7);
		int maxHeight = (int) (screenHeight * 0.07);
		int initialHeight = (int) (screenHeight * 0.10);
		int incrementHeight =  maxHeight + (int) (screenHeight * 0.03);

		JTextArea p = new JTextArea();
		p.setEditable(editableTextArea);
		p.setLineWrap(true);
		p.setText("p = ");
		p.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(p);
		
		JTextArea pVAL = new JTextArea();
		pVAL.setEditable(editableTextArea);
		pVAL.setLineWrap(true);
		_p_ = c.get_p();
		pVAL.setText(_p_.toString());
		pVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(pVAL);

		
		initialHeight += incrementHeight;
		JTextArea a = new JTextArea();
		a.setEditable(editableTextArea);
		a.setLineWrap(true);
		a.setText("a = ");
		a.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(a);
		
		JTextArea aVAL = new JTextArea();
		aVAL.setEditable(editableTextArea);
		aVAL.setLineWrap(true);
		_a_ = c.get_a();
		aVAL.setText(_a_.toString());
		aVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(aVAL);

		initialHeight += incrementHeight;
		JTextArea b = new JTextArea();
		b.setEditable(editableTextArea);
		b.setLineWrap(true);
		b.setText("b = ");
		b.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(b);
		
		JTextArea bVAL = new JTextArea();
		bVAL.setEditable(editableTextArea);
		bVAL.setLineWrap(true);
		_b_ = c.get_b();
		bVAL.setText(_b_.toString());
		bVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(bVAL);

		initialHeight += incrementHeight;
		JTextArea x_G = new JTextArea();
		x_G.setEditable(editableTextArea);
		x_G.setLineWrap(true);
		x_G.setText("x = ");
		x_G.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(x_G);
		
		JTextArea x_G_VAL = new JTextArea();
		x_G_VAL.setEditable(editableTextArea);
		x_G_VAL.setLineWrap(true);
		_x_ = c.get_G().getAffineX();
		if(_x_ == null)
		{
			x_G_VAL.setText("");
		}
		else
		{
			x_G_VAL.setText(_x_.toString());
		}
		x_G_VAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(x_G_VAL);

		initialHeight += incrementHeight;
		JTextArea y_G = new JTextArea();
		y_G.setEditable(editableTextArea);
		y_G.setLineWrap(true);
		y_G.setText("y = ");
		y_G.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(y_G);
		
		JTextArea y_G_VAL = new JTextArea();
		y_G_VAL.setEditable(editableTextArea);
		y_G_VAL.setLineWrap(true);
		_y_ = c.get_G().getAffineY();
		if(_y_ == null)
		{
			y_G_VAL.setText("");
		}
		else
		{
			y_G_VAL.setText(_y_.toString());
		}
		y_G_VAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(y_G_VAL);

		initialHeight += incrementHeight;
		JTextArea n = new JTextArea();
		n.setEditable(editableTextArea);
		n.setLineWrap(true);
		n.setText("n = ");
		n.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(n);
		
		JTextArea nVAL = new JTextArea();
		nVAL.setEditable(editableTextArea);
		nVAL.setLineWrap(true);
		_n_ = c.get_n();
		nVAL.setText(_n_.toString());
		nVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(nVAL);

		
		
		int specialWidth = (int) (screenWidth * 0.05);
		
		initialHeight += incrementHeight;
		JTextArea h = new JTextArea();
		h.setEditable(editableTextArea);
		h.setLineWrap(true);
		h.setText("h = ");
		h.setBounds(startingPoint, initialHeight, widthOneLetter, (int) (screenHeight * 0.025));
		panelTarget.add(h);
		
		JTextArea hVAL = new JTextArea();
		hVAL.setEditable(editableTextArea);
		_h_ = c.get_h();
		hVAL.setText(Integer.toString(_h_));
		hVAL.setBounds(x, initialHeight, specialWidth, (int) (screenHeight * 0.025));
		panelTarget.add(hVAL);
	}
	
	
	/**
	 * Generates text areas for the defined panel in order to show the VR curve's parameters
	 * 
	 * @param c The VR curve to show
	 * @param editableTextArea Whether to display editable text areas or not
	 * @return The new panel showing the curve's parameters
	 */
	private JPanel printVRParams(Curve c, boolean editableTextArea)
	{
		JPanel panelTarget = new JPanel();
		panelTarget.setLayout(null);
		panelTarget.setBounds(0, 0, screenWidth, screenHeight);
		panelTarget.setVisible(false);
		f.getContentPane().add(panelTarget);
		
		String authorityText = "Authority / Creator: ";
		int len = this.getTextLength(authorityText);
		JTextArea authority = new JTextArea();
		authority.setEditable(false);
		authority.setText(authorityText);
		authority.setBounds(
				(int) (screenWidth * 0.05), //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				len, //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(authority);
		
		len = (int) (len + (screenWidth * 0.06)); //Base spacing between texts
		JTextArea authorityVALUE = new JTextArea();
		authorityVALUE.setEditable(editableTextArea);
		authorityVALUE.setLineWrap(true);
		_authority_ = c.get_author();
		authorityVALUE.setText(_authority_);
		authorityVALUE.setBounds(
				len, //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				(int) (screenWidth * 0.15), //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(authorityVALUE);


		len = (int) (len + (screenWidth * 0.15) + (screenWidth * 0.06)); 
		String nameText = "Curve's name: ";
		int name_len = this.getTextLength(nameText);
		JTextArea name = new JTextArea();
		name.setEditable(false);
		name.setText(nameText);
		name.setBounds(
				len, //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				name_len, //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(name);
		

		len = (int) (len + name_len + (screenWidth * 0.01));
		JTextArea nameVALUE = new JTextArea();
		nameVALUE.setEditable(editableTextArea);
		nameVALUE.setLineWrap(true);
		_name_ = c.get_name();
		nameVALUE.setText(_name_);
		nameVALUE.setBounds(
				len, //Initial x. MUST consider the "Initial x" and "Max width" of AUTHORITY text!
				(int) (screenHeight * 0.03), //Initial y. Same as Authority
				(int) (screenWidth * 0.15), //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(nameVALUE);


		len = (int) (len + (screenWidth * 0.15) + (screenWidth * 0.06)); 
		String securityText = "Security Level = ";
		int secLev_len = this.getTextLength(securityText);
		JTextArea SecLev = new JTextArea();
		SecLev.setEditable(false);
		SecLev.setText(securityText);
		SecLev.setBounds(
				len, //Initial x
				(int) (screenHeight * 0.03), //Initial y 
				secLev_len, //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(SecLev);
		
		
		len = (int) (len + secLev_len + (screenWidth * 0.01));
		JTextArea secLev_VALUE = new JTextArea();
		secLev_VALUE.setEditable(editableTextArea);
		_securityLevel_ = c.get_L();
		secLev_VALUE.setText(Integer.toString(_securityLevel_));
		secLev_VALUE.setBounds(
				len, //Initial x. MUST consider the "Initial x" and "Max width" of NAME text!
				(int) (screenHeight * 0.03), //Initial y. Same as Authority
				(int) (screenWidth * 0.05), //Max width
				(int) (screenHeight * 0.025) //Max height
				);
		panelTarget.add(secLev_VALUE);
		
		

		int widthOneLetter = this.getTextLength("p = ");
		int startingPoint = (int) (screenWidth * 0.05);
		
		int x = startingPoint + widthOneLetter + (int) (screenWidth * 0.01);
		int maxWidth = (int) (screenWidth * 0.7);
		int maxHeight = (int) (screenHeight * 0.07);
		int initialHeight = (int) (screenHeight * 0.10);
		int incrementHeight =  maxHeight + (int) (screenHeight * 0.03);

		JTextArea p = new JTextArea();
		p.setEditable(editableTextArea);
		p.setLineWrap(true);
		p.setText("p = ");
		p.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(p);
		
		JTextArea pVAL = new JTextArea();
		pVAL.setEditable(editableTextArea);
		pVAL.setLineWrap(true);
		_p_ = c.get_p();
		pVAL.setText(_p_.toString());
		pVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(pVAL);

		
		initialHeight += incrementHeight;
		JTextArea a = new JTextArea();
		a.setEditable(editableTextArea);
		a.setLineWrap(true);
		a.setText("a = ");
		a.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(a);
		
		JTextArea aVAL = new JTextArea();
		aVAL.setEditable(editableTextArea);
		aVAL.setLineWrap(true);
		_a_ = c.get_a();
		aVAL.setText(_a_.toString());
		aVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(aVAL);

		initialHeight += incrementHeight;
		JTextArea b = new JTextArea();
		b.setEditable(editableTextArea);
		b.setLineWrap(true);
		b.setText("b = ");
		b.setBounds(startingPoint, initialHeight, widthOneLetter, maxHeight);
		panelTarget.add(b);
		
		JTextArea bVAL = new JTextArea();
		bVAL.setEditable(editableTextArea);
		bVAL.setLineWrap(true);
		_b_ = c.get_b();
		bVAL.setText(_b_.toString());
		bVAL.setBounds(x, initialHeight, maxWidth, maxHeight);
		panelTarget.add(bVAL);
		
		return panelTarget;
	}
	
	
	/**
	 * Calculates the length (in pixels) of a text string
	 * 
	 * @param text The text to calculate the width
	 * @return The text's width
	 */
	private int getTextLength(String text)
	{
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		FontMetrics fm = g2d.getFontMetrics();
		g2d.dispose();
		return fm.stringWidth(text);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
