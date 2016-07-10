/*
Ï * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keti.tas.soft;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ChenNan
 */
public class PortManagement {
    
    public static int getIdelPortNum(){     
        int port = -1;
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PortManagement.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return port;
    }
}
