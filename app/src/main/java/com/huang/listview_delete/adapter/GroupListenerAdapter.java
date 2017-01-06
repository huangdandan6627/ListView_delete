package com.huang.listview_delete.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.listview_delete.R;
import com.huang.listview_delete.listview.SliderView;

import java.util.ArrayList;
import java.util.List;

public class GroupListenerAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> datas = new ArrayList<>();

    public GroupListenerAdapter(Context context) {
        mContext = context;
    }

    public void setGroupInvits(List<String> data) {
        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        SliderView slideView = (SliderView) convertView;
        if (slideView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.group_listener_content, parent, false);
            slideView = new SliderView(mContext);
            slideView.setContentView(itemView);
            holder = new ViewHolder(slideView);
            slideView.setTag(holder);
        } else {
            holder = (ViewHolder) slideView.getTag();
        }

        slideView.shrink();

        holder.constent.setAlpha(1.0f);
        holder.message.setText(datas.get(position));
        holder.mAvatar.setImageResource(R.drawable.xiaomi);

        /**
         * 点击删除的业务逻辑
         */
        holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 重新绘制
                Toast.makeText(mContext, "点击删除", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 点击同意的业务逻辑
         */
        holder.constent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constent.setAlpha(.5f);
                Toast.makeText(mContext, "点击同意", Toast.LENGTH_SHORT).show();
            }
        });
        return slideView;
    }

    private static class ViewHolder {
        public ImageView constent;
        public TextView message;
        public ViewGroup deleteHolder;
        private ImageView mAvatar;

        ViewHolder(View view) {
            constent = (ImageView) view.findViewById(R.id.constent);
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            message = (TextView) view.findViewById(R.id.message);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        }
    }
}
