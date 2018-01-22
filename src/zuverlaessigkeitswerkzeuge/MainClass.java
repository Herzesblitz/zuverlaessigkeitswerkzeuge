package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;


//Blockdiagrammm wird von links nach rechts(bei seriellen Strukturen) und von oben nach unten (bei parallelen Strukturen)
public class MainClass {
	
	static int maxheigth_el=0;
	static int maxwidth_el=0;
	
	int height = 600;
	int width = 800;
	
	Blockdiagramm bd = new Blockdiagramm();
	MainFrame mf = new MainFrame();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc = new MainClass();
//		mc.mf.init_frame();
//		mc.test_zeichnen();
		
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
				neu.block.x = x; neu.block.y = y;
				neu.lines.add(new Line(e.block.x + e.block.width, e.block.y + e.block.height/2, x, y+neu.block.height/2, Color.black));
			e.parent.einfuegen(pos, neu);
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