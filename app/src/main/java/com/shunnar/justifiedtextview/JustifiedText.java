package com.shunnar.justifiedtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.utmil.AttributeSet;

/**
 * <p/>
 * <li>Description:TODO</li>
 * <li>@author: shunnar</li>
 * <li>Date: 2017/10/31 下午1:21</li>
 * <li>@version: 1.0.0 </li>
 * <li>@since JDK 1.8 </li>
 */
public class JustifiedText extends android.support.v7.widget.AppCompatTextView {

    private int mViewWidth;

    private float mLineY;

    private boolean isSpace = true;

    public JustifiedText(Context context) {
        super(context);
    }

    public JustifiedText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JustifiedText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        mViewWidth = getMeasuredWidth();
        String text = getText().toString();
        int length = text.length();//文本长度
        mLineY = getTextSize();
        float drawY = mLineY;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();//暂未用到
        drawY += paddingTop;
        mViewWidth = mViewWidth - paddingLeft - paddingRight;
        int spaceWidth = 0;
        int colonWidth = 0;//冒号的宽度
//是否存在冒号 包括中英 冒号   因为一般来说  冒号是跟在最后一个字符后面的  所以在计算宽度的时候需要除去
        if (text.contains(":")) {
            colonWidth = (int) textPaint.measureText(":");
        } else if (text.contains("：")) {
            colonWidth = (int) textPaint.measureText("：");
        }
        int spaceLength = 1;
        if (colonWidth != 0) {
            spaceLength = 2;
        }
        //计算每个空格的宽度
        spaceWidth = (int) ((mViewWidth - textPaint.measureText(text) - colonWidth) / (length - spaceLength));
        int drawX = paddingLeft;
        for (int i = 0; i < length; i++) {//循环画每一个字
            String chars = text.substring(i, (i + 1));
            if (i != 0) {
                drawX = paddingLeft + (int) textPaint.measureText(text.substring(0, i));
                //获取字的宽度 大小
                if (isSpace) {//是否需要填充空格 这里默认需要
                    if (colonWidth != 0 && i == (length - 1)) {
                        drawX += ((i - 1) * spaceWidth);
                    } else {
                        drawX += (i * spaceWidth);
                    }
                }
            }
            canvas.drawText(chars, drawX, drawY, textPaint);
        }
    }
}
