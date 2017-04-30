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

    public void crash(String id){//�tk�z�s valamelyik vonattal. Az id annak a vonatnak az azonos�t�ja (neve) amelyikkel �tk�zik egy m�sik vonat.
    	crashedWith = id;
    	try {
            ((Engine) this).stop();//meg�ll�tjuk hogy ne mozogjon tov�bb a vonat.
        }catch (Exception e){}
        Game.gameOver();//j�t�knak v�ge lesz
    }
    public abstract void move();//mozgat�s, minden esetben fel�l�rjuk.

    public boolean getDerailed(){//lek�ri az els� vonatelemnek �llapot�t, ez csak egy engine lehet.
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);//sor elej�re megy�nk
        return eb.derailed;
    }
    public String getCrshed(){//lek�ri az engine �llapot�t
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
