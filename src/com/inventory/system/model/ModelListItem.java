
package com.inventory.system.model;

/**
 *
 * @author iqbal
 */
public class ModelListItem {
    private final String categoryName;
    private final int categoryID;

    public ModelListItem(String categoryName, int categoryID) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    @Override
    public String toString() {
        return categoryName; // This will display the category name in the JComboBox
    }
}

