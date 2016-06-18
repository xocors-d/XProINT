package com.xocors.bot.xpro.client.gui.component;

import com.xocors.bot.xpro.client.gui.model.AppTableItem;
import com.xocors.bot.xpro.client.gui.utility.OptionListGenerator;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.Format;
import java.util.Base64;

/**
 * Created by xocor_zg5ru7n on 2016/6/3.
 */
public class AppFormattedTableCellFactory<S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private TextAlignment alignment;
    private Format format;
    private ObservableList<AppTableItem> appData;
//    private String systemFieldList;

    public TextAlignment getAlignment() {
        return alignment;
    }
    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
    }
    public Format getFormat() {
        return format;
    }
    public void setFormat(Format format) {
        this.format = format;
    }
    public ObservableList<AppTableItem> getAppData() {
        return appData;
    }
    public void setAppData(ObservableList<AppTableItem> appData) {
        this.appData = appData;
    }
//    public void setSystemFieldList(String systemFieldList){this.systemFieldList = systemFieldList;}
//    public String getSystemFieldList(){return systemFieldList;}


    @Override
    @SuppressWarnings("unchecked")
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            public void updateItem(Object item, boolean empty) {

                if (item == getItem()) {
                    return;
                }
                AppTableItem appItem = null;
                if( getTableRow() != null ) {
                    appItem = (AppTableItem)getTableRow().getItem();
//                    if (appItem!=null){
//                        if(systemFieldList.indexOf(appItem.getFieldCell())!=-1) return;
//                    }
                    //if(appItem!=null) System.out.println(appItem.getFieldCell());
                }

                super.updateItem((T) item, empty);

                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                } else if (item instanceof String) {
                    if(appItem!=null && appItem.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_idTypeFldName)){
                        super.setText(null);
                        super.setGraphic(OptionListGenerator.generateIDTypeList((String) item,appData));
                        return;
                    }



                    if(appItem!=null && appItem.getFieldCell().equalsIgnoreCase("country")){

                        ToggleGroup group = new ToggleGroup();

                        RadioButton rbCN = new RadioButton(OptionListGenerator.getDescByKey(OptionListGenerator.CN));
                        rbCN.setToggleGroup(group);
                        rbCN.setId(OptionListGenerator.CN);
                        rbCN.setSelected(OptionListGenerator.CN.equalsIgnoreCase((String)item));

                        rbCN.setOnAction(event -> {
                            appData.forEach(t->{
                                if(t.getFieldCell().equalsIgnoreCase(((AppTableItem)getTableRow().getItem()).getFieldCell())){
                                    t.setValueCell(rbCN.getId());
                                    System.out.println(event.getEventType().toString()+"=>"+rbCN.getId());
                                }
                            });
                        });

                        RadioButton rbHK = new RadioButton(OptionListGenerator.getDescByKey(OptionListGenerator.HK));
                        rbHK.setToggleGroup(group);
                        rbHK.setId(OptionListGenerator.HK);
                        rbHK.setSelected(OptionListGenerator.HK.equalsIgnoreCase((String)item));
                        rbHK.setOnAction(event -> {
                            appData.forEach(t->{
                                if(t.getFieldCell().equalsIgnoreCase(((AppTableItem)getTableRow().getItem()).getFieldCell())){
                                    t.setValueCell(rbHK.getId());
                                    System.out.println(event.getEventType().toString()+"=>"+rbHK.getId());
                                }
                            });
                        });

//                        AnchorPane rbAcPane = new AnchorPane();
//                        rbAcPane.getChildren().addAll(rbCN, rbHK);

                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().addAll(rbCN, rbHK);
                        super.setText(null);
                        super.setGraphic(hBox);
                        return;
                    }

                    if (item.toString().indexOf("base64,")!=-1) {
                        String[] data = item.toString().split(",");

                        byte[] imgBytes = Base64.getDecoder().decode(data[1].trim().getBytes());
                        InputStream is = new ByteArrayInputStream(imgBytes);
                        Image img = new Image(is);
                        ImageView imgVw = new ImageView(img);
                        super.setText(null);
                        super.setGraphic(imgVw);
                        return;
                    } else {

                        TextField txtField = new TextField((String) item);
                        txtField.setOnAction(event -> {
                            appData.forEach(t->{
                                if(t.getFieldCell().equalsIgnoreCase(((AppTableItem)getTableRow().getItem()).getFieldCell())){
                                    t.setValueCell(txtField.getText());
                                }
                            });
                            System.out.println(event.getEventType().toString()+"=>"+txtField.getText());
                        });

                        if(appItem!=null && appItem.getFieldCell().equalsIgnoreCase("id")){
                            txtField.setDisable(true);
                        }

                        super.setText(null);
                        super.setGraphic(txtField);
                        return;
                    }
                } else if (item instanceof String[]) {
                    if(appItem!=null && appItem.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_prefPhoneFldName)){
                        super.setText(null);
                        super.setGraphic(OptionListGenerator.generateIPhoneList((String[])item, appData));
                        return;
                    }

                    if(appItem!=null && appItem.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_prefTimeSlotFldName)){
                        super.setText(null);
                        super.setGraphic(OptionListGenerator.generateTimeSlotList((String[])item, appData));
                        return;
                    }

                    if(appItem!=null && appItem.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_prefStoreFldName)){
                        super.setText(null);
                        super.setGraphic(OptionListGenerator.generateAppleStoreList((String[])item, appData));
                        return;
                    }
                    super.setText(null);
                    super.setGraphic(null);
                    return;
                } else if (item instanceof Integer) {
                    super.setGraphic(new TextField(((Integer) item).toString()));
                } else if (item instanceof Boolean) {
                    CheckBox cb = new CheckBox();
                    cb.setSelected((Boolean) item);
                    super.setGraphic(cb);
                } else if (item instanceof Node) {
                    super.setText(null);
                    super.setGraphic((Node) item);
                } else {
                    super.setText(item.toString());
                    super.setGraphic(null);
                }
            }
        };
        cell.setTextAlignment(alignment);
        switch (alignment) {
            case CENTER:
                cell.setAlignment(Pos.CENTER);
                break;
            case RIGHT:
                cell.setAlignment(Pos.CENTER_RIGHT);
                break;
            default:
                cell.setAlignment(Pos.CENTER_LEFT);
                break;
        }
        return cell;
    }



}