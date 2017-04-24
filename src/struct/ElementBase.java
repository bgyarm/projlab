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

    //Szerintem ennyi elég a crash-hez, illetve a gameOver kaphat String paraméter, 
    //És abból ki tudja írni, hogy crash volt
    //Esetleg a grafikai megjelenítésnél majd oda tudunk dobni valamit
    public void crash(String id){
    	crashedWith = id;
    	try {
            ((Engine) this).stop();
        }catch (Exception e){}
        Game.gameOver();
    }
    public abstract void move();
    
    public void destroy(){
    }
    public boolean getDerailed(){
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);
        return eb.derailed;
    }
    public String getCrshed(){
        ElementBase eb;
        for(eb = this; this.parent != null; eb = this.parent);
        return eb.crashedWith;
    }
    public RailElement getActRail(){return actRail;}
    public abstract void giveToken();
    public abstract void takeToken();
    public abstract boolean hasToken();
}
