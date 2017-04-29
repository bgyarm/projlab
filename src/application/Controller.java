package application;

import graphics.*;
import struct.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;

public class Controller {
    View view = null;
    private RailElement[][] railMap = null;
    Command comm = null;
    ArrayList<String> events = new ArrayList<>();
    EventListener listener;

    public Controller(View view, String actLevel) {
        comm = new Command();
        comm.input(actLevel.substring(0, actLevel.lastIndexOf('.')));
        comm.init();
        int size = view.getSize().width / comm.getDim().width;
        if(size > view.getSize().height / comm.getDim().height)
            size = view.getSize().height / comm.getDim().height;
        View.imgSize = size;
        this.view = view;

        railMap = comm.getMap();
        init();
        view.repaint();
    }

    void start(){
        listener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(x > 0 && x < view.getWidth() && y > 0 && y < view.getHeight())//view pozíciója a Windowon
                    events.add(elementAt(x, y));
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    void init(){
        for(int i = 0; i < railMap.length; i++)
            for(int j = 0; j < railMap[i].length; j++){
                if(railMap[i][j] != null) {
                    Drawable d = null;
                    if (railMap[i][j].getClass().getSimpleName().equals("Rail")) {
                        RailElement[] neighs = comm.getNeighbours(i, j);
                        String direction = "";
                        if (neighs[0] != null)
                            direction += "A";
                        if (neighs[1] != null)
                            direction += "B";
                        if (neighs[2] != null)
                            direction += "C";
                        if (neighs[3] != null)
                            direction += "D";
                        d = new GRail(j * View.imgSize, i * View.imgSize, direction, railMap[i][j].getStation());
                    }
                    else if (railMap[i][j].getClass().getSimpleName().equals("Switch")) {
                        d = new GSwitch(j * View.imgSize, i * View.imgSize, ((Switch) railMap[i][j]).getState());
                    }
                    else if (railMap[i][j].getClass().getSimpleName().equals("Crossing")) {
                        d = new GCrossing(j * View.imgSize, i * View.imgSize);
                    }
                    else if (railMap[i][j].getClass().getSimpleName().equals("Tunnel")) {
                        RailElement[] neighs = comm.getNeighbours(i, j);
                        String direction = "";
                        if (neighs[0] != null)
                            direction += "A";
                        if (neighs[1] != null)
                            direction += "B";
                        if (neighs[2] != null)
                            direction += "C";
                        if (neighs[3] != null)
                            direction += "D";
                        d = new GTunnel(j * View.imgSize, i * View.imgSize, direction);
                    }
                    view.addElem(d);
                }
            }
        view.repaint();
    }

    public void validate(){
        for (String ev : events) {
            comm.runCommand(ev, railMap);
        }
    }

    public String elementAt(int x, int y) {
        RailElement elem = railMap[(int) y / View.imgSize][(int) x / View.imgSize];
        if (elem != null) {
            String type = elem.getClass().getSimpleName();
            if (type.equals("Switch"))
                return  String.format("S %d %d", y, x);
            else if (type.equals("Tunnel"))
                return String.format("T %d %d D", y, x);
        }
        else
            return String.format("T %d %d C", y, x);
        return null;
    }
}
