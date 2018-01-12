package com.dev.yarulin.smstotime;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.dev.yarulin.smstotime.adapters.EventAdapter;
import com.dev.yarulin.smstotime.decorators.EventDecorator;
import com.dev.yarulin.smstotime.models.DateFormatPattern;
import com.dev.yarulin.smstotime.models.EventModel;
import com.dev.yarulin.smstotime.models.SettingModel;
import com.dev.yarulin.smstotime.models.SmsModel;
import com.dev.yarulin.smstotime.storages.SettingsStorage;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final Uri SMS_INBOX_CONTENT_URI = Uri.parse("content://sms/inbox");
    private static final String SORT_ORDER = "date DESC";
    private  final String TAG = "======MainActivity=======";
    private final int REQUEST_CODE_READ_SMS = 1;
    private MaterialCalendarView smsCal;
    private List<EventModel> currentEvents = new ArrayList<>();

    private List<EventModel> events;

    private static boolean READ_SMS_GRANTED =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsCal = (MaterialCalendarView) findViewById(R.id.calendarView);
        smsCal.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        smsCal.setSelectionColor(getResources().getColor(R.color.selectColor,null));
        SettingsStorage.TestInit(getResources());
        Run();
        EventAdapter ea = new EventAdapter(this,R.layout.sms_item,currentEvents,SettingsStorage.CURRENT.SETTINGS);
        ListView eventList = (ListView) findViewById(R.id.eventList);
        eventList.setAdapter(ea);

        smsCal.setOnDateChangedListener((MaterialCalendarView widget, CalendarDay date, boolean selected) -> {
            ea.clear();
            Log.d(TAG, "<---onDateChanged--->" );
            Log.d(TAG, "date: "+ date );
            Log.d(TAG, "selected: "+ selected );
            if(events!=null){
                Log.d(TAG, "/*************/" );
                for (EventModel ev : events){
                    if(ev.getDateStamp().equals(date.getDate())){
                        ev.getMessages().forEach(x->{
                            Log.d("PHONE:"+x.getAddress(), x.getBody() );
                        });
                        //add ev
                        ea.add(ev);
                    }
                }
                Log.d(TAG, "/***********/" );
            }

            justifyListViewHeightBasedOnChildren(eventList);
        });


        Log.d(TAG,"onCreate end");
    }

    private static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
    private int day = 19;
    public void OnTestClick(View view) {
        Log.d(TAG, "OnTestClick start");
//        day++;
//        Log.d(TAG, "Day=" + day);
//        Calendar c = Calendar.getInstance();
//        c.set(2017, 11, day);
//        //smsCal.setSelectedDate(c);
//        if(day%2==0){
//            smsCal.setSelectionColor(R.color.colorOne);
//        }else{
//            smsCal.setSelectionColor(R.color.colorTwo);
//        }
//
//        smsCal.setSelectedDate(c);
        /************************/
//        Calendar c = Calendar.getInstance();
//        ArrayList<CalendarDay> dates1 = new ArrayList<>();
//        ArrayList<CalendarDay> dates2 = new ArrayList<>();
//        Resources res = getResources();
//
//        for (int i = 0; i < 5; i++) {
//            CalendarDay day = CalendarDay.from(c);
//            if(i>2){
//                dates1.add(day);
//            }else{
//                dates2.add(day);
//            }
//            c.add(Calendar.DATE, -2);
//        }
//        smsCal.addDecorators(new EventDecorator(
//                getResources().getColor(R.color.colorOne,null),dates1),
//                new EventDecorator(getResources().getColor(R.color.colorTwo,null),dates2));
        String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);
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

      /***********===========================================================================================****************/
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
            BuildHistory();
        }
        else{
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }

    public void OnRun(View view) {
        Log.d(TAG, "<-OnRun->");
        Run();
    }

    private void Run(){
        Log.d(TAG, "<-Run->");
        if (!READ_SMS_GRANTED) {
            int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
            if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
                READ_SMS_GRANTED = true;
                Log.d(TAG, "It's ok! READ_SMS_GRANTED = true");
            } else {
                // вызываем диалоговое окно для установки разрешений
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_READ_SMS);
            }
        }
        if (READ_SMS_GRANTED) {
            BuildHistory();
        }
    }

    private void BuildHistory(){
        Log.d(TAG, "<-BuildHistory->");
        List<SmsModel> allSms =  GetSms();

        Log.d(TAG, "<-events->");
        /*Build models*/
        events = new ArrayList<>();

        for (SettingModel set : SettingsStorage.CURRENT.SETTINGS){
            DateFormatPattern currentFormat = SettingsStorage.CURRENT.GetFormatById(set.getDatePatternId());
            for(SmsModel sms: allSms){
                if(sms.getAddress().equals(set.getAddress())){
                    Date smsDate = GetDateFromSmsBody(currentFormat,sms);
                    if(smsDate!=null) {
                        EventModel event = null;
                        if(events.stream().anyMatch(x->x.Equals(set.getSettingsId(),smsDate))){
                            event = events.stream().filter(x->x.Equals(set.getSettingsId(),smsDate)).findFirst().get();
                        }else {
                            event = new EventModel(set.getSettingsId(), smsDate);
                            events.add(event);
                            Log.d(TAG, "Add  event:" + event.getSettingsId() + " " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(event.getDateStamp()));
                        }
                        event.AddSms(sms);
                        Log.d(TAG, sms.toString() + " event:" + event.getSettingsId() + " " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(event.getDateStamp()));
                    }
                }
            }
        }
        /*Draw decorators*/
        List<EventDecorator> decorators = new ArrayList<>();
        for (SettingModel set : SettingsStorage.CURRENT.SETTINGS){
            EventDecorator dec = new EventDecorator(set.getSettingsId(),set.getColor());
            Date[] eventDates =  events.stream().filter(x->x.getSettingsId() == set.getSettingsId()).map(x->x.getDateStamp()).toArray(Date[]::new);
            for (Date d : eventDates){
                dec.addDate(d);
            }
            if(eventDates.length>0){
                decorators.add(dec);
            }
        }
        smsCal.addDecorators(decorators);
    }

    private List<SmsModel> GetSms(){
        Log.d(TAG, "<-GetSms->");

        List<SmsModel> result = new ArrayList<>();
        if (READ_SMS_GRANTED) {
            Cursor cursor =  getContentResolver().query(
                    SMS_INBOX_CONTENT_URI,
                    new String[] { "_id", "thread_id", "address", "person", "date", "body" },
                    null,
                    null,
                    SORT_ORDER);
            int count = 0;
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) { // must check the result to prevent exception
                        do {
                            // String[] columns = cursor.getColumnNames();
                            // for (int i=0; i<columns.length; i++) {
                            // Log.v("columns " + i + ": " + columns[i] + ": " + cursor.getString(i));
                            // }
                            long messageId = cursor.getLong(0);
                            long threadId = cursor.getLong(1);
                            String address = cursor.getString(2);
                            long contactId = cursor.getLong(3);
                            String contactId_string = String.valueOf(contactId);
                            long timestamp = cursor.getLong(4);
                            String body = cursor.getString(5);

                            SmsModel smsMessage = new SmsModel(messageId,contactId,address,timestamp,body);
                            result.add(smsMessage);
                            Log.d(TAG, smsMessage.toString());
                        } while (cursor.moveToNext());
                    } else {
                        Log.d(TAG, "Empty box, no SMS");
                    }
                } finally {
                    cursor.close();
                }
            }


        }
        return result;
    }

    private Date GetDateFromSmsBody(DateFormatPattern format, SmsModel sms) {
        Date result = null;
        DateFormat df = new SimpleDateFormat(format.getDesc());
        if(sms!=null && sms.getBody()!=null) {
            Matcher m = Pattern.compile(format.getRegexPattern()).matcher(sms.getBody());
            while (m.find()) {
                  String findObject = m.group();
                  try{
                      result =  df.parse(findObject);
                      if(result!=null) {
                          break;
                      }
                  }
                  catch (ParseException pe) {
                       Log.e(TAG,pe.getMessage());
                  }
            }
        }
        return result;
    }
}
