package com.xocors.bot.xpro.client.gui.component;

import com.xocors.bot.xpro.client.gui.db.XProAppRepository;
import com.xocors.bot.xpro.client.gui.model.AppTableItem;
import com.xocors.bot.xpro.client.gui.model.DashboardTableItem;
import com.xocors.bot.xpro.client.gui.utility.ObjToMapConverter;
import com.xocors.bot.xpro.client.gui.utility.OptionListGenerator;
import com.xocors.bot.xpro.common.XProApplication;
import com.xocors.bot.xpro.gateway.XProGateway;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.*;

/**
 * Created by yoyow on 2016/5/31.
 */

public class MainPaneController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabDashboard;
    @FXML
    private Tab tabAppMaint;
    @FXML
    private TableView tbViewDashboard;
    @FXML
    private CheckBox cbForAll;
    @FXML
    private TableColumn isSelectedCol;
    @FXML
    private TableColumn appIDCol;
    @FXML
    private TableColumn captchaImgCol;
    @FXML
    private TableColumn captchaTxtCol;
    @FXML
    private TableColumn smsImgCol;
    @FXML
    private TableColumn smsTxtCol;
    @FXML
    private TableColumn smsReceivedCol;
    @FXML
    private TableColumn stsMsgCol;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnSubmitAll;
    @FXML
    private Button btnSubmitSelected;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView tbApplication;
    @FXML
    private TableColumn fieldCol;
    @FXML
    private TableColumn valueCol;
    @FXML
    private Label lblAppOptSummary;
//    private String systemFieldList;
//    private AppFormattedTableCellFactory appFormattedTableCellFactory;

    private ConfigurableApplicationContext context;
    private Map<String,Object> appMap;
    private ObservableList<DashboardTableItem> dashboardData = FXCollections.observableArrayList();
    private ObservableList<AppTableItem> appData = FXCollections.observableArrayList(app -> new Observable[] { app.valueCellProperty()});
    private String systemFieldList;

    private XProAppRepository xProAppRepository;

    private XProGateway xProGateway;

    public MainPaneController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        };

        btnRefresh.setOnAction(event -> refresh());
        btnSubmitAll.setOnAction(event -> {
            List<XProApplication> appList = new ArrayList<>();
            dashboardData.forEach(row -> {
                appList.add(xProAppRepository.findByApplicationID(row.getAppIDCell()));
            });

            submitApplications(appList);

        });

        btnSubmitSelected.setOnAction(event -> {
            List<XProApplication> appList = new ArrayList<>();
            dashboardData.forEach(row -> {
                if(row.getIsSelectedCell()){
                    appList.add(xProAppRepository.findByApplicationID(row.getAppIDCell()));

                }
            });
            submitApplications(appList);

        });

        cbForAll.setOnAction(event -> {
            if (event.getSource() instanceof CheckBox) {
                dashboardData.forEach(item -> item.setIsSelectedCell(cbForAll.isSelected()));
            }
        });

        txtSearch.setOnAction(event -> {
            if(txtSearch.getText().trim().length()==0){
                sendAlertMessage("Please enter search keyword.");
                return;
            }

            search(txtSearch.getText());
        });

        btnSave.setOnAction(event -> {
            save();
        });

        btnDelete.setOnAction(event -> {
            if(appData.isEmpty()){
                sendAlertMessage("Please search application for deletion.");
                return;
            }

            XProApplication app = xProAppRepository.findByApplicationID(txtSearch.getText().trim());
            if(app != null) {
                deleteApplication(app);
                appData.clear();
                sendAlertMessage("Application '"+app.getApplicationID()+"' has been deleted.");
            }else{
                sendAlertMessage("Unexpected exception!");
            }
        });

        FormattedTableCellFactory formattedTableCellFactory = new FormattedTableCellFactory();
        formattedTableCellFactory.setAlignment(TextAlignment.LEFT);

        isSelectedCol.setCellValueFactory(new PropertyValueFactory<>("isSelectedCell"));
        isSelectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(isSelectedCol));

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appIDCell"));

        captchaImgCol.setCellValueFactory(new PropertyValueFactory<>("captchaImgCell"));
        captchaImgCol.setCellFactory(formattedTableCellFactory);

        captchaTxtCol.setCellValueFactory(new PropertyValueFactory<>("captchaTxtCell"));
        captchaTxtCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        captchaTxtCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                dashboardData.get(event.getTablePosition().getRow()).setCaptchaTxtCell(event.getNewValue().toString());
                DashboardTableItem rowItem = (DashboardTableItem) event.getRowValue();
                XProApplication application = xProAppRepository.findByApplicationID(rowItem.getAppIDCell());
                application.setCaptchaResult(event.getNewValue().toString());
                xProAppRepository.save(application);
            }
        });

        smsImgCol.setCellValueFactory(new PropertyValueFactory<>("smsImgCell"));
        smsImgCol.setCellFactory(formattedTableCellFactory);

        smsTxtCol.setCellValueFactory(new PropertyValueFactory<>("smsTxtCell"));
        smsTxtCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        smsTxtCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                dashboardData.get(event.getTablePosition().getRow()).setSmsTxtCell(event.getNewValue().toString());
                DashboardTableItem rowItem = (DashboardTableItem) event.getRowValue();
                XProApplication application = xProAppRepository.findByApplicationID(rowItem.getAppIDCell());
                application.setSmsImgOcrResult(event.getNewValue().toString());
                xProAppRepository.save(application);
            }
        });

        smsReceivedCol.setCellValueFactory(new PropertyValueFactory("smsReceivedCell"));

        stsMsgCol.setCellValueFactory(new PropertyValueFactory<>("stsMsgCell"));

        tbViewDashboard.setItems(dashboardData);
        tbViewDashboard.setRowFactory(new Callback<TableView<DashboardTableItem>, TableRow<DashboardTableItem>>() {
            @Override
            public TableRow<DashboardTableItem> call(TableView<DashboardTableItem> tableView) {
                final TableRow<DashboardTableItem> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Edit");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //tbViewDashboard.getItems().remove(row.getItem());
                        search(row.getItem().getAppIDCell());
                        tabPane.getSelectionModel().select(tabAppMaint);
                    }
                });
                contextMenu.getItems().add(removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );
                return row ;
            }
        });

        //Application Table initialtion
        AppFormattedTableCellFactory appFormattedTableCellFactory = new AppFormattedTableCellFactory();
        appFormattedTableCellFactory.setAlignment(TextAlignment.LEFT);
        appFormattedTableCellFactory.setAppData(appData);


        fieldCol.setCellValueFactory(new PropertyValueFactory("fieldCell"));

        valueCol.setCellValueFactory(new PropertyValueFactory("valueCell"));
        valueCol.setCellFactory(appFormattedTableCellFactory);



        appData.addListener((ListChangeListener<AppTableItem>) c -> {
            lblAppOptSummary.setText("[ Application Summary ] \n");
            appData.forEach(e->{
                if(e.getValueCell() instanceof String[]){
                    lblAppOptSummary.setText(lblAppOptSummary.getText()+ e.getFieldCell() + " ->\n");
                    Arrays.stream((String[])e.getValueCell()).forEach(value-> lblAppOptSummary.setText(lblAppOptSummary.getText()+ "\t"+ OptionListGenerator.getDescByModel(value) + "\n"));
                }
            });
        });

        tbApplication.setItems(appData);
        tbApplication.getSortOrder().add(fieldCol);
    }

    private void submitApplications(List<XProApplication> appList) {
        appList.forEach(application -> xProGateway.submitApplication(application));
    }

    public void refresh(){
        tbViewDashboard.setDisable(true);

        dashboardData.clear();

        List<XProApplication> appList = xProAppRepository.findAll();
        appList.forEach(app ->{
            DashboardTableItem item = new DashboardTableItem(app.getApplicationID());
            item.setSmsTxtCell(app.getSmsImgOcrResult());
            item.setCaptchaTxtCell(app.getCaptchaResult());
            item.setIsSelectedCell(false);
            item.setCaptchaImgCell(app.getCaptchaContentB64());
            item.setSmsImgCell(app.getSmsImgB64());
            item.setSmsReceivedCell(app.getSmsContent());

           addDashboardRow(item);
        });

        tbViewDashboard.setDisable(false);



    }

    public void setDashboardData(ObservableList<DashboardTableItem> data){
        this.dashboardData.clear();
        this.dashboardData.addAll(data);
        //refresh();
    }

    public void addDashboardRow(DashboardTableItem item){
        dashboardData.add(item);
    }

    public ObservableList<DashboardTableItem> getDashboardDataData(){
        return dashboardData;
    }

    public ObservableList<AppTableItem> getAppData() {
        return appData;
    }

    public void setAppData(ObservableList<AppTableItem> appData) {

        this.appData.clear();
        tbApplication.getItems().clear();
        this.appData.addAll(appData);
        tbApplication.sort();
        tbApplication.refresh();
    }

    public void addAppRow(AppTableItem item){
        appData.add(item);
    }

    private void search(String appID){
//        XProApplication app = new XProApplication(appID);
//        app.setCountry(OptionListGenerator.CN);
//        app.setIdType("EXITENTRYPASS");


        XProApplication app = xProAppRepository.findByApplicationID(appID);


        if(app==null){
            sendAlertMessage("No application found for '"+txtSearch.getText()+"'.");

            appData.clear();
            return;
        }
        presentAppToTable(app);


    }

    private void presentAppToTable(XProApplication app){
        ObservableList<AppTableItem> appData = FXCollections.observableArrayList();

        appMap = ObjToMapConverter.convertAppToMap(app);
        appMap.forEach((K, V) -> {
            AppTableItem item = new AppTableItem();
            if(systemFieldList.indexOf(K)==-1) {
                item.setFieldCell(K);
                item.setValueCell(V);
                appData.add(item);
            }
        });

        setAppData(appData);
    }


    private void save(){

        if (appData.size()==0){
            if(txtSearch.getText().trim().length()==0){
                sendAlertMessage("No application to be saved.");
                return;
            }else{
                XProApplication app = new XProApplication(txtSearch.getText().trim());
                presentAppToTable(app);
                sendAlertMessage("Created new application for " + app.getApplicationID());
                return;
            }
        }

        appData.forEach(row ->{
            appMap.put(row.getFieldCell(), row.getValueCell());
        });
        XProApplication app = ObjToMapConverter.convertObjToApp(appMap);

        xProAppRepository.save(app);

        sendAlertMessage("Application "+app.getApplicationID()+" is saved;");
        search(app.getApplicationID());
        refresh();
    }

    public void updateApplication(XProApplication application){
        XProApplication originalApp = xProAppRepository.findByApplicationID(application.getApplicationID());
        application.setIdType(originalApp.getIdType());
        xProAppRepository.save(application);
        refresh();
    }

    private void deleteApplication(XProApplication app){
        xProAppRepository.delete(app);
        refresh();
    }

    private void sendAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
        systemFieldList = context.getEnvironment().getProperty("xpro.client.appplication.systemFieldList");

//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        System.out.println(String.join("\n",beanDefinitionNames));

        xProAppRepository = context.getBean(XProAppRepository.class);
        xProGateway = (XProGateway) context.getBean("xProGateway");

    }

}