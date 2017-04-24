package struct;

public class Tunnel extends Rail {
	//bels� p�lya, 3 hossz� + 2 bels� s�n
    private static Rail[] tunnel = new Rail[3];
    private static boolean initialized = false;
    private  static void init(){
        for(int i = 0; i < 3; i++)
            tunnel[i] = new Rail();
        tunnel[0].setNext(tunnel[1]);
        tunnel[1].setNext(tunnel[0]);
        tunnel[1].setNext(tunnel[2]);
        tunnel[2].setNext(tunnel[1]);
        initialized = true;
    }

    public static boolean isInitialized(){return initialized;}

    public static Rail[] getInner(){
        return tunnel;
    }

    //Egy bej�rat fel�p�t�se
    public Tunnel(){
    	//Ha ez az els�, akkor a bels� p�lya is
    	if(!initialized)
    		init();
    	//A bels� s�n fel�p�t�se a szabad v�gen
    	//Maximum 2 alag�tv�g lehet, �gy �sszesen 1 alag�t
    	if(tunnel[0].setNext(this))
    		railA = tunnel[0];
    	else
    		if(tunnel[2].setNext(this))
    			railA = tunnel[2];
    }

    @Override
    public boolean setNext(RailElement next){
        if(next == null || railB == next) return true;
        if(railA != null && railB == null){ //ha csak 2
            railB = next;
            return true;
        }
        return false;
    }

    public boolean build(RailElement entrance){
        if(this.setNext(entrance) && entrance.setNext(this))
            return true;
        else
            entrance.remove(this);
        return false;
    }
    
    public boolean destroy(){

        ElementBase entElement = railB.getTrainElement();
        ElementBase inElement = this.getTrainElement();

        if(entElement == null && inElement == null){
            if(((Rail) railB).remove(this))
            	this.remove(railB);
            return true;
        }
        return false;
    }
}
