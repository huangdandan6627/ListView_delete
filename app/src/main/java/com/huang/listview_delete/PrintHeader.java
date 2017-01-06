package com.huang.listview_delete;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Administrator on 2016.12.06.
 */

public class PrintHeader extends FrameLayout implements PtrUIHandler {

    private LayoutInflater inflater;

    // 下拉刷新视图（头部视图）
    private ViewGroup headView;

    // 下拉刷新文字
    private TextView tvHeadTitle;

    // 下拉图标
    private ImageView ivWindmill;

    private AnimationDrawable animationDrawable;

    public PrintHeader(Context context) {
        this(context, null);
    }

    public PrintHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrintHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {

        inflater = LayoutInflater.from(context);
        /**
         * 头部
         */
        headView = (ViewGroup) inflater.inflate(R.layout.windmill_header, this, true);
        ivWindmill = (ImageView) headView.findViewById(R.id.iv_windmill);
        tvHeadTitle = (TextView) headView.findViewById(R.id.tv_head_title);
        ivWindmill.setImageResource(R.drawable.print_head_anim1);
        animationDrawable = (AnimationDrawable) ivWindmill.getDrawable();

    }

    @Override
    public void onUIReset(PtrFrameLayout ptrFrameLayout) {
        tvHeadTitle.setText("下拉刷新");
        animationDrawable = (AnimationDrawable) ivWindmill.getDrawable();
        animationDrawable.stop();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
        tvHeadTitle.setText("下拉刷新");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        tvHeadTitle.setText("正在刷新");
        animationDrawable = (AnimationDrawable) ivWindmill.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        ivWindmill.clearAnimation();
        tvHeadTitle.setText("刷新完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//            drawable.postRotation(currentPos - lastPos);
            animationDrawable = (AnimationDrawable) ivWindmill.getDrawable();
            animationDrawable.start();
            invalidate();
        }

        //        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
//            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                tvHeadTitle.setText("下拉刷新");
//
//            }
//        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
//            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                tvHeadTitle.setText("松开刷新");
//            }
//        }
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        if (ptrIndicator.getCurrentPosY() < mOffsetToRefresh && ptrIndicator.getLastPosY() >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvHeadTitle.setText("下拉刷新");

            }
        } else if (ptrIndicator.getCurrentPosY() > mOffsetToRefresh && ptrIndicator.getLastPosY() <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvHeadTitle.setText("松开刷新");
            }
        }
    }
}
