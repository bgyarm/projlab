package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GCrossing extends Drawable {
    public GCrossing(int x, int y){
        super(x, y);
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            image = resize(ImageIO.read(new File(path + "cross.png")), View.imgSize, View.imgSize);
        } catch (IOException ex){}
    }
}
