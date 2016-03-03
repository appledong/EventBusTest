package com.dong.eventbustest.message;

import java.util.List;

/**
 * Created by dongdz on 2016/3/2.
 */
public class StickyMessage {

    public String message;

    public List<String> list;

    public StickyMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
