package com.questappx.tictactoe;

import static com.questappx.tictactoe.FirstActivity.fbInterstitial;
import static com.questappx.tictactoe.FirstActivity.interstitialAd;
//import static com.questappx.tictactoe.FirstActivity.interstitialAdImplement;
import static com.questappx.tictactoe.FirstActivity.rewardedAd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.questappx.tictactoe.Extras.AppOpenManager;
import com.questappx.tictactoe.Extras.InterstitialAdImplement;
import com.questappx.tictactoe.Extras.LevelsTicTac;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements onGameListener {


    public ImageView playerOneIv, playerTwoIv;
    ImageView settingIv, activityBackIv, backGameIv;
    long lastAdShow = 0;

    public static TextView playerOneClick, playerTwoClick;
    RelativeLayout playerWinLayout;
    AdView adView;
    int gameLoseTrace = 0;
//    public static String TAG = "adError";
    int gridSize;
//            , maskGameSpace;
    ImageView  playerWinImgIv, retryIv, homeIv;
    ImageView playerWinTextTv;
    int isAIPlaying, difficultyLevel, mode;

    RelativeLayout maskGameSpace;

    TicTac ticTac;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        if(interstitialAdImplement == null)
//        {
//            interstitialAdImplement =  new InterstitialAdImplement(getApplicationContext(), interstitialAd, fbInterstitial);
//        }

        init();
        clickListener();



//        AppOpenManager appOpenManager = new AppOpenManager(this);
//
        Random random = new Random();
        int rand = random.nextInt(2);
        if(rand == 1)
        {
            if(FirstActivity.interstitialAd != null)
            {
                FirstActivity.interstitialAd.show(MainActivity.this);
            }
            else {
                FirstActivity.activityOpenAd = true;
            }
        }



    }

    private void init()
    {
        Intent intent = getIntent();

         gridSize = intent.getIntExtra("grid",3);
//        mode = Data.PICKAGE_PLAYMODE;
        mode = intent.getIntExtra("mode",0);
        isAIPlaying = intent.getIntExtra("ai",0);
        difficultyLevel = intent.getIntExtra("difficulty",0);

        lastAdShow = System.currentTimeMillis();

        TextView gamePlayModeTv = findViewById(R.id.gamePlayModeTv);
        if(mode == 0)
        {
            gamePlayModeTv.setText("Original");
        }
        else if(mode == 1)
        {
            gamePlayModeTv.setText("Blockage");
        }
        else if(mode == 2)
        {
            gamePlayModeTv.setText("Barrier");
        }
        else if(mode == 3)
        {
            gamePlayModeTv.setText("Fade Away");
        }
        else if(mode == 4)
        {
            gamePlayModeTv.setText("Pickage");
        }

        if(gridSize == Data.LEVELS_GRID)
        {
            findViewById(R.id.playersTurnLayoutLevel).setVisibility(View.VISIBLE);
            findViewById(R.id.playersTurnLayout).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.playersTurnLayoutLevel).setVisibility(View.GONE);
            findViewById(R.id.playersTurnLayout).setVisibility(View.VISIBLE);
        }

//            interstitialAdImplement.setActivityOpenAd(true);



        playerOneIv = findViewById(R.id.playerOneIv);
        playerTwoIv = findViewById(R.id.playerTwoIv);
        settingIv = findViewById(R.id.settingIv);
        activityBackIv = findViewById(R.id.acitivtybackIv);
        playerOneClick = findViewById(R.id.playerone_clicks);
        playerTwoClick = findViewById(R.id.playertwo_clicks);
        backGameIv = findViewById(R.id.backGameTv);
        playerWinLayout = findViewById(R.id.playerwinLayout);
        playerWinImgIv = findViewById(R.id.playerwin_img_iv);
        retryIv = findViewById(R.id.retry_win_iv);
        homeIv = findViewById(R.id.home_win_iv);
        playerWinTextTv = findViewById(R.id.playerWin_tv);
        maskGameSpace = findViewById(R.id.mask_gamespace);


        if(gridSize != Data.LEVELS_GRID)
        {
            Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            playerWinImgIv.getLayoutParams().height = width + 15;
        }


        startPlayMode(gridSize, isAIPlaying, difficultyLevel);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                
                loadAdmobAds();
            }
        },4000);



    }

    private void loadAdmobAds()
    {


        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void loadRewardedAdmobAd() {
        RewardedAd.load(this, getString(R.string.RewardedId), new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                rewardedAd = null;
//                Log.d(TAG, "Rewarded : onAdFailedToLoad");
//                Log.d(TAG, "Rewarded : " + loadAdError.getMessage());
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd mrewardedAd) {
                super.onAdLoaded(rewardedAd);
//                Log.d(TAG, "Rewarded : onAdLoaded");
                rewardedAd = mrewardedAd;
            }
        });


    }

    private void startPlayMode(int grid, int isAIPlaying, int difficultyLevel) {
        if(grid == 3)
        {
//             ThreeXTicTac threeXTicTac = new ThreeXTicTac(this,difficultyLevel, mode);
            ThreeXTicTac threeXTicTac = new ThreeXTicTac(this, difficultyLevel, mode);
             ticTac = threeXTicTac;
//             threeXTicTac = new ThreeXTicTac(this,difficultyLevel, mode);
            showSettingDialog(threeXTicTac);
            if(isAIPlaying == 0)
            {
                threeXTicTac.playMultiPlayer();
            }
            else
            {
                threeXTicTac.playSingleMode();
            }

            threeXTicTac.setGameListener(this);

            //will restart match
            restartMatch(threeXTicTac);
//            settingIv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SettingDialog dialog = new SettingDialog(MainActivity.this, threeXTicTac);
//                    dialog.show();
//                }
//            });
        }
        else if(grid == 6)
        {
            SixXTicTac sixXTicTac = new SixXTicTac(this, difficultyLevel, mode);
            ticTac = sixXTicTac;
//             sixXTicTac = new SixXTicTac(this, difficultyLevel, mode);
            showSettingDialog(sixXTicTac);
            sixXTicTac.setGameListener(this);

            if(isAIPlaying == 0)
            {
                sixXTicTac.playMultiPlayer();
            }
            else
            {
                sixXTicTac.playSingleMode();
            }


            restartMatch(sixXTicTac);

        }
        else if(grid == 9)
        {
            NineXTicTac nineXTicTac = new NineXTicTac(this, difficultyLevel, mode);
            ticTac = nineXTicTac;
//             nineXTicTac = new NineXTicTac(this, difficultyLevel, mode);
            showSettingDialog(nineXTicTac);
            nineXTicTac.setGameListener(this);

            if(isAIPlaying == 0)
            {
                nineXTicTac.playMultiPlayer();
            }
            else
            {
                nineXTicTac.playSingleMode();
            }

            restartMatch(nineXTicTac);
        }
        else if(grid == 11)
        {
            ElevenXTicTac elevenXTicTac = new ElevenXTicTac(this, difficultyLevel, mode);
            ticTac = elevenXTicTac;
//             elevenXTicTac = new ElevenXTicTac(this, difficultyLevel, mode);
            showSettingDialog(elevenXTicTac);
            elevenXTicTac.setGameListener(this);

            if(isAIPlaying == 0)
            {
                elevenXTicTac.playMultiPlayer();
            }
            else
            {
                elevenXTicTac.playSingleMode();
            }

            restartMatch(elevenXTicTac);
        }
        else if(grid == Data.LEVELS_GRID)
        {
            LevelsTicTac levelsTicTac = new LevelsTicTac(this, 4, difficultyLevel);
            ticTac = levelsTicTac;
//             elevenXTicTac = new ElevenXTicTac(this, difficultyLevel, mode);
            showSettingDialog(levelsTicTac);
            levelsTicTac.setGameListener(this);

//            if(isAIPlaying == 0)
//            {
//                levelsTicTac.playMultiPlayer();
//            }
//            else
//            {
                levelsTicTac.playSingleMode();
//            }

            restartMatch(levelsTicTac);
        }
    }

    private void clickListener()
    {
        activityBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        maskGameSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        homeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        playerWinLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void showAdmobRewarded() {

        if(rewardedAd != null) {
            rewardedAd.show((Activity) MainActivity.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                }
            });
        }
        else {
            loadRewardedAdmobAd();
        }

    }

    private void restartMatch(TicTac ticTac)
    {

        retryIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(maskGameSpace.getVisibility() == View.VISIBLE)
//                {
//                    maskGameSpace.setVisibility(View.GONE);
//                }
                playerWinLayout.setVisibility(View.GONE);
                maskGameSpace.setVisibility(View.GONE);
                ticTac.restartMatch();
            }
        });

        backGameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (interstitialAd != null) {
//                    interstitialAd.show(MainActivity.this);
//                }
//                interstitialAdImplement.showInterstitial(MainActivity.this);

                if(FirstActivity.interstitialAd != null)
                {
                    FirstActivity.interstitialAd.show(MainActivity.this);
                }

                ticTac.restartMatch();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ExitDialog dialog = new ExitDialog(MainActivity.this);
        dialog.show();

//        super.onBackPressed();
//        finish();
    }

    private void showSettingDialog(TicTac ticTac)
    {
        settingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog dialog = new SettingDialog(MainActivity.this, ticTac);
                dialog.show();
            }
        });
    }

    
    public void showRandomAdmobRewarded(int gamePlayMode, int totalBoxes)
    {
        Random random = new Random();
        int rand;
        if((lastAdShow + 30000) <= System.currentTimeMillis())
        {
            lastAdShow = System.currentTimeMillis();
            if (gridSize != Data.LEVELS_GRID) {
                if (gamePlayMode == Data.FADEAWAY_PLAYMODE) {
                    rand = random.nextInt(2);
                    if(rand == 1)
                    {
                        showAdmobRewarded();
                    }
                    else
                    {
//
                        if(FirstActivity.interstitialAd != null)
                        {
                            FirstActivity.interstitialAd.show(MainActivity.this);
                        }
//                        interstitialAdImplement.showInterstitial(MainActivity.this);
                    }
                } else {
                    rand = random.nextInt(2);
                    if (rand == 1) {
                        showAdmobRewarded();
                    } else {
//
                        if(FirstActivity.interstitialAd != null)
                        {
                            FirstActivity.interstitialAd.show(MainActivity.this);
                        }
//                        interstitialAdImplement.showInterstitial(MainActivity.this);
                    }
                }
            }
            else
            {
                if(FirstActivity.interstitialAd != null)
                {
                    FirstActivity.interstitialAd.show(MainActivity.this);
                }
//                if(interstitialAd != null)
//                {
//                    interstitialAd.show(this);
//                }
//                interstitialAdImplement.showInterstitial(MainActivity.this);
            }
        }

    }

    @Override
    public void onGameEnd(int player, int gamePlayMode, int totalboxes) {

        maskGameSpace.setVisibility(View.VISIBLE);

        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        containerLayout.setDrawingCacheEnabled(true);
        containerLayout.buildDrawingCache();
        Bitmap imageToSave = containerLayout.getDrawingCache();
        playerWinImgIv.setImageBitmap(imageToSave);
        playerWinImgIv.invalidate();
        playerWinLayout.setVisibility(View.VISIBLE);

        ImageView imageViewWatchAd = findViewById(R.id.watchAdToGoNextlevel);
        imageViewWatchAd.setVisibility(View.GONE);
        if((gridSize == Data.LEVELS_GRID) && (player != 1) && (gameLoseTrace >= 2))
        {
            if(imageViewWatchAd.getVisibility() == View.GONE)
            {
                imageViewWatchAd.setVisibility(View.VISIBLE);
            }
            imageViewWatchAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchAd_GoNextLevel(gamePlayMode);
                }
            });
        }

        ImageView gotoNextLevel = findViewById(R.id.goToNextLevel_Iv);
        gotoNextLevel.setVisibility(View.GONE);
        if(gridSize == Data.LEVELS_GRID && player == 1)
        {
            //if levels activity and player 1 wins
            if(gotoNextLevel.getVisibility() == View.GONE)
            {
                gotoNextLevel.setVisibility(View.VISIBLE);
            }

            gotoNextLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gamePlayMode == CurrLevel
                    ((LevelsTicTac)ticTac).goToNextLevel(gamePlayMode);
                    playerWinLayout.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            if(gotoNextLevel.getVisibility() == View.VISIBLE)
            {
                gotoNextLevel.setVisibility(View.GONE);
            }
        }


        if(player == 0)
        {//Draw
            if(gridSize == Data.LEVELS_GRID)
            {
                Glide.with(MainActivity.this).load(R.drawable.youlost_text).into(playerWinTextTv);
            }
            else
            {
                Glide.with(MainActivity.this).load(R.drawable.draw_win_text).into(playerWinTextTv);
            }
            gameLoseTrace++;
        }
        else if(player == 1)
        {
            if(gridSize == Data.LEVELS_GRID)
            {
                Glide.with(MainActivity.this).load(R.drawable.youwin_text).into(playerWinTextTv);
            }
            else
            {
                Glide.with(MainActivity.this).load(R.drawable.player1_win_text).into(playerWinTextTv);
            }
        }
        else
        {
            if(gridSize == Data.LEVELS_GRID)
            {
                Glide.with(MainActivity.this).load(R.drawable.youlost_text).into(playerWinTextTv);
            }
            else
            {
                if(isAIPlaying == 1)
                {
                    Glide.with(MainActivity.this).load(R.drawable.ai_win_text).into(playerWinTextTv);
                }
                else
                {
                    Glide.with(MainActivity.this).load(R.drawable.player2_win_text).into(playerWinTextTv);
                }
            }
            gameLoseTrace++;
        }

        showRandomAdmobRewarded(gamePlayMode , totalboxes);

        Glide.with(this).load(R.drawable.player_one_glow).into(playerOneIv);
        Glide.with(this).load(R.drawable.player_two_dim).into(playerTwoIv);

    }

    private void watchAd_GoNextLevel(int currLevel) {

        if(rewardedAd != null) {
            Toast.makeText(this, "Showing Ad", Toast.LENGTH_SHORT).show();
            rewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    ImageView gotoNextLevel = findViewById(R.id.goToNextLevel_Iv);
                    gotoNextLevel.setVisibility(View.VISIBLE);

                    ImageView watchAdBtn = findViewById(R.id.watchAdToGoNextlevel);
                    watchAdBtn.setVisibility(View.GONE);

                    gotoNextLevel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LevelsTicTac) ticTac).goToNextLevel(currLevel);
                            gameLoseTrace = 0;
                            playerWinLayout.setVisibility(View.GONE);
                        }
                    });
                }
            });

        }
        else
        {
            Toast.makeText(this, "Ad not loaded yet", Toast.LENGTH_SHORT).show();
            loadRewardedAdmobAd();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        ticTac.DeallocateMemory();
//        Glide.get(this).clearDiskCache();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();
            }
        }).start();

    }


}

