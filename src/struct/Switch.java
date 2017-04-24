package struct;

public class Switch extends RailElement {
	//négy irányú váltó
    private RailElement[] sw;
    private State state;

    //négy állapottal
    enum State{
        AB(0, 1),
        BC(1, 2),
        CD(2, 3),
        DA(3, 4),
        AC(0, 2),
        BD(1, 3);
        int[] a;
        State(int a, int b){this.a = new int[]{a, b};}
        int[] getState(){return a;}
    }



    public Switch(){
        sw = new RailElement[4];
    	trainElement = null;
        notConnected = new RailElement() {
            @Override
            public boolean setNext(RailElement next) {
                return false;
            }

            @Override
            public RailElement getNext(RailElement prev) {
                return null;
            }
        };
        state = State.AB;
    }
	public Switch(String state){
        sw = new RailElement[4];
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
		changeDirection(state);
	}

    //Az elsõ null érték helyére rakjuk az új sínelemet
    @Override
    public boolean setNext(RailElement element){
        if(element == null)
            return setNext(notConnected);
        if(element != notConnected)
            for(int i = 0; i < 4; i++)
                if(sw[i] == element) return true;
        for(int i = 0; i < 4; i++)
            if (sw[i] == null) {
                sw[i] = element;
                return true;
            }
        return false;
    }

    @Override
    public RailElement getNext(RailElement prev){
        //Megkapjuk az állapotból, hogy mely sínek vannak összekötve
        //Majd ha az egyik végét kaptuk elõzõként, akkor a másikat adjuk vissza
        //Ha egyiket sem, akkor null értéket
        RailElement tmp = prev == null ? notConnected : prev;
        if(tmp == sw[state.getState()[0]])
            return sw[state.getState()[1]];
        else if (tmp == sw[state.getState()[1]])
            return sw[state.getState()[0]];
        return null;
    }

    //megadja, hogy lehetséges-e egyáltalán a váltás
    private boolean valid(){
    	int n = 0;
    	for (int i = 0; i < 4; i++){
            if(sw[i] != null) n++;
        }
    	return n>2;
    }

    public void changeDirection(String dir){
        if(trainElement == null) {
            state = State.valueOf(dir);
            for(int i = 0; i < 4; i++)
                if(i == state.getState()[0] || i == state.getState()[1])
                    sw[i].setNext(this);
                else
                    sw[i].remove(this);
        }
    }

    public void changeDirection() {
    	//Ha nem lehetséges a váltás, ne próbálkozzon
        if(!valid()) return;
        if (trainElement == null) {
        	switch(state){
        	case AB:
        		state = State.BC;
        		if(sw[2] == null)
        			changeDirection();
        		break;
        	case BC:
        		state = State.CD;
        		if(sw[3] == null)
        			changeDirection();
        		break;
        	case CD:
        		state = State.DA;
        		if(sw[0] == null)
        			changeDirection();
        		break;
        	case DA:
        		state = State.AC;
        		if(sw[2] == null)
        			changeDirection();
        		break;
        	case AC:
        		state = State.BD;
        		if(sw[1] == null || sw[3] == null)
        			changeDirection();
        		break;
        	case BD:
        		state = State.AB;
        		if(sw[0] == null)
        			changeDirection();
        		break;
        	}
        }
    }

    @Override
    public boolean isEntrance(){
        int n = 0;
        for(int i = 0; i < 4; i++)
            if(sw[i] == notConnected)
                n++;
        return n > 0 && n < 3;
    }

    public String  getState(){return state.name();}
}
