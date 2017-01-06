package com.huang.listview_delete.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.huang.listview_delete.utils.Logutil;

public class SilderListView extends ListView {

    public SliderView mFocusedItemView;

    float mX = 0;
    float mY = 0;
    private int mPosition = -1;
    boolean isSlider = false;
    private boolean mIsOnMeasure;
    private boolean shown = false;
    private OnRefreshListener listener;

    public SilderListView(Context context) {
        super(context);
    }

    public SilderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SilderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 处理ScrollerView嵌套ListView引发的事件冲突
     * ||(mTouchState == TOUCH_STATE_X)
     */
    private int mLastY;
    private int mLastX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int duraX = Math.abs((int) (ev.getX() - mLastX));
                int duraY = Math.abs((int) (ev.getY() - mLastY));
                if (mFocusedItemView != null) {
                    shown = mFocusedItemView.isShown();
                } else {
                    shown = false;
                }


                if ((duraX > 10 && duraY < 40) || shown) {
                    //当x轴距离为>10并且y轴滑动距离<40的时候,认为是侧滑删除操作，父控件不拦截子控件的事件
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    Logutil.e("huang=======父控件不拦截 我自己出去");
                    if (listener != null) {
                        listener.noRefersh();
                    }
                } else {
                    // 拦截子控件的事件，交给父控件处理
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    Logutil.e("huang========拦截子控件,父类处理");
                    if (listener != null) {
                        listener.startRefresh();
                    }
                }

                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();

                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    if (!isSlider) {
                        listener.startRefresh();
                    }
                    Logutil.e("侧滑删除是否展示出来--------" + isSlider);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshListener {
        void startRefresh();

        void noRefersh();
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        mIsOnMeasure = true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mIsOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSlider = false;
                mX = x;
                mY = y;
                int position = pointToPosition((int) x, (int) y);
                if (mPosition != position) {
                    mPosition = position;
                    if (mFocusedItemView != null) {
                        mFocusedItemView.reset();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPosition != -1) {
                    if (Math.abs(mY - y) < 30 && Math.abs(mX - x) > 20) {
                        int first = this.getFirstVisiblePosition();
                        int index = mPosition - first;
                        mFocusedItemView = (SliderView) getChildAt(index);
                        mFocusedItemView.onTouchEvent(event);
                        isSlider = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSlider) {
                    isSlider = false;
                    if (mFocusedItemView != null) {
                        mFocusedItemView.adjust(mX - x > 0);
                        return true;
                    }
                }

                if (listener != null) {
                    if (!isSlider) {
                        listener.startRefresh();
                    }
                    Logutil.e("侧滑删除是否展示出来--------" + isSlider);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


}