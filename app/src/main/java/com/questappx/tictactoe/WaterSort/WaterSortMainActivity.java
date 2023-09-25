package com.questappx.tictactoe.WaterSort;

import static com.questappx.tictactoe.Data.NO_COLOR;
import static com.questappx.tictactoe.FirstActivity.fbInterstitial;
import static com.questappx.tictactoe.FirstActivity.interstitialAd;
//import static com.questappx.tictactoe.FirstActivity.interstitialAdImplement;
import static com.questappx.tictactoe.FirstActivity.rewardedAd;
import static com.questappx.tictactoe.WaterSort.WaterSortLevelActivity.tubeColors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.questappx.tictactoe.Data;
import com.questappx.tictactoe.Extras.AppOpenManager;
import com.questappx.tictactoe.Extras.Internet;
import com.questappx.tictactoe.Extras.InterstitialAdImplement;
import com.questappx.tictactoe.Extras.LevelsTicTac;
import com.questappx.tictactoe.FirstActivity;
import com.questappx.tictactoe.MainActivity;
import com.questappx.tictactoe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaterSortMainActivity extends AppCompatActivity {
    private List<Tube> tubes = new ArrayList<>();
    int levelNo;
    private TubeAdapter tubeAdapter;
    GridView gridView;
    TextView levelTv;
    RelativeLayout playerwinLayout;
    AdView adView;

    ImageView addTubeBtn, retryBtn, undoActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_sort);

        linkXml();

        clickListener();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loadAdmobAds();
            }
        },4000);
    }

    private void loadAdmobAds()
    {
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                loadRewardedAdmobAd();
//                new InterstitialAdImplement(MainActivity.this, interstitialAd).InterstitialAdLoadCall();
//            }
//        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        Random random = new Random();
        int rand = random.nextInt(2);
        if(rand == 1)
        {
            if(FirstActivity.interstitialAd != null)
            {
                FirstActivity.interstitialAd.show(WaterSortMainActivity.this);
            }
            else {
                FirstActivity.activityOpenAd = true;
            }
        }
    }
    private void clickListener() {
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGrid();
            }
        });
        
        undoActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoAction();

            }
        });

        addTubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchAdToAddTube();
            }
        });

        playerwinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void addNewTube() {
        if(tubes.size() == 0 || tubeAdapter == null)
        {
            Toast.makeText(this, "Can't add Tube", Toast.LENGTH_SHORT).show();
            return;
        }

        Tube tube = new Tube();
        tube.setTotalSize(tubes.get(0).totalSize);
        for(int i=0;i<tubes.get(0).totalSize;i++)
        {
            tube.setRandomColor(NO_COLOR);
        }
        tubes.add(tube);
        tubeAdapter.notifyDataSetChanged();
    }

    private void undoAction() {
        tubeAdapter.undoMoves();
    }

    private void linkXml() {
        gridView = findViewById(R.id.grid_view);
        levelTv = findViewById(R.id.waterSortLevelNoTv);
        levelNo = getIntent().getIntExtra(Data.TAG_WATERSORT_LEVELSINFO,0);
        levelTv.setText("Level : " + levelNo);
        
        retryBtn = findViewById(R.id.retrySortBtn);
        undoActionBtn = findViewById(R.id.undoSortBtn);
        addTubeBtn = findViewById(R.id.addTubeBtn);

        playerwinLayout = findViewById(R.id.playerwinLayout);

        setGrid();

//        if(interstitialAdImplement == null)
//        {
//            interstitialAdImplement = new InterstitialAdImplement(getApplicationContext(), interstitialAd, fbInterstitial);
//            interstitialAdImplement.InterstitialAdLoadCall();
//        }
//        interstitialAdImplement.reward = 2;
//        interstitialAdImplement.setRewardListener(new onSortEndListener() {
//            @Override
//            public void onGameEnd() {
//                addNewTube();
//                if(addTubeBtn.getVisibility() == View.VISIBLE)
//                {
//                    addTubeBtn.setVisibility(View.GONE);
//                }
//            }
//        });



    }

    private void playerWinWorking()
    {
        LinearLayout retryWinLayout, homeWinLayout, nextLevelWinLayout;
        retryWinLayout = findViewById(R.id.retry_win_iv);
        homeWinLayout = findViewById(R.id.home_win_iv);
        nextLevelWinLayout = findViewById(R.id.goToNextLevel_Iv);

        retryWinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGrid();
                if(playerwinLayout.getVisibility() == View.VISIBLE)
                {
                    playerwinLayout.setVisibility(View.GONE);
                }

            }
        });

        homeWinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerwinLayout.getVisibility() == View.VISIBLE)
                {
                    playerwinLayout.setVisibility(View.GONE);
                }
                finish();
            }
        });

        nextLevelWinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextLevel();
                if(playerwinLayout.getVisibility() == View.VISIBLE)
                {
                    playerwinLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void gotoNextLevel() {
        if(tubeAdapter.goToNextLevel(levelNo))
        {
            levelNo++;
            levelTv.setText("Level : " + levelNo);
            setGrid();
        }
        else {
            Toast.makeText(this, "More Levels Coming Soon", Toast.LENGTH_SHORT).show();
        }
    }

    private void setGrid()
    {
        int numColumns;
        int numOfTubes = tubeColors[levelNo].length;
        if(numOfTubes <=6)
        {
            numColumns = 4;
        }
        else if(numOfTubes <= 10)
        {
            numColumns = 5;
            gridView.setHorizontalSpacing(0);
        }
        else
        {
            numColumns = 6;
            gridView.setHorizontalSpacing(0);
        }

        gridView.setNumColumns(numColumns);


        if(tubes != null)
        {
            tubes.clear();
        }
        for (int i = 0; i < tubeColors[levelNo].length; i++) {
            Tube tube = new Tube();
            tube.setTotalSize(tubeColors[levelNo][i].length);
            for(int j=0;j<tubeColors[levelNo][i].length;j++)
            {
                tube.setRandomColor(tubeColors[levelNo][i][j]);
            }
            tubes.add(tube);
        }

        tubeAdapter = new TubeAdapter(this, gridView, tubes,levelNo, new onSortEndListener() {
            @Override
            public void onGameEnd() {
                gameEndWorking();
            }
        });
        gridView.setAdapter(tubeAdapter);

        tubeAdapter.notifyDataSetChanged();

        if(addTubeBtn.getVisibility() == View.GONE)
        {
            addTubeBtn.setVisibility(View.VISIBLE);
        }


    }

    private void gameEndWorking() {
        if(playerwinLayout.getVisibility() == View.GONE)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playerwinLayout.setVisibility(View.VISIBLE);
                    playerWinWorking();
                }
            },600);
        }

            showRandomAd();
    }

    private void showRandomAd() {
        if(FirstActivity.interstitialAd != null)
        {
            FirstActivity.interstitialAd.show(WaterSortMainActivity.this);
        }
//        if(interstitialAdImplement != null)
//        {
//            Random random = new Random();
//            int rand = random.nextInt(2);
//            if(rand == 1)
//            {
//                interstitialAdImplement.showInterstitial(WaterSortMainActivity.this);
//            }
//        }
    }

    public void watchAdToAddTube() {
        Toast.makeText(this, "Loading Ad", Toast.LENGTH_SHORT).show();

        if(rewardedAd != null)
        {
            rewardedAd.show((Activity) WaterSortMainActivity.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    addNewTube();
                    if(addTubeBtn.getVisibility() == View.VISIBLE)
                    {
                        addTubeBtn.setVisibility(View.GONE);
                    }
                }
            });
        }
        else {
            if(FirstActivity.interstitialAd != null)
            {
                FirstActivity.interstitialAd.show(WaterSortMainActivity.this);
            }
//            interstitialAdImplement.reward = 1;
//            interstitialAdImplement.showInterstitial(WaterSortMainActivity.this);
        }
    }

    private void loadRewardedAdmobAd() {
        RewardedAd.load(WaterSortMainActivity.this, getString(R.string.RewardedId), new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd mrewardedAd) {
                super.onAdLoaded(rewardedAd);
                rewardedAd = mrewardedAd;

            }
        });
    }

}

