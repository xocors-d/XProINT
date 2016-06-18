package com.xocors.bot.xpro.sms.service.impl;

import com.xocors.bot.xpro.common.XProApplication;
import com.xocors.bot.xpro.sms.service.XProSMSServer;
import org.smslib.OutboundMessage;
import org.smslib.smsserver.SMSServer;
import org.smslib.smsserver.interfaces.Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by yoyow on 2016/5/3.
 */

public class XProSMSServerImpl implements XProSMSServer {

    @Autowired
    Environment env;

    private SMSServer smsServer;

    @Override
    public void send(XProApplication application) {

        if (application.getSmsContent().startsWith("#") || application.getSmsContent().startsWith("*")) {
            smsServer.sendUSSD(application.getSmsContent(), application.getSmsSenderNum());
        } else {
            try {
                for (Interface<? extends Object> inf : smsServer.getInfList()) {
                    OutboundMessage outboundMsg = convertSMSToOutboundMsg(application);
                    inf.writeMessageToSend(outboundMsg);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    @Override
    public void start() {
        smsServer = new SMSServer(env);
        Thread serverThread = new Thread(smsServer);
        serverThread.start();
    }

    @Override
    public void end() {

    }

    private OutboundMessage convertSMSToOutboundMsg(XProApplication application) {
        OutboundMessage outboundMsg = new OutboundMessage();
        outboundMsg.setPriority(2147483647);
        outboundMsg.setStatusReport(true);
        outboundMsg.setRecipient(application.getSmsReceiverNum());
        outboundMsg.setText(application.getSmsContent());
        return outboundMsg;
    }
}
