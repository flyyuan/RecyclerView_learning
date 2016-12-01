package com.example.yuan.recycler5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
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
        animator.setSupportsChangeAnimations(true);//设置是否支持动画
        animator.setMoveDuration(   3000);//设置删除动画延迟时间
        recycler.setLayoutManager(linearLayoutManager);//设置布局
        recycler.setItemAnimator(animator);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);//可以再控件之上绘制东西
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                c.drawBitmap(bitmap ,400 , 400 , null);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawColor(Color.BLACK);//绘制前景，甚至可以加一个动画
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.set(0 , 5 * position ,0 , 5 * position);//间距的宽高，这样可以灵活地去设定间距
            }
        });
        adapter.setOnChildClickListener(this);
    }

    @Override//调用onChildClick接口
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
        //adapter.remove(position);
        //adapter.add(position,"新增数据");
        adapter.change(position,"新增数据");
    }
}
