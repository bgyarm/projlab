package application;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Menü és fõablak
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
     * @param game a játék
     */
    public Menu(Game game){
        this.game = game;
        addMouseListener(new menuMouseListener(this));
        try {
            levelPath = new File("").getAbsolutePath() + "\\levels\\";//a levels mappÃ¡bÃ³l tÃ¶ltjÃ¼k be a pÃ¡lyÃ¡kat.
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
     * Frissíti az aktuális pályát és a játék információit
     */
    public void update(){
        act += levels.length;
        act %= levels.length;
        repaint();
        game.getInfo();
    }

    /**
     * Elindítja a játékot
     * @param b indulási bit
     */
    void setStarted(boolean b){started = b; game.start(); game.setOver(false);}
    /**
     * @return igaz, ha el van indítva a játék, másként hamis
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
     * @return A jelenlegi pálya neve
     */
    public String getAct(){return levels[act];}
}
