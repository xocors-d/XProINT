package com.xocors.bot.xpro.common;

import org.apache.http.impl.client.BasicCookieStore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Created by yoyow on 2016/5/24.
 */
public class XProApplication {
    @Id
    private String id;
    private List<Map<String, String>> steps;
    private int currentStep = 0;
    private String sourceQueue="";
    private String targetQueue="";
    private String country="";
    @Indexed(unique=true)
    private String applicationID="";
    private String userName="";
    private String password="";
    private String customerMobile="";
    private String idType="";
    private String idNumber="";
    private String weChat="";
    private String whatsApp="";
    private String[] preferredIPhones = new String[]{};
    private String[] preferredStores = new String[]{};
    private String[] preferredTimeSlots = new String[]{};
    private URL httpURL;
    private URL httpReferrer;
    private BasicCookieStore cookies;
    private String httpContent="";
    private String smsSenderNum="";
    private String smsReceiverNum="";
    private String smsContent="";
    private String smsImgB64 ="";
    private String smsImgOcrResult="";
    private String captchaContentB64="";
    private String captchaResult="";
    private String remark="";


    public XProApplication(){

    }

    public XProApplication(String applicationID) {
        this.applicationID = applicationID;
        //captchaTxtCol = new TableColumn<>("captchaTxtCell");
//        File img = new File("/main/gui_layouts/_captcha.jpeg");
//
//        byte[] base64ImgStr = null;
//        try {
//            base64ImgStr = Base64.getEncoder().encode(Files.readAllBytes(img.toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        setCaptchaContentB64("image/jpeg;base64,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");

//        img = new File("/main/gui_layouts/_smsImg.png");
//
//        try {
//            base64ImgStr = Base64.getEncoder().encode(Files.readAllBytes(img.toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        setSmsImgB64("image/png;base64,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");
    }

    public List<Map<String, String>> getSteps() {
        return steps;
    }

    public void setSteps(List<Map<String, String>> steps) {
        this.steps = steps;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public String getSourceQueue() {
        return sourceQueue;
    }

    public void setSourceQueue(String sourceQueue) {
        this.sourceQueue = sourceQueue;
    }

    public String getTargetQueue() {
        return targetQueue;
    }

    public void setTargetQueue(String targetQueue) {
        this.targetQueue = targetQueue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String[] getPreferredIPhones() {
        return preferredIPhones;
    }

    public void setPreferredIPhones(String[] preferredIPhones) {
        this.preferredIPhones = preferredIPhones;
    }

    public String[] getPreferredStores() {
        return preferredStores;
    }

    public void setPreferredStores(String[] preferredStores) {
        this.preferredStores = preferredStores;
    }

    public URL getHttpURL() {
        return httpURL;
    }

    public void setHttpURL(URL httpURL) {
        this.httpURL = httpURL;
    }

    public URL getHttpReferrer() {
        return httpReferrer;
    }

    public void setHttpReferrer(URL httpReferrer) {
        this.httpReferrer = httpReferrer;
    }

    public BasicCookieStore getCookies() {
        return cookies;
    }

    public void setCookies(BasicCookieStore cookies) {
        this.cookies = cookies;
    }

    public String getHttpContent() {
        return httpContent;
    }

    public void setHttpContent(String httpContent) {
        this.httpContent = httpContent;
    }

    public String getSmsSenderNum() {
        return smsSenderNum;
    }

    public void setSmsSenderNum(String smsSenderNum) {
        this.smsSenderNum = smsSenderNum;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getCaptchaContentB64() {
        return captchaContentB64;
    }

    public void setCaptchaContentB64(String captchaContentB64) {
        this.captchaContentB64 = captchaContentB64;
    }

    public String getCaptchaResult() {
        return captchaResult;
    }

    public void setCaptchaResult(String captchaResult) {
        this.captchaResult = captchaResult;
    }

    public String getSmsReceiverNum() {
        return smsReceiverNum;
    }

    public void setSmsReceiverNum(String smsReceiverNum) {
        this.smsReceiverNum = smsReceiverNum;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String[] getPreferredTimeSlots() {
        return preferredTimeSlots;
    }

    public void setPreferredTimeSlots(String[] preferredTimeSlots) {
        this.preferredTimeSlots = preferredTimeSlots;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getSmsImgB64() {
        return smsImgB64;
    }

    public void setSmsImgB64(String smsImgB64) {
        this.smsImgB64 = smsImgB64;
    }

    public String getSmsImgOcrResult() {
        return smsImgOcrResult;
    }

    public void setSmsImgOcrResult(String smsImgOcrResult) {
        this.smsImgOcrResult = smsImgOcrResult;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}