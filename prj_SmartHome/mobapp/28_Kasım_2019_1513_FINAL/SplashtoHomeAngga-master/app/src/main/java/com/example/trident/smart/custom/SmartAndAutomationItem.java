package com.example.trident.smart.custom;

public class SmartAndAutomationItem {
    /**
     * used by "ActivitySmartSettings.java,ActivityAutomationSettings.java"
     * For Actions and Conditions lists items content structure.
     */
    private String Name ="";//Device Name
    private String Location="";//Device Location: Mutfak,Oturma Odası,etc.
    private String State="";//Device State: Switch on-off,time etc.
    private String Type="";  //Device type :lamp,wall_plug,time_lapse etc. see "DeviceType.java
    public SmartAndAutomationItem(){
        //..
    }

    public void setName(String item){
        this.Name = item;
    }
    public String getName(){
        return this.Name;
    }
    public void setLocation(String loc){
        this.Location = loc;
    }
    public String getLocation(){
        return this.Location;
    }

    public void setState(String state){
        this.State = state;
    }
    public String getState(){
        return this.State;
    }

    public void setType(String type){
        this.Type = type;
    }
    public String getType(){
        return this.Type;
    }


}

