package struct;

public class Switch extends RailElement {
	//n�gy ir�ny� v�lt�
    private RailElement[] sw;
    private State state;

    //hat lehets�ges �llapot
    enum State{//�llapotok azzal a jelz�ssel hogy melyik s�nhez vannak csatlakoztatva
        AB(0, 1),
        BC(1, 2),
        CD(2, 3),
        DA(3, 4),
        AC(0, 2),
        BD(1, 3);
        int[] a;
        State(int a, int b){this.a = new int[]{a, b};}
        int[] getState(){return a;}//visszakapjuk a k�t �sszek�t�tt s�nt.
    }



    public Switch(){
        sw = new RailElement[4];
    	trainElement = null;
        notConnected = new RailElement() {//nem csatlakozott s�nt jelzi
            @Override
            public boolean setNext(RailElement next) {
                return false;
            }

            @Override
            public RailElement getNext(RailElement prev) {
                return null;
            }
        };
        state = State.AB;//alap �llapot az AB �llapot
    }
	public Switch(String state){//meg lehet adni kezd� �llapottal is
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
            return setNext(notConnected);//ha nincs csatlakoztatva akkor is jelz� �rt�kkel be�ll�tunk egy objektumot
        if(element != notConnected)
            for(int i = 0; i < 4; i++)
                if(sw[i] == element) return true;//ha m�r szerepel
        for(int i = 0; i < 4; i++)
            if (sw[i] == null) {//ha be tudjuk �rni be�rjuk
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
    private boolean valid(){//akor lehets�ges ha van legal�bb k�t akt�v sine
    	int n = 0;
    	for (int i = 0; i < 4; i++){
            if(sw[i] != null || sw[i] != notConnected) n++;//null is lehet ha m�g nem volt megh�vva r� a setNext
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

    public void changeDirection() {//megpr�b�lja a k�vetkez� �llapotba �ll�tani a v�lt�t
    	//Ha nem lehets�ges a v�lt�s, ne pr�b�lkozzon
        if(!valid()) return;
        if (trainElement == null) {
        	switch(state){//megviszg�lja az aktu�lis helyzetet, majd d�nt a k�vetkez� �llapotr�l
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
    public boolean isEntrance(){//lehet e kiindul�pont
        int n = 0;
        for(int i = 0; i < 4; i++)
            if(sw[i] == null || sw[i] == notConnected)//akkor lehet ha van legal�bb 1 nem csatlakoztatott v�ge
                n++;
        return n > 0 && n < 3;
    }

    public String  getState(){return state.name();}
}
