package com.ruinkami.overrider.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.ruinkami.overrider.R;

import java.lang.reflect.Field;

/**
 * Created by ruinkami on 2015/7/24.
 */

public class GameView extends View {
    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    Bitmap mBitmap1, mBitmap2;
    int screenWidth, screenHeight;
    int cardWidth, cardHeight;
    int phaseWidth, phaseHeight;
    int deckWidth, deckHeight;
    int handCardListWidth, handCardListHeight;
    int avatarLength;
    int infoWidth, infoHeight;
    int marginDefault;

    public GameView(Context context) {
        super(context);
        initialView(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialView(context);
    }

    public void initialView(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - getStatusBarHeight(context);

        cardWidth = (int) getResources().getDimension(R.dimen.card_width);
        cardHeight = (int) getResources().getDimension(R.dimen.card_height);
        phaseWidth = (int) getResources().getDimension(R.dimen.phase_width);
        phaseHeight = (int) getResources().getDimension(R.dimen.phase_height);
        deckWidth = (int) getResources().getDimension(R.dimen.deck_width);
        deckHeight = (int) getResources().getDimension(R.dimen.deck_height);
        handCardListWidth = (int) getResources().getDimension(R.dimen.hand_card_list_width);
        handCardListHeight = (int) getResources().getDimension(R.dimen.hand_card_list_height);
        avatarLength = (int) getResources().getDimension(R.dimen.avatar_length);
        infoWidth = (int) getResources().getDimension(R.dimen.info_width);
        infoHeight = (int) getResources().getDimension(R.dimen.info_height);
        marginDefault = (int) getResources().getDimension(R.dimen.margin_default);

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
        mBitmap1 = resizeBitmap(mBitmap1, cardWidth, cardHeight);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.c2);

        mPaint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);

        //绘制底部场地
        mPaint.setColor(0xFFDDDDDD);
        canvas.drawRect(0, 0, screenWidth, screenHeight, mPaint);

        //绘制中间的游戏阶段区域
        mPaint.setColor(0xFFB2C3B2);
        canvas.drawRect(screenWidth / 2 - phaseWidth / 2, screenHeight / 2 - phaseHeight / 2,
                screenWidth / 2 + phaseWidth / 2, screenHeight / 2 + phaseHeight / 2, mPaint);

        //绘制卡牌放置区域
        mPaint.setColor(0xFFC3AB85);
        //player2
        canvas.drawRect(screenWidth / 2 - cardWidth / 2, screenHeight / 2 - phaseHeight / 2 - marginDefault - cardHeight,
                screenWidth / 2 + cardWidth / 2, screenHeight / 2 - phaseHeight / 2 - marginDefault, mPaint);
        canvas.drawRect(screenWidth / 2 - cardWidth * 3 / 2 - marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault - cardHeight,
                screenWidth / 2 - cardWidth / 2 - marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault, mPaint);
        canvas.drawRect(screenWidth / 2 + cardWidth / 2 + marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault - cardHeight,
                screenWidth / 2 + cardWidth * 3 / 2 + marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault, mPaint);
        //player1
        canvas.drawRect(screenWidth / 2 - cardWidth / 2, screenHeight / 2 + phaseHeight / 2 + marginDefault,
                screenWidth / 2 + cardWidth / 2, screenHeight / 2 + phaseHeight / 2 + cardHeight + marginDefault, mPaint);
        canvas.drawRect(screenWidth / 2 - cardWidth * 3 / 2 - marginDefault, screenHeight / 2 + phaseHeight / 2 + marginDefault,
                screenWidth / 2 - cardWidth / 2 - marginDefault, screenHeight / 2 + phaseHeight / 2 + cardHeight + marginDefault, mPaint);
        canvas.drawRect(screenWidth / 2  + cardWidth / 2 + marginDefault, screenHeight / 2 + phaseHeight / 2 + marginDefault,
                screenWidth / 2 + cardWidth * 3 / 2 + marginDefault, screenHeight / 2 + phaseHeight / 2 + cardHeight + marginDefault, mPaint);

        //绘制手卡区域
        mPaint.setColor(0xFFC36E81);
        //player2
        canvas.drawRect(0, 0,
                handCardListWidth, handCardListHeight, mPaint);
        //player1
        canvas.drawRect(screenWidth - handCardListWidth, screenHeight - handCardListHeight,
                screenWidth, screenHeight, mPaint);


        //绘制个人头像区域
        mPaint.setColor(0xFF68A9C3);
        //player2
        canvas.drawRect(screenWidth - avatarLength, 0,
                screenWidth, avatarLength, mPaint);
        //player1
        canvas.drawRect(0, screenHeight - avatarLength,
                avatarLength, screenHeight, mPaint);

        //绘制个人HP区域
        mPaint.setColor(0xFF5CC3A1);
        //player2
        canvas.drawRect(screenWidth - infoWidth, avatarLength,
                screenWidth, avatarLength + infoHeight, mPaint);
        //player1
        canvas.drawRect(0, screenHeight - avatarLength - infoHeight,
                infoWidth, screenHeight - avatarLength, mPaint);

        //绘制两侧的卡组区域
        mPaint.setColor(0xFFA39BC4);
        //player2
        canvas.drawRect(0, avatarLength + infoHeight - deckHeight,
                deckWidth, avatarLength + infoHeight, mPaint);
        //player1
        canvas.drawRect(screenWidth - deckWidth, screenHeight - avatarLength - infoHeight,
                screenWidth, screenHeight - avatarLength - infoHeight + deckHeight, mPaint);



        //canvas.drawBitmap(mBitmap1, 50, 50, mPaint);

        //canvas.drawBitmap(mBitmap2, 100, 450, mPaint);
        /*
        mPaint.setColor(Color.BLUE);
        canvas.drawText("啊", 10, 120, mPaint);
        */
    }

    public Bitmap resizeBitmap(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
