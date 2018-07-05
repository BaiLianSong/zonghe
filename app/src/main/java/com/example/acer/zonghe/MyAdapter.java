package com.example.acer.zonghe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by acer on 2018/7/1.
 */

public class MyAdapter extends BaseAdapter {
    private List<UserBean.DataBean> list;
    private Context context;

    public MyAdapter(List<UserBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.listview,null);
            holder.text = convertView.findViewById(R.id.text);
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getUserImg(),holder.image,MyApplication.getOption());
        holder.text.setText(list.get(position).getTitle());

        return convertView;
    }

    class ViewHolder{
        ImageView image;
        TextView text;
    }
}
