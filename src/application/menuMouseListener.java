package application;

import javafx.application.Application;

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
        if(!menu.getStarted()) {
            if (mouse.distance(prev) < 15) {
                menu.act--;
                menu.update();
            } else if (mouse.distance(next) < 15) {
                menu.act++;
                menu.update();
            } else if (mouse.distance(new Point(160, Window.windowHeight - 80)) < 50) {
                menu.setStarted(true);
                menu.repaint();
            } else if (mouse.distance(new Point(Window.windowWidth - 220, Window.windowHeight - 80)) < 50) {
                System.exit(0);
            }
        } else {
            if (mouse.distance(new Point(Window.windowWidth - 220, Window.windowHeight - 80)) < 50) {
                menu.setStarted(false);
                menu.update();
                menu.repaint();
            }
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
