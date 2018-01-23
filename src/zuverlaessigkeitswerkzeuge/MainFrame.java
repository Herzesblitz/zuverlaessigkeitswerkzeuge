package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//import sun.awt.RepaintArea;

abstract class _2DObject{
	
}

class Block extends _2DObject{
	   int x; int y; int height; int width;
	   Color color;
	   String name="-";
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
		    g2.drawRect(((Block) a).x,((Block) a).y,((Block) a).width,((Block) a).height);
		    g2.drawString(((Block) a).name, ((Block) a).x+((Block) a).width/2 , ((Block) a).y +((Block) a).height/2);
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


	static int posX; static int posY; char keyTyped; static int pressedX; static int pressedY; static boolean realeased=true;

	JCanvas jc = new JCanvas();
	static MainFrame frame=new MainFrame();
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		frame.init_frame();

		System.out.println("test");
		
		frame.test(); 
		frame.zeichneObjekte(frame.jc);
		
		 
	}
	
	public  void init_frame() {
			 addMouseMotionListener(new MouseMotionAdapter() {
		            public void mouseMoved(final MouseEvent e) {
		                posX = e.getX();
		                posY = e.getY();
		                System.out.println("a:"+posX+" "+posY);
		            }
		            
		     });
			 
			 addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					realeased =true;
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					//System.out.println("k."+posX+" "+posY);
		            realeased = false;
		            MainClass.elementVerschieben(posX, posY);
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});

			 addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					System.out.println(e.getKeyChar());
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			Dimension aufloesung= Toolkit.getDefaultToolkit().getScreenSize();

			System.out.println(aufloesung.width+" "+aufloesung.height);
			

			frame.setTitle("Zuverlässigkeitswerkzeuge");
		    frame.setResizable(true);
		    frame.setLocation(0, 0);
		    frame.setVisible(true);	 frame.pack();	
			frame.setSize(aufloesung);

	}
	
	private void test() {
		Color red = Color.red;
    	Color blue = Color.blue;
    	Block b1 = new Block(0, 0, 500, 500,blue);
    	Block b2 = new Block(100, 100, 200, 200,red);
	   
    	jc.zeichnen.add(b1);
	    jc.zeichnen.add(b2);
	}
	
	public static void zeichneObjekte(JCanvas jc) {
//		for(_2DObject d: jc.zeichnen) {
//			if(d instanceof Block)System.out.println(((Block) d).x);
//			if(d instanceof Line)System.out.println(((Line) d).x1);
//		}
		frame.getContentPane().add(jc);
		frame.repaint();
	}


	

}
