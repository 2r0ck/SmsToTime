package com.dev.yarulin.smstotime.models;

import java.util.UUID;

/**
 * Created by Yarulin on 27.12.2017.
 */

public class SettingModel {
    private UUID settingsId;
    private String address;
    private int color;
    private int datePatternId;


    public SettingModel(int color, String address,int datePatternId){
        this.setColor(color);
        this.setAddress(address);
        this.setDatePatternId(datePatternId);
        settingsId = UUID.randomUUID();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public UUID getSettingsId() {
        return settingsId;
    }

    public int getDatePatternId() {
        return datePatternId;
    }

    public void setDatePatternId(int datePatternId) {
        this.datePatternId = datePatternId;
    }
}
