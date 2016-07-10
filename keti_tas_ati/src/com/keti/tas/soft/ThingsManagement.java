/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keti.tas.soft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ChenNan
 */
public class ThingsManagement{
            

    private final AdaptorInfomation adaptorInfomation;
    private static ArrayList<ThingInformation> thingMasterTable = null;
    private static ArrayList<ThingControlInfo> thingControlMasterTable = null;
    private final CubeTasClient client;
   
    public ThingsManagement(AdaptorInfomation adaptorInfo){
        this.adaptorInfomation = adaptorInfo;
        
        thingMasterTable = new ArrayList<ThingInformation>();
        thingControlMasterTable = new ArrayList<ThingControlInfo>();
        
        client = new CubeTasClient();

        registAdaptor();
        reRegistAll();
    }
    
    private void registAdaptor() {
        try {
            adaptorInfomation.checkRegistMsg(client.requestToCube(adaptorInfomation.createRegistMsg()));
        } catch (IOException ex) {
            Logger.getLogger(ThingsManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void registThing(ThingInfoInterface thing){
        try {
            thing.checkRegistMsg(client.requestToCube(thing.createRegistMsg()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }   
    }
   
    public void addThing(final String name, final String type, final String desc ){              

        ThingInformation thing = new ThingInformation(name, type, desc);

        for(int i = 0; i < thingMasterTable.size(); i++) {
            if(thingMasterTable.get(i).equals(thing)){
                return;
            }
        }

        thingMasterTable.add(thing);
    }
    
    public void addControlThing(final String name, final String type, final String desc, final int port){
                
        ThingControlInfo thing = new ThingControlInfo(name, type, desc, port);

        for(int i = 0; i < thingControlMasterTable.size(); i++) {
            if(thingControlMasterTable.get(i).equals(thing)){
                return;
            }
        }

        thingControlMasterTable.add(thing);
    }
   
    private void reRegistAll(){
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(true){

                    if(adaptorInfomation.getRegistStatus()){

                        try {
                            for(ThingInformation thing : thingMasterTable){
                                if(!thing.getRegistStatus()){
                                    registThing(thing);
                                }
                            }
                            
                            Thread.sleep(1000);
                            
                            for(ThingControlInfo thing : thingControlMasterTable){
                                if(!thing.getRegistStatus()){
                                    registThing(thing);
                                }
                            }
                            
                            Thread.sleep(1000);
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ThingsManagement.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }).start();
        
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while(true) {                    
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException ex) {
////                        Logger.getLogger(ThingsManagement.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                
//                    if(adaptorInfomation.getRegistStatus()){
//
//                        for(ThingControlInfo thing : thingControlMasterTable){
//                            if(!thing.getRegistStatus()){
//                                registThing(thing);
//                            }
//                        }
//                    }
//
//                }
//            }
//        }).start();
    }
    
    public static ThingInformation getThingIDByName(String thingName){
      
        for(ThingInformation thing : thingMasterTable){
            if(thingName.equals(thing.getThingName())){
                if(thing.getRegistStatus()){
                    return thing;
                }
            }
        }
        return null;
    }
    
    public static ThingControlInfo getConThingByName(String thingName){
        
        for(ThingControlInfo thing : thingControlMasterTable){
            if(thingName.equals(thing.getThingName())){
                if(thing.getRegistStatus()){
                    return thing;
                }
            }
        }
        return null;
    }
}
