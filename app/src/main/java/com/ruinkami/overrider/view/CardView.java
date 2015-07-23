package com.ruinkami.overrider.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruinkami.overrider.R;

/**
 * TODO: document your custom view class.
 */
public class CardView extends LinearLayout {
    private ImageView im_card_lv;
    private ImageView im_card_pic;
    private TextView tv_card_title;
    private TextView tv_card_info;

    public CardView(Context context, AttributeSet attrs) { super(context, attrs); }

    public CardView(Context context) { super(context); }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        im_card_lv = (ImageView) this.findViewById(R.id.card_lv);
        im_card_pic = (ImageView) this.findViewById(R.id.card_pic);
        tv_card_title = (TextView) this.findViewById(R.id.card_pic);
        tv_card_info = (TextView) this.findViewById(R.id.card_info);
    }

}
