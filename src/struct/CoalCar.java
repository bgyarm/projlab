package struct;

public class CoalCar extends ElementBase {

	public  CoalCar(ElementBase parent){
		child = null;
		this.parent = parent;
		parent.child = this;

		//tesztel�shez
		this.setName("CoalCar " + count++);
	}

	//� egyszer�en csak h�zza a k�vetkez� kocsit
	@Override
	public void move() {
        prevRail = actRail;
        actRail = parent.prevRail;
        if(actRail != null)
            actRail.setTrainElement(this);
        if(prevRail != null)
            prevRail.setTrainElement(child);
		if(child != null){
    		child.move();
    	}
	}

	
	//Ha valaki neki akarja adni a tokent, egyb�l tov�bbadja
	@Override
	public void giveToken() {
		if(child != null)
			child.giveToken();
	}

	@Override
	public void takeToken() {
	}

	@Override
	public boolean hasToken() {
		return false;
	}

}
