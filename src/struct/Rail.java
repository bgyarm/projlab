package struct;

/**
 * Az egyszer� s�nt meval�s�t� oszt�ly
 */
public class Rail extends RailElement {
    protected RailElement railA;
    protected RailElement railB;
    private Station station;

    /**
     * �llom�s n�lk�li s�n
     */
    public Rail(){
        super();
        station = null;
        railA = notConnected;
        railB = notConnected;
    }
    /**
     * @param s A s�n mellett tal�lhat� �llom�s
     */
    public Rail(Station s) {
    	super();
    	station = s;
        railA = notConnected;
        railB = notConnected;
    }
    /**
     * @param next Mellette l�v� s�nelem
     */
    public Rail(RailElement next){
        super();
        setNext(next);
    }

    	/* (non-Javadoc)
    	 * @see struct.RailElement#setNext(struct.RailElement)
    	 */
    	@Override
    public boolean setNext(RailElement next){
        if(next == null)
            return this.setNext(notConnected);
    	//Csak akkor tudja be�ll�tani a k�vetkez�t, ha van olyan v�ge, ami nincs csatlakoztatva sehova
    	if(next == notConnected || railA == next || railB == next) return true;
        if(railA == notConnected)
    		railA = next;
    	else if (railB == notConnected)
    		railB = next;
    	else
    		return false;
    	//ha valamelyiket siker�lt be�ll�tani, akkor igazzal t�r�nk vissza, egy�bk�nt hamissal
    	return true;
    }

    /* (non-Javadoc)
     * @see struct.RailElement#getNext(struct.RailElement)
     */
    @Override
    public RailElement getNext(RailElement prev){
    	//Ha valaki a prev ir�ny�b�l �rkezik, megadja merre van a k�vetkez� s�n, egy�bk�nt null-t ad vissza
        if(prev == railA)
            return railB;
        if(prev == railB)
            return  railA;
        return null;
    }

    /* (non-Javadoc)
     * @see struct.RailElement#getStation()
     */
    @Override
    public Station getStation(){
        return station;
    }//visszaadja az �llom�st

    /* (non-Javadoc)
     * @see struct.RailElement#isEntrance()
     */
    @Override
    public boolean isEntrance(){//lehet e bej�rat. Ilyenkor valamelyik v�g�nek nullnak kell lenni, ahol bej�n a vonat.
        if((railA == notConnected && railB != notConnected) || (railB == notConnected && railA != notConnected))//azt hogy p�lya sz�l�r�l indul-e azt a vonat l�trehoz�sakor ellen�rizz�k
            return true;
        return false;
    }

    /* (non-Javadoc)
     * @see struct.RailElement#remove(struct.RailElement)
     */
    @Override
    public boolean remove(RailElement element){//sz�tkapcsoljuk az aktu�lis s�nt �s a param�terben kapottat, ha lehet.
    	if(railA == element)
    		railA = notConnected;
    	else if(railB == element)
    		railB = notConnected;
    	else
    		return false;
    	return true;
    }
}
