package com.dong.eventbustest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dong.eventbustest.R;
import com.dong.eventbustest.message.BaseMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dongdz on 2016/3/2.
 * 综合来说3.0的eventbus和2.x的用法基本一致，只是引入了注解的模式
 * 1. 创建消息体
 * 2. 注册subscribe即可
 * 3. 在合适的时机里面post消息
 * 2和3的关联由1决定
 * observe和suscribe之间的数据通过1声明和传递
 * 使用场景：扩展的观察者模式，可以提供多个subscribe，
 * 同时也可以在多个地方post消息。
 */
public class CommonActivity extends AppCompatActivity {

    @Bind(R.id.textview_display)
    TextView textview_display;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     *
     * @param baseMessage
     * Subscribe注解：必须手动引入当前的注解类，不能够自动引入
     * 2. 相比较2.x来说，subscribe的方法名可以随便书写，不需要严格按照eventbus的要求。
     */
    @Subscribe
    public void onMessageEvent(BaseMessage baseMessage) {
        i++;
        textview_display.setText(baseMessage.message + i);
    }

    @OnClick(R.id.button_sendmessage)
    public void onClick() {
        EventBus.getDefault().post(new BaseMessage("测试eventbus发送基本消息"));
    }
}
