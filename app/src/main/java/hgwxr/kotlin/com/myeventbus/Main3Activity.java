package hgwxr.kotlin.com.myeventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import hgwxr.kotlin.com.mylibrary.MyEventBus;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        findViewById(R.id.eventBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent());
            }
        });
        findViewById(R.id.my_eventBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyEventBus.instance().post(new MessageEvent());
            }
        });
    }
}
