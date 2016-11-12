package com.training.edison.codesimple;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AboutFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton imgGit = (ImageButton) getView().findViewById(R.id.github);
        ImageButton imgWeibo = (ImageButton) getView().findViewById(R.id.weibo);
        ImageButton imgZhihu = (ImageButton) getView().findViewById(R.id.zhihu);
        imgGit.setOnClickListener(this);
        imgWeibo.setOnClickListener(this);
        imgZhihu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhihu:
                Intent intentZhihu = new Intent(Intent.ACTION_VIEW);
                intentZhihu.setData(Uri.parse("http://www.zhihu.com/people/shi-cheng-16-47"));
                getActivity().startActivity(intentZhihu);
                break;
            case R.id.weibo:
                Intent intentWeibo = new Intent(Intent.ACTION_VIEW);
                intentWeibo.setData(Uri.parse("http://weibo.com/hcz10"));
                startActivity(intentWeibo);
                break;
            case R.id.github:
                Intent intentGit = new Intent(Intent.ACTION_VIEW);
                intentGit.setData(Uri.parse("http://github.com/hcz017"));
                getActivity().startActivity(intentGit);
                break;
            default:
        }
    }
}
