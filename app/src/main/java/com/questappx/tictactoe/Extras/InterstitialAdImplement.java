    package com.questappx.tictactoe.Extras;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.installations.remote.FirebaseInstallationServiceClient;
import com.questappx.tictactoe.Data;
import com.questappx.tictactoe.FirstActivity;
import com.questappx.tictactoe.MainActivity;
import com.questappx.tictactoe.R;
import com.questappx.tictactoe.WaterSort.onSortEndListener;


    public class InterstitialAdImplement {

    private static final String TAG = "InterstitialAdImplement";
    Context context;
    InterstitialAd interstitialAd;
    public int reward = 0;
        boolean activityOpenAd = false;
    // reward 1 -- adding tube
    //reward 2 -- first enter show ad

    onSortEndListener rewardListener;

    com.facebook.ads.InterstitialAd fbInterstitial;


    public InterstitialAdImplement(Context context, InterstitialAd interstitialAd, com.facebook.ads.InterstitialAd fbInterstitial) {
        this.context = context;
        this.interstitialAd = interstitialAd;
        this.fbInterstitial = fbInterstitial;


    }

        public void setRewardListener(onSortEndListener rewardListener)
        {
            this.rewardListener = rewardListener;
        }

    public void InterstitialAdLoadCall() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, context.getResources().getString(R.string.InterstitialId), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        FirstActivity.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                        if(true)
                        {
                            FirstActivity.interstitialAd.show((Activity) context);
                            activityOpenAd = false;
                        }

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                if(reward == 1)
                                {
                                    reward = 0;
                                    rewardListener.onGameEnd();
                                }
                                // Called when fullscreen content is dismissed.
                                Log.d(TAG, "The ad was dismissed.");
//                                EventBus.getDefault().post(new AdMessageEvent("onAdClosed"));
                                InterstitialAdLoadCall();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                FirstActivity.interstitialAd = null;
                                Log.d(TAG, "The ad was shown.");
                            }
                        });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;
//                        InterstitialAdLoadCall();
                        initializeFB_Interstitial();
                    }


                });

    }

        public void setActivityOpenAd(boolean bool)
        {

            if(interstitialAd != null)
            {
                showInterstitial((Activity) context);
            }
            else {
                activityOpenAd = bool;
            }
        }

        private void initializeFB_Interstitial() {
//        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#"+Data.FB_INTERSTITIAL_ID);
            fbInterstitial = new com.facebook.ads.InterstitialAd(context, Data.FB_INTERSTITIAL_ID);
            // Create listeners for the Interstitial Ad
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
                    Log.e(TAG, "Fb :Interstitial ad displayed.");
//                    itemClickListener.OnClick(1);
//                Toast.makeText(EditorActivity.this, "intersitial displayed", Toast.LENGTH_SHORT).show();
//                interstitialAd.loadAd(
//                        interstitialAd.buildLoadAdConfig()
//                                .withAdListener(this)
//                                .build());
                }


                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                    Log.e(TAG, "Fb :Interstitial ad dismissed.");
//                Toast.makeText(EditorActivity.this, "interstitial dismissed", Toast.LENGTH_SHORT).show();
                    fbInterstitial.loadAd(
                            fbInterstitial.buildLoadAdConfig()
                                    .withAdListener(this)
                                    .build());
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    FirstActivity.fbInterstitial = null;
                    // Ad error callback
                    Log.e(TAG, "Fb :Interstitial ad failed to load: " + adError.getErrorMessage());
//                Toast.makeText(EditorActivity.this, adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Fb :Interstitial ad is loaded and ready to be displayed!");
//                Toast.makeText(EditorActivity.this, "on adLoaded", Toast.LENGTH_SHORT).show();
                    // Show the ad
//                interstitialAd.show();
                    if(activityOpenAd)
                    {
                        if(fbInterstitial.isAdLoaded())
                        {
                            fbInterstitial.show();
                        }
                        activityOpenAd = false;
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                    Log.d(TAG, "Fb :Interstitial ad clicked!");
//                Toast.makeText(EditorActivity.this, "onAdClicked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                    Log.d(TAG, "Fb :Interstitial ad impression logged!");
//                Toast.makeText(EditorActivity.this, "onLogingImpression", Toast.LENGTH_SHORT).show();
                }
            };

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            fbInterstitial.loadAd(
                    fbInterstitial.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        }


        public void showInterstitial(Activity activity)
        {
            if(FirstActivity.interstitialAd != null)
            {
                FirstActivity.interstitialAd.show(activity);
            }
            else if(FirstActivity.fbInterstitial != null)
            {
                if(FirstActivity.fbInterstitial.isAdLoaded())
                {
                    FirstActivity.fbInterstitial.show();
                }
            }
        }
}
