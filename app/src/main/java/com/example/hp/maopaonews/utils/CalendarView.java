package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by hp on 2016/1/18.
 */
public class CalendarView extends LinearLayout {
    ItemListemer itemListemer;
    private Context context;
    private GridView dateGrid;
    private TextView txtTitle;
    private final Calendar calendar = Calendar.getInstance();
    private static  final  int MAX_DAY_COUNT=42;
    private DayInfo[]  dayInfos=new DayInfo[MAX_DAY_COUNT];

    private String titleformat;
    private CalendarAdapter calendarAdapter;
    //日期信息实体类

    public class DayInfo{

        public int day;
        public DayType dayType;

        @Override
        public String toString() {
            return String.valueOf(day);
        }
    }

    public void setItemListemer(ItemListemer itemListemer){

        this.itemListemer=itemListemer;
    }

    //日期的类型
    public enum DayType{
        DAY_TYPE_NONE(0),
        DAY_TYPE_FORE(1),
        DAY_TYPE_NOW(2),
        DAY_TYPE_NEXT(3);

        private int value;

        DayType(int value){

            this.value=value;
        }
        public  int getValue(){

            return value;
        }

    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        showCalendar(calendar);
    }

    public CalendarView(Context context) {
        super(context);
        init(context);
        showCalendar(calendar);
    }

    private View.OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private void init(Context context) {
        this.context = context;
        View rootView = View.inflate(context, R.layout.widget_calendar, null);
        dateGrid = (GridView) rootView.findViewById(R.id.widgetCalendar_calendar);
        txtTitle = (TextView) rootView.findViewById(R.id.widgetCalendar_txtTitle);
        rootView.findViewById(R.id.widgetCalendar_imgForeMonth).setOnClickListener(listener);
        rootView.findViewById(R.id.widgetCalendar_imgNextMonth).setOnClickListener(listener);
        this.setOrientation(VERTICAL);
        this.addView(rootView);
    }

    //显示日历数据
    private void showCalendar(Calendar calendar) {
        int year= calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;//从零开始
        Log.d("KKK","month="+month);

        int centry=Integer.valueOf(String.valueOf(year).substring(0,2));
        int tmpYear=Integer.valueOf(String.valueOf(year).substring(2,4));
        if(month==1||month==2){
            tmpYear-=1;
            month+=12;

        }
        //计算该月的第一天是星期几
        int firstOfWeek = (tmpYear + (tmpYear/4) + centry/4-2*centry+26*(month+1)/10)%7;
        Log.d("KKK","firstOfWeek="+firstOfWeek);
        if(firstOfWeek<=0)
            firstOfWeek = 7 + firstOfWeek;//处理星期的显示1，2，3，4，5，6，7
        //计算第一天所在的索引值,如果该天是周一,则做换行处理
        final int firstDayIndex = firstOfWeek == 1 ? 7 : firstOfWeek - 1;
        final  int  dayCount=getDayCount(year,month);
        //处理本月的数据
        Log.d("KKK","firstDayIndex="+firstDayIndex);
        Log.d("KKK","dayCount="+dayCount);
        for(int i=firstDayIndex;i<firstDayIndex+dayCount;i++){
            if(dayInfos[i]==null){
                dayInfos[i]=new DayInfo();

            }
            dayInfos[i].day=i-firstDayIndex+1;
            dayInfos[i].dayType=DayType.DAY_TYPE_NOW;
        }
        //处理前一个月的数据
        calendar.add(Calendar.MONTH,-1);
        year = calendar.get(Calendar.YEAR);//获取年份
        month = calendar.get(Calendar.MONTH) + 1;//获取月份

        final  int foreDayCount=getDayCount(year,month);
        for (int i = 0; i < firstDayIndex; i++) {
            if (dayInfos[i] == null) {
                dayInfos[i]=new DayInfo();
            }
            dayInfos[i].day = foreDayCount  + i- firstDayIndex + 1;
            dayInfos[i].dayType= DayType.DAY_TYPE_FORE;
        }
        //处理下个月的数据
        for(int i=0;i<MAX_DAY_COUNT-dayCount-firstDayIndex;i++){


            if (dayInfos[firstDayIndex + dayCount + i] == null) {
                dayInfos[firstDayIndex+dayCount+i]=new DayInfo();
            }

            dayInfos[firstDayIndex + dayCount + i].day = i + 1;
            dayInfos[firstDayIndex + dayCount + i].dayType = DayType.DAY_TYPE_NEXT;
        }

        calendar.add(Calendar.MONTH,1);
        titleformat=new SimpleDateFormat("yyyy年MM月").format(calendar.getTime());
        txtTitle.setText(titleformat);
        calendarAdapter=new CalendarAdapter(context,dayInfos);
        dateGrid.setAdapter(calendarAdapter);
    }

    private int getDayCount(int year,int month){
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
                return 31;
            case 2:
            case 14:
                return isLeapYear(year)?28:29;


            case 4:
            case 6:
            case 9:
            case 11:
                return 30;

        }

        return 0;
    }

    private boolean isLeapYear(int year){
        return !((year%4==0 && year%100!=0)||year%400==0);

    }

    class  CalendarAdapter extends BaseAdapter{
         private Context context;
        private int positionItem;
        private List<DayInfo> dayInfoList=new ArrayList<>();

        public CalendarAdapter(Context context,DayInfo[] dayInfos)  {
            this.context=context;
            if(dayInfos!=null && dayInfos.length>0){
                //将数组装换为集合
                this.dayInfoList.addAll(Arrays.asList(dayInfos));

            }
        }

        @Override
        public int getCount() {
            return dayInfoList==null?0:dayInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return dayInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final DayInfo item = dayInfoList.get(position);
            if (convertView == null) {
                convertView = new TextView(context);
                AbsListView.LayoutParams cellLayoutParams=new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.MATCH_PARENT);
                convertView.setLayoutParams(cellLayoutParams);
                TextView txtCell = (TextView) convertView;
                txtCell.setGravity(Gravity.CENTER);
                txtCell.setPadding(8, 12, 8, 12);
                txtTitle.getPaint().setFakeBoldText(true);
                txtCell.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17f);
            }
            TextView txtItem=(TextView)convertView;
            txtItem.setText(item.toString());
            if(item.dayType==DayType.DAY_TYPE_FORE||item.dayType==DayType.DAY_TYPE_NEXT){

                txtItem.setTextColor(Color.DKGRAY);
            }else {
                txtItem.setTextColor(Color.BLACK);

            }

            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTimeInMillis(calendar.getTimeInMillis());//获取当前时间的毫秒表现形式
            tmpCalendar.set(Calendar.DAY_OF_MONTH, item.day);
            if (isDateEqual(Calendar.getInstance(), tmpCalendar) && item.dayType == DayType.DAY_TYPE_NOW) {
                txtItem.setBackground(new ColorDrawable(Color.parseColor("#66aaff")));
            } else if (item.dayType==DayType.DAY_TYPE_NOW){
                txtItem.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                if(position==positionItem){
                    txtItem.setBackgroundDrawable(new ColorDrawable(Color.RED));
                }
            } else {
                    txtItem.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));

                }


            OnClickListener listener=new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (item.dayType){
                        case DAY_TYPE_FORE:
                        calendar.add(Calendar.MONTH, -1);
                        showCalendar(calendar);
                            break;
                        case DAY_TYPE_NOW:
                            positionItem=position;
                            calendarAdapter.notifyDataSetChanged();
                            String s = txtTitle.getText().toString()+item.toString()+"日";
                            itemListemer.itemlistener(s);
                            Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        case DAY_TYPE_NEXT:
                            calendar.add(Calendar.MONTH,1);
                            showCalendar(calendar);
                            break;
                    }


                }
            };

            txtItem.setOnClickListener(listener);
            return convertView;
        }
    }
    /**
     * 判断两个Calendar中的日期是否相等
     */
    private boolean isDateEqual(Calendar calendar,Calendar calendar1){
        return (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == calendar1.get(Calendar.DATE));
    }

}
