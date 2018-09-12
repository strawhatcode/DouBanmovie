package com.example.douban.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.douban.Activity.AboutActivity;
import com.example.douban.R;

public class Fragment_setting extends Fragment implements View.OnClickListener{
    View view;
    private TextView about;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view ==null) {
            view = inflater.inflate(R.layout.activity_setting, container, false);
        }
        //缓存这个view,点击另外的fragment后，再点回来则不再重新加载数据
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null){
            group.removeView(view);
        }

        about = view.findViewById(R.id.as_about);

        about.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.as_about:
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
