package com.questappx.tictactoe;

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

import com.bumptech.glide.Glide;

public class DifficultyDialog extends Dialog {

    Context context;
    ImageView cancelBtn, emojiIv, okIv;
    ImageView textDifficultyIv;
    SeekBar seekBar;
    int gridSize, isAIplaying, difficultyLevel, gameMode;

    public DifficultyDialog(Context context, int mode, int isAIplaying, int gameMode)
    {
        super(context);
        this.context = context;
        this.gridSize = mode;
        this.isAIplaying = isAIplaying;
        this.gameMode = gameMode;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_level_dialog);


        setCancelable(false);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancelBtn = findViewById(R.id.cancelBtn_difficulty);
        seekBar = findViewById(R.id.seekbar_diffculty_level);
        textDifficultyIv = findViewById(R.id.diffcultyLevelIv);
        emojiIv = findViewById(R.id.emojiIv);
        okIv = findViewById(R.id.ok_diffcultyBtn);



        clickListener();


    }

    private void clickListener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0)
                {
//                    textDifficultyIv.setImageResource(R.drawable.easy_difficulty);
                    Glide.with(context).load(R.drawable.easy_difficulty).into(textDifficultyIv);
                    seekBar.setThumb(getContext().getDrawable(R.drawable.diffculty_seekone));
//                    emojiIv.setImageResource(R.drawable.low_difficulty_emoji);
                    Glide.with(context).load(R.drawable.low_difficulty_emoji).into(emojiIv);
                    difficultyLevel = progress;
                }
                else if(progress == 1)
                {
//                    textDifficultyIv.setImageResource(R.drawable.medium_difficulty);
                    Glide.with(context).load(R.drawable.medium_difficulty).into(textDifficultyIv);
                    seekBar.setThumb(getContext().getDrawable(R.drawable.diffculty_seektwo1));
//                    emojiIv.setImageResource(R.drawable.medium_difficulty_emoji);
                    Glide.with(context).load(R.drawable.medium_difficulty_emoji).into(emojiIv);
                    difficultyLevel = progress;
                }
                if(progress == 2)
                {
//                    textDifficultyIv.setImageResource(R.drawable.hard_difficulty);
                    Glide.with(context).load(R.drawable.hard_difficulty).into(textDifficultyIv);
                    seekBar.setThumb(getContext().getDrawable(R.drawable.difficulty_seekthree));
//                    emojiIv.setImageResource(R.drawable.hard_diffulty_emoji);
                    Glide.with(context).load(R.drawable.hard_diffulty_emoji).into(emojiIv);
                    difficultyLevel = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        okIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
                dismiss();
            }
        });

    }

    private void playGame()
    {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.putExtra("grid", gridSize);
        newIntent.putExtra("ai",isAIplaying);
        newIntent.putExtra("difficulty",difficultyLevel);
        newIntent.putExtra("mode",gameMode);
        context.startActivity(newIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
