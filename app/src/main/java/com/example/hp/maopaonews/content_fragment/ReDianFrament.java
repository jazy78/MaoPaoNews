package com.example.hp.maopaonews.content_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.maopaonews.Activities.WebViewActivity;
import com.example.hp.maopaonews.Activities.XinWenXiActivity;
import com.example.hp.maopaonews.CostomAdapter.XinWenBaseAdapter;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.pullrefreshview.PullToRefreshBase;
import com.example.hp.maopaonews.pullrefreshview.PullToRefreshListView;
import com.example.hp.maopaonews.utils.CommonUtil;
import com.example.hp.maopaonews.utils.XinWenJson;
import com.example.hp.maopaonews.utils.XinWenXiData;
import com.example.hp.maopaonews.utils.XinWen_adapter;
import com.example.hp.maopaonews.utils.XinWen_toutiao;
import com.example.hp.maopaonews.utils.XutilsGetData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/12.
 */
public class ReDianFrament extends Fragment {

    private int daohangtype;
    private String url;
    private View contentView;
    private LinearLayout titlebar;
    private PullToRefreshListView toutiao_lv;
    private XutilsGetData xutilsGetData = new XutilsGetData();
    private XinWenBaseAdapter  toutiaoAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daohangtype= XinWen_adapter.getXinWenType("热点");
        url=new XinWenURL().getRedian();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           if(contentView==null){
               contentView=initView(inflater);

           }


        return contentView;
    }
    public View initView(LayoutInflater inflater){
        final  View view=inflater.inflate(R.layout.redian_view,null);
        titlebar=(LinearLayout)view.findViewById(R.id.redian_title_bar);
        toutiao_lv=(PullToRefreshListView)view.findViewById(R.id.xinwen_toutiao_lv);
        toutiao_lv.setPullLoadEnabled(false);
        toutiao_lv.setScrollLoadEnabled(false);
        toutiao_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getdata(url, true);//刷新数据
                String stringDate = CommonUtil.getStringData();// 下拉刷新时获取当前的刷新时间
                toutiao_lv.setLastUpdatedLabel(stringDate);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        getdata(url, true);
        return  view;

    }

    public void getdata(String url,final  boolean refresh){

        if(CommonUtil.isNetWork(getActivity())){

            xutilsGetData.xUtilsHttp(getActivity(), url, new XutilsGetData.CallBackHttp() {
                @Override
                public void handleData(String data) {
                    getshowdata(data, refresh);
                }
            },true);


        }


    }
    private List<XinWen_toutiao.T1348647853363Entity> toutiao_list = new ArrayList<>();
    private TextView footView;
    private View headview;
    private int height;
    boolean isScroll;
    private void getshowdata(String data,boolean refresh){
        toutiao_list.clear();
        XinWen_toutiao toutiao_object= XinWenJson.getdata(data,daohangtype);
        toutiao_list.addAll(toutiao_object.getT1348647853363());
        if(footView==null){
            footView=new TextView(getActivity());
            footView.setText("亲，请等下一个24小时吧...");
            footView.setTextSize(20);
            footView.setWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth());
            footView.setHeight(150);
            footView.setGravity(Gravity.CENTER);
            toutiao_lv.getRefreshableView().addFooterView(footView);
        }

        if(headview==null){
            headview=View.inflate(getActivity(),R.layout.redian_listviewhead,null);
            //获得控件的宽高
            int w=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            headview.measure(w,h);
            height=headview.getMeasuredHeight();
            toutiao_lv.getRefreshableView().addHeaderView(headview);

        }
        toutiaoAdapter=new XinWenBaseAdapter(getActivity(),toutiao_list);
        toutiao_lv.getRefreshableView().setAdapter(toutiaoAdapter);
        toutiaoAdapter.setToutiao_list(toutiao_list);
        toutiao_lv.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        toutiao_lv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0||position==toutiao_list.size()+1){
                    return;
                }
                frament2activity(position);//跳转轮播详细页面
            }
        });

        isScroll=true;
        toutiao_lv.onPullDownRefreshComplete();//隐藏下拉头
        /////////
    }


    private void frament2activity(int pos){
        int position=pos-1;
        int bujutype=XinWen_adapter.getType(toutiao_list.get(position).getSkipType());
        //传入详细页面的数据
        XinWenXiData xinWenXi = new XinWenXiData();
        xinWenXi.setBujuType(bujutype);
        xinWenXi.setLanMuType(daohangtype);
        xinWenXi.setReplaycount(toutiao_list.get(position).getReplyCount());//跟帖数量
        xinWenXi.setTitle(toutiao_list.get(position).getTitle());//标题
        xinWenXi.setXinwentext(toutiao_list.get(position).getDigest());//内容
        switch (bujutype){
            case XinWen_adapter.TYPE_putong:
            case XinWen_adapter.TYPE_zhuanti:
            case XinWen_adapter.TYPE_zhibo:
                String urlzhibo = toutiao_list.get(position).getUrl();
                xinWenXi.setUrl(urlzhibo);//详细页面url
                Intent intentzhibo = new Intent(getActivity(), WebViewActivity.class);
                intentzhibo.putExtra("xinwendata", xinWenXi);
                startActivity(intentzhibo);
                getActivity().overridePendingTransition(R.anim.xinwen_inactivity, R.anim.xinwen_inactivity);
                break;
            case XinWen_adapter.TYPE_duotu:
                String urlduotuRight = toutiao_list.get(position).getSkipID();
                String urlRighBefor = urlduotuRight.substring(urlduotuRight.lastIndexOf("|") - 4);
                String urlRight = urlRighBefor.replaceAll("\\|", "/");
                String urlduotu = "http://c.3g.163.com/photo/api/set/" + urlRight + ".json";
                //0096|81994    http://c.3g.163.com/photo/api/set/0096/82126.json
                xinWenXi.setUrl(urlduotu);//详细页面url
                //跳转到详细页
                Intent intentduotu = new Intent(getActivity(), XinWenXiActivity.class);
                intentduotu.putExtra("xinwendata", xinWenXi);
                startActivity(intentduotu);
                break;


        }


    }

}
