package application;


import graphics.Drawable;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class View extends JPanel {
    public static int imgSize = 80;
    private Drawable[][] rails;
    private Drawable[][] trains;
    private BufferedImage buffer;

    public View(int w, int h){
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        setBounds(10, 50, w, h);

        setOpaque(true);
        setBackground(Color.lightGray);
        setVisible(true);
    }
    public void init(int w, int h){
        rails = new Drawable[h][w];
        trains = new Drawable[h][w];
    }

    public int getW(){return rails[0].length*imgSize;}
    public int getH(){return rails.length*imgSize;}

    public void addRail(Drawable elem, int w, int h){rails[h][w] = elem;}
    public void addTrain(Drawable elem, int w, int h){trains[h][w] = elem;}
    public Drawable getRail(int w, int h){return rails[h][w];}
    public Drawable getTrain(int w, int h){return trains[h][w];}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < rails.length; i++)
            for(int j = 0; j < rails[i].length; j++) {
                if (rails[i][j] != null)
                    rails[i][j].draw(buffer.getGraphics());
                if (trains[i][j] != null)
                    trains[i][j].draw(buffer.getGraphics());
        }
        g.drawImage(buffer, 0, 0, this);
    }
    public BufferedImage getBuffer(){return buffer;}

    public void clear(){
        Graphics g = buffer.getGraphics();
        g.setColor(Color.lightGray);
        g.fillRect (0, 0, this.getWidth(), this.getHeight());
        repaint();
    }

    public void clear(int x, int y){
        Graphics g = buffer.getGraphics();
        g.setColor(getBackground());
        g.fillRect (x, y, View.imgSize, View.imgSize);
    }
}
