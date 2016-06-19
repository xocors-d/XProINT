package com.xocors.bot.xpro.gateway;

import com.xocors.bot.xpro.common.XProApplication;
import org.springframework.integration.annotation.Gateway;

/**
 * Created by xocors on 18/6/2016.
 */
public interface XProGateway {

    @Gateway(requestChannel="applications")
    void submitApplication(XProApplication application);
}
