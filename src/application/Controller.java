package application;

import graphics.*;
import struct.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Az események irányításáért felelös osztály
 */
public class Controller {
    View view = null;
    private RailElement[][] railMap = null;
    Command comm = null;
    ArrayList<String> events = new ArrayList<>();
    MouseListener listener;

    /**
     * @param v nézet
     * @param actLevel jelenlegi pálya
     */
    public Controller(View v, String actLevel) {
        comm = new Command();
        view = v;
        init(actLevel);

        listener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(x > 0 && x < view.getW() && y > 0 && y < view.getH()) {
                    String comm = elementAt(x/View.imgSize, y/View.imgSize);
                    if(comm != null) {
                        events.add(comm);
                        validate();
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
        };
    }

    /**
     * Betölti a pályát
     * @param actLevel jelenlegi pálya
     */
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

    /**
     * A nézethez adja a grafikai elemet
     * @param elem sínelem
     * @param x szélességi koordináta
     * @param y magassági koordináta
     */
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

    /**
     * Validálja a nézetet
     */
    public void validate(){
        for (Iterator<String> iterator = events.iterator(); iterator.hasNext(); ) {
            comm.runCommand(iterator.next(), railMap);
            iterator.remove();
        }
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
                            view.addTrain(new GEngine(j * View.imgSize, i * View.imgSize, direction, isBoom, tmp.getPrevRail()==null), j, i);
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

    /**
     * @param x szélességi koordináta
     * @param y magassági koordináta
     * @return A koordinátán lévö elem, ha van ott valami, egyébként null
     */
    public String elementAt(int x, int y) {
        RailElement elem = railMap[y][x];
        if (elem != null) {
            String type = elem.getClass().getSimpleName();
            if (type.equals("Switch")) {
                return String.format("S %d %d", y, x);
            }
            else if (type.equals("Tunnel")) {
                return String.format("T %d %d D", y, x);
            }
        }
        else {
            return String.format("T %d %d C", y, x);
        }
        return null;
    }

    /**
     * Esemény hozzáadása
     * @param ev esemény
     */
    public void addEvent(String ev){
        events.add(ev);
    }

    /**
     * új vonat elhelyezése a pályán
     */
    public void newTrain(){
        if(Game.numTrains < Game.maxTrains) {
            int tries = 0;
            String s = "";
            Random r = new Random();
            int entNum = comm.getEntraces().size();
            if (entNum > 0) {
                Point ent;
                do {
                    ent = comm.getEntraces().get(r.nextInt(entNum));
                    tries++;
                    if(tries > 3) break;
                } while ((railMap[ent.x][ent.y].getTrainElement() != null || railMap[ent.x][ent.y].getNext(null) == null) && !Game.isOver());
                if(tries <= 3) {
                    s = String.format("E%d %d %d", Game.numTrains, ent.x, ent.y);
                    addEvent(s);
                    for (int i = 0; i < r.nextInt(3) + 1; i++) {
                        int c = r.nextInt(5);
                        String color = "";
                        if (c == 0)
                            color = "C";
                        else if (c == 1)
                            color = "R";
                        else if (c == 2)
                            color = "G";
                        else if (c == 3)
                            color = "B";
                        else if (c == 4)
                            color = "U";
                        s = String.format("C%d %s", Game.numTrains, color);
                        if (c != 0)
                            s += " " + r.nextInt(2);
                        addEvent(s);
                    }
                    System.out.println("NewTrain");
                    Game.numTrains++;
                    validate();
                }
            }
        }
    }

    /**
     * @return Az egér eseménykezelöje
     */
    public MouseListener getListener(){return listener;}
}
