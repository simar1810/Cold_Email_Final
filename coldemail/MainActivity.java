package com.example.coldemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class MainActivity extends AppCompatActivity {
    Button button;
    VideoView videoView;
//    private InterstitialAd mInterstitialAd;
    //private static AppOpenManager appOpenManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        appOpenManager = new AppOpenManager(this);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
//                new InterstitialAdLoadCallback() {
//                    private static final String TAG = "Add Loaded" ;
//
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i(TAG, "onAdLoaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.d(TAG, loadAdError.toString());
//                        mInterstitialAd = null;
//                    }
//                });
//        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
//            private static final String TAG = "Callback";
//
//            @Override
//            public void onAdClicked() {
//                // Called when a click is recorded for an ad.
//                Log.d(TAG, "Ad was clicked.");
//            }
//
//            @Override
//            public void onAdDismissedFullScreenContent() {
//                // Called when ad is dismissed.
//                // Set the ad reference to null so you don't show the ad a second time.
//                Log.d(TAG, "Ad dismissed fullscreen content.");
//                mInterstitialAd = null;
//            }
//
//            @Override
//            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                // Called when ad fails to show.
//                Log.e(TAG, "Ad failed to show fullscreen content.");
//                mInterstitialAd = null;
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Called when an impression is recorded for an ad.
//                Log.d(TAG, "Ad recorded an impression.");
//            }
//
//            @Override
//            public void onAdShowedFullScreenContent() {
//                // Called when ad is shown.
//                Log.d(TAG, "Ad showed fullscreen content.");
//            }
//        });


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,login.class));
            }
        });
//        if (mInterstitialAd != null) {
//            mInterstitialAd.show(MainActivity.this);
//        } else {
//            Log.d("TAG", "The interstitial ad wasn't ready yet.");
//        }

    }
    @Override
    protected void onPostResume(){
        videoView.resume();
        super.onPostResume();

    }
    @Override
    protected void onRestart(){
        videoView.start();
        super.onRestart();
    }
    @Override
    protected void onPause(){
        videoView.suspend();
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        videoView.stopPlayback();
        super.onDestroy();
    }


}