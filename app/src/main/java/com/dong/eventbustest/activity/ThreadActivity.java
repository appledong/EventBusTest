package com.dong.eventbustest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.dong.eventbustest.R;
import com.dong.eventbustest.message.BgThreadMessage;
import com.dong.eventbustest.message.PostThreadMessage;
import com.dong.eventbustest.message.SyscThreadMessage;
import com.dong.eventbustest.message.ThreadMessage;
import com.dong.eventbustest.message.UIThreadMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dongdz on 2016/3/2.
 * EventBus提供了四种线程模式：
 * UIthread（UI线程）:无论消息来自那里都在UI线程里面执行
 * BackgroundThread：无论消息来自那里都是在后台线程里面执行（此时若来自线程则继续在线程里执行，若来自UI则新建一后台线程执行）
 * PostThread：suscribe的逻辑执行和消息发送的线程保持一致
 * SyscThread：无论消息来自那里都是新建一个线程去执行
 * 2.x和3.0相比：线程的声明也有变化,2.x是通过eventbus定义好的api实现，3.0是通过subscribe注解的参数进行设置
 */
public class ThreadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostMessage(PostThreadMessage postThreadMessage) {
        Log.e("dongdianzhou", "onPostMessage:当前的线程是:" + Thread.currentThread().getId());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onSyscMessage(SyscThreadMessage syscThreadMessage) {
        Log.e("dongdianzhou", "onSyscMessage:当前的线程是:" + Thread.currentThread().getId());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBGMessage(BgThreadMessage bgThreadMessage) {
        Log.e("dongdianzhou", "onBGMessage:当前的线程是:" + Thread.currentThread().getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIMessage(UIThreadMessage uiThreadMessage) {
        Log.e("dongdianzhou", "onUIMessage:当前的线程是:" + Thread.currentThread().getId());
    }

    @OnClick({R.id.button_sendmessage_thread_bg,R.id.button_sendmessage_ui_bg
    ,R.id.button_sendmessage_thread_post,R.id.button_sendmessage_ui_post
    ,R.id.button_sendmessage_thread_sysc,R.id.button_sendmessage_ui_sysc
    ,R.id.button_sendmessage_thread_ui,R.id.button_sendmessage_ui_ui})
    public void onButtonClick(Button button){
        Log.e("dongdianzhou","onButtonClick:当前UI线程是：" + Thread.currentThread().getId());
        switch (button.getId()){
            case R.id.button_sendmessage_thread_bg:
                sendThreadMessage(new BgThreadMessage("BgThreadMessage"));
                break;
            case R.id.button_sendmessage_thread_post:
                sendThreadMessage(new PostThreadMessage("PostThreadMessage"));
                break;
            case R.id.button_sendmessage_thread_sysc:
                sendThreadMessage(new SyscThreadMessage("SyscThreadMessage"));
                break;
            case R.id.button_sendmessage_thread_ui:
                sendThreadMessage(new UIThreadMessage("UIThreadMessage"));
                break;
            case R.id.button_sendmessage_ui_bg:
                sendUIMessage(new BgThreadMessage("BgThreadMessage"));
                break;
            case R.id.button_sendmessage_ui_post:
                sendUIMessage(new PostThreadMessage("PostThreadMessage"));
                break;
            case R.id.button_sendmessage_ui_sysc:
                sendUIMessage(new SyscThreadMessage("SyscThreadMessage"));
                break;
            case R.id.button_sendmessage_ui_ui:
                sendUIMessage(new UIThreadMessage("UIThreadMessage"));
                break;
        }
    }

    private void sendUIMessage(final ThreadMessage threadMessage){
        EventBus.getDefault().post(threadMessage);
    }

    private void sendThreadMessage(final ThreadMessage threadMessage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("dongdianzhou",threadMessage + ":当前UI线程是：" + Thread.currentThread().getId());
                EventBus.getDefault().post(threadMessage);
            }
        }).start();
    }
}
