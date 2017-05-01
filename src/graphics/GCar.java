package graphics;

import application.View;
import struct.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GCar extends Drawable {
    String direction;
    public GCar(int x, int y, String dir, String color) {
        super(x, y);
        direction = dir;
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
}
