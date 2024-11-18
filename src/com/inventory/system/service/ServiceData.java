package com.inventory.system.service;

import com.inventory.connection.DatabaseConnection;
import com.inventory.model.ModelUser;
import com.inventory.system.form.Form_1;
import com.inventory.system.model.ModelBarangMasuk;
import com.inventory.system.model.ModelCategories;
import com.inventory.system.model.ModelGudang;
import com.inventory.system.model.ModelItems;
import com.inventory.system.model.ModelTransaction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iqbal
 */
public class ServiceData {
      private final Connection con;
      
      public ServiceData() {
            try {
                  DatabaseConnection dbConnection = DatabaseConnection.getInstance();
                  dbConnection.connectToDatabase();
                  con = dbConnection.getConnection();
            } catch (SQLException e) {
                  throw new RuntimeException("Failed to connect to the database");
            }
      }
      
//    GET CATEGORY START
      public List<ModelCategories> getCategory() throws SQLException{
            PreparedStatement pstmtInsert = null;
            int category_id;
            String category_name;
            List<ModelCategories> categoryList = new ArrayList<>();
            String sql = "SELECT * FROM categories";
            
            try {
                  pstmtInsert  = con.prepareStatement(sql);
                  ResultSet r = pstmtInsert.executeQuery();
                  while (r.next()) {
                        category_id = r.getInt("category_id");
                        category_name = r.getString("category_name");
                        ModelCategories category = new ModelCategories(category_id, category_name);
                        categoryList.add(category);
                  }
                  
            } catch (SQLException e) {
                  if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                      System.out.println(ex);
                }
            }
            System.out.println("tidak terkoneksi dengan database\ncode erorr: "+e);
            throw e;
            }finally {
            if (pstmtInsert != null) {
                pstmtInsert.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
                
        }
            
            return categoryList;
      }
//    GET CATEGORY END
      
      
      
//    CREATE CATEGORY START
      public String setCategory(int user_id, String category_name,String description) throws SQLException{
            PreparedStatement pstmtInsert = null;
            String success = null;
            if (category_name.isEmpty() || description.isEmpty()) {
                  success = null;
            }else{
            try {
                  String sql = "INSERT INTO categories (category_name, description ) VALUES (?, ?)";
                  pstmtInsert = con.prepareStatement(sql);
                  pstmtInsert.setString(1, category_name);
                  pstmtInsert.setString(2, description);
                  int rowsAffected = pstmtInsert.executeUpdate();
                  if (rowsAffected > 0) {
                        success = "Berhasil";
                        String sqlLog = "INSERT INTO audit_log (user_id, action, table_name, record_id, new_value, action_date) VALUES (?, 'Menambah Data Kategori', 'categories', ?, ?, NOW());";
                        pstmtInsert = con.prepareStatement(sqlLog);
                        pstmtInsert.setInt(1, user_id);
                        pstmtInsert.setInt(2, user_id);
                        pstmtInsert.setString(3, category_name);
                        int addLog = pstmtInsert.executeUpdate();
                        if (addLog > 0) {
                          success = "berhasil";
                        }
                  }
                  
            } catch (SQLException e) {
            if (con != null) {
                try {
                        con.rollback();
                } catch (SQLException ex) {
                        System.out.println(ex);
                }
            }
            System.out.println("tidak terkoneksi dengan database\ncode erorr: "+e);
            throw e;
            } finally {
                  if (pstmtInsert != null) {
                        pstmtInsert.close();
                  }
                  if (con != null) {
                        con.setAutoCommit(true);
                  }       
            }
            }
        return success;
      }
//    CREATE CATEGORY END
      
      
      
      
      
//    GET ALL ITEMS START
      public List<ModelItems> getAllItems() throws SQLException{
            PreparedStatement pstmtInsert = null;
            List<ModelItems> itemsList = new ArrayList<>();
            try {
                  
            pstmtInsert  = con.prepareStatement("SELECT id.category_id, id.category_name, ci.* FROM categories id JOIN items ci ON id.category_id = ci.category_id");
            ResultSet r = pstmtInsert.executeQuery();
            while (r.next()) {
                  String category_name = r.getString("category_name");
                  int item_id = r.getInt("item_id");
                  int category_id = r.getInt("category_id");
                  int gudang_id = r.getInt("gudang_id");
                  int quantity = r.getInt("quantity");
                  String item_name = r.getString("item_name");
                  double price = r.getDouble("price");
                  double total_price = r.getDouble("total_price");
                  String created_at = r.getString("created_at");
                  String updated_at = r.getString("updated_at");
                  String kode_barang = r.getString("kode_item");
                  
                  ModelItems item =  new ModelItems(item_id, category_id, gudang_id, quantity, price, total_price, category_name, item_name, created_at, updated_at, kode_barang);
                  itemsList.add(item);
            }
            } catch (SQLException e) {
                  if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                      System.out.println(ex);
                }
            }
            System.out.println("tidak terkoneksi dengan database\ncode erorr: "+e);
            throw e;
            }finally {
            if (pstmtInsert != null) {
                pstmtInsert.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
                
        }
            
            return itemsList;
      }
//    GET ALL ITEMS END


//    CREATE ITEMS START
    public boolean setItem(int record_id, ModelUser user_id, int categoryID, int gudang_id, String item_name, double price, String code_item, int quantitiy) throws SQLException {
      double total_price = price * quantitiy;  
      PreparedStatement pstmtInsert = null;
        boolean success = false;
        PreparedStatement pstmtCheck;
        ResultSet rsCheck ;
          if (item_name.isEmpty() ) {
                success = false;
          }else{
        try {
            con.setAutoCommit(false);
            
            String SQLchecking = "SELECT category_id FROM categories WHERE category_id = ?";
            pstmtCheck = con.prepareStatement(SQLchecking);
            pstmtCheck.setInt(1, categoryID);
            rsCheck = pstmtCheck.executeQuery();
            if (!rsCheck.next()) {
                  System.out.println("Category dengan ID " + categoryID + " tidak ditemukan.");
            }else{
                  String sql = "INSERT INTO items (category_id, gudang_id, item_name, price, kode_item, quantity, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
                  pstmtInsert = con.prepareStatement(sql);
                  
                  pstmtInsert.setInt(1, categoryID);
                  pstmtInsert.setInt(2, gudang_id);
                  pstmtInsert.setString(3, item_name);
                  pstmtInsert.setDouble(4, price);
                  pstmtInsert.setString(5, code_item);
                  pstmtInsert.setInt(6, quantitiy);
                  pstmtInsert.setDouble(7, total_price);
                  int rowsAffected = pstmtInsert.executeUpdate();
                  if (rowsAffected > 0) {
                        success = true;
                        String sqlLog = "INSERT INTO audit_log (user_id, action, table_name, record_id, new_value, action_date) VALUES (?, 'Menambah Data Barang', 'items', ?, ?, NOW());";
                        pstmtInsert = con.prepareStatement(sqlLog);
                        pstmtInsert.setInt(1, user_id.getUserID());
                        pstmtInsert.setInt(2, record_id);
                        pstmtInsert.setString(3, item_name);
                        int statusLog = pstmtInsert.executeUpdate();
                        if (statusLog > 0) {
                          success = true;
                        }
                        
                  }
                  con.commit();
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                      System.out.println(ex);
                }
            }
            System.out.println("tidak terkoneksi dengan database\ncode erorr: "+e);
            throw e;
        } finally {
            if (pstmtInsert != null) {
                pstmtInsert.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
                
        }
          }
        return success;
    }
//    CREATE ITEMS END
    



    // UPDATE ITEMS START
public boolean setUpdateItem(ModelUser user_id, int record_id, int categoryID, int gudang_id, String item_name, double price, String kode_item, int quantity) throws SQLException {
    PreparedStatement pstmtUpdate = null;
    PreparedStatement pstmtCheck = null;
    PreparedStatement pstmtOldValue = null;
    PreparedStatement pstmtLog = null;
    ResultSet rsCheck = null;
    ResultSet rsOldValue = null;
    boolean success = false;

    if (item_name.isEmpty()) {
        success = false;
    } else {
        try {
            con.setAutoCommit(false);

            // Check if the category exists
            String SQLchecking = "SELECT category_id FROM categories WHERE category_id = ?";
            pstmtCheck = con.prepareStatement(SQLchecking);
            pstmtCheck.setInt(1, categoryID);
            rsCheck = pstmtCheck.executeQuery();

            if (!rsCheck.next()) {
                System.out.println("Category dengan ID " + categoryID + " tidak ditemukan.");
            } else {
                // Retrieve old values before updating
                String sqlOld = "SELECT item_name, price, quantity FROM items WHERE kode_item = ?";
                pstmtOldValue = con.prepareStatement(sqlOld);
                pstmtOldValue.setString(1, kode_item);
                rsOldValue = pstmtOldValue.executeQuery();

                if (rsOldValue.next()) {
                    String oldItemName = rsOldValue.getString("item_name");
                    double oldPrice = rsOldValue.getDouble("price");
                    int oldQuantity = rsOldValue.getInt("quantity");

                    // Update the item
                    String sql = "UPDATE items SET category_id = ?, gudang_id = ?, item_name = ?, price = ?, quantity = ? WHERE kode_item = ?";
                    pstmtUpdate = con.prepareStatement(sql);
                    pstmtUpdate.setInt(1, categoryID);
                    pstmtUpdate.setInt(2, gudang_id);
                    pstmtUpdate.setString(3, item_name);
                    pstmtUpdate.setDouble(4, price);
                    pstmtUpdate.setInt(5, quantity);
                    pstmtUpdate.setString(6, kode_item);

                    int rowsAffected = pstmtUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        // Insert log entry
                        String sqlLog = "INSERT INTO audit_log (user_id, action, table_name, record_id, old_value, new_value, action_date) VALUES (?, 'UPDATE', 'items', ?, ?, ?, NOW())";
                        pstmtLog = con.prepareStatement(sqlLog);
                        pstmtLog.setInt(1, user_id.getUserID());
                        pstmtLog.setInt(2, record_id);
                        pstmtLog.setString(3, String.format("Name: %s, Price: %.2f, Quantity: %d", oldItemName, oldPrice, oldQuantity));
                        pstmtLog.setString(4, String.format("Name: %s, Price: %.2f, Quantity: %d", item_name, price, quantity));

                        int statusLog = pstmtLog.executeUpdate();
                        if (statusLog > 0) {
                            success = true;
                        }
                    }
                    con.commit();
                } else {
                    System.out.println("Item with kode_item " + kode_item + " not found.");
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            System.out.println("Tidak terkoneksi dengan database\nCode error: " + e);
            throw e;
        } finally {
            if (rsCheck != null) {
                try {
                    rsCheck.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (rsOldValue != null) {
                try {
                    rsOldValue.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (pstmtCheck != null) {
                try {
                    pstmtCheck.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (pstmtOldValue != null) {
                try {
                    pstmtOldValue.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (pstmtUpdate != null) {
                try {
                    pstmtUpdate.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (pstmtLog != null) {
                try {
                    pstmtLog.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
        }
    }
    return success;
}
// UPDATE ITEMS END
    
    
    // DELETE ITEMS BY CODE ITEM START
    public boolean deleteItemByCode(String code) throws SQLException {
      PreparedStatement pstmtDeleted = null;
    PreparedStatement pstmtCheck = null;
    ResultSet rsCheck = null;
    boolean success = false;

    if (code.isEmpty()) {
        success = false;
    } else {
        try {
            con.setAutoCommit(false);

            String SQLchecking = "SELECT kode_item FROM items WHERE kode_item = ?";
            pstmtCheck = con.prepareStatement(SQLchecking);
            pstmtCheck.setString(1, code);
            rsCheck = pstmtCheck.executeQuery();

            if (!rsCheck.next()) {
                System.out.println("Item dengan kode " + code + " tidak ditemukan.");
                success = false;
            } else {
                String sql = "DELETE FROM items WHERE kode_item = ?";
                pstmtDeleted = con.prepareStatement(sql);
                pstmtDeleted.setString(1, code);

                int rowsAffected = pstmtDeleted.executeUpdate();
                if (rowsAffected > 0) {
                    success = true;
                }
                con.commit();
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            System.out.println("Tidak terkoneksi dengan database\nCode error: " + e);
            throw e;
        } finally {
            if (rsCheck != null) {
                try {
                    rsCheck.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (pstmtCheck != null) {
                try {
                    pstmtCheck.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (pstmtDeleted != null) {
                try {
                    pstmtDeleted.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
        }
    }
    return success;
}
// DELETE ITEMS BY CODE ITEM END

      
    
    
    
    
//    GET TREANSACTION BY USER ID START
      public List<ModelTransaction> getTransactionByUiTtype(ModelUser userID, String transaction_type) throws SQLException{
            int user_id;
            int transaction_id;
            int item_id;
            int quantity;
            String transcT;
            Date transcD;
            String note;
            double totalPrice;
            String kode_transaction;
            List<ModelTransaction> dataList = new ArrayList<>();
            if (transaction_type.equals("")) {
              transaction_type = "in AND out";
            }
            PreparedStatement p = con.prepareStatement("SELECT ui.user_id, tu.* FROM users ui JOIN transactions tu ON ui.user_id = tu.user_id WHERE ui.user_id = ? AND transaction_type = ?");
            p.setInt(1, userID.getUserID());
            p.setString(2, transaction_type);
            ResultSet r = p.executeQuery();
            if (r.first()) {
                  user_id = r.getInt("user_id");
                  transaction_id = r.getInt("transaction_id");
                  item_id = r.getInt("item_id");
                  quantity = r.getInt("quantity");
                  transcT= r.getString("transaction_type");
                  transcD = r.getDate("transaction_date");
                  note = r.getString("note");
                  totalPrice = r.getDouble("total_price");
                  kode_transaction = r.getString("kode_transaction");
            }else{
                  user_id = 0;
                  transaction_id = 0;
                  item_id = 0;
                  quantity = 0;
                  transcT= "null";
                  transcD = null;
                  note = "null";
                  totalPrice = 0;
                  kode_transaction = "null";
                  System.out.println("Tidak ada transaksi dari user ini: " + userID.getUserName());
            }
            ModelTransaction data = new ModelTransaction(user_id, transaction_id, item_id,quantity,transcT,note, transcD, totalPrice, kode_transaction);
            dataList.add(data);
            return dataList;
      }
//    GET TREANSACTION BY USER ID END
      
      
      
      
      
//    GET TOTAL TREANSACTION STRAT
      public int getTotalItemIsReady() throws SQLException {
        int totalCount = 0;
        
        // Query SQL untuk menghitung jumlah item dengan quantity != 0
        String sql = "SELECT COUNT(*) AS total_count FROM items WHERE quantity != 0";
        
        // Persiapkan statement
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Ambil nilai jumlahnya dari hasil ResultSet
            if (rs.next()) {
                totalCount = rs.getInt("total_count");
            }
        }
        
        return totalCount;
    }
//    GET TOTAL TRNASACTION END
    
      
      
      
      
//    SET TRANSACTION BY USER ID START
public boolean setTransactionByUserId(int userID, int itemID, int quantity, String transactionType, String note, double totalPrice, String kode_transaction) throws SQLException {
    PreparedStatement pstmtInsertOrUpdate;
    PreparedStatement pstmtUpdateItems = null;
    PreparedStatement pstmtCheck;
    ResultSet rs;
    boolean success = false;

    if (transactionType.isEmpty() || note.isEmpty()) {
        // Handle empty transactionType or note
    } else {
        try {
            con.setAutoCommit(false);

            // Step 1: Check available quantity (for "out" transactions)
            if (transactionType.equals("out")) {
                String sqlCheck = "SELECT quantity FROM items WHERE item_id = ?";
                pstmtCheck = con.prepareStatement(sqlCheck);
                pstmtCheck.setInt(1, itemID);
                rs = pstmtCheck.executeQuery();

                if (rs.next()) {
                    int availableQuantity = rs.getInt("quantity");
                    if (availableQuantity < quantity) {
                        throw new SQLException("Insufficient quantity available for item_id: " + itemID);
                    }
                } else {
                    throw new SQLException("Item not found with item_id: " + itemID);
                }
            }

                // Transaction does not exist, insert a new one
                String sqlInsert = "INSERT INTO transactions (user_id, item_id, quantity, transaction_type, note, total_price, kode_transaction) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmtInsertOrUpdate = con.prepareStatement(sqlInsert);
                pstmtInsertOrUpdate.setInt(1, userID);
                pstmtInsertOrUpdate.setInt(2, itemID);
                pstmtInsertOrUpdate.setInt(3, quantity);
                pstmtInsertOrUpdate.setString(4, transactionType);
                pstmtInsertOrUpdate.setString(5, note);
                pstmtInsertOrUpdate.setDouble(6, totalPrice);
                pstmtInsertOrUpdate.setString(7, kode_transaction);

            pstmtInsertOrUpdate.executeUpdate();

            // Step 3: Update items table
            // Get price from items table
            String sqlGetPrice = "SELECT price FROM items WHERE item_id = ?";
            pstmtCheck = con.prepareStatement(sqlGetPrice);
            pstmtCheck.setInt(1, itemID);
            rs = pstmtCheck.executeQuery();

            double price = 0.0;
            if (rs.next()) {
                price = rs.getDouble("price");
            } else {
                throw new SQLException("Item not found with item_id: " + itemID);
            }

            // Update items table
            String sqlUpdateItems;
          switch (transactionType) {
            case "in":
              sqlUpdateItems = "UPDATE items SET quantity = quantity + ?, total_price = ? * quantity WHERE item_id = ?";
              pstmtUpdateItems = con.prepareStatement(sqlUpdateItems);
              pstmtUpdateItems.setInt(1, quantity);
              pstmtUpdateItems.setDouble(2, price);
              pstmtUpdateItems.setInt(3, itemID);
              break;
            case "out":
              sqlUpdateItems = "UPDATE items SET quantity = quantity - ?, total_price = ? * quantity WHERE item_id = ?";
              pstmtUpdateItems = con.prepareStatement(sqlUpdateItems);
              pstmtUpdateItems.setInt(1, quantity);
              pstmtUpdateItems.setDouble(2, price);
              pstmtUpdateItems.setInt(3, itemID);
              break;
            default:
              throw new IllegalArgumentException("Invalid transaction type: " + transactionType);
          }

            pstmtUpdateItems.executeUpdate();

            con.commit(); // Commit transaction
            success = true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    // Handle rollback error
                }
            }
            throw e;
        } finally {
            // Close resources in finally block
            // ...
        }
    }

    return success;
}
//    SET TRANSACTION BY USER ID END

    
    
    
//    GET BARANG MASUK START
    public List<ModelBarangMasuk> getBarangMasuk(ModelUser userId, String transactionType) throws SQLException {
        List<ModelBarangMasuk> dataList = new ArrayList<>();

        String sql = "SELECT "
                + "t.transaction_id, t.kode_transaction, t.total_price, t.note, i.updated_at, i.kode_item, i.item_name, t.quantity AS item_quantity, i.price AS item_price, c.category_name "
                + "FROM "
                + "transactions t "
                + "JOIN"
                + " items i "
                + "ON"
                + " t.item_id = i.item_id "
                + "JOIN "
                + "categories c "
                + "ON "
                + "i.category_id = c.category_id "
                + "WHERE"
                + " t.user_id = ? "
                + "AND"
                + " t.transaction_type = ? ";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, userId.getUserID());
            p.setString(2, transactionType);
            ResultSet r = p.executeQuery();

            while (r.next()) {
                int transactionId = r.getInt("transaction_id");
                String kodeTransaction = r.getString("kode_transaction");
                Date transactionDate = r.getDate("updated_at");
                double totalPrice = r.getDouble("total_price");
                String kodeItem = r.getString("kode_item");
                String itemName = r.getString("item_name");
                int itemQuantity = r.getInt("item_quantity");
                double itemPrice = r.getDouble("item_price");
                String categoryName = r.getString("category_name");
                String noteTransaction = r.getString("note");

                ModelBarangMasuk data = new ModelBarangMasuk(userId, transactionId, kodeItem, itemName, categoryName, transactionType, transactionDate, totalPrice, kodeTransaction, itemQuantity, itemPrice, noteTransaction);
                dataList.add(data);
            }
        }

        return dataList;
    }
//    GET BARANG MASUK END

  
    
//  GET CAPACITY GUDANG BY ID START
  public int getCapacity(int id) throws SQLException{
    PreparedStatement p;
    int capacity = 0;
    String sql = "SELECT capacity FROM warehouse WHERE gudang_id = ? ";
    try {
    p = con.prepareStatement(sql);
    p.setInt(1, id);
    ResultSet r = p.executeQuery();
      if (r.next()) {
        capacity = r.getInt("capacity");
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    
    return capacity;
  }
//  GET CAPACITY GUDANG BY ID END
    
    
    
    
//    GET TOTAL STOCK GUDANG START
    public int getTotal() throws SQLException {
        String sql = "SELECT SUM(quantity) FROM items ";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
//    GET TOTAL STOCK GUDANG END
    

    

//    GET TOTAL OUT START
    public int getTotalInlOut(String type , int  leng) throws SQLException {
        String sql = "SELECT SUM(quantity) FROM transactions WHERE transaction_type = ? AND transaction_date >= DATE_SUB(CURDATE(), INTERVAL ? MONTH);";
            
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
              pstmt.setString(1, type);
              pstmt.setInt(2, leng);
              
          
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
//    GET TOTAL OUT END

    
    
//    GET PERSENTASE KAPASITAS GUDANG START
    public float getPersentase() throws SQLException {
      PreparedStatement pstmt;
      int capacity;
      float persentase = 0;
      String sqlG = "SELECT capacity FROM warehouse WHERE gudang_id = ?";
      try {
        pstmt = con.prepareStatement(sqlG);
        pstmt.setInt(1, 1);
        ResultSet r = pstmt.executeQuery();
        if (r.next()) {
          
          capacity = r.getInt("capacity");
          float sisaKapasitas = capacity - getTotal();
          persentase = sisaKapasitas / capacity * 100;
        persentase = Math.round(persentase * 100.0f) / 100.0f;
        }
      } catch (SQLException e) {
      }
      return persentase;
    }
//    GET PERSENTASE KAPASITAS GUDANG END
    
    
    
//    GET PERSENTASE IN OUT START
    public float getPersentaseInOut(String type, int leng) throws SQLException {
      float totalQuantity ;
      float persentase = 0;
      PreparedStatement p;
      String sql = "SELECT SUM(quantity) FROM transactions WHERE transaction_type = ? AND transaction_date >= DATE_SUB(CURDATE(), INTERVAL ? MONTH);";
      
        try {
          p = con.prepareStatement(sql);
          p.setString(1, type);
          p.setInt(2, leng);
          ResultSet r = p.executeQuery();
          if (r.next()) {
            totalQuantity = r.getInt(1);
            double kapacity = getCapacity(1);
            persentase = (float) (totalQuantity / kapacity * 100);
            persentase = Math.round(persentase * 100.0f) / 100.0f;
          }
          
      } catch (SQLException e) {
          System.out.println(e);
      }
        return persentase;
    }
//    GET PERSENTASE OUT END

  
    
    
//    GET ID CATEGORY BY ID START
  public int getIdCategoryByName(String name){
    PreparedStatement p;
    int category_id = 0;
    
    String sql = "SELECT category_id FROM categories WHERE category_name = ? ";
    try {
      p = con.prepareStatement(sql);
      p.setString(1, name);
      
      ResultSet r = p.executeQuery();
      
      if (r.next()) {
        category_id = r.getInt("category_id");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return category_id;
  }
//    GET ID CATEGORY BY ID END
      
  
  
  
  
  
// GET ALL GUDANG START
  public List<ModelGudang> getAllGudang(){
    PreparedStatement pstmtInsert ;
    List<ModelGudang> gudangList = new ArrayList<>();
    String sql = "SELECT * FROM warehouse";
    try {
      pstmtInsert = con.prepareStatement(sql);
      ResultSet r = pstmtInsert.executeQuery();
      if (r.next()) {
        int gudang_id = r.getInt("gudang_id");
        String gudang_name = r.getString("gudang_name");
        String location = r.getString("location");
        String capacity = r.getString("capacity");
        ModelGudang gudang = new ModelGudang(gudang_id, gudang_name, location, gudang_id);
        gudangList.add(gudang);
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    return gudangList;
  }
// GET ALL GUDANG END
 
  
  
  
//    GET ID GUDANG BY ID START
  public int getIdGudangByName(String name){
    PreparedStatement p;
    int gudang_id = 0;
    
    String sql = "SELECT gudang_id FROM warehouse WHERE gudang_name = ? ";
    try {
      p = con.prepareStatement(sql);
      p.setString(1, name);
      
      ResultSet r = p.executeQuery();
      
      if (r.next()) {
        gudang_id = r.getInt("gudang_id");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return gudang_id;
  }
//    GET ID GUDANG BY ID END
}

      
      
      
      
      
      
      

