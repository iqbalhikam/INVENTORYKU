package com.inventory.system.swing;

import com.inventory.system.model.modelMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author iqbal
 */
public class menuItem extends javax.swing.JPanel {
      
      private boolean selected;
      private boolean over;
      
      public menuItem(modelMenu data) {
            initComponents();
            setOpaque(false);
            
            if (data.getType() == modelMenu.MenuType.MENU) {
                  lbIcon.setIcon(data.toIcon());
                  lbName.setText(data.getName());
            }else if (data.getType() == modelMenu.MenuType.TITLE) {
                  lbIcon.setText(data.getName());
                  lbIcon.setFont(new Font("sasserif", 1, 12));
                  lbName.setVisible(false);
            }  else {
                  lbName.setText(" ");
            }
      }
      
      /**
       * @param selected the selected to set
       */
      public void setSelected(boolean selected) {
            this.selected = selected;
            repaint();
      }
      
      public void setOver(boolean over) {
            this.over = over;
            repaint();
      }
      
      @SuppressWarnings("unchecked")
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
      private void initComponents() {

            lbIcon = new javax.swing.JLabel();
            lbName = new javax.swing.JLabel();

            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            lbIcon.setBackground(new java.awt.Color(255, 255, 255));
            lbIcon.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
            lbIcon.setForeground(new java.awt.Color(255, 255, 255));

            lbName.setBackground(new java.awt.Color(255, 255, 255));
            lbName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            lbName.setForeground(new java.awt.Color(255, 255, 255));
            lbName.setText("label nama");
            lbName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbIcon)
                        .addGap(18, 18, 18)
                        .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            );
      }// </editor-fold>//GEN-END:initComponents
      
      @Override
      protected void paintComponent(Graphics g) {
            if (selected || over) {
                  Graphics2D g2 = (Graphics2D)g;
                  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  if (selected) {
                        g2.setColor(new Color(255, 255, 255, 80));
                  }else {
                        g2.setColor(new Color(255, 255, 255, 20));
                  }
                  g2.fillRoundRect(0, 0, getWidth() -20, getHeight(), 5, 5);
            }
            super.paintComponent(g);
      }
      

      // Variables declaration - do not modify//GEN-BEGIN:variables
      private javax.swing.JLabel lbIcon;
      private javax.swing.JLabel lbName;
      // End of variables declaration//GEN-END:variables
}
