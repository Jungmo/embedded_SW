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
public class ThingControlInfo implements ThingInfoInterface{
    
    private final String thingName;
    private final String thingType;
    private final int tasPort;
    private final String thingDesc;
    private boolean isRegiisted = false;
    
    public ThingControlInfo(String name, String type, String desc, int tasPort){
        this.thingName = name;
        this.thingType = type;
        this.tasPort = tasPort;
        this.thingDesc = desc;
    }
    
    @Override
    public String createRegistMsg(){
        String msg = 
                "requestThingControlRegistration;" +
                "<TAS>" + 
                "<ThingControl>" +
                "<tasPoc>" + tasPort + "</tasPoc>" +
                "<name>" + thingName + "</name>" +
                "<description>" + thingDesc + "</description>" +
                "<cmdType>" + thingType + "</cmdType>" +
                "<execReqArgs>1</execReqArgs>" +
                "<execMode>1</execMode>" +
                "<execFrequency>0</execFrequency>" +
                "<execDelay>0</execDelay>" +
                "<execNumber>0</execNumber>" +
                "</ThingControl>" +
                "</TAS>";
        
        return msg;
    }
    
    @Override
    public void checkRegistMsg(String msg){
        if(msg.length() > 0){
            String[] value = msg.split(";");
            if("registSuccess".equals(value[0])){
                setRegistStatus(true);
            }
            else if("registFailed".equals(value[0])) {
            	if("AlreadyRegisterd".equals(value[2])) setRegistStatus(true);
            }
        }
    }
    
    @Override
    public String getThingName(){
        return this.thingName;
    }
    
    @Override
    public boolean getRegistStatus(){
        return isRegiisted;
    }
    
    private void setRegistStatus(boolean isRegisted){
        this.isRegiisted = isRegisted;
    }
    
    @Override
    public boolean equals(ThingInfoInterface t){
        if(thingName.equals(t.getThingName())){
            return true;
        } else{
            return false;
        }
    }
}
