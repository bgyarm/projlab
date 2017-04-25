package struct;

public class Tunnel extends Rail {
	//bels� p�lya, 3 hossz� + 2 bels� s�n
    private static Rail[] tunnel = new Rail[3];
    private static boolean initialized = false;
    private  static void init(){//l�trehozzuk a bels� sineket.
        for(int i = 0; i < 3; i++)
            tunnel[i] = new Rail();
        tunnel[0].setNext(tunnel[1]);//megfelel�en �sszekapcsoljuk �ket
        tunnel[1].setNext(tunnel[0]);
        tunnel[1].setNext(tunnel[2]);
        tunnel[2].setNext(tunnel[1]);
        initialized = true;//ez ut�n m�r l�trej�tt a l�thatatlan alag�t
    }

    public static boolean isInitialized(){return initialized;}

    public static Rail[] getInner(){
        return tunnel;
    }

    //Egy bej�rat fel�p�t�se
    public Tunnel(){
    	//Ha ez az els�, akkor a bels� p�lya is
    	if(!initialized)
    		init();
    	//A bels� s�n fel�p�t�se a szabad v�gen
    	//Maximum 2 alag�tv�g lehet, �gy �sszesen 1 alag�t
    	if(tunnel[0].setNext(this))
    		railA = tunnel[0];
    	else
    		if(tunnel[2].setNext(this))
    			railA = tunnel[2];
    }

    @Override
    public boolean setNext(RailElement next){//megpr�b�ljuk be�ll�tani a k�vetkez� s�nnek a kopottat
        if(next == null || railB == next) return true;//ha m�r be van �ll�tva vagy nem akarunk semmit be�ll�tani
        if(railA != null && railB == null){ //ha railA null, akkor a konstruktorban nem tudta megkapni az alag�t semeylik v�g�t. railB-hez ha m�r van valaki hozz�k�tve akkor nem tudjuk be�ll�tani
            railB = next;
            return true;//ha betudtuk �ll�tani, be is �ll�tjuk
        }
        return false;
    }

    public boolean build(RailElement entrance){//megpr�b�ljuk fel�p�teni
        if(this.setNext(entrance) && entrance.setNext(this))// ha fel lehet �p�teni
            return true;//ekkor igazzal t�r�nk vissza
        else
            entrance.remove(this);//ha nem akkor megsz�ntetj�k a kapcsolatot, majd k�v�lr�l megsemmis�tj�k
        return false;// ekkor nem siker�l a fel�p�t�s
    }
    
    public boolean destroy(){//megsemmis�tj�k
        ElementBase entElement = railB.getTrainElement();
        ElementBase inElement = this.getTrainElement();

        if(entElement == null && inElement == null){//ha nincs senki k�zvetlen�l a bej�raton
            if(railB.remove(this))//megpr�b�ljuk sz�tkapcsolni
            	this.remove(railB);
            return true;// ha lehet
        }
        return false;//ha nem lehet megsemmis�teni
    }
}
