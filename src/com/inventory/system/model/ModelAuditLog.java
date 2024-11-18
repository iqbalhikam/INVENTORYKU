
package com.inventory.system.model;

/**
 *
 * @author iqbal
 */
public class ModelAuditLog {
      private int log_id;
      private int user_id;
      private String action;
      private String table_name;
      private int record_id;
      private String old_value;
      private String new_value;
      private String action_date;
      
      // Getter and setter for log_id
      public int getLogID() {
            return log_id;
      }
      
      public void setLogID(int log_id) {
            this.log_id = log_id;
      }
      
      // Getter and setter for user_id
      public int getUserID() {
            return user_id;
      }
      
      public void setUserID(int user_id) {
            this.user_id = user_id;
      }
      
      // Getter and setter for action
      public String getAction() {
            return action;
      }
      
      public void setAction(String action) {
            this.action = action;
      }
      
      // Getter and setter for table_name
      public String getTableName() {
            return table_name;
      }
      
      public void setTableName(String table_name) {
            this.table_name = table_name;
      }
      
      // Getter and setter for record_id
      public int getRecordID() {
            return record_id;
      }
      
      public void setRecordID(int record_id) {
            this.record_id = record_id;
      }
      
      // Getter and setter for old_value
      public String getOldValue() {
            return old_value;
      }
      
      public void setOldValue(String old_value) {
            this.old_value = old_value;
      }
      
      // Getter and setter for new_value
      public String getNewValue() {
            return new_value;
      }
      
      public void setNewValue(String new_value) {
            this.new_value = new_value;
      }
      
      // Getter and setter for action_date
      public String getActionDate() {
            return action_date;
      }
      
      public void setActionDate(String action_date) {
            this.action_date = action_date;
      }
      
      // Constructors
      public ModelAuditLog() {
      }
      
      public ModelAuditLog(int log_id, int user_id, String action, String table_name, int record_id, String old_value, String new_value, String action_date) {
            this.log_id = log_id;
            this.user_id = user_id;
            this.action = action;
            this.table_name = table_name;
            this.record_id = record_id;
            this.old_value = old_value;
            this.new_value = new_value;
            this.action_date = action_date;
      }
}
