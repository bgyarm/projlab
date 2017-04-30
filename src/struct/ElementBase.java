package struct;

import application.Game;

public abstract class ElementBase {
    protected RailElement actRail;
    protected RailElement prevRail;
    protected ElementBase child;
    protected ElementBase parent;
    private String crashedWith;
    protected boolean derailed;
    protected static int count = 0;
    protected String name;
    
    public void setName(String n){
    	name = n;
    }

    public String getName(){return name;}

    public void crash(String id){//ütközés valamelyik vonattal. Az id annak a vonatnak az azonosítója (neve) amelyikkel ütközik egy másik vonat.
    	crashedWith = id;
    	try {
            ((Engine) this).stop();//megállítjuk hogy ne mozogjon tovább a vonat.
        }catch (Exception e){}
        Game.gameOver();//játéknak vége lesz
    }
    public abstract void move();//mozgatás, minden esetben felülírjuk.

    public boolean getDerailed(){//lekéri az elsö vonatelemnek állapotát, ez csak egy engine lehet.
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);//sor elejére megyünk
        return eb.derailed;
    }
    public String getCrshed(){//lekéri az engine állapotát
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);
        return eb.crashedWith;
    }
    public RailElement getActRail(){return actRail;}
    public RailElement getPrevRail() {return prevRail;}
    public abstract void giveToken();
    public abstract void takeToken();
    public abstract boolean hasToken();
}
