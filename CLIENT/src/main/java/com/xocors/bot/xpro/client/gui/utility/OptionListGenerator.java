package com.xocors.bot.xpro.client.gui.utility;

import com.xocors.bot.xpro.client.gui.component.FormattedListCellFactory;
import com.xocors.bot.xpro.client.gui.model.AppTableItem;
import com.xocors.bot.xpro.client.gui.model.OptionListItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xocor_zg5ru7n on 2016/6/6.
 */
public class OptionListGenerator {

    private static final String TAB = "\t";
    public static final String APP_prefPhoneFldName = "preferredIPhones";
    public static final String APP_prefPhoneKey = "IPHONE";
    public static final String APP_prefTimeSlotFldName = "preferredTimeSlots";
    public static final String APP_prefTimeSlotKey = "TIMESLOT";
    public static final String APP_prefStoreFldName = "preferredStores";
    public static final String APP_prefStoreKey = "STORE";
    public static final String APP_idTypeFldName = "idType";
    public static final String APP_idTypeKey = "ID.TYPE";
    public static final String CN = "CN";
    public static final String HK = "HK";
    public static final String SEPARATOR = ".";


    public static final Map<String, String> DICT;

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put(CN, "China");
        aMap.put(HK, "Hong Kong");
        aMap.put("IP7", "IPhone 7");
        aMap.put("IP7P", "IPhone 7 Plus");
        aMap.put("16", "16 GB");
        aMap.put("64", "64 GB");
        aMap.put("128", "128 GB");
        aMap.put("GOLD", "Gold");
        aMap.put("GREY", "Rrey");
        aMap.put("SILV", "Silver");
        aMap.put("ROSE", "Rose Gold");
        aMap.put("PASSPORT", "Passport");
        aMap.put("HKIDCARD", "Hong Kong ID Card");
        aMap.put("EXITPASS", "China Exit Entry Pass");
        aMap.put("CNIDCARD", "China ID Card");
        aMap.put("10", "10:");
        aMap.put("11", "11:");
        aMap.put("00", "00");
        aMap.put("30", "30");
        aMap.put("12", "12:");
        aMap.put("AM", "AM");
        aMap.put("PM", "PM");
        aMap.put("IFC", "ifc mall");
        aMap.put("CWB", "Causeway Bay");
        aMap.put("FW", "Festival Walk");
        aMap.put("CAN", "Canton Road");

        DICT = Collections.unmodifiableMap(aMap);
    }

    public static final List<OptionListItem> OPTION_LIST = Arrays.asList(
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.128.GOLD", false, "MD_CN.IP7P.128.GOLD", "CN IPhone 7 Plus 128 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.128.GREY", false, "MD_CN.IP7P.128.GREY", "CN IPhone 7 Plus 128 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.128.SILV", false, "MD_CN.IP7P.128.SILV", "CN IPhone 7 Plus 128 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.128.ROSE", false, "MD_CN.IP7P.128.ROSE", "CN IPhone 7 Plus 128 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.64.GOLD", false, "MD_CN.IP7P.64.GOLD", "CN IPhone 7 Plus 64 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.64.GREY", false, "MD_CN.IP7P.64.GREY", "CN IPhone 7 Plus 64 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.64.SILV", false, "MD_CN.IP7P.64.SILV", "CN IPhone 7 Plus 64 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.64.ROSE", false, "MD_CN.IP7P.64.ROSE", "CN IPhone 7 Plus 64 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.16.GOLD", false, "MD_CN.IP7P.16.GOLD", "CN IPhone 7 Plus 16 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.16.GREY", false, "MD_CN.IP7P.16.GREY", "CN IPhone 7 Plus 16 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.16.SILV", false, "MD_CN.IP7P.16.SILV", "CN IPhone 7 Plus 16 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7P.16.ROSE", false, "MD_CN.IP7P.16.ROSE", "CN IPhone 7 Plus 16 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.128.GOLD", false, "MD_CN.IP7.128.GOLD", "CN IPhone 7 128 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.128.GREY", false, "MD_CN.IP7.128.GREY", "CN IPhone 7 128 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.128.SILV", false, "MD_CN.IP7.128.SILV", "CN IPhone 7 128 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.128.ROSE", false, "MD_CN.IP7.128.ROSE", "CN IPhone 7 128 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.64.GOLD", false, "MD_CN.IP7.64.GOLD", "CN IPhone 7 64 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.64.GREY", false, "MD_CN.IP7.64.GREY", "CN IPhone 7 64 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.64.SILV", false, "MD_CN.IP7.64.SILV", "CN IPhone 7 64 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.64.ROSE", false, "MD_CN.IP7.64.ROSE", "CN IPhone 7 64 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.16.GOLD", false, "MD_CN.IP7.16.GOLD", "CN IPhone 7 16 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.16.GREY", false, "MD_CN.IP7.16.GREY", "CN IPhone 7 16 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.16.SILV", false, "MD_CN.IP7.16.SILV", "CN IPhone 7 16 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".CN.IP7.16.ROSE", false, "MD_CN.IP7.16.ROSE", "CN IPhone 7 16 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.128.GOLD", false, "MD_HK.IP7P.128.GOLD", "HK IPhone 7 Plus 128 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.128.GREY", false, "MD_HK.IP7P.128.GREY", "HK IPhone 7 Plus 128 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.128.SILV", false, "MD_HK.IP7P.128.SILV", "HK IPhone 7 Plus 128 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.128.ROSE", false, "MD_HK.IP7P.128.ROSE", "HK IPhone 7 Plus 128 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.64.GOLD", false, "MD_HK.IP7P.64.GOLD", "HK IPhone 7 Plus 64 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.64.GREY", false, "MD_HK.IP7P.64.GREY", "HK IPhone 7 Plus 64 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.64.SILV", false, "MD_HK.IP7P.64.SILV", "HK IPhone 7 Plus 64 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.64.ROSE", false, "MD_HK.IP7P.64.ROSE", "HK IPhone 7 Plus 64 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.16.GOLD", false, "MD_HK.IP7P.16.GOLD", "HK IPhone 7 Plus 16 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.16.GREY", false, "MD_HK.IP7P.16.GREY", "HK IPhone 7 Plus 16 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.16.SILV", false, "MD_HK.IP7P.16.SILV", "HK IPhone 7 Plus 16 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7P.16.ROSE", false, "MD_HK.IP7P.16.ROSE", "HK IPhone 7 Plus 16 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.128.GOLD", false, "MD_HK.IP7.128.GOLD", "HK IPhone 7 128 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.128.GREY", false, "MD_HK.IP7.128.GREY", "HK IPhone 7 128 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.128.SILV", false, "MD_HK.IP7.128.SILV", "HK IPhone 7 128 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.128.ROSE", false, "MD_HK.IP7.128.ROSE", "HK IPhone 7 128 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.64.GOLD", false, "MD_HK.IP7.64.GOLD", "HK IPhone 7 64 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.64.GREY", false, "MD_HK.IP7.64.GREY", "HK IPhone 7 64 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.64.SILV", false, "MD_HK.IP7.64.SILV", "HK IPhone 7 64 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.64.ROSE", false, "MD_HK.IP7.64.ROSE", "HK IPhone 7 64 GB Rose"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.16.GOLD", false, "MD_HK.IP7.16.GOLD", "HK IPhone 7 16 GB Gold"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.16.GREY", false, "MD_HK.IP7.16.GREY", "HK IPhone 7 16 GB Grey"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.16.SILV", false, "MD_HK.IP7.16.SILV", "HK IPhone 7 16 GB Silver"),
            new OptionListItem(APP_prefPhoneKey+".HK.IP7.16.ROSE", false, "MD_HK.IP7.16.ROSE", "HK IPhone 7 16 GB Rose"),
            new OptionListItem(APP_idTypeKey+".PASSPORT", false, "PASSPORT", ""),
            new OptionListItem(APP_idTypeKey+".HKIDCARD", false, "HKIDCARD", ""),
            new OptionListItem(APP_idTypeKey+".EXITPASS", false, "EXITENTRYPASS", ""),
            new OptionListItem(APP_idTypeKey+".CNIDCARD", false, "CNIDCARD", ""),
            new OptionListItem(APP_prefTimeSlotKey +".10.00.10.30.AM", false, "10:00-10:30AM", ""),
            new OptionListItem(APP_prefTimeSlotKey +".10.30.11.00.AM", false, "10:30-11:00AM", ""),
            new OptionListItem(APP_prefTimeSlotKey +".11.00.11.30.AM", false, "11:00-11:30AM", ""),
            new OptionListItem(APP_prefTimeSlotKey +".11.30.12.00.PM", false, "11:30-12:00PM", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+HK+SEPARATOR+"IFC", false, "R428", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+HK+SEPARATOR+"FW", false, "R485", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+HK+SEPARATOR+"CAN", false, "R499", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+HK+SEPARATOR+"CWB", false, "R409", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+CN+SEPARATOR+"IFC", false, "R428", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+CN+SEPARATOR+"FW", false, "R485", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+CN+SEPARATOR+"CAN", false, "R499", ""),
            new OptionListItem(APP_prefStoreKey +SEPARATOR+CN+SEPARATOR+"CWB", false, "R409", "")
    );

    public static String getDescByModel(String model){
        Optional<OptionListItem> item = OPTION_LIST.stream()
                .filter(f -> f.getModel().equalsIgnoreCase(model))
                .findFirst();
        return getDescByKey(item.get().getKey());
    }

    public static String getDescByKey(String key){
        String[] elements = key.split("\\.");
        for(int i=0;i<elements.length;i++){
            elements[i]=DICT.get(elements[i]);
            if(elements[i]==null)elements[i]="";
        }
        //Arrays.stream(elements).forEach(e->e=DICT.get(e));
        return String.join(" ", elements).trim();
    }

    public static String getModelByKey(String key){
        Optional<OptionListItem> item = OPTION_LIST.stream()
                .filter(f -> f.getKey().equalsIgnoreCase(key))
                .findFirst();
        return item.get().getModel();
    }

    public static Accordion generateIPhoneList(String[] selectedPhone, ObservableList<AppTableItem> appData) {

        final String[] countryOptions = {CN, HK};
        final String[] modelOptions = {"IP7", "IP7P"};
        final String[] capacityOptions = {"16", "64", "128"};


        Accordion countries = new Accordion();

        Arrays.stream(countryOptions).forEach(country -> {
            Accordion models = new Accordion();
            countries.getPanes().add(new TitledPane(DICT.get(country), models));
            Arrays.stream(modelOptions).forEach(model -> {
                Accordion capacities = new Accordion();
                models.getPanes().add(new TitledPane(TAB+DICT.get(model), capacities));
                Arrays.stream(capacityOptions).forEach(capacity -> {


                    ObservableList<OptionListItem> optList = FXCollections.observableArrayList(
                            getOptionListByKey(APP_prefPhoneKey+SEPARATOR+country + SEPARATOR+ model + SEPARATOR + capacity));
                    optList.forEach(opt -> {
                        Arrays.stream(selectedPhone).forEach(phone -> {
                            if(phone.equalsIgnoreCase(opt.getModel())){
                                opt.setSelected(true);
                            }
                        });
                    });

                    FormattedListCellFactory formattedListCellFactory = new FormattedListCellFactory();
                    formattedListCellFactory.setAppData(appData);

                    ListView<OptionListItem> lv = new ListView<>();
                    lv.setCellFactory(formattedListCellFactory);
                    lv.setItems(optList);
                    lv.setPrefHeight(100.0);

                    capacities.getPanes().add(new TitledPane(TAB+TAB+DICT.get(capacity), lv));
                });
            });
        });
        return countries;
    }

    public static Accordion generateAppleStoreList(String[] selectedStores, ObservableList<AppTableItem> appData) {
        final String[] countryOptions = {CN, HK};
        Accordion countries = new Accordion();

        Arrays.stream(countryOptions).forEach(country -> {
            ObservableList<OptionListItem> optList = FXCollections.observableArrayList(
                    getOptionListByKey(APP_prefStoreKey + SEPARATOR + country));



            optList.forEach(opt -> {
                opt.setSelected(false);
                Arrays.stream(selectedStores).forEach(store -> {
                    if (store.equalsIgnoreCase(opt.getModel())) {
                        opt.setSelected(true);
                    }
                });
            });

            FormattedListCellFactory formattedListCellFactory = new FormattedListCellFactory();
            formattedListCellFactory.setAppData(appData);

            ListView<OptionListItem> lv = new ListView<>();
            lv.setCellFactory(formattedListCellFactory);
            lv.setItems(optList);
            lv.setPrefHeight(100.0);

            countries.getPanes().add(new TitledPane(DICT.get(country), lv));
        });
        return countries;
    }


    public static ComboBox generateIDTypeList(String selectedIDType, ObservableList<AppTableItem> appData) {

        FormattedListCellFactory formattedListCellFactory = new FormattedListCellFactory();
        formattedListCellFactory.setAppData(appData);

        ObservableList<OptionListItem> optList = FXCollections.observableArrayList(getOptionListByKey(APP_idTypeKey));

        int selectedIDX = 0;
        for(selectedIDX=0;selectedIDX<optList.size();selectedIDX++){
            if(optList.get(selectedIDX).getModel().equalsIgnoreCase(selectedIDType)){
                break;
            }
        }

        ComboBox cbIdType = new ComboBox();
        cbIdType.setCellFactory(formattedListCellFactory);
        cbIdType.setItems(optList);
        cbIdType.getSelectionModel().select(selectedIDX);
        cbIdType.setOnAction(event -> {
            appData.forEach(appTableItem -> {
                if(appTableItem.getFieldCell().equalsIgnoreCase(OptionListGenerator.APP_idTypeFldName)){
                    appTableItem.setValueCell(((OptionListItem)cbIdType.getSelectionModel().getSelectedItem()).getModel());
                }
            });
        });

        return cbIdType;
    }

    private static List<OptionListItem> getOptionListByKey(String key){
        List<OptionListItem> list = OPTION_LIST.stream()
                .filter(f -> f.getKey().startsWith(key))
                .collect(Collectors.toList());
        return list;
    }


    public static ListView generateTimeSlotList(String[] selectedTimeSlots, ObservableList<AppTableItem> appData) {

        ObservableList<OptionListItem> optList = FXCollections.observableArrayList(getOptionListByKey(APP_prefTimeSlotKey));

        optList.forEach(opt -> {
            opt.setSelected(false);
            Arrays.stream(selectedTimeSlots).forEach(timeSlot -> {
                if(timeSlot.equalsIgnoreCase(opt.getModel())){
                    opt.setSelected(true);
                }
            });
        });

        FormattedListCellFactory formattedListCellFactory = new FormattedListCellFactory();
        formattedListCellFactory.setAppData(appData);

        ListView<OptionListItem> lv = new ListView<>();
        lv.setCellFactory(formattedListCellFactory);
        lv.setItems(optList);
        lv.setPrefHeight(100.0);

        return lv;
    }


}