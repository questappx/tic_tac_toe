package com.questappx.tictactoe.Extras;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.questappx.tictactoe.R;


public class RateUsExit {
    Context context;

    public RateUsExit(Context context) {
        this.context = context;
    }

    public void textDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.exit_rateus);
        ImageView exitRateDialog = (ImageView) dialog.findViewById(R.id.exitRateDialog);
        ImageView rateDialog = (ImageView) dialog.findViewById(R.id.rateUsDialog);


        exitRateDialog.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                ((Activity)context).finish();
            }
        });
        rateDialog.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+context.getApplicationContext().getPackageName())));


            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }



}
