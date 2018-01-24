package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//Blockdiagrammm wird von links nach rechts(bei seriellen Strukturen) und von oben nach unten (bei parallelen Strukturen)
public class MainClass{
	DropDownMenuElement ddme = new DropDownMenuElement();
	static Element markedElement;
	
	Frame f = new Frame();   private TextField tf;

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
	static MainClass mc = new MainClass();;

	public static void main(String[] args) {
		
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
		markedElement.block.height = e.block.height;
		markedElement.block.width = e.block.width;
		markedElement.block.color = e.block.color;
		//System.out.println("test: "+Blockdiagramm.sucheElement(Blockdiagramm.anfang, e.block.x+10, e.block.y+10).name);
		mf.zeichneObjekte(MainFrame.jc);
	}
	
	
	private void test_zeichnen() {
		
		Color red = Color.red;
    	Block b2 = new Block(100, 100, 200, 200,red);
    	
	    MainFrame.jc.zeichnen.add(b2);
	    
	    mf.zeichneObjekte(MainFrame.jc);
	}
	
	private void test_blockdiagramm() {
		
		ersteKomponente_einf_sr("e1", 1, 1);


		
		zeichnen(Blockdiagramm.anfang);
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
			    MainFrame.jc.zeichnen.add(((Element) k).block);
			    if(((Element) k).vorg_line != null) {
				    MainFrame.jc.zeichnen.add(((Element) k).vorg_line);	
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
			    MainFrame.jc.zeichnen.add(((Element) k).block);
			    if(((Element) k).vorg_line != null) {
			    	MainFrame.jc.zeichnen.add(((Element) k).vorg_line);
			    }
			}
		}
	}
	
	//Blockdiagramm veraendern
		
		public void ersteKomponente_einf_sr(String name, double MTTF, double MTTR) {
			ArrayList<Komponente> k = new ArrayList<Komponente>();
			Element neu = new Element(name, MTTF, MTTR, Blockdiagramm.anfang);
			k.add(neu);
			Serielle_struktur sr = new Serielle_struktur(k, name, Blockdiagramm.anfang);
			Blockdiagramm.anfang.s.add(sr);
			zeichenobjekte_eintragen(neu);
		}
	
	//erweitert Komponente um Struktur
	//nur am Anfang verwenden  
//		public void aufSerielleStruktur_erweitern(String name, int x, int y) {
//			Element e = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
//			ArrayList<Komponente> k = new ArrayList<>(); k.add(e);
//			e.parent.s.add(new Serielle_struktur(k, name, e.parent));
//		}
//		
//		public void aufParalleleStruktur_erweitern(String name, int x, int y) {
//			Element e = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
//			ArrayList<Komponente> k = new ArrayList<>(); k.add(e);
//			e.parent.s.add(new Parallel_struktur(k, name, e.parent)); 
//		}
		
		//TODO: implementieren
		public void aufK_aus_N_erweitern(String name, int x, int y) {

		}
	
	//fuegt einzelne Elemente zu einer Struktur im BD hinzu
		/**
		 * fuegt ein Element nach Position zu seriellen Struktur hinzu
		 * Position wird durch klicken auf den Vorgaenger dieser indentifiziert
		 */
		public static void serielleStruktur_erweitern(String name, double MTTF, double MTTR,int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
				Element neu = new Element(name, MTTF, MTTR, e.parent);
				neu.block.x = e.block.x + e.block.width + 10; neu.block.y = e.block.y;
				neu.vorg_line = new Line(e.block.x + e.block.width, e.block.y + e.block.height/2, neu.block.x-10, neu.block.y +neu.block.height/2, Color.black);
			e.parent.einfuegen(pos, neu);
			mc.zeichenobjekte_eintragen(neu);
			mf.zeichneObjekte(MainFrame.jc);
		}

		/**
		 * fuegt ein Element zu parallelen Struktur hinzu
		 * Position wird durch klicken auf Element in pS indentifiziert
		 */
		public void parallelStruktur_erweitern(String name, double MTTF, double MTTR,int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
				Element neu = new Element(name, MTTF, MTTR, e.parent);
				neu.block.x = x; neu.block.y = y;
				neu.vorg_line = new Line(e.block.x + e.block.width/2, e.block.y, neu.block.x + neu.block.width/2, neu.block.y + neu.block.height, Color.black);
			e.parent.einfuegen(pos, neu);		
		}
		
		/**
		 * fuegt ein Element zu parallelen Struktur hinzu
		 * Position wird durch klicken auf Element in pS indentifiziert
		 */
		public void K_aus_NStrutur_erweitern(String name, int x, int y) {

		}
		
		/*
		 * loescht ein element von serieller Struktur 
		 * Position wird durch klicken auf diesen indentifiziert
		 */
		//TODO: 
		public static void struktur_verkleinern(int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			System.out.println("lösche: "+e.name);

			mc.zeichenobjekte_austragen(e);
			mf.zeichneObjekte(MainFrame.jc);

			int pos = e.parent.s.indexOf(e);
			if(pos == 0)MainFrame.jc.zeichnen.remove(MainFrame.jc.zeichnen.indexOf(((Element)e.parent.s.get(1)).vorg_line));
			e.parent.loeschen(pos);
			//TODO: dirty fix fuer darstellung der line
//					markedElement = Blockdiagramm.sucheElement(Blockdiagramm.anfang, mf.posX, mf.posY);
//					elementVerschieben(0, 0);
//					markedElement = null;
			
			//wenn Struktur leer, Struktur loeschen
			if(e.parent.s.size() == 0) {
				System.out.println("loescht leere Struktur "+e.parent.name+" ...");
				if(e.parent instanceof Parallel_struktur) {
					((Parallel_struktur) e.parent).parent.s.remove(((Parallel_struktur) e.parent).parent);
				}
				if(e.parent instanceof Serielle_struktur) {
					((Serielle_struktur) e.parent).parent.s.remove(((Serielle_struktur) e.parent).parent);
				}
			}
			
		}
		
		//Elemente veraendern
				
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
//TODO: wird nie aufgerufen	
							if(markedElement.parent.s.get(0) == markedElement) {
								System.out.println("erstes!: ");
								((Element) markedElement.parent.s.get(1)).vorg_line.x2 = markedElement.block.x + markedElement.block.width;
								((Element) markedElement.parent.s.get(1)).vorg_line.y2 = markedElement.block.y + markedElement.block.height / 2;
		//							System.out.println(((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).x2 + " "+ ((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).y2 );
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
									
									//vorgeaenge koord
									int index_vorg =   markedElement.parent.s.indexOf(markedElement)-1;
									if(index_vorg > 0) {
										markedElement.vorg_line.x2 = ((Element) markedElement.parent.s.get(index_vorg)).block.x + ((Element) markedElement.parent.s.get(index_vorg)).block.width ;
										markedElement.vorg_line.y2 = ((Element) markedElement.parent.s.get(index_vorg)).block.y + ((Element) markedElement.parent.s.get(index_vorg)).block.height / 2;
									}
								
									//nachfolger koord
									int index_nachfol =   markedElement.parent.s.indexOf(markedElement)+1;
									if(index_nachfol < markedElement.parent.s.size()) {
										System.out.println("ad");
										((Element) markedElement.parent.s.get(index_nachfol)).vorg_line.x2 = markedElement.block.x + markedElement.block.width;
										((Element) markedElement.parent.s.get(index_nachfol)).vorg_line.y2 = markedElement.block.y + markedElement.block.height / 2;
			//							System.out.println(((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).x2 + " "+ ((Element) markedElement.parent.s.get(index_nachfol)).lines.get(0).y2 );
									
									}
								}
						}
							mf.zeichneObjekte(MainFrame.jc);
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
							mf.zeichneObjekte(MainFrame.jc);
							return;
						}
						mf.zeichneObjekte(MainFrame.jc);
					}
		            markedElement = Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
					if(markedElement==null)return;
					else {	
						
						System.out.println(markedElement.name);
		
						markedElement.block.color = markedElement.red;
						mf.zeichneObjekte(MainFrame.jc);
					}
				}
			
		//TODO: vllt. paralleltruktur -> k_n struktur umwandeln
	
		//Hilfsfunktionen
				private void zeichenobjekte_austragen(Komponente S) {
					if(S instanceof Struktur) {
						for(Komponente k: ((Struktur) S).s){
							zeichenobjekte_austragen(k);
						}
					}
					if(S instanceof Element) {
						if(((Element) S).vorg_line != null) {
						    MainFrame.jc.zeichnen.remove(((Element) S).vorg_line);
						}
					    MainFrame.jc.zeichnen.remove(((Element) S).block);		
					}
					for(_2DObject a: MainFrame.jc.zeichnen)System.out.println("A eingetragen: "+a.toString());
					
				}
			
		
				private void zeichenobjekte_eintragen(Komponente S) {
						if(S instanceof Struktur) {
							for(Komponente k: ((Struktur) S).s){
								 MainFrame.jc.zeichnen.add(((Struktur) S).rahmen);
								zeichenobjekte_eintragen(k);
							}
						}
						if(S instanceof Element) {
							if(((Element) S).vorg_line != null) {
							    MainFrame.jc.zeichnen.add(((Element) S).vorg_line);
							}
						    MainFrame.jc.zeichnen.add(((Element) S).block);		
						}
						for(_2DObject a: MainFrame.jc.zeichnen)System.out.println("E eingetragen "+a.toString());
				}
			
				private void zeichnen(Struktur S) {
					mf.init_frame();
					System.out.println("test");
					mf.zeichneObjekte(MainFrame.jc);
				}		

}