package application;


import graphics.Drawable;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * N�zet (megjelen�t�s) megval�s�t�sa
 */
public class View extends JPanel {
    public static int imgSize = 80;
    private Drawable[][] rails;
    private Drawable[][] trains;
    private BufferedImage buffer;

    /**
     * @param w p�lyasz�less�g
     * @param h p�lyamagass�g
     */
    public View(int w, int h){
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        setBounds(10, 50, w, h);

        setOpaque(true);
        setBackground(Color.lightGray);
        setVisible(true);
    }
    /**
     * @param w p�lyasz�less�g
     * @param h p�lyamagass�g
     */
    public void init(int w, int h){
        rails = new Drawable[h][w];
        trains = new Drawable[h][w];
    }
    /**
     * Vonatok kiv�tele a p�ly�r�l
     */
    public void clearTrains(){
        int h = trains.length;
        int w = trains[h].length;
        for(int i = 0; i < h; i++)
            for(int j = 0; j < w; j++)
                trains[i][j] = null;
    }

    /**
     * @return p�lyasz�less�g
     */
    public int getW(){return rails[0].length*imgSize;}
    /**
     * @return p�lyamagass�g
     */
    public int getH(){return rails.length*imgSize;}

    /**
     * Egy s�nelem a n�zethez ad�sa
     * @param elem s�nelem
     * @param w sz�less�gi koordin�ta
     * @param h magass�gi koordin�ta
     */
    public void addRail(Drawable elem, int w, int h){rails[h][w] = elem;}
    /**
     * Egy vonatelem a n�zethez ad�sa
     * @param elem vonatelem
     * @param w sz�less�gi koordin�ta
     * @param h magass�gi koordin�ta
     */
    public void addTrain(Drawable elem, int w, int h){trains[h][w] = elem;}
    public Drawable getRail(int w, int h){return rails[h][w];}
    public Drawable getTrain(int w, int h){return trains[h][w];}

    /**
     * vonat �s s�nelemek kirajzol�sa
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
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

    /**
     * P�lya elt�ntet�se
     */
    public void clear(){
        Graphics g = buffer.getGraphics();
        g.setColor(Color.lightGray);
        g.fillRect (0, 0, this.getWidth(), this.getHeight());
        repaint();
    }
}
