package com.example.hp.maopaonews.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.maopaonews.Bean.SearchBean;
import com.example.hp.maopaonews.R;

import java.util.List;

/**
 * Created by hp on 2016/1/8.
 */
public class SearchResultAdapter extends BaseAdapter {
    private List<SearchBean.DocEntity.ResultEntity> list;
    private Context context;
    public List<SearchBean.DocEntity.ResultEntity> getList() {
        return list;
    }
    public SearchResultAdapter(List<SearchBean.DocEntity.ResultEntity> list, Context context) {
        this.list = list;
        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHoudle2 viewHoudle2=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_searchresult,null);
            viewHoudle2 = new ViewHoudle2();
            viewHoudle2.result_title = (TextView) convertView.findViewById(R.id.result_title);
            viewHoudle2.result_ptime = (TextView) convertView.findViewById(R.id.result_ptime);
            convertView.setTag(viewHoudle2);
        }else {
            viewHoudle2 = (ViewHoudle2) convertView.getTag();
            String title = list.get(position).title;//专家：<em>中国</em>需要股权分散的B类企业
            String str = title.replace("<em>", "");
            String replace = str.replace("</em>", "");
            viewHoudle2.result_title.setText(replace);
            viewHoudle2.result_ptime.setText(list.get(position).ptime);
        }

        return convertView;
    }
    class ViewHoudle2 {
        TextView result_title,result_ptime;
    }

}
