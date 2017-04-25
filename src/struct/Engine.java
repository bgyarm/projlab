package struct;

import application.*;

public class Engine extends ElementBase {
    private boolean active;

    public  Engine(RailElement rail){
    	child = null;
        actRail = rail;
        prevRail = null;
        active = true;
        
        parent = null;
        this.setName("Engine " + count++);
    }
    
    @Override
    public void move(){
    	if(!active || Game.isOver()) return;//ha nincs vége a játéknak, vagy nem aktív, nem mozgatjuk
        //Logger.printMethodCall("Engine","move");
        
        RailElement nextRail = actRail.getNext(prevRail);//magkapjuk az előző pozícióból kiszámított következő pozíciót.
        

        //Logger.printAction("Engine","move","Changed previous rail to actual rail and updated actual rail");

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
            //Logger.printAction("Engine","move","Train derailed");
        }
    }

    public void stop(){
    	active = false;
    }//me glehet állítani

    private  void derail(){
        //Logger.printMethodCall("Engine","derail");
        derailed = true;
        active = false;
        Game.gameOver();//ekkor is vége lesz a játéknak
    }

	@Override
	public void giveToken(){
		child.giveToken();
	}

	@Override
	public boolean hasToken() {
		return false;
	}

	@Override
	public void takeToken() {
	}
}
