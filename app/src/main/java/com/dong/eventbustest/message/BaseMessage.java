package com.dong.eventbustest.message;

/**
 * Created by dongdz on 2016/3/2.
 */
public class BaseMessage {

    public String message;

    public BaseMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
