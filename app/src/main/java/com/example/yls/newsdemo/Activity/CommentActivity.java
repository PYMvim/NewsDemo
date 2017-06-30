package com.example.yls.newsdemo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yls.newsdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.CommentAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import data.Comments;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CommentAdapter mCommentAdapter;
    private List<Comments> mCommentList = new ArrayList<Comments>();
    private EditText edt_comment_content;
    private Button btn_comment_Sendcomment;
    private Comments mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        CommentGet();

        FindViews();

//        String Str_comment = getIntent().getStringExtra("Comment");
//        if(Str_comment != null){
//            Log.e("Str_commentStr_comment",Str_comment);
//            Comments comment = new Comments(Str_comment);
//            mCommentList.add(comment);
//        }

        mRecyclerView = (RecyclerView) findViewById(R.id.comment_listview);
        mCommentAdapter = new CommentAdapter(mCommentList);
        mRecyclerView.setAdapter(mCommentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));

    }

    private void FindViews() {
        final String url = getIntent().getStringExtra("StrUrl");

        edt_comment_content = (EditText) findViewById(R.id.edt_comment_content);

        btn_comment_Sendcomment = (Button) findViewById(R.id.btn_comment_Sendcomment);
        btn_comment_Sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getIntent().getStringExtra("StrUrl");
                String Comment_content = edt_comment_content.getText().toString();

                mComments = new Comments(Comment_content);
                mComments.setComment_content(Comment_content);
                mComments.setUrl(url);
                mComments.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
//                            Toast.makeText(CommentActivity.this, "添加数据成功，返回objectId为："+objectId, Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("CommentActivity","创建数据失败：" + e.getMessage());
//                            Toast.makeText(CommentActivity.this,"创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Collections.reverse(mCommentList);
                mCommentList.add(mComments);
                Collections.reverse(mCommentList);
                mCommentAdapter.changData(mCommentList);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//开启或者关闭软键盘
                edt_comment_content.setText("");
            }
        });
    }

    private void CommentGet() {
        String url = getIntent().getStringExtra("StrUrl");
        BmobQuery<Comments> query = new BmobQuery<Comments>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("url", url);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<Comments>() {
            @Override
            public void done(List<Comments> object, BmobException e) {
                if(e==null){
                    mCommentList.clear();
                    Toast.makeText(CommentActivity.this, "查询成功：共"+object.size()+"条评论。", Toast.LENGTH_SHORT).show();
                    for (Comments comment : object) {
                        //获得数据的objectId信息
                        comment.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        comment.getCreatedAt();
                        //获取数据
                        mCommentList.add(comment);
                        Collections.reverse(mCommentList);
                        mCommentAdapter.changData(mCommentList);
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCommentAdapter.changData(mCommentList);
    }
}
