package application;

import java.io.File;

public class Game {
	
	private static int level;
	private static boolean pause;
	private static boolean over = false;
	private static String levelPath;
	private static String[] levels;
	
	public Game(){
		level = 0;
		pause = false;
		try {
			levelPath = new File("").getAbsolutePath() + "\\levels\\";//a levels mappából töltjük be a pályákat.
			levels = new File(levelPath).list();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
    //Itt inicialízáljuk a grafikus részeket is majd.
	public static void start(String actLevel){
        Command c = new Command();
        c.input(actLevel);
        c.start();

    }
	
	public static void nextLevel(){
		level++;
	}

	public static boolean isOver(){return over;}
	
	public static void pause(){
		pause = !pause;
	}//megállíthatjuk, és elindíthatjuk a játékot
	
    public static void gameOver(){//játék vége
	    over = true;
        //Logger.printMethodCall("Game", "gameOver");
    }
}
