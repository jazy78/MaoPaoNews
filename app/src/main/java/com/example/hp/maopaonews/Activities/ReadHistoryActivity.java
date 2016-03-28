package com.example.hp.maopaonews.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.maopaonews.Bean.NewsBean;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.utils.CalendarView;
import com.example.hp.maopaonews.utils.DateTime;
import com.example.hp.maopaonews.utils.ItemListemer;
import com.example.hp.maopaonews.utils.MySqlOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hp on 2016/1/18.
 */
public class ReadHistoryActivity extends AppCompatActivity implements ItemListemer,AdapterView.OnItemClickListener {
    private CalendarView calendarView;
    private ListView listView;
    private ArrayList<NewsBean>  arrayList;
    private MyReadAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_history);
        calendarView=(CalendarView)findViewById(R.id.view);
        calendarView.setItemListemer(this);
        listView = (ListView) findViewById(R.id.listView);
        String date = DateTime.getDate();
        arrayList=getlistDate(date);

        adapter=new MyReadAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }
   public ArrayList getlistDate(String date){
       arrayList = new ArrayList();
       MySqlOpenHelper mySqlOpenHelper=new MySqlOpenHelper(this);
       SQLiteDatabase db=mySqlOpenHelper.getWritableDatabase();
       Cursor cursor=db.query("read_date",null,"date=?",new String[]{date},null,null,null);
       while (cursor.moveToNext()){
           String num = cursor.getString(2) + "";
           String title = cursor.getString(3) + "";
           String url = cursor.getString(4) + "";
           String replaycount = cursor.getString(5) + "";
           String lanMuType = cursor.getString(6) + "";
           arrayList.add(new NewsBean(date, num, title, url, replaycount, lanMuType));
       }
       return  arrayList;
   }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    class  MyReadAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return arrayList.size() == 0 ? 0 : arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           if(convertView==null){
               convertView=View.inflate(ReadHistoryActivity.this,R.layout.read_item,null);

           }

            TextView title = (TextView) convertView.findViewById(R.id.read_item);
            title.setText(arrayList.get(position).getTitle());

            return convertView;
        }
    }

    @Override
    public void itemlistener(String s) {
          arrayList.clear();
          arrayList=getlistDate(s);

        if(arrayList.size()==0){

            listView.setVisibility(View.GONE);
        }else {

            listView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }

    }
}
