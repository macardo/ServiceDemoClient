// AIDL_Service.aidl
package com.macardo.servicedemo_server;

// Declare any non-default types here with import statements

interface AIDL_Service {

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    //声明服务器端向客户端提供的接口方法
    void aidl_service();
}
