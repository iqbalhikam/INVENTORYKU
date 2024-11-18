
package com.inventory.system.model;

/**
 *
 * @author iqbal
 */
public class ModelCategories {
      private int category_id;
      private String category_name;
      private String description;
      
      // Getter and setter for category_id
      public int getCategoryID() {
            return category_id;
      }
      
      public void setCategoryID(int category_id) {
            this.category_id = category_id;
      }
      
      // Getter and setter for category_name
      public String getCategoryName() {
            return category_name;
      }
      
      public void setCategoryName(String category_name) {
            this.category_name = category_name;
      }
      
      // Getter and setter for description
      public String getDescription() {
            return description;
      }
      
      public void setDescription(String description) {
            this.description = description;
      }
      
      // Constructors
      public ModelCategories() {
      }
      
      public ModelCategories(int category_id, String category_name) {
            this.category_id = category_id;
            this.category_name = category_name;
      }
}
