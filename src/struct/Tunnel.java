package struct;

/**
 * Az alagutat megvalósító osztály
 */
public class Tunnel extends Rail {
	//belsõ pálya, 3 hosszú + 2 belsõ sín
    private static Rail[] tunnel = new Rail[3];
    private static boolean initialized = false;
    public static int count = 0;
    /**
     * A belsõ sínek létrehozása, csak egyszer fut le
     */
    private  static void init(){//létrehozzuk a belsö sineket.
        for(int i = 0; i < 3; i++)
            tunnel[i] = new Rail();
        tunnel[0].setNext(tunnel[1]);//megfelelöen összekapcsoljuk öket
        tunnel[1].setNext(tunnel[0]);
        tunnel[1].setNext(tunnel[2]);
        tunnel[2].setNext(tunnel[1]);
        initialized = true;//ez után már létrejött a láthatatlan alagút
    }

    /**
     * @return Már létre van-e hozva a belsõ sínpálya
     */
    public static boolean isInitialized(){return initialized;}

    public static Rail[] getInner(){
        return tunnel;
    }

    //Egy bejárat felépítése
    /**
     * Egy bejárat felépítése
     */
    public Tunnel(){
    	//Ha ez az elsõ, akkor a belsõ pálya is
    	if(!initialized)
    		init();

        //A belsõ sín felépítése a szabad végen
        //Maximum 2 alagútvég lehet, így összesen 1 alagút
        if (tunnel[0].setNext(this)) {
            railA = tunnel[0];
        } else if (tunnel[2].setNext(this)) {
            railA = tunnel[2];
        }
    }

    /* (non-Javadoc)
     * @see struct.Rail#setNext(struct.RailElement)
     */
    @Override
    public boolean setNext(RailElement next){//megpróbáljuk beállítani a következö sínnek a kopottat
        if(next == null || railB == next) return true;//ha már be van állítva vagy nem akarunk semmit beállítani
        if(railA != null && railB == null){ //ha railA null, akkor a konstruktorban nem tudta megkapni az alagút semeylik végét. railB-hez ha már van valaki hozzákötve akkor nem tudjuk beállítani
            railB = next;
            return true;//ha betudtuk állítani, be is állítjuk
        }
        return false;
    }

    /**
     * Az alagút felépítése
     * @param entrance A bejáratnál lévõ sín, amihez kapcsolni akarjuk
     * @return Igaz, ha sikerült felépíteni
     */
    public boolean build(RailElement entrance){//megpróbáljuk felépíteni
        if(this.setNext(entrance) && entrance.setNext(this) && count < 2) {// ha fel lehet építeni
            count++;
            return true;//ekkor igazzal térünk vissza
        } else
            entrance.remove(this);//ha nem akkor megszüntetjük a kapcsolatot, majd kívülröl megsemmisítjük
        return false;// ekkor nem sikerül a felépítés
    }
    
    /**
     * Az alagút megsemmisítése
     * @return Igaz, ha sikerült elpusztítani a bejáratot
     */
    public boolean destroy(){//megsemmisítjük
        ElementBase inElement = this.getTrainElement();

        if(inElement == null){//ha nincs senki közvetlenül a bejáraton
            if(railB != null)
                if(railB.remove(this))//megpróbáljuk szétkapcsolni
                    this.remove(railB);
            if(!tunnel[0].remove(this))
                tunnel[2].remove(this);
            count--;
            return true;// ha lehet
        }
        return false;//ha nem lehet megsemmisíteni
    }

    /**
     * Az alagút alaphelyzetbe állítása
     */
    public static void reset(){
        if(initialized) {
            tunnel[0].remove(tunnel[0].getNext(tunnel[1]));
            tunnel[2].remove(tunnel[2].getNext(tunnel[1]));
            tunnel[0].setTrainElement(null);
            tunnel[1].setTrainElement(null);
            tunnel[2].setTrainElement(null);
            count = 0;
        }
    }
}
