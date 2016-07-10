package com.keti.tas.soft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CubeTasClient {
    
    private final String serverIP = "127.0.0.1";
//	private final String serverIP = "203.254.173.154";
    private final int serverPort = 7579;
    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;

    public String requestToCube(String reqMsg) throws IOException{
        
        client = new Socket(serverIP, serverPort);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new PrintWriter(client.getOutputStream(), true);
        
        writer.println(reqMsg);
        System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Client send {" + reqMsg + "} to &CUBE!");
        String respMsg = reader.readLine();
        System.out.println(KoreaTimeZone.getDisplayTimeNow()+ " => Receive a message {" + respMsg + "} from &CUBE");
        
        if(respMsg == null){ respMsg = ""; }
        
        client.close();
         
        return respMsg;
    }
    
    public void upload(String data) throws IOException{
        client = new Socket(serverIP, serverPort);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new PrintWriter(client.getOutputStream(), true);
        
        writer.println(data);
        System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Client upload {" + data + "} to &CUBE!");
        
        client.close();
    }
}
