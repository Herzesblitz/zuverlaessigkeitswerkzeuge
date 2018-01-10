package zuverlaessigkeitswerkzeuge;

import java.awt.Color;
import java.util.ArrayList;

public class MainClass {
	
	int height = 600;
	int width = 800;
	
	Blockdiagramm bd = new Blockdiagramm();
	MainFrame mf = new MainFrame();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc = new MainClass();
//		mc.mf.init_frame();
//		mc.test_zeichnen();
		mc.initFrame();
		mc.test_blockdiagramm();
	}
	
	private void test_zeichnen() {
		Color red = Color.red;
    	Block b2 = new Block(100, 100, 200, 200,red);
	    mf.jc.zeichnen.add(b2);
	    
	    mf.zeichneObjekte(mf.jc);
	}
	
	private void test_blockdiagramm() {
		ArrayList<Komponente> k = new ArrayList<>();
		Element k1 = new Element("k1", 1, 1);
		Element k2 = new Element("k2", 1, 1);
		k1.block.x = 0; k1.block.y = (height/2)-50; k1.block.width = 200; k1.block.height = 100; 
		k.add(k1);
		k2.block.x = 266; k2.block.y = height/2-50; k2.block.width = 200; k2.block.height = 100;
		k2.lines.get(0).x1=200; k2.lines.get(0).y1=(height/2); k2.lines.get(0).x1=266; k2.lines.get(0).y2=(height/2);  
		k.add(k2);		
//
//		ArrayList<Komponente> p_ = new ArrayList<>();
//		p_.add(new Element("p1", 0, 0));
//		p_.add(new Element("p2", 0, 0));	
//		p_.add(new Element("p3", 0, 0));
//		Parallel_struktur p = new Parallel_struktur(p_, "parallelstruktur");
//		k.add(p);
//		
//		k.add((Struktur) new Komponente("k4", 0, 0));		
		Serielle_struktur sr = new Serielle_struktur(k, "test");
		zeichnen(sr);
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
	

	private void zeichnen(Struktur S) {
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
		System.out.println(((Block) mf.jc.zeichnen.get(2)).x);
		mf.zeichneObjekte(mf.jc);
	}
	
	private void initFrame() {
		mf.init_frame(height, width);
	}
	

}