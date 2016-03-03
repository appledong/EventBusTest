package com.dong.eventbustest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dong.eventbustest.R;
import com.dong.eventbustest.message.StickyMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dongdz on 2016/3/2.
 * common message:生命周期是post发出后到最后一个subscribe处理以后
 * sticky message：最后一个subscribe处理以后不会销毁掉而是一直保存这条消息（发出的最后一条）
 * 直到用户调用remove方法移除为止。在remove之前用户随时可以用当条消息及其附带的数据
 * 2.x和3.0的声明方式不一样，2.x是在注册的时候使用registersticky（）实现，而3.0则是利用
 * subscribe的注解实现
 */
public class StickyActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StickyMessage stickymessage = EventBus.getDefault().getStickyEvent(StickyMessage.class);
        if(stickymessage != null && stickymessage.list != null && stickymessage.list.size() > 0){
            for (String s:stickymessage.list) {
                Log.e("dongdianzhou","onDestroy:s:" + s);
            }
        }
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onStickyMessage(StickyMessage stickymessage){
        if(stickymessage != null && stickymessage.list != null && stickymessage.list.size() > 0){
            for (String s:stickymessage.list) {
                Log.e("dongdianzhou","onStickyMessage:s:" + s);
            }
        }
    }

    @OnClick(R.id.button_sticky)
    public void onClick(){
        StickyMessage stickyMessage = new StickyMessage("StickyMessage");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("haha" + i);
        }
        stickyMessage.list = list;
        EventBus.getDefault().postSticky(stickyMessage);
    }
}
