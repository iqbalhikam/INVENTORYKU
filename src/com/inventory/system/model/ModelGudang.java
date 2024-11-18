package com.inventory.system.model;

/**
 *
 * @author iqbal
 */
public class ModelGudang {
  private int gudangId;
  private String gudangName;
  private String location;
  private int capacity;
  
  public int getGudangId(){
    return gudangId;
  }
  
  public void setGudangId(int gudangId){
    this.gudangId = gudangId;
  }
  
  public String getGudangName(){
    return gudangName;
  }
  
  public void setGudangName(String gudangName){
    this.gudangName = gudangName;
  }
  
  public String getLocation(){
    return location;
  }
  
  public void setLocation(String location){
    this.location = location;
  }
  
  public int getCapacity(){
    return capacity;
  }
  
  public void setCapacity(int capacity){
    this.capacity = capacity;
  }
  
  public ModelGudang(int gudangId, String gudangName, String location, int capacity ){
    this.gudangId = gudangId;
    this.gudangName = gudangName;
    this.location = location;
    this.capacity = capacity;
  }
  
}
