package com.questappx.tictactoe;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class RateUsDialog extends Dialog {

    Context context;
    ImageView cancelBtn;
    public TextView okIv, remindmeLater, skip;

    private static final int LAUNCHES_UNTIL_PROMPT = 5;
    private static final int DAYS_UNTIL_PROMPT = 5;
    //    private static final int MILLIS_UNTIL_PROMPT = DAYS_UNTIL_PROMPT *  60 * 60;
    private static final int MILLIS_UNTIL_PROMPT = DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000;
    private static final String PREF_NAME = "APP_RATER_TTT";
    private static final String LAST_PROMPT = "LAST_PROMPT";
    private static final String LAUNCHES = "LAUNCHES";
    private static final String DISABLED = "DISABLED";

    public RateUsDialog(Context context)
    {
        super(context);
        this.context = context;
    }


    public void showDialog() {
//        super.show();


        boolean shouldShow = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long currentTime = System.currentTimeMillis();
        long lastPromptTime = sharedPreferences.getLong(LAST_PROMPT, 0);
        if (lastPromptTime == 0) {
            lastPromptTime = currentTime;
            editor.putLong(LAST_PROMPT, lastPromptTime);
        }

        if (!sharedPreferences.getBoolean(DISABLED, false)) {
            int launches = sharedPreferences.getInt(LAUNCHES, 0) + 1;
            if (launches > LAUNCHES_UNTIL_PROMPT) {
                if (currentTime > lastPromptTime + MILLIS_UNTIL_PROMPT) {
                    shouldShow = true;
                }
            }
            editor.putInt(LAUNCHES, launches);
        }

        if (shouldShow) {
            editor.putInt(LAUNCHES, 0).putLong(LAST_PROMPT, System.currentTimeMillis()).commit();
            this.show();
//            new RateItDialogFragment().show(fragmentManager, null);
        } else {
            editor.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rateus_dialog);


        setCancelable(false);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancelBtn = findViewById(R.id.cancelBtn_rateus);
        okIv = findViewById(R.id.ok_rateus);
        remindmeLater = findViewById(R.id.remindmeLater_Rateus);
        skip = findViewById(R.id.rateus_Skip);



        clickListener();


    }

    private void clickListener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Data.PACKAGE_NAME)));
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putBoolean(DISABLED, true).commit();
                dismiss();
            }
        });

        remindmeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putBoolean(DISABLED, true).commit();
                dismiss();
            }
        });



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
