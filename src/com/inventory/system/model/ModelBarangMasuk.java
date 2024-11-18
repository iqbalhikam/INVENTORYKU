
package com.inventory.system.model;

/**
 *
 * @author iqbal
 */
import com.inventory.model.ModelUser;
import java.util.Date;

public class ModelBarangMasuk {
    private ModelUser userId;
    private int transactionId;
    private String kodeItem;
    private String itemName;
    private String categoryName;
    private String transactionType;
    private Date transactionDate;
    private double totalPrice;
    private String kodeTransaction;
    private int itemQuantity;
    private double itemPrice;
    private String noteTransaction;

    // Constructor
    public ModelBarangMasuk(ModelUser userId, int transactionId, String kodeItem, String itemName, String categoryName, String transactionType, Date transactionDate, double totalPrice, String kodeTransaction, int itemQuantity, double itemPrice, String noteTransaction) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.kodeItem = kodeItem;
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.totalPrice = totalPrice;
        this.kodeTransaction = kodeTransaction;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.noteTransaction = noteTransaction;
    }

    // Getters and Setters
    public ModelUser getUserId() {
        return userId;
    }

    public void setUserId(ModelUser userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getKodeItem() {
        return kodeItem;
    }

    public void setKodeItem(String kodeItem) {
        this.kodeItem = kodeItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getKodeTransaction() {
        return kodeTransaction;
    }

    public void setKodeTransaction(String kodeTransaction) {
        this.kodeTransaction = kodeTransaction;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public String getNote(){
      return noteTransaction;
    }
    
    public void setNote(String noteTransaction){
      this.noteTransaction = noteTransaction;
    }

    @Override
    public String toString() {
        return "ModelTransaction{" +
                "userId=" + userId +
                ", transactionId=" + transactionId +
                ", kodeItem='" + kodeItem + '\'' +
                ", itemName='" + itemName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", totalPrice=" + totalPrice +
                ", kodeTransaction='" + kodeTransaction + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", itemPrice=" + itemPrice +
                '}';
    }
}

