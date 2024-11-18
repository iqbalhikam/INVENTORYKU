package com.inventory.system.model;

import javax.swing.Icon;

public class Model_Card {

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
    
    public String getValues1() {
        return values1;
    }

    public void setValues1(String values1) {
        this.values1 = values1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Model_Card(Icon icon, String title, String values, String description) {
        this.icon = icon;
        this.title = title;
        this.values = values;
        this.description = description;
    }
    public Model_Card(Icon icon, String title, String values,String values1, String description) {
        this.icon = icon;
        this.title = title;
        this.values = values;
        this.values1 = values1;
        this.description = description;
    }

    public Model_Card() {
    }

    private Icon icon;
    private String title;
    private String values;
    private String values1;
    private String description;
}
