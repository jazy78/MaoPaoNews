package com.example.hp.maopaonews.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.Bean.SearchBean;
import com.example.hp.maopaonews.CostomAdapter.MyGridViewAadapter;
import com.example.hp.maopaonews.CostomAdapter.SearchResultAdapter;
import com.example.hp.maopaonews.CostomProgressDialog.CustomProgressDialog;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.pullrefreshview.PullToRefreshListView;
import com.example.hp.maopaonews.utils.CommonUtil;
import com.example.hp.maopaonews.utils.MyHelper;
import com.example.hp.maopaonews.utils.ServerURL;
import com.example.hp.maopaonews.utils.SharedPreferencesUtil;
import com.example.hp.maopaonews.utils.XutilsGetData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/8.
 */
public class SearchActivity extends AppCompatActivity {
    private SearchView search_view;
    private ImageButton back;
    private ImageView clear_history;
    private RelativeLayout layout_sousuoHis;
    private GridView gv_searchHistory;
    private LinearLayout layoutsearchResult;
    private PullToRefreshListView lv_searchResult;
    private GridView houtWord_gridview;
    private TextView noHotWords;
    private HttpUtils httpUtils;
    private HttpHandler<String> handler;
    private List<String> list = new ArrayList<>();
    private MyGridViewAadapter gridViewAadapter;
    private String keywords;//搜索关键字
    private String tuijian;//推荐热词
    private MyHelper mySqlitehelper;
    private SQLiteDatabase writableDatabase;
    private SearchResultAdapter searchResultAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        InstanceActivtiyApplication();
        bindViews();
        mySqlitehelper = new MyHelper(this);
        writableDatabase = mySqlitehelper.getWritableDatabase();
        queryDB();
    }


    private void bindViews() {
        back = (ImageButton) findViewById(R.id.back);
        search_view = (SearchView) findViewById(R.id.search_view);
        search_view.setSubmitButtonEnabled(true);//是否显示确认搜索按钮
        search_view.setIconified(false);//设置是否显示放大镜
        search_view.onActionViewExpanded();
        clear_history = (ImageView) findViewById(R.id.clear_history);
        layout_sousuoHis = (RelativeLayout) findViewById(R.id.layout_sousuoHis);//搜索历史布局
        //houtWord_gridview = (GridView) findViewById(R.id.houtWord_gridview);//热词推荐
        gv_searchHistory = (GridView) findViewById(R.id.gv_searchHistory);//搜索历史

        layout_sousuoHis = (RelativeLayout) findViewById(R.id.layout_sousuoHis);//搜索历史布局
        gv_searchHistory = (GridView) findViewById(R.id.gv_searchHistory);//搜索历史

        layoutsearchResult = (LinearLayout) findViewById(R.id.searchResult);//搜索结果布局
        lv_searchResult = (PullToRefreshListView) findViewById(R.id.lv_searchResult);//搜索结果

        noHotWords = (TextView) findViewById(R.id.noHotWords);
        houtWord_gridview = (GridView) findViewById(R.id.houtWord_gridview);//热词推荐
        inintHotWordsData();//加载热词推荐数据
        initclick();
    }

    private void inintHotWordsData() {
        noHotWords.setVisibility(View.VISIBLE);
        if (!CommonUtil.isNetWork(this)) {
            String result = SharedPreferencesUtil.GetDate(this, ServerURL.tuiJianWord, "");
            if (!TextUtils.isEmpty(result)) {
                paserData(1, result);

            }
        } else {

            getData(1, ServerURL.tuiJianWord);
        }


    }


    private void getData(int flag, final String url) {

        if (!url.equals("")) {
            httpUtils = new HttpUtils();
            switch (flag) {
                case 1:
                    handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {


                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {

                            if (responseInfo.result != null) {
                                SharedPreferencesUtil.SaveData(SearchActivity.this, url, responseInfo.result);
                                paserData(1, responseInfo.result);
                            }

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(SearchActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case 2:
                    handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            if (responseInfo.result != null) {
                                SharedPreferencesUtil.SaveData(SearchActivity.this, url, responseInfo.result);
                                paserData(2, responseInfo.result);
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(SearchActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });


                    break;


            }

        }
        //////////
    }

    /**
     * {"hotWordList":
     * [
     * {"hotWord":"A股熔断"},
     * {"hotWord":"董浩将退休泪洒舞台"},
     * {"hotWord":"证监会暂停熔断机制"},{"hotWord":"故意丢手机测人心"},
     * {"hotWord":"中国飞人"},{"hotWord":"佩兰正式下课"},{"hotWord":"快播涉黄案开庭"},
     * {"hotWord":"养老金空账超3.5万亿"},{"hotWord":"中国网络宣传这三年"},
     * {"hotWord":"人民币汇率下跌"}
     * ]
     * }
     **/
    private void paserData(int flag, String result) {
        switch (flag) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("hotWordList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String hotWord = jo.getString("hotWord");
                        list.add(hotWord);
                    }

                    gridViewAadapter = new MyGridViewAadapter(list, this,0);
                    houtWord_gridview.setAdapter(gridViewAadapter);
                    noHotWords.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case 2:

                SearchBean searchBean = new Gson().fromJson(result, SearchBean.class);
                layout_sousuoHis.setVisibility(View.GONE);//隐藏搜索历史
                layoutsearchResult.setVisibility(View.VISIBLE);//显示搜索结果布局
                progressDialog.dismiss();
                layoutsearchResult.setVisibility(View.VISIBLE);
                searchResultAdapter = new SearchResultAdapter(searchBean.doc.result, this);
                lv_searchResult.getRefreshableView().setAdapter(searchResultAdapter);

                break;


        }

        /////
    }

    private void initclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        houtWord_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tuijian = gridViewAadapter.getList().get(position);
                keywords = tuijian;
                search_view.setQuery(tuijian, true);
            }
        });

        search_view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        //搜索框文本变化监听
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                keywords = query;
                initSearchNews(ServerURL.searchUrl1 + keywords + ServerURL.searchUrl2);
                ContentValues values = new ContentValues();
                values.put("url", ServerURL.searchUrl1 + keywords + ServerURL.searchUrl2);
                values.put("searchWord", keywords);
                writableDatabase.insert("searchHistory", null, values);
                Log.d("EEE", "点击我就执行");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {//判断为空时
                    layoutsearchResult.setVisibility(View.GONE);
                    queryDB();
                    Log.d("EEE", "我也zhixingle");
                }


                return true;
            }
        });
        gv_searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = adapterHistory.getList().get(i);
                Toast.makeText(SearchActivity.this, word, Toast.LENGTH_SHORT).show();
                search_view.setQuery(word, false);
               // initSearchNews(ServerURL.searchUrl1 + word + ServerURL.searchUrl2);//执行新闻搜索请求
            }
        });
        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "正在清空搜索历史...", Toast.LENGTH_SHORT).show();
                layout_sousuoHis.setVisibility(View.GONE);
                //返回 1 表示全部删完
                int delete = writableDatabase.delete("searchHistory", null, null);
                if (delete >= 1) {
                    Toast.makeText(SearchActivity.this, "搜索历史清空完成...", Toast.LENGTH_SHORT).show();

                }
            }
        });
        lv_searchResult.setPullLoadEnabled(false);
        lv_searchResult.setPullRefreshEnabled(false);
        lv_searchResult.setScrollLoadEnabled(false);
        lv_searchResult.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String docid=searchResultAdapter.getList().get(position).docid;
                Intent intent=new Intent(SearchActivity.this,YueDuDetialActivity.class);
                intent.putExtra("yueduDetial", docid) ;
                startActivity(intent);
                Log.d("GGG","decoid="+docid);

                overridePendingTransition(R.anim.search_fade_in,R.anim.search_fade_out);
                layoutsearchResult.setVisibility(View.GONE);
                queryDB();
            }
        });

    }

    CustomProgressDialog progressDialog;

    private void initSearchNews(String url) {

        progressDialog = new CustomProgressDialog(this, "数据加载中...", R.anim.animation_frame);
        if (!CommonUtil.isNetWork(this)) {
            if (keywords != null) {

                String searchResult = SharedPreferencesUtil.GetDate(this, url, "");
                if (!TextUtils.isEmpty(searchResult)) {

                    paserData(2, searchResult);
                }

            }

        } else {
            if (keywords != null) {
                getData(2, url);
                progressDialog.show();
            }

        }


    }

    MyGridViewAadapter adapterHistory = null;

    private void queryDB() {

        String searchWord = null;
        List<String> historyList = new ArrayList<>();

        Cursor cursor = writableDatabase.query("searchHistory", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            searchWord = cursor.getString(cursor.getColumnIndex("searchWord"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            historyList.add(searchWord);
        }
        cursor.close();
        int size = historyList.size();
        Log.d("EEE", "size=" + size);
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        gv_searchHistory.setLayoutParams(params);
        gv_searchHistory.setColumnWidth(itemWidth);
        gv_searchHistory.setHorizontalSpacing(5);
        gv_searchHistory.setStretchMode(GridView.NO_STRETCH);
        gv_searchHistory.setNumColumns(size);//所有数据都放在一行
        if (historyList.size() > 0) {
            adapterHistory = new MyGridViewAadapter(historyList, this,1);
            gv_searchHistory.setAdapter(adapterHistory);
            layout_sousuoHis.setVisibility(View.VISIBLE);
        }
    }


    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
}
