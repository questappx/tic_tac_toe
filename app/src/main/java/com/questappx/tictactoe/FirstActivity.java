package com.questappx.tictactoe;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.common.internal.Objects;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;
import com.questappx.tictactoe.Extras.AppOpenManager;
import com.questappx.tictactoe.Extras.Gdpr;
import com.questappx.tictactoe.Extras.InterstitialAdImplement;
import com.questappx.tictactoe.Extras.RateUsExit;
import com.questappx.tictactoe.WaterSort.WaterSortLevelActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {
    ;
    ImageView  tttcard, watersortcard;
    public static boolean IS_SOUND_ENABLED;

    public static boolean activityOpenAd = false;
    private static final String TAG = "InterstitialAdImplement";

    private ConsentInformation consentInformation;
    private ConsentForm consentForm;


    public static RewardedAd rewardedAd;
       public static InterstitialAd interstitialAd;
       public static com.facebook.ads.InterstitialAd fbInterstitial;

    TextView suggestUs;
    RelativeLayout suggestionLayout;
    int spinnerCurrentItem;
    String currentItem;
    int returnUser = 0;

//    public static InterstitialAdImplement interstitialAdImplement;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        Gdpr gdpr = new Gdpr(this);
        gdpr.setGdpr();


        init();


//        interstitialAdImplement = new InterstitialAdImplement(getApplicationContext(), interstitialAd, fbInterstitial);
//        interstitialAdImplement.InterstitialAdLoadCall();


       loadAdmobAds();

        clickListener();

    }

       private void loadAdmobAds()
       {
           loadInterstitialAd();
           loadRewardedAdmobAd();

           new AppOpenManager(this);
       }

       private void loadInterstitialAd()
       {
           AdRequest adRequest = new AdRequest.Builder().build();
           InterstitialAd.load(FirstActivity.this, getResources().getString(R.string.InterstitialId), adRequest,
                   new InterstitialAdLoadCallback() {
                       @Override
                       public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                           // The mInterstitialAd reference will be null until
                           // an ad is loaded.
                           FirstActivity.interstitialAd = interstitialAd;
                           Log.i(TAG, "onAdLoaded");

                           if(activityOpenAd)
                           {
                               activityOpenAd = false;
                               if(FirstActivity.interstitialAd != null)
                               {
                                   FirstActivity.interstitialAd.show(FirstActivity.this);
                               }
                           }

                           interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                               @Override
                               public void onAdDismissedFullScreenContent() {
//                                   if(reward == 1)
//                                   {
//                                       reward = 0;
//                                       rewardListener.onGameEnd();
//                                   }
//                                   // Called when fullscreen content is dismissed.
//                                   Log.d(TAG, "The ad was dismissed.");
////                                EventBus.getDefault().post(new AdMessageEvent("onAdClosed"));
//                                   InterstitialAdLoadCall();
                                   loadInterstitialAd();
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
                           loadInterstitialAd();
//                        InterstitialAdLoadCall();
//                           initializeFB_Interstitial();
                       }


                   });
       }

    private void loadRewardedAdmobAd() {
        RewardedAd.load(FirstActivity.this, getString(R.string.RewardedId), new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
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

    private void init()
    {
        suggestUs = findViewById(R.id.suggestUs);
        suggestionLayout = findViewById(R.id.suggestionLayout);

        tttcard = findViewById(R.id.tttcardIv);
        watersortcard = findViewById(R.id.waterSortcard);

        IS_SOUND_ENABLED = true;

        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//                .addTestDeviceHashedId("TEST-DEVICE-HASHED-ID")
                .build();

        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setConsentDebugSettings(debugSettings)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                        if (consentInformation.isConsentFormAvailable()) {
                            loadForm();
                        }
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.
                    }
                });


//        new AppOpenManager(this);
    }

    private void clickListener()
    {
        tttcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, ModeActivity.class));
            }
        });

        watersortcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, WaterSortLevelActivity.class));
            }
        });


        suggestUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestionLayout.setVisibility(View.VISIBLE);
                ReportWorking();
            }
        });
    }

//       private void sigIn() {
//              Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//              startActivityForResult(signInIntent, 1000);
//       }

//       @Override
//       public void onActivityResult(int requestCode, int resultCode, Intent data) {
//              super.onActivityResult(requestCode, resultCode, data);
//
//              // Handle Google Sign-in result
//              if (requestCode == 1000) {
//                     Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                     try {
//                            task.getResult(ApiException.class);
//                            navigateToSecondActivity();
////                            GoogleSignInAccount account = task.getResult(ApiException.class);
////                            // Signed in successfully, now insert data to MySQL using PHP
////                            insertDataToMySQL(account);
//                     } catch (ApiException e) {
//                            // Handle sign-in failure
//                            Log.w("errTag", "signInResult:failed code=" + e.getStatusCode());
//                     }
//              }
//       }
//
//       private void navigateToSecondActivity() {
////              Toast.makeText(this, "Sign In ok", Toast.LENGTH_SHORT).show();
//       }
//
//       private void insertDataToMySQL(GoogleSignInAccount account) {
//           String userName = account.getDisplayName();
//           String email = account.getEmail();
//
//
//       }


       private void ReportWorking() {
        final EditText problemDetails;
        final Spinner spinner;
        TextView sendEmailButton;
        ImageView hideEmailLayout;
        final EditText toSendEditText;
        toSendEditText = findViewById(R.id.toSendEditText);
        hideEmailLayout = findViewById(R.id.hideEmailLayout);
        hideEmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestionLayout.setVisibility(View.GONE);
            }
        });
        problemDetails = findViewById(R.id.messageEditText);
        spinner = findViewById(R.id.spinner);
        sendEmailButton = findViewById(R.id.sendEmailButton);


        problemDetails.setText(null);

        List<String> categories = new ArrayList<String>();
        categories.add("Choose Option");
        categories.add("Report a Problem");
        categories.add("Request a Feature");
        categories.add("Advertise Your Business");
        categories.add("Others");

//        spinner.setSelection(categories.size() - 4);
        spinner.setSelection(0);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentItem = parent.getItemAtPosition(position).toString();
                spinnerCurrentItem = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerCurrentItem == 0) {
                    Toast.makeText(FirstActivity.this, "Choose Option", Toast.LENGTH_SHORT).show();
                } else {
                    if (problemDetails.getText().toString().equals("")) {
                        Toast.makeText(FirstActivity.this, "Enter Message", Toast.LENGTH_SHORT).show();
                    } else {
                        String mailTo = "mailto:questappx@gmail.com" +
                                "?cc=" + getString(R.string.app_name) +
                                "&subject=" + Uri.encode(currentItem) +
                                "&body=" + problemDetails.getText().toString();
                        Intent email = new Intent(Intent.ACTION_SENDTO);
//                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"continuumdesk@gmail.com"});
//                        email.putExtra(Intent.EXTRA_SUBJECT, currentItem);
//                        email.putExtra(Intent.EXTRA_TEXT, problemDetails.getText().toString());
                        //need this to prompts email client only
//                        email.setType("message/rfc822");
                        email.setData(Uri.parse(mailTo));
                        try {
                            email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            returnUser = 1;
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void func_privacypolicy()
    {
        Uri uri = Uri.parse(Data.PrivacyPolicy);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void func_shareapp()
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= "\n" + Data.ShareMessage + "\n\n";
            String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+appLink);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void func_moreapps(View view)
    {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Quest%20Appx");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void func_rateus(View view)
    {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void loadForm() {
        // Loads a consent form. Must be called on the main thread.
        UserMessagingPlatform.loadConsentForm(
                this,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        FirstActivity.this.consentForm = consentForm;
                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(
                                    FirstActivity.this,
                                    new ConsentForm.OnConsentFormDismissedListener() {
                                        @Override
                                        public void onConsentFormDismissed(@Nullable FormError formError) {
                                            if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
                                                // App can start requesting ads.
                                            }

                                            // Handle dismissal by reloading form.
                                            loadForm();
                                        }
                                    });
                        }
                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        // Handle the error.
                    }
                }
        );
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (returnUser == 1) {
            suggestionLayout.setVisibility(View.GONE);
        } else {
            Log.v("error", "visibility");
        }

    }

//       private void sendNotification()
//       {
//              NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//              Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher_foreground, null);
//              BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//              Bitmap bitmap = bitmapDrawable.getBitmap();
//
//              Intent intent = new Intent(FirstActivity.this, ModeActivity.class);
//              //to prevent multiple activities to be opened, if one already then don't call other
//              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//              PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_NOTI, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//              Notification notification;
//
//              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                     notification = new Notification.Builder(this).
//                             setLargeIcon(bitmap)
//                             .setSmallIcon(R.drawable.ic_launcher_foreground)
//                             .setContentIntent(pendingIntent)
//                             .setContentText("Play Tic Tac Toe with your friends Now.")
//                             .setChannelId(NOTIFICATION_CHANNEL)
//                             .setAutoCancel(true)
//                             .setSubText("We got a message.").build();
//
//                     manager.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL, "NEW CHANNEL", NotificationManager.IMPORTANCE_HIGH));
//              }
//              else
//              {
//                     notification = new Notification.Builder(this).
//                             setLargeIcon(bitmap)
//                             .setSmallIcon(R.drawable.ic_launcher_foreground)
//                             .setContentText("New Message")
//                             .setAutoCancel(true)
//                             .setContentIntent(pendingIntent)
//                             .setSubText("We got a message.").build();
//              }
//
//              manager.notify(NOTIFICATION_ID, notification);
//
//       }

    @Override
    public void onBackPressed() {
        if(suggestionLayout.getVisibility() == View.VISIBLE)
        {
            suggestionLayout.setVisibility(View.GONE);
        }
        else
        {
               new RateUsExit(this).textDialog();
//            super.onBackPressed();
        }
    }

//       private void loadRewardedAdmobAd() {
//              RewardedAd.load(this, getString(R.string.RewardedId), new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
//                     @Override
//                     public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                            super.onAdFailedToLoad(loadAdError);
//                            rewardedAd = null;
////                Log.d(TAG, "Rewarded : onAdFailedToLoad");
////                Log.d(TAG, "Rewarded : " + loadAdError.getMessage());
//                     }
//
//                     @Override
//                     public void onAdLoaded(@NonNull RewardedAd mrewardedAd) {
//                            super.onAdLoaded(rewardedAd);
////                Log.d(TAG, "Rewarded : onAdLoaded");
//                            rewardedAd = mrewardedAd;
//                     }
//              });
//
//
//       }
}