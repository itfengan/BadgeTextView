package com.fengan.ui.badgetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import ui.fengan.com.badgetextview.R;


/**
 * 开头为角标的Title
 * @author fengan
 * @email fengan1102@gmail.com
 * @created 2018/4/16
 */

// TODO: 2018/4/21 支持点击和末行长度控制
public class BadgeTitleView extends android.support.v7.widget.AppCompatTextView {
    private Context mContext;
    private DynamicDrawableSpan mDynamicDrawableSpan;
    private SpannableStringBuilder mSpannableStringBuilder;
    private Drawable mBadgeDrawable;

    private static final int DEFAULT_BADGE_WIDTH = 53;//dp
    private static final int DEFAULT_BADGE_HEIGH = 15;//dp
    private static final int DEFAULT_BADGE_RADIUS = 2;//dp
    private static final int DEFAULT_BADGE_MARGIN = 5;//dp
    private static final int DEFAULT_BADGE_TEXTSIZE = 10;//sp
    private static final String DEFAULT_BADGE_CONTENT = "标签";
    private static final int DEFAULT_BADGE_BACKGROUND = Color.BLACK;
    private static final int DEFAULT_BADGE_TEXTCOLOR = Color.WHITE;

    private int mBadgeWidth;
    private int mBadgeHeigh;
    private int mBadgeRadius;
    private int mBadgeMargin;
    private int mBadgeTextSize;
    private String mBadgeContent = DEFAULT_BADGE_CONTENT;
    private int mBadageBackground;
    private int mBadageTextColor;

    public BadgeTitleView(Context context) {
        this(context, null);
    }

    public BadgeTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }



    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BadgeTitleView);
        mBadgeWidth = ta.getDimensionPixelSize(R.styleable.BadgeTitleView_badge_width, dp2px(context,DEFAULT_BADGE_WIDTH));
        mBadgeHeigh = ta.getDimensionPixelSize(R.styleable.BadgeTitleView_badge_heigh,  dp2px(context,DEFAULT_BADGE_HEIGH));
        mBadgeRadius = ta.getDimensionPixelSize(R.styleable.BadgeTitleView_badge_radius, dp2px(context,DEFAULT_BADGE_RADIUS));
        mBadgeMargin = ta.getDimensionPixelSize(R.styleable.BadgeTitleView_badge_margin,  dp2px(context,DEFAULT_BADGE_MARGIN));
        mBadgeTextSize = ta.getDimensionPixelSize(R.styleable.BadgeTitleView_badge_textSize, DEFAULT_BADGE_TEXTSIZE);
        mBadageBackground = ta.getColor(R.styleable.BadgeTitleView_badge_background, DEFAULT_BADGE_BACKGROUND);
        mBadageTextColor = ta.getColor(R.styleable.BadgeTitleView_badge_textColor, DEFAULT_BADGE_TEXTCOLOR);
        mBadgeContent = ta.getString(R.styleable.BadgeTitleView_badage_content);
        if (TextUtils.isEmpty(mBadgeContent)) {
            mBadgeContent = DEFAULT_BADGE_CONTENT;
        }
        ta.recycle();
    }

    private Drawable creatBadgeDrawable(Context context) {
        int width = mBadgeWidth;
        int heght = mBadgeHeigh;
        int radius = mBadgeRadius;
        TextView badge = new TextView(context);
        badge.setText(mBadgeContent);
        badge.setGravity(Gravity.CENTER);
        badge.setTextSize(TypedValue.COMPLEX_UNIT_PX,mBadgeTextSize);
        badge.setTextColor(mBadageTextColor);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mBadageBackground);
        gradientDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        badge.setBackgroundDrawable(gradientDrawable);
        badge.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heght, MeasureSpec.EXACTLY));
        badge.layout(0, 0, badge.getMeasuredWidth(), badge.getMeasuredHeight());
        badge.buildDrawingCache();
        Bitmap bitmap = badge.getDrawingCache();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        return bitmapDrawable;
    }

    private DynamicDrawableSpan creatDrawableSpan(final Context context) {
        if (mBadgeDrawable == null) {
            mBadgeDrawable = creatBadgeDrawable(context);
        }
        return new DynamicDrawableSpan() {
            @Override
            public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
                Drawable drawable = getDrawable();
                Rect rect = drawable.getBounds();
                if (fm != null) {
                    Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                    int fontHeight = fmPaint.bottom - fmPaint.top;
                    int drHeight = rect.bottom - rect.top;
                    //矫正中心对齐
                    int top = drHeight / 2 - fontHeight / 4;
                    int bottom = drHeight / 2 + fontHeight / 4;

                    fm.ascent = -bottom;
                    fm.top = -bottom;
                    fm.bottom = top;
                    fm.descent = top;
                }
                return rect.right + mBadgeMargin;
            }

            @Override
            public Drawable getDrawable() {
                mBadgeDrawable.setBounds(0, 0, mBadgeDrawable.getIntrinsicWidth(), mBadgeDrawable.getIntrinsicHeight());
                return mBadgeDrawable;
            }

            // 居中显示
            @Override
            public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
                Drawable drawable = getDrawable();
                canvas.save();
                int transY = 0;
                //获得将要显示的文本高度-图片高度除2等居中位置+top(换行情况)
                transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
                canvas.translate(x, transY);
                drawable.draw(canvas);
                canvas.restore();
            }
        };
    }

    private SpannableStringBuilder creatSpannableStringBuilder(Context context) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(mBadgeContent);
        if (mDynamicDrawableSpan == null) {
            mDynamicDrawableSpan = creatDrawableSpan(context);
        }
        builder.setSpan(mDynamicDrawableSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public CharSequence getText() {
        CharSequence text = super.getText();
        if (text instanceof Spanned) {
            if (!TextUtils.isEmpty(text) && text.length() > mBadgeContent.length()) {
                return text.subSequence(mBadgeContent.length(), text.length());
            } else {
                return "";
            }
        }
        return super.getText();
    }

    public void setTitle(final CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (mDynamicDrawableSpan == null) {
            mDynamicDrawableSpan = creatDrawableSpan(mContext);
        }
        if (mSpannableStringBuilder == null) {
            mSpannableStringBuilder = creatSpannableStringBuilder(mContext);
        }
        mSpannableStringBuilder.clear();
        mSpannableStringBuilder.append(mBadgeContent);
        mSpannableStringBuilder.setSpan(mDynamicDrawableSpan, 0, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.append(text);
        setText(mSpannableStringBuilder, BufferType.NORMAL);
    }

    private   int dp2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * sp转px
     * @param spValue sp值
     * @return px值
     */
    private  int sp2px(Context context,float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
