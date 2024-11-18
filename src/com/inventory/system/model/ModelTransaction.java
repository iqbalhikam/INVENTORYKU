
package com.inventory.system.model;
import java.sql.Date;

/**
 *
 * @author iqbal
 */
public class ModelTransaction {
  private int user_id;
  private int transaction_id;
  private int item_id;
  private int quantity;
  private String transactionType;
  private String note;
  private String kode_transaction;
  private Date transaction_date; 
  private double totalPrice;
  
  public int getUserID() {
    return user_id;
  }
  
  public void setUserID(int user_id) {
    this.user_id = user_id;
  }
  
  public int getTransactionID() {
    return transaction_id;
  }
  
  public void setTransactionID(int transaction_id) {
    this.transaction_id = transaction_id;
  }
  
  public int getItemID() {
    return item_id;
  }
  
  public void setItemID(int item_id) {
    this.item_id = item_id;
  }
  
  public int getQuantity() {
    return quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public String getTransactionType() {
    return transactionType;
  }
  
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }
  
  public Date getTransactionDate() {
    return transaction_date;
  }
  
  public double getTotalPrice() {
    return totalPrice;
  }
  
  public void setTotalPrice(double totalPrice){
    this.totalPrice = totalPrice;
  }
  
  
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public String getKodeTransaction(){
    return kode_transaction;
  }
  
  public void setKodeTransaction(String kode_transaction){
    this.kode_transaction = kode_transaction;
  }
  
  public ModelTransaction() {
  }
  
  public ModelTransaction(int user_id){
    this.user_id = user_id;
  }
  public ModelTransaction(Date transaction_date, String transaction_type, int total_transaction){
    this.transaction_date = transaction_date;
    this.transactionType = transaction_type;
    this.quantity = total_transaction;
  }
  public ModelTransaction(int user_id, int transaction_id, int item_id, int quantity, String transactionType, String note, Date  transaction_date, double totalPrice, String kode_transaction){
    this.user_id = user_id;
    this.transaction_id = transaction_id;
    this.item_id = item_id;
    this.quantity = quantity;
    this.transactionType = transactionType;
    this.note = note;
    this.transaction_date = transaction_date;
    this.totalPrice = totalPrice;
    this.kode_transaction = kode_transaction;
  }
  
  
  
}
