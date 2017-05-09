package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * A kirajzolhat� objektumok absztrakt oszt�lya
 */
public abstract class Drawable {
    protected BufferedImage image = null;
    int x = 0, y = 0;
    /**
     * @param x sz�less�gi koordin�ta
     * @param y hossz�s�gi koordin�ta
     */
    public Drawable(int x, int y){this.x = x; this.y = y;}
    /**
     * Rajzol
     * @param g grafikai elem
     */
    public void draw(Graphics g){
        g.drawImage(image, x, y, null);
    }

    /**
     * Forgat
     * @param angle forgat�s sz�ge(fokban)
     */
    public void rotate(double angle){
        double rotationRequired = Math.toRadians (angle);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(image, null);
    }

    /**
     * Megsz�nezi a k�pet
     * @param c Sz�n
     */
    public void colorImage(Color c) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(c);
        g.fillRect(0,0,w,h);
        g.dispose();
        image = dyed;
    }

    /**
     * �jram�retez
     * @param img k�p
     * @param newW �j sz�less�g
     * @param newH �j magass�g
     * @return a k�sz k�p
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
