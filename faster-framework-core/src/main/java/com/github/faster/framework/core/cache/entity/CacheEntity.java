package com.github.faster.framework.core.cache.entity;

/**
 * @author  zhangbowen
 *  存储缓存中的实体
 */
public class CacheEntity {
    private String jsonValue;
    private Class defaultType;

    public String getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public Class getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(Class defaultType) {
        this.defaultType = defaultType;
    }
}
