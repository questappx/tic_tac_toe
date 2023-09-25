package com.questappx.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;

import java.io.InputStream;

public class SettingDialog extends Dialog {

    private MainActivity mainActivity;
    TicTac ticTac;
    Context context;
    private ImageView cancelBtn, homeBtn, restartBtn, volume;
    int method;



    public SettingDialog(Context context, int method)
    {
        super(context);
        this.method = method;
    }

    public SettingDialog(@NonNull Context context, TicTac tic) {
        super(context);
        this.ticTac = tic;
        this.context = context;

        //default value to method -- used to control setting dialog on also other pages
        method = 0;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_dialog);
        setCancelable(false);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


         cancelBtn = findViewById(R.id.cancelBtn_settingdialog);
         homeBtn = findViewById(R.id.homeBtn_settingDialog);
         restartBtn = findViewById(R.id.restartBtn_settingdialog);
         volume = findViewById(R.id.volumeBtn_settingdialog);

         if(method == 1)
         {
             restartBtn.setVisibility(View.GONE);
             homeBtn.setVisibility(View.GONE);
         }

        if(FirstActivity.IS_SOUND_ENABLED)
        {
            volume.setImageResource(R.drawable.volume_btn);
//            Glide.with(context).load(R.drawable.volume_btn).into(volume);
//            Glide.with(context.getApplicationContext()).load(R.drawable.volume_btn).into(volume);
        }
        else
        {
            volume.setImageResource(R.drawable.no_volumebtn);
//            Glide.with(context).load(R.drawable.no_volumebtn).into(volume);
//            Glide.with(context.getApplicationContext()).load(R.drawable.no_volumebtn).into(volume);
        }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                ((Activity)context).finish();
            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTac.restartMatch();
                dismiss();
            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirstActivity.IS_SOUND_ENABLED == true)
                {
                    FirstActivity.IS_SOUND_ENABLED = false;
                    volume.setImageResource(R.drawable.no_volumebtn);
//                    Glide.with(context).load(R.drawable.no_volumebtn).into(volume);
                }
                else
                {
                    FirstActivity.IS_SOUND_ENABLED = true;
                    volume.setImageResource(R.drawable.volume_btn);
//                    Glide.with(context).load(R.drawable.volume_btn).into(volume);
                }
            }
        });

    }

    public void setRestartBtnVisibility(boolean bool){
        if(bool)
        {
            if(restartBtn.getVisibility() == View.GONE)
            restartBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            if(restartBtn.getVisibility() == View.VISIBLE)
            restartBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
