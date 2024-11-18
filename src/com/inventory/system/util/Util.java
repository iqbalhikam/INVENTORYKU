
package com.inventory.system.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.Timer;

/**
 *
 * @author iqbal
 */
public class Util {
  private Connection con;
  
    //    FORMAT MATA UANG START
  public String formatRupiah(double amount) {
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    return formatRupiah.format(amount);
  }
//    FORMAT MATA UANG END
  
  
//    GENERATE CODE BARANG STRAT
  public static String generateCode(String productName) {
    StringBuilder code = new StringBuilder();
    String[] words = productName.split(" ");
    for (String word : words) {
      if (!word.isEmpty()) {
        code.append(Character.toUpperCase(word.charAt(0)));
      }
    }
    Random random = new Random();
    int randomNumber = random.nextInt(900) + 100; // Menghasilkan angka antara 100 dan 999
    
// Menambahkan angka acak ke kode
code.append(randomNumber);
return code.toString();
  }
//    GENERATE CODE BARANG END
  
  
  //  SET COLOR SELECTED JCOMBOBOX START
  public void setColorSelectedJcombobox(JComboBox JCombobox){
    JCombobox.setRenderer(new DefaultListCellRenderer() {
      
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Color transparentGreen = new Color(0, 255, 0, 51);
        if (isSelected) {
          c.setBackground(transparentGreen);
          c.setForeground(Color.BLACK);
        } else {
          c.setBackground(Color.WHITE);
          c.setForeground(Color.BLACK);
        }
        return c;
      }
    });
  }
//  SET COLOR SELECTED JCOMBOBOX END
  
  //  ANIMATION START
  public void animateButtonTo(JButton BTN,int targetX, int targetY) {
    int startX = BTN.getX();
    int startY = BTN.getY();
    int deltaX = targetX - startX;
    int deltaY = targetY - startY;
    int steps = 20;
    int delay = 10; // Delay dalam milidetik
    
    Timer timer = new Timer(delay, new ActionListener() {
      int currentStep = 0;
      
      @Override
      public void actionPerformed(ActionEvent e) {
        currentStep++;
        int newX = startX + (deltaX * currentStep) / steps;
        int newY = startY + (deltaY * currentStep) / steps;
        BTN.setLocation(newX, newY);
        
        if (currentStep >= steps) {
          ((Timer) e.getSource()).stop();
        }
      }
    });
    
    timer.start();
  }
//  ANIMATION END

  
}
