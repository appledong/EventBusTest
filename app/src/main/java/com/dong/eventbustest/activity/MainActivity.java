package com.dong.eventbustest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dong.eventbustest.R;
import com.dong.eventbustest.message.ThreadMessage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 设置注解的参数时，注意单个参数使用（），多个参数使用{}
     * @param textView
     */
    @OnClick({R.id.textview_common, R.id.textview_thread, R.id.textview_priority,R.id.textview_sticky})
    public void onClick(TextView textView) {
        switch (textView.getId()) {
            case R.id.textview_common:
                startActivity(new Intent(MainActivity.this, CommonActivity.class));
                break;
            case R.id.textview_priority:
                startActivity(new Intent(MainActivity.this, PriorityActivity.class));
                break;
            case R.id.textview_thread:
                startActivity(new Intent(MainActivity.this, ThreadActivity.class));
                break;
            case R.id.textview_sticky:
                startActivity(new Intent(MainActivity.this, StickyActivity.class));
                break;
        }
    }
}
