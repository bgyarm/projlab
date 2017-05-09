package struct;

/**
 * A sínelemekhez absztrakt osztály
 */
public abstract class RailElement {
    ElementBase trainElement;
    static protected RailElement notConnected = new RailElement() {
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
	 * @param next mellette lévö sínelem
	 * @return sikerült-e összekapcsolni
	 */
	public abstract boolean setNext(RailElement next);

    /**
     * @param prev A haladási iránnyal ellentétesen lévö szomszédos sín
     * @return A haladási irányban lévö szomszédos sín
     */
    public abstract RailElement getNext(RailElement prev);

    /**
     * @param te A sínen lévö vonatelem
     */
    public void setTrainElement(ElementBase te){trainElement = te;}
    /**
     * @return A sínen lévö vonatelem
     */
    public ElementBase getTrainElement(){
        return trainElement;
    }
    /**
     * @return Lehet-e pályabejárat (ahol a vonatok indulnak)
     */
    public boolean isEntrance(){return false;}
    /**
     * @param element A sz?tkapcsoland? sín
     * @return Sikerült-e szétkapcsolni a két sínt
     */
    public boolean remove(RailElement element){return false;}

    /**
     * @return A sínelem mellett található állomás. null, ha nincs
     */
    public Station getStation() { return null; }

}
