package com.ruinkami.overrider.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruinkami.overrider.R;


/**
 * Created by ruinkami on 2015/7/15.
 */
public class GameActivity extends BaseActivity implements View.OnTouchListener,View.OnClickListener {
    protected final int P1_CELL_1 = 11;
    protected final int P1_CELL_2 = 12;
    protected final int P1_CELL_3 = 13;
    protected final int P2_CELL_1 = 21;
    protected final int P2_CELL_2 = 22;
    protected final int P2_CELL_3 = 23;

    private LinearLayout layout_card;
    private LinearLayout layout_p1_cell_1;
    private LinearLayout layout_p1_cell_2;
    private LinearLayout layout_p1_cell_3;
    private LinearLayout layout_p2_cell_1;
    private LinearLayout layout_p2_cell_2;
    private LinearLayout layout_p2_cell_3;
    private LinearLayout layout_p1_deck;
    private AbsoluteLayout layout_card_layer;
    private TextView tv_log;
    private TextView tv_cell;

    int initLeft;
    int initTop;
    int initRight;
    int initBottom;
    int lastX;
    int lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        layout_p1_cell_1 = (LinearLayout) this.findViewById(R.id.layout_p1_cell_1);
        layout_p1_cell_2 = (LinearLayout) this.findViewById(R.id.layout_p1_cell_2);
        layout_p1_cell_3 = (LinearLayout) this.findViewById(R.id.layout_p1_cell_3);
        layout_p2_cell_1 = (LinearLayout) this.findViewById(R.id.layout_p2_cell_1);
        layout_p2_cell_2 = (LinearLayout) this.findViewById(R.id.layout_p2_cell_2);
        layout_p2_cell_3 = (LinearLayout) this.findViewById(R.id.layout_p2_cell_3);

        layout_card_layer = (AbsoluteLayout) this.findViewById(R.id.card_layer);

        layout_p1_deck = (LinearLayout) this.findViewById(R.id.layout_p1_deck);
        layout_p1_deck.setOnClickListener(this);

        tv_log = (TextView) this.findViewById(R.id.tv_log);
        tv_cell = (TextView) this.findViewById(R.id.tv_cell);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                initLeft = v.getLeft();
                initTop = v.getTop();
                initRight = v.getRight();
                initBottom = v.getBottom();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                int centerX = (v.getLeft()+v.getRight())/2;
                int centerY = (v.getTop()+v.getBottom())/2;
                tv_log.setText("centerX:"+centerX+", centerY:"+centerY);
                int position = checkPosition(centerX, centerY);
                LinearLayout layout=null;
                switch(position){
                    case P1_CELL_1:
                        layout = layout_p1_cell_1;
                        break;
                    case P1_CELL_2:
                        layout = layout_p1_cell_2;
                        break;
                    case P1_CELL_3:
                        layout = layout_p1_cell_3;
                        break;
                    case P2_CELL_1:
                        layout = layout_p2_cell_1;
                        break;
                    case P2_CELL_2:
                        layout = layout_p2_cell_2;
                        break;
                    case P2_CELL_3:
                        layout = layout_p2_cell_3;
                        break;
                    default:
                        break;
                }
                if(layout!=null) {
                    tv_cell.setText(layout.getLeft()+","+layout.getTop()+","+layout.getRight()+","+layout.getBottom());
                    v.layout(layout.getLeft(), layout.getTop(), layout.getRight(), layout.getBottom());
                }else{
                    v.layout(initLeft, initTop, initRight, initBottom);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_p1_deck:
                LinearLayout linearLayout = new LinearLayout(this);
                layout_card_layer.addView(linearLayout);
                LayoutParams lp_linearLayout = linearLayout.getLayoutParams();
                lp_linearLayout.width = getResources().getDimensionPixelSize(R.dimen.layout_card_field_width);
                lp_linearLayout.height = getResources().getDimensionPixelSize(R.dimen.layout_card_field_height);
                linearLayout.setBackgroundColor(0xccc33719);
                linearLayout.setClickable(true);
                linearLayout.setLayoutParams(lp_linearLayout);
                linearLayout.setOnTouchListener(this);
                break;
            default:
                break;
        }
    }

    public int checkPosition(int X, int Y){
        if(checkInLayout(X, Y, layout_p1_cell_1)){
            return P1_CELL_1;
        }
        if(checkInLayout(X, Y, layout_p1_cell_2)){
            return P1_CELL_2;
        }
        if(checkInLayout(X, Y, layout_p1_cell_3)){
            return P1_CELL_3;
        }
        if(checkInLayout(X, Y, layout_p2_cell_1)){
            return P2_CELL_1;
        }
        if(checkInLayout(X, Y, layout_p2_cell_2)){
            return P2_CELL_2;
        }
        if(checkInLayout(X, Y, layout_p2_cell_3)){
            return P2_CELL_3;
        }

        return 0;
    }

    public boolean checkInLayout(int X, int Y, LinearLayout layout){
        if(X > layout.getLeft() && X < layout.getRight()
                && Y > layout.getTop() && Y < layout.getBottom()){
            return true;
        }
        return false;
    }
}
