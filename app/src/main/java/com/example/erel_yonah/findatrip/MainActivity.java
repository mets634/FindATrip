package com.example.erel_yonah.findatrip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private final static String ACTION = "ACTION_UPDATE";
    private final static String EXTRA = "EXTRA";

    private static Integer counter = 0;
    private BroadcastReceiver _refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getExtras().getString(EXTRA);
            ( (TextView) findViewById( R.id.showBroadcast ) ).setText(counter.toString() + ": " + text);
            counter++;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(_refreshReceiver, new IntentFilter(ACTION));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(_refreshReceiver);
    }
}
