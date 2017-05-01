package application;

import java.awt.*;
import java.io.*;
import java.rmi.server.ExportException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


import struct.*;
import struct.Color;

import javax.xml.bind.Element;

public class Command {
	private File input;
	private File output;
	private RailElement[][] railMap;
	private ElementBase[][] trainMap;
	private ArrayList<ArrayList<ElementBase>> trains = new ArrayList<>();
	private ArrayList<String> result = new ArrayList<>();
	private ArrayList<String> furthers;
	private Dimension mapSize;
	private boolean testing = false;
	
	public void input(String path){//bemeneti f�jl be�ll�t�sa
        String testdir;
        if(testing) {
            testdir = new File("").getAbsolutePath() + "\\tests\\";
        } else {
            testdir = new File("").getAbsolutePath() + "\\levels\\";
        }
            File temp = new File(testdir + path + ".txt");
            if (temp.isFile()) {
                input = temp;
                System.out.println("Input set: " + input.getAbsolutePath());
            } else
                System.out.println("File not found! (" + temp.getAbsolutePath() + ")");

	}
	
	public void output(String path){// kimeneti f�jl be�ll�t�sa
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
	
	public void init(){ //bemenetr�l �rkez� utas�t�sok lefuttat�sa
		try{
			FileInputStream fis;
			InputStreamReader isr;
			if(input != null) {//ha van �rv�nyes f�jl megadva a programnak, onnan t�lti be az utas�t�sokat, egy�bk�nt Consolb�l lehet megadni
				fis = new FileInputStream(input);
				isr = new InputStreamReader(fis);
				if(testing)
				    System.out.println("Test started from file.");
			} else {
                isr = new InputStreamReader(System.in);
                if(testing)
                    System.out.println("Test started from console.");
            }
			BufferedReader br = new BufferedReader(isr);
		    ArrayList<String> matrix = new ArrayList<>(); //p�lya le�r�s�t tartalmazza majd
		    ArrayList<String> commands = new ArrayList<>(); //az utas�t�sokat tartalmazza
		    furthers = new ArrayList<>(); //teszt: a v�rt eredm�ny ide fog bet�lteni/j�t�kban: tov�bbi vonatok l�trehoz�s�nak kommandjai
            trains.clear();

		    String info = br.readLine();
		    if(info == null)
		        return;
            String[] size = info.split("/")[0].trim().split(" ");//p�lyam�ret beolvas�sa
            if(size.length != 2)
                return;
            mapSize = new Dimension(Integer.parseInt(size[1]), Integer.parseInt(size[0]));
            System.out.format("Size of matrix is: %sx%s\n", mapSize.height, mapSize.width);
            String line;
		    while((line = br.readLine()) != null && !line.equals("-")){
		    	matrix.add(line.split("/")[0].trim());
		    }
            while((line = br.readLine()) != null && !line.equals(".")){
                commands.add(line.split("/")[0].trim());
            }
            while((line = br.readLine()) != null){
                if(line.split("/")[0].length() > 1)
                    furthers.add(line.split("/")[0].trim());
            }

            railMap(matrix);
            trainMap = new ElementBase[railMap.length][railMap[0].length];
            for (int i = 0; i < commands.size(); i++)
                runCommand(commands.get(i), railMap);

            /*
            Random r = new Random();
            double percent = 0.2;
            for (int i = 0; i < commands.size(); i++) {
                r.nextInt(100);//esem�nyek bek�vetkez�s�nek randomiz�l�sa.
                if(i < 100*percent)
                    runCommand(commands.get(i));
                else
                    i--;
            }*/
            getRailMap();
            getTrainMap();
            if(testing) {
                writeOut(result);
                if (testMatch(mapSize.height, furthers))
                    System.out.println("Match!");
                else
                    System.out.println("Not match!");
            }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void railMap(ArrayList<String> mrx){//a s�np�lya fel�p�t�se
		try{
			int h = mapSize.height;
			int w = mapSize.width;
			if(h <=0 || w <=0 || h != mrx.size())
				throw new Exception("Wrong parameter(s)!");
			
			railMap = new RailElement[h][w];
			for(int i = 0; i < h; i++) {
                String[] row = mrx.get(i).split(";");//felosztjuk a bemenetet sorokra
                for(int j = 0; j < w; j++){
                    RailElement temp = getElement(row[j]); //v�gigmegy�nk egy sor minden elem�n �s l�trehozzuk a megfelel? objektumot
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
                        for(int n = 0; n < 4; n++)//v�gigmegy a szomsz�dokon, ha valahol �rv�nyes s�n van �sszekapcsolja �ket
                            b = temp.setNext(neigh[n]);
                    }
                    if (!b) //ha valamelyik kapcsol�s sikertelen volt kiv�telt dobunk.
                        throw new Exception("Wrong matrix format! Rail cannot connect at (" + i + ", " + j + ").");
                }
            }
		} catch(Exception e){
            System.out.println(e.getMessage());
		}
	}

	private RailElement getElement(String string) { //megfelel� elem l�trehoz�as
		if(string.equals("R"))return new Rail();
		else if(string.equals("T"))return new Tunnel();
		else if(string.equals("S"))return new Switch();
		else if(string.equals("C"))return new Crossing();
		else if(string.charAt(0) == 'R') { //S�n �s �llom�s.
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

	public void runCommand(String cmd, RailElement[][] map){//esem�nyek els�t�se
	    try {
                String[] comm = cmd.split(" ");
                switch (comm[0].charAt(0)) {
                    case 'E'://Engine l�trehoz�sa:
                        if (comm.length == 3) {
                            ArrayList<ElementBase> train = new ArrayList<>();
                            Engine e;
                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if ((h == 0 || w == 0) && map[h][w].isEntrance()) {
                                e = new Engine(map[h][w]);
                                e.setName(comm[0]);
                                train.add(e);
                                //trainMap[h][w] = e;
                                trains.add(train);//vonat list�ba felv�tel
                            }
                        }
                        break;
                    case 'C'://Kocsi l�trehoz�sa, Carriage vagy CoalCar is lehet
                        ElementBase c = null;
                        ArrayList<ElementBase> train = trains.get(Character.getNumericValue(comm[0].charAt(1)));
                        ElementBase last = train.get(train.size() - 1);
                        if (comm.length == 3) {//3 param�ter eset�n Carriage
                            if(trains.size() > Character.getNumericValue(comm[0].charAt(1))) {
                                if (comm[1].equals("R")) //piros
                                    c = new Carriage(last, Color.red, comm[2].equals("1") ? true : false); //el�tte l�v� kocsi, sz�n �s utasok be�ll�t�sa
                                else if (comm[1].equals("G")) //z�ld
                                    c = new Carriage(last, Color.green, comm[2].equals("1") ? true : false);
                                else if (comm[1].equals("B")) //k�k
                                    c = new Carriage(last, Color.blue, comm[2].equals("1") ? true : false);
                                else if (comm[1].equals("U")) //'U' karakter eset�n b�rhol lesz�llhatnak az utasok a kocsib�l. (Sziv�rv�ny sz�n)
                                    c = new Carriage(last, Color.rainbow, comm[2].equals("1") ? true : false);
                                if(((Carriage)c).hasPassangers() && ((Carriage)c).searchToken())//ha vannak utasok, �s � az els� megkapja a tokent
                                        c.giveToken();
                            }
                        }
                        else if (comm.length == 2 && comm[1].equals("C")) { //2 param�ter eset�n �s ha a sz�ne 'C' (Coal) akkor CoalCar.
                            c = new CoalCar(last);
                        }
                        try{
                            c.setName(comm[0]);
                            train.add(c); //vonat list�ba felvesz�k
                        }catch (Exception e){}
                        break;
                    case 'T'://alag�t esem�yn eset�n
                        if (comm.length == 4) {//4 param�teres utas�t�s
                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if(comm[3].equals("C")) {//ha �p�teni akarunk
                                if (map[h][w] == null) {
                                    RailElement[] neighs = getNeighbours(h, w); //megkeress�k a szomsz�dban l�v� elemeket
                                    for(int n = 0; n < 4; n++) {
                                        if (neighs[n] != null) {//csak akkor �p�thet�nk egy helyre alag�tat, ha pontosan egy �rv�nyes s�nelem van a szomsz�dok k�z�tt
                                            boolean b = true;
                                            for (int j = 0; j < 4; j++)
                                                if (j != n)
                                                    b = b && neighs[j] == null;//pontosan 1 �rv�nyes elem lehet a k�rny�ken amihez hozz�k�thetj�k.
                                            if (b) {
                                                map[h][w] = new Tunnel();//l�trehozzuk �s megpr�b�ljuk elhelyezni
                                                if(!((Tunnel)map[h][w]).build(neighs[n]))// j� helyen fel�p�tj�k. Azt hogy h�ny alag�t van azt a Tunnel.build f�ggv�ny k�zben ellen�rizz�k
                                                    map[h][w] = null; // ha nem lehet �p�teni, megsz�ntetj�k.
                                                break;
                                            } else
                                                break;
                                        }
                                    }
                                }
                            } else if(comm[3].equals("D")) //ha lebontan�nk
                                if(map[h][w].getClass().getSimpleName().equals("Tunnel")) {
                                    if(((Tunnel) map[h][w]).destroy())//ha le lehet bontani, nem �ll rajta a vonat
                                        map[h][w] = null;// megsz�ntetj�k a p�ly�r�l is.
                                }
                        }
                        break;
                    case 'S'://v�lt�s eset�n
                        if (comm.length == 4) {//Ha param�ter�l kapja az �llapotot

                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if(map[h][w].getClass().getSimpleName().equals("Switch"))
                                ((Switch)map[h][w]).changeDirection(comm[3]);//Ha tud az adott �llapotba v�lt
                        } else if(comm.length == 3){ //ha nincs �lapot param�ter
                            int h = Integer.parseInt(comm[1]);
                            int w = Integer.parseInt(comm[2]);
                            if(map[h][w].getClass().getSimpleName().equals("Switch"))
                                ((Switch)map[h][w]).changeDirection();
                        }
                        break;
                    case 'M'://Mozgatjuk a vonatokat
                        int repeat = 1;
                        if(comm.length == 2)
                            repeat = Integer.parseInt(comm[1]);
                        for(;repeat > 0; repeat--) //ha van param�tere, akkor be�ll�tjuk az �rt�k�t repeat-nek, �s ennyiszer h�vjuk meg a f�ggv�nyt
                            if(comm[0].length() > 1){//egy bizonyos vonat mozgat�sa
                                int num;
                                num = Integer.parseInt(comm[0].substring(1));
                                trains.get(num).get(0).move();
                            } else {//�sszes vonat mozgat�sa
                                for(int j = 0; j < trains.size(); j++)
                                    trains.get(j).get(0).move();
                            }
                        break;
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public RailElement[] getNeighbours(int h, int w){//visszaadja egy adott s�n k�r�li elemeket
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

    public String getRailInfo(RailElement re){//Lek�rdezz�k egy s�nelem tulajdons�gait. Form�zottan visszaadjuk az inform�ci�t
        String str = "";
        if(re != null)
            switch (re.getClass().getSimpleName()) {
                case "Rail":
                    str += "R";
                    Station s = re.getStation();//s�n mellett lehet �llom�s is
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

	public void getRailMap(){//a p�ly�t form�zottan megjelen�ti
        String row = "";
        result.add("/RailMap:\n");
        for (int i = 0; i < railMap.length; i++) {
            for (int j = 0; j < railMap[i].length; j++) {
                row += getRailInfo(railMap[i][j]);//mindegyik elemr�l lek�rj�k az inform�ci�t
                if(j != railMap[i].length-1)
                    row += "| ";
            }
            result.add(row + '\n');//form�zottan hozz��rjuk az eredm�nyekhez
            row = "";
        }
    }

    public void updateTrainMap(){//friss�ti a trainmap �ltal t�rolt adatokat
        for (int i = 0; i < trainMap.length; i++) {
            for (int j = 0; j < trainMap[i].length; j++) {
                if(railMap[i][j] != null)
                    trainMap[i][j] = railMap[i][j].getTrainElement();
                else
                    trainMap[i][j] = null;
            }
        }
    }

    public String getTrainInfo(ElementBase eb){//hasonl� mint a getRailInfo csak vonatelemmel
        String str = "";
        if(eb != null)
            switch (eb.getClass().getSimpleName()) {
                case "Engine":
                    if(eb.getCrshed() != null)//m�st �runk ha �tk�z�tt
                        str += "!" + eb.getName().substring(1) +eb.getCrshed();
                    else if(eb.getDerailed())//m�st ha kisiklott
                        str+= "!D" + eb.getName().substring(1);
                    else
                        str += eb.getName();//�s megint m�st ha nem volt semmi gond
                    break;
                case "Carriage"://sz�n �s �llapot lek�r�se
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
                    str += eb.getName();//csak a nev�t t�roljuk el
                    break;
                default:
                    break;
            }
        else
            str += "0";//ha nincs az adott pontban vonat r�sz
        return String.format("%-4s", str);
    }

    public void getTrainMap(){//hasonl� mint a getRailMap
        updateTrainMap();//el�sz�r friss�tj�k a t�rk�pet
        String row = "";
        result.add("\n/Trainmap:\n");
        for (int i = 0; i < trainMap.length; i++) {
            for (int j = 0; j < trainMap[i].length; j++) {
                row += getTrainInfo(trainMap[i][j]);//lek�rj�k az inf�t
                if(j != trainMap[i].length-1)
                    row += "| ";
            }
            result.add(row + '\n');//hozz�adjuk a resulthoz
            row = "";
        }
        if(Tunnel.isInitialized()) {//ha l�trehoztunk alagutat, azt is megjelen�tj�k
            result.add("\n/Tunnel:\n");
            for (int i = 0; i < Tunnel.getInner().length; i++) {
                row += getTrainInfo(Tunnel.getInner()[i].getTrainElement());//lek�rj�k az inf�t
                if (i != Tunnel.getInner().length - 1)
                    row += "| ";
                else
                    row += "\n";
            }
            result.add(row + '\n');
        }
    }

    public boolean testMatch(int height, ArrayList<String> expected){//�sszehasonl�tja sorr�l sorra az eredm�nyeket az elv�rttal.
        result.remove(0); result.remove(height);
        int resH = height*2;//Rail + Trainmap, mindegyikhez a felirat + sort�r�s.
        if(Tunnel.isInitialized()) {
            result.remove(2*height); resH += 1; //+Tunnel
        }
        if(expected.size() != resH || result.size() != resH) return false;//ha nem annyi sora van mint amit v�rtunk visszat�r�nk
        for(int i = 0; i < resH; i++){
            if(!expected.get(i).equals(result.get(i).trim())){//am�g j�k az eredm�nyek v�rakozik
                System.out.println("Wrong results at line " + (i+1));//ha valami hib�s, ki�rja a sort, �s visszat�r hamissal
                return false;
            }
        }
        return true;//egy�bk�nt sikeres
    }

    public void writeOut(ArrayList<String> res){//ki�rjuk a param�terben �tadott lista tartalm�t.
        try {
            Writer out;
            if (output != null)
                out = new BufferedWriter(new FileWriter(output, true));
            else
                out = new PrintWriter(System.out);
            for (String line : res) {
                out.write(line);
            }
            out.flush();
            if(output != null)
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dimension getDim(){return mapSize;}
    public RailElement[][] getMap(){return railMap;}
    public ArrayList<String> getFurthers(){return furthers;}
}
