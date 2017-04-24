package application;

public class Logger {


    public static void printMethodCall(String className, String methodName){
        System.out.println(className + " " + methodName + "()");
    }
    public static void printAction(String className, String methodName, String action){
        System.out.println(className + " " + methodName + "() " + action);
    }
    public static void errorMessage(String err){
        System.out.println(err);
    }
}
