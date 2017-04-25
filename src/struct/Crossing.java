package struct;

public class Crossing extends RailElement {
	
    private RailElement[] cross;
    
    public Crossing(){
    	cross = new RailElement[4];
    	trainElement = null;
    	notConnected = new RailElement() {//egy jelz� elem ami azt mutatja hogy nincs csatlakoztatva a v�g�hez semmi. Ahhoz kell hogy a setNext mindig helyes sorrendben hozza l�tre a kapcsolatokat.
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

    
    //A szemk�ztit adja vissza. Ha egyikkel sem illeszkedik, vagy notConnected elem van szemk�zt, akkor null-t
	@Override
	public RailElement getNext(RailElement prev) {
        for(int i = 0; i < 4; i++)
            if(prev == cross[i])
                if(cross[i + (i%2 == 0 ? 1 : -1)] != notConnected) // ha a k�vetkez� nem "null", vagyis van csatlakoztatott elem.
                    return cross[i + (i%2 == 0 ? 1 : -1)];//a szemk�ztit adja vissza, 0->1 ,1->0, 2->3, 3->2.
		return null;
	}

	//Be�ll�tja a kapott s�nt szomsz�dosnak
    @Override
	public boolean setNext(RailElement element) {
		if(element == null)
            return setNext(notConnected);//ha nullt csatlakoztatn�nk akkor is jelz� �rt�kkel lefoglaljuk az egyik v�get.
		for(int i = 0; i < 4; i++)
            if(cross[i] == element) return true; // ha m�r szerepel az elemek k�z�tt

		for (int i = 0; i < 2; i++)//be�ll�tjuk a k�vetkez� s�neket.
            if (cross[i] == null) {//be�ll�tjuk el�sz�r a fels� elemet, k�vetkez�nek m�sik �gon az egyik oldals�t, majd a lentit
                cross[i] = element;//fels� �s a lenti be�ll�t�sa
                return true;
            }
            else if (cross[i+2] == null) {//ha m�r a fenti be van �ll�tva, akkor j�n az egyik oldals�, majd ha m�r a lenti is akkor a m�sikat is itt �ll�tjuk be
                cross[i+2] = element;//k�t oldals� be�ll�t�sa
                return true;
            }
        return false;//ha nem lehet
	}

    @Override
    public boolean isEntrance(){//ha lehet kezd�pont
        int n = 0;
        for(int i = 0; i < 4; i++)
            if(cross[i] == notConnected)//akkor lehet ha van legal�bb 1 nem cstlakoztatott v�ge
                n++;
        return n > 0 && n < 3;
    }
}
