package hgwxr.kotlin.com.myeventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hgwxr.kotlin.com.mylibrary.MyEventBus;
import hgwxr.kotlin.com.mylibrary.MySubscribe;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });
        EventBus.getDefault().register(this);
        MyEventBus.instance().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @MySubscribe
    public void onMyMessageEvent(MessageEvent event) {
        Toast.makeText(this,"My Event Toast",Toast.LENGTH_SHORT).show();
        /* Do something */
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this,"event Toast",Toast.LENGTH_SHORT).show();
        /* Do something */
    }

    @Override
    protected void onStop() {
        super.onStop();
//        MyEventBus.instance().unRegister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
