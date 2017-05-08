package application;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.Random;

public class Game extends JLayeredPane implements Runnable{

	private static boolean pause = true;
	private static boolean over = false;
    private String actLevel;
	private View view;
	private Menu menu;
    private final static int fps = 30;
    private Controller controller;
    static String overText = "";
    static int numTrains;
    public static Font font = new Font("Comic Sans MS", Font.BOLD, 40);
	
	public Game(){
		pause = false;
        setBounds(0, 0, Window.windowWidth, Window.windowHeight);
        setBackground(Color.black);
        menu = new Menu(this);
        view = new View(Window.windowWidth-20-Window.borderW, Window.windowHeight-250);
        view.setSize(Window.windowWidth-20-Window.borderW, Window.windowHeight-250);
        actLevel = menu.getAct();
        controller = new Controller(view, actLevel);
        getInfo();
        add(menu, new Integer(0), 0);
        add(view, new Integer(1), 0);
        view.addMouseListener(controller.getListener());
        setVisible(true);
        Thread t = new Thread(this);
        t.start();
	}

    public void getInfo(){
	    view.clear();
	    actLevel = menu.getAct();
        controller.init(actLevel);
    }

    public void start(){
        numTrains = 0;
        controller.newTrain();
    }

    @Override
    public void run(){
        Random r = new Random();
        try{
            while (true) {
                if (!over && menu.getStarted()) {

                    if (r.nextInt(100) < 18/numTrains)
                        controller.newTrain();
                    controller.validate();
                    Thread.sleep(800);
                    controller.addEvent("M");
                }
                else
                    Thread.sleep(500);
            }
        }catch (Exception e) {}
    }

	public static boolean isOver(){return over;}
    public static void setOver(boolean b){over = b;}
	public static void pause(){pause = !pause;}//megállíthatjuk, és elindíthatjuk a játékot
    public static void gameOver(String s){//játék vége
	    over = true;
	    overText = s;
        //Logger.printMethodCall("Game", "gameOver");
    }
}
