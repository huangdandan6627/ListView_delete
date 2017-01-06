package com.huang.listview_delete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.huang.listview_delete.adapter.GroupListenerAdapter;
import com.huang.listview_delete.listview.SilderListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {

    private PtrClassicFrameLayout mPtrclassicframelayout;
    private SilderListView list;
    private GroupListenerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (SilderListView) findViewById(R.id.list);
        mPtrclassicframelayout = (PtrClassicFrameLayout) findViewById(R.id.ptrclassicframelayout);

        list.setOnRefreshListener(new SilderListView.OnRefreshListener() {
            @Override
            public void startRefresh() {
                getRefreshState(PtrFrameLayout.Mode.REFRESH);
            }

            @Override
            public void noRefersh() {
                getRefreshState(PtrFrameLayout.Mode.NONE);
            }
        });

        //设置下拉刷新
        getRefreshState(PtrFrameLayout.Mode.REFRESH);
        mPtrclassicframelayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                frame.refreshComplete();
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                if (list.mFocusedItemView != null) {
                    list.mFocusedItemView.goHomeScrollto();
                    list.mFocusedItemView.invalidate();
                }
                frame.refreshComplete();
                refresh();
            }
        });

        //自动刷新
        mPtrclassicframelayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrclassicframelayout.autoRefresh();
            }
        });

        //设置适配器
        if (adapter == null) {
            adapter = new GroupListenerAdapter(this);
            list.setAdapter(adapter);
        }
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        List<String> data = new ArrayList<>();
        data.clear();
        for (int i = 0; i < 1000; i++) {
            data.add("条目====================" + i);
        }
        adapter.setGroupInvits(data);
    }

    /**
     * 下拉刷新的配置
     * @param refreshState
     */
    private void getRefreshState(PtrFrameLayout.Mode refreshState) {
        PublicUtil.setHeadView(this, mPtrclassicframelayout);
        mPtrclassicframelayout.setLoadingMinTime(1000);
        mPtrclassicframelayout.setMode(refreshState);
    }
}
