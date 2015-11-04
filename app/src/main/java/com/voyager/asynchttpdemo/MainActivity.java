package com.voyager.asynchttpdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                opHttp();
                break;
            case R.id.btn_download:
                download();
                break;
        }
    }

    private void download() {
        RequestParams requestParams = new RequestParams();
        Client.get("http://192.168.1.112:8080/test.avi", requestParams, new FileAsyncHttpResponseHandler(new File(Environment.getExternalStorageDirectory() + "/Download/test.avi")) {
            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(MainActivity.this, "访问失败!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int i, Header[] headers, File file) {
                Toast.makeText(MainActivity.this, "访问成功!", Toast.LENGTH_SHORT).show();
                try {
                    FileInputStream fis = new FileInputStream(file);
                    File saveFile = new File(Environment.getExternalStorageDirectory() + "/Download");
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(saveFile);
//                    while(fis.read())
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void opHttp() {
        RequestParams requestParams = new RequestParams();
        requestParams.add("id", "2015");
        Client.get("http://192.168.1.112:8080/Test/servlet/Test", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(MainActivity.this, "访问成功!", Toast.LENGTH_SHORT).show();
                Log.d("MAIN", "访问成功");
                for (Header header : headers) {
                    Log.d("MAIN", header.toString());
                }
                for (byte b : bytes) {
                    Log.d("MAIN", String.valueOf(b));
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MainActivity.this, "访问失败!", Toast.LENGTH_SHORT).show();
                Log.d("MAIN", "访问失败");
//                Log.d("MAIN", bytes.toString());
            }


        });
    }
}
