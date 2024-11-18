
package com.inventory.system.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.inventory.model.ModelUser;
import com.inventory.system.model.ModelBarangMasuk;
import com.inventory.system.model.ModelItems;
import com.inventory.system.model.Model_Card;
import com.inventory.system.service.ServiceData;
import com.inventory.system.util.Util;
import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author iqbal
 */
public final class FormHome extends javax.swing.JPanel {
  private final ServiceData serviceData = new ServiceData();
  private String Card1;
  private String Card2;
  private String Card3;
  private String Card_1;
  private String Card_2;
  private String Card_3;
  private ModelUser user;
  
  
  
  public FormHome(ModelUser user) throws SQLException {
    initComponents();
    this.user = user;
    initTabel();
    FieldSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
  }
  
  
  
//    SET ROW TABLE START
  public void initTabel() throws SQLException{
    DefaultTableModel model =(DefaultTableModel)TBItems.getModel();
    model.setRowCount(0);
    List<ModelItems> itemsList = (List<ModelItems>) serviceData.getAllItems();
    
    for(ModelItems s : itemsList ) {
      if (s.getQuntity() != 0) {
        
        Object[] rowData = {s.getItemName(), s.getKodeBarang(), s.getQuntity(), s.getCreatedAt(), s.getUpdatedAt(), s.getCategoryName()};
        model.addRow(rowData);
      }
      
      TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    TBItems.setRowSorter(sorter);
    
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
  }
//    SET ROW TABLE END
  
  
  
//    SET VALUE CART START
  public void setDataP(float Card1, int Card_1, float Card2, int Card_2, float Card3, int Card_3) throws SQLException{
    
    
    if (Card1 == 0 && Card2 == 0 && Card3 == 0) {
      this.Card1 = "null";
      this.Card2 = "null";
      this.Card3 = "null";
      this.Card_1 = "null";
      this.Card_2 = "null";
      this.Card_3 = "null";
    }
    else{
      this.Card1 = String.valueOf(Card1) + "%";
      this.Card2 = String.valueOf(Card2) + "%";
      this.Card3 = String.valueOf(Card3) + "%";
      this.Card_1 = "Total: " + String.valueOf(Card_1);
      this.Card_2 = "Total: " + String.valueOf(Card_2);
      this.Card_3 = "Total: " + String.valueOf(Card_3);
      
    }
    card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/inventory/system/icon/1.png")), "Total barang", this.Card_1, this.Card1));
    card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/inventory/system/icon/1.png")),"Barang Masuk",this.Card_2, this.Card2));
    card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/inventory/system/icon/1.png")),"Barang Kluar", this.Card_3,this.Card3));
  }
//    SET VALUE CART END
  
  
  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLayeredPane1 = new javax.swing.JLayeredPane();
    card1 = new com.inventory.system.componets.Card();
    card2 = new com.inventory.system.componets.Card();
    card3 = new com.inventory.system.componets.Card();
    jScrollPane1 = new javax.swing.JScrollPane();
    TBItems = new javax.swing.JTable();
    FieldSearch = new com.inventory.swing.MyTextField();

    setBackground(new java.awt.Color(255, 255, 255));
    setForeground(new java.awt.Color(102, 102, 102));

    jLayeredPane1.setPreferredSize(new java.awt.Dimension(887, 645));

    card1.setColor1(new java.awt.Color(0, 153, 102));
    card1.setColor2(new java.awt.Color(0, 204, 102));

    card2.setColor1(new java.awt.Color(0, 153, 102));
    card2.setColor2(new java.awt.Color(0, 204, 102));

    card3.setColor1(new java.awt.Color(0, 153, 102));
    card3.setColor2(new java.awt.Color(0, 204, 102));

    TBItems.setBackground(new Color(30, 149, 87, 30));
    TBItems.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    TBItems.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null, null, null},
        {null, null, null, null, null, null},
        {null, null, null, null, null, null},
        {null, null, null, null, null, null}
      },
      new String [] {
        "Nama Barang", "Kode Barang", "Stock", "Di buat", "Di perbarui", "kategori"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    TBItems.setRowHeight(30);
    TBItems.setSelectionBackground(new java.awt.Color(0, 204, 102));
    jScrollPane1.setViewportView(TBItems);
    if (TBItems.getColumnModel().getColumnCount() > 0) {
      TBItems.getColumnModel().getColumn(0).setResizable(false);
      TBItems.getColumnModel().getColumn(1).setResizable(false);
      TBItems.getColumnModel().getColumn(2).setResizable(false);
      TBItems.getColumnModel().getColumn(2).setPreferredWidth(20);
      TBItems.getColumnModel().getColumn(3).setResizable(false);
      TBItems.getColumnModel().getColumn(4).setResizable(false);
      TBItems.getColumnModel().getColumn(5).setResizable(false);
    }

    jLayeredPane1.setLayer(card1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(card2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(card3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(FieldSearch, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
    jLayeredPane1.setLayout(jLayeredPane1Layout);
    jLayeredPane1Layout.setHorizontalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane1Layout.createSequentialGroup()
        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(39, 39, 39)
        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
      .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    jLayeredPane1Layout.setVerticalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane1Layout.createSequentialGroup()
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
        .addComponent(FieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(20, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(20, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.inventory.swing.MyTextField FieldSearch;
  private javax.swing.JTable TBItems;
  private com.inventory.system.componets.Card card1;
  private com.inventory.system.componets.Card card2;
  private com.inventory.system.componets.Card card3;
  private javax.swing.JLayeredPane jLayeredPane1;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
}
