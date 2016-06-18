package com.xocors.bot.xpro.client.gui.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by xocor_zg5ru7n on 2016/6/5.
 */
public class AppTableItem {
    private SimpleStringProperty fieldCell = new SimpleStringProperty("");
    private SimpleObjectProperty valueCell = new SimpleObjectProperty();

    public String getFieldCell() {
        return fieldCell.get();
    }

    public SimpleStringProperty fieldCellProperty() {
        return fieldCell;
    }

    public void setFieldCell(String fieldCell) {
        this.fieldCell.set(fieldCell);
    }


    public Object getValueCell() {
        return valueCell.get();
    }

    public SimpleObjectProperty valueCellProperty() {
        return valueCell;
    }

    public void setValueCell(Object valueCell) {
        this.valueCell.set(valueCell);
    }
}