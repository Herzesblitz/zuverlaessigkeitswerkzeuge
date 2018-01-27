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
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


//import sun.awt.RepaintArea;

abstract class _2DObject{
	
}

class Rahmen extends _2DObject{
	   int x; int y; int height; int width;
	   String name ="-";
	   Color color;
	   double mttf= 0;
	   double mttr= 0;
	   double verfuegbarkeit;
	   double zuverlaessigkeit;
	   
	   public Rahmen(String name, int x, int y, int height, int width) {
		   this.name = name; this.x = x; this.y = y; this.height = height; this.width = width; 
	   }
}

class Block extends _2DObject{
	   int x; int y; int height; int width;
	   Color color;
	   String name="-";
	   double mttf= 0;
	   double mttr= 0;
	   double verf�gbarkeit=0;
	   double zuverl�ssigkeit = 0;
	   
	   public void werteUebernehmen(Block a) {
		   x = a.x; y = a.y; height = a.height; width = a.width; color = a.color;
	   }

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


//KLassen f�r DropDownMenu Element
class JCanvas extends JComponent
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<_2DObject> zeichnen = new ArrayList<>();
   
   public void paintComponent(Graphics g) 
   {
	   //System.out.println("zeichne ...");
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
		    g2.drawString(((Block) a).name, ((Block) a).x+20 , ((Block) a).y +20);
		    String mttf = String.valueOf(((Block) a).mttf); 
		    String mttr = String.valueOf(((Block) a).mttr); 
		    String verf�gbarkeit = String.valueOf(((Block) a).verf�gbarkeit);
		    String zuverl�ssigkeit = String.valueOf(((Block) a).zuverl�ssigkeit);
		    
		    g2.setColor(Color.black);
		    g2.drawString("MTTF: "+mttf, (int) (((Block) a).x+((Block) a).width*0.2) , (int) ( ((Block) a).y +((Block) a).height*0.65));
		    g2.drawString("MTTR: "+mttr, (int) (((Block) a).x+((Block) a).width*0.2), (int) (((Block) a).y +((Block) a).height*0.75));
		    g2.drawString("Verf�gbarkeit: "+verf�gbarkeit, (int) (((Block) a).x+((Block) a).width*0.2), (int) (((Block) a).y +((Block) a).height*0.85));
		    g2.drawString("Zuverl�ssigkeit: "+zuverl�ssigkeit, (int) (((Block) a).x+((Block) a).width*0.2), (int) (((Block) a).y +((Block) a).height*0.95));

		    continue;
		  }
		  if(a instanceof Line) {  
				Graphics2D g2 = (Graphics2D) g;
			    super.paintComponent(g);
			    g2.setColor(((Line) a).color);   
			    g2.drawLine(((Line) a).x1, ((Line) a).y1, ((Line) a).x2, ((Line) a).y2); 
			    continue;
		  }
		  if(a instanceof Rahmen) {
			  Graphics2D g2 = (Graphics2D) g;
			    super.paintComponent(g);
			    g2.setColor(((Rahmen) a).color);
			    	//System.out.println("Rahmen: "+((Rahmen) a).name+" "+((Rahmen) a).x+" "+((Rahmen) a).y+" "+((Rahmen) a).width+" "+((Rahmen) a).height);
			    g2.drawRect(((Rahmen) a).x, ((Rahmen) a).y, ((Rahmen) a).width, ((Rahmen) a).height);
			    g2.drawString(((Rahmen) a).name, ((Rahmen) a).x+((Rahmen) a).width/2 , ((Rahmen) a).y +10);
			    
			    String mttf = String.valueOf(((Rahmen) a).mttf); 
			    String mttr = String.valueOf(((Rahmen) a).mttr); 
			    g2.setColor(Color.black);
			    g2.drawString("MTTF: "+mttf, (int) (((Rahmen) a).x+((Rahmen) a).width*0.9) , (int) ( ((Rahmen) a).y +((Rahmen) a).height) -30);
			    g2.setColor(Color.black);
			    g2.drawString("MTTR: "+mttr, (int) (((Rahmen) a).x+((Rahmen) a).width*0.9), (int) (((Rahmen) a).y +((Rahmen) a).height) - 20);
		  }
		  
	  }
	  
	  
     }
   
   
 }

			
			class Aussehenfenster_element extends JFrame{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Element el = new Element("", 0, 0, null);
				JTextField editTextArea_hoehe;
				JTextField editTextArea_breite;
				JTextField editTextArea_farbe;
				
				public Aussehenfenster_element() {
					el = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
					if(el == null) return;
					
					this.setTitle("Aussehen: "+el.name);
					this.setResizable(true);
					this.setLocation(MainFrame.posX, MainFrame.posY);
					this.setVisible(true);	 
					this.setSize(500,200);
					
					Container cp = getContentPane();
					cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
					
					//INPUT TEXT AREA
					JLabel label=new JLabel("Aussehen ver�ndern: H�he, Breite, Farbe");
					label.setSize(100, 100);
					
					BoxLayout layout = new BoxLayout(cp, BoxLayout.Y_AXIS);
					cp.setLayout(layout);
					 this.getContentPane().add(label);
					 
					 editTextArea_hoehe = new JTextField(el.block.height);
					 editTextArea_hoehe.setHorizontalAlignment(SwingConstants.LEFT);
						cp.add(editTextArea_hoehe);
						 
					editTextArea_breite = new JTextField(String.valueOf(el.block.width));
					editTextArea_breite.setHorizontalAlignment(SwingConstants.LEFT);
					cp.add(editTextArea_breite);
						
					editTextArea_farbe= new JTextField(String.valueOf(el.block.color));
					editTextArea_farbe.setHorizontalAlignment(SwingConstants.LEFT);
						//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
						cp.add(editTextArea_farbe);			
						this.setVisible(true);
						
					
						editTextArea_hoehe.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String a = editTextArea_hoehe.getText();
								el.block.height = Integer.valueOf(a);
								MainClass.aendereEigenschaften(el);
							}
						});
						editTextArea_breite.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String a = editTextArea_breite.getText();
								el.block.width = Integer.valueOf(a);
								MainClass.aendereEigenschaften(el);
							}
						});
						editTextArea_farbe.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String farbe = editTextArea_farbe.getText();
								switch(farbe.toLowerCase()) {
									case "rot": el.block.color = Color.RED;
									case "blau": el.block.color = Color.BLUE;
									case "gr�n": el.block.color = Color.GREEN;
									case "schwarz": el.block.color = Color.BLACK;
									case "wei�": el.block.color = Color.WHITE;
									case "orange": el.block.color = Color.ORANGE;
									case "gelb": el.block.color = Color.yellow;
									case "grau": el.block.color = Color.GRAY;	
								}
								MainClass.aendereEigenschaften(el);
							}
						});	
						
				}
			}
			
			class Eigenschaftenfenster_element extends JFrame{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Element el = new Element("", 0, 0, null);
				JTextField editTextArea_name = new JTextField();
				JTextField editTextArea_mttf = new JTextField();
				JTextField editTextArea_verf�gbarkeit = new JTextField();
				JTextField editTextArea_zuverl�ssigkeit = new JTextField();
				JTextField editTextArea_mttr = new JTextField();
			
				 //Kontextfenster Element
					String name; double mttr; double mttf; double verf�gbarkeit; double zuverl�ssigkeit;
				
				public Eigenschaftenfenster_element() {
					el = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
					if(el == null) return;
						
					this.setTitle("Eigenschaften: "+el.name);
					this.setResizable(true);
					this.setLocation(MainFrame.posX, MainFrame.posY);
					this.setVisible(true);	 
					this.setSize(500,200);
					
					Container cp = getContentPane();
					cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
					
					//INPUT TEXT AREA
					JLabel label=new JLabel("Eigenschaften ver�ndern: Name, MTTF, MTTR");
					label.setSize(100, 100);
					
					BoxLayout layout = new BoxLayout(cp, BoxLayout.Y_AXIS);
					cp.setLayout(layout);
					 this.getContentPane().add(label);
					 
					editTextArea_name = new JTextField(el.name);
					editTextArea_name.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_name.setMaximumSize(new Dimension(200,400));
					cp.add(editTextArea_name);
					 
					editTextArea_mttf = new JTextField(String.valueOf(el.MTTF));
					editTextArea_mttf.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttf.setMaximumSize(new Dimension(200,400));
					cp.add(editTextArea_mttf);
					
					editTextArea_mttr= new JTextField(String.valueOf(el.MTTR));
					editTextArea_mttr.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
					cp.add(editTextArea_mttr);	
					
					editTextArea_verf�gbarkeit= new JTextField(String.valueOf(el.verfuegbarkeit));
					editTextArea_verf�gbarkeit.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
					cp.add(editTextArea_verf�gbarkeit);
					
					editTextArea_zuverl�ssigkeit= new JTextField(String.valueOf(el.zuverlassigkeit));
					editTextArea_zuverl�ssigkeit.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
					cp.add(editTextArea_zuverl�ssigkeit);
					this.setVisible(true);
					
					
					
					editTextArea_name.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							el.name = editTextArea_name.getText();
							MainClass.aendereEigenschaften(el);
							 //TODO: funzt?? sonst: el.setName(name);
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
					editTextArea_verf�gbarkeit.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							el.verfuegbarkeit = Double.valueOf(editTextArea_verf�gbarkeit.getText());
							MainClass.aendereEigenschaften(el);
						}
					});
					editTextArea_zuverl�ssigkeit.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							System.out.println(Double.valueOf(editTextArea_zuverl�ssigkeit.getText()));
							el.zuverlassigkeit = Double.valueOf(editTextArea_zuverl�ssigkeit.getText());
							MainClass.aendereEigenschaften(el);
						}
					});
				}	
			}
			
			
			
			/**
			 * Klasse die DropDownMenu das bei Rechtsklick auf ein Element entsteht darstellt und deren FUnktionalit�t implementiert.
			 * @author Johannes
			 */
			class DropDownMenuElement extends JPopupMenu implements ActionListener {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Element zeiger;
				Eigenschaftenfenster_element eigenschaften_fenster;
				Aussehenfenster_element aussehen_fenster;
				ActionListener menuListener;
			    JMenuItem Eigenschaften;
			    JMenuItem Aussehen;
			    JMenuItem Element_hinzufuegen_dahinter;
			    JMenuItem Element_loeschen;
			    JMenuItem Struktur_hinzufuegen;
			
			    //TODO: optionen erweitern
			
			    public DropDownMenuElement(){
			    	Eigenschaften = new JMenuItem("Eigenschaften");
			    	Aussehen = new JMenuItem("Aussehen");
			    	Element_hinzufuegen_dahinter = new JMenuItem("Element dahinter hinzufuegen");
			    	Struktur_hinzufuegen = new JMenuItem("Struktur hinzufuegen");
			    	Element_loeschen = new JMenuItem("Element l�schen");
			    	
			        add(Eigenschaften); add(Aussehen); add(Struktur_hinzufuegen); add(Element_hinzufuegen_dahinter); add(Element_loeschen);
			        //Buttons zum Actionlistener hinzufuegen
			        	Eigenschaften.addActionListener(this);
			        	Aussehen.addActionListener(this);
			        	Element_hinzufuegen_dahinter.addActionListener(this);
			        	Struktur_hinzufuegen.addActionListener(this);
			        	Element_loeschen.addActionListener(this);
			    }
			
			
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == Eigenschaften) {
				    	eigenschaften_fenster = new Eigenschaftenfenster_element();
				    	eigenschaften_fenster.el = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
					}
					if(e.getSource() == Aussehen) {
						aussehen_fenster = new Aussehenfenster_element();
						aussehen_fenster.el = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
					}
					if(e.getSource() == Element_hinzufuegen_dahinter) {
						zeiger = Blockdiagramm.sucheElement(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
						if(zeiger != null) {
							MainClass.serielleStruktur_nachbar_einf(zeiger.name, zeiger.MTTF, zeiger.MTTR,zeiger.block.x, zeiger.block.y);
						}
					}
					if(e.getSource() == Element_loeschen) {
							MainClass.struktur_verkleinern(MainFrame.posX, MainFrame.posY);
					}
					
				}    
			}

//KLassen f�r DropDownMenu 
			/**
			 * Klasse die DropDownMenu das bei Rechtsklick auf leeren Raum entsteht, darstellt und deren FUnktionalit�t implementiert.
			 * @author Johannes
			 *
			 */
			class DropDownMenuVoid extends JPopupMenu implements ActionListener{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Element zeiger;
				JMenuItem Element_hinzufuegen;
			    JMenuItem par_Struktur_hinzufuegen;
			    JMenuItem ser_Struktur_hinzufuegen;

			    
			    public DropDownMenuVoid(){
			    	Element_hinzufuegen = new JMenuItem("Element hinzuf�gen");
			    	par_Struktur_hinzufuegen = new JMenuItem("parallele Struktur hinzuf�gen");
			    	ser_Struktur_hinzufuegen = new JMenuItem("serielle Struktur hinzuf�gen");
			    	
			    	add(Element_hinzufuegen); add(ser_Struktur_hinzufuegen); add(par_Struktur_hinzufuegen);
			    	Element_hinzufuegen.addActionListener(this);
			    	par_Struktur_hinzufuegen.addActionListener(this);
			    	ser_Struktur_hinzufuegen.addActionListener(this);
			     }    
			    
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(arg0.getSource() == Element_hinzufuegen) {
						MainClass.Komponente_einf_kontextlos("neue Komponente", 1, 1, MainFrame.posX, MainFrame.posY);
					}
					if(arg0.getSource() == ser_Struktur_hinzufuegen) {
						MainClass.serielleStruktur_einf_kontextlos();
					}
					if(arg0.getSource() == par_Struktur_hinzufuegen) {
						MainClass.paralleleStruktur_einf_kontextlos();
					}
				}
			}
			
//KLassen f�r DropDownMenuStruktur
			class Eigenschaftenfenster_struktur extends JFrame{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				Struktur sr = new Struktur(new ArrayList<Komponente>(), "", 0, 0, 0, 0);
					
				JTextField editTextArea_name = new JTextField();
				JTextField editTextArea_mttf = new JTextField();
				JTextField editTextArea_mttr = new JTextField();
				JTextField editTextArea_width = new JTextField();
				JTextField editTextArea_heigth = new JTextField();

			
				 //Kontextfenster Element
					String name; double mttr; double mttf; int width; int heigth;
				
				public Eigenschaftenfenster_struktur() {
					sr = Blockdiagramm.sucheStruktur(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
					if(sr == null) return;

					this.setTitle("Eigenschaften: "+sr.name);
					this.setResizable(true);
					this.setLocation(MainFrame.posX, MainFrame.posY);
					this.setVisible(true);	 
					this.setSize(500,200);
					
					Container cp = getContentPane();
					cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
					
					//INPUT TEXT AREA
					JLabel label=new JLabel("Eigenschaften ver�ndern: Name, MTTF, MTTR");
					label.setSize(100, 100);
					
					BoxLayout layout = new BoxLayout(cp, BoxLayout.Y_AXIS);
					cp.setLayout(layout);
					 this.getContentPane().add(label);
					 
					editTextArea_name = new JTextField(sr.name);
					editTextArea_name.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_name.setMaximumSize(new Dimension(200,400));
					cp.add(editTextArea_name);
					 
					editTextArea_mttf = new JTextField(String.valueOf(sr.MTTF));
					editTextArea_mttf.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttf.setMaximumSize(new Dimension(200,400));
					cp.add(editTextArea_mttf);
					
					editTextArea_mttr= new JTextField(String.valueOf(sr.MTTR));
					editTextArea_mttr.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
					cp.add(editTextArea_mttr);		
					
					editTextArea_heigth= new JTextField(Integer.valueOf(String.valueOf(sr.rahmen.height)));
					editTextArea_heigth.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
					cp.add(editTextArea_heigth);		
					
					editTextArea_width= new JTextField(Integer.valueOf(String.valueOf(sr.rahmen.width)));
					editTextArea_width.setHorizontalAlignment(SwingConstants.LEFT);
					//editTextArea_mttr.setMaximumSize(new Dimension(200, 40));
					cp.add(editTextArea_width);		
					this.setVisible(true);
					
					editTextArea_name.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							sr.name = editTextArea_name.getText();
							MainClass.aendereEigenschaften(sr);
						}
					});
					editTextArea_mttf.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							sr.MTTF = Double.valueOf(editTextArea_mttf.getText());
							MainClass.aendereEigenschaften(sr);
						}
					});
					editTextArea_mttr.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							sr.MTTR = Double.valueOf(editTextArea_mttr.getText());
							MainClass.aendereEigenschaften(sr);
						}
					});
					editTextArea_width.addActionListener(new ActionListener() {			
						@Override
						public void actionPerformed(ActionEvent arg0) {
							sr.rahmen.width = Integer.valueOf(editTextArea_width.getText());
							MainClass.aendereEigenschaften(sr);
						}
					});
					editTextArea_heigth.addActionListener(new ActionListener() {			
						@Override
						public void actionPerformed(ActionEvent arg0) {
							sr.rahmen.height = Integer.valueOf(editTextArea_heigth.getText());
							MainClass.aendereEigenschaften(sr);
						}
					});
					
				}
				
				
			}
			
			/**
			 * Klasse die DropDownMenuStruktur das bei Rechtsklick auf Stuktur entsteht, darstellt und deren FUnktionalit�t implementiert.
			 * @author Johannes
			 *
			 */
			class DropDownMenuStruktur extends JPopupMenu implements ActionListener{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Struktur zeiger;
				JMenuItem Struktureigenschaften;
				JMenuItem Element_einf�gen;
				JMenuItem Struktur_einf�gen;
				Eigenschaftenfenster_struktur eig_fenster;
				JMenuItem Struktur_l�schen;
				
				 public DropDownMenuStruktur(){
					 Struktureigenschaften = new JMenuItem("Struktureigenschaften");
					 Element_einf�gen = new JMenuItem("Element einf�gen");
					 Struktur_einf�gen = new JMenuItem("Struktur einf�gen");
					 Struktur_l�schen = new JMenuItem("Struktur l�schen");

					 add(Struktureigenschaften); add(Element_einf�gen);	 add(Element_einf�gen); add(Struktur_l�schen);
					 Struktureigenschaften.addActionListener(this); Element_einf�gen.addActionListener(this);  Struktur_einf�gen.addActionListener(this); Struktur_l�schen.addActionListener(this);
					
				 }

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(arg0.getSource() == Struktureigenschaften) {
						eig_fenster = new Eigenschaftenfenster_struktur();
						zeiger = Blockdiagramm.sucheStruktur(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
					}
					if(arg0.getSource() == Element_einf�gen) {
						zeiger = Blockdiagramm.sucheStruktur(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
						if(zeiger instanceof Serielle_struktur) {
							MainClass.serielleStruktur_erweitern("neues Element", 1.0, 1.0,  MainFrame.posX,  MainFrame.posX, (Serielle_struktur) zeiger);
						}
						if(zeiger instanceof Parallel_struktur) {
							
						}
					}
					//TODO Struktur_einf�gen implementieren
					if(arg0.getSource() == Struktur_einf�gen) {
						
					}
					if(arg0.getSource() == Struktur_l�schen) {
						zeiger = Blockdiagramm.sucheStruktur(Blockdiagramm.anfang, MainFrame.posX, MainFrame.posY);
						MainClass.strukturL�schen(MainFrame.posX, MainFrame.posY);
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
	public DropDownMenuVoid ddmv;
	public DropDownMenuStruktur ddms;

	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//frame.init_frame();
		
		//System.out.println("test");
		
		//frame.test(); 
		//frame.zeichneObjekte(frame.jc);
	}
	
	public MainFrame() {
		
	}
	
	public void init_frame() {		
			Dimension aufloesung= Toolkit.getDefaultToolkit().getScreenSize();

			//System.out.println(aufloesung.width+" "+aufloesung.height);
			frame.setTitle("Zuverl�ssigkeitswerkzeuge");
			frame.setResizable(true);
			frame.setLocation(0, 0);
			frame.setVisible(true);	 frame.pack();	
			frame.setSize(700,700);
			
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
//			if(d instanceof Block)System.out.println("b: "+((Block) d).name+" "+((Block) d).color);
//			if(d instanceof Line)System.out.println("l: "+((Line) d).painted);
//			if(d instanceof Rahmen)System.out.println("r: "+((Rahmen) d).name+" "+((Rahmen) d).color);
//		}
		frame.getContentPane().add(jc);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		keyTyped = arg0.getKeyChar();
		if(keyTyped == '') {
			System.exit(0);
		}
		System.out.println(keyTyped);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			//System.out.println("leftclick");
			Element sucheElement =Blockdiagramm.sucheElement(MainClass.mc.bd.anfang, posX, posY);
			if(!(sucheElement == null)) {
				//System.out.println("Element mauszeiger: "+sucheElement.name);
				MainClass.elementMarkieren(sucheElement);
			}
			else if(sucheElement == null) {
				Struktur sucheStruktur = Blockdiagramm.sucheStruktur(MainClass.mc.bd.anfang, posX, posY);
				if(sucheStruktur != null) {
					if(sucheStruktur.equals(MainClass.mc.bd.anfang))System.out.println("anfang");
					else {
						//System.out.println("Struktur mauszeiger: "+sucheStruktur.name);
						MainClass.strukturMarkieren(sucheStruktur);
					}			
				}
				//else System.out.println("keine Struktur gefunden");
			}
		}
		if(SwingUtilities.isRightMouseButton(arg0)) {
			Element sucheElement =Blockdiagramm.sucheElement(MainClass.mc.bd.anfang, posX, posY);
			Struktur sucheStruktur = Blockdiagramm.sucheStruktur(MainClass.mc.bd.anfang, posX, posY);
			Element vergleich = MainClass.markedElement;
			if(!(sucheElement == null)) {
				if(!(vergleich == null)) {
					if(vergleich == sucheElement) {					
//TODO: funzt? dann entsprechende fkt in mc l�schen!						//MainClass.elementDropDownMenu(arg0.getX(), arg0.getY(), arg0);
						MainClass.markedElement =  Blockdiagramm.sucheElement(MainClass.mc.bd.anfang, arg0.getX(), arg0.getY());
						pop_ddme(arg0);
					}
				}
			}
			else if(sucheStruktur != null && !sucheStruktur.equals(MainClass.mc.bd.anfang)) {
				System.out.println("Struktur ausgew�hlt: "+sucheStruktur.name);
				if(MainClass.markedStruktur != null) {
					pop_ddms(arg0);
				}
			}
			else {
				System.out.println("ins nichts geklickte");
				pop_ddmv(arg0);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		pressedX = arg0.getX(); pressedY = arg0.getY(); 
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		deltaX = deltaY = 0; 
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(MainClass.markedElement != null) {
			MainClass.markedStruktur = null;
			MainClass.elementVerschieben(arg0.getX(),arg0.getY());
		}
		if(MainClass.markedStruktur != null) {
			MainClass.markedElement = null;
			MainClass.strukturVerschieben(arg0.getX(),arg0.getY());
		}

        //System.out.println("d:"+deltaX+" "+deltaY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        posX = e.getX();
        posY = e.getY();
        //System.out.println("a:"+posX+" "+posY);
    }
	
	void pop_ddme(MouseEvent e){
		ddme = new DropDownMenuElement();
	    ddme.show(e.getComponent(), e.getX(), e.getY());
	}
	
	void pop_ddmv(MouseEvent e) {
		ddmv = new DropDownMenuVoid();
		ddmv.show(e.getComponent(), e.getX(), e.getY());
	}
	
	void pop_ddms(MouseEvent e) {
		ddms = new DropDownMenuStruktur();
		ddms.show(e.getComponent(), e.getX(), e.getY());
	}

}
