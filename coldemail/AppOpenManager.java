//package com.example.coldemail;
//
//import android.app.Activity;
//import android.app.Application;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.view.menu.ShowableListMenu;
//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.OnLifecycleEvent;
//import androidx.lifecycle.ProcessLifecycleOwner;
//import static androidx.lifecycle.Lifecycle.Event.ON_START;
//
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.appopen.AppOpenAd;
//
//import java.util.Date;
//
///** Prefetches App Open Ads. */
//public class AppOpenManager implements LifecycleObserver,Application.ActivityLifecycleCallbacks{
//   private static final String LOG_TAG = "AppOpenManager";
//   private static final String AD_UNIT_ID = "ca-app-pub-2039327617695906/2312194107";
//
//    private AppOpenAd appOpenAd = null;
//   private Activity currentActivity;
//   private AppOpenAd.AppOpenAdLoadCallback loadCallback;
//   private static boolean isShowingAd = false;
//   private final MainActivity myApplication;
//    private long loadTime = 0;
//
//
//    /** Constructor
//    * @param myApplication*/
//   @RequiresApi(api = Build.VERSION_CODES.Q)
//   public AppOpenManager(MainActivity myApplication) {
//      this.myApplication = myApplication;
//      this.myApplication.registerActivityLifecycleCallbacks(this);
//       ProcessLifecycleOwner.get().getLifecycle().addObserver((LifecycleObserver) this);
//
//
//   }
//    /** LifecycleObserver methods */
//    @OnLifecycleEvent(ON_START)
//    public void onStart() {
//        showAdIfAvailable();
//        Log.d(LOG_TAG, "onStart");
//    }
//
//
//    /** Request an ad */
//   public void fetchAd() {
//      // Have unused ad, no need to fetch another.
//      if (isAdAvailable()) {
//         return;
//      }
//
//      loadCallback =
//              new AppOpenAd.AppOpenAdLoadCallback() {
//                 /**
//                  * Called when an app open ad has loaded.
//                  *
//                  * @param ad the loaded app open ad.
//                  */
//                 @Override
//                 public void onAdLoaded(AppOpenAd ad) {
//                    AppOpenManager.this.appOpenAd = ad;
//                    AppOpenManager.this.loadTime = (new Date()).getTime();
//
//                 }
//
//                 /**
//                  * Called when an app open ad has failed to load.
//                  *
//                  * @param loadAdError the error.
//                  */
//                 @Override
//                 public void onAdFailedToLoad(LoadAdError loadAdError) {
//                    // Handle the error.
//                 }
//
//              };
//      AdRequest request = getAdRequest();
//      AppOpenAd.load(
//              myApplication, AD_UNIT_ID, request,
//              AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
//   }
//
//
//   /** Creates and returns ad request. */
//   private AdRequest getAdRequest() {
//      return new AdRequest.Builder().build();
//   }
//
//   /** Utility method that checks if ad exists and can be shown. */
//   public boolean isAdAvailable() {
//       return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
//
//   }
//
//    private boolean wasLoadTimeLessThanNHoursAgo(int i) {
//        long dateDifference = (new Date()).getTime() - this.loadTime;
//        long numMilliSecondsPerHour = 3600000;
//        return (dateDifference < (numMilliSecondsPerHour * i));
//    }
//
//    /** ActivityLifecycleCallback methods */
//   @Override
//   public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
//
//   @Override
//   public void onActivityStarted(Activity activity) {
//      currentActivity = activity;
//   }
//
//   @Override
//   public void onActivityResumed(Activity activity) {
//      currentActivity = activity;
//   }
//
//   @Override
//   public void onActivityStopped(Activity activity) {}
//
//   @Override
//   public void onActivityPaused(Activity activity) {}
//
//   @Override
//   public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}
//
//   @Override
//   public void onActivityDestroyed(Activity activity) {
//      currentActivity = null;
//   }
//   /** Shows the ad if one isn't already showing. */
//   public void showAdIfAvailable() {
//      // Only show ad if there is not already an app open ad currently showing
//      // and an ad is available.
//      if (!isShowingAd && isAdAvailable()) {
//         Log.d(LOG_TAG, "Will show ad.");
//
//         FullScreenContentCallback fullScreenContentCallback =
//                 new FullScreenContentCallback() {
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                       // Set the reference to null so isAdAvailable() returns false.
//                       AppOpenManager.this.appOpenAd = null;
//                       isShowingAd = false;
//                       fetchAd();
//                    }
//
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(AdError adError) {}
//
//                    @Override
//                    public void onAdShowedFullScreenContent() {
//                       isShowingAd = true;
//                    }
//                 };
//
//         appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
//         appOpenAd.show(currentActivity);
//
//      } else {
//         Log.d(LOG_TAG, "Can not show ad.");
//         fetchAd();
//      }
//   }
//
//
//}
