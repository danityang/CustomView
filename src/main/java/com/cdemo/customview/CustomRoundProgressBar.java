package com.cdemo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by yangdi on 2017/5/24.
 */

public class CustomRoundProgressBar extends View{

    // 进度条宽度
    private int drawWidth;
    // 进度条颜色
    private int drawColor;
    // 中间数字颜色
    private int textColor;
    // 中间数字字体大小
    private int textSize;

    // 字的高度
    private float mTxtHeight;

    // 进度条最大值
    private int maxProgress;
    // 进度条当前值
    private int currentProgress;

    private Paint mPaint;

    String TAG = "CustomRoundProgressBar";


    public CustomRoundProgressBar(Context context) {
        this(context, null);
    }

    public CustomRoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取在xml文件中定义的属性和值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundProgressBar, defStyleAttr, 0);
        drawWidth = typedArray.getDimensionPixelSize(R.styleable.CustomRoundProgressBar_drawwidth, TypedValue.COMPLEX_UNIT_DIP);
        drawColor = typedArray.getInt(R.styleable.CustomRoundProgressBar_drawcolor, Color.LTGRAY);
        textColor = typedArray.getColor(R.styleable.CustomRoundProgressBar_textcolor,Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.CustomRoundProgressBar_textsize, TypedValue.COMPLEX_UNIT_SP);

        typedArray.recycle();

        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            // 这里不考虑wrap_content的情况，如有需要可自己补上
         }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            // 这里不考虑wrap_content的情况，如有需要可自己补上
        }

        // 设置最终宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 获取视图宽度/2
        int viewWidth = getWidth()/2;
        // 获取视图高度/2
        int viewHeight = getHeight()/2;
        // 圆形进度条半径为宽度的3倍
        int radius = drawWidth*3;

        // 画最外层圆圈
        mPaint.setColor(Color.LTGRAY);
        // 设置画笔宽度
        mPaint.setStrokeWidth(drawWidth);
        // 绘制空心效果
        mPaint.setStyle(Paint.Style.STROKE);
        // 画板底色
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(viewWidth, viewHeight, radius, mPaint);


        // 画中间进度数字
        mPaint.setStrokeWidth(0);/* 上面设置过一次画笔宽度，现在需要设置为0，否则画出的字挤在一起*/
        // 画笔颜色
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        // 字体
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        // 通过画布
        float textWidth = mPaint.measureText(currentProgress+"%");
        canvas.drawText(currentProgress+"%", viewWidth - textWidth/2, viewHeight + textSize/3, mPaint);


        // 画进度
        mPaint.setColor(drawColor);
        mPaint.setStrokeWidth(drawWidth);
        // 绘制进度
        RectF oval = new RectF(viewWidth - radius, viewHeight - radius, viewWidth + radius, viewHeight + radius);
        canvas.drawArc(oval, 0, getProgress(), false, mPaint);
    }


    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        postInvalidate();
    }

    private int getProgress(){
        if(currentProgress < maxProgress){
            return 360*currentProgress/maxProgress;
        }else{
            return 360;
        }
    }




}
