package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;


//import sun.awt.RepaintArea;

abstract class _2DObject{
	
}

class Block extends _2DObject{
	   int x; int y; int height; int width;
	   Color color;
	   Block(int x, int y, int height, int width, Color color){
		   this.x = x; this.y = y; this.height = height; this.width = width;
		   this.color = color;
	   };
	   boolean painted;
}

class Line extends _2DObject{
	int x1; int y1; int x2; int y2;
	Color color;
	Line(int x1, int y1, int x2, int y2, Color color){
		   this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
		   this.color = color;
	};
	boolean painted;
}

class JCanvas extends JComponent
{
   ArrayList<_2DObject> zeichnen = new ArrayList<>();
   
   public void paintComponent(Graphics g) 
   {
	   
	  for(_2DObject a: zeichnen) {
		  System.out.println(a.toString());
		  if(a instanceof Block) {  
			Graphics2D g2 = (Graphics2D) g;
		    super.paintComponent(g);
		    g2.setColor(((Block) a).color);   
		    g2.fillRect(((Block) a).x,((Block) a).y,((Block) a).width,((Block) a).height);
		    continue;
		  }
		  if(a instanceof Line) {  
				Graphics2D g2 = (Graphics2D) g;
			    super.paintComponent(g);
			    g2.setColor(((Line) a).color);   
			    g2.drawLine(((Line) a).x1, ((Line) a).y1, ((Line) a).x2, ((Line) a).y2); 
			    continue;
		  }
		  
	  }
	  
	  
     }
   
   
 }

public class MainFrame extends JFrame{
	int posX; int posY;
	JCanvas jc = new JCanvas();
	static MainFrame frame=new MainFrame();
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		 // TODO code application logic here
<<<<<<< HEAD
=======
<<<<<<< HEAD
		frame.init_frame();

=======
		System.out.println("test");
>>>>>>> f7837798a78a8944b7d57e295f4b5dacbbdd731c
		frame.init_frame(600,800);
>>>>>>> 6909cd9a9b403103f2ace5e2e02e3eb78f36558b
		
		frame.test(); 
		frame.zeichneObjekte(frame.jc);
	}
	
	public  void init_frame() {
			 addMouseMotionListener(new MouseMotionAdapter() {
		            public void mouseMoved(final MouseEvent e) {
		                posX = e.getX();
		                posY = e.getY();
		                //System.out.println("a:"+posX+" "+posY);
		            }
		     });
		
		    Dimension aufloesung= Toolkit.getDefaultToolkit().getScreenSize();

		    frame.setTitle("Zuverlässigkeitswerkzeuge");
		    frame.setSize(aufloesung.width, aufloesung.height);
		    frame.setResizable(false);
		    frame.setLocation(0, 0);
		    frame.setVisible(true);	
		    	
	}
	
	private void test() {
		Color red = Color.red;
    	Color blue = Color.blue;
    	Block b1 = new Block(0, 0, 500, 500,blue);
    	Block b2 = new Block(100, 100, 200, 200,red);
	   
    	jc.zeichnen.add(b1);
	    jc.zeichnen.add(b2);
	}
	
	public void zeichneObjekte(JCanvas jc) {
//		for(_2DObject d: jc.zeichnen) {
//			if(d instanceof Block)System.out.println(((Block) d).x);
//			if(d instanceof Line)System.out.println(((Line) d).x1);
//		}
		frame.getContentPane().add(jc);
		frame.repaint();
	}
	
	
		


}
