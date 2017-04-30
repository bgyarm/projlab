package struct;

public class Switch extends RailElement {
	//négy irányú váltó
    private RailElement[] sw;
    private State state;

    //hat lehetséges állapot
    enum State{//állapotok azzal a jelzéssel hogy melyik sínhez vannak csatlakoztatva
        AB(0, 1),
        BC(1, 2),
        CD(2, 3),
        DA(3, 4),
        AC(0, 2),
        BD(1, 3);
        int[] a;
        State(int a, int b){this.a = new int[]{a, b};}
        int[] getState(){return a;}//visszakapjuk a két összekötött sínt.
    }



    public Switch(){
        sw = new RailElement[4];
    	trainElement = null;
        notConnected = new RailElement() {//nem csatlakozott sínt jelzi
            @Override
            public boolean setNext(RailElement next) {
                return false;
            }

            @Override
            public RailElement getNext(RailElement prev) {
                return null;
            }
        };
        state = State.AB;//alap állapot az AB állapot
    }
	public Switch(String state){//meg lehet adni kezdö állapottal is
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
            return setNext(notConnected);//ha nincs csatlakoztatva akkor is jelzö értékkel beállítunk egy objektumot
        if(element != notConnected)
            for(int i = 0; i < 4; i++)
                if(sw[i] == element) return true;//ha már szerepel
        for(int i = 0; i < 4; i++)
            if (sw[i] == null) {//ha be tudjuk írni beírjuk
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
    private boolean valid(){//akor lehetséges ha van legalább két aktív sine
    	int n = 0;
    	for (int i = 0; i < 4; i++){
            if(sw[i] != null || sw[i] != notConnected) n++;//null is lehet ha még nem volt meghívva rá a setNext
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

    public void changeDirection() {//megpróbálja a következö állapotba állítani a váltót
    	//Ha nem lehetséges a váltás, ne próbálkozzon
        if(!valid()) return;
        if (trainElement == null) {
        	switch(state){//megviszgálja az aktuális helyzetet, majd dönt a következö állapotról
        	case AB:
        		state = State.BC;
        		break;
        	case BC:
        		state = State.CD;
        		break;
        	case CD:
        		state = State.DA;
        		break;
        	case DA:
        		state = State.AC;
        		break;
        	case AC:
        		state = State.BD;
        		break;
        	case BD:
        		state = State.AB;
        		break;
        	}
        }
    }

    @Override
    public boolean isEntrance(){//lehet e kiindulópont
        int n = 0;
        for(int i = 0; i < 4; i++)
            if(sw[i] == null || sw[i] == notConnected)//akkor lehet ha van legalább 1 nem csatlakoztatott vége
                n++;
        return n > 0 && n < 3;
    }

    public String  getState(){return state.name();}
}
