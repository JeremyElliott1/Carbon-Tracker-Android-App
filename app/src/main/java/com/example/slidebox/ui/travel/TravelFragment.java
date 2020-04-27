package com.example.slidebox.ui.travel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.HelpOptions;
import com.example.slidebox.R;


public class TravelFragment extends Fragment {

    private TravelViewModel travelViewModel;
    Button button;
    TextView liveUpdate;
    TextView total;
    Handler handler = new Handler();
    Runnable r= new Runnable(){
        @Override
        public void run() {
            liveUpdate.setText("" + MyService.getDistance() + " m");
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

    private Button start;
    private Button stop;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelViewModel =
                ViewModelProviders.of(this).get(TravelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_travel, container, false);


                MyService = new MyService();
                Intent intent = new Intent(getActivity(),MyService.class);
                getActivity().startService(intent);
                getActivity().bindService(intent, serviceConnection, getActivity().BIND_AUTO_CREATE);
                liveUpdate = root.findViewById(R.id.textView);
                total = root.findViewById(R.id.textView4);
                handler.post(r);

                start = root.findViewById(R.id.start);
                stop = root.findViewById(R.id.stop);

                onStartClick();
                onStopClick();

                return root;
            }


            public void onStartClick(){
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    total.setText("0 m");
                    MyService.Start();
                }
            });
            }

            public void onStopClick(){
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total.setText(String.valueOf(MyService.getDistance())+ " m");
                MyService.Stop();
            }
        });
            }

            public void onDestroy() {
                if (serviceConnection != null) {
                    getActivity().unbindService(serviceConnection);
                    serviceConnection = null;
                }
                super.onDestroy();
            }
}



