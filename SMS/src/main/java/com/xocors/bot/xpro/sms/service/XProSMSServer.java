package com.xocors.bot.xpro.sms.service;


import com.xocors.bot.xpro.common.XProApplication;

/**
 * Created by yoyow on 2016/5/3.
 */


public interface XProSMSServer {
    void send(XProApplication application);
    void start();
    void end();
}
