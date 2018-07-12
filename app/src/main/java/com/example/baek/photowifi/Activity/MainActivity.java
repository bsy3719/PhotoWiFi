package com.example.baek.photowifi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.baek.photowifi.Fragment.ListWifiFragment;
import com.example.baek.photowifi.Fragment.MyWifiFragment;
import com.example.baek.photowifi.R;
import com.example.baek.photowifi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment();
        setBottomNavi();

    }

    private void setBottomNavi(){

        AHBottomNavigationItem MyWifi = new AHBottomNavigationItem(R.string.bottom_navi_my_wifi, R.drawable.ic_wifi, R.color.colorPrimary);
        AHBottomNavigationItem ListWifi = new AHBottomNavigationItem(R.string.bottom_navi_list_wifi, R.drawable.ic_list, R.color.colorWhite);

        mBinding.bottomNavigation.addItem(MyWifi);
        mBinding.bottomNavigation.addItem(ListWifi);

        // Set current item programmatically
        mBinding.bottomNavigation.setCurrentItem(0);

        // Manage titles
        //mBinding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        mBinding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //mBinding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // 클릭시 아이콘색
        mBinding.bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        // 평소 아이콘색
        mBinding.bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorDarkGray));

        // Set listeners
        mBinding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (position){
                    case 0 :
                        transaction.replace(R.id.fragment_container, MyWifiFragment.newInstance()).addToBackStack(null).commit();
                        break;
                    case 1 :
                        transaction.replace(R.id.fragment_container, ListWifiFragment.newInstance()).addToBackStack(null).commit();
                        break;
                }
                return true;
            }
        });

    }

    private void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, MyWifiFragment.newInstance()).commitAllowingStateLoss();
    }
}
