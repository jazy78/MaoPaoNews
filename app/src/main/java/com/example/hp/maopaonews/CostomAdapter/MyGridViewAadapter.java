package com.example.hp.maopaonews.CostomAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.maopaonews.R;

import java.util.List;

/**
 * Created by hp on 2016/1/8.
 */
public class MyGridViewAadapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int flag;

    public MyGridViewAadapter(List<String> list,Context context,int flag) {
        this.list=list;
        this.context=context;
        this.flag=flag;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public List<String> getList() {
        return list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoudle viewHoudle=null;
        if(convertView==null){
             viewHoudle=new ViewHoudle();
             if (flag==0) {
                 convertView = View.inflate(context, R.layout.item_gridview, null);
             }else {

                 convertView = View.inflate(context, R.layout.item_gridview1, null);
             }
             viewHoudle.tv = (TextView) convertView.findViewById(R.id.tv);
             convertView.setTag(viewHoudle);
        }else {
            viewHoudle = (ViewHoudle) convertView.getTag();

        }
        viewHoudle.tv.setText(list.get(position));

        return convertView;
    }

    class ViewHoudle {
        TextView tv;
    }
}
