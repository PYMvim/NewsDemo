package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yls.newsdemo.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import data.Collection;

/**
 * Created by Min on 2017-06-30.
 */

public class CollectionNewsAdapter extends BaseAdapter {
    private Context context;
    private List<Collection> collection_newslist = new ArrayList<>();

    public CollectionNewsAdapter(Context context, List<Collection> collection_newslist){
        this.context = context;
        this.collection_newslist = collection_newslist;
    }

    @Override
    public int getCount() {
        return collection_newslist.size();
    }

    @Override
    public Object getItem(int arg0) {
        return collection_newslist.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int index = position;
        final Collection collection = collection_newslist.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.collection_news_list_item, parent, false);
            TextView CollectionNewsTxt = (TextView) convertView.findViewById(R.id.CollectionNews_txt);
            CollectionNewsTxt.setText(collection.getTitle());
            TextView btn_not_collection = (TextView) convertView.findViewById(R.id.txt_not_collection);
            btn_not_collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collection.setCollection(false);
                    Toast.makeText(context, "删除收藏", Toast.LENGTH_SHORT).show();

                    collection.update(collection.getBMOB_ID(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("------BOMB_UPDATE", "更新成功:" + collection.getUpdatedAt());
                            } else {
                                Log.e("------BOMB_UPDATE", "更新失败：" + e.getMessage());
                            }
                        }
                    });
                }
            });

        }
        return convertView;
    }

    public void changData(List<Collection> collection_newslist) {
        this.collection_newslist = collection_newslist;
        notifyDataSetChanged();
    }
}
