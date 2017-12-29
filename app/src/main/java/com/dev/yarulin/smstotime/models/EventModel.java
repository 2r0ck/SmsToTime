package com.dev.yarulin.smstotime.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yarulin on 28.12.2017.
 */

public class EventModel {
    private final UUID settingsId;
    private final UUID eventId;
    Date dateStamp;
    List<SmsModel> smss;

    public EventModel(UUID settingsId, Date dateStamp) {
        this.settingsId = settingsId;
        this.dateStamp = dateStamp;

        eventId = UUID.randomUUID();
        smss = new ArrayList<>();
    }

    public UUID getSettingsId() {
        return settingsId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public Date getDateStamp() {
        return dateStamp;
    }


    public void AddSms(SmsModel sms){
        smss.add(sms);
    }

    public boolean Equals(UUID setId,  Date dStamp){
         return  setId == settingsId && dStamp ==dateStamp;
    }
}
