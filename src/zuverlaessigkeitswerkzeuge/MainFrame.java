package zuverlaessigkeitswerkzeuge;

import java.awt.BorderLayout;
//TODO Strukturen bekommen Darstellung: Umrandung der Elemente, Name an Umrandung, ggf. MTTF etc.
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import sun.reflect.annotation.AnnotatedTypeFactory;



//import sun.awt.RepaintArea;

abstract class _2DObject{
	
}

class Block extends _2DObject{
	   int x; int y; int height; int width;
	   Color color;
	   String name="-";
	   double mttf= 0;
	   double mttr= 0;

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
		  //System.out.println(a.toString());
		  if(a instanceof Block) {  
			Graphics2D g2 = (Graphics2D) g;
		    super.paintComponent(g);
		    g2.setColor(((Block) a).color);   
		    g2.drawRect(((Block) a).x,((Block) a).y,((Block) a).width,((Block) a).height);
		    g2.setColor(Color.LIGHT_GRAY);
		    g2.fillRect(((Block) a).x+5,((Block) a).y+5,((Block) a).width-5,((Block) a).height-5);
		    g2.setColor(Color.black);
		    g2.drawString(((Block) a).name, ((Block) a).x+((Block) a).width/2 , ((Block) a).y +((Block) a).height/2);
		    String mttf = String.valueOf(((Block) a).mttf); 
		    String mttr = String.valueOf(((Block) a).mttr); 
		    g2.setColor(Color.black);
		    g2.drawString("MTTF: "+mttf, (int) (((Block) a).x+((Block) a).width*0.3) , (int) ( ((Block) a).y +((Block) a).height*0.65));
		    g2.setColor(Color.black);
		    g2.drawString("MTTR: "+mttr, (int) (((Block) a).x+((Block) a).width*0.3), (int) (((Block) a).y +((Block) a).height*0.8));
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
//TODO: actionlistener
//TODO: klasse fuer eigenschaften fenster

class Eigenschaftenfenster_element extends JFrame{
	Element el = new Element("", 0, 0, null);
	JTextField editTextArea_name = new JTextField();
	JTextField editTextArea_mttf = new JTextField();
	JTextField editTextArea_mttr = new JTextField();

	 //Kontextfenster Element
		String name; double mttr; double mttf;
	
	public Eigenschaftenfenster_element() {
		el = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
		if(el == null) return;
			
		this.setTitle(el.name);
		this.setResizable(true);
		this.setLocation(MainFrame.posX, MainFrame.posY);
		this.setVisible(true);	 
		this.setSize(500,200);
		
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
		
		//INPUT TEXT AREA
		JLabel label=new JLabel("Eigenschaften verändern: Name, MTTF, MTTR");
		label.setSize(100, 100);
		
		BoxLayout layout = new BoxLayout(cp, BoxLayout.Y_AXIS);
		cp.setLayout(layout);
		 this.getContentPane().add(label);
		 
		editTextArea_name = new JTextField(el.name);
		editTextArea_name.setHorizontalAlignment(editTextArea_name.LEFT);
		//editTextArea_name.setMaximumSize(new Dimension(200,400));
		cp.add(editTextArea_name);
		 
		editTextArea_mttf = new JTextField(String.valueOf(el.MTTF));
		editTextArea_mttf.setHorizontalAlignment(editTextArea_mttf.LEFT);
		//editTextArea_mttf.setMaximumSize(new Dimension(200,400));
		cp.add(editTextArea_mttf);
		
		editTextArea_mttr= new JTextField(String.valueOf(el.MTTR));
		editTextArea_mttr.setHorizontalAlignment(editTextArea_mttr.LEFT);
		//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
		cp.add(editTextArea_mttr);			
		this.setVisible(true);
		
		editTextArea_name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				el.name = editTextArea_name.getText();
				MainClass.aendereEigenschaften(el);
			}
		});
		editTextArea_mttf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				el.MTTF = Double.valueOf(editTextArea_mttf.getText());
				MainClass.aendereEigenschaften(el);
			}
		});
		editTextArea_mttr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				el.MTTR = Double.valueOf(editTextArea_mttr.getText());
				MainClass.aendereEigenschaften(el);
			}
		});
		
	}	
}

class DropDownMenuElement extends JPopupMenu implements ActionListener {
	Eigenschaftenfenster_element fenster;
	ActionListener menuListener;
    JMenuItem Eigenschaften;
    JMenuItem Option2;
    JMenuItem Option3;
    //TODO: optionen erweitern
    
   

    public DropDownMenuElement(){
    	Eigenschaften = new JMenuItem("Eigenschaften");
    	Option2 = new JMenuItem("Option2");
    	Option3 = new JMenuItem("Option3");
        add(Eigenschaften); add(Option2); add(Option3);
        //Buttons zum Actionlistener hinzufuegen
        	Eigenschaften.addActionListener(this);
        	Option2.addActionListener(this);
        	Option3.addActionListener(this);

    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Eigenschaften) {
	    	fenster = new Eigenschaftenfenster_element();
			fenster.el = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
		}
		if(e.getSource() == fenster.editTextArea_name) {
			fenster.name = fenster.editTextArea_name.getText();
		}
	}    
}



public class MainFrame  extends JFrame implements MouseMotionListener, MouseListener, KeyListener{
	static int pressedX; static int pressedY; static int posX; static int posY; 
	static int deltaX; static int deltaY;
	char keyTyped;
	
	static JCanvas jc = new JCanvas();
	static MainFrame frame=new MainFrame();
	public DropDownMenuElement ddme;
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//frame.init_frame();
		Eigenschaftenfenster_element el = new Eigenschaftenfenster_element();
		
		//System.out.println("test");
		
		//frame.test(); 
		//frame.zeichneObjekte(frame.jc);
	}
	
	public MainFrame() {
		
	}
	
	public void init_frame() {		
			Dimension aufloesung= Toolkit.getDefaultToolkit().getScreenSize();

			System.out.println(aufloesung.width+" "+aufloesung.height);
			frame.setTitle("Zuverlässigkeitswerkzeuge");
			frame.setResizable(true);
			frame.setLocation(0, 0);
			frame.setVisible(true);	 frame.pack();	
			frame.setSize(aufloesung);
			
			frame.addMouseMotionListener(this);
			frame.addMouseListener(this);
			frame.addKeyListener(this);

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
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		keyTyped = arg0.getKeyChar();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(arg0)) MainClass.elementMarkieren(arg0.getX(), arg0.getY());
		if(SwingUtilities.isRightMouseButton(arg0)) MainClass.elementDropDownMenu(arg0.getX(), arg0.getY(), arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		pressedX = arg0.getX(); pressedY = arg0.getY(); 
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		deltaX = deltaY = 0; 
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		MainClass.elementVerschieben(arg0.getX(),arg0.getY());
        //System.out.println("d:"+deltaX+" "+deltaY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        posX = e.getX();
        posY = e.getY();
        //System.out.println("a:"+posX+" "+posY);
    }
	
	


	void doPop(MouseEvent e){
		DropDownMenuElement menu = new DropDownMenuElement();
	    menu.show(e.getComponent(), e.getX(), e.getY());
	}

}
