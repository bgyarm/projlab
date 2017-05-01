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
    	//Csak akkor tudja beállítani a következõt, ha van olyan vége, ami nincs csatlakoztatva sehova
    	if(next == null || railA == next || railB == next) return true;
        if(railA == null)
    		railA = next;
    	else if (railB == null)
    		railB = next;
    	else
    		return false;
    	//ha valamelyiket sikerült beállítani, akkor igazzal térünk vissza, egyébként hamissal
    	return true;
    }

    @Override
    public RailElement getNext(RailElement prev){
    	//Ha valaki a prev irányából érkezik, megadja merre van a következõ sín, egyébként null-t ad vissza
        if(prev == railA)
            return railB;
        if(prev == railB)
            return  railA;
        return null;
    }

    public Station getStation(){
        return station;
    }//visszaadja az állomást

    @Override
    public boolean isEntrance(){//lehet e bejárat. Ilyenkor valamelyik végének nullnak kell lenni, ahol bejön a vonat.
        if(railA == null || railB == null)//azt hogy pálya szélér?l indul e azt a vonat létrehozásakor ellen?rizzük
            return true;
        return false;
    }

    @Override
    public boolean remove(RailElement element){//szétkapcsoljuk az aktuális sínt és a paraméterben kapottat, ha lehet.
    	if(railA == element)
    		railA = null;
    	else if(railB == element)
    		railB = null;
    	else
    		return false;
    	return true;
    }
}
