package application;

/**
 * Tesztel�shez kellett
 */
public class Logger {


    /**
     * Ki�rja a konzolra, hogy mely oszt�ly mely met�dusa lett megh�vva
     * @param className oszt�lyn�v
     * @param methodName met�dusn�v
     */
    public static void printMethodCall(String className, String methodName){
        System.out.println(className + " " + methodName + "()");
    }
    /**
     * Ki�rja a konzolra, hogy mely oszt�ly mely met�dusa lett megh�vva, �s az mit csin�lt
     * @param className oszt�lyn�v
     * @param methodName met�dusn�v
     * @param action m�velet
     */
    public static void printAction(String className, String methodName, String action){
        System.out.println(className + " " + methodName + "() " + action);
    }
    /**
     * Ki�rja a konzolra az esetleges hib�kat
     * @param err hiba�zenet
     */
    public static void errorMessage(String err){
        System.out.println(err);
    }
}
