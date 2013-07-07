package com.motech.test.CRUD;

import java.util.Map;
import java.util.HashMap;

public abstract class CustomizableEntity {

    private Map customProperties;

    public Map getCustomProperties() {
        if (customProperties == null)
            customProperties = new HashMap();
        return customProperties;
    }
    public void setCustomProperties(Map customProperties) {
        this.customProperties = customProperties;
    }

    public Object getValueOfCustomField(String name) {
        return getCustomProperties().get(name);
    }

    public void setValueOfCustomField(String name, Object value) {
        getCustomProperties().put(name, value);
    }

}