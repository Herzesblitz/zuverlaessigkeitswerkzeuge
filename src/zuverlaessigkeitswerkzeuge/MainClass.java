package zuverlaessigkeitswerkzeuge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//Blockdiagrammm wird von links nach rechts(bei seriellen Strukturen) und von oben nach unten (bei parallelen Strukturen)
public class MainClass{
	DropDownMenuElement ddme = new DropDownMenuElement();
	static Element markedElement;
	
	Frame f = new Frame();   private TextField tf;

	private static final long serialVersionUID = 1L;

	public MainClass() {
		 f = new Frame("Two listeners example");
	     tf = new TextField(30);
	}
	JCanvas jc = new JCanvas();

	//eingabe-variablen
	static int pressedX; static int pressedY; static int posX; static int posY; static boolean released=true; 
	
	static int maxheigth_el=0;
	static int maxwidth_el=0;
	
	int height = 600;
	int width = 800;
	
	Blockdiagramm bd = new Blockdiagramm();
	static MainFrame mf = new MainFrame();


	public static void main(String[] args) {
		MainClass mc = new MainClass();


		mc.test_blockdiagramm();
	}

	public static void aendereEigenschaften(Element e) {
		markedElement = Blockdiagramm.sucheElement(Blockdiagramm.anfang, e.block.x+10, e.block.y+10);
		markedElement.name = e.name;
		markedElement.block.name = e.name;
		markedElement.MTTF = e.MTTF;
		markedElement.block.mttf = e.MTTF;
		markedElement.MTTR = e.MTTR;
		markedElement.block.mttr = e.MTTR;

		
		//System.out.println("test: "+Blockdiagramm.sucheElement(Blockdiagramm.anfang, e.block.x+10, e.block.y+10).name);
		mf.zeichneObjekte(mf.jc);
	}
	
	
	private void test_zeichnen() {
		
		Color red = Color.red;
    	Block b2 = new Block(100, 100, 200, 200,red);
    	
	    mf.jc.zeichnen.add(b2);
	    
	    mf.zeichneObjekte(mf.jc);
	}
	
	private void test_blockdiagramm() {
		ersteKomponente_einf("e1", 1, 1);
		serielleStruktur_erweitern("e2", 1, 1, 100, 100);
		serielleStruktur_erweitern("e3", 1, 1, 200, 100);

		zeichnen(bd.anfang);
	}
	
	private void setDimensions(Struktur S, int heigth, int width) {
		for(int i=0; i<S.s.size(); i++) {
			Komponente k = S.s.get(i);
			if(k instanceof Struktur) {
				if(k instanceof Serielle_struktur) {
					setDimensions(((Struktur) k),heigth,(4/5)*width/S.s.size());
				}
				if(k instanceof Parallel_struktur) {
					setDimensions(((Struktur) k),(4/5)*heigth/S.s.size(),width);
				}
			}
			if(k instanceof Element) {
			    mf.jc.zeichnen.add(((Element) k).block);
			    if(((Element) k).vorg_line != null) {
				    mf.jc.zeichnen.add(((Element) k).vorg_line);	
			    }
			}
		}
	}
	
	private void setCoordinates(Struktur S, int x, int y, int heigth, int width) {
		for(int i=0; i<S.s.size(); i++) {
			Komponente k = S.s.get(i);
			if(k instanceof Struktur) {
				if(k instanceof Serielle_struktur) {
					setCoordinates(((Struktur) k), S.s.size()/width * i , heigth/2 - S.s.size()/width*(4/5), (S.s.size()/width*(4/5)) / 2 , S.s.size()/width*(4/5));
				}
				if(k instanceof Parallel_struktur) {
					
				}
			}
			if(k instanceof Element) {
			    mf.jc.zeichnen.add(((Element) k).block);
			    if(((Element) k).vorg_line != null) {
			    	mf.jc.zeichnen.add(((Element) k).vorg_line);
			    }
			}
		}
	}
	
	//Blockdiagramm veraendern
		
		private void ersteKomponente_einf(String name, double MTTF, double MTTR) {
			Element neu = new Element(name, MTTF, MTTR, bd.anfang);
			bd.anfang.s.add(neu);
		}
	
	//erweitert Komponente um Struktur
	//nur am Anfang verwenden  
		private void aufSerielleStruktur_erweitern(String name, int x, int y) {
			Element e = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			ArrayList<Komponente> k = new ArrayList<>(); k.add(e);
			e.parent.s.add(new Serielle_struktur(k, name, e.parent));
		}
		
		private void aufParalleleStruktur_erweitern(String name, int x, int y) {
			Element e = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			ArrayList<Komponente> k = new ArrayList<>(); k.add(e);
			e.parent.s.add(new Parallel_struktur(k, name, e.parent)); 
		}
		
		private void aufK_aus_N_erweitern(String name, int x, int y) {

		}
	
	//fuegt einzelne Elemente zu einer Struktur im BD hinzu
		/*
		 * fuegt ein Element nach Position zu seriellen Struktur hinzu
		 * Position wird durch klicken auf den Vorgaenger dieser indentifiziert
		 */
		private void serielleStruktur_erweitern(String name, double MTTF, double MTTR,int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
				Element neu = new Element(name, MTTF, MTTR, e.parent);
				neu.block.x = e.block.x + e.block.width + 10; neu.block.y = e.block.y;
				neu.vorg_line = new Line(e.block.x + e.block.width, e.block.y + e.block.height/2, neu.block.x, neu.block.y +neu.block.height/2, Color.black);
			e.parent.einfuegen(pos, neu);
		}
		
		
		
		public static void elementDropDownMenu(int x, int y, MouseEvent arg0) {
			markedElement =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			if(markedElement==null)return;
			else {
	            mf.doPop(arg0);
			}
		}
		
		public static void elementVerschieben(int x, int y) {
			if(markedElement==null)return;
			else {
				//TODO: Kollisionspruefung
				
				//Blockverschiebung
				markedElement.block.x = x-markedElement.block.width/2;
				markedElement.block.y = y-markedElement.block.height/2;
				//TODO: linien werden tw. ueber bloecken gezeichnet
				//Lineberechnung
				//spezialfall: erstes Element in AL der parent-struktur
					if(markedElement.parent.s.get(0) == markedElement && markedElement.parent.s.size()>1) {
						((Element) markedElement.parent.s.get(1)).vorg_line.x2 = markedElement.block.x + markedElement.block.width;
						((Element) markedElement.parent.s.get(1)).vorg_line.y2 = markedElement.block.y + markedElement.block.height / 2;
//							System.out.println(((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).x2 + " "+ ((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).y2 );
						System.out.println("1");
					}
				//sonst
					if(markedElement.vorg_line != null) {
						if(markedElement.parent instanceof Parallel_struktur) {
							markedElement.vorg_line.x1 = markedElement.block.x + markedElement.block.width/2;
							markedElement.vorg_line.y1 = markedElement.block.y + markedElement.block.height;
						}
						if(markedElement.parent instanceof Struktur || markedElement.parent instanceof Serielle_struktur) { //TODO: Struktur-Teil ist schlechter fix
							markedElement.vorg_line.x1 = markedElement.block.x ;
							markedElement.vorg_line.y1 = markedElement.block.y + markedElement.block.height/2 ;
							//System.out.println(line.x1+" "+line.y1);
							
							//vorgeaenge koord
							int index_vorg =   markedElement.parent.s.indexOf(markedElement)-1;
	
							markedElement.vorg_line.x2 = ((Element) markedElement.parent.s.get(index_vorg)).block.x + ((Element) markedElement.parent.s.get(index_vorg)).block.width;
							markedElement.vorg_line.y2 = ((Element) markedElement.parent.s.get(index_vorg)).block.y + ((Element) markedElement.parent.s.get(index_vorg)).block.height / 2;
							
							System.out.println();
							//nachfolger koord
							int index_nachfol =   markedElement.parent.s.indexOf(markedElement)+1;
							if(markedElement.parent.s.size()-1 >= index_nachfol) {
								((Element) markedElement.parent.s.get(index_nachfol)).vorg_line.x2 = markedElement.block.x + markedElement.block.width;
								((Element) markedElement.parent.s.get(index_nachfol)).vorg_line.y2 = markedElement.block.y + markedElement.block.height / 2;
	//							System.out.println(((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).x2 + " "+ ((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).y2 );
							
							}
							
						}
				}
					mf.zeichneObjekte(mf.jc);
			}
		}
		
		public static void strukturGroesseVeraendern(int x, int y) {
			
		}
		
		public static void elementGroesseVeraendern(int x, int y) {
			
		}
		//TODO: automatische verkleinerung?
		
		public static void elementMarkieren(int x, int y) {
			if(markedElement != null) {			
				markedElement.block.color = markedElement.blue;
				if(markedElement ==  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y)) {
					markedElement = null;
					mf.zeichneObjekte(mf.jc);
					return;
				}
				mf.zeichneObjekte(mf.jc);
			}
            markedElement = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			if(markedElement==null)return;
			else {	
				
				System.out.println(markedElement.name);

				markedElement.block.color = markedElement.red;
				mf.zeichneObjekte(mf.jc);
			}
		}
		
		/*
		 * loescht ein element von serieller Struktur 
		 * Position wird durch klicken auf diesen indentifiziert
		 */
		//TODO: 
		private void struktur_verkleinern(int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
			e.parent.loeschen(pos);
		}
		
		//TODO
		private void k_aus_n_Struktur_erweitern(String name, double MTTF, double MTTR,int x, int y) {

		}
		
		/*
		 * fuegt ein Element zu parallelen Struktur hinzu
		 * Position wird durch klicken auf Element in pS indentifiziert
		 */
		private void parallelStruktur_erweitern(String name, double MTTF, double MTTR,int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
				Element neu = new Element(name, MTTF, MTTR, e.parent);
				neu.block.x = x; neu.block.y = y;
				neu.vorg_line = new Line(e.block.x + e.block.width/2, e.block.y, neu.block.x + neu.block.width/2, neu.block.y + neu.block.height, Color.black);
			e.parent.einfuegen(pos, neu);		
		}
			
	//klicke Element, fuege parallelstruk dahinter ein //TODO: implen
	private void paralleleStrutur_einf_dahinter(String name, int x, int y) {
		//pruefung ist Element in serieller struktur?
		bd.zeiger = Blockdiagramm.sucheElement(bd.anfang, x, y);
		
		//ansonsten fuege parallelstruktur "daneben" -also in ArrayList des Parent ein
		
	}
	
	
	//klicke Element, fuege seriellestruk dahinter ein //TODO: implen
		private void serielleStrutur_einf_dahinter(String name, int x, int y) {
			//pruefung ist Element in paralleler struktur?

			bd.zeiger = Blockdiagramm.sucheElement(bd.anfang, x, y);
			//ansonsten fuege parallelstruktur "daneben" -also in ArrayList des Parent ein

		}
		
		
		//TODO: vllt. paralleltruktur -> k_n struktur umwandeln
		//
		private void K_aus_NStrutur_einf_dahinter(String name, int x, int y) {

		}

	private void zeichnen(Struktur S) {
		mf.init_frame();
		for(Komponente k: S.s) {
			if(k instanceof Struktur) {
				zeichnen((Struktur) k);
			}
			if(k instanceof Element) {
				if(((Element) k).vorg_line != null) {
				    mf.jc.zeichnen.add(((Element) k).vorg_line);
				}
			    mf.jc.zeichnen.add(((Element) k).block);		
			}
		}
		System.out.println("test");
		mf.zeichneObjekte(mf.jc);
	}		

}