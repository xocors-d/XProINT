package com.xocors.bot.xpro.client.gui.model;

import com.xocors.bot.xpro.client.gui.utility.OptionListGenerator;

/**
 * Created by xocor_zg5ru7n on 2016/6/6.
 */
public class OptionListItem {
    private boolean isSelected;
    private String key;
    private String model;
    private String description;

    public OptionListItem(String key, Boolean isSelected, String model, String description){
        setKey(key);
        setSelected(isSelected);
        setModel(model);
        setDescription(description);

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return OptionListGenerator.getDescByKey(key);
    }
}