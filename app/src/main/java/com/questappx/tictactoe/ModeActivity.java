package com.questappx.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ModeActivity extends AppCompatActivity {

    ImageView settingIv, activityBackIv, leftModeIv, rightModeIv, modeImgIv, multiplayerIv,modeTextIv, singlePlayerBtn, levelBtn;
    int gridSize, isAIplaying, gameMode;
    Spinner spinner_playModes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2);

        init();
    }

    private void init()
    {
        settingIv = findViewById(R.id.settingIv);
        activityBackIv = findViewById(R.id.acitivtybackIv);
        leftModeIv = findViewById(R.id.leftModeBtnIv);
        rightModeIv = findViewById(R.id.rightModeBtnIv);
        modeTextIv = findViewById(R.id.modeTextIv);
        modeImgIv = findViewById(R.id.modeImgIv);
        multiplayerIv = findViewById(R.id.mulitplayerBtn);
        singlePlayerBtn = findViewById(R.id.singlePlayerBtn);
        spinner_playModes = findViewById(R.id.spinnerPlayModes);
        levelBtn = findViewById(R.id.levelsBtn);

        isAIplaying = 0;

        gridSize = 3;

        clickListener();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gamePlayeModes, android.R.layout.preference_category);
        adapter.setDropDownViewResource(android.R.layout.preference_category);
        spinner_playModes.setAdapter(adapter);


    }

    private void clickListener()
    {
//        modeTextIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DifficultyDialog dialog = new DifficultyDialog(ModeActivity.this, mode, isAIplaying);
//                dialog.show();
//
//            }
//        });


        levelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModeActivity.this, LevelsActivity.class));
            }
        });


        settingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog dialog = new SettingDialog(ModeActivity.this, 1);
//                dialog.setRestartBtnVisibility(false);
                dialog.show();
            }
        });

        activityBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        leftModeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMode();
            }
        });

        rightModeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMode();
            }
        });

        multiplayerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAIplaying = 0;
//                DifficultyDialog dialog = new DifficultyDialog(ModeActivity.this, mode, isAIplaying);
//                dialog.show();
                playMultiGame();
            }
        });

        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAIplaying = 1;
                DifficultyDialog dialog = new DifficultyDialog(ModeActivity.this, gridSize, isAIplaying, gameMode);
                dialog.show();

            }
        });

        spinner_playModes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gameMode = Data.ORGINAL_PLAYMODE;
            }
        });
    }

    private void playMultiGame()
    {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        Intent newIntent = new Intent(ModeActivity.this, MainActivity.class);
        newIntent.putExtra("grid", gridSize);
        newIntent.putExtra("ai",isAIplaying);
        newIntent.putExtra("mode",gameMode);
//        newIntent.putExtra("difficulty",0);
        startActivity(newIntent);
    }



    private void previousMode() {
        if(gridSize == 6)
        {
            gridSize = 3;
            leftModeIv.setVisibility(View.INVISIBLE);
//            modeTextIv.setImageResource(R.drawable.three_text);
//            modeImgIv.setImageResource(R.drawable.threex_img);
            Glide.with(ModeActivity.this).load(R.drawable.three_text).into(modeTextIv);
            Glide.with(ModeActivity.this).load(R.drawable.threex_img).into(modeImgIv);
        }
        else if(gridSize == 9)
        {
            gridSize = 6;
//            modeTextIv.setImageResource(R.drawable.six_text);
//            modeImgIv.setImageResource(R.drawable.sixx_img);
            Glide.with(ModeActivity.this).load(R.drawable.six_text).into(modeTextIv);
            Glide.with(ModeActivity.this).load(R.drawable.sixx_img).into(modeImgIv);
        }
        else if(gridSize == 11)
        {
            gridSize = 9;
//            modeTextIv.setImageResource(R.drawable.ninex_text);
//            modeImgIv.setImageResource(R.drawable.ninex_img);
            Glide.with(ModeActivity.this).load(R.drawable.ninex_text).into(modeTextIv);
            Glide.with(ModeActivity.this).load(R.drawable.ninex_img).into(modeImgIv);
            rightModeIv.setVisibility(View.VISIBLE);
        }
    }

    private void nextMode()
    {
        if(gridSize == 3)
        {
            leftModeIv.setVisibility(View.VISIBLE);
            gridSize = 6;
//            modeImgIv.setImageResource(R.drawable.sixx_img);
//            modeTextIv.setImageResource(R.drawable.six_text);
            Glide.with(ModeActivity.this).load(R.drawable.six_text).into(modeTextIv);
            Glide.with(ModeActivity.this).load(R.drawable.sixx_img).into(modeImgIv);
        }
        else if(gridSize == 6)
        {
            gridSize = 9;
//            modeTextIv.setImageResource(R.drawable.ninex_text);
//            modeImgIv.setImageResource(R.drawable.ninex_img);
            Glide.with(ModeActivity.this).load(R.drawable.ninex_text).into(modeTextIv);
            Glide.with(ModeActivity.this).load(R.drawable.ninex_img).into(modeImgIv);
        }
        else if(gridSize == 9)
        {
            gridSize = 11;
//            modeImgIv.setImageResource(R.drawable.elevenx_im);
//            modeTextIv.setImageResource(R.drawable.elevenx_text);
            Glide.with(ModeActivity.this).load(R.drawable.elevenx_text).into(modeTextIv);
            Glide.with(ModeActivity.this).load(R.drawable.elevenx_im).into(modeImgIv);
            rightModeIv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}