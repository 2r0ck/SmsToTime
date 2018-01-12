package com.dev.yarulin.smstotime.adapters;

import android.content.Context;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.dev.yarulin.smstotime.R;
import com.dev.yarulin.smstotime.models.EventModel;
import com.dev.yarulin.smstotime.models.SettingModel;

import java.util.List;

/**
 * Created by Yarulin on 09.01.2018.
 */

public class EventAdapter extends ArrayAdapter<EventModel> {

    private Context context;
    private final int resource;
    @NonNull
    private final  List<EventModel>  events;
    private LayoutInflater inflater;
    private final List<SettingModel> settings;

    public EventAdapter(@NonNull Context context, int resource, List<EventModel> events, List<SettingModel> settings) {
        super(context, resource, events);
        this.context = context;
        this.resource = resource;
        this.events = events;
        this.inflater = LayoutInflater.from(context);
        this.settings = settings;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.resource, parent, false);
        TextView title = (TextView) view.findViewById(R.id.textTitle);
        ListView smsList = (ListView)  view.findViewById(R.id.sms_list);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.sms_container);

        EventModel em = this.events.get(position);
        if(em!=null){
            SettingModel set = settings.stream().filter(x->x.getSettingsId() == em.getSettingsId()).findFirst().get();
            if(set!=null) {
                title.setText(set.getAddress());
                String[] arr = em.getMessages().stream().map(x -> x.getBody()).toArray(String[]::new);
                int t = android.R.layout.simple_list_item_1;
                ArrayAdapter<String> adapter = new ArrayAdapter(this.context, t, arr);
                smsList.setAdapter(adapter);
                //set bkg
                container.setBackgroundColor(set.getColor());
                justifyListViewHeightBasedOnChildren(smsList);
            }
            else {
                //todo log err
                Log.e("EventAdapter","err1");
            }
        }else{
            //todo log err
            Log.e("EventAdapter","err2");
        }
        return view;

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
}
