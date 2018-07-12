package com.example.baek.photowifi.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baek.photowifi.R;
import com.example.baek.photowifi.Activity.TextRecoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWifiFragment extends Fragment {

    private static final int i_RECO = 1001;


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_wifi, container, false);
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
        }
        return super.onOptionsItemSelected(item);
    }

}
