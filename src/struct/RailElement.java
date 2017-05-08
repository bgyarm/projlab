package struct;

public abstract class RailElement {
    ElementBase trainElement;
    protected RailElement notConnected;
	
	public abstract boolean setNext(RailElement next);

    public abstract RailElement getNext(RailElement prev);

    public void setTrainElement(ElementBase te){trainElement = te;}
    public ElementBase getTrainElement(){
        return trainElement;
    }
    public boolean isEntrance(){return false;}
    public boolean remove(RailElement element){return false;}

    public Station getStation() { return null; } //csak Rail mellett lehet station. Railben override szükséges.

}
