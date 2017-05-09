package graphics;

import application.Game;
import application.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A v�lt� kirajzolhat� megval�s�t�sa
 */
public class GSwitch extends Drawable {
    /**
     * @param x Sz�less�gi koordin�ta
     * @param y Magass�gi koordin�ta
     * @param state A v�lt� �ll�sa
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
    /* (non-Javadoc)
     * @see graphics.Drawable#draw(java.awt.Graphics)
     * Jelzi, hogy ez egy v�lt� egy S bet�vel
     */
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, View.imgSize/4));
        g.drawString("S", x + View.imgSize/2 - View.imgSize/8, y + View.imgSize/2 + View.imgSize/8);
    }
}
