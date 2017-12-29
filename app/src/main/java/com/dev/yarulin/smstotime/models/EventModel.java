package com.dev.yarulin.smstotime.decorators;

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
    List<SmsModel> sms;

    EventModel(UUID settingsId,Date dateStamp,List<SmsModel> sms ){
        this.settingsId = settingsId;
        this.dateStamp = dateStamp;
        this.sms = sms;
        eventId = UUID.randomUUID();
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
}
