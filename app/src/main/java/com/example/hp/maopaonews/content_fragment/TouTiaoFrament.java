package com.example.hp.maopaonews.content_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.hp.maopaonews.viewpager.TouTiaoViewPager;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RunnableFuture;
import java.util.logging.Handler;

/**
 * Created by hp on 2016/1/6.
 */
public class TouTiaoFrament extends Fragment {
    private String url = null;
    private PullToRefreshListView toutiao_lv;
    private View view;
    private int daohangtype;
    private XinWenURL xinWenURL = new XinWenURL();
    private XinWenBaseAdapter toutiao_adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        String daohangtitle = bundle.getString("xinwendaohang");
        daohangtype = XinWen_adapter.getXinWenType(daohangtitle);
        url = getUrl();
    }

    private String getUrl() {
        String url = null;
        switch (daohangtype) {
            case XinWen_adapter.toutiao:
                url = xinWenURL.getToutiao();//头条url
                break;
            case XinWen_adapter.yule:
                url = xinWenURL.getYule();//娱乐

                break;
            case XinWen_adapter.tiyu:
                url = xinWenURL.getTiyu();
                break;
            case XinWen_adapter.caijing:
                url = xinWenURL.getChaijing();
                break;
            case XinWen_adapter.keji:
                url = xinWenURL.getKeji();
                break;
            case XinWen_adapter.shishang://时尚
                url = xinWenURL.getShishang();
                break;
            case XinWen_adapter.lishi://历史
                url = xinWenURL.getLishi();
                break;
            case XinWen_adapter.caipiao://彩票
                url = xinWenURL.getCaipiao();
                break;
            case XinWen_adapter.junshi://军事
                url = xinWenURL.getJunshi();
                break;
            case XinWen_adapter.youxi://游戏
                url = xinWenURL.getYouxi();
                break;
        }
        return url;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {

            view = initview(inflater);
        }
        return view;
    }

    private View initview(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.xinwen_toutiaoframent, null, false);
        toutiao_lv = (PullToRefreshListView) view.findViewById(R.id.xinwen_toutiao_lv);
        getdata(url, true);
        toutiao_lv.setPullLoadEnabled(true);//上拉刷新
        toutiao_lv.setScrollLoadEnabled(false);//滚动到底刷新
        toutiao_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getdata(url, true);//刷新数据
                String stringDate = CommonUtil.getStringData();// 下拉刷新时获取当前的刷新时间
                toutiao_lv.setLastUpdatedLabel(stringDate);//将时间添加到刷新的表头
                Log.d("PPP","下拉刷新");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d("PPP","上拉刷新");
            }
        });

        toutiao_lv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                frament2activity(position);//跳转轮播详细页面
            }
        });

        return view;
    }

    private void frament2activity(int position){

        int pos;
        if(listads==null){
            pos=position;
        }else {
            pos=position-1;
        }

        int bujutype=XinWen_adapter.getType(toutiao_list.get(pos).getSkipType());
        //传入详细页面的数据
        XinWenXiData xinWenXi = new XinWenXiData();
        xinWenXi.setBujuType(bujutype);
        xinWenXi.setLanMuType(daohangtype);
        xinWenXi.setReplaycount(toutiao_list.get(pos).getReplyCount());//跟帖数量
        xinWenXi.setTitle(toutiao_list.get(pos).getTitle());//标题
        xinWenXi.setXinwentext(toutiao_list.get(pos).getDigest());//内容
        switch (bujutype) {
            case XinWen_adapter.TYPE_putong:
            case XinWen_adapter.TYPE_zhuanti:
            case XinWen_adapter.TYPE_zhibo:

                String urlzhibo = toutiao_list.get(pos).getUrl();
                xinWenXi.setUrl(urlzhibo);//详细页面url
                //跳转到详细页
                Intent intentzhibo = new Intent(getContext(), WebViewActivity.class);
                intentzhibo.putExtra("xinwendata", xinWenXi);
                startActivity(intentzhibo);
                getActivity().overridePendingTransition(R.anim.xinwen_inactivity, R.anim.xinwen_inactivity);
                break;
            case XinWen_adapter.TYPE_duotu:

                String urlduotuRight = toutiao_list.get(pos).getSkipID();
                String urlRighBefor = urlduotuRight.substring(urlduotuRight.lastIndexOf("|") - 4);
                String urlRight = urlRighBefor.replaceAll("\\|", "/");
                String urlduotu = "http://c.3g.163.com/photo/api/set/" + urlRight + ".json";
                //0096|81994    http://c.3g.163.com/photo/api/set/0096/82126.json
                xinWenXi.setUrl(urlduotu);//详细页面url
                //跳转到详细页
                Intent intentduotu = new Intent(getContext(), XinWenXiActivity.class);
                intentduotu.putExtra("xinwendata", xinWenXi);
                startActivity(intentduotu);
                break;
        }

    }



    //网络请求获得数据 refresh   true为刷新数据  false为加载数据  存储根据url保存数据

    private XutilsGetData xutilsGetData = new XutilsGetData();

    public void getdata(String url, final boolean refresh) {
        if (CommonUtil.isNetWork(getActivity())) {
            xutilsGetData.xUtilsHttp(getActivity(), url, new XutilsGetData.CallBackHttp() {
                @Override
                public void handleData(String data) {
                    getShowdata(data, refresh);
                }
            }, true);

        } else {
            String data = xutilsGetData.getData(getActivity(), url, null);
            if (data != null) {
                getShowdata(data, refresh);

            }
        }

    }

    private List<XinWen_toutiao.T1348647853363Entity> toutiao_list = new ArrayList<>();
    boolean isrefresh = true;
    // 显示数据  或者分页加载数据

    private void getShowdata(String data, boolean refresh) {

        if (refresh) {
            toutiao_list.clear();
        }
        XinWen_toutiao toutiao_object = XinWenJson.getdata(data, daohangtype);
        toutiao_list.addAll(toutiao_object.getT1348647853363());
        if (isrefresh && showLunbo() != null) {

            toutiao_lv.getRefreshableView().addHeaderView(showLunbo());
            isrefresh = false;
        }

        if (toutiao_adapter == null) {
            toutiao_adapter = new XinWenBaseAdapter(getActivity(), toutiao_list);
            toutiao_lv.getRefreshableView().setAdapter(toutiao_adapter);
        }
        toutiao_adapter.setToutiao_list(toutiao_list);
        toutiao_lv.onPullDownRefreshComplete();//下拉刷新完成
        toutiao_lv.onPullUpRefreshComplete();//上拉刷新完成

    }


    //轮播显示的方法
    private List<Lunbo> lunboList = new ArrayList<>();
    private View lunboView;
    private LinearLayout linearLayouticon;
    private ImageView beforicon;
    private TouTiaoViewPager lunbo_viewPager;
    String xiangxiUrl;
    List<XinWen_toutiao.T1348647853363Entity.AdsEntity> listads;//字段listads
    int size = 0;

    private View showLunbo() {
        lunboList.clear();
        listads = null;
        listads = toutiao_list.get(0).getAds();
        if (listads == null) {
            return null;
        }
        size = listads.size();
        lunboView = View.inflate(getActivity(), R.layout.xinwen_toutiao_lunbo, null);
        if (size == 1) {
            TextView title = (TextView) lunboView.findViewById(R.id.toutiao_lunboyitu_title);
            title.setText(toutiao_list.get(0).getAds().get(0).getTitle());
            ImageView lunbo_yitu = (ImageView) lunboView.findViewById(R.id.daohang_lunbo_yitu);
            lunbo_yitu.setVisibility(View.VISIBLE);
            xiangxiUrl = toutiao_list.get(0).getAds().get(0).getUrl();
            XutilsGetData.xUtilsImageiv(lunbo_yitu, toutiao_list.get(0).getAds().get(0).getImgsrc(), getActivity(), false);
            lunbo_yitu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentlunbo2activity(0);
                }
            });
            return lunboView;
        }

        linearLayouticon = (LinearLayout) lunboView.findViewById(R.id.toutiao_lunbo_ll);
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView icon = new ImageView(getActivity());
            params.leftMargin = 10;
            icon.setLayoutParams(params);
            if (i == 0) {
                icon.setImageResource(R.mipmap.toutiao_lunbo_icon2);
            } else {

                icon.setImageResource(R.mipmap.toutiao_lunbo_icon);
            }
            linearLayouticon.addView(icon);
            ImageView imageView = new ImageView(getActivity());
            imageView.setTag(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentlunbo2activity((int) v.getTag());
                }
            });

            XutilsGetData.xUtilsImageiv(imageView, toutiao_list.get(0).getAds().get(i).getImgsrc(), getActivity(), false);
            xiangxiUrl = toutiao_list.get(0).getAds().get(i).getUrl();
            lunboList.add(new Lunbo(toutiao_list.get(0).getAds().get(i).getTitle(), xiangxiUrl, imageView));
        }
        final TextView title = (TextView) lunboView.findViewById(R.id.toutiao_lunbo_title);
        title.setText(lunboList.get(0).getTitle());
       // beforicon = (ImageView) linearLayouticon.getChildAt(size - 1);


        lunbo_viewPager = (TouTiaoViewPager) lunboView.findViewById(R.id.toutiao_lunbo_viewpager);
        lunbo_viewPager.setVisibility(View.VISIBLE);
        lunbo_viewPager.setOffscreenPageLimit(0);
        Lunboadapter lunboadapter = new Lunboadapter();
        lunbo_viewPager.setAdapter(lunboadapter);
        lunbo_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int i = position % size;
                title.setText(lunboList.get(i).getTitle());
            /*    ImageView currmIcon = (ImageView) linearLayouticon.getChildAt(i);
                currmIcon.setImageResource(R.mipmap.toutiao_lunbo_icon2);//设置当前的图片
                beforicon.setImageResource(R.mipmap.toutiao_lunbo_icon);//改变上一个图片
                beforicon = currmIcon;*/
                int count=linearLayouticon.getChildCount();
                for(int j=0;j<count;j++){
                    if(j==i){
                        ((ImageView)linearLayouticon.getChildAt(j)).setImageResource(R.mipmap.toutiao_lunbo_icon2);

                    }else{
                        ((ImageView)linearLayouticon.getChildAt(j)).setImageResource(R.mipmap.toutiao_lunbo_icon);


                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (thread == null) {
            startthreadLunbo();//开启子线程轮播
            thread.start();
        }

       // new Thread(runnable).start();

        return lunboView;
    }


//      Runnable runnable=new Runnable() {
//          @Override
//          public void run() {
//              lunbo_viewPager.setCurrentItem(lunbo_viewPager.getCurrentItem()+1);
//             lunboView.postDelayed(runnable,3000);
//          }
//      } ;


    public void startthreadLunbo() {
        thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //实现循环
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        };


    }

    private android.os.Handler handler = new android.os.Handler() {

        @Override
        public void handleMessage(Message msg) {
            lunbo_viewPager.setCurrentItem(lunbo_viewPager.getCurrentItem() + 1);
        }
    };

    private Thread thread;//子线程轮播对象

    class Lunboadapter extends PagerAdapter {


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(lunboList.get(position % lunboList.size()).getImageView());
                return lunboList.get(position % lunboList.size()).getImageView();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    //轮播工具类
    class Lunbo {
        String title;
        String xiangxiurl;
        ImageView imageView;

        public Lunbo(String title, String xiangxiurl, ImageView imageView) {
            this.title = title;
            this.xiangxiurl = xiangxiurl;
            this.imageView = imageView;
        }

        public String getTitle() {
            return title;
        }

        public String getXiangxiurl() {
            return xiangxiurl;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }

    private void fragmentlunbo2activity(int position) {

        String skiptype = toutiao_list.get(0).getAds().get(position).getTag();
        int lanmutype = XinWen_adapter.getType(skiptype);
        //传入详细页面的数据
        XinWenXiData xinWenXiData = new XinWenXiData();
        xinWenXiData.setLanMuType(daohangtype);
        xinWenXiData.setTitle(toutiao_list.get(0).getAds().get(position).getTitle());

        switch (lanmutype) {
            case XinWen_adapter.TYPE_putong:
            case XinWen_adapter.TYPE_zhuanti:
            case XinWen_adapter.TYPE_zhibo:

                String urlputong = toutiao_list.get(0).getAds().get(position).getUrl();
                xinWenXiData.setUrl(urlputong);
                Intent intentputong = new Intent(getActivity(), WebViewActivity.class);
                intentputong.putExtra("xinwendata", xinWenXiData);
                startActivity(intentputong);
            case XinWen_adapter.TYPE_duotu:
                String urlduotuRight = toutiao_list.get(0).getAds().get(position).getUrl();
                String urlRighBefor = urlduotuRight.substring(urlduotuRight.lastIndexOf("|") - 4);
                //0096|81994    http://c.3g.163.com/photo/api/set/0096/82126.json
                String urlRight = urlRighBefor.replaceAll("\\|", "/");
                String urlduotu = "http://c.3g.163.com/photo/api/set/" + urlRight + ".json";
                xinWenXiData.setUrl(urlduotu);
                Intent intentduotu = new Intent(getContext(), XinWenXiActivity.class);
                intentduotu.putExtra("xinwendata", xinWenXiData);
                startActivity(intentduotu);
                break;
        }

    }

////////////
}
