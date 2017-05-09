package struct;

import application.*;

/**
 * A mozdonyt megval�s�t� oszt�ly
 */
public class Engine extends ElementBase {
    private boolean active;

    /**
     * @param rail A mozdony kezd�helye
     */
    public  Engine(RailElement rail){
    	child = null;
        actRail = rail;
        prevRail = null;
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
        
        RailElement nextRail = actRail.getNext(prevRail);//magkapjuk az előző pozícióból kiszámított következő pozíciót.


        if(nextRail != null && nextRail.getTrainElement() == null){//ha nem siklik ki, és nincs akivel ütközne
            prevRail = actRail;
            actRail = nextRail;
            actRail.setTrainElement(this);
            prevRail.setTrainElement(child);//beállítjuk az új állapotokat

            if(child != null){
        		child.move();//megpróbáljuk mozgatni a kapcsolt kocsikat
        	}
        }
        else if(nextRail != null && nextRail.getTrainElement() != null) {//ha van valaki előttünk
            crash(nextRail.getTrainElement().getName().substring(1));//ütközés és átadjuk az idját a vonatnak akivel ütköztünk
            nextRail.getTrainElement().crash(this.getName().substring(1));// másik vonaton is meg kell hívni
        }
        else{// már csak az az eset maradt, hogy nincs előttünk sín, ekkor kisiklik a vonat
            derail();
        }
    }

    /**
     * Meg�ll�tja a vonatot
     */
    public void stop(){
    	active = false;
    }//meg lehet állítani

    /**
     * Kisiklik a vonat
     */
    private  void derail(){
        derailed = true;
        active = false;
        Game.gameOver("Vonat kisiklott!");//ekkor is vége lesz a játéknak
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
