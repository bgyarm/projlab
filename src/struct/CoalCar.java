package struct;

/**
 * Szeneskocsit megval�s�t� oszt�ly. N�la nem lehet token, nem sz�ll�that utasokat.
 */
public class CoalCar extends ElementBase {

	/**
	 * @param parent Az �t h�z� elem
	 */
	public  CoalCar(ElementBase parent){
		child = null;
		this.parent = parent;
		parent.child = this;

		//tesztel�shez
		this.setName("CoalCar " + count++);
	}

	//� egyszer�en csak h�zza a k�vetkez� kocsit
	/* (non-Javadoc)
	 * @see struct.ElementBase#move()
	 */
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

	
	/* (non-Javadoc)
	 * @see struct.ElementBase#giveToken()
	 */
	@Override
	public void giveToken() {
		if(child != null)
			child.giveToken();
	}

	/* (non-Javadoc)
	 * @see struct.ElementBase#takeToken()
	 */
	@Override
	public void takeToken() {
	}

	/* (non-Javadoc)
	 * @see struct.ElementBase#hasToken()
	 */
	@Override
	public boolean hasToken() {
		return false;
	}

}
