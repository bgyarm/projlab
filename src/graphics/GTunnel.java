package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Az alagút kirajzolható megvalósítása
 */
public class GTunnel extends Drawable {
    /**
     * @param x Szélességi koordináta
     * @param y Magassági koordináta
     * @param dir Iránya
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
