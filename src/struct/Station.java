package struct;

/**
 * Az �llom�st megval�s�t� oszt�ly. Csak s�n tagjak�nt szereplhet.
 */
public class Station{
    private Color c;
    private boolean passengers;

    /**
     * @param c Az �llom�s sz�ne
     * Ekkor nincsenek utasok
     */
    public Station(Color c){
    	this.c = c;
    	passengers = false;
    }
    
    /**
     * @param c Az �llom�s sz�ne
     * @param p Vannek-e ott utasok
     */
    public Station(Color c, boolean p){
    	this.c = c;
    	passengers = p;
    }

	/**
	 * @return Az �llom�s sz�ne
	 */
	public Color getColor(){
        return c;
    }//visszaadja a sz�n�t
    
    //vannak-e utasok
    /**
     * @return Igaz, ha az �llom�son vannak utasok.
     */
    public boolean hasPassengers(){
    	return passengers;
    }
    
    //ki�r�t�sn�l hasznos, esetleg ha termel�dhetnek ide utasok
    /**
     * @param p Az �llom�son vannak-e utasok
     */
    public void setPassengers(boolean p){
    	passengers = p;
    }
}
