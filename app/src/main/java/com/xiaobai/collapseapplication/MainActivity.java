package com.xiaobai.collapseapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {

    private static final int HANDLER_WHAT = 100;
    private TabLayout mTabLayout;
    private DataViewPager mVpFragment;
    private FragmentPagerAdapter mDataAdapter;
    private Handler mPagerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_WHAT:
                    if (mDataAdapter != null) {
                        mDataAdapter.notifyDataSetChanged();
                    }
                    break;
            }
            return true;
        }
    });
    private TextView mTvBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        registerEvent();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setTranslucentStatusBar(toolbar);
        mTabLayout = findViewById(R.id.tab_layout);
        mVpFragment = findViewById(R.id.vp_fragment);
        mTvBack = findViewById(R.id.tv_back);

    }


    /**
     * 设置透明状态栏
     *
     * @return CoordinatorTabLayout
     */
    public void setTranslucentStatusBar(Toolbar toolbar) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (toolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + SystemView.getStatusBarHeight(this),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }

    }

    @Subscribe
    public void onSendMessage(EventMessage eventMessage) {
        Log.w("onSendMessage--", "接收消息");
       /* if (eventMessage.isSendMessage) {
            if (mDataAdapter != null) {
                mDataAdapter.notifyDataSetChanged();
            }
        }*/
    }

    @Override
    protected void initData() {

        final DataFragment[] dataFragments = {DataFragment.newInstance(0), DataFragment.newInstance(1), DataFragment.newInstance(2), DataFragment.newInstance(3), DataFragment.newInstance(4)};


        mDataAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private int mChildCount;

            @Override
            public Fragment getItem(int i) {
                return dataFragments[i];
            }

            @Override
            public int getCount() {
                return dataFragments.length;
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                if (mChildCount > 0) {
                    mChildCount--;
                    return PagerAdapter.POSITION_NONE;
                }
                return super.getItemPosition(object);
            }

            @Override
            public void notifyDataSetChanged() {
                mChildCount = getCount();
                super.notifyDataSetChanged();
            }
        };
        mVpFragment.setAdapter(mDataAdapter);


        //mVpFragment.setCurrentItem(1,true);
        for (int i = 0; i < dataFragments.length; i++) {
            switch (i) {
                case 0:
                    mTabLayout.addTab(mTabLayout.newTab().setText("详情"), i);
                    break;
                case 1:
                    mTabLayout.addTab(mTabLayout.newTab().setText("优惠"), i);
                    break;
                case 2:
                    mTabLayout.addTab(mTabLayout.newTab().setText("热销"), i);
                    break;
                case 3:
                    mTabLayout.addTab(mTabLayout.newTab().setText("评论"), i);
                    break;
                case 4:
                    mTabLayout.addTab(mTabLayout.newTab().setText("心得"), i);
                    break;
                default:
                    mTabLayout.addTab(mTabLayout.newTab().setText("详情"), 0);
                    break;
            }
        }
        mPagerHandler.sendEmptyMessageDelayed(HANDLER_WHAT, 100);

    }

    @Override
    protected void initEvent() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVpFragment.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mTabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        unRegisterEvent();
        mPagerHandler.removeMessages(HANDLER_WHAT);
        mPagerHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    public static class EventMessage {
        public EventMessage(boolean isSendMessage) {
            this.isSendMessage = isSendMessage;
        }

        public boolean isSendMessage;
    }


}
