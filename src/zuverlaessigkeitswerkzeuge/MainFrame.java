package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.sun.javafx.geom.ConcentricShapePair;

class JCanvas extends JComponent
{
	
   int x; int y; int height; int width;
   
   public void paintComponent(Graphics g) 
   {
    Graphics2D g2 = (Graphics2D) g;
      super.paintComponent(g);
      g2.setColor(Color.blue);   
      g2.fillRect(x,y,height,width);
   }
 }

public class MainFrame extends JFrame{

	JCanvas jc = new JCanvas();
	static MainFrame frame=new MainFrame();

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		 // TODO code application logic here
		frame.init_frame();
		frame.moveTo(500,0);
	}
	
	private  void init_frame() {
		    frame.setTitle("Zuverlässigkeitswerkzeuge");
		    frame.setSize(1000, 1000);
		    frame.setResizable(false);
		    frame.setLocation(50, 50);
		    frame.setVisible(true);		
		    jc.x = 0; jc.y = 500; jc.height = 100; jc.width = 100;
		    frame.getContentPane().add(jc);
	}
		
	private void moveTo(int x, int y) {
		frame.getContentPane().remove(jc);
		jc.x =x; jc.y = y;
		frame.getContentPane().add(jc);
	}

}
