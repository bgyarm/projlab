package struct;

import application.Game;

/**
 * Utassz�ll�t� kocsit megval�s�t� oszt�ly
 */
public class Carriage extends ElementBase {
    private boolean empty;
    private boolean token;
    private Color color;

    /**
     * @param parent Az �t h�z� elem
     * @param color A kocsi sz�ne
     * @param passengers Vannek-e utasok a kocsin 
     */
    public  Carriage(ElementBase parent, Color color, boolean passengers){
    	child = null;
        this.color = color;
        this.empty = !passengers;
        this.token = false;
        this.parent = parent;
        parent.child = this;
        
        //tesztel�shez
        this.setName("Carriage " + count++);
    }

    /**
     * @return A kocsi sz�ne
     */
    public Color getColor(){return color;}
    /**
     * @return Vannek-e utasok a kocsin
     */
    public boolean hasPassangers(){return !empty;}

    /* (non-Javadoc)
     * @see struct.ElementBase#move()
     */
    public void move() {

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
            	//ha üres a kocsi és az állomáson vannak emberek, vegyük fel őket
            	//ekkor a tokent ellenőriznünk kell
            	if(empty && s.hasPassengers()){
            	    Game.points++;//növeljük a pontokat
            		empty = false;
            		s.setPassengers(false);
            		token = searchToken();
            	}
            	//ha nem volt üres a kocsink, akkor rakjuk le az utasokat és passzoljuk tovább a tokent
            	else if (!empty && token) {
            		 Game.points += 2;//növeljük a megszerzett pontokat
                     empty = true;
                     token = false;
                     if(child != null)
                    	 tokenpass = true;
                 }
            }
        }
        
        //az elöl lévő kocsinak van elsőbbsége az állomásnál, ezért van a mozgatás a végén
        if(child != null){
            if(tokenpass)
                child.giveToken();
    		child.move();
    	}
    }

	/* (non-Javadoc)
	 * @see struct.ElementBase#giveToken()
	 */
	@Override
	public void giveToken() {
		token = true;		
	}
	
	/**
	 * @return Ennél a kocsinál lehet-e a token
	 */
	public boolean searchToken(){
		//ha nálunk van, megtaláltuk
		if(token) return true;
		boolean b = false;
		//elindulunk a mozdony irányúba
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
			//ha találtunk tokent, akkor elvesszük tőle, hiszen mi előrébb állunk
			if(b){
				iter.takeToken();
				return true;
			}
			iter = iter.child;
		}
		
		//ha egyik irányban sem találtunk tokent, akkor kisajátítjuk
		return true;
	}

	/* (non-Javadoc)
	 * @see struct.ElementBase#hasToken()
	 */
	@Override
	public boolean hasToken() {
		return token;
	}
	
	/* (non-Javadoc)
	 * @see struct.ElementBase#takeToken()
	 */
	@Override
	public void takeToken(){
		token = false;
	}
}
