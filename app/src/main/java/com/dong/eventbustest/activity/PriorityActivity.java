package com.dong.eventbustest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.dong.eventbustest.R;
import com.dong.eventbustest.message.PriorityMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dongdz on 2016/3/2.
 * 对于多个subscribe，不设置其priority，触发的优先级是注册的先后（代码执行的顺序有关）
 * 对于多个subscribe，设置了优先级，优先级越高将优先执行
 * 对比2.x和3.0eventbus，设置优先级的方式不太一样，2.x是在注册subscribe的时候指定优先级
 * 3.0是在subscribe注解注明
 */
public class PriorityActivity  extends AppCompatActivity {

    @Bind(R.id.textview_display_priority1)
    TextView textview_display_priority1;

    @Bind(R.id.textview_display_priority2)
    TextView textview_display_priority2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(priority = 1)
    public void operationMessage1(PriorityMessage priorityMessage){
        Log.e("dongdianzhou","走了operationMessage1");
        textview_display_priority1.setText(priorityMessage.message);
    }

    @Subscribe(priority = 2)
    public void operationMessage2(PriorityMessage priorityMessage){
        Log.e("dongdianzhou","走了operationMessage2");
        textview_display_priority2.setText(priorityMessage.message);
    }

    @OnClick(R.id.button_sendmessage)
    public void onClick(){
        EventBus.getDefault().post(new PriorityMessage("优先级别消息"));
    }
}
