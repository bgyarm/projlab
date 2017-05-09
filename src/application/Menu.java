package application;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Men� �s f�ablak
 */
public class Menu extends JPanel {
    Game game;
    Point prev = new Point(30, 30);
    Point next = new Point(Window.windowWidth-Window.borderW-60, 30);
    boolean started = false;

    int act = 0;
    private static String levelPath;
    private static String[] levels;

    /**
     * @param game a j�t�k
     */
    public Menu(Game game){
        this.game = game;
        addMouseListener(new menuMouseListener(this));
        try {
            levelPath = new File("").getAbsolutePath() + "\\levels\\";//a levels mappából töltjük be a pályákat.
            levels = new File(levelPath).list();
        } catch (Exception e){
            e.printStackTrace();
        }
        setOpaque(true);
        setBackground(Color.gray);
        setBounds(10, 10, Window.windowWidth-20-Window.borderW, Window.windowHeight-20-Window.borderH);
        repaint();
    }

    /**
     * Friss�ti az aktu�lis p�ly�t �s a j�t�k inform�ci�it
     */
    public void update(){
        act += levels.length;
        act %= levels.length;
        repaint();
        game.getInfo();
    }

    /**
     * Elind�tja a j�t�kot
     * @param b indul�si bit
     */
    void setStarted(boolean b){started = b; game.start(); game.setOver(false);}
    /**
     * @return igaz, ha el van ind�tva a j�t�k, m�sk�nt hamis
     */
    public boolean getStarted(){return started;}

    /**
     * Kirajzol egy grafikai elemet.
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(Game.font);
        g.drawString(levels[act].substring(0, levels[act].lastIndexOf('.')), 200, 30);
        if(started){
            g.drawString("Back", Window.windowWidth - 240, Window.windowHeight - 80);
            g.drawString(Integer.toString(Game.points), 20, 35);
            if(Game.isOver()) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
                g.drawString(Game.overText, Window.windowWidth/2 - 250, Window.windowHeight - 80);
            }
        }
        else {
            g.drawString("<", prev.x, prev.y);
            g.drawString(">", next.x, next.y);
            g.drawString("Start", 150, Window.windowHeight - 80);
            g.drawString("Exit", Window.windowWidth - 240, Window.windowHeight - 80);
        }
    }

    /**
     * @return A jelenlegi p�lya neve
     */
    public String getAct(){return levels[act];}
}
