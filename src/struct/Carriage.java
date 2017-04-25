package struct;

public class Carriage extends ElementBase {
    private boolean empty;
    private boolean token;
    private Color color;

    public  Carriage(ElementBase parent, Color color, boolean passangers){
    	child = null;
        this.color = color;
        this.empty = !passangers;
        this.token = false;
        this.parent = parent;
        parent.child = this;
        
        //teszteléshez
        this.setName("Carriage " + count++);
    }

    public Color getColor(){return color;}
    public boolean hasPassangers(){return !empty;}

    public void move() {//mozgatjuk a vagonokat.

        prevRail = actRail;//beállítjuk a dolgokat
    	actRail = parent.prevRail;
    	if(actRail != null)
    		actRail.setTrainElement(this);
    	if(prevRail != null)
    	    prevRail.setTrainElement(child);
    	boolean tokenpass = false;//csak akkor adjuk át a tokent ha kiürült egy vagon. Alapból ezért false
        
        //nézzük meg, van-e itt állomás, ahová érkeztünk
        //ha van és egyezik a színünk...
		Station s = null;
		if(actRail != null)
        	s = actRail.getStation();
        if (s != null) {
            Color c = s.getColor();
            if(c.equals(color)){
            	//ha üres a kocsi és az állomáson vannak emberek, vegyük fel õket
            	//ekkor a tokent ellenõriznünk kell
            	if(empty && s.hasPassengers()){
            		empty = false;
            		s.setPassengers(false);
            		token = searchToken();
            	}
            	//ha nem volt üres a kocsink, akkor rakjuk le az utasokat és passzoljuk tovább a tokent
            	else if (!empty && token) {
                     empty = true;
                     token = false;
                     if(child != null)
                    	 tokenpass = true;
                 }
            }
        }
        
        //az elöl lévõ kocsinak van elsõbbsége az állomásnál, ezért van a mozgatás a végén
        if(child != null){
            if(tokenpass)
                child.giveToken();
    		child.move();
    	}
    }

	@Override
	public void giveToken() {
		token = true;		
	}
	
	public boolean searchToken(){
		//ha nálunk van, megtaláltuk
		if(token) return true;
		boolean b = false;
		//elindulunk a mozdony irányába
		ElementBase iter = parent;
		//ha találunk tokent, vagy a mozdonyhoz érünk, megállunk
		while(iter != null && !b){
			b = iter.hasToken();
			//ha találtunk tokent a mozdonyig, akkor nem lehet nálunk a token
			if(b) return false;
			iter = iter.parent;
		}
		//ha nem találtunk, nézzük meg a vonat másik vége felé
		iter = child;
		//ha találunk tokent, vagy a végére értünk, megállunk
		while(!b && iter != null){
			b = iter.hasToken();
			//ha találtunk tokent, akkor elvesszük tõle, hiszen mi elõrébb állunk
			if(b){
				iter.takeToken();
				return true;
			}
			iter = iter.child;
		}
		
		//ha egyik irányban sem találtunk tokent, akkor kisajátítjuk
		return true;
	}

	@Override
	public boolean hasToken() {
		return token;
	}
	
	@Override
	public void takeToken(){
		token = false;
	}
}
