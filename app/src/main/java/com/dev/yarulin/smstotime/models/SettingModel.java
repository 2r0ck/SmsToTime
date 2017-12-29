package com.dev.yarulin.smstotime.decorators;

import java.util.UUID;

/**
 * Created by Yarulin on 27.12.2017.
 */

public class SettingModel {
    private UUID GroupId;
    private String address;
    private int color;
    private String dateTimeFormat;

    public SettingModel(int color, String address,String dateTimeFormat){
        this.setColor(color);
        this.setAddress(address);
        this.setDateTimeFormat(dateTimeFormat);
        GroupId = UUID.randomUUID();
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

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public UUID getGroupId() {
        return GroupId;
    }
}
