package com.example.douban.OverrideView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//这个类用来重写listview，解决listview与scorllview的滑动条冲突，并且用scorllview滑动条
//实现listview可以和其他布局组件公用一个滑动条
public class Listview_list extends ListView {
    public Listview_list(Context context) {
        super(context);
    }

    public Listview_list(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Listview_list(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
