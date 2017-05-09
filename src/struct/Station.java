package struct;

/**
 * Az állomást megvalósító osztály. Csak sín tagjaként szereplhet.
 */
public class Station{
    private Color c;
    private boolean passengers;

    /**
     * @param c Az állomás színe
     * Ekkor nincsenek utasok
     */
    public Station(Color c){
    	this.c = c;
    	passengers = false;
    }
    
    /**
     * @param c Az állomás színe
     * @param p Vannek-e ott utasok
     */
    public Station(Color c, boolean p){
    	this.c = c;
    	passengers = p;
    }

	/**
	 * @return Az állomás színe
	 */
	public Color getColor(){
        return c;
    }//visszaadja a színét
    
    //vannak-e utasok
    /**
     * @return Igaz, ha az állomáson vannak utasok.
     */
    public boolean hasPassengers(){
    	return passengers;
    }
    
    //kiürítésnél hasznos, esetleg ha termelõdhetnek ide utasok
    /**
     * @param p Az állomáson vannak-e utasok
     */
    public void setPassengers(boolean p){
    	passengers = p;
    }
}
