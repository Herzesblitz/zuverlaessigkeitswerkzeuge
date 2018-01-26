package zuverlaessigkeitswerkzeuge;

//TODO: stationaerer Zustand berechen

import java.lang.reflect.Array;
import java.util.ArrayList;

class Zustand
{
	String name;
	ArrayList<Double> uebergangskosten;
	ArrayList<Zustand> nachfolger;

    public Zustand(String name)
    {
        this.name = name;
        this.uebergangskosten=new ArrayList<Double>();
        this.nachfolger=new ArrayList<Zustand>();

    }
    public  void add_Zustand_init( double kosten, Zustand Zustandnachfolger)
    {
        if(!Zustandnachfolger.equals(this)||this.uebergangskosten.size()<=this.nachfolger.size())
        {
            this.nachfolger.add(Zustandnachfolger);
            this.uebergangskosten.add(kosten);
        }

    }

    /* Beim Löschen alle Zustände die mit gelöschten Zustand verbunden waren, mit dessen Vorgänger verknüpfen
        sonst sind die Knoten zwar im MK aber keine Übergaenge
    */
    public  void remove_Zustand(String name_remove_Zustand)
    {
            int size = this.nachfolger.size();
            for (int i = 0; i < size; i++)
            {
                if ((this.nachfolger.get(i)).name.equals(name_remove_Zustand))
                {
                    this.nachfolger.remove(i);
                    this.uebergangskosten.remove(i);
                    break;
                }
            }
    //TODO: modifiziere existierende Zustände z.b anpassen der Kosten

    }

    public  boolean test_Kosten_zwischen_0_und_1(double kosten)
    {

        for (int i = 0; i <this.uebergangskosten.size() ; i++)
        {
         kosten+=this.uebergangskosten.get(i);
        }
        if(kosten<0.0 || kosten >1.0)
        {
            return false;
        }
        return true;
    }
}

class MK_Struktur
{
	ArrayList<Zustand> Zustaende;
	ArrayList<ArrayList<Double>> next_Zustand_list;
	//Matrix indirekt durch Kostenarray im Zustand enthalten zeile = Zustand spalten =Kostenarray
	String name;

	public MK_Struktur(String name)
    {
        Zustaende=new ArrayList<Zustand>();
		this.name = name;
		next_Zustand_list=new ArrayList<ArrayList<Double>>();

	}

    public  void addZustand( Zustand neuerZustand,int index_Zustand_vorgaenger,double kosten)
    {
        int index_des_neuen_Element=0;
        if (!this.Zustaende.contains(neuerZustand))
        {
            this.Zustaende.add(neuerZustand);
            index_des_neuen_Element=this.Zustaende.size()-1;

            if(neuerZustand.uebergangskosten.size()<this.Zustaende.size())
            {
                for (int i = 0; i <this.Zustaende.size() ; i++)
                {
                    neuerZustand.add_Zustand_init(0, this.Zustaende.get(i));
                }
                for (int j = 0; j <this.Zustaende.size() ; j++)
                {
                this.Zustaende.get(j).add_Zustand_init(0,neuerZustand);
                }
            }
        }

        else
        {
            index_des_neuen_Element=this.Zustaende.indexOf(neuerZustand);
        }
        Zustand Vorgaenger_Zustand=this.Zustaende.get(index_Zustand_vorgaenger);
        Vorgaenger_Zustand.uebergangskosten.set(index_des_neuen_Element,kosten);;
    }

    public void remove_Zustand(MK_Struktur Basis,String name)
    {
        //TODO: anpassen der nachfolger Zustaende
    }
    ArrayList<Double> neue_Wahrscheinlichkeit;
    public void berechne_naechster_Zustand_to_n(ArrayList<Double> alte_Zustandswahrscheinlichkeit,int n)
    {
        if(this.next_Zustand_list.size()==0)
        {
            this.next_Zustand_list.add(alte_Zustandswahrscheinlichkeit);
        }
        for (int p = 0; p <n ; p++)
        {
            neue_Wahrscheinlichkeit = new ArrayList<Double>();
            Double wert;
            int index=this.next_Zustand_list.size()-1;
            for (int i = 0; i <alte_Zustandswahrscheinlichkeit.size(); i++) {
                wert = 0.0;
                for (int j = 0; j <alte_Zustandswahrscheinlichkeit.size() ; j++)
                {
                    wert += this.next_Zustand_list.get(index).get(j) * this.Zustaende.get(i).uebergangskosten.get(j);
                }
                neue_Wahrscheinlichkeit.add(wert);
            }
            this.next_Zustand_list.add(neue_Wahrscheinlichkeit);
        }


    }

    public  void print_UEMatrix()
    {
        System.out.println("Print Matrix");
        for (int i = 0; i <this.Zustaende.size() ; i++)
        {
            for (int j = 0; j <this.Zustaende.size() ; j++)
            {
                System.out.print(this.Zustaende.get(i).uebergangskosten.get(j)+" | ");
            }
            System.out.println();
        }
    }

    public void print_nextZustand()
    {
        System.out.println("Print next_Zustand");
        for (int i = 0; i <this.next_Zustand_list.size() ; i++)
        {
            for (int j = 0; j <this.Zustaende.size() ; j++)
            {
                System.out.print(this.next_Zustand_list.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }

}

public class MarkovKetten
{

	public static void main(String[] args)
    {


        Zustand z1=new Zustand("z1");
        Zustand z2=new Zustand("z2");
        Zustand z3=new Zustand("z3");
        Zustand z4=new Zustand("z4");
        MK_Struktur m =new MK_Struktur("mk");
        m.addZustand(z1,0,-0.4);
        m.addZustand(z2,0,0.4);
        m.addZustand(z3,0,0.0);
        m.addZustand(z4,0,0.0);
        m.addZustand(z1,1,0.6);
        m.addZustand(z2,1,-1.2);
        m.addZustand(z3,1,0.6);
        m.addZustand(z4,1,0.0);
        m.addZustand(z1,2,-0.4);
        m.addZustand(z2,2,0.4);
        m.addZustand(z3,2,0.0);
        m.addZustand(z4,2,0.0);
        m.addZustand(z1,3,-0.4);
        m.addZustand(z2,3,0.4);
        m.addZustand(z3,3,0.0);
        m.addZustand(z4,3,0.0);

        ArrayList<Double> StartWarscheinlichkeit=new ArrayList<>();
        StartWarscheinlichkeit.add(1.0); //z1
        StartWarscheinlichkeit.add(0.0); //z2
        StartWarscheinlichkeit.add(0.0);
        m.print_UEMatrix();
        m.berechne_naechster_Zustand_to_n(StartWarscheinlichkeit,20);
        m.print_nextZustand();

        /*
        Beispiel 1
        int p=0;
        int q=0;
        Zustand z1=new Zustand("z1");
        MK_Struktur Markov=new MK_Struktur("mk");
        Markov.addZustand(z1,0,0.2);
        for (int i = 0; i <5 ; i++)
        {
            if(i>1)
            {
                p=i-1;
                q=p-1;
            }
            else {p=0;}

            Zustand x = new Zustand("x" + i);
            Markov.addZustand(x, p,0.24*i);
            Markov.addZustand(x, q,0.03*i);
        }
        Markov.addZustand(z1,3,4);


        Markov.print_UEMatrix();

        ArrayList<Double> StartWarscheinlichkeit=new ArrayList<>();
        StartWarscheinlichkeit.add(1.0); //z1
        StartWarscheinlichkeit.add(0.0); //x0
        StartWarscheinlichkeit.add(0.0); //x1
        StartWarscheinlichkeit.add(0.0); //x2 Elemente
        StartWarscheinlichkeit.add(0.0);
        StartWarscheinlichkeit.add(0.0);

            Markov.berechne_naechster_Zustand_to_n(StartWarscheinlichkeit,1000);

        Markov.print_nextZustand();
        */
    }

}
