package com.example.hp.maopaonews.CostomAdapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.maopaonews.R;

import java.util.List;

/**
 * Created by hp on 2016/1/19.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ItemViewHolder> {

    private Context context;
    private List<Integer> drawableList;
    private List<String> stringList;

    public RecycleAdapter(Context context, List<Integer> drawables, List<String> strings) {
        this.context = context;
        this.drawableList = drawables;
        this.stringList = strings;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle,null);
        ItemViewHolder holder=new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.imageView.setImageResource(drawableList.get(position));
        holder.text.setText(stringList.get(position));

    }

    @Override
    public int getItemCount() {
        return drawableList==null?0:drawableList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
       private ImageView imageView;
        private TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imagview);
            text=(TextView)itemView.findViewById(R.id.text);

        }
    }
}
