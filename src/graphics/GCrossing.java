package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A keresztez�d�s kirajzolhat� megval�s�t�sa
 */
public class GCrossing extends Drawable {
    /**
     * @param x Sz�less�gi koordin�ta
     * @param y Magass�gi koordin�ta
     */
    public GCrossing(int x, int y){
        super(x, y);
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            image = resize(ImageIO.read(new File(path + "cross.png")), View.imgSize, View.imgSize);
        } catch (IOException ex){}
    }
}
