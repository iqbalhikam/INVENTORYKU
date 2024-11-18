
package com.inventory.system.model;

import java.time.LocalDate;

/**
 *
 * @author iqbal
 */
public class ModelPersentase {
  private LocalDate date;
  private int totalIn;
  private int totalOut;
  private double persentaseIn;
  private double persentaseOut;
  
  public ModelPersentase() {
  }
  
  public ModelPersentase(LocalDate date, int totalIn, int totalOut, double persentaseIn, double persentaseOut) {
    this.date = date;
    this.totalIn = totalIn;
    this.totalOut = totalOut;
    this.persentaseIn = persentaseIn;
    this.persentaseOut = persentaseOut;
  }
  
  public LocalDate getDate(){
    return date;
  }
  
  public void setDate(LocalDate date){
    this.date = date;
  }
  
  public int getTotalIn(){
    return totalIn;
  }
  
  public void setTotalIn(int totalIn){
    this.totalIn = totalIn;
  }
  
  public int getTotalOut(){
    return totalOut;
  }
  
  public void setTotalOut(int totalOut){
    this.totalOut = totalOut;
  }
  
  public double getPersentaseIn(){
    return persentaseIn;
  }
  
  public void setPersentaseIn(double persentaseIn){
    this.persentaseIn = persentaseIn;
  }
  
  public double getPersentaseOut(){
    return persentaseOut;
  }
  
  public void setPersentaseOut(double persentaseOut){
    this.persentaseOut = persentaseOut;
  }
  
}
