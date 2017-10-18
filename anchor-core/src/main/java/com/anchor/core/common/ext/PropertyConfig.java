package com.anchor.core.common.ext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuqh
 * @ClassName: WebPropertyPlaceholderConfigurer
 * @Description:
 * @date 2017/10/10 15:39
 * @since 1.0.1
 */
public class PropertyConfig extends PropertyPlaceholderConfigurer {
    public final static ConcurrentHashMap<String,String> config = new ConcurrentHashMap<String,String>();

    //加载配置文件
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);

        Enumeration<String> enumeration = (Enumeration<String>) props.propertyNames();
        while(enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            config.put(key,props.getProperty(key));
        }
    }

    public static String get(String key){
        return config.get(key);
    }
    public static String get(String key, String defualt){
        String val =  config.get(key);
        if(StringUtils.isBlank(val)){
            return defualt;
        }
        return val;
    }
}
