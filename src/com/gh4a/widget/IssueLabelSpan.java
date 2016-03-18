package com.gh4a.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import com.gh4a.R;
import com.gh4a.utils.UiUtils;

import org.eclipse.egit.github.core.Label;

public class IssueLabelSpan extends ReplacementSpan {
    private int mFgColor;
    private int mBgColor;
    private int mPadding;
    private int mRightAndBottomMargin;
    private float mTextSize;

    private int mAscent;
    private int mDescent;

    public IssueLabelSpan(Context context, Label label, boolean withMargin) {
        super();
        mBgColor = UiUtils.colorForLabel(label);
        mFgColor = UiUtils.textColorForBackground(context, mBgColor);

        Resources res = context.getResources();
        mPadding = res.getDimensionPixelSize(R.dimen.issue_label_padding);
        mRightAndBottomMargin =
                withMargin ? res.getDimensionPixelSize(R.dimen.issue_label_margin) : 0;
        mTextSize = res.getDimension(R.dimen.issue_label_text_size);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(mTextSize);
        if (fm != null) {
            paint.getFontMetricsInt(fm);
            mAscent = -fm.ascent;
            mDescent = fm.descent;
            fm.top -= mPadding;
            fm.ascent -= mPadding;
            fm.bottom += mPadding + mRightAndBottomMargin;
            fm.descent += mPadding + mRightAndBottomMargin;
        }

        float textSize = paint.measureText(text, start, end);
        return (int) Math.ceil(textSize) + 2 * mPadding + mRightAndBottomMargin;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
            float x, int top, int y, int bottom, Paint paint) {
        paint.setTextSize(mTextSize);
        float textSize = paint.measureText(text, start, end);

        final float bgLeft = x;
        final float bgRight = bgLeft + textSize + 2 * mPadding;
        final float bgTop = y - mAscent - mPadding;
        final float bgBottom = y + mDescent + mPadding;
        paint.setColor(mBgColor);
        canvas.drawRect(bgLeft, bgTop, bgRight, bgBottom, paint);

        paint.setColor(mFgColor);
        canvas.drawText(text, start, end, x + mPadding, y, paint);
    }
}