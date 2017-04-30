package graphics;

import application.View;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GEngine extends Drawable {
    String direction;
    public GEngine(int x, int y, String dir) {
        super(x, y);
        direction = dir;
        String path = new File("").getAbsolutePath() + "\\img\\";
        try {
            image = resize(ImageIO.read(new File(path + "engine.png")), View.imgSize, View.imgSize);
        } catch (IOException ex) {}
    }
}
