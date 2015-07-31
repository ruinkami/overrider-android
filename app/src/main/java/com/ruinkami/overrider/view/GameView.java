package com.ruinkami.overrider.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

import com.ruinkami.overrider.R;

import java.lang.reflect.Field;

/**
 * Created by ruinkami on 2015/7/24.
 */

public class GameView extends SurfaceView implements Callback, View.OnTouchListener {
    //Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    private Bitmap mBitmap1, mBitmap2;
    private int screenWidth, screenHeight;
    private int cardWidth, cardHeight;
    private int phaseWidth, phaseHeight;
    private int deckWidth, deckHeight;
    private int handCardListWidth, handCardListHeight;
    private int avatarLength;
    private int infoWidth, infoHeight;
    private int marginDefault;
    private LoopThread thread;

    public GameView(Context context) {
        super(context);
        initialView(context);
        init(); //初始化,设置生命周期回调方法
        this.setOnTouchListener(this);
    }

    public void initialView(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - getStatusBarHeight(context);

        initSize();

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
        mBitmap1 = resizeBitmap(mBitmap1, cardWidth, cardHeight);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.c2);

        //mPaint = new Paint();
    }

    private void initSize() {
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
    }

    private Rect getFieldRect(){
        Rect r = new Rect(0, 0, screenWidth, screenHeight);
        return r;
    }

    private Rect getPhaseRect(){
        Rect r = new Rect(screenWidth / 2 - phaseWidth / 2, screenHeight / 2 - phaseHeight / 2,
                screenWidth / 2 + phaseWidth / 2, screenHeight / 2 + phaseHeight / 2);
        return r;
    }

    private Rect getCardRect(CardLocation cl){
        Rect r=null;
        switch(cl){
            case LeftTop:
                r = new Rect(screenWidth / 2 - cardWidth * 3 / 2 - marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault - cardHeight,
                        screenWidth / 2 - cardWidth / 2 - marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault);
                break;
            case MiddleTop:
                r = new Rect(screenWidth / 2 - cardWidth / 2, screenHeight / 2 - phaseHeight / 2 - marginDefault - cardHeight,
                        screenWidth / 2 + cardWidth / 2, screenHeight / 2 - phaseHeight / 2 - marginDefault);
                break;
            case RightTop:
                r = new Rect(screenWidth / 2 + cardWidth / 2 + marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault - cardHeight,
                        screenWidth / 2 + cardWidth * 3 / 2 + marginDefault, screenHeight / 2 - phaseHeight / 2 - marginDefault);
                break;
            case LeftBottom:
                r = new Rect(screenWidth / 2 - cardWidth * 3 / 2 - marginDefault, screenHeight / 2 + phaseHeight / 2 + marginDefault,
                        screenWidth / 2 - cardWidth / 2 - marginDefault, screenHeight / 2 + phaseHeight / 2 + cardHeight + marginDefault);
                break;
            case MiddleBottom:
                r = new Rect(screenWidth / 2 - cardWidth / 2, screenHeight / 2 + phaseHeight / 2 + marginDefault,
                        screenWidth / 2 + cardWidth / 2, screenHeight / 2 + phaseHeight / 2 + cardHeight + marginDefault);
                break;
            case RightBottom:
                r = new Rect(screenWidth / 2 + cardWidth / 2 + marginDefault, screenHeight / 2 + phaseHeight / 2 + marginDefault,
                        screenWidth / 2 + cardWidth * 3 / 2 + marginDefault, screenHeight / 2 + phaseHeight / 2 + cardHeight + marginDefault);
                break;
            default:
                break;
        }
        return r;
    }

    private Rect getHandCardRect(Player p){
        Rect r=null;
        switch(p){
            case Player1:
                r = new Rect(screenWidth - handCardListWidth, screenHeight - handCardListHeight,
                        screenWidth, screenHeight);
                break;
            case Player2:
                r = new Rect(0, 0,
                        handCardListWidth, handCardListHeight);
                break;
            default:
                break;
        }
        return r;
    }

    private Rect getAvatarRect(Player p){
        Rect r=null;
        switch(p){
            case Player1:
                r = new Rect(0, screenHeight - avatarLength,
                        avatarLength, screenHeight);
                break;
            case Player2:
                r = new Rect(screenWidth - avatarLength, 0,
                        screenWidth, avatarLength);
                break;
            default:
                break;
        }
        return r;
    }

    private Rect getHPRect(Player p){
        Rect r=null;
        switch(p){
            case Player1:
                r = new Rect(0, screenHeight - avatarLength - infoHeight,
                        infoWidth, screenHeight - avatarLength);
                break;
            case Player2:
                r = new Rect(screenWidth - infoWidth, avatarLength,
                        screenWidth, avatarLength + infoHeight);
                break;
            default:
                break;
        }
        return r;
    }

    private Rect getDeckRect(Player p){
        Rect r=null;
        switch(p){
            case Player1:
                r = new Rect(screenWidth - deckWidth, screenHeight - avatarLength - infoHeight,
                        screenWidth, screenHeight - avatarLength - infoHeight + deckHeight);
                break;
            case Player2:
                r = new Rect(0, avatarLength + infoHeight - deckHeight,
                        deckWidth, avatarLength + infoHeight);
                break;
            default:
                break;
        }
        return r;
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this); //设置Surface生命周期回调
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new LoopThread(holder, getContext());
        thread.isRunning = true;
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 执行绘制的绘制线程
     *
     * @author Administrator
     */
    class LoopThread extends Thread {

        SurfaceHolder surfaceHolder;
        Context context;
        boolean isRunning;
        float radius = 10f;
        Paint mPaint;

        public LoopThread(SurfaceHolder surfaceHolder, Context context) {

            this.surfaceHolder = surfaceHolder;
            this.context = context;
            isRunning = false;

            mPaint = new Paint();
            mPaint.setColor(Color.YELLOW);
            mPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning) {
                try {
                    synchronized (surfaceHolder) {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas != null) {
                            doDraw(canvas);
                        }
                        //通过它来控制帧数执行一次绘制后休息50ms
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null && surfaceHolder != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        public void doDraw(Canvas canvas) {
            //这个很重要，清屏操作，清楚掉上次绘制的残留图像
//            canvas.drawColor(Color.BLACK);
//            canvas.translate(200, 200);
//            canvas.drawCircle(0, 0, radius++, mPaint);
//            if(radius > 100){
//                radius = 10f;
//            }

            mPaint.setStyle(Paint.Style.FILL);

            //绘制底部场地
            mPaint.setColor(0xFFDDDDDD);
            canvas.drawRect(getFieldRect(), mPaint);

            //绘制中间的游戏阶段区域
            mPaint.setColor(0xFFB2C3B2);
            canvas.drawRect(getPhaseRect(), mPaint);

            //绘制卡牌放置区域
            //player2
            mPaint.setColor(0x77C3AB85);
            canvas.drawRect(getCardRect(CardLocation.LeftTop), mPaint);
            canvas.drawRect(getCardRect(CardLocation.MiddleTop), mPaint);
            canvas.drawRect(getCardRect(CardLocation.RightTop), mPaint);
            //player1
            mPaint.setColor(0xFFC3AB85);
            canvas.drawRect(getCardRect(CardLocation.LeftBottom), mPaint);
            canvas.drawRect(getCardRect(CardLocation.MiddleBottom), mPaint);
            canvas.drawRect(getCardRect(CardLocation.RightBottom), mPaint);

            //绘制手卡区域
            //player2
            mPaint.setColor(0x77C36E81);
            canvas.drawRect(getHandCardRect(Player.Player2), mPaint);
            //player1
            mPaint.setColor(0xFFC36E81);
            canvas.drawRect(getHandCardRect(Player.Player1), mPaint);


            //绘制个人头像区域
            //player2
            mPaint.setColor(0x7768A9C3);
            canvas.drawRect(getAvatarRect(Player.Player2), mPaint);
            mPaint.setColor(0xFF68A9C3);
            //player1
            canvas.drawRect(getAvatarRect(Player.Player1), mPaint);

            //绘制个人HP区域
            //player2
            mPaint.setColor(0x775CC3A1);
            canvas.drawRect(getHPRect(Player.Player2), mPaint);
            //player1
            mPaint.setColor(0xFF5CC3A1);
            canvas.drawRect(getHPRect(Player.Player1), mPaint);

            //绘制两侧的卡组区域
            //player2
            mPaint.setColor(0x77A39BC4);
            canvas.drawRect(getDeckRect(Player.Player2), mPaint);
            //player1
            mPaint.setColor(0xFFA39BC4);
            canvas.drawRect(getDeckRect(Player.Player1), mPaint);
        }
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

    public int getStatusBarHeight(Context context) {
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

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return superState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
    }

    public enum CardLocation {
        LeftTop,MiddleTop,RightTop,LeftBottom,MiddleBottom,RightBottom
    }

    public enum Player {
        Player1,Player2
    }

}
