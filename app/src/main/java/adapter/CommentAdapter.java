package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yls.newsdemo.R;

import java.util.List;

import data.Comments;

/**
 * Created by Min on 2017/5/31.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private List<Comments> mCommentsList;

    public CommentAdapter(List<Comments> mCommentsList){
        this.mCommentsList = mCommentsList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        final Comments comments = mCommentsList.get(position);
        holder.titleView.setText(comments.getComment_content());
//        设置头像 暂未设置
//        Glide.with(holder.imgView.getContext())
//                .load(comments.getHead())
//                .into(holder.imgView);

//        列表Item的点击事件
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    public void changData(List<Comments> mCommentsList) {
        this.mCommentsList = mCommentsList;
        notifyDataSetChanged();
    }
}
