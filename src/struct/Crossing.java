package struct;

public class Crossing extends RailElement {
	
    private RailElement[] cross;
    
    public Crossing(){
    	cross = new RailElement[4];
    	trainElement = null;
    	notConnected = new RailElement() {
            @Override
            public boolean setNext(RailElement next) {
                return true;
            }

            @Override
            public RailElement getNext(RailElement prev) {
                return null;
            }
        };
    }

    
    //A szemköztit adja vissza. Ha egyikkel sem illeszkedik, akkor null-t
	@Override
	public RailElement getNext(RailElement prev) {
        for(int i = 0; i < 4; i++)
            if(cross[i] == notConnected)
                return null;
        for(int i = 0; i < 4; i++)
            if(prev == cross[i])
                return cross[i + (i%2 == 0 ? 1 : -1)];
		return null;
	}

	//Beállítja a kapott sínt szomszédosnak
    @Override
	public boolean setNext(RailElement element) {
		if(element == null)
            return setNext(notConnected);
		for(int i = 0; i < 4; i++)
            if(cross[i] == element) return true;

		for (int i = 0; i < 2; i++)
            if (cross[i] == null) {
                cross[i] = element;
                return true;
            }
            else if (cross[i+2] == null) {
                cross[i+2] = element;
                return true;
            }
        return false;
	}

    @Override
    public boolean isEntrance(){
        int n = 0;
        for(int i = 0; i < 4; i++)
            if(cross[i] == notConnected)
                n++;
        return n > 0 && n < 3;
    }
}
