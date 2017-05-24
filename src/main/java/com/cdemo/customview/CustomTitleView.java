package com.cdemo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by yangdi on 2017/5/24.
 */

public class CustomTitleView extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    // 矩形/长方形
    private Rect mRect;
    // 画笔
    private Paint mPaint;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取在xml布局文件中声明/定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        mTitleText = typedArray.getString(R.styleable.CustomTitleView_titletext);
        mTitleTextColor = typedArray.getColor(R.styleable.CustomTitleView_titletextcolor, Color.BLACK);
//        mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTitleView_titletextsize, (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTitleView_titletextsize, TypedValue.COMPLEX_UNIT_SP);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setTextSize(mTitleTextSize);
        mRect = new Rect();
//        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRect);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // int widthMeasureSpec, int heightMeasureSpec:
        // 子类View的这两个参数，由ViewGroup中的layout_width，layout_height和padding以及View自身的layout_margin共同决定。
        // 这个值由高32位和低16位组成，高32位保存的值叫specMode，可以通过如代码中所示的MeasureSpec.getMode()获取；低16位为specSize，同样可以由MeasureSpec.getSize()获取。
        // MeasureSpec的specMode,一共三种类型：
        // EXACTLY：一般是设置了明确的值,如：200dp或者是MATCH_PARENT
        // AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
        // UNSPECIFIED：表示子布局想要多大就多大，很少使用
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {// 确定的值
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRect);
            float textWidth = mRect.width();
            width = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
        }
        if (heightMode == MeasureSpec.EXACTLY) {// 确定的值
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRect);
            float textHeight = mRect.height();
            height = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Canvas相当于画笔，Paint相当于画布
        mPaint.setColor(Color.TRANSPARENT);
        // 画画之前先调画笔颜色 ——调色
        // 画一个长方形
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        // 画一个Text
        // getWidth():获取view的宽度，getHeight()获取view的高度
        canvas.drawText(mTitleText, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);
    }
}
