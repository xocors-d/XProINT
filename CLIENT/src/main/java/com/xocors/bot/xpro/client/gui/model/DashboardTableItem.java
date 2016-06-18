package com.xocors.bot.xpro.client.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Created by yoyow on 2016/6/1.
 */
public class DashboardTableItem {
    private SimpleBooleanProperty isSelectedCell = new SimpleBooleanProperty(false);
    private SimpleStringProperty appIDCell = new SimpleStringProperty("");
    private SimpleStringProperty captchaImgCell = new SimpleStringProperty("");
    private SimpleStringProperty captchaTxtCell = new SimpleStringProperty("");
    private SimpleStringProperty smsImgCell = new SimpleStringProperty("");
    private SimpleStringProperty smsTxtCell = new SimpleStringProperty("");
    private SimpleStringProperty smsReceivedCell = new SimpleStringProperty("");
    private SimpleStringProperty stsMsgCell = new SimpleStringProperty("");

    public DashboardTableItem(){
        this("");
    }

    public DashboardTableItem(String appIDCell) {
        setAppIDCell(appIDCell);
//        if(appIDCell.length()>=6){
//            setIsSelectedCell(true);
//        }
//        setSmsReceivedCell("Received MSG");
//        setCaptchaTxtCell("Captcha text");
//
//        //captchaTxtCol = new TableColumn<>("captchaTxtCell");
//        File img = new File("D:\\github_clone\\xpro-integration\\CLIENT\\src\\main\\resources\\gui_layouts\\_captcha.jpeg");
//
//        byte[] base64ImgStr = null;
//        try {
//            base64ImgStr = Base64.getEncoder().encode(Files.readAllBytes(img.toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        setCaptchaImgCell(new String(base64ImgStr));
//
//        img = new File("D:\\github_clone\\xpro-integration\\CLIENT\\src\\main\\resources\\gui_layouts\\_smsImg.png");
//
//        try {
//            base64ImgStr = Base64.getEncoder().encode(Files.readAllBytes(img.toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        setSmsImgCell(new String(base64ImgStr));

    }

    public String getAppIDCell() {
        return appIDCell.get();
    }

    public SimpleStringProperty appIDCellProperty() {
        return appIDCell;
    }

    public void setAppIDCell(String appIDCell) {
        this.appIDCell.set(appIDCell);
    }

    public String getCaptchaImgCell() {
        return captchaImgCell.get();
    }

    public SimpleStringProperty captchaImgCellProperty() {
        return captchaImgCell;
    }

    public void setCaptchaImgCell(String captchaImgCell) {
        this.captchaImgCell.set(captchaImgCell);
    }

    public String getCaptchaTxtCell() {
        return captchaTxtCell.get();
    }

    public SimpleStringProperty captchaTxtCellProperty() {
        return captchaTxtCell;
    }

    public void setCaptchaTxtCell(String captchaTxtCell){
        this.captchaTxtCell.set(captchaTxtCell);
    }

    public String getSmsImgCell() {
        return smsImgCell.get();
    }

    public SimpleStringProperty smsImgCellProperty() {
        return smsImgCell;
    }

    public void setSmsImgCell(String smsImgCell) {
        this.smsImgCell.set(smsImgCell);
    }

    public String getSmsTxtCell() {
        return smsTxtCell.get();
    }

    public SimpleStringProperty smsTxtCellProperty() {
        return smsTxtCell;
    }

    public void setSmsTxtCell(String smsTxtCell) {
        this.smsTxtCell.set(smsTxtCell);
    }

    public String getSmsReceivedCell() {
        return smsReceivedCell.get();
    }

    public SimpleStringProperty smsReceivedCellProperty() {
        return smsReceivedCell;
    }

    public void setSmsReceivedCell(String smsReceivedCell) {
        this.smsReceivedCell.set(smsReceivedCell);
    }

    public String getStsMsgCell() {
        return stsMsgCell.get();
    }

    public SimpleStringProperty stsMsgCellProperty() {
        return stsMsgCell;
    }

    public void setStsMsgCell(String stsMsgCell) {
        this.stsMsgCell.set(stsMsgCell);
    }

    public boolean getIsSelectedCell() {
        return isSelectedCell.get();
    }

    public SimpleBooleanProperty isSelectedCellProperty() {
        return isSelectedCell;
    }

    public void setIsSelectedCell(boolean isSelectedCell) {
        this.isSelectedCell.set(isSelectedCell);
    }
}