package application;

/**
 * Teszteléshez kellett
 */
public class Logger {


    /**
     * Kiírja a konzolra, hogy mely osztály mely metódusa lett meghívva
     * @param className osztálynév
     * @param methodName metódusnév
     */
    public static void printMethodCall(String className, String methodName){
        System.out.println(className + " " + methodName + "()");
    }
    /**
     * Kiírja a konzolra, hogy mely osztály mely metódusa lett meghívva, és az mit csinált
     * @param className osztálynév
     * @param methodName metódusnév
     * @param action mûvelet
     */
    public static void printAction(String className, String methodName, String action){
        System.out.println(className + " " + methodName + "() " + action);
    }
    /**
     * Kiírja a konzolra az esetleges hibákat
     * @param err hibaüzenet
     */
    public static void errorMessage(String err){
        System.out.println(err);
    }
}
