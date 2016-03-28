package com.example.hp.maopaonews.content_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.maopaonews.Activities.YueDuDetialActivity;
import com.example.hp.maopaonews.Bean.YueDuBean;
import com.example.hp.maopaonews.CostomAdapter.YueDuAdapter;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.pullrefreshview.PullToRefreshBase;
import com.example.hp.maopaonews.pullrefreshview.PullToRefreshListView;
import com.example.hp.maopaonews.utils.CommonUtil;
import com.example.hp.maopaonews.utils.ServerURL;
import com.example.hp.maopaonews.utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/13.
 */
public class YueDuFrament extends Fragment {
    private PullToRefreshListView rListView;
    private View view;
    private HttpUtils httpUtils;
    private HttpHandler<String> httpHandler;
    private List<YueDuBean.推荐Entity> list = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            if (view == null) {

                view = initView(inflater);
            }
             return view;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.yuedu_content, null, false);
        httpUtils = new HttpUtils();
        initPullTorefresh(inflate);
        return inflate;

    }

    public void initPullTorefresh(final View inflate) {
        rListView = (PullToRefreshListView) inflate.findViewById(R.id.refresh);
        initdata();
        rListView.setPullLoadEnabled(false);  //上拉加载，屏蔽
        rListView.setScrollLoadEnabled(true); //设置滚动加载可用
        rListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(CommonUtil.isNetWork(getActivity())){
                   getData(ServerURL.yueDuURL,true);
                    String string=CommonUtil.getStringData();
                    rListView.setLastUpdatedLabel(string);
                }else {

                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();
                    rListView.onPullDownRefreshComplete();
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                  if(CommonUtil.isNetWork(getActivity())){
                      getData(ServerURL.yueDuURLJiaZai,false);

                  }else {
                      Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();
                      rListView.onPullUpRefreshComplete();

                  }
            }
        });

        rListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String replayid=adapter.getList().get(position).replyid;
                Intent intent=new Intent(getActivity(), YueDuDetialActivity.class);
                intent.putExtra("yueduDetial", replayid);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zcdh_set_in,R.anim.zcdh_set_out);
            }
        });



    }

    private void initdata() {
        if (!CommonUtil.isNetWork(getActivity())) {
        String result=SharedPreferencesUtil.GetDate(getActivity(),ServerURL.yueDuURL,"");

            if(!TextUtils.isEmpty(result)){

                paserData(result,false);
            }
        } else {

            getData(ServerURL.yueDuURL, false);
        }

    }

    private void getData(final String url, final boolean isRefresh) {
        if (!url.equals("")) {

           httpHandler= httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {

                    SharedPreferencesUtil.SaveData(getActivity(), url, responseInfo.result);
                    paserData(responseInfo.result, isRefresh);//Gson解析数据
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private YueDuAdapter adapter;

    private void paserData(String result, boolean isRefresh) {
        YueDuBean yueBean = new Gson().fromJson(result, YueDuBean.class);
       // list.addAll(yueBean.推荐);
        if (isRefresh) {
            adapter = new YueDuAdapter(getActivity(), yueBean.推荐);
            rListView.getRefreshableView().setAdapter(adapter);
        } else {
            if (adapter == null) {
                adapter = new YueDuAdapter(getActivity(), yueBean.推荐);
                rListView.getRefreshableView().setAdapter(adapter);

            } else {

                list.addAll(adapter.getList());
                adapter.setList(list);
            }


        }

        rListView.onPullUpRefreshComplete();
        rListView.onPullDownRefreshComplete();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(httpUtils!=null){
            if(httpHandler!=null)
            httpHandler.cancel();

        }
    }

    ////////////////
}
