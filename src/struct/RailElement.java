package struct;

/**
 * A s�nelemekhez absztrakt oszt�ly
 */
public abstract class RailElement {
    ElementBase trainElement;
    static protected RailElement notConnected = new RailElement() {//nem csatlakozott sínt jelzi
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
     * @param element A sz�tkapcsoland� s�n
     * @return Siker�lt-e sz�tkapcsolni a k�t s�nt
     */
    public boolean remove(RailElement element){return false;}

    /**
     * @return A s�nelem mellett tal�lhat� �llom�s. null, ha nincs
     */
    public Station getStation() { return null; } //csak Rail mellett lehet station. Railben override szükséges.

}
