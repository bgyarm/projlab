package graphics;

import application.View;
import struct.Station;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Az alag�t kirajzolhat� megval�s�t�sa
 */
public class GTunnel extends Drawable {
    /**
     * @param x Sz�less�gi koordin�ta
     * @param y Magass�gi koordin�ta
     * @param dir Ir�nya
     */
    public GTunnel(int x, int y, String dir){
        super(x, y);
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            image = resize(ImageIO.read(new File(path + "tunnel.png")), View.imgSize, View.imgSize);
            if(dir.equals("A"))
                rotate(180);
            else if(dir.equals("B"))
                rotate(90);
            else if(dir.equals("D"))
                rotate(270);
        } catch (IOException ex){}
    }
}
