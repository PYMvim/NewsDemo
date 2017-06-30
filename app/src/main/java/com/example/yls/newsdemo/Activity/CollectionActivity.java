package com.example.yls.newsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yls.newsdemo.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CollectionNewsAdapter;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import data.Collection;

public class CollectionActivity extends AppCompatActivity {
    private static final int UPDATA_LIST = 1001;
    private ListView Collection_lv_news;
    private CollectionNewsAdapter adapter;
    private List<Collection>  collection_newslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        init();
    }

    private void FindViews() {
        Collection_lv_news = (ListView) findViewById(R.id.Collection_lv_news);
        Collection_lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this, NewsContentActivity.class);
                intent.putExtra("StrUrl",collection_newslist.get(position).getUrl());
                startActivity(intent);
            }
        });

        adapter = new CollectionNewsAdapter(CollectionActivity.this, collection_newslist);
        Collection_lv_news.setAdapter(adapter);
    }

    private void init() {
        //第一：默认初始化
        Bmob.initialize(this, "c3dd12a099c15e8590611829664012a5");

        FindViews();
        QueryCollectionNews();
    }

    public void QueryCollectionNews() {
        BmobQuery<Collection> query = new BmobQuery<Collection>();
        //查询IsCollection为true的数据
        query.addWhereEqualTo("IsCollection", true);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> object, BmobException e) {
                if(e==null){
                    collection_newslist.clear();
//                    toast("查询成功：共"+object.size()+"条数据。");
                    for (Collection collection : object) {
                        //添加数据
                        collection_newslist.add(collection);
                        adapter.changData(collection_newslist);
                        adapter.notifyDataSetChanged();
                        //获得数据的objectId信息
                        collection.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        collection.getCreatedAt();
                    }
                }else{
                    Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        QueryCollectionNews();
        adapter.changData(collection_newslist);
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
