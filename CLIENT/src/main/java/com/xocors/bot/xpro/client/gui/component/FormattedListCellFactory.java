package com.xocors.bot.xpro.client.gui.component;

import com.xocors.bot.xpro.client.gui.model.AppTableItem;
import com.xocors.bot.xpro.client.gui.model.OptionListItem;
import com.xocors.bot.xpro.client.gui.utility.OptionListGenerator;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.commons.lang.ArrayUtils;

/**
 * Created by xocor_zg5ru7n on 2016/6/6.
 */
public class FormattedListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {
    private ObservableList<AppTableItem> appData;

    public ObservableList<AppTableItem> getAppData() {
        return appData;
    }

    public void setAppData(ObservableList<AppTableItem> appData) {
        this.appData = appData;
    }

    @Override
    public ListCell<T> call(ListView<T> listView) {
        ListCell<T> cell = new ListCell<T>() {
            @Override
            public void updateItem(T item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                }else if (item instanceof String) {
//                    if(((String) item).startsWith(OptionListGenerator.APP_idTypeKey)){
//                        super.setText(OptionListGenerator.getDescByKey((String)item));
//                        super.setId(OptionListGenerator.getModelByKey((String)item));
//                        super.setGraphic(null);
//                    }
                }else if (item instanceof OptionListItem) {

                    if(((OptionListItem) item).getKey().startsWith(OptionListGenerator.APP_prefPhoneKey)){
                        CheckBox checkBox = new CheckBox();
                        checkBox.setText(OptionListGenerator.getDescByKey(((OptionListItem) item).getKey()));
                        checkBox.setSelected(((OptionListItem) item).isSelected());
                        checkBox.setId(((OptionListItem) item).getModel());
                        checkBox.setOnAction(event -> {
                            appData.forEach(app ->{
                                if(app.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_prefPhoneFldName)){
                                    String[] result = (String[]) ArrayUtils.removeElement((String[])app.getValueCell(), checkBox.getId());
                                    ((OptionListItem) item).setSelected(false);
                                    if(checkBox.isSelected()){
                                        ((OptionListItem) item).setSelected(true);
                                        result = (String[])ArrayUtils.add((String[])app.getValueCell(), checkBox.getId());
                                    }
                                    app.setValueCell(result);
                                }
                            });
                        });
                        super.setText(null);
                        super.setGraphic(checkBox);
                        return;
                    }

                    if(((OptionListItem) item).getKey().startsWith(OptionListGenerator.APP_prefStoreKey)){
                        CheckBox checkBox = new CheckBox();
                        checkBox.setText(OptionListGenerator.getDescByKey(((OptionListItem) item).getKey()));
                        checkBox.setSelected(((OptionListItem) item).isSelected());
                        checkBox.setId(((OptionListItem) item).getModel());
                        checkBox.setOnAction(event -> {
                            appData.forEach(app ->{
                                if(app.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_prefStoreFldName)){
                                    String[] result = (String[]) ArrayUtils.removeElement((String[])app.getValueCell(), checkBox.getId());
                                    ((OptionListItem) item).setSelected(false);
                                    if(checkBox.isSelected()){
                                        ((OptionListItem) item).setSelected(true);
                                        result = (String[])ArrayUtils.add((String[])app.getValueCell(), checkBox.getId());
                                    }
                                    app.setValueCell(result);
                                }
                            });
                        });
                        super.setText(null);
                        super.setGraphic(checkBox);
                        return;
                    }

                    if(((OptionListItem) item).getKey().startsWith(OptionListGenerator.APP_prefTimeSlotKey)){
//                        CheckBox checkBox = new CheckBox();
//                        checkBox.setText(((OptionListItem) item).getModel());
//                        checkBox.setSelected(((OptionListItem) item).isSelected());
//                        checkBox.setId(((OptionListItem) item).getModel());
//                        checkBox.setOnAction(event -> {
//                            appData.forEach(app ->{
//                                if(app.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_prefTimeSlotFldName)){
//                                    String[] result = (String[]) ArrayUtils.removeElement((String[])app.getValueCell(), checkBox.getId());
//                                    ((OptionListItem) item).setSelected(false);
//                                    if(checkBox.isSelected()){
//                                        ((OptionListItem) item).setSelected(true);
//                                        result = (String[])ArrayUtils.add((String[])app.getValueCell(), checkBox.getId());
//                                    }
//                                    app.setValueCell(result);
//                                }
//                            });
//                        });
//                        super.setText(null);
//                        super.setGraphic(checkBox);
//                        return;
                        CheckBox checkBox = generateCheckBoxForField((OptionListItem) item, OptionListGenerator.APP_prefTimeSlotFldName);
                        super.setText(null);
                        super.setGraphic(checkBox);
                        return;
                    }

                    if(((OptionListItem) item).getKey().startsWith(OptionListGenerator.APP_idTypeKey)){
                        super.setText(OptionListGenerator.getDescByKey(((OptionListItem) item).getKey()));
                        super.setId(((OptionListItem) item).getModel());
                        super.setGraphic(null);
                        return;
                    }

                } else {
                    super.setText(null);
                }
            }
        };
        return cell;
    }

    private CheckBox generateCheckBoxForField(OptionListItem item, String fieldName){
        CheckBox checkBox = new CheckBox();
        checkBox.setText(item.getModel());
        checkBox.setSelected(item.isSelected());
        checkBox.setId(item.getModel());
        checkBox.setOnAction(event -> {
            appData.forEach(app ->{
                if(app.getFieldCell().equalsIgnoreCase(fieldName)){
                    String[] result = (String[]) ArrayUtils.removeElement((String[])app.getValueCell(), checkBox.getId());
                    item.setSelected(false);
                    if(checkBox.isSelected()){
                        item.setSelected(true);
                        result = (String[])ArrayUtils.add((String[])app.getValueCell(), checkBox.getId());
                    }
                    app.setValueCell(result);
                }
            });
        });

        return checkBox;
    }

}