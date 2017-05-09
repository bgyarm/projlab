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
	private static Menu menu;
    private Controller controller;
    static String overText = "";
    static int numTrains;
    static int maxTrains = 2;
    static int ticks = 0;
    public static int points = 0;
    public static Font font = new Font("Comic Sans MS", Font.BOLD, 40);
	
	public Game(){
		pause = false;
        setBounds(0, 0, Window.windowWidth, Window.windowHeight);
        setBackground(Color.black);
        menu = new Menu(this);
        view = new View(Window.windowWidth-20-Window.borderW, Window.windowHeight-180);
        view.setSize(Window.windowWidth-20-Window.borderW, Window.windowHeight-180);
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
        ticks = 0;
        controller.newTrain();
    }

    @Override
    public void run(){
        Random r = new Random();
        try{
            while (true) {
                if (!over && menu.getStarted()) {
                    ticks++;
                    if (r.nextInt(100) < 5)
                        controller.newTrain();
                    controller.validate();
                    Thread.sleep(800);
                    controller.addEvent("M");
                    menu.repaint(0, 0, 100, 40);
                    if(ticks > 30){
                        gameOver("Nyertel!");
                        Thread.sleep(3000);
                        overText = "";
                        menu.act++;
                        menu.update();
                        Thread.sleep(1000);
                        start();
                        over = false;
                    }
                }
                else
                    Thread.sleep(500);
            }
        }catch (Exception e) {e.printStackTrace();}
    }

	public static boolean isOver(){return over;}
    public static void setOver(boolean b){over = b;}
	public static void pause(){pause = !pause;}//megállíthatjuk, és elindíthatjuk a játékot
    public static void gameOver(String s){//játék vége
	    over = true;
	    overText = s;
	    menu.repaint();
        //Logger.printMethodCall("Game", "gameOver");
    }
}
