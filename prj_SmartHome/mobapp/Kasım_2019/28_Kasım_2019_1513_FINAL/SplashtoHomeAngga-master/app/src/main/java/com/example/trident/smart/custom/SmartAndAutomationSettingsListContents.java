package com.example.trident.smart.custom;

import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.SmartAndAutomationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmartAndAutomationSettingsListContents {
    /**
     * used by "ActivitySmartSettings.java" and "ActivityAutomationSettings.java"
     * for Action List items content management.
     */
     private static String TAG = "kerim[SmartAndAutomationSettingsListContents]";

    public SmartAndAutomationSettingsListContents(){

    }

    public int count(){
        return  arrayList_SmartAndAutomationItem.size();
    }
    //For Test: new V
    private static ArrayList<SmartAndAutomationItem> arrayList_SmartAndAutomationItem = new ArrayList<SmartAndAutomationItem>();
    public void fill(){
        String[] name = new String[] { "WD", "WD_2","Time-lapse","DD"};//todo: these values get from Database.
        String[] location = {"Oturma odası","Mutfak","","Salon"};//todo: these values get from Database.
        String[] state = {"Switch:OFF","Switch:ON","00:15","Switch:OFF"};//todo: these values get from Database.
        String[] type = {"lamp","wall_plug","time_lapse","lamp"};//todo:these values get from Database.
        SmartAndAutomationItem smartAndAutomationItem;
        arrayList_SmartAndAutomationItem.clear();
        for(int i=0;i<name.length;i++){//
            smartAndAutomationItem = new SmartAndAutomationItem();
            smartAndAutomationItem.setName(name[i]);
            smartAndAutomationItem.setLocation(location[i]);
            smartAndAutomationItem.setState(state[i]);
            smartAndAutomationItem.setType(type[i]);
            arrayList_SmartAndAutomationItem.add(smartAndAutomationItem);
        }
    }//fill()

    /**
     *
     * @return ArrayList<SmartAndAutomationItem>
     */
    public ArrayList<SmartAndAutomationItem> getItems(){
        return arrayList_SmartAndAutomationItem;
    }

    /**
     * set Item
     * @param smartAndAutomationItem
     */
    public void setItem(SmartAndAutomationItem smartAndAutomationItem){
        arrayList_SmartAndAutomationItem.add(smartAndAutomationItem);
    }

    /**
     *
     * @param index  item index(position)
     * @return SmartAndAutomationItem
     */
    public SmartAndAutomationItem getItem(int index){
        return arrayList_SmartAndAutomationItem.get(index);
    }

    /**
     *
     * @param position which item(index)
     * @param state new value for update.
     */
    public void editState(int position,String state){
        SmartAndAutomationItem smartAndAutomationItem = arrayList_SmartAndAutomationItem.get(position);
        smartAndAutomationItem.setState(state);
        arrayList_SmartAndAutomationItem.set(position,smartAndAutomationItem);
    }

    /**
     *
     * @param item item name to be deleted in the items.
     */
    public void remove(String item){
        SmartAndAutomationItem smartAndAutomationItem;
        for(int i=0;i<arrayList_SmartAndAutomationItem.size();i++){
            smartAndAutomationItem = arrayList_SmartAndAutomationItem.get(i);
            if(smartAndAutomationItem.getName().equals(item)) {
                arrayList_SmartAndAutomationItem.remove(i);
                break;
            }
        }
    }//remove

    /**
     *
     * @param item which item.
     * @return if found in items return true.
     */
    public boolean isExists(String item){
        SmartAndAutomationItem smartAndAutomationItem;
        for(int i=0;i<arrayList_SmartAndAutomationItem.size();i++){
            smartAndAutomationItem = arrayList_SmartAndAutomationItem.get(i);
            if(smartAndAutomationItem.getName().equals(item)) {
                return true;
            }
        }
        return false;
    }//isExists
}
