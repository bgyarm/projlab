package application;

import graphics.*;
import struct.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.util.ArrayList;

public class Controller {
    View view = null;
    private RailElement[][] railMap = null;
    Command comm = null;
    ArrayList<String> events = new ArrayList<>();
    MouseListener listener;
    ArrayList<Dimension> modified = new ArrayList<>();

    public Controller(View v, String actLevel) {
        comm = new Command();
        view = v;
        init(actLevel);

        listener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(x > 0 && x < view.getW() && y > 0 && y < view.getH()) {//view pozíciója a Windowon
                    String comm = elementAt(x/View.imgSize, y/View.imgSize);
                    if(comm != null) {
                        events.add(comm);
                        validate();
                    }
                } else {
                    //newTrain();
                    //events.add("M");
                    //validate();
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
        };
    }

    void init(String actLevel){
        if(railMap != null)
            for(int i = 0; i < railMap.length; i++)
                for(int j = 0; j < railMap[i].length; j++)
                    if(railMap[i][j] != null && railMap[i][j].getClass().getSimpleName().equals("Tunnel"))
                        ((Tunnel)railMap[i][j]).destroy();
        comm.input(actLevel.substring(0, actLevel.lastIndexOf('.')));
        comm.init();
        int size = view.getSize().width / comm.getDim().width;
        if(size > view.getSize().height / comm.getDim().height)
            size = view.getSize().height / comm.getDim().height;
        View.imgSize = size;
        view.init(comm.getDim().width, comm.getDim().height);
        railMap = comm.getMap();
        for(int i = 0; i < railMap.length; i++)
            for(int j = 0; j < railMap[i].length; j++){
                getGraphics(railMap[i][j], i, j);
            }
        view.clear();
    }

    void getGraphics(RailElement elem, int x, int y){
        Drawable d = null;
        if(elem != null) {
            if (elem.getClass().getSimpleName().equals("Rail")) {
                RailElement[] neighs = comm.getNeighbours(x, y);
                String direction = "";
                if (neighs[0] != null)
                    direction += "A";
                if (neighs[1] != null)
                    direction += "B";
                if (neighs[2] != null)
                    direction += "C";
                if (neighs[3] != null)
                    direction += "D";
                d = new GRail(y * View.imgSize, x * View.imgSize, direction, elem.getStation());
            }
            else if (elem.getClass().getSimpleName().equals("Switch")) {
                d = new GSwitch(y * View.imgSize, x * View.imgSize, ((Switch) elem).getState());
            }
            else if (elem.getClass().getSimpleName().equals("Crossing")) {
                d = new GCrossing(y * View.imgSize, x * View.imgSize);
            }
            else if (elem.getClass().getSimpleName().equals("Tunnel")) {
                RailElement[] neighs = comm.getNeighbours(x, y);
                String direction = "";
                if (neighs[0] != null) {
                    direction += "A";
                }
                if (neighs[1] != null) {
                    direction += "B";
                }
                if (neighs[2] != null) {
                    direction += "C";
                }
                if (neighs[3] != null) {
                    direction += "D";
                }
                d = new GTunnel(y * View.imgSize, x * View.imgSize, direction);
            }
        }
        view.addRail(d, y, x);
    }

    public void validate(){
        for (String ev : events) {
            comm.runCommand(ev, railMap);
        }
        events.clear();/*
        for( Dimension d : modified)
            getGraphics(railMap[d.width][d.height], d.width, d.height);
        modified.clear();*/
        for(int i = 0; i < railMap.length; i++)
            for(int j = 0; j < railMap[i].length; j++)
                getGraphics(railMap[i][j], i, j);
        for(int i = 0; i < railMap.length; i++)
            for(int j = 0; j < railMap[i].length; j++) {
                if (railMap[i][j] != null && !railMap[i][j].getClass().getSimpleName().equals("Tunnel")) {
                    if (railMap[i][j].getTrainElement() != null) {
                        ElementBase tmp = railMap[i][j].getTrainElement();
                        RailElement[] neighs = comm.getNeighbours(i, j);
                        String direction = "";
                        for(int n = 0; n < 4; n++)
                            if(neighs[n] != null && neighs[n].equals(tmp.getPrevRail()))
                                direction += n;
                        for(int n = 0; n < 4; n++)
                            if(neighs[n] != null && neighs[n].equals(tmp.getActRail().getNext(tmp.getPrevRail())))
                                direction += n;
                        if (tmp.getClass().getSimpleName().equals("Engine")) {
                            boolean isBoom = tmp.getDerailed() || tmp.getCrshed() != null;
                            view.addTrain(new GEngine(j * View.imgSize, i * View.imgSize, direction, isBoom), j, i);
                        } else if (tmp.getClass().getSimpleName().equals("CoalCar")){
                            view.addTrain(new GCar(j * View.imgSize, i * View.imgSize, direction, "coal", false), j, i);
                        }
                        else {
                            String color = ((Carriage)tmp).getColor().name();
                            view.addTrain(new GCar(j * View.imgSize, i * View.imgSize, direction, color, ((Carriage) tmp).hasPassangers()), j, i);
                        }
                    } else
                        view.addTrain(null, j, i);
                }
            }
            view.clear();
    }

    public String elementAt(int x, int y) {
        RailElement elem = railMap[y][x];
        if (elem != null) {
            String type = elem.getClass().getSimpleName();
            if (type.equals("Switch")) {
                modified.add(new Dimension(y, x));
                return String.format("S %d %d", y, x);
            }
            else if (type.equals("Tunnel")) {
                modified.add(new Dimension(y, x));
                if(y > 0)
                    modified.add(new Dimension(y-1, x));
                else if(y < railMap.length - 1)
                    modified.add(new Dimension(y+1, x));
                if(x > 0)
                    modified.add(new Dimension(y, x-1));
                if(x < railMap[y].length - 1)
                    modified.add(new Dimension(y, x+1));
                return String.format("T %d %d D", y, x);
            }
        }
        else {
            modified.add(new Dimension(y, x));
            if(y > 0)
                modified.add(new Dimension(y-1, x));
            else if(y < railMap.length - 1)
                modified.add(new Dimension(y+1, x));
            if(x > 0)
                modified.add(new Dimension(y, x-1));
            if(x < railMap[y].length - 1)
                modified.add(new Dimension(y, x+1));
            return String.format("T %d %d C", y, x);
        }
        return null;
    }

    public void addEvent(String ev){
        events.add(ev);
    }

    public void newTrain(){
        String s = "";
        do{
            if(comm.getFurthers().size() > 0){
                addEvent(comm.getFurthers().get(0));
                comm.getFurthers().remove(0);
                System.out.println("NewTrain");
            }
        }
        while(comm.getFurthers().size() > 0 && comm.getFurthers().get(0).charAt(0) != 'E');
    }

    public MouseListener getListener(){return listener;}
}
