/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keti.tas.soft;

/**
 *
 * @author ChenNan
 */
public class AdaptorInfomation {
    public final int port;
    public final String name;
    public final String description;
    private boolean isRegiisted = false;
    
    public AdaptorInfomation(int port){
         this.name = "Sample TAS";
         this.port = port;
         this.description = "Sample TAS for Test";
    }
    
    public AdaptorInfomation(String name, int port, String desc){
        this.name = name;
        this.port = port;
        this.description = desc;
    }
   
    public String createRegistMsg(){
        String tasRegist =
                "requestTASRegistration;" +
                "<TAS>" +
                "<TASProfile>" +
                "<Poc>" + port + "</Poc>" +
                "<Name>" + name + "</Name>" +
                "<Description>" + description + "</Description>" +
                "</TASProfile>" +
                "</TAS>";
        
        return tasRegist;
    }
    
    public void checkRegistMsg(String msg){
        if(msg.length() > 0){
            String[] value = msg.split(";");
            if("registSuccess".equals(value[0])){
                if("ThingAdaptationSoftware".equals(value[1])){
                    setRegistStatus(true);
                }
            }
            else if("registFailed".equals(value[0])) {
            	if("AlreadyRegisterd".equals(value[1])) setRegistStatus(true);
            }
        }
    } 
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getPort(){
        return this.port;
    }
    
    public boolean getRegistStatus(){
        return isRegiisted;
    }
    
    private void setRegistStatus(boolean isRegisted){
        this.isRegiisted = isRegisted;
    }
}
