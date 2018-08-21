package com.example.douban.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.douban.Activity.BoxofficeActivity;
import com.example.douban.Activity.MainActivity;
import com.example.douban.Activity.MovieDetail;
import com.example.douban.Activity.Top250Activity;
import com.example.douban.Adapter.Adapter_boxoffice;
import com.example.douban.Adapter.Adapter_hot;
import com.example.douban.Adapter.Adapter_viewpages;
import com.example.douban.R;
import com.example.douban.Activity.SearchActivity;
import com.example.douban.Thread.Asyn_Thread;

import java.util.ArrayList;
import java.util.Map;

public class Fragment_list extends Fragment {
    private ListView coming;
    private TextView top250,boxoffice;
    ArrayList arr;
    private View view;

    private ViewPager viewPager;
    private LinearLayout dots;
    private int[] images;
    private int defaultposition = 0;
    private boolean isrunning = false;
    ArrayList<ImageView> imagelist;
    private  boolean isload = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_list, container, false);
        }
        coming = view.findViewById(R.id.coming);

        top250 = view.findViewById(R.id.top250);
        boxoffice = view.findViewById(R.id.boxoffice);

        viewPager = view.findViewById(R.id.viewpager);
        dots = view.findViewById(R.id.pager_dots);

        //缓存这个view,点击另外的fragment后，再点回来则不再重新加载数据
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null) {
            group.removeView(view);
        }
        String url = "http://api.douban.com/v2/movie/coming_soon";
        @SuppressLint("HandlerLeak")
        //开启新线程加载数据到指定布局当中
                Asyn_Thread at = new Asyn_Thread(url, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    SearchActivity searchActivity = new SearchActivity();
                    Bundle b = msg.getData();
                    String str = b.getString("text");
                    arr = searchActivity.information(str);
                    coming.setAdapter(new Adapter_hot(getActivity(), arr));
                }
            }
        }, "coming");
        at.start();

        //点击top250跳转到相应页面
        top250.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Top250Activity.class);
                startActivity(intent);
            }
        });

        //点击boxoffice跳转到相应页面
        boxoffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), BoxofficeActivity.class);
                startActivity(intent1);
            }
        });
        coming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                Map<String, Object> m = (Map<String, Object>) arr.get(i);
                String id = (String) m.get("id");
                intent.putExtra("clickid", id);
                startActivity(intent);
            }
        });

        //判断viewpage是否加载过，如果没有加载过则调用相关方法和开启线程来加载viewpager，
        // 如果已经加载过，则不会再加载里面的内容，从而不会重复调用轮播图相关方法和重复开启线程导致轮播图有问题
        if (isload == false) {
            init_images();
            init_adapter();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    isrunning = true;
                    while (isrunning) {
                        try {
                            Thread.sleep(4000);//每过4秒自动轮转到下一个图
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            }
                        });
                    }
                }
            }).start();
        }
        Log.i("Fragment","onCreateView");

        return view;
    }
    //初始化轮播图的数据
    public void init_images(){
        images = new int[] {R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5};
        imagelist = new ArrayList<ImageView>();
        View dot_view;//用来填装小圆点
        ImageView imageView;//用来填装要轮播的图片
        LinearLayout.LayoutParams layoutParams;//给小圆点设置位置参数
        for (int i=0;i<images.length;i++){
            //添加图片
            imageView = new ImageView(getActivity());
            imageView.setImageResource(images[i]);
            imagelist.add(imageView);

            //添加dot小圆点
            dot_view = new View(getActivity());
            dot_view.setBackgroundResource(R.drawable.selector_dots);
            layoutParams = new LinearLayout.LayoutParams(20,20);
            if (i != 0){
                layoutParams.leftMargin = 10;
            }
            dot_view.setEnabled(false);//设置全部小圆点默认不可用
            dots.addView(dot_view,layoutParams);
        }
        Log.i("Fragment-------","init_images");

    }
    //初始化适配器
    public void init_adapter(){
        Adapter_viewpages adapter_viewpages = new Adapter_viewpages(viewPager,imagelist);
        dots.getChildAt(0).setSelected(true);//设置第一张图片对应的第一个小圆点为选中状态
        viewPager.setAdapter(adapter_viewpages);
        int dot = Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % imagelist.size());
        viewPager.setCurrentItem(dot);//设置从几十亿次中开始轮播，达到无限循环
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newposition = position % imagelist.size();
                dots.getChildAt(defaultposition).setSelected(false); //转到下一张图后，前一张图的小圆点设置为非选中状态
                dots.getChildAt(newposition).setSelected(true);//转到下一张图后，这张图的小圆点设置为选中状态，
                defaultposition = newposition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Log.i("Fragment-------","init_adapter");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isrunning = false;
        Log.i("Fragment","onDestroy");

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Fragment","onDestroyView");
        isload = true;// 当转到其他fragment时，把isload（是否加载过）设置为真，则表示已经加载过
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Fragment","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Fragment","onCreat");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Fragment","onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fragment","onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Fragment","onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment","onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment","onStop");

    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment","onDetach");

    }
}

