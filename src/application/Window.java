package application;

import javax.swing.*;
import java.awt.*;

/**
 * Az ablak megvalósítása
 */
public class Window extends JFrame{
    static int windowWidth = 800;
    static int windowHeight = 600;
    static int borderW = 0;
    static int borderH = 0;
    Game game;
    View view;

    /**
     * @param w szélesség
     * @param h magasság
     */
    public Window(int w, int h) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        windowWidth = w;
        windowHeight = h;
        setTitle("Sized");
        setResizable(false);
        init();
        pack();
    }

    /**
     * Játék létrehozása, ablak inicializálása
     */
    public void init(){
        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBounds(0, 0, windowWidth, windowHeight);
        setLayout(new BorderLayout());
        setVisible(true);
        borderH = getInsets().top + getInsets().bottom;
        borderW = getInsets().left + getInsets().right;
        game = new Game();
        add(game, BorderLayout.CENTER);
    }
}
