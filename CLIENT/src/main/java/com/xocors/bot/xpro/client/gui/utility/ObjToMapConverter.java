package com.xocors.bot.xpro.client.gui.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xocors.bot.xpro.common.XProApplication;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xocor_zg5ru7n on 2016/6/5.
 */
public class ObjToMapConverter {
    public static Map<String, Object> convertAppToMap(XProApplication app) {
        Map<String, Object> map = new HashMap<>();

        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(app.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }


        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                try {
                    if(!pd.getName().equalsIgnoreCase("class")){
                        map.put(pd.getName(), reader.invoke(app));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }

        return map;
    }

    public static XProApplication convertObjToApp(Map<String,Object> map){

        ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        XProApplication app = mapper.convertValue(map, XProApplication.class);
        ObjToMapConverter.convertAppToMap(app).forEach((K,V) ->{
            System.out.println("Key = "+ K + " Value = " + V);
        });

        return app;
    }

}