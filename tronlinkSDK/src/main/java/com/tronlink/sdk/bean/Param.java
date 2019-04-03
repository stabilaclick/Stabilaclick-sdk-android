package com.tronlink.sdk.bean;

public class Param {
//    String paramName;
    String paramValue;
    paramType paramType;

    public enum paramType {
        ADDRESS,
        STRING,
        BOOL,
        BYTES,
        DOUBLE
    }

//    public String getParamName() {
//        return paramName;
//    }
//
//    public void setParamName(String paramName) {
//        this.paramName = paramName;
//    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Param.paramType getParamType() {
        return paramType;
    }

    public void setParamType(Param.paramType paramType) {
        this.paramType = paramType;
    }
}
