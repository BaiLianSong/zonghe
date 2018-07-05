package com.example.acer.zonghe;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/7/1.
 */

public class MyFragment01 extends Fragment {
    private MyAdapter myAdapter;
    private View view;
    private PullToRefreshListView pullToRefreshListView;
    private HttpUtil httpUtil = HttpUtil.getInstence();
    private String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?pageSize=15&page=";
    private int page = 1;
    private Handler handler = new Handler();
    private List<UserBean.DataBean> blist = new ArrayList<>();
    private List<String> list;
    private Banner banner;
    private String[] image = {
            "http://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
            "http://www.wanandroid.com/blogimgs/ffb61454-e0d2-46e7-bc9b-4f359061ae20.png",
            "http://www.wanandroid.com/blogimgs/ab17e8f9-6b79-450b-8079-0f2287eb6f0f.png",
            "http://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png",
            "http://www.wanandroid.com/blogimgs/84810df6-adf1-45bc-b3e2-294fa4e95005.png",
            "http://www.wanandroid.com/blogimgs/90cf8c40-9489-4f9d-8936-02c9ebae31f0.png",
            "http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png"
    };
    public static MyFragment01 getInstence(String title){
        MyFragment01 myFragment01 = new MyFragment01();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        myFragment01.setArguments(bundle);
        return myFragment01;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment01,container,false);
        banner = view.findViewById(R.id.banner);
        banner.setImageLoader(new MyImageLoader());

        list = new ArrayList<>();
        for (int i = 0 ; i < image.length ; i++){
            list.add(image[i]);
        }
        banner.setImages(list);
        banner.start();

        initView();

        return view;
    }

    private void initView(){
        pullToRefreshListView = view.findViewById(R.id.pull_listview);

        pullToRefreshListView.setMode(PullToRefreshListView.Mode.BOTH);

        ILoadingLayout loadingLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        loadingLayoutProxy.setPullLabel("下拉刷新");
        loadingLayoutProxy.setRefreshingLabel("正在刷新");
        loadingLayoutProxy.setReleaseLabel("松开刷新");

        ILoadingLayout loadingLayoutProxy1 = pullToRefreshListView.getLoadingLayoutProxy(false, true);
        loadingLayoutProxy1.setPullLabel("上拉加载");
        loadingLayoutProxy1.setRefreshingLabel("正在加载");
        loadingLayoutProxy1.setReleaseLabel("松开加载");

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1 ;
                getJsonData();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onRefreshComplete();
                    }
                },2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page+=1;
                getJsonData();;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onRefreshComplete();
                    }
                },2000);
            }
        });
        myAdapter = new MyAdapter(blist,getActivity());
        pullToRefreshListView.setAdapter(myAdapter);

    }

    public void getJsonData(){
        String url = path+page;
        Log.i("TAG",url);
        httpUtil.getData(url);
        httpUtil.setHttpUtilListenr(new HttpUtil.HttpUtilListenr() {
            @Override
            public void getDataJson(String json) {
                Log.i("TAG",json);
                Gson gson = new Gson();
                UserBean userBean = gson.fromJson(json, UserBean.class);
                List<UserBean.DataBean> data = userBean.getData();
                Log.i("TAG",data+"");


                //blist = new ArrayList<>();

                if (page == 1){
                    blist.clear();
                }
                blist.addAll(data);
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
