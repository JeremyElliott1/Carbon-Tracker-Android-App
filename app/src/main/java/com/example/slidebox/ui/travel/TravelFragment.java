package com.example.slidebox.ui.travel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.HelpOptions;
import com.example.slidebox.R;
import com.example.slidebox.User;


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

    private GridLayout travelGrid;

    private boolean travelMode;
    private boolean inTravel;

    private double[] pointsPerMeter = {0.0001,0.002,0.002,0.0005};
    private int selectedTravelMode;
    private int totalDistance;


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

                travelMode=false;
                inTravel=false;

                start = root.findViewById(R.id.start);
                stop = root.findViewById(R.id.stop);

                onStartClick();
                onStopClick();

               travelGrid = root.findViewById(R.id.travelGrid);


                   onTransportSelected();

                return root;
            }


            private void onStartClick(){
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(travelMode) {
                        inTravel=true;
                        total.setText("0 m");
                        MyService.Start();
                    }
                    else{
                        Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Please select a mode of transport",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 200);
                        toast.show();
                    }
                }
            });

            }

            private void onStopClick(){
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inTravel) {
                    totalDistance = MyService.getDistance();
                    total.setText(String.valueOf(totalDistance) + " m");
                    MyService.Stop();
                    int pointsAwarded = (int) (Math.ceil(totalDistance * pointsPerMeter[selectedTravelMode]));
                    User.getInstance().addPoints(pointsAwarded);
                    Toast toast=Toast.makeText(getActivity().getApplicationContext(),"You have been awarded " + pointsAwarded + " points" ,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 200);
                    toast.show();
                    inTravel = false;


                }
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

            private void onTransportSelected(){
                for (int i = 0; i < travelGrid.getChildCount(); i++) {
                    final CardView cardView = (CardView) travelGrid.getChildAt(i);
                    final int finalI = i;
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!inTravel) {
                                selectedTravelMode=finalI;
                                for (int i = 0; i < travelGrid.getChildCount(); i++) {
                                    CardView cards = (CardView) travelGrid.getChildAt(i);
                                    cards.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                                }
                                cardView.setCardBackgroundColor(Color.parseColor("#0000ff"));
                                travelMode = true;
                            }
                        }
                    });
                }
            }
}



