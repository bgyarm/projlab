package graphics;

import application.View;
import struct.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * Az egyszerû sínelem kirajzolható megvalósítása
 */
public class GRail extends Drawable {
    Station station;
    /**
     * @param x Szélességi koordináta
     * @param y Magassági koordináta
     * @param dir Iránya
     * @param station Rajta lévõ állomás (ha van)
     */
    public GRail(int x, int y, String dir, Station station){
        super(x, y);
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            if(dir.equals("AC") || dir.equals("BD") || dir.length() == 1) {
                image = resize(ImageIO.read(new File(path + "straight.png")), View.imgSize, View.imgSize);
                if(dir.contains("B") || dir.contains("D"))
                    rotate(90);
            } else {
                image = resize(ImageIO.read(new File(path + "turn.png")), View.imgSize, View.imgSize);
                if (dir.equals("AB"))
                    rotate(180);
                else if (dir.equals("BC"))
                    rotate(90);
                else if (dir.equals("AD"))
                    rotate(270);
            }
            if(station != null){
                this.station = station;
                Color c = station.getColor().getColor();
                colorImage(new Color(c.getRed(), c.getGreen(), c.getBlue(), 100));
            }
        } catch (IOException ex){}
    }
    /* (non-Javadoc)
     * @see graphics.Drawable#draw(java.awt.Graphics)
     * Ha van rajta állomás, jelzi, az utasokat is
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if(station != null && station.hasPassengers()) {
            g.setColor(station.getColor().getColor());
            g.setFont(new Font("Comic Sans MS", Font.BOLD, View.imgSize / 4));
            g.drawString("P", x + View.imgSize / 2 - View.imgSize / 8, y + View.imgSize / 2 + View.imgSize / 8);
        }
    }

}
