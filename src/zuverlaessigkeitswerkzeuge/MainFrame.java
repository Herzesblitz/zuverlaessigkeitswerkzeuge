package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
=======
>>>>>>> ce565b43bee7a2386a082da7fe72229c697a8747
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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
<<<<<<< HEAD
public class MainFrame extends JFrame implements KeyListener,ActionListener{
	int posX; int posY;
	//KL Teil
		JTextArea displayArea;
	    JTextField typingArea;
	
=======

public class MainFrame extends JFrame{
	int posX; int posY; char keyTyped;
>>>>>>> ce565b43bee7a2386a082da7fe72229c697a8747
	JCanvas jc = new JCanvas();
	static MainFrame frame=new MainFrame();
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		 // TODO code application logic here
<<<<<<< HEAD
		
		
		frame.init_frame();

		System.out.println("test");		
=======

		frame.init_frame();

		System.out.println("test");
		
>>>>>>> ce565b43bee7a2386a082da7fe72229c697a8747
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
<<<<<<< HEAD
		        
=======
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
>>>>>>> ce565b43bee7a2386a082da7fe72229c697a8747
		
			
			Dimension aufloesung= Toolkit.getDefaultToolkit().getScreenSize();

			//KL
		        typingArea.addKeyListener(this);
		        getContentPane().add(frame);
			
		    frame.setTitle("Zuverlässigkeitswerkzeuge");
		    frame.setSize(aufloesung.width, aufloesung.height);
		    frame.setResizable(false);
		    frame.setLocation(0, 0);
		    frame.setVisible(true);	 frame.pack();
		    	
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("d");

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("b");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("a");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		 //Clear the text components.
        displayArea.setText("");
        typingArea.setText("");
        
        //Return the focus to the typing area.
        typingArea.requestFocusInWindow();		
	}
	
	
		


}
