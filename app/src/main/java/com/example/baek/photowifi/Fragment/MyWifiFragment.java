package com.example.baek.photowifi.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baek.photowifi.Adapter.WifiAdapter;
import com.example.baek.photowifi.Model.Wifi;
import com.example.baek.photowifi.R;
import com.example.baek.photowifi.Activity.TextRecoActivity;
import com.example.baek.photowifi.databinding.FragmentMyWifiBinding;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWifiFragment extends Fragment {

    private static final int i_RECO = 1001;
    private FragmentMyWifiBinding mBinding;
    private WifiManager wifimanager;
    private WifiAdapter mAdapter;

    private List<ScanResult> mScanResult;
    private List<Wifi> mWifiList = new ArrayList<>();
    private static final String TAG = "MyWifiFragment";


    public MyWifiFragment() {
        // Required empty public constructor
    }

    public static MyWifiFragment newInstance() {
        MyWifiFragment fragment = new MyWifiFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_wifi, container, false);

        mBinding.wifiRecyclerView.setHasFixedSize(true);
        mBinding.wifiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return mBinding.getRoot();
    }

    //툴바 아이템 셋팅
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.photo_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_item_photo:
                Intent i = new Intent (getActivity(), TextRecoActivity.class);
                startActivityForResult(i, i_RECO);
                break;
            case R.id.menu_item_wifi:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
