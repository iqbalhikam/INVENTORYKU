
package com.inventory.system.swing;

import com.inventory.system.event.EventMenuSelected;
import com.inventory.system.model.modelMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

/**
 *
 * @author iqbal
 * @param <E>
 */
public class listMenu < E extends Object> extends JList<E>{
      
      private final DefaultListModel model;
      private int selectedIndex = -1;
      private int overIndex = -1;
      
      private EventMenuSelected event;
      
      public void addEventMenuSelected(EventMenuSelected event){
            this.event = event;
      }
      
      public listMenu() {
            model = new DefaultListModel();
            setModel(model);
            addMouseListener(new MouseAdapter(){
                  @Override
                  public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                              int index = locationToIndex(e.getPoint());
                              Object o = model.getElementAt(index);
                              if (o instanceof modelMenu) {
                                    modelMenu menu = (modelMenu)o;
                                    if (menu.getType() == modelMenu.MenuType.MENU) {
                                          selectedIndex = index;
                                          System.out.println(index + "  di clik" );
                                          if (event != null) {
                                                event.selected(index);
                                          }
                                    }
                              }else{
                                    selectedIndex = index;
                              }
                              repaint();
                        }
                  }

                  @Override
                  public void mouseExited(MouseEvent e) {
                        overIndex = -1;
                        repaint();
                  }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                  @Override
                  public void mouseMoved(MouseEvent e) {
                        int index = locationToIndex(e.getPoint());
                        if (index != overIndex) {
                              Object o = model.getElementAt(index);
                              if (o instanceof modelMenu) {
                                    modelMenu menu = (modelMenu)o;
                                    if (menu.getType() == modelMenu.MenuType.MENU) {
                                          overIndex = index;
                                    }else{
                                          overIndex = -1;
                                    }
                                    repaint();
                              }
                              
                        }
                  }
                  
            });
      }
      
      @Override
      public ListCellRenderer<? super E> getCellRenderer() {
            return new DefaultListCellRenderer(){
                  @Override
                  public Component getListCellRendererComponent(JList<?> jlist, Object o, int index, boolean selected, boolean focus) {
                        modelMenu data;
                        if (o instanceof modelMenu) {
                              data = (modelMenu) o;
                        }else{
                              data = new modelMenu("",o + "", modelMenu.MenuType.EMPTY);
                        }
                        
                        menuItem item =new menuItem(data);
                        item.setSelected(selectedIndex == index);
                        item.setOver(overIndex == index);
                        return item;
                  }
                  
            };
      }
      public void addItem(modelMenu data) {
            model.addElement(data);
      }
      
      
}
