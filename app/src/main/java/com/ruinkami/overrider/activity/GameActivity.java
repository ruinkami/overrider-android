package com.ruinkami.overrider.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ruinkami.overrider.R;
import com.ruinkami.overrider.view.GameView;


/**
 * Created by ruinkami on 2015/7/15.
 */
public class GameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        GameView mv= new GameView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(mv, lp);

    }

}
