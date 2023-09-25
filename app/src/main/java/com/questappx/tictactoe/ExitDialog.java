package com.questappx.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class ExitDialog extends Dialog {

    Context context;
    ImageView cancelBtn, okIv;

    public ExitDialog(Context context)
    {
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_game_dialog);


        setCancelable(false);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancelBtn = findViewById(R.id.cancelBtn_exit);
        okIv = findViewById(R.id.ok_exitBtn);



        clickListener();


    }

    private void clickListener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        okIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).onBackPressed();
                ((Activity)context).finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
