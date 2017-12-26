package com.dev.yarulin.smstotime;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     private  final String TAG = "======MainActivity=======";
     private final int REQUEST_CODE_READ_SMS = 1;
      private MaterialCalendarView smsCal;
    private static boolean READ_SMS_GRANTED =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsCal = (MaterialCalendarView) findViewById(R.id.calendarView);
        smsCal.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        Log.d(TAG,"onCreate end");

    }
private int day = 20;
    public void OnTestClick(View view) {
        Log.d(TAG, "OnTestClick start");
        day++;
        Log.d(TAG, "Day=" + day);
        Calendar c = Calendar.getInstance();
        c.set(2017, 11, day);
        //smsCal.setSelectedDate(c);
        if(day%2==0){
            smsCal.setSelectionColor(R.color.colorOne);
        }else{
            smsCal.setSelectionColor(R.color.colorTwo);
        }

        smsCal.setSelectedDate(c);
        Log.d(TAG, "OnTestClick end");
    }

    public void OnTestReadClick(View view) {
        Log.d(TAG, "<-OnTestReadClick->");
        if(READ_SMS_GRANTED) {


            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    String msgData = "";
                    for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    }
                    Log.d(TAG, msgData);
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "Empty box, no SMS");
            }
        }
        else{
            Log.d(TAG, "READ_SMS_GRANTED = false");
        }

    }

    public void OnGetGrantClick(View view) {
        Log.d(TAG, "<-OnGetGrClick->");
        if(!READ_SMS_GRANTED){
            int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
            if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
                READ_SMS_GRANTED = true;
                Log.d(TAG, "It's ok! READ_SMS_GRANTED = true");
            }
            else{
                // вызываем диалоговое окно для установки разрешений
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_READ_SMS);
            }

        }else{
            Log.d(TAG, "READ_SMS_GRANTED = true");
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        Log.d(TAG, "<-onRequestPermissionsResult->");
        switch (requestCode){
            case REQUEST_CODE_READ_SMS:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    READ_SMS_GRANTED = true;
                }
        }
        if(READ_SMS_GRANTED){
            Log.d(TAG, "Done! READ_SMS_GRANTED = true");
        }
        else{
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }
}
