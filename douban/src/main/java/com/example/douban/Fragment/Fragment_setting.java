package com.example.douban.Fragment;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.douban.R;

public class Fragment_setting extends Fragment {
    View view;
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
        return view;
    }
}
