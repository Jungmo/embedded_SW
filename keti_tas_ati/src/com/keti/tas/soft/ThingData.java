package com.keti.tas.soft;


public class ThingData {
    
    public String makeThingDataMsg(String name, String type, String content){
            String thingDataUpload =
                "requestThingDataUpload;" +
                "<TAS>" +
                "<ThingData>" +
                "<containerName>" + name + "</containerName>" +
                "<typeOfContent>" + type + "</typeOfContent>" +
                "<content>" + content + "</content>" +
                "<linkType>no</linkType>" +
                "</ThingData>" +
                "</TAS>";
            
            return thingDataUpload;
    }
}
