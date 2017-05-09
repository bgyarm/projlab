package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A váltó kirajzolható megvalósítása
 */
public class GSwitch extends Drawable {
    /**
     * @param x Szélességi koordináta
     * @param y Magassági koordináta
     * @param state A váltó állása
     */
    public GSwitch(int x, int y, String state){
        super(x, y);
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            if (state.equals("AC") || state.equals("BD")) {
                image = resize(ImageIO.read(new File(path + "switchStraight.png")), View.imgSize, View.imgSize);
                if(state.equals("BD"))
                    rotate(90);
            }
            else {
                image = resize(ImageIO.read(new File(path + "switch.png")), View.imgSize, View.imgSize);
                if(state.equals("AB"))
                    rotate(180);
                else if(state.equals("BC"))
                    rotate(90);
                else if(state.equals("DA"))
                    rotate(270);
            }
        } catch (IOException ex){}
    }
    /**
     * Jelzi, hogy ez egy váltó egy S betûvel
     * @see graphics.Drawable#draw(java.awt.Graphics)
     */
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, View.imgSize/4));
        g.drawString("S", x + View.imgSize/2 - View.imgSize/8, y + View.imgSize/2 + View.imgSize/8);
    }
}
