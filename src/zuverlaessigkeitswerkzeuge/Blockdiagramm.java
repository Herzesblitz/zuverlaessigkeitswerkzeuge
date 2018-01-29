package zuverlaessigkeitswerkzeuge;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;


class Komponente{
	String name;
	double zuverlassigkeit=0;
	double verfuegbarkeit=0;
	double MTTF=0, MTBF=0, MTTR=0;
	public Struktur parent;
	
	public Komponente(String name, double MTTF, double MTTR){
		this.name = name; this.MTTF = MTTF; this.MTTR = MTTR;
	}
}

class Element extends Komponente{
	Struktur parent;

	int offset_oberStruktur_x = 0; int offset_oberStruktur_y=0;
	Block block = new Block(0, 0, 150, 150,Color.blue);
	Line vorg_line;
	
	public Element(String name, double MTTF, double MTTR, Struktur parent) {
		super(name, MTTF, MTTR);
		block.name = name; block.mttf = MTTF; block.mttr = MTTR;
		this.parent = parent;
		berechne_zuverlassigkeit(1);
		berechne_verfuegbarkeit();
		berechne_MTBF();
	}
	
	public void setMTTF(double MTTF) {
		this.MTTF = MTTF;
		berechne_verfuegbarkeit();
		berechne_zuverlassigkeit(1);
		berechne_MTBF();
		if(parent != null && parent instanceof Struktur) {
			parent.berechneWerte();
		}
		block.mttf = MTTF;
	}
	
	public void setMTTR(double MTTR) {
		this.MTTR = MTTR;
		berechne_verfuegbarkeit();
		berechne_MTBF();
		if(parent != null && parent instanceof Struktur) {
			parent.berechneWerte();
		}
		block.mttr = MTTR;
	}
	
	public void setVerfuegbarkeit(double verfuegbarkeit) {
		this.verfuegbarkeit = verfuegbarkeit;
		if(parent != null && parent instanceof Struktur) {
			parent.berechneWerte();
		}
		block.verfügbarkeit = verfuegbarkeit;
	}
	
	public void setZuverlassigkeit(double zuverlassigkeit) {
		this.zuverlassigkeit = zuverlassigkeit;
		if(parent != null && parent instanceof Struktur) {
			parent.berechneWerte();
		}
	}
	
	public void setName(String name) {
		this.name = name;
		this.block.name = name;
	}

	
	public void set_zuverlassigkeit(double r){
		this.zuverlassigkeit = r;
		block.zuverlässigkeit = zuverlassigkeit;
	}
	
	public void berechne_zuverlassigkeit(double t){
		this.zuverlassigkeit = ((double) 1/(double)Math.pow(Math.E, MTTF*t));  
		System.out.println(this.zuverlassigkeit);
		block.zuverlässigkeit = zuverlassigkeit;
		if(parent instanceof Serielle_struktur) ((Serielle_struktur) parent).berechne_zuverlassigkeit(1);
		if(parent instanceof Parallel_struktur) ((Parallel_struktur) parent).berechne_zuverlassigkeit();
		if(parent instanceof K_aus_N_struktur_gleichwertig) ((K_aus_N_struktur_gleichwertig) parent).berechne_zuverlassigkeit();
		
	}
	
	public void berechne_verfuegbarkeit(){
		this.verfuegbarkeit = MTTF / (MTTF + MTTR);
	}
	
	public void berechne_MTBF(){
		if(MTTF != 0 && MTTR != 0)this.MTBF = MTTF + MTTR;
	}
}

class Struktur extends Komponente{
	ArrayList<Komponente> s = new ArrayList<Komponente>();
	Struktur parent;
	Rahmen rahmen;
	Line vorg_linie;
	int offset_oberStruktur_x = 0; int offset_oberStruktur_y=0;


	public Struktur(ArrayList<Komponente> k, String name, int x, int y, int height, int width){
		super(name, 0, 0);
		rahmen = new Rahmen(name, x, y, height, width);
		this.s = k;
	}
	
	public void berechneWerte() {
		berechne_MTTR();
		berechne_MTTF();
	}
		
	public void passeGroesseAn(Komponente e, int min_x, int max_x, int min_y, int max_y) {
		if(e instanceof Element) {
			if(((Element) e).block.x < min_x) min_x = ((Element) e).block.x - 100;
			if(((Element) e).block.y < min_y) min_y = ((Element) e).block.y - 100;
			if(((Element) e).block.x + ((Element) e).block.width > max_x) max_x= ((Element) e).block.x + ((Element) e).block.width + 100;
			if(((Element) e).block.y + ((Element) e).block.height > max_y) max_y= ((Element) e).block.y + ((Element) e).block.height + 100;
		}
		else if(e instanceof Struktur) {
			for(Komponente k: ((Struktur) e).s) {
				
			}
		}
	}
	
	/**
	 * fuegt komponente an index im AL des Parents ein
	 * @param index
	 * @param k
	 */
	public void einfuegen(int index, Komponente k) {
		if(index == s.size()-1)s.add(k);
		else {
			ArrayList<Komponente> s_ = new ArrayList<>();
			for(int i=0; i<index; i++) s_.add(s.get(i));
			s_.add(k);
			for(int i=index; i<s.size(); i++) s_.add(s.get(i));
			s = s_;
		}
	}
	
	

	//TODO: setVorg_lineimplementieren
	public void setVorg_line(Struktur Vorgaenger) {
		if(this.parent == null || this.parent instanceof Serielle_struktur) {
			
		}
		else if(this.parent instanceof Parallel_struktur || this.parent instanceof K_aus_N_struktur_gleichwertig) {
				
		}
	}
	
	public void loeschen(int index) {
		if(index > 0 && index < s.size()) s.remove(index);
		else if(index == 0) {
			((Element) s.get(1)).vorg_line = null;
		}
		else {
			System.err.println("Komponente existiert nicht!");
			System.exit(-1);
		}
	}
	
	public void ausgabe_werte(){
		berechneWerte();
		System.out.println("name: "+this.name);
		System.out.println("mttf: "+this.MTTF);
		System.out.println("mttr: "+this.MTTR);
		System.out.println("zuverlaessigkeit "+this.zuverlassigkeit);
		System.out.println("verfuegbarkeit "+this.verfuegbarkeit);
		System.out.println();
	}
	
	public void berechne_MTTF(){
		this.MTTF = 0;
		for(Komponente k: s){
			if(k.MTTF==0)System.err.println("MTTF einer komponente ist 0 !");
			this.MTTF += 1 / k.MTTF;
		}
		this.MTTF = 1 / this.MTTF;
	}
	
	public void berechne_MTTR(){
		this.MTTR = ((1-this.verfuegbarkeit)/this.verfuegbarkeit)*this.MTTF;
	}
	
	public void setName() {
		
	}
}
 

class Parallel_struktur extends Struktur{
	Struktur parent;

	public Parallel_struktur(ArrayList<Komponente> k, String name, Struktur parent, int x, int y, int height, int width){
		super(k, name, x, y, height, width);
		this.parent = parent;
		rahmen = new Rahmen(name,x, y, height, width);
	}
	
	public void berechneWerte() {
		berechne_zuverlassigkeit();
		berechne_MTTR();
		berechne_verfuegbarkeit();
		berechne_MTTF();
	}
	
	public void berechne_zuverlassigkeit() {
		this.zuverlassigkeit = 1;
		for(Komponente a: s){
			zuverlassigkeit*=(1-a.zuverlassigkeit);
		}
		System.out.println(this.zuverlassigkeit);
		this.zuverlassigkeit = 1-this.zuverlassigkeit;
		this.rahmen.zuverlässigkeit = zuverlassigkeit;
	}
	
	public void berechne_verfuegbarkeit(){
		this.verfuegbarkeit = 1;
		for(Komponente k: s){
			if(k.verfuegbarkeit==0)System.err.println("verfuegbarkeit einer komponente ist 0 !");
			//System.out.println(k.verfuegbarkeit);
			this.verfuegbarkeit *= (1 - k.verfuegbarkeit);
		}
		this.verfuegbarkeit = 1-this.verfuegbarkeit;
		this.rahmen.zuverlässigkeit = this.zuverlassigkeit;
	}
	
	public void berechne_MTTF(){
		this.MTTF= (this.verfuegbarkeit/(1-this.verfuegbarkeit))*this.MTTR;
		this.rahmen.mttf = MTTF;
	}
	
	public void berechne_MTTR(){
		this.MTTR = 0;
		for(Komponente k: s){
			if(k.MTTR==0)System.err.println("MTTR einer komponente ist 0 !");
			this.MTTR += (1 / k.MTTR);
		}
		this.MTTR = 1 / this.MTTR;
		this.rahmen.mttr = MTTR;
	}
	
}

class Serielle_struktur extends Struktur{
	Struktur parent;

	public Serielle_struktur(ArrayList<Komponente> k, String name, Struktur parent, int x, int y, int height, int width){
		super(k, name, x, y, height, width);
		this.parent = parent;
	}
		
	public void berechneWerte() {
		berechne_verfuegbarkeit();
		berechne_MTTF();
		berechne_MTTR();
		berechne_zuverlassigkeit(0);
	}

	public void berechne_zuverlassigkeit(int t) {
		this.zuverlassigkeit = 1;
		for(Komponente k: s){
			if(k.zuverlassigkeit==0)System.err.println(this.name+" "+"zuverlassigkeit einer komponente ist 0 !");
			zuverlassigkeit*=k.zuverlassigkeit;
		}
		this.rahmen.zuverlässigkeit = zuverlassigkeit;
	}
	
	public void berechne_MTTF(){
		this.MTTF = 0;
		for(Komponente k: s){
			if(k.MTTF==0)System.err.println("MTTF einer komponente ist 0 !");
			this.MTTF += 1 / k.MTTF;
		}
		this.MTTF = 1 / this.MTTF;
		this.rahmen.mttf = MTTF;
	}
	
	public void berechne_MTTR(){
		this.MTTR = ((1-this.verfuegbarkeit)/this.verfuegbarkeit)*this.MTTF;
		this.rahmen.mttr = MTTR;
	}
	
	public void berechne_verfuegbarkeit(){
		this.verfuegbarkeit = 1;
		for(Komponente k: s){
			if(k.verfuegbarkeit==0)System.err.println("verfuegbarkeit einer komponente ist 0 !");
			this.verfuegbarkeit *= k.verfuegbarkeit;
		}
		this.rahmen.verfügbarkeit = verfuegbarkeit;
	}
}

class K_aus_N_struktur_gleichwertig extends Struktur{
	int k;
	Struktur parent;
	
	public K_aus_N_struktur_gleichwertig(ArrayList<Komponente> k, String name, Struktur parent, int l,  int x, int y, int height, int width) {
		super(k, name, x, y, height, width);
		this.k = l;
		this.parent = parent;
		
		
//		super(k, name);
//		this.parent = parent;
//		berechne_zuverlassigkeit();
//		berechne_MTTR();
//
//		berechne_verfuegbarkeit();
//		berechne_MTTF();
	}
	

	public void berechneWerte() {
		berechne_zuverlassigkeit();
		berechne_MTTR();
		verfuegbarkeit();
		berechne_MTTF();
	}
	
	int fak(int a){	
		if(a == 0)return 1;
		if(a<0)System.err.println("fak(n) fr n<0 nicht definiert");
		if(a>0) {
			return a*fak(a-1);
		}
		return 1;
	}
	
	double n_uber_k(int n, int k){
		return ((double)fak(n) / ((double) fak(n-k)* (double) fak(k)));
	}

	public void verfuegbarkeit(){
		double el_verf = s.get(0).verfuegbarkeit;
		double n = s.size();
		double uber_factor = n_uber_k(s.size(), k);
		//System.out.println(k);
		this.verfuegbarkeit = Math.pow(el_verf, n) + uber_factor*Math.pow(el_verf, k)*(1-(el_verf));
	}
	
	public void berechne_MTTF(){
		this.MTTF = s.get(0).MTTF / s.size() + s.get(0).MTTF / k;
		this.rahmen.mttf = this.MTTF;
	}
	
	public void berechne_zuverlassigkeit(){
		double el_zuv= s.get(0).zuverlassigkeit;
		double n = s.size();
		double uber_factor = n_uber_k(s.size(), k);
		double zuv= Math.pow(el_zuv,n)+uber_factor*Math.pow(el_zuv,k)*Math.pow(1-el_zuv,n-k);
		this.zuverlassigkeit = zuv;
		rahmen.zuverlässigkeit = zuverlassigkeit;
	}
}


public class Blockdiagramm {
	static Komponente zeiger = new Komponente("", 1, 1); 
	static Struktur anfang = new Struktur(new ArrayList<Komponente>(), "anfang", 0, 0, 0, 0);
	
//	public static Struktur sucheStruktur(Komponente a, int x, int y) {
//		if(a instanceof Struktur) {
//			if(y>= (((Struktur) a).rahmen).y && y <= (((Struktur) a).rahmen).y +  (((Struktur) a).rahmen).width || a.equals(Blockdiagramm.anfang)) {
//				if(x>= (((Struktur) a).rahmen).x && x <= (((Struktur) a).rahmen).x +  (((Struktur) a).rahmen).width  || a.equals(Blockdiagramm.anfang)) {
//					System.out.println("passt: "+a.name);
//					//TODO: testen!!
//					if(((Struktur) a).s.size() > 0) {
//						for(Komponente k: ((Struktur) a).s) {
//							//if(!(k instanceof Struktur))return null;
//							
//						}		
//					}
//					else {					System.out.println("ret2 "+a.name);
//						return (Struktur) a;
//					}
//				}
//			}	
//		}
//		System.out.println("nicht passend"+a.name);
//		return null;
//	}
	
	public static Struktur sucheStruktur(Komponente a, int x, int y) {
		if(!(a instanceof Struktur))return null;
		else {
			if(y>= (((Struktur) a).rahmen).y && y <= (((Struktur) a).rahmen).y +  (((Struktur) a).rahmen).width || a.equals(Blockdiagramm.anfang)) {
				if(x>= (((Struktur) a).rahmen).x && x <= (((Struktur) a).rahmen).x +  (((Struktur) a).rahmen).width  || a.equals(Blockdiagramm.anfang)) {
					if(((Struktur) a).s.size() > 0) {
						for(Komponente k: ((Struktur) a).s) {
							if(sucheStruktur(k, x, y) == null) {continue;}
							return sucheStruktur(k, x, y);
						}
					}
					return (Struktur) a;
				}
			}
			return null;
		}
	}
			
				
	public static void komponenteEinfügen(Komponente e, Struktur parent) {
		parent.s.add(e);
		e.parent = parent;
		if(e instanceof Parallel_struktur) {
			((Parallel_struktur) e).berechneWerte();	
		}
		if(e instanceof Serielle_struktur) {
			((Serielle_struktur) e).berechneWerte();	
		}
		if(e instanceof K_aus_N_struktur_gleichwertig) {
			((K_aus_N_struktur_gleichwertig) e).berechneWerte();
		}
	}
	

	public static void strukturEinf(Struktur e, Struktur parent) {
		System.out.println(e.rahmen.x+" "+e.rahmen.y+" "+e.rahmen.height+" "+e.rahmen.width+" ");
		parent.s.add(e);
		e.parent = parent;
	}
	
	public static void komponenteLöschen(Komponente k, Struktur parent) {
		//Element e = sucheElement(anfang, x, y);
//		if(e != null) {
//			System.out.println("funktion: komponenteLöschen "+e.name+" "+e.parent.name);
//			e.parent.s.remove(e.parent.s.indexOf(e));
//		}
//		else {
//			// Struktur k = sucheStruktur(anfang, x, y);
//		     k.parent.s.remove(k.parent.s.indexOf(k));
//		}
		parent.s.remove(parent.s.indexOf(k));
	}
	
	public static void offsetneuberechnen(Struktur e,int x, int y) {
		Struktur parent = sucheStruktur(anfang, x, y);
		e.offset_oberStruktur_x = e.parent.rahmen.x - parent.rahmen.x;
		e.offset_oberStruktur_y = e.parent.rahmen.y - parent.rahmen.y;

	}
	
	
	public static Element sucheElement(Komponente a, int x, int y) {
		if(a instanceof Element) {
			if(y >= ((Element) a).block.y && y <= (((Element) a).block.y + ((Element) a).block.height)) {
				if(x >= ((Element) a).block.x && x <= (((Element) a).block.x + ((Element) a).block.width)) {
					return (Element) a;
				}
			}
			return null;
		}
		for(Komponente k: ((Struktur) a).s) {
			Element e=sucheElement(k, x, y);
			if(e==null) continue;
			return sucheElement(k, x, y);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		ArrayList<Komponente> k_s=new ArrayList<Komponente>(); 
		Serielle_struktur system = new Serielle_struktur(k_s, "system", anfang, 0, 0, 0, 0);

		//main computer
		ArrayList<Komponente> k_mc=new ArrayList<Komponente>(); 
		Serielle_struktur main_computer = new Serielle_struktur(k_mc, "main computer", system, 0, 0, 0, 0);
			Element cpu = new Element("CPU", 1950, 1.2, main_computer);
			cpu.block.x = 0; cpu.block.y = 0;
			Element mem = new Element("memory", 1500, 2.0, main_computer);
			mem.block.x = 101; cpu.block.y = 0;
			Element con = new Element("console", 3800, 0.8, main_computer);
			con.block.x = 202; con.block.y = 0;
			k_mc.add(cpu); 
			k_mc.add(mem); 
			k_mc.add(con); 
		main_computer.berechneWerte();
		main_computer.ausgabe_werte();
		
		//power
		ArrayList<Komponente> k_p =new ArrayList<Komponente>(); 
		Parallel_struktur power = new Parallel_struktur(k_p, "power", system, 0, 0, 0, 0);
			Element ups1 = new Element("ups1", 15800, 3.75, power);
			Element ups2 = new Element("ups2", 15800, 3.75, power);
			ups1.block.x = 303; ups1.block.y = 0;
			ups2.block.x = 303; ups2.block.y = 101;
			Element ut = new Element("utility", 460, 0.5, power);
			ut.block.x = 303; ut.block.y = 202;
			k_p.add(ups1); 
			k_p.add(ups1); 
			k_p.add(ut);
		power.ausgabe_werte();
		
		//disks
		ArrayList<Komponente> k_d =new ArrayList<Komponente>(); 
		K_aus_N_struktur_gleichwertig disks = new K_aus_N_struktur_gleichwertig(k_d, "disks", system, 3, 0, 0, 0, 0);
			Element d1 = new Element("disk1",1800,4.5, disks);
			Element d2 = new Element("disk2",1800,4.5, disks);
			Element d3 = new Element("disk3",1800,4.5, disks);
			Element d4 = new Element("disk4",1800,4.5, disks);
			d1.block.x = 404; d1.block.y = 0;
			d2.block.x = 404; d2.block.y = 101;
			d3.block.x = 404; d3.block.y = 202;
			d4.block.x = 404; d4.block.y = 303;
			k_d.add(d1);			
			k_d.add(d2);
			k_d.add(d3);
			k_d.add(d4);
		d1.set_zuverlassigkeit(0.9);
		d2.set_zuverlassigkeit(0.9);
		d3.set_zuverlassigkeit(0.9);
		d4.set_zuverlassigkeit(0.9);
		disks.berechneWerte();
		disks.ausgabe_werte();

		//gesamtsystem
		k_s.add(disks);
		k_s.add(power);
		k_s.add(main_computer);
		anfang.s.add(system);
		system.ausgabe_werte();
	}
	
	
	
}
