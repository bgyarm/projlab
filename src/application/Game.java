package application;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.Random;

/**
 * A j�t�kot �sszefog� oszt�ly
 */
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

    /**
     * A j�t�kr�l begy�jti a friss inform�ci�kat
     */
    public void getInfo(){
	    view.clear();
	    actLevel = menu.getAct();
        controller.init(actLevel);
    }

    /**
     * Elind�tja a j�t�kot (start gomb)
     */
    public void start(){
        pause = false;
        numTrains = 0;
        ticks = 0;
        controller.newTrain();
    }

    /**
     * A j�t�k ciklusa, l�pteti a mozdonyokat �s n�ha �j mozdonyt helyez el a p�ly�n. A l�p�ssz�mot figyeli �s egy bizonyos id� ut�n nyer a j�t�kos.
     */
    @Override
    public void run(){
        Random r = new Random();
        try{
            while (true) {
                if (!over && menu.getStarted()) {
                    if (r.nextInt(100) < 5)
                        controller.newTrain();
                    controller.validate();
                    menu.repaint(0, 0, 150, 40);
                    Thread.sleep(800);
                    controller.addEvent("M");
                    ticks++;
                    if(ticks >= 30){
                        gameOver("Nyertel!");
                        pause = true;
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

	/**
	 * @return Igaz, ha v�ge van a j�t�knak, minden m�s esetben hamis
	 */
	public static boolean isOver(){return over;}
    /**
     * Be�ll�tja, hogy v�ge van-e a j�t�knak
     * @param b v�gejelz�s
     */
    public static void setOver(boolean b){over = b;}
    public static boolean isPaused(){return pause;}
	/**
	 * Sz�neteli vagy folytatja a j�t�kot
	 */
	public static void pause(){pause = !pause;}//meg�ll�thatjuk, �s elind�thatjuk a j�t�kot
    /**
     * J�t�k v�ge �s sz�veg ki�r�sa (mi�rt lett v�ge)
     * @param s Sz�veg a j�t�kosnak
     */
    public static void gameOver(String s){//j�t�k v�ge
	    over = true;
	    overText = s;
	    menu.repaint();
        //Logger.printMethodCall("Game", "gameOver");
    }
}
