package application;

import javax.swing.*;
import java.awt.*;

/**
 * Az ablak megval�s�t�sa
 */
public class Window extends JFrame{
    static int windowWidth = 800;
    static int windowHeight = 600;
    static int borderW = 0;
    static int borderH = 0;
    Game game;
    View view;

    /**
     * @param w sz�less�g
     * @param h magass�g
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
     * J�t�k l�trehoz�sa, ablak inicializ�l�sa
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
