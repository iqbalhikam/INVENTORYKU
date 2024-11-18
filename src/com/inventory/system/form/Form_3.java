
package com.inventory.system.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.inventory.connection.DatabaseConnection;
import com.inventory.model.ModelUser;
import com.inventory.system.model.ModelBarangMasuk;
import com.inventory.system.model.ModelItems;
import com.inventory.system.service.ServiceData;
import com.inventory.system.util.Util;
import java.awt.Color;
import java.awt.Point;
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

/**
 *
 * @author iqbal
 */
public final class Form_3 extends javax.swing.JPanel {
  
  ServiceData serviceData = new ServiceData();
  ModelUser user = new ModelUser();
  private Connection con;
  private Point defaultLocation;
  private boolean stockMode = false;
  Util util = new Util();
  public Form_3(ModelUser user) throws SQLException {
    this.user = user;
    initComponents();
    initCombobox();
    initTabel();
    setTotalPrice();
    util.setColorSelectedJcombobox(JCombobox);
    FieldHarga.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Harga Barang");
    FieldTotalHarga.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Total Harga");
    FieldNote.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Keterangan...");
    FieldStock1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Stok Keluar...");
    FieldSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
  }
  
  
//  SET BTN LOCATION START
  private void addCustomMouseListeners() {
    MouseAdapter mouseListener = new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        if (stockMode) {
          util.animateButtonTo(BTNAddBarang, 0,0); // Panggil animasi saat mouse masuk (mode stock)
        } else {
          util.animateButtonTo(BTNAddBarang, 160, 0); // Panggil animasi saat mouse masuk (mode normal)
        }
      }
      
      @Override
      public void mouseExited(MouseEvent e) {
        util.animateButtonTo(BTNAddBarang, 0,0);
      }
    };
    jPanel1.addMouseListener(mouseListener);
  }
//  SET BTN LOCATION END
  
  
  
//  SET TOTAL PRICE START
  private void setTotalPrice() {
    FieldStock1.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        String selectedItem = JCombobox.getSelectedItem().toString();
        String text = FieldStock1.getText();
        
        if (isNumeric(text)) {
          int inputQuantity = Integer.parseInt(text);
          
          Item item = getItemByName(selectedItem);
          double priceItem = item.getItemPrice();
          int currentStock = item.getItemStock();
          
          
          int totalQuantity = currentStock - inputQuantity;
          
          if (totalQuantity < 0) {
            jPanel1.setVisible(true);
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.BOTTOM_LEFT, "STOCK TIDAK CUKUP!!!");
            stockMode = false;
            jPanel1.setLocation(0,0);
            FieldTotalHarga.setText("");
          } else {
            stockMode = true;
            jPanel1.setLocation(160, 0);
            
            double totalPriceOut = inputQuantity * priceItem;
            
            FieldTotalHarga.setText(util.formatRupiah(totalPriceOut));
            FieldHarga.setText(util.formatRupiah(priceItem));
          }
        } else if (text.isEmpty()) {
          stockMode = false;
          jPanel1.setLocation(0,0);
          
          
          FieldTotalHarga.setText("");
        } else {
          stockMode = false;
          jPanel1.setLocation(0,0);
          
          
          Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.BOTTOM_LEFT, "STOCK MASUK SALAH!!!");
        }
        addCustomMouseListeners();
      }
    });
  }
//  SET TOTAL PRICE END
  
  
  
//  CHECK VALUE NUMERIC START
  public static boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
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
  
  
  
//  GET ITEM BY NAME START
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
    List<ModelBarangMasuk> itemsList = (List<ModelBarangMasuk>) serviceData.getBarangMasuk(user, "out");
    
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

    btnAddPanel = new javax.swing.JLayeredPane();
    JCombobox = new javax.swing.JComboBox<>();
    FieldNote = new com.inventory.swing.MyTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    TBLTransaction = new javax.swing.JTable();
    BTNUpdate = new com.inventory.swing.ButtonOutLine();
    BTNDelete = new com.inventory.swing.ButtonOutLine();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    FieldTotalHarga = new com.inventory.swing.MyTextField();
    FieldHarga = new com.inventory.swing.MyTextField();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    FieldStock1 = new com.inventory.swing.MyTextField();
    jLabel6 = new javax.swing.JLabel();
    FieldSearch = new com.inventory.swing.MyTextField();
    jLayeredPane1 = new javax.swing.JLayeredPane();
    jPanel1 = new javax.swing.JPanel();
    BTNAddBarang = new com.inventory.swing.ButtonOutLine();

    setBackground(new java.awt.Color(255, 255, 255));

    btnAddPanel.setOpaque(true);

    JCombobox.setBackground(new Color(30, 149, 87, 30));
    JCombobox.setBorder(null);
    JCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    JCombobox.setPreferredSize(new java.awt.Dimension(64, 38));
    JCombobox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JComboboxActionPerformed(evt);
      }
    });

    FieldNote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FieldNoteActionPerformed(evt);
      }
    });

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
        "Kode Barang", "Nama", "Kategori", "Kode Transaksi", "Total Harga", "Stok", "Tanggal", "Keterangan"
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
      TBLTransaction.getColumnModel().getColumn(2).setPreferredWidth(30);
      TBLTransaction.getColumnModel().getColumn(3).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(3).setPreferredWidth(30);
      TBLTransaction.getColumnModel().getColumn(4).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(5).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(5).setPreferredWidth(5);
      TBLTransaction.getColumnModel().getColumn(6).setResizable(false);
      TBLTransaction.getColumnModel().getColumn(7).setResizable(false);
    }

    BTNUpdate.setBackground(new java.awt.Color(0, 255, 153));
    BTNUpdate.setForeground(new java.awt.Color(0, 0, 0));
    BTNUpdate.setText("Perbarui");
    BTNUpdate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNUpdateActionPerformed(evt);
      }
    });

    BTNDelete.setBackground(new java.awt.Color(0, 255, 153));
    BTNDelete.setForeground(new java.awt.Color(0, 0, 0));
    BTNDelete.setText("Hapus");
    BTNDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNDeleteActionPerformed(evt);
      }
    });

    jLabel2.setBackground(new java.awt.Color(255, 255, 255));
    jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 0, 0));
    jLabel2.setText("ID Barang :");

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
    jLabel4.setText("Keterangan:");

    jLabel5.setBackground(new java.awt.Color(255, 255, 255));
    jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 0, 0));
    jLabel5.setText("Harga Barang :");

    FieldStock1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FieldStock1ActionPerformed(evt);
      }
    });

    jLabel6.setBackground(new java.awt.Color(255, 255, 255));
    jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(0, 0, 0));
    jLabel6.setText("Stock Keluar:");

    jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanel1.setBackground(new Color(0,0,0,0));
    jPanel1.setDoubleBuffered(false);
    jPanel1.setEnabled(false);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );

    jLayeredPane1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, 50));

    BTNAddBarang.setBackground(new java.awt.Color(0, 255, 153));
    BTNAddBarang.setForeground(new java.awt.Color(0, 0, 0));
    BTNAddBarang.setText("Simpan");
    BTNAddBarang.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        BTNAddBarangMouseEntered(evt);
      }
    });
    BTNAddBarang.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNAddBarangActionPerformed(evt);
      }
    });
    jLayeredPane1.add(BTNAddBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 46));

    btnAddPanel.setLayer(JCombobox, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(FieldNote, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(BTNUpdate, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(BTNDelete, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(FieldTotalHarga, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(FieldHarga, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(FieldStock1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(FieldSearch, javax.swing.JLayeredPane.DEFAULT_LAYER);
    btnAddPanel.setLayer(jLayeredPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout btnAddPanelLayout = new javax.swing.GroupLayout(btnAddPanel);
    btnAddPanel.setLayout(btnAddPanelLayout);
    btnAddPanelLayout.setHorizontalGroup(
      btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(btnAddPanelLayout.createSequentialGroup()
        .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(btnAddPanelLayout.createSequentialGroup()
            .addGap(43, 43, 43)
            .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(btnAddPanelLayout.createSequentialGroup()
                .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(JCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(FieldNote, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(FieldStock1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAddPanelLayout.createSequentialGroup()
                .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(FieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(FieldTotalHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAddPanelLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(BTNUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BTNDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(43, 43, 43))
          .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAddPanelLayout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    btnAddPanelLayout.setVerticalGroup(
      btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(btnAddPanelLayout.createSequentialGroup()
        .addGap(6, 6, 6)
        .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel2)
          .addGroup(btnAddPanelLayout.createSequentialGroup()
            .addGap(4, 4, 4)
            .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel4)
              .addComponent(jLabel6))))
        .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(JCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(FieldNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(FieldStock1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addGap(35, 35, 35)
        .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(jLabel3))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(FieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(FieldTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(23, 23, 23)
        .addGroup(btnAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(BTNDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
          .addComponent(BTNUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(4, 4, 4)
        .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 6, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(btnAddPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(20, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(btnAddPanel)
        .addGap(20, 20, 20))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void JComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboboxActionPerformed
// TODO add your handling code here:
  }//GEN-LAST:event_JComboboxActionPerformed

  private void BTNAddBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNAddBarangActionPerformed
    
    int idItem;
    double priceItem = getItemByName(JCombobox.getSelectedItem().toString()).getItemPrice();
    double totalPrice = priceItem * Integer.parseInt(FieldStock1.getText());
    idItem = getItemByName(JCombobox.getSelectedItem().toString()).getItemId();
    try {
      boolean status = serviceData.setTransactionByUserId(user.getUserID(), idItem, Integer.parseInt(FieldStock1.getText()), "out", FieldNote.getText(), totalPrice, Util.generateCode(JCombobox.getSelectedItem().toString()));
      if (status) {
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT, "TRANSAKSI BERHASIL");
        initTabel();
      }else{
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.BOTTOM_LEFT, "TRANSAKSI GAGAL!!!");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Form_2.class.getName()).log(Level.SEVERE, null, ex);
    }
  }//GEN-LAST:event_BTNAddBarangActionPerformed

  private void BTNDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNDeleteActionPerformed
    System.out.println("LKASI : " + defaultLocation);
  }//GEN-LAST:event_BTNDeleteActionPerformed

  private void BTNAddBarangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNAddBarangMouseEntered
// TODO add your handling code here:
  }//GEN-LAST:event_BTNAddBarangMouseEntered

  private void BTNUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNUpdateActionPerformed
    System.out.println(getItemByName(JCombobox.getSelectedItem().toString()).getItemPrice());
  }//GEN-LAST:event_BTNUpdateActionPerformed

  private void FieldNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldNoteActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_FieldNoteActionPerformed

  private void FieldTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldTotalHargaActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_FieldTotalHargaActionPerformed

  private void FieldStock1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldStock1ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_FieldStock1ActionPerformed
  

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.inventory.swing.ButtonOutLine BTNAddBarang;
  private com.inventory.swing.ButtonOutLine BTNDelete;
  private com.inventory.swing.ButtonOutLine BTNUpdate;
  private com.inventory.swing.MyTextField FieldHarga;
  private com.inventory.swing.MyTextField FieldNote;
  private com.inventory.swing.MyTextField FieldSearch;
  private com.inventory.swing.MyTextField FieldStock1;
  private com.inventory.swing.MyTextField FieldTotalHarga;
  private javax.swing.JComboBox<String> JCombobox;
  private javax.swing.JTable TBLTransaction;
  private javax.swing.JLayeredPane btnAddPanel;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLayeredPane jLayeredPane1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
}
