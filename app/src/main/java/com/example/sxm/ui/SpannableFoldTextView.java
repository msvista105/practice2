package com.example.sxm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.sxm.practice.R;
import com.example.sxm.utils.LogUtils;

public class SpannableFoldTextView extends AppCompatTextView implements View.OnClickListener {
    private static final String TAG = "SpannableFoldTextView";
    private int mShowMaxLines = 0;
    private static final String ELLIPSIZE_END = "...";
    private static final int MAX_LINE = 4;
    private static final String EXPAND_TIP_TEXT = "收起全文";
    private static final String FOLD_TIP_TEXT = "全文";
    private static final int TIP_COLOR = 0xFFFFFFFF;
    /**
     * 全文显示的位置
     */
    private static final int END = 0;
    /**
     * 折叠文本
     */
    private String mFoldText;
    /**
     * 展开文本
     */
    private String mExpandText;
    /**
     * 原始文本
     */
    private CharSequence mOriginalText;
    /**
     * 是否展开
     */
    private boolean isExpand;
    /**
     * 全文显示的位置
     */
    private int mTipGravity;
    /**
     * 全文文字的颜色
     */
    private int mTipColor;
    /**
     * 全文是否可点击
     */
    private boolean mTipClickable;
    /**
     * 全文点击的span
     */
    private ExpandSpan mSpan;
    private boolean flag;
    /**
     * 展开后是否显示文字提示
     */
    private boolean isShowTipAfterExpand;

    /**
     * 是否是Span的点击
     */
    private boolean isExpandSpanClick;
    /**
     * 父view是否设置了点击事件
     */
    private boolean isParentClick;

    public SpannableFoldTextView(Context context) {
        this(context, null);
    }

    public SpannableFoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpannableFoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShowMaxLines = MAX_LINE;
        mSpan = new ExpandSpan();
        if(attrs != null){
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.FoldTextView);
            mShowMaxLines = arr.getInt(R.styleable.FoldTextView_showMaxLine,MAX_LINE);
            mTipGravity = arr.getInt(R.styleable.FoldTextView_tipGravity, END);
            mTipColor = arr.getColor(R.styleable.FoldTextView_tipColor, TIP_COLOR);
            mTipClickable = arr.getBoolean(R.styleable.FoldTextView_tipClickable, false);
            mFoldText = arr.getString(R.styleable.FoldTextView_foldText);
            mExpandText = arr.getString(R.styleable.FoldTextView_expandText);
            isShowTipAfterExpand = arr.getBoolean(R.styleable.FoldTextView_showTipAfterExpand, false);
            arr.recycle();
        }
        if (mFoldText == null) mFoldText = FOLD_TIP_TEXT;
        if (mExpandText == null) mExpandText = EXPAND_TIP_TEXT;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        //
        if (TextUtils.isEmpty(text) || mShowMaxLines == 0) {
            super.setText(text, type);
        } else if (isExpand) {
            SpannableStringBuilder spannable = new SpannableStringBuilder(mOriginalText);
            addTip(spannable,type);
        } else {
            if(!flag){
                getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        flag = true;
                        formatText(text,type);
                        return true;
                    }
                });
            }else{
                formatText(text,type);
            }
        }

    }
    private void addTip(SpannableStringBuilder spannable,BufferType type){
        if(!(isExpand && !isShowTipAfterExpand)){
            //全文 显示位置（末尾还是下一行）
            if(mTipGravity == END){
                spannable.append("   ");
            }else{
                spannable.append("\n");
            }
            int length = 0;
            if (isExpand) {
                spannable.append(EXPAND_TIP_TEXT);
                length = EXPAND_TIP_TEXT.length();
            }else{
                spannable.append(FOLD_TIP_TEXT);
                length = FOLD_TIP_TEXT.length();
            }
            //tip可点击
            if(mTipClickable){
                spannable.setSpan(mSpan,spannable.length()-length,spannable.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                setMovementMethod(LinkMovementMethod.getInstance());//激活部分字符串的点击
            }
            spannable.setSpan(new ForegroundColorSpan(mTipColor),
                    spannable.length()-length,spannable.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        super.setText(spannable,type);
    }
    private void formatText(CharSequence text, BufferType type){
        mOriginalText = text;
        Layout layout = getLayout();
        if (layout == null || !mOriginalText.equals(layout.getText())){
            super.setText(mOriginalText,type);
            layout = getLayout();
        }
        if (layout == null){
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    translateText(getLayout(),type);
                }
            });
        }else{
            translateText(layout,type);
        }
    }
    private void translateText(Layout layout, BufferType type){
        if(getLayout().getLineCount() > mShowMaxLines){
            SpannableStringBuilder span = new SpannableStringBuilder();
            Paint paint = getPaint();
            int start = layout.getLineStart(mShowMaxLines -1);
            int end = layout.getLineVisibleEnd(mShowMaxLines -1);
            LogUtils.d(TAG,"translateText  start:"+start+" end:"+end);
            StringBuilder builder = new StringBuilder();
            builder.append(ELLIPSIZE_END);
            if(mTipGravity == END){
                builder.append("   ");
                builder.append(mFoldText);
            }
            //paint.measureText("nihao---")测量字符串的长度
            //从后（end）向前(start)测量MOriginalText能满足paint.measureText("nihao---")长度的字符串的个数
            int length = paint.breakText(mOriginalText, start, end, false, paint.measureText(builder.toString()), null);
            end -= length + 1;
            CharSequence ellipsize = mOriginalText.subSequence(0,end);
            span.append(ellipsize);
            span.append(ELLIPSIZE_END);
            addTip(span,type);
        }
    }

    class ExpandSpan extends ClickableSpan{
        @Override
        public void onClick(View widget) {
            if (mTipClickable) {
                isExpand = !isExpand;
                isExpandSpanClick = true;
                LogUtils.d(TAG, "span click");
                setText(mOriginalText);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
        }
    }
}
