package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yls.newsdemo.R;

import java.util.List;

import data.News;

/**
 * Created by yls on 2017/5/16.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<News.ResultBean.NewsBean> newsList;

    public NewsAdapter(Context context, List<News.ResultBean.NewsBean> newsList){
        this.context = context;
        this.newsList = newsList;
    }


    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return newsList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        News.ResultBean.NewsBean news = newsList.get(postion);
        if(convertView == null) {
            if (postion%3==0 && news.getThumbnail_pic_s02() != null && news.getThumbnail_pic_s03() != null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.news_threeimg_item, parent, false);
                TextView newsTxt = (TextView) convertView.findViewById(R.id.News_txt);
                ImageView imgHead1 = (ImageView) convertView.findViewById(R.id.News_img1);
                ImageView imgHead2 = (ImageView) convertView.findViewById(R.id.News_img2);
                ImageView imgHead3 = (ImageView) convertView.findViewById(R.id.News_img3);
////                设置新闻图片
                Glide.with(imgHead1.getContext()).load(news.getThumbnail_pic_s()).into(imgHead1);
                Glide.with(imgHead2.getContext()).load(news.getThumbnail_pic_s02()).into(imgHead2);
                Glide.with(imgHead3.getContext()).load(news.getThumbnail_pic_s03()).into(imgHead3);
                newsTxt.setText(news.getTitle());
                return convertView;
            }
                //设置新闻图片
                convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
                TextView newsTxt = (TextView) convertView.findViewById(R.id.News_txt);
                ImageView imgHead = (ImageView) convertView.findViewById(R.id.News_img);
                Glide.with(imgHead.getContext()).load(news.getThumbnail_pic_s()).into(imgHead);
                newsTxt.setText(news.getTitle());
        }
        return convertView;
    }

    public void changData(List<News.ResultBean.NewsBean> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
