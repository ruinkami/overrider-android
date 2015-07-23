package com.ruinkami.overrider.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ruinkami.overrider.R;

/**
 * Created by ruinkami on 2015/7/15.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchParams(savedInstanceState);
        initView();
    }

    private void initView() {
        startBtn = (Button) findViewById(R.id.playBtn);
        startBtn.setOnClickListener(this);
    }

    private void fetchParams(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playBtn:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("overrider://game"));
                startActivity(intent);
                break;
        }
    }
}
