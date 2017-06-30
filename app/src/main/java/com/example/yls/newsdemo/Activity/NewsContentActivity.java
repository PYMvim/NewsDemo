package com.example.yls.newsdemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yls.newsdemo.R;
import com.tencent.smtt.sdk.WebView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import data.Collection;
import data.Comments;

public class NewsContentActivity extends Activity {
    private WebView News_Web;
    private EditText edt_comment;
    private Button btn_sendcomment;
    private Button btn_comment;
    private Button btn_Collection;
    private Comments mComments;
    private boolean IsCollection = false;
    private Collection This_collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        init();
        findViews();

    }

    private void init() {
        //第一：默认初始化
        Bmob.initialize(this, "c3dd12a099c15e8590611829664012a5");

        QueryCollection();
    }

    private void QueryCollection() {
        BmobQuery<Collection> query = new BmobQuery<Collection>();
        //查询IsCollection为true的数据
        query.addWhereEqualTo("url", getIntent().getStringExtra("StrUrl"));
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> object, BmobException e) {
                if(e==null){
                    Log.e("------BMOB_QUERY","查询成功：共"+object.size()+"条数据。");
                    for (Collection collection : object) {
                        //获得数据的objectId信息
                        //判断是否收藏该新闻
                        This_collection = collection;
                        if(This_collection.isCollection()){
                            btn_Collection.setTextColor(Color.YELLOW);
                            IsCollection = true;
                        }
                        collection.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        collection.getCreatedAt();
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void findViews() {
        News_Web = (WebView) findViewById(R.id.News_web);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        btn_sendcomment = (Button) findViewById(R.id.btn_Sendcomment);
        btn_comment = (Button) findViewById(R.id.btn_comment);
        btn_Collection = (Button) findViewById(R.id.btn_Collection);

        final String url = getIntent().getStringExtra("StrUrl");
        News_Web.loadUrl(url);

        btn_Collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IsCollection){
                    btn_Collection.setTextColor(Color.YELLOW);
                    Toast.makeText(NewsContentActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                    final Collection mCollection = new Collection();
                    mCollection.setUrl(url);
                    mCollection.setTitle(getIntent().getStringExtra("title"));
                    mCollection.setCollection(true);
                    if(This_collection==null) {
                        mCollection.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    Log.e("------BMOB_ADD_DATA", "添加数据成功，返回objectId为：" + objectId);
                                    mCollection.setBMOB_ID(objectId);
                                    //保存对应的bmob的ibjectId
                                    mCollection.update(mCollection.getBMOB_ID(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                QueryCollection();
                                                Log.e("------BOMB_UPDATE", "更新成功:" + mCollection.getUpdatedAt());
                                            } else {
                                                Log.e("------BOMB_UPDATE", "更新失败：" + e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(NewsContentActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        This_collection.setCollection(!IsCollection);
                        This_collection.update(This_collection.getBMOB_ID(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.e("------BOMB_UPDATE", "收藏成功:" + This_collection.getUpdatedAt());
                                } else {
                                    Log.e("------BOMB_UPDATE", "收藏成功更新失败：" + e.getMessage());
                                }
                            }
                        });
                    }
                }else {
                        btn_Collection.setTextColor(Color.GRAY);
                        Toast.makeText(NewsContentActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();

                        This_collection.setCollection(!IsCollection);
                        This_collection.update(This_collection.getBMOB_ID(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.e("------BOMB_UPDATE", "取消收藏:" + This_collection.getUpdatedAt());
                                } else {
                                    Log.e("------BOMB_UPDATE", "取消收藏更新失败：" + e.getMessage());
                                }
                            }
                        });
                }
                IsCollection = !IsCollection;
                Log.e("IsCollection",IsCollection+"");
            }
        });

        btn_sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Comment_content = edt_comment.getText().toString();
                Intent intent = new Intent(NewsContentActivity.this,CommentActivity.class);
                intent.putExtra("Comment",Comment_content);
                intent.putExtra("StrUrl",url);

                mComments = new Comments(Comment_content);
                mComments.setCollection(IsCollection);
                mComments.setComment_content(Comment_content);
                mComments.setUrl(url);
                mComments.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(NewsContentActivity.this, "添加数据成功，返回objectId为："+objectId, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NewsContentActivity.this,"创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                startActivity(intent);
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsContentActivity.this,CommentActivity.class);
                intent.putExtra("StrUrl",url);
                startActivity(intent);
            }
        });
    }
}
