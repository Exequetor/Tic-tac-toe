import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class GUIGato extends JFrame {
	public static JButton btn00;
	public static JButton btn01;
	public static JButton btn02;
	public static JButton btn10;
	public static JButton btn11;
	public static JButton btn12;
	public static JButton btn20;
	public static JButton btn21;
	public static JButton btn22;
	private static JTextField statusText;
	private static ActionListener cliente;
	private static final JButton DEFAULT_BORDER = new JButton (""); 
	
	public static final Rectangle SCREEN_SIZE = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	/*
	 * Constructor de la interfaz. No se hace visible al instanciar.
	 */
	public GUIGato(ActionListener cliente) {
		setBounds(0, 0, SCREEN_SIZE.width/5, (int)(SCREEN_SIZE.height/2.5));
		setLocation(SCREEN_SIZE.width/2 - getWidth()/2, SCREEN_SIZE.height/2 - getHeight()/2);
		setResizable (false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		GUIGato.cliente = cliente;
		
		btn00 = new JButton("");
		btn00.setFont(new Font("Dialog", Font.BOLD, 19));
		btn00.setBounds(53, 75, 50, 50);
		btn00.addActionListener(cliente);
		getContentPane().add(btn00);
		
		btn01 = new JButton("");
		btn01.setFont(new Font("Dialog", Font.BOLD, 19));
		btn01.setBounds(115, 75, 50, 50);
		btn01.addActionListener(cliente);
		getContentPane().add(btn01);
		
		btn02 = new JButton("");
		btn02.setFont(new Font("Dialog", Font.BOLD, 19));
		btn02.setBounds(177, 75, 50, 50);
		btn02.addActionListener(cliente);
		getContentPane().add(btn02);
		
		btn10 = new JButton("");
		btn10.setFont(new Font("Dialog", Font.BOLD, 19));
		btn10.setBounds(53, 137, 50, 50);
		btn10.addActionListener(cliente);
		getContentPane().add(btn10);
		
		btn11 = new JButton("");
		btn11.setFont(new Font("Dialog", Font.BOLD, 19));
		btn11.setBounds(115, 137, 50, 50);
		btn11.addActionListener(cliente);
		getContentPane().add(btn11);
		
		btn12 = new JButton("");
		btn12.setFont(new Font("Dialog", Font.BOLD, 19));
		btn12.setBounds(177, 137, 50, 50);
		btn12.addActionListener(cliente);
		getContentPane().add(btn12);
		
		btn20 = new JButton("");
		btn20.setFont(new Font("Dialog", Font.BOLD, 19));
		btn20.setBounds(53, 199, 50, 50);
		btn20.addActionListener(cliente);
		getContentPane().add(btn20);
		
		btn21 = new JButton("");
		btn21.setFont(new Font("Dialog", Font.BOLD, 19));
		btn21.setBounds(115, 199, 50, 50);
		btn21.addActionListener(cliente);
		getContentPane().add(btn21);
		
		btn22 = new JButton("");
		btn22.setFont(new Font("Dialog", Font.BOLD, 19));
		btn22.setBounds(177, 199, 50, 50);
		btn22.addActionListener(cliente);
		getContentPane().add(btn22);
		
		statusText = new JTextField();
		statusText.setHorizontalAlignment(SwingConstants.CENTER);
		statusText.setEditable(false);
		statusText.setBackground(UIManager.getColor("Button.background"));
		statusText.setBounds(12, 12, 261, 35);
		getContentPane().add(statusText);
		statusText.setColumns(10);
		setVisible(true);
	}
	
	/*
	 * Modifica el mensaje de estado de la interfaz dandole un color específico
	 */
	public static void setStatus (String message, Color color) {
		statusText.setText (message);
		statusText.setForeground (color);
	}
	
	/*
	 * Modifica el mensaje de estado de la interfaz
	 */
	public static void setStatus (String message) {
		statusText.setText(message);
		statusText.setForeground(Color.black);
	}
	
	/*
	 * Modifica el color del mensaje de estado de la interfaz
	 */
	public static void setStatusColor (Color color) {
		statusText.setForeground(color);
	}
	
	/*
	 * Pone una X con el color especificado en el botón indicado por las coordenadas x,y
	 */
	public static void setX (int x, int y, Color color) {
		setX (x, y);
		setColor (x, y, color);
	}
	
	
	public static void setO (int x, int y, Color color) {
		setO (x, y);
		setColor (x, y, color);
	}
	
	public static void setColor (int x, int y, Color color) {
		int button = x*3 + y;
		switch (button) {
			case 0: btn00.setForeground(color); break;
			case 1: btn01.setForeground(color); break;
			case 2: btn02.setForeground(color); break;
			case 3: btn10.setForeground(color); break;
			case 4: btn11.setForeground(color); break;
			case 5: btn12.setForeground(color); break;
			case 6: btn20.setForeground(color); break;
			case 7: btn21.setForeground(color); break;
			case 8: btn22.setForeground(color); break;
			
		}
	}
	
	public static void setX (int x, int y) {
		int button = x*3 + y;
		switch (button) {
			case 0: btn00.setText("X"); break;
			case 1: btn01.setText("X"); break;
			case 2: btn02.setText("X"); break;
			case 3: btn10.setText("X"); break;
			case 4: btn11.setText("X"); break;
			case 5: btn12.setText("X"); break;
			case 6: btn20.setText("X"); break;
			case 7: btn21.setText("X"); break;
			case 8: btn22.setText("X"); break;
			
		}

	}
	
	public static String getTextButton (int x, int y) {
		int button = x*3 + y;
		String text = "";
		switch (button) {
		case 0: text = btn00.getText(); break;
		case 1: text = btn01.getText(); break;
		case 2: text = btn02.getText(); break;
		case 3: text = btn10.getText(); break;
		case 4: text = btn11.getText(); break;
		case 5: text = btn12.getText(); break;
		case 6: text = btn20.getText(); break;
		case 7: text = btn21.getText(); break;
		case 8: text =  btn22.getText(); break;
		
		}
		return text;
	}
	
	public static void setO (int x, int y) {
		int button = x*3 + y;
		switch (button) {
		case 0: btn00.setText("O"); break;
		case 1: btn01.setText("O"); break;
		case 2: btn02.setText("O"); break;
		case 3: btn10.setText("O"); break;
		case 4: btn11.setText("O"); break;
		case 5: btn12.setText("O"); break;
		case 6: btn20.setText("O"); break;
		case 7: btn21.setText("O"); break;
		case 8: btn22.setText("O"); break;
		
		}
	}
	
	public static void setBlank (int x, int y) {
		int button = x*3 + y;
		switch (button) {
		case 0: btn00.setText(""); break;
		case 1: btn01.setText(""); break;
		case 2: btn02.setText(""); break;
		case 3: btn10.setText(""); break;
		case 4: btn11.setText(""); break;
		case 5: btn12.setText(""); break;
		case 6: btn20.setText(""); break;
		case 7: btn21.setText(""); break;
		case 8: btn22.setText(""); break;
		
		}
	}
	
	public static void setEnabled (int x, int y) {
		int button = x*3 + y;
		switch (button) {
		case 0: GUIGato.btn00.setContentAreaFilled(true);
				GUIGato.btn00.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn00.addActionListener(cliente);
				break;
		case 1: GUIGato.btn01.setContentAreaFilled(true);
				GUIGato.btn01.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn01.addActionListener(cliente);
				break;
		case 2: GUIGato.btn02.setContentAreaFilled(true);
				GUIGato.btn02.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn02.addActionListener(cliente);
				break;
		case 3: GUIGato.btn10.setContentAreaFilled(true);
				GUIGato.btn10.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn10.addActionListener(cliente);
				break;
		case 4: GUIGato.btn11.setContentAreaFilled(true);
				GUIGato.btn11.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn11.addActionListener(cliente);
				break;
		case 5: GUIGato.btn12.setContentAreaFilled(true);
				GUIGato.btn12.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn12.addActionListener(cliente);
				break;
		case 6: GUIGato.btn20.setContentAreaFilled(true);
				GUIGato.btn20.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn20.addActionListener(cliente);
				break;
		case 7: GUIGato.btn21.setContentAreaFilled(true);
				GUIGato.btn21.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn21.addActionListener(cliente);
				break;
		case 8: GUIGato.btn22.setContentAreaFilled(true);
				GUIGato.btn22.setBorder(DEFAULT_BORDER.getBorder());
				GUIGato.btn22.addActionListener(cliente);
				break;
		}
	}
	
	public static void setDisabled (int x, int y) {
		int button = x*3 + y;
		switch (button) {
		case 0: GUIGato.btn00.setContentAreaFilled(false);
				GUIGato.btn00.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn00.removeActionListener(cliente);
				break;
		case 1: GUIGato.btn01.setContentAreaFilled(false);
				GUIGato.btn01.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn01.removeActionListener(cliente);
				break;
		case 2: GUIGato.btn02.setContentAreaFilled(false);
				GUIGato.btn02.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn02.removeActionListener(cliente);
				break;
		case 3: GUIGato.btn10.setContentAreaFilled(false);
				GUIGato.btn10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn10.removeActionListener(cliente);
				break;
		case 4: GUIGato.btn11.setContentAreaFilled(false);
				GUIGato.btn11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn11.removeActionListener(cliente);
				break;
		case 5: GUIGato.btn12.setContentAreaFilled(false);
				GUIGato.btn12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn12.removeActionListener(cliente);
				break;
		case 6: GUIGato.btn20.setContentAreaFilled(false);
				GUIGato.btn20.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn20.removeActionListener(cliente);
				break;
		case 7: GUIGato.btn21.setContentAreaFilled(false);
				GUIGato.btn21.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn21.removeActionListener(cliente);
				break;
		case 8: GUIGato.btn22.setContentAreaFilled(false);
				GUIGato.btn22.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				GUIGato.btn22.removeActionListener(cliente);
				break;
		}
	}
	
}
