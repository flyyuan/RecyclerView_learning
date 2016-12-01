package com.example.yuan.recycler5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyViewHolder.OnChildClickListener {

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        List<String> list=new ArrayList<>();
        for (int i=0; i<300; i++){
            list.add(String.format(Locale.CHINA,"第%03d条数据",i));
        }//伪装数据
        adapter = new MyAdapter(this,list);//把list的数据传入到adapter
        //LinearLayoutManager第一参数是context，第二个是是方向，第三个是布尔值
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            //网格布局
            //getSpanSize如果是垂直方向就是占多少列，水平方向就是占多少行
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;//position == 0就是第000条数据，返回3，就是第00条数据占3行
                }
                if (position == 1) {
                    return 2;
                }
                return 1;//返回值是1，默认一列，如果是0，就看不见任何东西了
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
        MyItemAnimator animator = new MyItemAnimator();//当不设置动画系统的时候，动画系统会使用默认的动画
        animator.setRemoveDuration(3000);//设置删除动画延迟时间
        recycler.setLayoutManager(gridLayoutManager);//设置布局
        recycler.setItemAnimator(animator);
        recycler.setAdapter(adapter);
        adapter.setOnChildClickListener(this);
    }

    @Override//调用onChildClick接口
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
        adapter.remove(position);
    }
}
