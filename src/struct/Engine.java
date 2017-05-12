package struct;

import application.*;

/**
 * A mozdonyt megvalósító osztály
 */
public class Engine extends ElementBase {
    private boolean active;

    /**
     * @param rail A mozdony kezdõhelye
     */
    public  Engine(RailElement rail){
        actRail = rail;
        prevRail = RailElement.notConnected;
    	child = null;
        active = true;
        parent = null;
        this.setName("Engine " + count++);
    }
    
    /* (non-Javadoc)
     * @see struct.ElementBase#move()
     */
    @Override
    public void move(){
    	if(!active) return;//ha nincs vége a játéknak, vagy nem aktív, nem mozgatjuk
        
        RailElement nextRail = actRail.getNext(prevRail);//magkapjuk az elözö pozícióból kiszámított következö pozíciót.


        if(nextRail != RailElement.notConnected && nextRail.getTrainElement() == null){//ha nem siklik ki, és nincs akivel ütközne
            prevRail = actRail;
            actRail = nextRail;
            actRail.setTrainElement(this);
            prevRail.setTrainElement(child);//beállítjuk az új állapotokat

            if(child != null){
        		child.move();//megpróbáljuk mozgatni a kapcsolt kocsikat
        	}
        }
        else if(nextRail.getTrainElement() != null) {//ha van valaki elöttünk
            crash(nextRail.getTrainElement().getName().substring(1));//ütközés és átadjuk az idját a vonatnak akivel ütköztünk
            nextRail.getTrainElement().crash(this.getName().substring(1));// másik vonaton is meg kell hívni
        }
        else{// már csak az az eset maradt, hogy nincs elöttünk sín, ekkor kisiklik a vonat
            derail();
        }
    }

    /**
     * Megállítja a vonatot
     */
    public void stop(){
    	active = false;
    }

    /**
     * Kisiklik a vonat
     */
    private  void derail(){
        derailed = true;
        active = false;
        Game.gameOver("Vonat kisiklott!");//ekkor is vége lesz a játéknak
        Game.points = 0;
    }

	/* (non-Javadoc)
	 * @see struct.ElementBase#giveToken()
	 */
	@Override
	public void giveToken(){
		child.giveToken();
	}

	/* (non-Javadoc)
	 * @see struct.ElementBase#hasToken()
	 */
	@Override
	public boolean hasToken() {
		return false;
	}

	/* (non-Javadoc)
	 * @see struct.ElementBase#takeToken()
	 */
	@Override
	public void takeToken() {
	}
}
