package application;

import java.io.*;
import java.rmi.server.ExportException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;


import struct.*;

import javax.xml.bind.Element;

public class Command {
	private File input;
	private File output;
	private RailElement[][] railMap;
	private ElementBase[][] trainMap;
	private ArrayList<ArrayList<ElementBase>> trains = new ArrayList<>();
	private ArrayList<String> result = new ArrayList<>();
	
	public void input(String path){
	    String testdir = new File("").getAbsolutePath() + "\\tests\\";
		File temp = new File(testdir + path + ".txt");
		if(temp.isFile()){
			input = temp;
			System.out.println("Input set: " + input.getAbsolutePath());
		}
		else
			System.out.println("File not found! (" + temp.getAbsolutePath() + ")");
	}
	
	public void output(String path){
        String resultdir = new File("").getAbsolutePath() + "\\results\\";
		File temp = new File(resultdir + path + ".txt");
		if(temp.isFile()){
			System.out.println("File already exists!");
		}
		else{
			output = temp;
			System.out.println("Output created: " + output.getAbsolutePath());
		}
	}
	
	public void start(){
		try{
			FileInputStream fis;
			InputStreamReader isr;
			if(input != null) {//ha van érvényes fájl megadva a programnak, onnan tölti be az utasításokat, egyébként Consolból lehet megadni
				fis = new FileInputStream(input);
				isr = new InputStreamReader(fis);
				System.out.println("Test started from file.");
			} else {
                isr = new InputStreamReader(System.in);
                System.out.println("Test started from console.");
            }
			BufferedReader br = new BufferedReader(isr);
		    ArrayList<String> matrix = new ArrayList<>();
		    ArrayList<String> commands = new ArrayList<>();
		    ArrayList<String> expected = new ArrayList<>();

		    String info = br.readLine();
		    if(info == null)
		        return;
            String[] size = info.split("/")[0].trim().split(" ");
            if(size.length != 2)
                return;
            System.out.format("Size of matrix is: %sx%s\n", size[0], size[1]);
            String line;
		    while((line = br.readLine()) != null && !line.equals("-")){
		    	matrix.add(line.split("/")[0].trim());
		    }
            while((line = br.readLine()) != null && !line.equals(".")){
                commands.add(line.split("/")[0].trim());
            }
            while((line = br.readLine()) != null){
                if(line.split("/")[0].length() > 1)
                    expected.add(line.split("/")[0].trim());
            }

		    railMap(size, matrix);
            runCommands(commands);
		    getRailMap();
		    getTrainMap();
		    writeOut();
		    if(testMatch(Integer.parseInt(size[0]), expected))
		        System.out.println("Match!");
		    else
		        System.out.println("Not match!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void railMap(String [] size, ArrayList<String> mrx){
		try{
			int h = Integer.parseInt(size[0]);
			int w = Integer.parseInt(size[1]);
			if(h <=0 || w <=0 || h != mrx.size())
				throw new Exception("Wrong parameter(s)!");
			
			railMap = new RailElement[h][w];
			for(int i = 0; i < h; i++) {
                String[] row = mrx.get(i).split(";");//felosztjuk a bemenetet sorokra
                for(int j = 0; j < w; j++){
                    RailElement temp = getElement(row[j]); //végigmegyünk egy sor minden elemén és létrehozzuk a megfelel? objektumot
                    if(temp != null) {
                        railMap[i][j] = temp;
                    }
                }
            }

            boolean b = true;
            for(int i = 0; i < h; i++) {
                for(int j = 0; j < w; j++){
                    if(railMap[i][j] != null) {
                        RailElement temp = railMap[i][j];
                        RailElement[] neigh = getNeighbours(i, j);
                        for(int n = 0; n < 4; n++)//végigmegy a szomszédokon, ha valahol érvényes sín van összekapcsolja öket
                            b = temp.setNext(neigh[n]);
                    }
                    if (!b) //ha valamelyik kapcsolás sikertelen volt kivételt dobunk.
                        throw new Exception("Wrong matrix format! Rail cannot connect at (" + i + ", " + j + ").");
                }
            }
		} catch(Exception e){
            System.out.println(e.getMessage());
		}
	}

	private RailElement getElement(String string) {
		if(string.equals("R"))return new Rail();
		else if(string.equals("T"))return new Tunnel();
		else if(string.equals("S"))return new Switch();
		else if(string.equals("C"))return new Crossing();
		else if(string.charAt(0) == 'R') {
			Station s;
			switch(string.charAt(1)){
				case 'R':
					s = new Station(Color.red);
					break;
				case 'G':
					s = new Station(Color.green);
					break;
				case 'B':
					s = new Station(Color.blue);
					break;
				default:
					s = null;
					break;
			}
			if(string.charAt(2) == '1' && s != null)
				s.setPassengers(true);
			if(s != null)
				return new Rail(s);
		}
		return null;
	}

	public void runCommands(ArrayList<String> cmds){
	    try {
            trainMap = new ElementBase[railMap.length][railMap[0].length];

            for (int i = 0; i < cmds.size(); i++) {
                String[] comm = cmds.get(i).split(" ");
                switch (comm[0].charAt(0)) {
                    case 'E'://Engine létrehozása:
                        if (comm.length == 3) {
                            ArrayList<ElementBase> train = new ArrayList<>();
                            Engine e;
                            int h = Integer.parseInt(comm[1]); //
                            int w = Integer.parseInt(comm[2]);
                            if ((h == 0 || w == 0) && railMap[h][w].isEntrance()) {
                                e = new Engine(railMap[h][w]);
                                e.setName(comm[0]);
                                train.add(e);
                                trainMap[h][w] = e;
                                trains.add(train);
                            }
                        }
                        break;
                    case 'C'://Kocsi létrehozása, Carriage vagy CoalCar is lehet
                        ElementBase c = null;
                        ArrayList<ElementBase> train = trains.get(Character.getNumericValue(comm[0].charAt(1)));
                        ElementBase last = train.get(train.size() - 1);
                        if (comm.length == 3) {//3 paraméter esetén Carriage
                            if(trains.size() > Character.getNumericValue(comm[0].charAt(1))) {
                                if (comm[1].equals("R")) //piros
                                    c = new Carriage(last, Color.red, comm[2].equals("1") ? true : false);
                                else if (comm[1].equals("G")) //zöld
                                    c = new Carriage(last, Color.green, comm[2].equals("1") ? true : false);
                                else if (comm[1].equals("B")) //kék
                                    c = new Carriage(last, Color.blue, comm[2].equals("1") ? true : false);
                                else if (comm[1].equals("U")) //'U' karakter esetén bárhol leszállhatnak az utasok a kocsiból. (Szivárvány szín)
                                    c = new Carriage(last, Color.rainbow, comm[2].equals("1") ? true : false);
                                if(((Carriage)c).hasPassangers() && ((Carriage)c).searchToken())
                                        c.giveToken();
                            }
                        }
                        else if (comm.length == 2 && comm[1].equals("C")) { //2 paraméter esetén és ha a színe 'C' (Coal) akkor CoalCar.
                            c = new CoalCar(last);
                        }
                        try{
                            c.setName(comm[0]);
                            train.add(c); //vonat listába felveszük
                        }catch (Exception e){}
                        break;
                    case 'T'://alagút eseméyn esetén
                        if (comm.length == 4) {
                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if(comm[3].equals("C")) {//ha építeni akarunk
                                if (railMap[h][w] == null) {
                                    RailElement[] neighs = getNeighbours(h, w);
                                    for(int n = 0; n < 4; n++) {
                                        if (neighs[n] != null) {//csak akkor építhetünk egy helyre alagútat, ha pontosan egy érvényes sínelem van a szomszédok között
                                            boolean b = true;
                                            for (int j = 0; j < 4; j++)
                                                if (j != n)
                                                    b = b && neighs[j] == null;
                                            if (b) {
                                                railMap[h][w] = new Tunnel();
                                                if(!((Tunnel)railMap[h][w]).build(neighs[n]))
                                                    railMap[h][w] = null;
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if(comm[3].equals("D")) //ha lebontanánk
                                if(railMap[h][w].getClass().getSimpleName().equals("Tunnel")) {
                                    if(((Tunnel) railMap[h][w]).destroy())
                                        railMap[h][w] = null;
                                }
                        }
                        break;
                    case 'S'://váltás esetén
                        if (comm.length == 4) {
                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if(railMap[h][w].getClass().getSimpleName().equals("Switch"))
                                ((Switch)railMap[h][w]).changeDirection(comm[3]);
                        } else if(comm.length == 3){
                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if(railMap[h][w].getClass().getSimpleName().equals("Switch"))
                                ((Switch)railMap[h][w]).changeDirection();
                        }
                        break;
                    case 'M'://Mozgatjuk a vonatokat
                        int repeat = 1;
                        if(comm.length == 2)
                            repeat = Integer.parseInt(comm[1]);
                        for(;repeat > 0; repeat--) //ha van paramétere, akkor beállítjuk az értékét repeat-nek, és ennyiszer hívjuk meg a függvényt
                            if(comm[0].length() > 1){//egy bizonyos vonat mozgatása
                                int num;
                                num = Integer.parseInt(comm[0].substring(1));
                                trains.get(num).get(0).move();
                            } else {//összes vonat mozgatása
                                for(int j = 0; j < trains.size(); j++)
                                    trains.get(j).get(0).move();
                            }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public RailElement[] getNeighbours(int h, int w){
	    int maxh = railMap.length;
	    int maxw = railMap[0].length;
        RailElement[] neighs = new RailElement[4];
	    if(h >= 0 && h < maxh && w >= 0 && w < maxw){
	        if(h > 0)
	            neighs[0] = railMap[h-1][w];
	        if(w > 0)
	            neighs[1] = railMap[h][w-1];
	        if(h < maxh - 1)
                neighs[2] = railMap[h+1][w];
            if(w < maxw - 1)
                neighs[3] = railMap[h][w+1];
        }
        return neighs;
    }

    public String getRailInfo(RailElement re){
        String str = "";
        if(re != null)
            switch (re.getClass().getSimpleName()) {
                case "Rail":
                    str += "R";
                    Station s = re.getStation();
                    if(s != null) {
                        if (s.getColor() == Color.red)
                            str += "R" + (s.hasPassengers() ? "1" : "0");
                        if (s.getColor() == Color.green)
                            str += "G" + (s.hasPassengers() ? "1" : "0");
                        if (s.getColor() == Color.blue)
                            str += "B" + (s.hasPassengers() ? "1" : "0");
                    }
                    break;
                case "Switch":
                    str += "S" + ((Switch)re).getState();
                    break;
                case "Crossing":
                    str += "C";
                    break;
                case "Tunnel":
                    str += "T";
                    break;
                default:
                    break;
            }
        else
            str += "0";
        return String.format("%-4s", str);
    }

	public void getRailMap(){
        String row = "";
        result.add("/RailMap:\n");
        for (int i = 0; i < railMap.length; i++) {
            for (int j = 0; j < railMap[i].length; j++) {
                row += getRailInfo(railMap[i][j]);
                if(j != railMap[i].length-1)
                    row += "| ";
            }
            result.add(row + '\n');
            row = "";
        }
    }

    public void updateTrainMap(){
        for (int i = 0; i < trainMap.length; i++) {
            for (int j = 0; j < trainMap[i].length; j++) {
                if(railMap[i][j] != null)
                    trainMap[i][j] = railMap[i][j].getTrainElement();
                else
                    trainMap[i][j] = null;
            }
        }
    }

    public String getTrainInfo(ElementBase eb){
        String str = "";
        if(eb != null)
            switch (eb.getClass().getSimpleName()) {
                case "Engine":
                    if(eb.getCrshed() != null)
                        str += "!" + eb.getName().substring(1) +eb.getCrshed();
                    else if(eb.getDerailed())
                        str+= "!D" + eb.getName().substring(1);
                    else
                        str += eb.getName();
                    break;
                case "Carriage":
                    Carriage c = (Carriage)eb;
                    str += c.getName();
                    if(c.getColor() == Color.red)
                        str += "R" + (c.hasPassangers() ? "1" : "0");
                    if(c.getColor() == Color.green)
                        str += "G" + (c.hasPassangers() ? "1" : "0");
                    if(c.getColor() == Color.blue)
                        str += "B" + (c.hasPassangers() ? "1" : "0");
                    if(c.getColor() == Color.rainbow)
                        str += "U" + (c.hasPassangers() ? "1" : "0");
                    break;
                case "CoalCar":
                    str += eb.getName();
                    break;
                default:
                    break;
            }
        else
            str += "0";
        return String.format("%-4s", str);
    }

    public void getTrainMap(){
        updateTrainMap();
        String row = "";
        result.add("\n/Trainmap:\n");
        for (int i = 0; i < trainMap.length; i++) {
            for (int j = 0; j < trainMap[i].length; j++) {
                row += getTrainInfo(trainMap[i][j]);
                if(j != trainMap[i].length-1)
                    row += "| ";
            }
            result.add(row + '\n');
            row = "";
        }
        if(Tunnel.isInitialized()) {
            result.add("\n/Tunnel:\n");
            for (int i = 0; i < Tunnel.getInner().length; i++) {
                row += getTrainInfo(Tunnel.getInner()[i].getTrainElement());
                if (i != Tunnel.getInner().length - 1)
                    row += "| ";
                else
                    row += "\n";
            }
            result.add(row + '\n');
        }
    }

    public boolean testMatch(int height, ArrayList<String> expected){
        result.remove(0); result.remove(height);
        int resH = height*2;//Rail + Trainmap, mindegyikhez a felirat + sortörés.
        if(Tunnel.isInitialized()) {
            result.remove(2*height); resH += 1; //+Tunnel
        }
        if(expected.size() != resH || result.size() != resH) return false;
        for(int i = 0; i < resH; i++){
            if(!expected.get(i).equals(result.get(i).trim())){
                System.out.println("Wrong results at line " + (i+1));
                return false;
            }
        }
        return true;
    }

    public void writeOut(){
        try {
            Writer out;
            if (output != null)
                out = new BufferedWriter(new FileWriter(output, true));
            else
                out = new PrintWriter(System.out);
            for (String line : result) {
                out.write(line);
            }
            out.flush();
            if(output != null)
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
