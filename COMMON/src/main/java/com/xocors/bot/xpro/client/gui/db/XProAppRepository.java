package com.xocors.bot.xpro.client.gui.db;

import com.xocors.bot.xpro.common.XProApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by xocor_zg5ru7n on 2016/6/14.
 */

public interface XProAppRepository extends MongoRepository<XProApplication, String> {
    public XProApplication findByApplicationID(String applicationID);

}