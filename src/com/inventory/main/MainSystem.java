package com.inventory.main;

import com.inventory.model.ModelUser;
import com.inventory.system.form.FormHome;
import com.inventory.system.form.Form_1;
import com.inventory.system.form.Form_2;
import com.inventory.system.form.Form_3;
import com.inventory.system.form.form_4;
import com.inventory.system.service.ServiceData;
import com.inventory.system.util.Util;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import raven.toast.Notifications;

/**
 *
 * @author iqbal
 */
public final class MainSystem extends javax.swing.JFrame {
  
  private FormHome home;
  private Form_1 form1;
  private Form_2 form2;
  private Form_3 form3;
  private form_4 form4;
  
  private final ModelUser user;
  private final ServiceData serviceData = new ServiceData();
  private Util util;
  private int transaction;
  private int sort = 1;
  
  public MainSystem(ModelUser user) throws SQLException {
    initComponents();
    initMenuSelected(user);
    this.user = user;
    this.util = new Util();
    util.setColorSelectedJcombobox(CBoxBatasan);
    System.out.println(user.getUserID());
    setBackground(new Color(0,0,0,0));
    
    panelMenu1.initMoving(MainSystem.this);
    lbUser.setText(user.getUserName());
    System.out.println("total = " +  serviceData.getTotalItemIsReady());
    home.setDataP(
            serviceData.getPersentase(), //PERSENTASE KAPASITAS GUDANG
            serviceData.getTotal(), //TOTAL STOK DI GUDANG
            serviceData.getPersentaseInOut("in", sort), //PERSENTASE PARANG MASUK 
            serviceData.getTotalInlOut("in", sort), //TOTAL STOK MASUK
            serviceData.getPersentaseInOut("out", sort), //PERSENTASE PARANG MASUK 
            serviceData.getTotalInlOut("out", sort) //STOK BARANG KELUAR
    );
    setLocationRelativeTo(null);
    setForm(home);
    Notifications.getInstance().setJFrame(this);
  }
 
  
//    INIT MENU SELECTED START
  private void initMenuSelected(ModelUser user) throws SQLException{
    
    home = new FormHome(user);
    form1 = new Form_1(user);
    form2 = new Form_2(user);
    form3 = new Form_3(user);
    form4 = new form_4();
    
    panelMenu1.addEventMenuSelected((int index) -> {
      System.out.println("index : " + index);
      switch (index) {
        case 0:
          setForm(home);
          {
            try {
              home.setDataP(
                      serviceData.getPersentase(),
                      serviceData.getTotal(),
                      serviceData.getPersentaseInOut("in", sort),
                      serviceData.getTotalInlOut("in", sort),
                      serviceData.getPersentaseInOut("out", sort),
                      serviceData.getTotalInlOut("out", sort)
              );
              home.initTabel();
            } catch (SQLException ex) {
              Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
          break;
          
        case 1:
          setForm(form1);
          {
            try {
              form1.initTabel();
            } catch (SQLException ex) {
              Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
          break;
          
        case 2:
          setForm(form2);
          {
            try {
              form2.initTabel();
            } catch (SQLException ex) {
              Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
          break;
          
        case 3:
          setForm(form3);
          {
            try {
              form3.initTabel();
            } catch (SQLException ex) {
              Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
          break;
        case 4:
          setForm(form4);
          break;
        case 5:
          break;
        case 6:
          break;
        case 7:
          break;
        case 8:
          break;
        case 9:
          break;
        case 10:
          Main.clearUserIdFromPersistentStorage();
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          System.exit(0);
        default:
          break;
      }
    });
    
  }
//    INIT MENU SELECTED END
  
  
//    SET FORM MENU START
  private void setForm(JComponent com) {
    mainPanel.removeAll();
    mainPanel.add(com);
    mainPanel.repaint();
    mainPanel.revalidate();
  }
//    SET FORM MENU END
  
  
  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
    buttonGroup = new javax.swing.ButtonGroup();
    panelBorder1 = new com.inventory.swing.panelBorder();
    layer1 = new javax.swing.JLayeredPane();
    jLayeredPane1 = new javax.swing.JLayeredPane();
    BTNClose = new com.inventory.swing.Button();
    BTNMinimaze = new com.inventory.swing.Button();
    mainPanel = new javax.swing.JPanel();
    panelMenu1 = new com.inventory.system.componets.panelMenu();
    lbUser = new javax.swing.JLabel();
    jLayeredPane2 = new javax.swing.JLayeredPane();
    jLabel1 = new javax.swing.JLabel();
    CBoxBatasan = new javax.swing.JComboBox<>();
    jLabel2 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setUndecorated(true);

    panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

    BTNClose.setForeground(new java.awt.Color(255, 255, 255));
    BTNClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/system/icon/icons8-cancel-20.png"))); // NOI18N
    BTNClose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    BTNClose.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        BTNCloseMouseEntered(evt);
      }
    });
    BTNClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNCloseActionPerformed(evt);
      }
    });

    BTNMinimaze.setForeground(new java.awt.Color(255, 255, 255));
    BTNMinimaze.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/system/icon/icons8-macos-minimize-20.png"))); // NOI18N
    BTNMinimaze.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    BTNMinimaze.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BTNMinimazeActionPerformed(evt);
      }
    });

    jLayeredPane1.setLayer(BTNClose, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane1.setLayer(BTNMinimaze, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
    jLayeredPane1.setLayout(jLayeredPane1Layout);
    jLayeredPane1Layout.setHorizontalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(BTNMinimaze, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(4, 4, 4)
        .addComponent(BTNClose, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    jLayeredPane1Layout.setVerticalGroup(
      jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jLayeredPane1Layout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(BTNClose, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(BTNMinimaze, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 0, Short.MAX_VALUE))
    );

    mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102)));
    mainPanel.setLayout(new java.awt.BorderLayout());

    lbUser.setBackground(new java.awt.Color(255, 255, 255));
    lbUser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    lbUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lbUser.setText("User Name");

    jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(51, 51, 51));
    jLabel1.setText("Sortir :");

    CBoxBatasan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    CBoxBatasan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
    CBoxBatasan.setBorder(null);
    CBoxBatasan.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        CBoxBatasanActionPerformed(evt);
      }
    });

    jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(51, 51, 51));
    jLabel2.setText("Bulan");

    jLayeredPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane2.setLayer(CBoxBatasan, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jLayeredPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
    jLayeredPane2.setLayout(jLayeredPane2Layout);
    jLayeredPane2Layout.setHorizontalGroup(
      jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(CBoxBatasan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    jLayeredPane2Layout.setVerticalGroup(
      jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(CBoxBatasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel1)
          .addComponent(jLabel2)))
    );

    layer1.setLayer(jLayeredPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    layer1.setLayer(mainPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
    layer1.setLayer(panelMenu1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    layer1.setLayer(lbUser, javax.swing.JLayeredPane.DEFAULT_LAYER);
    layer1.setLayer(jLayeredPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout layer1Layout = new javax.swing.GroupLayout(layer1);
    layer1.setLayout(layer1Layout);
    layer1Layout.setHorizontalGroup(
      layer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layer1Layout.createSequentialGroup()
        .addComponent(panelMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addGroup(layer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layer1Layout.createSequentialGroup()
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 17, Short.MAX_VALUE))
          .addGroup(layer1Layout.createSequentialGroup()
            .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(50, 50, 50)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    layer1Layout.setVerticalGroup(
      layer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layer1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      .addGroup(layer1Layout.createSequentialGroup()
        .addComponent(panelMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
    panelBorder1.setLayout(panelBorder1Layout);
    panelBorder1Layout.setHorizontalGroup(
      panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(layer1)
    );
    panelBorder1Layout.setVerticalGroup(
      panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(layer1)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    pack();
    setLocationRelativeTo(null);
  }// </editor-fold>//GEN-END:initComponents

  private void BTNCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNCloseActionPerformed
    setVisible(false);
    System.exit(0);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }//GEN-LAST:event_BTNCloseActionPerformed

  private void BTNMinimazeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNMinimazeActionPerformed
    this.setState(MainSystem.ICONIFIED);
  }//GEN-LAST:event_BTNMinimazeActionPerformed

  private void BTNCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNCloseMouseEntered
    
  }//GEN-LAST:event_BTNCloseMouseEntered

  private void CBoxBatasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBoxBatasanActionPerformed
    sort = Integer.parseInt(CBoxBatasan.getSelectedItem().toString());
    try {
      home.setDataP(serviceData.getPersentase(),serviceData.getTotal(), serviceData.getPersentaseInOut("in", sort), serviceData.getTotalInlOut("in", sort), serviceData.getPersentaseInOut("out", sort), serviceData.getTotalInlOut("out", sort));
    } catch (SQLException ex) {
      Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
    }
  }//GEN-LAST:event_CBoxBatasanActionPerformed
  
  
  
  public static void main(ModelUser user) {
    java.awt.EventQueue.invokeLater(() -> {
      try {
        new MainSystem(user).setVisible(true);
      } catch (SQLException ex) {
        Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
  }
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.inventory.swing.Button BTNClose;
  private com.inventory.swing.Button BTNMinimaze;
  private javax.swing.JComboBox<String> CBoxBatasan;
  private javax.swing.ButtonGroup buttonGroup;
  private javax.swing.Box.Filler filler1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLayeredPane jLayeredPane1;
  private javax.swing.JLayeredPane jLayeredPane2;
  private javax.swing.JLayeredPane layer1;
  private javax.swing.JLabel lbUser;
  private javax.swing.JPanel mainPanel;
  private com.inventory.swing.panelBorder panelBorder1;
  private com.inventory.system.componets.panelMenu panelMenu1;
  // End of variables declaration//GEN-END:variables
}
