package com.example.slidebox.ui.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.example.slidebox.R;

public class TravelActivity extends AppCompatActivity {

    TextView textView;
    TextView total;
    Handler handler = new Handler();
    Runnable r= new Runnable(){
        @Override
        public void run() {
            textView.setText("" + MyService.getDistance());
            handler.post(r);
        }
    };

    private  MyService MyService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService = ((MyService.MyBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MyService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        MyService = new MyService();
        Intent intent = new Intent(TravelActivity.this,MyService.class);
        startService(intent);
        bindService(intent, serviceConnection, this.BIND_AUTO_CREATE);
        textView = findViewById(R.id.textView);
        total = findViewById(R.id.textView4);
        handler.post(r);
    }


    public void onStartClick(View v){
        MyService.Start();
    }

    public void onStopClick(View v){
        total.setText(String.valueOf(MyService.getDistance()));
        MyService.Stop();
    }

    protected void onDestroy(){
        if(serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }
        super.onDestroy();
    }

}
