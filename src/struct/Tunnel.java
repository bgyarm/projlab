package struct;

public class Tunnel extends Rail {
	//belsõ pálya, 3 hosszú + 2 belsõ sín
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

    //Egy bejárat felépítése
    public Tunnel(){
    	//Ha ez az elsõ, akkor a belsõ pálya is
    	if(!initialized)
    		init();
    	//A belsõ sín felépítése a szabad végen
    	//Maximum 2 alagútvég lehet, így összesen 1 alagút
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
