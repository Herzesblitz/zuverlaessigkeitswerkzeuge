package zuverlaessigkeitswerkzeuge;

import java.util.ArrayList;

class Zustand{
	String name;
	Zustand uebergang1;
	double Kosten_uebergang1;
	Zustand uebergang2;
	double Kosten_uebergang2;
}

class MK_Struktur {
	Zustand Startzustand;
	String name;
	
	public void Struktur(String name) {
		this.name = name;
	}
	
}

class MK_Serielle_struktur extends MK_Struktur{

	public MK_Serielle_struktur(String name) {
	}

}

public class MarkovKetten {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
