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
    	if(!active || Game.isOver()) return;
        //Logger.printMethodCall("Engine","move");
        
        RailElement nextRail = actRail.getNext(prevRail);
        

        //Logger.printAction("Engine","move","Changed previous rail to actual rail and updated actual rail");

        if(nextRail != null && nextRail.getTrainElement() == null){
            prevRail = actRail;
            actRail = nextRail;
            actRail.setTrainElement(this);
            prevRail.setTrainElement(child);

            if(child != null){
        		child.move();
        	}
        }
        else if(nextRail != null && nextRail.getTrainElement() != null) {
            crash(nextRail.getTrainElement().getName().substring(1));
            nextRail.getTrainElement().crash(this.getName().substring(1));
        }
        else{
            derail();
            //Logger.printAction("Engine","move","Train derailed");
        }
    }

    public void stop(){
    	active = false;
    }

    private  void derail(){
        //Logger.printMethodCall("Engine","derail");
        derailed = true;
        active = false;
        Game.gameOver();
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
