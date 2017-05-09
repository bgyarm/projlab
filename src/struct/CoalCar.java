package struct;

/**
 * Szeneskocsit megvalósító osztály. Nála nem lehet token, nem szállíthat utasokat.
 */
public class CoalCar extends ElementBase {

	/**
	 * @param parent Az õt húzó elem
	 */
	public  CoalCar(ElementBase parent){
		child = null;
		this.parent = parent;
		parent.child = this;

		//teszteléshez
		this.setName("CoalCar " + count++);
	}

	//õ egyszerûen csak húzza a következõ kocsit
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
