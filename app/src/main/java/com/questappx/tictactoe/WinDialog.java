package com.questappx.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog {

    private String message;
    private MainActivity mainActivity;
    TicTac ticTac;

    public WinDialog(@NonNull Context context,TicTac tic, String message) {
        super(context);
        this.message = message;
        this.ticTac = tic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.win_dialog);
        setCancelable(false);

        final TextView messageTxt = findViewById(R.id.messageTxt);
        final Button startAgain = findViewById(R.id.startAgain);
        final TextView cross = findViewById(R.id.crossDialog);

        messageTxt.setText(message);

        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTac.restartMatch();
                dismiss();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
