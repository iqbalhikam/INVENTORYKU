
package com.inventory.system.model;

/**
 *
 * @author iqbal
 */public class ModelItems {
      private int item_id;
      private int category_id;
      private String item_name;
      private String category_name;
      private String kode_barang;
      private int quantity;
      private double price;
      private double total_price;
      private String created_at;
      private String updated_at;
      private int gudang_id;
      
      // Getter and setter for user_id
      public int getItemID() {
            return item_id;
      }
      
      public void setItemID(int user_id) {
            this.item_id = user_id;
      }
      public int getCategoryID() {
            return category_id;
      }
      
      public void setCategoryID(int category_id) {
            this.category_id = category_id;
      }
      
      public int getGudangId(){
        return gudang_id;
      }
      
      public void setGudangId(int gudang_id){
        this.gudang_id = gudang_id;
      }
      
      public int getQuntity(){
            return quantity;
      }
      
      public void setQuantity(int quantity){
            this.quantity = quantity;
      }
      public double getPrice(){
            return price;
      }
      
      public void setPrice(int price){
            this.price = price;
      }
      public double getTotalPrice(){
            return total_price;
      }
      
      public void setTotalPrice(int total_price){
            this.total_price = total_price;
      }
      
      public String getItemName(){
            return item_name;
      }
      
      public void setItemName(String item_name){
            this.item_name = item_name;
      }
      public String getCategoryName(){
            return category_name;
      }
      
      public void setCategoryName(String category_name){
            this.category_name = category_name;
      } 
      
      public String getKodeBarang(){
            return kode_barang;
      }
      
      public void setKodeBarang(String kode_barang){
            this.kode_barang = kode_barang;
      }
      
      
      public String getCreatedAt(){
            return created_at;
      }
      
      public void setCreatedAt(String created_at){
            this.created_at = created_at;
      }
      
      
      public String getUpdatedAt(){
            return updated_at;
      }
      
      public void setUpdatedAt(String updated_at){
            this.updated_at = updated_at;
      }
      
      public ModelItems(){
            
      }
      
      public ModelItems(int category_id){
            this.category_id = category_id;
      }
      public ModelItems(String item_name){
            this.item_name = item_name;
      }
      public ModelItems(int item_id, int category_id, int gudang_id, int quantity, double price, double total_price, String category_name , String item_name, String created_at, String updated_at, String kode_barang){
            this.item_id = item_id;
            this.category_id = category_id;
            this.gudang_id = gudang_id;
            this.category_name = category_name;
            this.quantity = quantity;
            this.price = price;
            this.total_price = total_price;
            this.item_name = item_name;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.kode_barang = kode_barang;
      }
      public ModelItems( int quantity, double price, double total_price, String category_name, String item_name, String created_at, String updated_at, String kode_barang){
            this.quantity = quantity;
            this.price = price;
            this.total_price = total_price;
            this.category_name = category_name;
            this.item_name = item_name;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.kode_barang = kode_barang;
      }
}
