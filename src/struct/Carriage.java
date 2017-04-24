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
        
        //tesztel�shez
        this.setName("Carriage " + count++);
    }

    public Color getColor(){return color;}
    public boolean hasPassangers(){return !empty;}

    public void move() {

        prevRail = actRail;
    	actRail = parent.prevRail;
    	if(actRail != null)
    		actRail.setTrainElement(this);
    	if(prevRail != null)
    	    prevRail.setTrainElement(child);
    	boolean tokenpass = false;
    	//RailElement prevRail = actRail;
        
        //n�zz�k meg, van-e itt �llom�s, ahov� �rkezt�nk
        //ha van �s egyezik a sz�n�nk...
		Station s = null;
		if(actRail != null)
        	s = actRail.getStation();
        if (s != null) {
            Color c = s.getColor();
            if(c.equals(color)){
            	//ha �res a kocsi �s az �llom�son vannak emberek, vegy�k fel �ket
            	//ekkor a tokent ellen�rizn�nk kell
            	if(empty && s.hasPassengers()){
            		empty = false;
            		s.setPassengers(false);
            		token = searchToken();
            	}
            	//ha nem volt �res a kocsink, akkor rakjuk le az utasokat �s passzoljuk tov�bb a tokent
            	else if (!empty && token) {
                     empty = true;
                     token = false;
                     if(child != null)
                    	 tokenpass = true;
                 }
            }
        }
        
        //az el�l l�v� kocsinak van els�bbs�ge az �llom�sn�l, ez�rt van a mozgat�s a v�g�n
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
		//ha n�lunk van, megtal�ltuk
		if(token) return true;
		boolean b = false;
		//elindulunk a mozdony ir�ny�ba
		ElementBase iter = parent;
		//ha tal�lunk tokent, vagy a mozdonyhoz �r�nk, meg�llunk
		while(iter != null && !b){
			b = iter.hasToken();
			//ha tal�ltunk tokent a mozdonyig, akkor nem lehet n�lunk a token
			if(b) return false;
			iter = iter.parent;
		}
		//ha nem tal�ltunk, n�zz�k meg a vonat m�sik v�ge fel�
		iter = child;
		//ha tal�lunk tokent, vagy a v�g�re �rt�nk, meg�llunk
		while(!b && iter != null){
			b = iter.hasToken();
			//ha tal�ltunk tokent, akkor elvessz�k t�le, hiszen mi el�r�bb �llunk
			if(b){
				iter.takeToken();
				return true;
			}
			iter = iter.child;
		}
		
		//ha egyik ir�nyban sem tal�ltunk tokent, akkor kisaj�t�tjuk
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
