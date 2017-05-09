package struct;

import application.Game;

/**
 * A mozg� elemekhez absztrakt oszt�ly (mozdony, szeneskocsi, utaskocsi)
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
     * @param n n�v
     */
    public void setName(String n){
    	name = n;
    }

    /**
     * @return n�v
     */
    public String getName(){return name;}

    /**
     * @param id A m�sik vonat neve
     */
    public void crash(String id){//�tk�z�s valamelyik vonattal. Az id annak a vonatnak az azonos�t�ja (neve) amelyikkel �tk�zik egy m�sik vonat.
    	crashedWith = id;
    	try {
            ((Engine) this).stop();//meg�ll�tjuk hogy ne mozogjon tov�bb a vonat.
        }catch (Exception e){}
        Game.gameOver("Vonatok �tk�ztek!!");//j�t�knak v�ge lesz
    }
    /**
     * A mozgat�s�rt felel�s f�ggv�ny, minden oszt�lynak maga kell megval�s�tania.
     * A mozdony h�zza a m�g�tte l�v�t, valamint ellen�rzi, hogy nekimegy-e valaminek.
     * A kocsik h�zz�k a m�g�tt�k l�v�ket, �s tov�bbadj�k a tokent, ha nincs r� sz�ks�g�k.
     * �llom�sokon az utaskocsik le/felsz�ll�tj�k az utasokat sz�ks�g eset�n.
     */
    public abstract void move();//mozgat�s, minden esetben fel�l�rjuk.

    /**
     * @return Kisiklott-e a vonat
     */
    public boolean getDerailed(){//lek�ri az els� vonatelemnek �llapot�t, ez csak egy engine lehet.
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);//sor elej�re megy�nk
        return eb.derailed;
    }
    /**
     * @return �tk�z�tt-e a vonat, ha igen, akkor kivel
     */
    public String getCrshed(){//lek�ri az engine �llapot�t
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);
        return eb.crashedWith;
    }
    /**
     * @return A vonatelem melyik s�nelemen �ll
     */
    public RailElement getActRail(){return actRail;}
    /**
     * @return Az el�z� s�nelem
     */
    public RailElement getPrevRail() {return prevRail;}
    /**
     * A tokent ennek a vonatelemnek adja. Mozdony/szeneskocsi eset�n egyb�l tov�bbadja, ha tudja
     */
    public abstract void giveToken();
    /**
     * Elveszi a tokent ett�l a vonatelemt�l
     */
    public abstract void takeToken();
    /**
     * @return Igaz, ha enn�l van a token
     */
    public abstract boolean hasToken();
}
