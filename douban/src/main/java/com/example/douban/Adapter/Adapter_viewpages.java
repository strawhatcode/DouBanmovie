package com.example.douban.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class Adapter_viewpages extends PagerAdapter {
    private ArrayList<ImageView> imageViews;
    private ViewPager viewPager;
    public Adapter_viewpages(ViewPager viewPager,ArrayList<ImageView> imageViews){
        this.viewPager = viewPager;
        this.imageViews = imageViews;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//返回interger类型的最大值
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //索引position: 0 --> 2;    imageViews.size() == 2;
        int newposition = position % imageViews.size(); //获取当前的索引
        ImageView imageView = imageViews.get(newposition); //把新获取到的索引添加到imageview中
//        ViewGroup parent = (ViewGroup) imageView.getParent();
//        if (parent != null){
//            parent.removeView(imageView);
//        }
        viewPager.addView(imageView); //把view对象添加到viewpages中
        return imageView;//返回view对象
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        viewPager.removeView(imageViews.get(position % imageViews.size()));//销毁当前索引的imageview
            container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;//判断view是否可以重复使用（缓存）
    }
}

