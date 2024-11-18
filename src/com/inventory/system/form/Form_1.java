package com.inventory.system.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.inventory.model.ModelUser;
import com.inventory.system.model.ModelCategories;
import com.inventory.system.model.ModelGudang;
import com.inventory.system.model.ModelItems;
import com.inventory.system.service.ServiceData;
import com.inventory.system.util.Util;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
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
public final class Form_1 extends javax.swing.JPanel {
  ServiceData serviceData = new ServiceData();
  private Connection con;
  private final boolean mouseInComboBox = false;
  private final ModelUser user;
  private final Util util;
  private String generateKodeBarang;
  
  
  public Form_1(ModelUser user) throws SQLException {
    initComponents();
    this.util = new Util();
    initTabel();
    initCombobox();
    this.user = user;
    util.setColorSelectedJcombobox(kategoryId);
    int selectedRow = tblBarang.getSelectedRow();
    if (selectedRow == -1) {
      
      initKodeBarang();
    }
    
    namaBarang.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nama Barang...");
    kodeBarang.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Kode Barang...");
    hargaBarang.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Harga Barang...");
    FieldSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
  }
  
  
  
  
  
  
  
//    INIT CODE BARANG START
  public void initKodeBarang(){
    namaBarang.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        kodeBarang.setText("");
      }
      
      @Override
      public void focusLost(FocusEvent e) {
        kodeBarang.setText("BRG/"+Util.generateCode(namaBarang.getText()));
      }
      
    });
  }
//    INIT CODE BARANG END
  
  
//    INIT COMBOBOX START
  private void initCombobox() throws SQLException{
    List<ModelCategories> categoryList = (List<ModelCategories>) serviceData.getCategory();
    for (ModelCategories s : categoryList) {
      String categoryName = s.getCategoryName();
      boolean alreadyExists = false;
      
      // Periksa apakah item sudah ada di JComboBox
      for (int i = 0; i < kategoryId.getItemCount(); i++) {
        if (kategoryId.getItemAt(i).equals(categoryName)) {
          alreadyExists = true;
          break;
        }
      }
      
      if (!alreadyExists) {
        kategoryId.addItem(categoryName);
      }
      
    }
    List<ModelGudang> gudangList = (List<ModelGudang>) serviceData.getAllGudang();
    for (ModelGudang s : gudangList) {
      String gudangName = s.getGudangName();
      boolean alreadyExists = false;
      
      // Periksa apakah item sudah ada di JComboBox
      for (int i = 0; i < gudang.getItemCount(); i++) {
        if (gudang.getItemAt(i).equals(gudangName)) {
          alreadyExists = true;
          break;
        }
      }
      
      if (!alreadyExists) {
        gudang.addItem(gudangName);
      }
      
    }
  }
//    INIT COMBOBOX END
  
  
  
  
  
//    GENERATE CODE BARANG STRAT
  public static int generateIdLog() {
    StringBuilder code = new StringBuilder();
    
    Random random = new Random();
    int randomNumber = random.nextInt(900) + 100; // Menghasilkan angka antara 100 dan 999
    
    // Menambahkan angka acak ke kode
    code.append(randomNumber);
    return Integer.parseInt(code.toString());
  }
//    GENERATE CODE BARANG END
  
  
  
//    SET RUPIAH TO DOUBLE START
  public double setRupiahToDouble(String price) {
    String cleanedPrice = price.replaceAll("(?i)rp", "").replace(" ", "");
    
    cleanedPrice = cleanedPrice.replace(".", "").replace(",", ".");
    
    return Double.parseDouble(cleanedPrice);
  }
//    SET RUPIAH TO DOUBLE END
  
  
//    INIT TABLE START
  public void initTabel() throws SQLException{
    DefaultTableModel model =(DefaultTableModel)tblBarang.getModel();
    model.setRowCount(0);
    List<ModelItems> itemsList = (List<ModelItems>) serviceData.getAllItems();
    
    for(ModelItems s : itemsList ) {
      
      Object[] rowData = {s.getKodeBarang(), s.getItemName(), s.getCategoryName(),util.formatRupiah(s.getPrice()), s.getCreatedAt().substring(0, 16), s.getUpdatedAt().substring(0, 16)};
      model.addRow(rowData);
    }
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    tblBarang.setRowSorter(sorter);
    
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
    kodeBarang = new com.inventory.swing.MyTextField();
    namaBarang = new com.inventory.swing.MyTextField();
    kategoryId = new javax.swing.JComboBox<>();
    hargaBarang = new com.inventory.swing.MyTextField();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    gudang = new javax.swing.JComboBox<>();
    jLabel5 = new javax.swing.JLabel();
    jLayeredPane2 = new javax.swing.JLayeredPane();
    jScrollPane1 = new javax.swing.JScrollPane();
    tblBarang = new javax.swing.JTable();
    jLayeredPane3 = new javax.swing.JLayeredPane();
    BTNUpdate = new com.inventory.swing.ButtonOutLine();
    BTNAddBarang = new com.inventory.swing.ButtonOutLine();
    BTNDelete = new com.inventory.swing.ButtonOutLine();
    FieldSearch = new com.inventory.swing.MyTextField();

    setBackground(new java.awt.Color(255, 255, 255));

    jLayeredPane1.setBackground(new java.awt.Color(153, 153, 153));

    kodeBarang.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        kodeBarangActionPerformed(evt);
      }
    });

    kategoryId.setBackground(new Color(30, 149, 87, 30));
    kategoryId.setForeground(new java.awt.Color(0, 0, 0));
    kategoryId.setBorder(null);
    kategoryId.setLightWeightPopupEnabled(false);
    kategoryId.setOpaque(true);
    kategoryId.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        kategoryIdActionPerformed(evt);
      }
    });

    jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 0, 0));
    jLabel1.setText("Nama Barang :");

    jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 0, 0));
    jLabel2.setText("Kode Barang:");

    jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 0, 0));
    jLabel3.setText("Kategori Barang:");

    jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(0, 0, 0));
    jLabel4.setText("Harga Barang:");

    gudang.setBackground(new Color(30, 149, 87, 30));
    gudang.setForeground(new java.awt.Color(0, 0, 0));
    gudang.setBorder(null);
    gudang.setLightWeightPopupEnabled(false);
    gudang.setOpaque(true);
    gudang.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        gudangActionPerformed(evt);
      }
    });

    jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 0, 0));
    jLabel5.setText("Gudang :");

    jLayeredPane1.setLayer(kodeBarang, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(namaBarang, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(kategoryId, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(hargaBarang, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(gudang, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
    jLayeredPane1.setLayout(jLayeredPane1Layout);
    jLayeredPane1Layout.setHorizontalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane1Layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jLayeredPane1Layout.createSequentialGroup()
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
          .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(namaBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(kodeBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(18, 18, 18)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
          .addGroup(jLayeredPane1Layout.createSequentialGroup()
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(gudang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(kategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addComponent(hargaBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(20, 20, 20))
    );
    jLayeredPane1Layout.setVerticalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane1Layout.createSequentialGroup()
        .addGap(30, 30, 30)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(6, 6, 6)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(kodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(gudang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(hargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(20, Short.MAX_VALUE))
    );

    tblBarang.setBackground(new Color(30, 149, 87, 30));
    tblBarang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    tblBarang.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null, null, null},
        {null, null, null, null, null, null},
        {null, null, null, null, null, null},
        {null, null, null, null, null, null}
      },
      new String [] {
        "Kode ", "Nama ", "Kategori", "Harga", "Di buat", "Di perbarui"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    tblBarang.setOpaque(false);
    tblBarang.setRowHeight(30);
    tblBarang.setSelectionBackground(new java.awt.Color(0, 204, 102));
    jScrollPane1.setViewportView(tblBarang);

    BTNUpdate.setBackground(new java.awt.Color(0, 255, 153));
    BTNUpdate.setForeground(new java.awt.Color(0, 0, 0));
    BTNUpdate.setText("Perbarui");
    BTNUpdate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNUpdateActionPerformed(evt);
      }
    });

    BTNAddBarang.setBackground(new java.awt.Color(0, 255, 153));
    BTNAddBarang.setForeground(new java.awt.Color(0, 0, 0));
    BTNAddBarang.setText("Simpan");
    BTNAddBarang.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNAddBarangActionPerformed(evt);
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

    jLayeredPane3.setLayer(BTNUpdate, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane3.setLayer(BTNAddBarang, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane3.setLayer(BTNDelete, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
    jLayeredPane3.setLayout(jLayeredPane3Layout);
    jLayeredPane3Layout.setHorizontalGroup(
      jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane3Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(BTNAddBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
        .addComponent(BTNUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(90, 90, 90)
        .addComponent(BTNDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20))
    );
    jLayeredPane3Layout.setVerticalGroup(
      jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane3Layout.createSequentialGroup()
        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(BTNDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(BTNUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(BTNAddBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 6, Short.MAX_VALUE))
    );

    FieldSearch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FieldSearchActionPerformed(evt);
      }
    });

    jLayeredPane2.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane2.setLayer(jLayeredPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane2.setLayer(FieldSearch, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
    jLayeredPane2.setLayout(jLayeredPane2Layout);
    jLayeredPane2Layout.setHorizontalGroup(
      jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane2Layout.createSequentialGroup()
        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(jLayeredPane2Layout.createSequentialGroup()
            .addGap(39, 610, Short.MAX_VALUE)
            .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jLayeredPane2Layout.createSequentialGroup()
            .addContainerGap(20, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(20, Short.MAX_VALUE))
      .addGroup(jLayeredPane2Layout.createSequentialGroup()
        .addGap(175, 175, 175)
        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jLayeredPane2Layout.setVerticalGroup(
      jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane2Layout.createSequentialGroup()
        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(1, 1, 1)
        .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(20, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(jLayeredPane2)
          .addComponent(jLayeredPane1))
        .addContainerGap(20, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(20, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

      private void kodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeBarangActionPerformed
        // TODO add your handling code here:
      }//GEN-LAST:event_kodeBarangActionPerformed

      private void kategoryIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoryIdActionPerformed
        // TODO add your handling code here:
      }//GEN-LAST:event_kategoryIdActionPerformed

      private void BTNDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNDeleteActionPerformed
        System.out.println("id audit log = " +generateIdLog());
        int selectedRow = tblBarang.getSelectedRow();
        if (selectedRow == -1) {
          Notifications.getInstance().show(Notifications.Type.INFO,Notifications.Location.BOTTOM_LEFT, "KLIK TABLE UNTUK DI HAPUS!!!");
          return;
        }
        String kode_barang = tblBarang.getValueAt(selectedRow, 0).toString();
        try {
          boolean status = serviceData.deleteItemByCode(kode_barang);
          if (status) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS,Notifications.Location.BOTTOM_LEFT, "DATA BERHASIL DI HAPUS");
            initTabel();
          }else{
            Notifications.getInstance().show(Notifications.Type.ERROR,Notifications.Location.BOTTOM_LEFT, "DATA GAGAL DI HAPUS!");
          }
        } catch (SQLException ex) {
          Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
        }
      }//GEN-LAST:event_BTNDeleteActionPerformed

      private void BTNUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNUpdateActionPerformed
        
        int selectedRow = tblBarang.getSelectedRow();
        if (selectedRow == -1) {
          Notifications.getInstance().show(Notifications.Type.INFO,Notifications.Location.BOTTOM_LEFT, "KLIK TABLE UNTUK DI PERBARUI");
          return;
        }
        
        String kode_barang  = tblBarang.getValueAt(selectedRow, 0).toString();
        String nama_barang  = tblBarang.getValueAt(selectedRow, 1).toString();
        String haarga = tblBarang.getValueAt(selectedRow, 3).toString();
        kodeBarang.setText(kode_barang);
        namaBarang.setText(nama_barang);
        hargaBarang.setText(haarga);

      }//GEN-LAST:event_BTNUpdateActionPerformed

      private void BTNAddBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNAddBarangActionPerformed
        int idCategory = serviceData.getIdCategoryByName(kategoryId.getSelectedItem().toString());
        generateKodeBarang = Util.generateCode(namaBarang.getText());
        int generateRecordId = generateIdLog();
        
        try {
          
          if (idCategory == 0 || namaBarang.getText().isEmpty() || hargaBarang.getText().isEmpty() || generateKodeBarang.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO,Notifications.Location.BOTTOM_LEFT, "JANGAN LUPA DI ISI SEMUA FORM DI ATAS!!");
          }else{
            int selectedRow = tblBarang.getSelectedRow();
            
            if (selectedRow == -1) {
              
              initKodeBarang();
              boolean status = serviceData.setItem(generateRecordId, user, idCategory, serviceData.getIdGudangByName(gudang.getSelectedItem().toString()), namaBarang.getText(), Double.parseDouble(hargaBarang.getText()), generateKodeBarang, 0);
              if (status) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,Notifications.Location.BOTTOM_LEFT, "DATA BARANG BERHASIL DI TAMBAH");
                initTabel();
                namaBarang.setText("");
                this.kodeBarang.setText("");
                hargaBarang.setText("");
              }else{
                Notifications.getInstance().show(Notifications.Type.ERROR,Notifications.Location.BOTTOM_LEFT, "DATA BARANG GAGAL DI TAMBAH!");
              }
            }else{
              String kode_barang  = tblBarang.getValueAt(selectedRow, 0).toString();
              
              this.kodeBarang.setText(kode_barang);
              boolean status = serviceData.setUpdateItem(user,generateRecordId , idCategory,serviceData.getIdGudangByName(gudang.getSelectedItem().toString()), namaBarang.getText(), setRupiahToDouble(hargaBarang.getText()), kode_barang, 0);
              if (status) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,Notifications.Location.BOTTOM_LEFT, "DATA BARANG BERHASIL DI UPDATE");
                initTabel();
                namaBarang.setText("");
                this.kodeBarang.setText("");
                hargaBarang.setText("");
              }else{
                Notifications.getInstance().show(Notifications.Type.ERROR,Notifications.Location.BOTTOM_LEFT, "DATA BARANG GAGAL DI UPDATE!");
              }
              
            }
            
          }
          
        } catch (SQLException ex) {
          Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
        }
      }//GEN-LAST:event_BTNAddBarangActionPerformed

  private void gudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gudangActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_gudangActionPerformed

  private void FieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSearchActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_FieldSearchActionPerformed
  
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.inventory.swing.ButtonOutLine BTNAddBarang;
  private com.inventory.swing.ButtonOutLine BTNDelete;
  private com.inventory.swing.ButtonOutLine BTNUpdate;
  private com.inventory.swing.MyTextField FieldSearch;
  private javax.swing.JComboBox<String> gudang;
  private com.inventory.swing.MyTextField hargaBarang;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLayeredPane jLayeredPane1;
  private javax.swing.JLayeredPane jLayeredPane2;
  private javax.swing.JLayeredPane jLayeredPane3;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JComboBox<String> kategoryId;
  private com.inventory.swing.MyTextField kodeBarang;
  private com.inventory.swing.MyTextField namaBarang;
  private javax.swing.JTable tblBarang;
  // End of variables declaration//GEN-END:variables
}
