/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keti.tas.soft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ChenNan
 */
public class CubeTasServer extends TasAbstractServer{
    private final ServerSocket server;
    private final int port;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isWaitClient = false;
//    private Collection listeners;
    
    public CubeTasServer() throws IOException{
        this.port = PortManagement.getIdelPortNum();
        server = new ServerSocket(port, 200, InetAddress.getLoopbackAddress());
//        server = new ServerSocket(port, 200, InetAddress.getByName("203.254.173.146"));
    }
    
    @Override
    public void start() {
        System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Info: A TAS server for &CUBE is running now!");
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                
                isWaitClient = true;

                while (isWaitClient) {
                    try {
                        Socket client = server.accept();
                        System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Info: A &CUBE has accessed!");
                        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        writer = new PrintWriter(client.getOutputStream(), true);

                        String receiveMsg = reader.readLine();
                        if(receiveMsg.trim().length() > 0&& !"".equals(receiveMsg)){
                            activeReceiveEvent(receiveMsg);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(CubeTasServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
    
    public void controlResponse(String msg){
        if(!server.isClosed()){
            writer.println(msg);
        }
    }
    
    @Override
    public void stop(){
        isWaitClient = false;
        if(!server.isClosed()){
            try {
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(CubeTasServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getServerPort(){
        return port;
    }
}
