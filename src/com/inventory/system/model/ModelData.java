
package com.inventory.system.model;

/**
 *
 * @author iqbal
 */
public class ModelData {

      
      public int getUserID() {
            return userID;
      }
      
      public void setUserID(int userID) {
            this.userID = userID;
      }
      
      public String getKodeProduk() {
            return kodeProduk;
      }
      
      public void setkodeProduk(String kodeProduk) {
            this.kodeProduk = kodeProduk;
      }
      
      public String getNamaProduk() {
            return namaProduk;
      }
      
      public void setNamaProduk(String namaProduk) {
            this.namaProduk = namaProduk;
      }
      
      public String getDeskripsiProduk() {
            return deskripsiProduk;
      }
      
      public void setDeskripsiProduk(String deskripsiProduk) {
            this.deskripsiProduk = deskripsiProduk;
      }
      
      public String getKategoriProduk() {
            return kategoriProduk;
      }
      
      public void setKategoriProduk(String kategoriProduk) {
            this.kategoriProduk = kategoriProduk;
      }
      
      public int getHargaBeli() {
            return hargaBeli;
      }
      
      public void setHargaBeli(int hargaBeli) {
            this.hargaBeli = hargaBeli;
      }
      
      public int getHargaJual() {
            return hargaJual;
      }
      
      public void setHargaJual(int hargaJual) {
            this.hargaJual = hargaJual;
      }
      
      public int getStockProduk() {
            return stockProduk;
      }
      
      public void setStockProduk(int stockProduk) {
            this.stockProduk = stockProduk;
      }
      
      public String getDate(){
            return created_at;
      }
      
      public void setDate(String created_at){
            this.created_at = created_at;
      }
        
      public ModelData(int userID) {
            this.userID = userID;
      }
      
      public ModelData(String kodeProduk, String namaProduk, String deskripsiProduk, String kategoriProduk, int hargaBeli, int hargaJual, int stockProduk){
            this.kodeProduk = kodeProduk;
            this.namaProduk = namaProduk;
            this.deskripsiProduk = deskripsiProduk;
            this.kategoriProduk = kategoriProduk;
            this.hargaBeli = hargaBeli;
            this.hargaJual = hargaJual;
            this.stockProduk = stockProduk;
      }
      
      public ModelData() {
      }
      
      private int userID;
      private String kodeProduk;
      private String namaProduk;
      private String deskripsiProduk;
      private String kategoriProduk;
      private int hargaBeli;
      private int hargaJual;
      private int stockProduk;
      private String created_at;
      
}
