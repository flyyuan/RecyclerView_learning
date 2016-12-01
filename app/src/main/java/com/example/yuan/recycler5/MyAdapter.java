package com.example.yuan.recycler5;

import android.content.Context;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yuan on 2016/11/30.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    private MyViewHolder.OnChildClickListener listener;
    private RecyclerView recyclerView;

    public void setOnChildClickListener(MyViewHolder.OnChildClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);//找到item
        view.setOnClickListener(this);
        return new MyViewHolder(view);//返回到MyViewHolder
    }

    @Override//Called by RecyclerView when it starts observing this Adapter.
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;//recyclerview提为一个属性
    }

    @Override//Called by RecyclerView when it stops observing this Adapter.
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView=null;//为recycleview置空
    }

    @Override//绑定位置
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.text.setText(list.get(position));//把文本传入holder
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override//多布局复用的方法重写，返回的是任意值
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override//点击
    public void onClick(View v) {
        //判断recyclerview是否为空
        if (recyclerView!=null&&listener!=null) {
            int position = recyclerView.getChildAdapterPosition(v);//点击获取获取到Adapter的位置，把v传进去
            listener.onChildClick(recyclerView,v,position,list.get(position));
        }
    }
    //从指定位置删除
    public void remove(int position){
        //list.remove(position);//从指定位置删除
        //提醒adapter刷新，recycleview中调用刷新方法不可以是notifyDateSetChanged，要告诉了recycleview做了什么样的操作才可以
        //notifyDataSetChanged();
        notifyItemRemoved(position);//notifyItemRemoved的删除带有动画
    }
    public void add(int position,String data){
        list.add(position,data);
        notifyItemInserted(position);
    }
    public void change(int position,String date){
        list.remove(position);
        list.add(position,date);
       notifyItemChanged(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = ((TextView) itemView.findViewById(R.id.item_text));
        }
        //自定义OnClickListener接口
        public interface OnChildClickListener{
            //（父类，本身的view，位置，数据）
            void onChildClick(RecyclerView parent,View view,int position,String data);
        }
    }
}
