package zuverlaessigkeitswerkzeuge;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

class Komponente{
	String name;
	double zuverlassigkeit=0;
	double verfuegbarkeit=0;
	double MTTF=0, MTBF=0, MTTR=0;
	
	public Komponente(String name, double MTTF, double MTTR){
		this.name = name; this.MTTF = MTTF; this.MTTR = MTTR;
	}
}

class Element extends Komponente{
	Struktur parent;

	Color black = Color.BLACK;
	Color blue = Color.blue;
	Block block = new Block(0, 0, 500, 500,blue);
	ArrayList<Line> lines = new ArrayList<>(Arrays.asList(new Line(0, 0, 0, 0, blue)));
	
	public Element(String name, double MTTF, double MTTR, Struktur parent) {
		super(name, MTTF, MTTR);
		this.parent = parent;
		zuverlassigkeit(0);
		berechne_verfuegbarkeit();
		berechne_MTBF();
	}
	
	public void set_zuverlassigkeit(double r){
		this.zuverlassigkeit = r;
	}

	
	public void zuverlassigkeit(double t){
		this.zuverlassigkeit = ((double) 1/(double)Math.pow(Math.E, MTTF*t));  
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

	public Struktur(ArrayList<Komponente> k, String name){
		super(name, 0, 0);
		this.s = k;

	}
	
	public void berechneWerte() {
		
		berechne_MTTR();
		berechne_MTTF();
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
}
 

class Parallel_struktur extends Struktur{
	Struktur parent;
	
	public Parallel_struktur(ArrayList<Komponente> k, String name, Struktur parent){
		super(k, name);
		this.parent = parent;
		

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
	}
	
	public void berechne_verfuegbarkeit(){
		this.verfuegbarkeit = 1;
		for(Komponente k: s){
			if(k.verfuegbarkeit==0)System.err.println("verfuegbarkeit einer komponente ist 0 !");
			//System.out.println(k.verfuegbarkeit);
			this.verfuegbarkeit *= (1 - k.verfuegbarkeit);
		}
		this.verfuegbarkeit = 1-this.verfuegbarkeit;
	}
	
	public void berechne_MTTF(){
		this.MTTF= (this.verfuegbarkeit/(1-this.verfuegbarkeit))*this.MTTR;
	}
	
	public void berechne_MTTR(){
		this.MTTR = 0;
		for(Komponente k: s){
			if(k.MTTR==0)System.err.println("MTTR einer komponente ist 0 !");
			this.MTTR += (1 / k.MTTR);
		}
		this.MTTR = 1 / this.MTTR;
	}
}

class Serielle_struktur extends Struktur{
	Struktur parent;

	public Serielle_struktur(ArrayList<Komponente> k, String name, Struktur parent){
		super(k, name);
		
		this.parent = parent;
		
	}
	
	public void berechneWerte() {
		this.berechne_verfuegbarkeit();
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
	
	public void berechne_verfuegbarkeit(){
		this.verfuegbarkeit = 1;
		for(Komponente k: s){
			if(k.verfuegbarkeit==0)System.err.println("verfuegbarkeit einer komponente ist 0 !");
			this.verfuegbarkeit *= k.verfuegbarkeit;
		}
	}
}

class K_aus_N_struktur_gleichwertig extends Struktur{
	int k;
	Struktur parent;
	
	public K_aus_N_struktur_gleichwertig(ArrayList<Komponente> K, String name, int k, Struktur parent) {
		super(K, name);
		this.k = k;
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
		if(a<0)System.err.println("fak(n) für n<0 nicht definiert");
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
	}
	
	public void berechne_zuverlassigkeit(){
		double el_zuv= s.get(0).zuverlassigkeit;
		double n = s.size();
		double uber_factor = n_uber_k(s.size(), k);
		double zuv= Math.pow(el_zuv,n)+uber_factor*Math.pow(el_zuv,k)*Math.pow(1-el_zuv,n-k);
		this.zuverlassigkeit = zuv;
	}
	
}


public class Blockdiagramm {
	static Komponente zeiger = new Komponente("", 1, 1); 
	static Struktur anfang = new Struktur(new ArrayList<Komponente>(), "");
	
	public static Element sucheStruktur(Struktur a, int x, int y) {	
		for(Komponente k: a.s) {
			if(k instanceof Element) {
				if(y >= ((Element) k).block.y && y <= ((Element) k).block.y + ((Element) k).block.height) {
					if(x >= ((Element) k).block.x && x <= ((Element) k).block.x + ((Element) k).block.width) {
						return (Element) k;
					}
				}
			}
			else {
				sucheStruktur(a, x, y);
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		ArrayList<Komponente> k_s=new ArrayList<Komponente>(); 
		Serielle_struktur system = new Serielle_struktur(k_s, "system", anfang);

		//main computer
		ArrayList<Komponente> k_mc=new ArrayList<Komponente>(); 
		Serielle_struktur main_computer = new Serielle_struktur(k_mc, "main computer", system);
			Element cpu = new Element("CPU", 1950, 1.2, main_computer);
			Element mem = new Element("memory", 1500, 2.0, main_computer);
			Element con = new Element("console", 3800, 0.8, main_computer);
			k_mc.add(cpu); 
			k_mc.add(mem); 
			k_mc.add(con); 
		main_computer.berechneWerte();
		main_computer.ausgabe_werte();
		
		//power
		ArrayList<Komponente> k_p =new ArrayList<Komponente>(); 
		Parallel_struktur power = new Parallel_struktur(k_p, "power", system);
			Element ups1 = new Element("ups1", 15800, 3.75, power);
			Element ut = new Element("utility", 460, 0.5, power);
			k_p.add(ups1); 
			k_p.add(ups1); 
			k_p.add(ut);
		power.ausgabe_werte();
		
		//disks
		ArrayList<Komponente> k_d =new ArrayList<Komponente>(); 
		K_aus_N_struktur_gleichwertig disks = new K_aus_N_struktur_gleichwertig(k_d, "disks", 3, system);
			Element d1 = new Element("disk1",1800,4.5, disks);
			k_d.add(d1);			
			k_d.add(d1);
			k_d.add(d1);
			k_d.add(d1);
		d1.set_zuverlassigkeit(0.9);
		disks.berechneWerte();
		disks.ausgabe_werte();

		//gesamtsystem
		k_s.add(disks);
		k_s.add(power);
		k_s.add(main_computer);
		system.ausgabe_werte();
	}
	
	
	
}
