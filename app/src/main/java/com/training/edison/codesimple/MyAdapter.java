package com.training.edison.codesimple;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ArticleBean> mArticleBeen;

    public MyAdapter(List<ArticleBean> articleBeanList) {
        mArticleBeen = articleBeanList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mAtTitle.setText(mArticleBeen.get(position).getTitle());
        viewHolder.mAtTime.setText(mArticleBeen.get(position).getTime());
        viewHolder.mAtContent.setText(mArticleBeen.get(position).getContent());
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mArticleBeen.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mAtTitle;
        TextView mAtContent;
        TextView mAtTime;

        ViewHolder(View view) {
            super(view);
            mAtTitle = (TextView) view.findViewById(R.id.at_title);
            mAtTime = (TextView) view.findViewById(R.id.at_time);
            mAtContent = (TextView) view.findViewById(R.id.at_content);
        }
    }

}
