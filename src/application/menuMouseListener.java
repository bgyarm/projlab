package application;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class menuMouseListener implements MouseListener {

    Point prev;
    Point next;
    Menu menu;

    public menuMouseListener(Menu m){
        prev = new Point(m.prev.x+10, m.prev.y-20);
        next = new Point(m.next.x+10, m.next.y-20);
        menu = m;
    }

    public void mouseClicked(MouseEvent e) {
        Point mouse = new Point(e.getX(), e.getY());
        if(mouse.distance(prev) < 15) {
            menu.act--;
            menu.update();
        } else if(mouse.distance(next) < 15){
            menu.act++;
            menu.update();
        } else if(mouse.distance(new Point(200, Window.windowHeight-200)) < 50){

        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
