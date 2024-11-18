package com.inventory.system.componets;

import com.inventory.system.event.EventMenuSelected;
import com.inventory.system.model.modelMenu;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

/**
 *
 * @author iqbal
 */
public class panelMenu extends javax.swing.JPanel {
  
  private EventMenuSelected event;
  
  public void addEventMenuSelected(EventMenuSelected event) {
    this.event = event;
    listMenu1.addEventMenuSelected(event);
  }
  
  public panelMenu() {
    initComponents();
    setOpaque(false);
    listMenu1.setOpaque(false);
    jlogo.setOpaque(false);
    init();
  }
  
  private void init(){
    listMenu1.addItem(new modelMenu("1", "Dashboard", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu("2", "Data Barang", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu("3", "Barang Masuk", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu("4", "Barang Keluar", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu("5", "Informasi Barang", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu("", " ", modelMenu.MenuType.EMPTY));
    
    listMenu1.addItem(new modelMenu("", "My Data", modelMenu.MenuType.TITLE));
    listMenu1.addItem(new modelMenu(" ", " ", modelMenu.MenuType.EMPTY));
    listMenu1.addItem(new modelMenu("6", "Riwayat", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu(" ", " ", modelMenu.MenuType.EMPTY));
    listMenu1.addItem(new modelMenu("10", "Keluar", modelMenu.MenuType.MENU));
    listMenu1.addItem(new modelMenu("", "", modelMenu.MenuType.EMPTY));
  }
  
  @SuppressWarnings("unchecked")
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
      private void initComponents() {

            panelMoving = new javax.swing.JPanel();
            jlogo = new javax.swing.JLabel();
            listMenu1 = new com.inventory.system.swing.listMenu<>();

            panelMoving.setBackground(new java.awt.Color(255, 255, 255));
            panelMoving.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            panelMoving.setOpaque(false);

            jlogo.setBackground(new java.awt.Color(255, 255, 255));
            jlogo.setFont(new java.awt.Font("ROG Fonts", 1, 20)); // NOI18N
            jlogo.setForeground(new java.awt.Color(255, 255, 255));
            jlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/system/icon/Group 2 (5).png"))); // NOI18N
            jlogo.setText("InventoryKu");
            jlogo.setOpaque(true);

            javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
            panelMoving.setLayout(panelMovingLayout);
            panelMovingLayout.setHorizontalGroup(
                  panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovingLayout.createSequentialGroup()
                        .addGap(0, 17, Short.MAX_VALUE)
                        .addComponent(jlogo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
            );
            panelMovingLayout.setVerticalGroup(
                  panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(panelMovingLayout.createSequentialGroup()
                        .addComponent(jlogo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
            layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(listMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
            );
      }// </editor-fold>//GEN-END:initComponents
      
      @Override
      protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradients = new GradientPaint(0, 0, Color.decode("#209A59"), 0, getHeight(), Color.decode("#0A6847"));
        g2.setPaint(gradients);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth()-20, 0, getWidth(), getHeight());
        super.paintChildren(g);
        
      }
      
      private int x;
      private int y;
      
      public void initMoving(JFrame frame){
        panelMoving.addMouseListener(new MouseAdapter(){
          @Override
          public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
          }
        });
        
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            frame.setLocation(e.getXOnScreen()-x, e.getYOnScreen()-y);
          }
          
        });
        
      }
      

      // Variables declaration - do not modify//GEN-BEGIN:variables
      private javax.swing.JLabel jlogo;
      private com.inventory.system.swing.listMenu<String> listMenu1;
      private javax.swing.JPanel panelMoving;
      // End of variables declaration//GEN-END:variables
      
      public class lbUser {
        
        public lbUser() {
        }
      }
}
