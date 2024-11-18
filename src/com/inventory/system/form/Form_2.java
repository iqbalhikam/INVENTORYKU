package com.inventory.system.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.inventory.connection.DatabaseConnection;
import com.inventory.model.ModelUser;
import com.inventory.system.model.ModelBarangMasuk;
import com.inventory.system.model.ModelItems;
import com.inventory.system.service.ServiceData;
import com.inventory.system.util.Util;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.toast.Notifications;


public final class Form_2 extends javax.swing.JPanel {
  ServiceData serviceData = new ServiceData();
  Util util = new Util();
  ModelUser user = new ModelUser();
  private Connection con;
  private boolean stockMode = true;
  
  public Form_2(ModelUser user) throws SQLException {
    this.user = user;
    initComponents();
    initCombobox();
    setTotalPrice();
    initTabel();
    util.setColorSelectedJcombobox(JCombobox);
    addCustomMouseListeners();
    FieldHarga.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Harga Barang");
    FieldTotalHarga.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Total Harga");
    FieldNote.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Keterangan...");
    FieldStock.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Stok Masuk...");
    FieldSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
  }
  
  
  
  
  //  SET BTN LOCATION START
  private void addCustomMouseListeners() {
    MouseAdapter mouseListener = new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        if (stockMode) {
          util.animateButtonTo(BTNAddBarang, 30, 2);
        } else {
          util.animateButtonTo(BTNAddBarang, 150, 2);
        }
      }
      
      @Override
      public void mouseExited(MouseEvent e) {
        util.animateButtonTo(BTNAddBarang, 30, 2);
      }
    };
    jPanel1.addMouseListener(mouseListener);
  }
//  SET BTN LOCATION END
  
  
  
  
  
  
  
//  SET TOTAL PRICE START
  private void setTotalPrice(){
    FieldStock.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        
        try {
          
          double priceItem = getItemByName(JCombobox.getSelectedItem().toString()).getItemPrice();
          FieldHarga.setText(util.formatRupiah(priceItem));
          String text = FieldStock.getText();
          if (isNumeric(text)) {
            if (serviceData.getCapacity(1) - serviceData.getTotal() < Integer.parseInt(text) ) {
              System.out.println("gudang kapacity = " + serviceData.getPersentase());
              jPanel1.setLocation(30, 2);
              stockMode = false;
              Notifications.getInstance().show(Notifications.Type.INFO,Notifications.Location.BOTTOM_LEFT, "KAPASITAS GIDANG MENCAPAI LIMIT!!!");
            }else{
              stockMode = true;
              jPanel1.setLocation(180, 0);
              double totalPrice = priceItem * Integer.parseInt(text);
              FieldTotalHarga.setText(util.formatRupiah(totalPrice));
            }
          }else if (text.isEmpty()) {
            FieldTotalHarga.setText("");
          }else{
            Notifications.getInstance().show(Notifications.Type.INFO,Notifications.Location.BOTTOM_LEFT, "STOCK MASUK SALAH!!!");
          }
          
        } catch (SQLException ex) {
          System.out.println(ex);
          Logger.getLogger(Form_2.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });
  }
//  SET TOTAL PRICE END
  
//  CHECK VALUE NUMERIC START
  public static boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    str = str.trim();
    try {
      Double.valueOf(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
//  CHECK VALUE NUMERIC END
  
  
//    INIT COMBOBOX START
  public void initCombobox() throws SQLException{
    List<ModelItems> modelItems = (List<ModelItems>) serviceData.getAllItems();
    for (ModelItems s : modelItems) {
      String itemsName = s.getItemName();
//      String itemsCode = s.getKodeBarang();
boolean alreadyExists = false;

// Periksa apakah item sudah ada di JComboBox
for (int i = 0; i < JCombobox.getItemCount(); i++) {
  if (JCombobox.getItemAt(i).equals(itemsName)) {
    alreadyExists = true;
    break;
  }
}

if (!alreadyExists) {
  JCombobox.addItem(itemsName);
}

    }
  }
//    INIT COMBOBOX END
  
  
  
  
  
//  GET ITEM ID BY NAME START
  public Item getItemByName(String name){
    
    try {
      DatabaseConnection dbConnection = DatabaseConnection.getInstance();
      dbConnection.connectToDatabase();
      con = dbConnection.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException("tidak terkoneksi ke database");
    }
    
    
    PreparedStatement p;
    int item_id;
    int stock;
    double itemPrice;
    Item item = null;
    String sql = "SELECT item_id, price, quantity FROM items WHERE item_name = ? ";
    try {
      p = con.prepareStatement(sql);
      p.setString(1, name);
      
      ResultSet r = p.executeQuery();
      
      if (r.next()) {
        item_id = r.getInt("item_id");
        stock = r.getInt("quantity");
        itemPrice = r.getDouble("price");
        
        item = new Item(item_id, itemPrice, stock);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return item;
  }
//  GET ITEM ID BY NAME END
  
  
//  MODEL ITEM START
  class Item {
    private final int itemId;
    private final int stock;
    private final double itemPrice;
    
    public Item(int itemId, double itemPrice, int stock) {
      this.itemId = itemId;
      this.itemPrice = itemPrice;
      this.stock = stock;
    }
    
    public int getItemId() {
      return itemId;
    }
    public int getItemStock() {
      return stock;
    }
    
    public double getItemPrice() {
      return itemPrice;
    }
  }
//  MODEL ITEM END
  
  
  
  
  
  
//    INIT TABLE START
  public void initTabel() throws SQLException{
    DefaultTableModel model =(DefaultTableModel)TBLTransaction.getModel();
    model.setRowCount(0);
    List<ModelBarangMasuk> itemsList = (List<ModelBarangMasuk>) serviceData.getBarangMasuk(user, "in");
    
    for(ModelBarangMasuk s : itemsList ) {
      
      Object[] rowData = {s.getKodeItem(), s.getItemName(), s.getCategoryName(), s.getKodeTransaction(),util.formatRupiah(s.getTotalPrice()), s.getItemQuantity(), s.getTransactionDate(), s.getNote()};
      model.addRow(rowData);
    }
    
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    TBLTransaction.setRowSorter(sorter);
    
    FieldSearch.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        search(sorter);
      }
      
      @Override
      public void removeUpdate(DocumentEvent e) {
        search(sorter);
      }
      
      @Override
      public void changedUpdate(DocumentEvent e) {
        search(sorter);
      }
      
      public void search(TableRowSorter<DefaultTableModel> sorter) {
        String text = FieldSearch.getText();
        if (text.trim().length() == 0) {
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
      }
    });
  }
//    INIT TABLE END
  
  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLayeredPane1 = new javax.swing.JLayeredPane();
    JCombobox = new javax.swing.JComboBox<>();
    FieldStock = new com.inventory.swing.MyTextField();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    FieldTotalHarga = new com.inventory.swing.MyTextField();
    FieldHarga = new com.inventory.swing.MyTextField();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    FieldNote = new com.inventory.swing.MyTextField();
    jLabel6 = new javax.swing.JLabel();
    jLayeredPane2 = new javax.swing.JLayeredPane();
    jScrollPane1 = new javax.swing.JScrollPane();
    TBLTransaction = new javax.swing.JTable();
    jLayeredPane3 = new javax.swing.JLayeredPane();
    jPanel1 = new javax.swing.JPanel();
    BTNAddBarang = new com.inventory.swing.ButtonOutLine();
    FieldSearch = new com.inventory.swing.MyTextField();

    setBackground(new java.awt.Color(255, 255, 255));
    setForeground(new java.awt.Color(255, 255, 255));
    setPreferredSize(new java.awt.Dimension(887, 645));

    jLayeredPane1.setOpaque(true);

    JCombobox.setBackground(new Color(30, 149, 87, 30));
    JCombobox.setBorder(null);
    JCombobox.setPreferredSize(new java.awt.Dimension(64, 38));
    JCombobox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JComboboxActionPerformed(evt);
      }
    });

    jLabel2.setBackground(new java.awt.Color(255, 255, 255));
    jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 0, 0));
    jLabel2.setText("Keterangan:");

    jLabel3.setBackground(new java.awt.Color(255, 255, 255));
    jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 0, 0));
    jLabel3.setText("Total Harga :");

    FieldTotalHarga.setEnabled(false);
    FieldTotalHarga.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FieldTotalHargaActionPerformed(evt);
      }
    });

    FieldHarga.setEnabled(false);

    jLabel4.setBackground(new java.awt.Color(255, 255, 255));
    jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(0, 0, 0));
    jLabel4.setText("Stock Masuk:");

    jLabel5.setBackground(new java.awt.Color(255, 255, 255));
    jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 0, 0));
    jLabel5.setText("Harga :");

    jLabel6.setBackground(new java.awt.Color(255, 255, 255));
    jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(0, 0, 0));
    jLabel6.setText("ID Barang :");

    jLayeredPane1.setLayer(JCombobox, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(FieldStock, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(FieldTotalHarga, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(FieldHarga, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(FieldNote, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
    jLayeredPane1.setLayout(jLayeredPane1Layout);
    jLayeredPane1Layout.setHorizontalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
        .addGap(39, 39, 39)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(jLayeredPane1Layout.createSequentialGroup()
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(JCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
            .addGap(18, 18, 18)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(FieldNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(FieldStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addGroup(jLayeredPane1Layout.createSequentialGroup()
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(FieldHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
              .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(65, 65, 65)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(FieldTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jLayeredPane1Layout.setVerticalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane1Layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel4)
          .addComponent(jLabel2)
          .addComponent(jLabel6))
        .addGap(6, 6, 6)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(JCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(FieldStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(FieldNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(jLabel5))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(FieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(FieldTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20))
    );

    TBLTransaction.setBackground(new Color(30, 149, 87, 30));
    TBLTransaction.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    TBLTransaction.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null}
      },
      new String [] {
        "Kode Barang", "Nama ", "Kategory", "Kode Transaksi", "Total Harga", "Stok", "Tanggal", "Keterangan"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    TBLTransaction.setRowHeight(30);
    TBLTransaction.setSelectionBackground(new java.awt.Color(0, 204, 102));
    jScrollPane1.setViewportView(TBLTransaction);
    if (TBLTransaction.getColumnModel().getColumnCount() > 0) {
      TBLTransaction.getColumnModel().getColumn(0).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(0).setPreferredWidth(30);
      TBLTransaction.getColumnModel().getColumn(1).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(2).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(2).setPreferredWidth(20);
      TBLTransaction.getColumnModel().getColumn(3).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(3).setPreferredWidth(50);
      TBLTransaction.getColumnModel().getColumn(4).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(5).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(5).setPreferredWidth(2);
      TBLTransaction.getColumnModel().getColumn(6).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(6).setPreferredWidth(30);
      TBLTransaction.getColumnModel().getColumn(7).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(7).setPreferredWidth(250);
    }

    jLayeredPane2.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
    jLayeredPane2.setLayout(jLayeredPane2Layout);
    jLayeredPane2Layout.setHorizontalGroup(
      jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
        .addContainerGap(30, Short.MAX_VALUE)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(30, 30, 30))
    );
    jLayeredPane2Layout.setVerticalGroup(
      jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(63, 63, 63))
    );

    jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanel1.setBackground(new Color(0,0,0,0)
    );
    jPanel1.setDoubleBuffered(false);
    jPanel1.setEnabled(false);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 110, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 50, Short.MAX_VALUE)
    );

    jLayeredPane3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 110, 50));

    BTNAddBarang.setBackground(new java.awt.Color(0, 255, 153));
    BTNAddBarang.setForeground(new java.awt.Color(0, 0, 0));
    BTNAddBarang.setText("Simpan");
    BTNAddBarang.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNAddBarangActionPerformed(evt);
      }
    });
    jLayeredPane3.add(BTNAddBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 2, 100, 46));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap(17, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(jLayeredPane2)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30, 30, 30))
          .addComponent(jLayeredPane1))
        .addGap(20, 20, 20))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(30, 30, 30)
            .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

      private void JComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboboxActionPerformed
        // TODO add your handling code here:
      }//GEN-LAST:event_JComboboxActionPerformed

      private void BTNAddBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNAddBarangActionPerformed
        int idItem;
        double priceItem = getItemByName(JCombobox.getSelectedItem().toString()).getItemPrice();
        double totalPrice = priceItem * Integer.parseInt(FieldStock.getText());
        idItem = getItemByName(JCombobox.getSelectedItem().toString()).getItemId();
        int stock = getItemByName(JCombobox.getSelectedItem().toString()).getItemStock();
        try {
          if (serviceData.getPersentase() >= 1) {
            boolean status = serviceData.setTransactionByUserId(user.getUserID(), idItem, Integer.parseInt(FieldStock.getText()), "in", FieldNote.getText(), totalPrice, Util.generateCode(JCombobox.getSelectedItem().toString()));
            if (status)  {
              Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT, "TRANSAKSI BERHASIL");
              initTabel();
            }else{
              Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.BOTTOM_LEFT, "TRANSAKSI GAGAL!!!");
            }
          }else{
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.BOTTOM_LEFT, "GUDANG SUDAH MENCAPAIN LIMIT!!!");
            
          }
        } catch (SQLException ex) {
          Logger.getLogger(Form_2.class.getName()).log(Level.SEVERE, null, ex);
        }
      }//GEN-LAST:event_BTNAddBarangActionPerformed

  private void FieldTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldTotalHargaActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_FieldTotalHargaActionPerformed
  

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.inventory.swing.ButtonOutLine BTNAddBarang;
  private com.inventory.swing.MyTextField FieldHarga;
  private com.inventory.swing.MyTextField FieldNote;
  private com.inventory.swing.MyTextField FieldSearch;
  private com.inventory.swing.MyTextField FieldStock;
  private com.inventory.swing.MyTextField FieldTotalHarga;
  private javax.swing.JComboBox<String> JCombobox;
  private javax.swing.JTable TBLTransaction;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLayeredPane jLayeredPane1;
  private javax.swing.JLayeredPane jLayeredPane2;
  private javax.swing.JLayeredPane jLayeredPane3;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
}
