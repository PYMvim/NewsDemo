package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yls.newsdemo.R;

/**
 * Created by Min on 2017/5/31.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder{
    ImageView imgView;
    TextView titleView;
    View view;

    public CommentViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        imgView = (ImageView) itemView.findViewById(R.id.comment_img);
        titleView = (TextView) itemView.findViewById(R.id.comment_txt);
    }
}
