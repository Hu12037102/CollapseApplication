package com.xiaobai.collapseapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DataFragment extends Fragment {

    private View rootView;
    private RecyclerView mRvData;
    private int mPosition = -1;

    public static DataFragment newInstance(int position) {
        DataFragment dataFragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        dataFragment.setArguments(bundle);
        return dataFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
       // EventBus.getDefault().register(this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

        mRvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.w("initEvent--",newState+"--");
                if (newState == 0){
                    EventBus.getDefault().post(new MainActivity.EventMessage(true));
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPosition = arguments.getInt("position", -1);
        }
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("钢铁侠-Mark:" + i + "---页面：" + mPosition);


        }
        DataAdapter dataAdapter = new DataAdapter(data, getContext());
        mRvData.setAdapter(dataAdapter);

    }

    private void initView() {
        mRvData = rootView.findViewById(R.id.ry_data);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroy() {
      //  EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
