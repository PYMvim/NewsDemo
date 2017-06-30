package fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.yls.newsdemo.Activity.CollectionActivity;
import com.example.yls.newsdemo.Activity.NewsContentActivity;
import com.example.yls.newsdemo.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.NewsAdapter;
import data.News;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int MSG_GET_NEWS = 10001;
    private int mPage;
    private List<News.ResultBean.NewsBean> newsList = new ArrayList<News.ResultBean.NewsBean>();
    private NewsAdapter adapter;
    private boolean isADD = false;
    private android.os.Handler mHandler;
    private ListView listView;
    private Button btn_news;
    private Button btn_find;
    private Button btn_collection;

    private final String URL1 = "http://v.juhe.cn/toutiao/index?type=top&key=951120925db65e7801656e888efa6c4c";
    private final String URL2 = "http://v.juhe.cn/toutiao/index?type=junshi&key=951120925db65e7801656e888efa6c4c";
    private final String URL3 = "http://v.juhe.cn/toutiao/index?type=tiyu&key=951120925db65e7801656e888efa6c4c";
    private final String URL4 = "http://v.juhe.cn/toutiao/index?type=guoji&key=951120925db65e7801656e888efa6c4c";

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();

        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initHandler();
        mPage = getArguments().getInt(ARG_PAGE);
    }

    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == MSG_GET_NEWS){
                    adapter.changData(newsList);
                    return  true;
                }
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        listView = (ListView) view.findViewById(R.id.News_lv);
        btn_collection = (Button) view.findViewById(R.id.btn_MyCollection);
        btn_find = (Button) view.findViewById(R.id.btn_find);
        btn_news = (Button) view.findViewById(R.id.btn_news);

        btn_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_collection.setTextColor(Color.BLACK);
                btn_collection.setTextSize(20);

                btn_find.setTextColor(Color.WHITE);
                btn_find.setTextSize(15);
                btn_news.setTextColor(Color.WHITE);
                btn_news.setTextSize(15);

                Intent intent = new Intent();
                intent.setClass(getContext(), CollectionActivity.class);
                startActivity(intent);
            }
        });

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_find.setTextColor(Color.BLACK);
                btn_find.setTextSize(20);

                btn_collection.setTextColor(Color.WHITE);
                btn_collection.setTextSize(15);
                btn_news.setTextColor(Color.WHITE);
                btn_news.setTextSize(15);
            }
        });

        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_news.setTextColor(Color.BLACK);
                btn_news.setTextSize(20);

                btn_collection.setTextColor(Color.WHITE);
                btn_collection.setTextSize(15);
                btn_find.setTextColor(Color.WHITE);
                btn_find.setTextSize(15);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsContentActivity.class);
                intent.putExtra("StrUrl",newsList.get(position).getUrl());
                intent.putExtra("title",newsList.get(position).getTitle());
                startActivity(intent);
            }
        });

        getNewsFromJuhe();
        return view;
    }

    private void getNewsFromJuhe() {
        OkHttpClient client = new OkHttpClient();
        Request request;
        if (!isADD) {
            String url;
            switch (mPage) {
                case 1:
                    request = new Request.Builder().url(URL1).build();
                    Log.e("toutiao","toutiao");
                    break;
                case 2:
                    request = new Request.Builder().url(URL2).build();
                    Log.e("junshi","junshi");
                    break;
                case 3:
                    request = new Request.Builder().url(URL3).build();
                    Log.e("tiyu","tiyu");
                    break;
                case 4:
                    request = new Request.Builder().url(URL4).build();
                    Log.e("guoji","guoji");
                    break;
                default:
                    request = new Request.Builder().url(URL1).build();
                    break;
            }

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("AAA", "GET DATA FAILED");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                Log.e("AAABBBCCCDDDEEE",response.body().string());
                    Gson gson = new Gson();
                    News news = gson.fromJson(response.body().string(), News.class);
                    newsList = news.getResult().getData();
                    mHandler.sendEmptyMessage(MSG_GET_NEWS);
                }
            });
            isADD = !isADD;
        }
        adapter = new NewsAdapter(getContext(), newsList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
