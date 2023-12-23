package com.questappx.tictactoe.Extras;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.util.concurrent.atomic.AtomicBoolean;


public class Gdpr {
    Activity context;
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    
    String TAG = "GDPR";

    public Gdpr(Activity context) {
        this.context = context;
    }

    public void setGdpr() {
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(context);
        consentInformation.requestConsentInfoUpdate(
                context,
                params,
                (ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            context,
                            (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
                                if (loadAndShowError != null) {
                                    // Consent gathering failed.
                                    Log.w(TAG, String.format("%s: %s",
                                            loadAndShowError.getErrorCode(),
                                            loadAndShowError.getMessage()));
                                }

                                // Consent has been gathered.
                                if (consentInformation.canRequestAds()) {
                                    initializeMobileAdsSdk();
                                }
                            }


                    );
                },
                (ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
                    // Consent gathering failed.
                    Log.w(TAG, String.format("%s: %s",
                            requestConsentError.getErrorCode(),
                            requestConsentError.getMessage()));
                });

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk();
        }
    }

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(context);

        // TODO: Request an ad.
        // InterstitialAd.load(...);
    }

}
