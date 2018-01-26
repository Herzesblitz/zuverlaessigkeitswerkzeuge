package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


//Blockdiagrammm wird von links nach rechts(bei seriellen Strukturen) und von oben nach unten (bei parallelen Strukturen)
public class MainClass{
	DropDownMenuElement ddme = new DropDownMenuElement();
	static Element markedElement = null;
	static Struktur markedStruktur = null;
	
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
		mf.init_frame();
	}
	
	public static void aendereEigenschaften(Struktur s) {
		markedStruktur = Blockdiagramm.sucheStruktur(Blockdiagramm.anfang, s.rahmen.x+10, s.rahmen.y+10);
		markedStruktur.name = s.name;
		markedStruktur.rahmen.name = s.name;
		markedStruktur.MTTF = s.MTTF;
		markedStruktur.rahmen.mttf = s.MTTF;
		markedStruktur.MTTR = s.MTTR;
		markedStruktur.rahmen.mttr = s.MTTR;
		markedStruktur.rahmen.height = s.rahmen.height;
		markedStruktur.rahmen.width = s.rahmen.width;
		markedStruktur.rahmen.color = s.rahmen.color;
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
		public static void serielleStruktur_einf_kontextlos() {
			Struktur neu = new Serielle_struktur(new ArrayList<Komponente>(), "neue serielle Struktur", mc.bd.anfang, 100, 100, 500, 1000);
			Blockdiagramm.anfang.s.add(neu);
			mc.zeichenobjekte_eintragen(neu);
			System.out.println(Blockdiagramm.anfang.s.get(0));
				//for(_2DObject a: mc.mf.jc.zeichnen)System.out.println("zo: "+a.toString());
			mc.zeichnen();
		}
		
		public static void paralleleStruktur_einf_kontextlos() {
			Struktur neu = new Parallel_struktur(new ArrayList<Komponente>(), "neue parallele Struktur",mc.bd.anfang, 100, 100, 1000, 500);
			Blockdiagramm.anfang.s.add(neu);
			mc.zeichenobjekte_eintragen(neu);
				//for(_2DObject a: mc.mf.jc.zeichnen)System.out.println("zo: "+a.toString());
			mc.zeichnen();
		}
	
		public static void Komponente_einf_kontextlos(String name, double MTTF, double MTTR, int x, int y) {
			Element neu = new Element(name, MTTF, MTTR, Blockdiagramm.anfang);
			neu.block.x = x; neu.block.y = y;
			Blockdiagramm.anfang.s.add(neu);
			mc.zeichenobjekte_eintragen(neu);
			mc.zeichnen();
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
		
		//TODO: K_aus_N_erweitern implementieren
		public void K_aus_N_erweitern(String name, double MTTF, double MTTR, int x, int y, K_aus_N_struktur_gleichwertig parent) {

		}
	
	//fuegt einzelne Elemente zu einer Struktur im BD hinzu
		public static void serielleStruktur_erweitern(String name, double MTTF, double MTTR,int x, int y, Serielle_struktur parent) {
			markedStruktur.rahmen.color =Color.BLACK; 
			mf.zeichneObjekte(MainFrame.jc); 
			//TODO: Struktur nach einf immer noch rot(markiert?)
			Element neu = new Element(name, MTTF, MTTR, parent);
			if(parent.s.size() > 0) {
				int last_x = 0; int last_y=0; int last_width=0; int last_heigth=0;
				if(parent.s.get(parent.s.size()-1) instanceof Element) {
					Element last = (Element) parent.s.get(parent.s.size()-1);
					last_x = last.block.x; last_y = last.block.y; last_width = last.block.width; last_heigth = last.block.height;
					
					neu.block.x = last_x+ last_width+10; neu.block.y = last_y; neu.block.width = last_width; neu.block.height = last_heigth;
					neu.vorg_line = new Line(last_x,last_y +last_heigth/2,last_x + last_width, last_y+ last_heigth/2, Color.black);
				}
				if(parent.s.get(parent.s.size()-1) instanceof Struktur) {
					Struktur last = (Struktur) parent.s.get(parent.s.size()-1);
					last_x = last.rahmen.x; last_y = last.rahmen.y; last_width = last.rahmen.width; last_heigth = last.rahmen.height;
					
					neu.block.x = last_x+ last_width+10; neu.block.y = last_y; neu.block.width = 100; neu.block.height = 100;
					neu.vorg_line = new Line(last_x,last_y +last_heigth/2,last_x + last_width, last_y+ last_heigth/2, Color.black);
				} 
				parent.einfuegen(parent.s.size(), neu);	
			}
			else {
				neu.block.x = neu.block.y = neu.block.width = neu.block.height = 100;
				neu.vorg_line = null;
			}
			System.out.println(neu.name);
			
			parent.einfuegen(0, neu);	
			mc.zeichenobjekte_eintragen(neu);
			mf.zeichneObjekte(MainFrame.jc);
		}

		
		
		/**
		 * fuegt ein Element nach Position zu seriellen Struktur hinzu
		 * Position wird durch klicken auf den Vorgaenger dieser indentifiziert
		 */
		public static void serielleStruktur_nachbar_einf(String name, double MTTF, double MTTR,int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			int pos = e.parent.s.indexOf(e);
			System.out.println("nachbar: "+e.name+" "+"parent-pos "+pos);
				Element neu = new Element(name, MTTF, MTTR, e.parent);
				neu.block.x = e.block.x + e.block.width+10; neu.block.y = e.block.y;
				neu.vorg_line = new Line(neu.block.x,neu.block.y +neu.block.height/2,e.block.x + e.block.width,e.block.y + e.block.height/2, Color.black);
			e.parent.einfuegen(pos+1, neu);
			mc.zeichenobjekte_eintragen(neu);
			mf.zeichneObjekte(MainFrame.jc);
		}

		/**
		 * fuegt ein Element zu parallelen Struktur hinzu
		 * Position wird durch klicken auf Element in pS indentifiziert
		 */
		public void parallelStruktur_nachbar_einf(String name, double MTTF, double MTTR,int x, int y) {
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
		//TODO: K_aus_NStrutur_erweitern implementieren
		public void K_aus_NStrutur_erweitern(String name, int x, int y) {

		}
		
		/*
		 * loescht ein element von serieller Struktur 
		 * Position wird durch klicken auf diesen indentifiziert
		 */
		public static void struktur_verkleinern(int x, int y) {
			Element e =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
			if(e == null) {
				System.err.println("Mauszeiger ist auf keinem Element!");
				return;
			}
			//System.out.println("lösche: "+e.name);

			mc.zeichenobjekte_austragen(e);
			mf.zeichneObjekte(MainFrame.jc);
			
			Element nachfolger = null;
			
			//fix um bei 2 Elementen die Linie vom 2. Element zu löschen
			if(e.parent.s.size() ==2) {
				System.out.println("2 fall");
				int pos = e.parent.s.indexOf(e);
				if(pos == 0) {
					mc.mf.jc.zeichnen.remove(mc.mf.jc.zeichnen.indexOf(((Element) e.parent.s.get(1)).vorg_line));
					((Element) e.parent.s.get(1)).vorg_line = null;
				}
				e.parent.loeschen(pos);
				return;
			}
			//fix um bei 3+ Elementen linien richtig zu ziehen
			if(e.parent.s.size() >2) {
				int pos = e.parent.s.indexOf(e);
				nachfolger = (Element) e.parent.s.get(pos+1);
				if(pos == 0)MainFrame.jc.zeichnen.remove(MainFrame.jc.zeichnen.indexOf(((Element)e.parent.s.get(1)).vorg_line));
				e.parent.loeschen(pos);
				//fix mittleres Element loeschens
					if(nachfolger.parent.s.size() > 1 ) {
						Element vorgaenger = ((Element) nachfolger.parent.s.get(nachfolger.parent.s.indexOf(nachfolger)-1));
						nachfolger.vorg_line.x2 = vorgaenger.block.x + vorgaenger.block.width;
						nachfolger.vorg_line.y2 = vorgaenger.block.y + vorgaenger.block.height/2;
					}
				
				return;
			}
			

			
			
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
			
			if(e.parent.s.indexOf(e) < e.parent.s.size()-1) {
				nachfolger = (Element) e.parent.s.get(e.parent.s.indexOf(e)+1);
				System.out.println("parent: "+nachfolger.parent.name);
				System.out.println(nachfolger.parent.s.size());
			}
			
		}
		
		//Elemente veraendern
				
				public static void elementDropDownMenu(int x, int y, MouseEvent arg0) {
					markedElement =  Blockdiagramm.sucheElement(Blockdiagramm.anfang, x, y);
					if(markedElement==null)return;
					else {
			            mf.pop_ddme(arg0);
					}
				}
				
				public static void voidDropDownMenu(int x, int y, MouseEvent arg0) {
					mf.pop_ddmv(arg0);
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
							if(markedElement.parent.s.get(0).equals(markedElement) && markedElement.parent.s.size() >1) {
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
								
				public static void elementGroesseVeraendern(int x, int y) {
					
				}
				
				//TODO: automatische verkleinerung?	
				public static void elementMarkieren(Element e) {
					if(markedElement != null) {			
						markedElement.block.color = markedElement.blue;
						if(markedElement.equals(e)) {
							markedElement = null;
							mf.zeichneObjekte(MainFrame.jc);
							return;
						}
						markedElement = null;
						mf.zeichneObjekte(MainFrame.jc);
					}
		            markedElement =e;
					if(markedElement==null)return;
					else {	
						markedElement.block.color = markedElement.red;
						mf.zeichneObjekte(MainFrame.jc);
					}
					//System.out.println("markiertes Element: "+markedElement.name);
				}
		
		//Struktur veraendern
				public static void strukturMarkieren(Struktur a) {
					if(markedStruktur != null) {			
						markedStruktur.rahmen.color = Color.black;
						if(markedStruktur.equals(a)) {
							markedStruktur = null;
							mf.zeichneObjekte(MainFrame.jc);
							return;
						}
						markedStruktur = null;
						mf.zeichneObjekte(MainFrame.jc);
					}
					markedStruktur = a;
					//System.out.println(markedStruktur.name);
					if(markedStruktur==null)return;
					else {	
							System.out.println("markiert: "+markedStruktur.name);
						markedStruktur.rahmen.color = Color.RED;
						mf.zeichneObjekte(MainFrame.jc);
					}
					//System.out.println("markierte Struktur: "+markedStruktur.name);
				}
				
				public static void strukturVerschieben(int x, int y) {
					if(markedStruktur == null)return;
					else {
						markedStruktur.rahmen.x = x-markedStruktur.rahmen.width/2;
						markedStruktur.rahmen.y = y-markedStruktur.rahmen.height/2;
//TODO: mit Devin: wie substrukturen verschieben?
						for(Komponente k: markedStruktur.s) {
							if(k instanceof Element)elementVerschieben(((Element) k).block.x+x, ((Element) k).block.y+y);
							else if(k instanceof Struktur){
								strukturVerschieben(x-markedStruktur.rahmen.x, y-markedStruktur.rahmen.y); //TODO: vllt falsch
							}
						}
						mf.zeichneObjekte(MainFrame.jc);

					}
					
				}
				
			
	
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
							MainFrame.jc.zeichnen.add(((Struktur) S).rahmen);
							for(Komponente k: ((Struktur) S).s){
								zeichenobjekte_eintragen(k);
							}
						}
						if(S instanceof Element) {
							if(((Element) S).vorg_line != null) {
							    MainFrame.jc.zeichnen.add(((Element) S).vorg_line);
							}
						    MainFrame.jc.zeichnen.add(((Element) S).block);		
						}
							//for(_2DObject a: MainFrame.jc.zeichnen)System.out.println("E eingetragen "+a.toString());
				}
			
				private void zeichnen() {
					mf.zeichneObjekte(MainFrame.jc);
				}		

}