package com.dev.yarulin.smstotime.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yarulin on 27.12.2017.
 */

public class SmsModel {
    private long messageId;
    private long contactId;
    private String address;
    private long timestamp;
    private String body;



    public SmsModel(long messageId, long contactId, String address, long timestamp, String body ){
        this.messageId = messageId;
        this.contactId = contactId;
        this.address = address;
        this.timestamp = timestamp;
        this.body = body;
    }

    @Override
    public String toString(){
        return  "tel:" + address + " sms:" + body + " time:" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(timestamp*1000));
    }

    public long getMessageId() {
        return messageId;
    }

    public long getContactId() {
        return contactId;
    }

    public String getAddress() {
        return address;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBody() {
        return body;
    }



}
