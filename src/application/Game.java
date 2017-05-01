package application;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class Game extends JLayeredPane {

	private static boolean pause;
	private static boolean over = false;
    private String actLevel;
	private View view;
	private Menu menu;
    private final static int fps = 30;
    private Controller controller;
    public static Font font = new Font("Comic Sans MS", Font.BOLD, 40);
	
	public Game(){
		pause = false;
        setBounds(0, 0, Window.windowWidth, Window.windowHeight);
        setBackground(Color.black);
        menu = new Menu(this);
        view = new View(Window.windowWidth-20-Window.borderW, Window.windowHeight/2);
        view.setSize(Window.windowWidth-20-Window.borderW, Window.windowHeight/2);
        actLevel = menu.getAct();
        controller = new Controller(view, actLevel);
        getInfo();
        add(menu, new Integer(0), 0);
        add(view, new Integer(1), 0);
        view.addMouseListener(controller.getListener());
        setVisible(true);
	}

    public void getInfo(){
	    view.clear();
	    actLevel = menu.getAct();
        controller.init(actLevel);
    }


    public void startLevel(){
        controller.validate();
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
