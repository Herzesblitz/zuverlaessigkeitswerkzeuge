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
	PopUpDemo pud = new PopUpDemo();
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
		// TODO Auto-generated method stub
		MainClass mc = new MainClass();


		mc.test_blockdiagramm();
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
			    for(Line l: ((Element) k).lines) {
				    mf.jc.zeichnen.add(l);	
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
			    for(Line l: ((Element) k).lines) {
				    mf.jc.zeichnen.add(l);	
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
	private void serielleStrutur_einf(String name) {
		bd.anfang.s.add(new Serielle_struktur(new ArrayList<Komponente>(), name, bd.anfang));
	}

	private void parallelStrutur_einf(int anz, String name) {
		bd.anfang.s.add(new Parallel_struktur (new ArrayList<Komponente>(), name, bd.anfang));
	}	
		
		private void aufStruktur_erweitern(String name, int x, int y) {
			Element e = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			ArrayList<Komponente> k = new ArrayList<>(); k.add(e);
			e.parent.s.add(new Serielle_struktur(k, name, e.parent));
		}
		
		private void aufParalleleStruktur_erweitern(String name, int x, int y) {
			Element e = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			ArrayList<Komponente> k = new ArrayList<>(); k.add(e);
			e.parent.s.add(new Parallel_struktur(k, name, e.parent)); 
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
				neu.lines.add(new Line(e.block.x + e.block.width, e.block.y + e.block.height/2, neu.block.x, neu.block.y +neu.block.height/2, Color.black));
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
				markedElement.block.x = x;
				markedElement.block.y = y;
				
				//Lineberechnung
				
				for(Line line: markedElement.lines) {
					if(markedElement.parent instanceof Parallel_struktur) {
						line.x1 = markedElement.block.x + markedElement.block.width/2;
						line.y1 = markedElement.block.y + markedElement.block.height;
					}
					if(markedElement.parent instanceof Struktur || markedElement.parent instanceof Serielle_struktur) { //TODO: Struktur-Teil ist schlechter fix
						line.x1 = markedElement.block.x ;
						line.y1 = markedElement.block.y + markedElement.block.height/2 ;
						//System.out.println(line.x1+" "+line.y1);
						
						//vorgeaenge koord
						int index_vorg =   markedElement.parent.s.indexOf(markedElement)-1;

						line.x2 = ((Element) markedElement.parent.s.get(index_vorg)).block.x + ((Element) markedElement.parent.s.get(index_vorg)).block.width;
						line.y2 = ((Element) markedElement.parent.s.get(index_vorg)).block.y + ((Element) markedElement.parent.s.get(index_vorg)).block.height / 2;

						//nachfolger koord
						int index_nachfol =   markedElement.parent.s.indexOf(markedElement)+1;
						if(markedElement.parent.s.size()-1 < index_nachfol)continue;
						((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).x2 = markedElement.block.x + markedElement.block.width;
						((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).y2 =markedElement.block.y + markedElement.block.height / 2;
					}
				}
					mf.zeichneObjekte(mf.jc);
			}
		}
		
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
		private void serielleStruktur_verkleinern(int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
			e.parent.loeschen(pos);
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
				neu.lines.add(new Line(e.block.x + e.block.width/2, e.block.y, neu.block.x + neu.block.width/2, neu.block.y + neu.block.height, Color.black));
			e.parent.einfuegen(pos, neu);		
		}
		
		/*
		 * loescht ein element von parallel Struktur 
		 * Position wird durch klicken auf diesen indentifiziert
		 */
		private void parallelStruktur_verkleinern(int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
			e.parent.loeschen(pos);
		}

	
	
	private void paralleleStrutur_einf(String name, int x, int y) {
		bd.zeiger = Blockdiagramm.sucheElement(bd.anfang, x, y);
	}
	

	private void zeichnen(Struktur S) {
		mf.init_frame();
		for(Komponente k: S.s) {
			if(k instanceof Struktur) {
				zeichnen((Struktur) k);
			}
			if(k instanceof Element) {
			    mf.jc.zeichnen.add(((Element) k).block);
			    for(Line l: ((Element) k).lines) {
				    mf.jc.zeichnen.add(l);	
			    }
			}
		}
		System.out.println("test");
		mf.zeichneObjekte(mf.jc);
	}		

}