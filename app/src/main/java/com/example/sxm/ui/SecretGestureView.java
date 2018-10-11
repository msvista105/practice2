package com.example.sxm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.sxm.practice.R;
import com.example.sxm.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SecretGestureView extends ViewGroup {
    private static final String TAG = "SecretGestureView";
    private static final int NORMAL = 0;
    private static final int TOUCH = 1;
    private static final int ERROR = 2;
    private static final int padding = 20;//边缘留空白
    private int colsNum = 3;//列数
    private int rowsNum = 3;//行数
    //将圆圈居中，x轴的偏移量
    private int offsetX = 0;
    //将圆圈居中，y轴的偏移量
    private int offsetY = 0;
    private int childWidth = 0;
    private int childHeight = 0;
    //上一个按钮的index
    private int lastIndex = -1;
    private int currentIndex = -1;
    //SecretGestureView的canvas
    private Canvas mCanvas = null;
    //路径
    Path mPath = new Path();
    Paint mPaint = new Paint();

    List<String> pathPoints = new ArrayList<>();

    public SecretGestureView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public SecretGestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public SecretGestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public SecretGestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SecretGestureView);
        colsNum = ta.getInt(R.styleable.SecretGestureView_colsNum, 3);
        rowsNum = ta.getInt(R.styleable.SecretGestureView_rowsNum, 3);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > height) {
            offsetX = (width - height) / 2;
        } else {
            offsetY = (height - width) / 2;
        }
        LogUtils.d(TAG, "onMeasure width:" + width + " height:" + height);
        //手势界面为正方形
        width = height = width < height ? width : height;
        addChildView(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getWidth();
        int height = getHeight();
        LogUtils.d(TAG, "onLayout width:" + width + " height:" + height);
        for (int i = 0; i < getChildCount(); i++) {
            int pos_col = i % colsNum;
            int pos_row = i / colsNum;
            LogUtils.d(TAG, "onLayout pos_col:" + pos_col + " pos_row:" + pos_row);
            View child = getChildAt(i);
            child.layout(pos_col * child.getMeasuredWidth() + offsetX,
                    pos_row * child.getMeasuredHeight() + offsetY,
                    (pos_col + 1) * child.getMeasuredWidth() + offsetX,
                    (pos_row + 1) * child.getMeasuredHeight() + offsetY);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        LogUtils.d(TAG, "onTouchEvent action:" + action);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                View child = findChild((int) x, (int) y);
                if (child != null && child instanceof CircleView) {
                    ((CircleView) child).setLockType(TOUCH);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < getChildCount(); i++) {
                    View v = getChildAt(i);
                    if (v instanceof CircleView) {
                        ((CircleView) v).setLockType(NORMAL);
                    }
                }
                resetState();
                invalidate();
                break;
        }
        //return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        LogUtils.d(TAG, "onDraw ---- ");
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setAntiAlias(true);
        Path path = new Path();
        for (int i = 0; i < pathPoints.size(); i++) {
            String[] points = pathPoints.get(i).split("-");
            if (points.length != 2) {
                LogUtils.d(TAG, "onDraw ---- points.length is error");
                break;
            }
            int x = Integer.valueOf(points[0]);
            int y = Integer.valueOf(points[1]);
            if (i == 0) {
                path.moveTo(x + offsetX, y + offsetY);
            } else {
                path.lineTo(x + offsetX, y + offsetY);
            }
        }
        canvas.drawPath(path, paint);
    }

    private void addChildView(int width, int height) {
        removeAllViews();
        childWidth = width / colsNum;
        childHeight = height / rowsNum;
        for (int i = 0; i < colsNum * rowsNum; i++) {
            CircleView child = new CircleView(getContext());
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
            //child.setBackgroundResource(R.drawable.ic_logo);
            addView(child);
        }
    }

    @Nullable
    private View findChild(int x, int y) {
        int index = 0;
        if (childWidth == 0 || childHeight == 0) return null;
        if (x > offsetX && x < getMeasuredWidth() - offsetX
                && y > offsetY && y < getMeasuredHeight() - offsetY) {
            index = (x - offsetX) / childWidth;
//            if ((x-offsetX)%childWidth != 0){
//                index ++;
//            }
//            index += ((y-offsetY)/childHeight + ((y-offsetY)%childHeight != 0? 1:0)) * colsNum;
            index += (y - offsetY) / childHeight * colsNum;
            //防止误触
            View child = getChildAt(index);
            int[] pos = {0, 0};
            pos[0] = (x - offsetX) / childWidth * childWidth + offsetX;
            pos[1] = (y - offsetY) / childHeight * childHeight + offsetY;
//            child.getLocationInWindow(pos);
            LogUtils.d(TAG, "findChild x:" + x + " y:" + y + " pos[0]:" + pos[0] + " pos[1]:" + pos[1]);
            if (x < pos[0] + padding || x > pos[0] + childWidth - padding
                    || y < pos[1] + padding || y > pos[1] + childHeight - padding) {
                return null;
            }
            currentIndex = index;
            if (currentIndex != lastIndex && lastIndex != -1) {
                drawArrow(lastIndex, currentIndex);
                invalidate();
            }
            lastIndex = currentIndex;
            return child;
        } else {
            return null;
        }
    }

    private void drawArrow(int lastIndex, int currentIndex) {
        //计算两者的圆心
        int lastCenterX = 0;
        int lastCenterY = 0;
        int curCenterX = 0;
        int curCenterY = 0;
        lastCenterX = lastIndex % colsNum * childWidth + childWidth / 2;
        lastCenterY = lastIndex / rowsNum * childHeight + childHeight / 2;
        curCenterX = currentIndex % colsNum * childWidth + childWidth / 2;
        curCenterY = currentIndex / rowsNum * childHeight + childHeight / 2;
        LogUtils.d(TAG, "drawArrow  lastCenterX:" + lastCenterX + " lastCenterY:" + lastCenterY
                + " curCenterX:" + curCenterX + " curCenterY:" + curCenterY);
        pathPoints.add(lastCenterX + "-" + lastCenterY);
        pathPoints.add(curCenterX + "-" + curCenterY);
        invalidate();
        //划线
//        mPath.reset();
//        mPath.moveTo(lastCenterX,lastCenterY);
//        mPath.lineTo(lastCenterX, lastCenterY);
//        mPath.lineTo(curCenterX, curCenterY);
//        mPath.close();
//        mPaint.setColor(Color.BLUE);
//        invalidate();
//        mPaint.setAntiAlias(true);
//
//        if (mCanvas != null) {
//            mCanvas.drawPath(mPath, mPaint);
//        }
    }

    private void resetState() {
//        offsetX = offsetY = 0;
//        childHeight = childWidth = 0;
//        colsNum = rowsNum = 3;
        lastIndex = -1;
        currentIndex = -1;
        pathPoints.clear();
    }

    class CircleView extends View {
        int lockType = NORMAL;
        Paint paint = new Paint();

        public CircleView(Context context) {
            super(context);
        }

        public CircleView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int center = getMeasuredHeight() / 2;
            int radius = (getMeasuredWidth() - 2 * padding) / 2;
            switch (lockType) {
                case NORMAL:
                    paint.setColor(Color.BLUE);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                    paint.setAntiAlias(true);
                    canvas.drawCircle(center, center, radius, paint);
                    break;
                case TOUCH:
                    paint.setColor(Color.GREEN);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                    paint.setAntiAlias(true);
                    canvas.drawCircle(center, center, radius, paint);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(center, center, 10, paint);
                    break;
                case ERROR:
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                    paint.setAntiAlias(true);
                    canvas.drawCircle(center, center, radius, paint);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(center, center, 10, paint);
                    break;
                default:
                    LogUtils.d(TAG, "onDraw lockType error!");
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            LogUtils.d("CircleView", "onTouchEvent action:" + event.getAction());
            return super.onTouchEvent(event);
        }

        protected void setLockType(int type) {
            lockType = type;
            this.invalidate();
        }

        protected void drawArrow(int angle) {
            //
        }
    }
}
