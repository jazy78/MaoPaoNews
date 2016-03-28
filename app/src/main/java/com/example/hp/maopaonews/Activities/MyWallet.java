package com.example.hp.maopaonews.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.maopaonews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/18.
 */
public class MyWallet extends AppCompatActivity {

    private Toolbar toolbar;
    private com.example.hp.maopaonews.GridView.GridView gridView;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initdata();
        gridView = (com.example.hp.maopaonews.GridView.GridView) findViewById(R.id.grid_content);
        myAdapter = new MyAdapter(this, drawableList, stringList);
        gridView.setAdapter(myAdapter);
        setonItemListener();

    }

    private void setonItemListener(){

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Intent intent=new Intent(MyWallet.this,LocalServiceActivity.class);
                                startActivity(intent);
                        break;

                }

            }
        });
    }

    private void initdata() {

        drawableList.add(getResources().getDrawable(R.drawable.car1));
        drawableList.add(getResources().getDrawable(R.drawable.car2));
        drawableList.add(getResources().getDrawable(R.drawable.car3));
        drawableList.add(getResources().getDrawable(R.drawable.car4));
        drawableList.add(getResources().getDrawable(R.drawable.car5));
        drawableList.add(getResources().getDrawable(R.drawable.car6));
        drawableList.add(getResources().getDrawable(R.drawable.car7));
        drawableList.add(getResources().getDrawable(R.drawable.car8));
        drawableList.add(getResources().getDrawable(R.drawable.car9));
        drawableList.add(getResources().getDrawable(R.drawable.car10));
        drawableList.add(new ColorDrawable());
        drawableList.add(new ColorDrawable());

        stringList.add("身边的服务");
        stringList.add("话费充值");
        stringList.add("网易公益");
        stringList.add("网易彩票");
        stringList.add("VIP特权");
        stringList.add("网易车险");
        stringList.add("一元夺宝");
        stringList.add("Q币充值");
        stringList.add("游戏充值");
        stringList.add("网易理财");

        stringList.add("");
        stringList.add("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }


        return true;
    }


    class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Drawable> drawableList;
        private List<String> stringList;

        public MyAdapter(Context context, List<Drawable> drawableList, List<String> stringList) {
            this.context = context;
            this.drawableList = drawableList;
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            return drawableList == null ? 0 : drawableList.size();
        }

        @Override
        public Object getItem(int position) {
            return drawableList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.mywallet_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                viewHolder.text = (TextView) convertView.findViewById(R.id.textview);
                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setImageDrawable(drawableList.get(position));
            viewHolder.text.setText(stringList.get(position));

            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView text;

        }

    }
}
