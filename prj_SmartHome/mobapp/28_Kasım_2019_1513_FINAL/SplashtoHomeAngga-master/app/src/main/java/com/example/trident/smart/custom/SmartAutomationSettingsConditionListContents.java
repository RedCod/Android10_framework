package com.example.trident.smart.custom;

import java.util.ArrayList;

public class SmartAutomationSettingsConditionListContents {
    /**
     * using by "ActivityAutomationSettings.java"
     * for Conditions List items content management.
     */

    public SmartAutomationSettingsConditionListContents(){

    }
    private static ArrayList<SmartAndAutomationItem> arrayList_automationConditionItems = new ArrayList<SmartAndAutomationItem>();
    public void fill(){
        String[] name = {"Sıcaklık:Daha az 40°C","Hava:Karlı","Hava:Güneşli","Gün Doğumu/Gün Batımı:Gündoğumu","Nem:Nemli","İş günü 15:20","Switch:ON"};//item.
        String[] city = {"İstanbul","İstanbul","İstanbul","Denizli","İstanbul","Program","Plug Mutfak"};//so Desicription2
        String[] type = {"temperature","weather","weather","sunrise_sunset","humidity","schedule","wall_plug"};//@see DeviceType.java
        SmartAndAutomationItem automationConditionItem;
        arrayList_automationConditionItems.clear();
        for(int i=0;i<name.length;i++){
            automationConditionItem = new SmartAndAutomationItem();
            automationConditionItem.setName(name[i]);
            automationConditionItem.setLocation(city[i]);
            //automationConditionItem.setState(""); //do not use...
            automationConditionItem.setType(type[i]);
            arrayList_automationConditionItems.add(automationConditionItem);
        }
    }//fill


    /**
     * set Item
     * @param automationConditionItem
     */
    public void setItem(SmartAndAutomationItem automationConditionItem){
        arrayList_automationConditionItems.add(automationConditionItem);
    }

    /**
     *
     * @param index wich item(index)
     * @return value
     */
    public SmartAndAutomationItem getItem(int index){
        return arrayList_automationConditionItems.get(index);
    }

    /**
     *
     * @return ArrayList<SmartAndAutomationItem>
     */
    public ArrayList<SmartAndAutomationItem> getItems(){
        return  arrayList_automationConditionItems;
    }

    /**
     * Edit item.
     * @param position which one
     * @param smartAndAutomationItem  all of value.
     */
    public void editItem(int position,SmartAndAutomationItem smartAndAutomationItem){
        arrayList_automationConditionItems.set(position,smartAndAutomationItem);
    }

    /**
     * !!!!!!! DO NOT USE FOR CONDITIONS LIST ITEMS!!!!!!!!!!!!!!!!!!
     *
     * @param position which item(index)
     * @param state new value for update.
     */
    /*public void editState(int position,String state){
        SmartAndAutomationItem automationConditionItem = arrayList_automationConditionItems.get(position);
        automationConditionItem.setState(state);//update
        arrayList_automationConditionItems.set(position,automationConditionItem);
    }*/

    /**
     *
     * @param item item name to be deleted in the items.
     */
    public void remove(String item){
        SmartAndAutomationItem automationConditionItem;
        for(int i=0;i<arrayList_automationConditionItems.size();i++){
            automationConditionItem = arrayList_automationConditionItems.get(i);
            if(automationConditionItem.getName().equals(item)){
                arrayList_automationConditionItems.remove(i);
                break;
            }
        }
    }

    /**
     *
     * @param item which item
     * @return if found in items return true.
     */
    public boolean isExists(String item){
        SmartAndAutomationItem automationConditionItem;
        for(int i=0;i<arrayList_automationConditionItems.size();i++){
            automationConditionItem = arrayList_automationConditionItems.get(i);
            if(automationConditionItem.getName().equals(item))
                return true;
        }
        return false;
    }//isExists



}
