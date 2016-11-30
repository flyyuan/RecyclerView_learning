package com.example.yuan.recycler5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
        recycler.setAdapter(adapter);
        adapter.setOnChildClickListener(this);
    }

    @Override//调用onChildClick接口
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
        adapter.remove(position);
    }
}
