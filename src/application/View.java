package application;


import graphics.Drawable;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class View extends JPanel {
    public static int imgSize = 80;
    private ArrayList<Drawable> elements = new ArrayList<>();
    private BufferedImage buffer;

    public View(int w, int h){
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        setBounds(10, 50, w, h);
        setOpaque(true);
        setBackground(Color.lightGray);
        setVisible(true);
    }
    public void addElem(Drawable elem){elements.add(elem);}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Drawable d : elements){
            d.draw(buffer.getGraphics());
        }
        g.drawImage(buffer, 0, 0, this);
    }
    public BufferedImage getBuffer(){return buffer;}

    public void clear(){
        Graphics g = buffer.getGraphics();
        g.setColor(getBackground());
        g.fillRect (0, 0, buffer.getWidth(), buffer.getHeight());
        elements.clear();
        invalidate();
    }
}
