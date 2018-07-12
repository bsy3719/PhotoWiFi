package com.example.baek.photowifi.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baek.photowifi.Model.Wifi;
import com.example.baek.photowifi.R;

public class WifiViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    private Wifi mWifi;
    private TextView mId_textView;
    private TextView mPassword_textView;

    private Context mContext;

    public WifiViewHolder(View itemView, Context context) {
        super(itemView);

        mContext = context;

        mId_textView = itemView.findViewById(R.id.list_id_text_view);
        mPassword_textView = itemView.findViewById(R.id.list_password_text_view);

        itemView.setOnClickListener(this);
    }

    public void bindMain(Wifi wifi){
        mWifi = wifi;
        mId_textView.setText(mWifi.getId());
        mPassword_textView.setText(mWifi.getPassword());
    }

    @Override
    public void onClick(View v) {
        //userInfo.setTravelId(mTravelItem.getmId());
        //Intent intent = WriteActivity.newIntent(mContext, mTravelItem);
        //mContext.startActivity(intent);
    }
}
