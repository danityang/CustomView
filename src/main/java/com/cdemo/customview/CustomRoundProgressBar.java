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

public class CustomRoundProgressBar extends View {

    private int drawWidth;
    private int drawColor;
    private int textColor;
    private int textSize;
    private String text;

    Paint mPaint;


    public CustomRoundProgressBar(Context context) {
        this(context, null);
    }

    public CustomRoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundProgressBar, defStyleAttr, 0);
        drawWidth = typedArray.getDimensionPixelSize(R.styleable.CustomRoundProgressBar_drawwidth, TypedValue.COMPLEX_UNIT_DIP);
        drawColor = typedArray.getInt(R.styleable.CustomRoundProgressBar_drawcolor, Color.LTGRAY);
        textColor = typedArray.getColor(R.styleable.CustomRoundProgressBar_numcolor,Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.CustomRoundProgressBar_numsize, TypedValue.COMPLEX_UNIT_SP);
        text = typedArray.getString(R.styleable.CustomRoundProgressBar_num);

        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{

        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{

        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int centre = getWidth()/2; //获取圆心的x坐标
        int radius = drawWidth*2; //圆环的半径

        // 画最外层圆圈
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(drawWidth);
        // 绘制空心效果
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(getWidth()/2, getHeight()/2, radius, mPaint);

        // 中间进度条
        mPaint.setStrokeWidth(0);// 上面设置过一次画笔宽度，现在需要设置为0，否则画出的字体很粗，挤在一起
        mPaint.setColor(Color.LTGRAY);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        float textWidth = mPaint.measureText(text+"%");
        canvas.drawText("80%", getWidth()/2 - textWidth / 3, getHeight()/2 + textSize/2, mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(drawWidth);
        // 绘制进度
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 0, 350, false, mPaint);

    }
}
