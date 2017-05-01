package struct;

public class Rail extends RailElement {
    protected RailElement railA;
    protected RailElement railB;
    private Station station;

    public Rail(){
    	trainElement = null;
        station = null;
        railA = null;
        railB = null;
    }
    public Rail(Station s) {
    	super();
    	station = s;
    }
    public Rail(RailElement next){
        super();
        setNext(next);
    }

    	@Override
    public boolean setNext(RailElement next){
    	//Csak akkor tudja be�ll�tani a k�vetkez�t, ha van olyan v�ge, ami nincs csatlakoztatva sehova
    	if(next == null || railA == next || railB == next) return true;
        if(railA == null)
    		railA = next;
    	else if (railB == null)
    		railB = next;
    	else
    		return false;
    	//ha valamelyiket siker�lt be�ll�tani, akkor igazzal t�r�nk vissza, egy�bk�nt hamissal
    	return true;
    }

    @Override
    public RailElement getNext(RailElement prev){
    	//Ha valaki a prev ir�ny�b�l �rkezik, megadja merre van a k�vetkez� s�n, egy�bk�nt null-t ad vissza
        if(prev == railA)
            return railB;
        if(prev == railB)
            return  railA;
        return null;
    }

    public Station getStation(){
        return station;
    }//visszaadja az �llom�st

    @Override
    public boolean isEntrance(){//lehet e bej�rat. Ilyenkor valamelyik v�g�nek nullnak kell lenni, ahol bej�n a vonat.
        if(railA == null || railB == null)//azt hogy p�lya sz�l�r?l indul e azt a vonat l�trehoz�sakor ellen?rizz�k
            return true;
        return false;
    }

    @Override
    public boolean remove(RailElement element){//sz�tkapcsoljuk az aktu�lis s�nt �s a param�terben kapottat, ha lehet.
    	if(railA == element)
    		railA = null;
    	else if(railB == element)
    		railB = null;
    	else
    		return false;
    	return true;
    }
}
