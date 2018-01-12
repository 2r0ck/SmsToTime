package com.dev.yarulin.smstotime.storages;

import android.content.res.Resources;

import com.dev.yarulin.smstotime.R;
import com.dev.yarulin.smstotime.models.DateFormatPattern;
import com.dev.yarulin.smstotime.models.SettingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yarulin on 28.12.2017.
 */

public class SettingsStorage {
    public static SettingsStorage CURRENT;

    public List<SettingModel> SETTINGS;
    public List<DateFormatPattern> FORMATS;
    private Resources res;

     private SettingsStorage(){

     }

    public static void  TestInit(Resources res){
        CURRENT = new SettingsStorage();
        CURRENT.res = res;
        CURRENT.SETTINGS = CURRENT.GetTestSettings();
        CURRENT.FORMATS = CURRENT.GetFormats();
    }

    private List<DateFormatPattern> GetFormats (){
        List<DateFormatPattern> formats = new ArrayList<>();
        formats.add(new DateFormatPattern(1,"dd.MM.yyyy","(0*[1-9]|[12][0-9]|3[01])[.](0*[1-9]|1[012])[.]((19|20)\\d\\d|\\d\\d)"));
        formats.add(new DateFormatPattern(2,"MM/dd/yy","(0*[1-9]|1[012])[/](0*[1-9]|[12][0-9]|3[01])[/]((19|20)\\d\\d|\\d\\d)"));
        return formats;
    }

    private List<SettingModel> GetTestSettings(){
        List<SettingModel> settingModels=  new ArrayList<>();
        settingModels.add(new SettingModel(res.getColor(R.color.colorOne,null),"+16505556789",2));
        settingModels.add(new SettingModel(res.getColor(R.color.colorTwo,null),"6505551212",1));
        return  settingModels;
    }

    public DateFormatPattern GetFormatById(int idFormat){
        return  FORMATS.stream().filter(x->x.getId() == idFormat).findFirst().get();
    }

}
