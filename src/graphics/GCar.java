package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A szenes/utasokocsik kirajzolható objektuma
 */
public class GCar extends Drawable {
    String direction;
    boolean passengers;
    /**
     * @param x Szélességi koordináta
     * @param y Magassági koordináta
     * @param dir A kocsi iránya
     * @param color A kocsi színe
     * @param passengers Vannak-e utasok
     */
    public GCar(int x, int y, String dir, String color, boolean passengers) {
        super(x, y);
        direction = dir;
        this.passengers = passengers;
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            image = resize(ImageIO.read(new File(path + color + ".png")), View.imgSize, View.imgSize);
            if(dir.equals("23") || dir.equals("32") || dir.equals("01") || dir.equals("10")) {
                rotate(45);
            } else if(dir.equals("13") || dir.equals("31") || dir.equals("3") || dir.equals("1")) {
                rotate(90);
            }else if(dir.equals("12") || dir.equals("21") || dir.equals("03") || dir.equals("30") ) {
                rotate(135);
            }
        } catch (IOException ex) {}
    }

    /**
     * Rajzol
     * Ha vannak utasok, azt is jelzi
     * @see graphics.Drawable#draw(java.awt.Graphics)
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if(passengers) {
            g.setColor(java.awt.Color.WHITE);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, View.imgSize / 4));
            g.drawString("P", x + View.imgSize / 2 - View.imgSize / 8, y + View.imgSize / 2 + View.imgSize / 8);
        }
    }
}
