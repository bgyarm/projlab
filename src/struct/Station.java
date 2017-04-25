package struct;

public class Station{
    private Color c;
    private boolean passengers;

    public Station(Color c){
    	this.c = c;
    	passengers = false;
    }
    
    public Station(Color c, boolean p){
    	this.c = c;
    	passengers = p;
    }

	public Color getColor(){
        return c;
    }//visszaadja a sz�n�t
    
    //vannak-e utasok
    public boolean hasPassengers(){
    	return passengers;
    }
    
    //ki�r�t�sn�l hasznos, esetleg ha termel�dhetnek ide utasok
    public void setPassengers(boolean p){
    	passengers = p;
    }
}
