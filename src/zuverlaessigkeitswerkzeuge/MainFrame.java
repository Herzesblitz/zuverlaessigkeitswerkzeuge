package zuverlaessigkeitswerkzeuge;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		 // TODO code application logic here
		MainFrame MF = new MainFrame();
		MF.init_frame();
		MF.initComponents();
	}
	
	private  void init_frame() {
		 MainFrame frame=new MainFrame();
		    frame.setTitle("Zuverlässigkeitswerkzeuge");
		    frame.setSize(1000, 620);
		    frame.setResizable(false);
		    frame.setLocation(50, 50);
		    frame.setVisible(true);		 
	}
	
	private void initComponents() {
		JButton jButton1 = new javax.swing.JButton();
		JButton jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());
 
        jButton1.setText("jButton1");
        getContentPane().add(jButton1);
 
        jButton1.setText("jButton2");
        getContentPane().add(jButton1);
 
     
 
        pack();
    }

}
