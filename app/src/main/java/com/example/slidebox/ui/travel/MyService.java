package com.example.slidebox.ui.travel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.slidebox.R;

public class MyService extends Service {
    int NOTIFICATION_ID = 001;
    private final String CHANNEL_ID = "100";
    private Status state = Status.STOPPING;
    float distance;
    Location location = null;

    public MyService() {
    }

    public final IBinder binder = new MyBinder();

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void onCreate(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(MyService.this, TravelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Walking")
                .setContentText("Back to main page")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(NOTIFICATION_ID,	mBuilder.build());
        super.onCreate();

    }
    public float getDistance(){
        return distance;
    }


    public enum Status{
        RUNNING,
        STOPPING
    }
    public Status getState(){
        return state;
    }

    public void Start() {
        state = Status.RUNNING;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            Log.d("travel", e.toString());
        }
        ;
        MyLocationListener locationListener = new MyLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5, // minimum time interval between updates
                    5, // minimum distance between updates, in metres
                    locationListener);
        } catch (SecurityException e) {
            Log.d("travel", e.toString());
        }
    }
    public void Stop(){
        state = Status.STOPPING;

        Log.d("travel", "stop " + distance);
        Reset();
    }

    public void onDestroy(){
        super.onCreate();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public class MyLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            if(state == Status.RUNNING){
                if(distance == 0){
                    try {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } catch (SecurityException e) {
                        Log.d("travel", e.toString());
                    }
                    Update(location);
                }else{
                    Update(location);
                }
            }
            Log.d("travel", location.getLatitude() + " " + location.getLongitude());
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // information about the signal, i.e. number of satellites
            Log.d("travel", "onStatusChanged: " + provider + " " + status);
        }
        @Override
        public void onProviderEnabled(String provider) {
            // the user enabled (for example) the GPS
            Log.d("travel", "onProviderEnabled: " + provider);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // the user disabled (for example) the GPS
            Log.d("travel", "onProviderDisabled: " + provider);
        }
    }

    private void Update(Location otherLocation){

        distance = distance + location.distanceTo(otherLocation);
        location = otherLocation;
        Log.d("travel", "onProviderDisabled: " + distance);
    }
    private void Reset(){
        distance = 0;
    }
}
