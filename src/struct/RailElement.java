package struct;

/**
 * A sínelemekhez absztrakt osztály
 */
public abstract class RailElement {
    ElementBase trainElement;
    protected RailElement notConnected;
	
	/**
	 * @param next mellette lévõ sínelem
	 * @return sikerült-e összekapcsolni
	 */
	public abstract boolean setNext(RailElement next);

    /**
     * @param prev A haladási iránnyal ellentétesen lévõ szomszédos sín
     * @return A haladási irányban lévõ szomszédos sín
     */
    public abstract RailElement getNext(RailElement prev);

    /**
     * @param te A sínen lévõ vonatelem
     */
    public void setTrainElement(ElementBase te){trainElement = te;}
    /**
     * @return A sínen lévõ vonatelem
     */
    public ElementBase getTrainElement(){
        return trainElement;
    }
    /**
     * @return Lehet-e pályabejárat (ahol a vonatok indulnak)
     */
    public boolean isEntrance(){return false;}
    /**
     * @param element A szétkapcsolandó sín
     * @return Sikerült-e szétkapcsolni a két sínt
     */
    public boolean remove(RailElement element){return false;}

    /**
     * @return A sínelem mellett található állomás. null, ha nincs
     */
    public Station getStation() { return null; } //csak Rail mellett lehet station. Railben override szÃ¼ksÃ©ges.

}
