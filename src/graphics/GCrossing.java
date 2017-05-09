package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A keresztezõdés kirajzolható megvalósítása
 */
public class GCrossing extends Drawable {
    /**
     * @param x Szélességi koordináta
     * @param y Magassági koordináta
     */
    public GCrossing(int x, int y){
        super(x, y);
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            image = resize(ImageIO.read(new File(path + "cross.png")), View.imgSize, View.imgSize);
        } catch (IOException ex){}
    }
}
