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
public class ThingInformation implements ThingInfoInterface{
    
    private final String thingName;
    private final String containerType;
    @SuppressWarnings("unused")
	private final String description;    
    private String thingID;    
    private boolean isRegiisted = false;
    
    public ThingInformation (String name, String type, String des){
        this.thingName = name;
        this.containerType = type;
        this.description = des;
    }
    
    @Override
    public String createRegistMsg(){
        String thingRegist =
            "requestThingRegistration;" +
            "<TAS>" +
            "<ThingProfile>" +
            "<name>" + thingName + "</name>" +
            "<containerType>" + containerType + "</containerType>" +
            "<uploadCondition>nothing</uploadCondition>" +
            "<uploadConditionValue>nothing</uploadConditionValue>" +
            "</ThingProfile>" +
            "</TAS>";
        
        return thingRegist;
    }
    
    @Override
    public void checkRegistMsg(String msg){
        if(msg.length() > 0){
            String[] value = msg.split(";");
            String header = value[0];
            String body = value[1];
            if("registSuccess".equals(header)){
                value = body.split(",");
                String name = value[0];
                String id = value[1];
                if(thingName.equals(name)){
                    setThingID(id);
                    setRegistStatus(true);
                }
            }
            else if("registFailed".equals(value[0])) {
            	if("AlreadyRegisterd".equals(value[2])) {
            		value = body.split(",");
                    String name = value[0];
                    String id = value[1];
                    if(thingName.equals(name)){
                        setThingID(id);
                        setRegistStatus(true);
                    }            		
            	}
            }
        }
    }
    
    @Override
    public String getThingName(){
        return this.thingName;
    }
    
    public String getThingID(){
        return this.thingID;
    }
    
    private void setThingID(String ID){
        this.thingID = ID;
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
        return thingName.equals(t.getThingName());
    }
}
