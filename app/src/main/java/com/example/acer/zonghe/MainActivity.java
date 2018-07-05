package com.example.acer.zonghe;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String path = "http://www.wanandroid.com/tools/mockapi/6523/article_channel_list";
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private String[] title = {"导航一","导航二","导航三","导航四","导航五","导航六","导航七","导航八"};
    private List<String> slist;
    private List<TextView> tlist;
    private Button btn;
    private List<ChannelBean> clist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        horizontalScrollView = findViewById(R.id.horizontal);
        linearLayout = findViewById(R.id.horizontal_linear);
        viewPager = findViewById(R.id.viewpager);
        tlist = new ArrayList<>();
        clist = new ArrayList<>();
        for (int i = 0 ; i < title.length ; i++){
            TextView textView = new TextView(MainActivity.this);
            textView.setText(title[i]);
            textView.setTextSize(20);
            textView.setId(i+1000);
            clist.add(new ChannelBean(title[i],true));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    viewPager.setCurrentItem(id-1000);
                }
            });

            if (i == 0){
                textView.setTextColor(Color.RED);
            }else {
                textView.setTextColor(Color.BLACK);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20,10,20,10);
            linearLayout.addView(textView,layoutParams);
            tlist.add(textView);
        }
        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0 ; i < tlist.size() ; i++){
                    if (position == i){
                        tlist.get(i).setTextColor(Color.RED);
                    }else {
                        tlist.get(i).setTextColor(Color.BLACK);
                    }
                }

                TextView textView = tlist.get(position);
                int i = textView.getWidth() + 10;
                horizontalScrollView.scrollTo(position*i,0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelActivity.startChannelActivity(MainActivity.this,clist);
            }
        });
    }

    class MyPageAdapter extends FragmentPagerAdapter{

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment01.getInstence(tlist.get(position).getText().toString());
        }

        @Override
        public int getCount() {
            return tlist.size();
        }
    }
}
