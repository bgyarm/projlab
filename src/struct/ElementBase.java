package struct;

import application.Game;

/**
 * A mozgó elemekhez absztrakt osztály (mozdony, szeneskocsi, utaskocsi)
 */
public abstract class ElementBase {
    protected RailElement actRail;
    protected RailElement prevRail;
    protected ElementBase child;
    protected ElementBase parent;
    private String crashedWith;
    protected boolean derailed;
    protected static int count = 0;
    protected String name;
    
    /**
     * @param n név
     */
    public void setName(String n){
    	name = n;
    }

    /**
     * @return név
     */
    public String getName(){return name;}

    /**
     * @param id A másik vonat neve
     */
    public void crash(String id){//ütközés valamelyik vonattal. Az id annak a vonatnak az azonosítója (neve) amelyikkel ütközik egy másik vonat.
    	crashedWith = id;
    	try {
            ((Engine) this).stop();//megállítjuk hogy ne mozogjon tovább a vonat.
        }catch (Exception e){}
        Game.gameOver("Vonatok ütköztek!!");//játéknak vége lesz
    }
    /**
     * A mozgatásért felelõs függvény, minden osztálynak maga kell megvalósítania.
     * A mozdony húzza a mögötte lévõt, valamint ellenõrzi, hogy nekimegy-e valaminek.
     * A kocsik húzzák a mögöttük lévõket, és továbbadják a tokent, ha nincs rá szükségük.
     * Állomásokon az utaskocsik le/felszállítják az utasokat szükség esetén.
     */
    public abstract void move();//mozgatás, minden esetben felülírjuk.

    /**
     * @return Kisiklott-e a vonat
     */
    public boolean getDerailed(){//lekéri az elsö vonatelemnek állapotát, ez csak egy engine lehet.
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);//sor elejére megyünk
        return eb.derailed;
    }
    /**
     * @return Ütközött-e a vonat, ha igen, akkor kivel
     */
    public String getCrshed(){//lekéri az engine állapotát
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);
        return eb.crashedWith;
    }
    /**
     * @return A vonatelem melyik sínelemen áll
     */
    public RailElement getActRail(){return actRail;}
    /**
     * @return Az elõzõ sínelem
     */
    public RailElement getPrevRail() {return prevRail;}
    /**
     * A tokent ennek a vonatelemnek adja. Mozdony/szeneskocsi esetén egybõl továbbadja, ha tudja
     */
    public abstract void giveToken();
    /**
     * Elveszi a tokent ettõl a vonatelemtõl
     */
    public abstract void takeToken();
    /**
     * @return Igaz, ha ennél van a token
     */
    public abstract boolean hasToken();
}
