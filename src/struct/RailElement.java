package struct;

/**
 * A s�nelemekhez absztrakt oszt�ly
 */
public abstract class RailElement {
    ElementBase trainElement;

    static final public RailElement notConnected  = new RailElement() {

        /* (non-Javadoc)
         * @see struct.RailElement#setNext(struct.RailElement)
         */
        @Override
        public boolean setNext(RailElement next) {
            return false;
        }

        /* (non-Javadoc)
         * @see struct.RailElement#getNext(struct.RailElement)
         */
        @Override
        public RailElement getNext(RailElement prev) {
            return null;
        }
    };

    public RailElement(){trainElement = null;}

	/**
	 * @param next mellette l�v� s�nelem
	 * @return siker�lt-e �sszekapcsolni
	 */
	public abstract boolean setNext(RailElement next);

    /**
     * @param prev A halad�si ir�nnyal ellent�tesen l�v� szomsz�dos s�n
     * @return A halad�si ir�nyban l�v� szomsz�dos s�n
     */
    public abstract RailElement getNext(RailElement prev);

    /**
     * @param te A s�nen l�v� vonatelem
     */
    public void setTrainElement(ElementBase te){trainElement = te;}
    /**
     * @return A s�nen l�v� vonatelem
     */
    public ElementBase getTrainElement(){
        return trainElement;
    }
    /**
     * @return Lehet-e p�lyabej�rat (ahol a vonatok indulnak)
     */
    public boolean isEntrance(){return false;}

    /**
     * @return Mindk�t v�ge null-e
     */
    public boolean isBothNull(){
        if(this.getNext(notConnected) == notConnected)
            return true;
        return false;
    }
    /**
     * @param element A sz�tkapcsoland� s�n
     * @return Siker�lt-e sz�tkapcsolni a k�t s�nt
     */
    public boolean remove(RailElement element){return true;}

    /**
     * @return A s�nelem mellett tal�lhat� �llom�s. null, ha nincs
     */
    public Station getStation() { return null; }

}
