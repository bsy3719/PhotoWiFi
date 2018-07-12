package com.example.baek.photowifi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baek.photowifi.Model.Wifi;
import com.example.baek.photowifi.R;
import com.example.baek.photowifi.ViewHolder.WifiViewHolder;

import java.util.List;

public class WifiAdapter extends RecyclerView.Adapter<WifiViewHolder> {

    private List<Wifi> mWifiList;
    private Context mContext;

    public WifiAdapter(List<Wifi> wifiList,  Context context){
        mWifiList = wifiList;
        mContext = context;
    }

    @Override
    public WifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_wifi, parent, false);

        return new WifiViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(WifiViewHolder holder, int position) {
        holder.bindMain(mWifiList.get(position));

    }

    @Override
    public int getItemCount() {
        return mWifiList.size();
    }

    public void setmWifiList(List<Wifi> mWifiList) {
        this.mWifiList = mWifiList;
    }
}