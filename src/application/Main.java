package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import struct.*;

public class Main {

    public static void main(String[] args) {
    	Command comm = new Command();
    	InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String[] cmd = null;
        try{
        	while(true){
        		String line = br.readLine();
        		if(line == null) break;
        		cmd = line.split(" ");
        		if(cmd[0].equals("input"))comm.input(cmd[1]);
        		if(cmd[0].equals("output"))comm.output(cmd[1]);
        		if(cmd[0].equals("start"))comm.start();
        		if(cmd[0].equals("exit"))break;
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

   
}
