package com.macardo.servicedemo_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.macardo.servicedemo_server.AIDL_Service;

public class MainActivity extends AppCompatActivity {

    Button bind_serviceBtn;
    //定义接口变量
    private AIDL_Service mAIDL_Service;
    //创建ServiceConnection的匿名类
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //使用AIDL_Service.Stub.asInterface()方法获取服务器端返回的IBinder对象，将IBinder对象转换成mAIDL_Service接口对象
            mAIDL_Service = AIDL_Service.Stub.asInterface(service);

            try {
                //通过mAIDL_Service对象调用在AIDL_Service.aidl文件中定义的接口方法，实现跨进程通讯
                mAIDL_Service.aidl_service();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind_serviceBtn = findViewById(R.id.bind_serviceBtn);
        bind_serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent指定服务器端的服务名和所在包，绑定Service
                Intent intent = new Intent("com.macardo.servicedemo_server.AIDL_Service");
                //Android 5.0后无法只通过隐式的Intent绑定远程Service,需要通过setPackage()制定包名
                intent.setPackage("com.macardo.servicedemo_server");
                //绑定Service
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
            }
        });
    }
}
