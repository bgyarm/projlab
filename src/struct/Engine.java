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
    	if(!active) return;//ha nincs v�ge a j�t�knak, vagy nem akt�v, nem mozgatjuk
        
        RailElement nextRail = actRail.getNext(prevRail);//magkapjuk az el�z� poz�ci�b�l kisz�m�tott k�vetkez� poz�ci�t.


        if(nextRail != RailElement.notConnected && nextRail.getTrainElement() == null){//ha nem siklik ki, �s nincs akivel �tk�zne
            prevRail = actRail;
            actRail = nextRail;
            actRail.setTrainElement(this);
            prevRail.setTrainElement(child);//be�ll�tjuk az �j �llapotokat

            if(child != null){
        		child.move();//megpr�b�ljuk mozgatni a kapcsolt kocsikat
        	}
        }
        else if(nextRail.getTrainElement() != null) {//ha van valaki el�tt�nk
            crash(nextRail.getTrainElement().getName().substring(1));//�tk�z�s �s �tadjuk az idj�t a vonatnak akivel �tk�zt�nk
            nextRail.getTrainElement().crash(this.getName().substring(1));// m�sik vonaton is meg kell h�vni
        }
        else{// m�r csak az az eset maradt, hogy nincs el�tt�nk s�n, ekkor kisiklik a vonat
            derail();
        }
    }

    /**
     * Meg�ll�tja a vonatot
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
        Game.gameOver("Vonat kisiklott!");//ekkor is v�ge lesz a j�t�knak
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
