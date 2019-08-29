package com.seatig.common;

import com.seatig.utils.DESUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    String[] propertys={"jdbc.username","jdbc.password"};
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if(isEncryptProp(propertyName)){
            System.out.println("username="+DESUtils.getEncryptString("postgres"));
            System.out.println("password="+DESUtils.getEncryptString("123456"));

            return  DESUtils.getDecryptString(propertyValue);

        }else {
            return propertyValue;
        }

    }

    public boolean isEncryptProp(String name){
        for (int i=0;i<propertys.length;i++){
            if (propertys[i].equals(name)){
                return true;
            }
        }
        return false;
    }
}
