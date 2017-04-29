package application;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Menu extends JPanel {
    Game game;
    Point prev = new Point(30, 30);
    Point next = new Point(Window.windowWidth-Window.borderW-60, 30);

    int act = 0;
    private static String levelPath;
    private static String[] levels;

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

    public void update(){
        act += levels.length;
        act %= levels.length;
        repaint();
        game.getInfo();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(Game.font);
        g.drawString("<", prev.x, prev.y);
        g.drawString(">", next.x, next.y);
        g.drawString(levels[act].substring(0, levels[act].lastIndexOf('.')), 200, 30);
    }

    public String getAct(){return levels[act];}
}
