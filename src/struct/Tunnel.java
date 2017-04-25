package struct;

public class Tunnel extends Rail {
	//belsõ pálya, 3 hosszú + 2 belsõ sín
    private static Rail[] tunnel = new Rail[3];
    private static boolean initialized = false;
    private  static void init(){//létrehozzuk a belsö sineket.
        for(int i = 0; i < 3; i++)
            tunnel[i] = new Rail();
        tunnel[0].setNext(tunnel[1]);//megfelelöen összekapcsoljuk öket
        tunnel[1].setNext(tunnel[0]);
        tunnel[1].setNext(tunnel[2]);
        tunnel[2].setNext(tunnel[1]);
        initialized = true;//ez után már létrejött a láthatatlan alagút
    }

    public static boolean isInitialized(){return initialized;}

    public static Rail[] getInner(){
        return tunnel;
    }

    //Egy bejárat felépítése
    public Tunnel(){
    	//Ha ez az elsõ, akkor a belsõ pálya is
    	if(!initialized)
    		init();
    	//A belsõ sín felépítése a szabad végen
    	//Maximum 2 alagútvég lehet, így összesen 1 alagút
    	if(tunnel[0].setNext(this))
    		railA = tunnel[0];
    	else
    		if(tunnel[2].setNext(this))
    			railA = tunnel[2];
    }

    @Override
    public boolean setNext(RailElement next){//megpróbáljuk beállítani a következö sínnek a kopottat
        if(next == null || railB == next) return true;//ha már be van állítva vagy nem akarunk semmit beállítani
        if(railA != null && railB == null){ //ha railA null, akkor a konstruktorban nem tudta megkapni az alagút semeylik végét. railB-hez ha már van valaki hozzákötve akkor nem tudjuk beállítani
            railB = next;
            return true;//ha betudtuk állítani, be is állítjuk
        }
        return false;
    }

    public boolean build(RailElement entrance){//megpróbáljuk felépíteni
        if(this.setNext(entrance) && entrance.setNext(this))// ha fel lehet építeni
            return true;//ekkor igazzal térünk vissza
        else
            entrance.remove(this);//ha nem akkor megszüntetjük a kapcsolatot, majd kívülröl megsemmisítjük
        return false;// ekkor nem sikerül a felépítés
    }
    
    public boolean destroy(){//megsemmisítjük
        ElementBase entElement = railB.getTrainElement();
        ElementBase inElement = this.getTrainElement();

        if(entElement == null && inElement == null){//ha nincs senki közvetlenül a bejáraton
            if(railB.remove(this))//megpróbáljuk szétkapcsolni
            	this.remove(railB);
            return true;// ha lehet
        }
        return false;//ha nem lehet megsemmisíteni
    }
}
