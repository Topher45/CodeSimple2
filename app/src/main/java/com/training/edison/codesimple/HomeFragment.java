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
                    String link = element.select("h1.title").select("a").attr("href"); //链接
                    Log.i(TAG, "run: title: " + title);
                    Log.i(TAG, "run: link: " + link);
                    Elements articleContent = element.select("div.p_part");
                    Log.i(TAG, "run: articleContent.attr: " + articleContent.select("p").attr(""));
                    String content = "";
                    for (Element ac : articleContent) {
                        Log.i(TAG, "run: ac: " + ac.text());
                        content = content + ac.text() + "\n";
                    }
                    articleBeanList.add(new ArticleBean(title, "2016-11-11", content));
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
}
