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
            if(dir.equals("23") || dir.equals("10"))
                rotate(45);
            else if(dir.equals("13") || dir.equals("1"))
                rotate(90);
            else if(dir.equals("03") || dir.equals("12"))
                rotate(135);
            else if(dir.equals("02") || dir.equals("0"))
                rotate(180);
            else if(dir.equals("01") || dir.equals("32"))
                rotate(225);
            else if(dir.equals("31") || dir.equals("3"))
                rotate(270);
            else if(dir.equals("21") || dir.equals("30"))
                rotate(315);
        } catch (IOException ex) {}
    }
}
