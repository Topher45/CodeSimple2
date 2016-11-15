package com.training.edison.codesimple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private static final String TAG = "HomeFragment";
    private List<ArticleBean> articleBeanList = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_home, container, false);
        new Thread(runnable).start();
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        return parentView;
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Document doc;
            try {
                //URL加载一个Document
                doc = Jsoup.connect("http://codesimple.farbox.com/").get();
                //使用DOM方法来遍历文档，并抽取元素
                //每一篇文章
                Elements elements = doc.select("article.post");
                for (Element element : elements) {
                    Elements articleInfo = element.select("h1.title");//标题信息
                    Log.i(TAG, "run: articleInfo: " + articleInfo);
                    String title = element.select("h1.title").text().trim();//标题
                    String link = element.select("h1.title").select("a").attr("abs:href"); //相对链接
                    String time = element.select("div.date").text();
                    Elements articleContent = element.select("div.p_part");
                    Log.i(TAG, "run: title: " + title);
                    Log.i(TAG, "run: link: " + link);
                    Log.i(TAG, "run: time: " + time);
                    Log.i(TAG, "run: articleContent.attr: " + articleContent.select("p").attr(""));
                    String content = "";
                    for (Element ac : articleContent) {
                        Log.i(TAG, "run: ac: " + ac.text());
                        content = content + ac.text() + "\n";
                    }
                    articleBeanList.add(new ArticleBean(title, time, content, link));
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //创建并设置Adapter
            MyAdapter mAdapter = new MyAdapter(articleBeanList);
            mRecyclerView.setAdapter(mAdapter);
        }
    };

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final List<ArticleBean> mArticleBeen;

        public MyAdapter(List<ArticleBean> articleBeanList) {
            mArticleBeen = articleBeanList;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item, viewGroup, false);
            return new ViewHolder(view);
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.mAtTitle.setText(mArticleBeen.get(position).getTitle());
            viewHolder.mAtTime.setText(mArticleBeen.get(position).getTime());
            viewHolder.mAtContent.setText(mArticleBeen.get(position).getContent());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = mArticleBeen.get(position).getLink();
                    Toast.makeText(getContext(), "item onClicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mArticleBeen.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mAtTitle;
            public final TextView mAtContent;
            public final TextView mAtTime;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mAtTitle = (TextView) view.findViewById(R.id.at_title);
                mAtTime = (TextView) view.findViewById(R.id.at_time);
                mAtContent = (TextView) view.findViewById(R.id.at_content);
            }
        }
    }
}
