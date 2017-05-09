package struct;

/**
 * Keresztezõdés megvalósítása
 */
public class Crossing extends RailElement {
	
    private RailElement[] cross;
    
    public Crossing(){
    	cross = new RailElement[4];
    	trainElement = null;
    }

    
    //A szemköztit adja vissza. Ha egyikkel sem illeszkedik, vagy notConnected elem van szemközt, akkor null-t
	/* (non-Javadoc)
	 * @see struct.RailElement#getNext(struct.RailElement)
	 */
	@Override
	public RailElement getNext(RailElement prev) {
        for(int i = 0; i < 4; i++)
            if(prev == cross[i])
                if(cross[i + (i%2 == 0 ? 1 : -1)] != notConnected) // ha a következö nem "null", vagyis van csatlakoztatott elem.
                    return cross[i + (i%2 == 0 ? 1 : -1)];//a szemköztit adja vissza, 0->1 ,1->0, 2->3, 3->2.
		return null;
	}

	//Beállítja a kapott sínt szomszédosnak
    /* (non-Javadoc)
     * @see struct.RailElement#setNext(struct.RailElement)
     */
    @Override
	public boolean setNext(RailElement element) {
		if(element == null)
            return setNext(notConnected);//ha nullt csatlakoztatnánk akkor is jelzö értékkel lefoglaljuk az egyik véget.
		for(int i = 0; i < 4; i++)
            if(cross[i] == element) return true; // ha már szerepel az elemek között

		for (int i = 0; i < 2; i++)//beállítjuk a következö síneket.
            if (cross[i] == null) {//beállítjuk elöször a felsö elemet, következönek másik ágon az egyik oldalsót, majd a lentit
                cross[i] = element;//felsö és a lenti beállítása
                return true;
            }
            else if (cross[i+2] == null) {//ha már a fenti be van állítva, akkor jön az egyik oldalsó, majd ha már a lenti is akkor a másikat is itt állítjuk be
                cross[i+2] = element;//két oldalsó beállítása
                return true;
            }
        return false;//ha nem lehet
	}

    /* (non-Javadoc)
     * @see struct.RailElement#isEntrance()
     */
    @Override
    public boolean isEntrance(){//ha lehet kezdöpont
        int n = 0;
        for(int i = 0; i < 4; i++)
            if(cross[i] == notConnected)//akkor lehet ha van legalább 1 nem cstlakoztatott vége
                n++;
        return n > 0 && n < 3;
    }
}
