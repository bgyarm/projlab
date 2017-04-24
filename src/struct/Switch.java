package struct;

public class Switch extends RailElement {
	//n�gy ir�ny� v�lt�
    private RailElement[] sw;
    private State state;

    //n�gy �llapottal
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

    //Az els� null �rt�k hely�re rakjuk az �j s�nelemet
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
        //Megkapjuk az �llapotb�l, hogy mely s�nek vannak �sszek�tve
        //Majd ha az egyik v�g�t kaptuk el�z�k�nt, akkor a m�sikat adjuk vissza
        //Ha egyiket sem, akkor null �rt�ket
        RailElement tmp = prev == null ? notConnected : prev;
        if(tmp == sw[state.getState()[0]])
            return sw[state.getState()[1]];
        else if (tmp == sw[state.getState()[1]])
            return sw[state.getState()[0]];
        return null;
    }

    //megadja, hogy lehets�ges-e egy�ltal�n a v�lt�s
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
    	//Ha nem lehets�ges a v�lt�s, ne pr�b�lkozzon
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
