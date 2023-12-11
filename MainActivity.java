package edu.birzeit.a1191740_taher_hasan;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
    private ImageView backgroundImageView;
    private int[] backgroundImages = {R.drawable.day,R.drawable.night};
    private int currentIndex = 0;
    private float maxFAD = 1.0F, minFAD = 0.7F; // fading Range
    private Handler cloudAnimationHandler1 = new Handler();
    private Handler cloudAnimationHandler2 = new Handler();
    private final int cloudAnimationDelay1 = 8000,cloudAnimationDelay2 = 12000 ,  fadeInDuration = 5000,fadeOutDuration = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageMoon = (ImageView)findViewById(R.id.moon);
        final ImageView imageSun = (ImageView)findViewById(R.id.sun);
        backgroundImageView = findViewById(R.id.backgroundImageView);

        imageMoon.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.start_moon));
        imageSun.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.start_sun));
        startCloudAnimation(); // for the first cloud
        startCloudAnimation2();// for the second cloud


        changeBackgroundImageAfterDelay();
        /*
            Note: this will ENTER A LOOP
            first changeBackgroundImageAfterDelay() ==> at 0 second
            then fadeIn.start()                     ==> at 5 seconds
            then fadeOut() to fadeOut.start()       ==> at 10 seconds
            and again to changeBackgroundImageAfterDelay
            and so on until the program stops
        */


    }

    private void startCloudAnimation() {
        final ImageView imageCloud1 = (ImageView)findViewById(R.id.cloud1);
        imageCloud1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.start_cloud1));
        cloudAnimationHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnimation();
            }
        }, cloudAnimationDelay1); // 8 seconds
    }
    private void startCloudAnimation2() {
        final ImageView imageCloud2 = (ImageView)findViewById(R.id.cloud2);
        imageCloud2.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.start_cloud2));
        cloudAnimationHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnimation2();
            }
        }, cloudAnimationDelay2);// 12 seconds
    }
    private void changeBackgroundImageAfterDelay() {

        // Change the background image
        backgroundImageView.setImageResource(backgroundImages[currentIndex]);

        // Fade in the new image
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(backgroundImageView, "alpha", minFAD, maxFAD);
        fadeIn.setDuration(fadeInDuration);

        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                // Increment the index or reset to 0 if at the end
                currentIndex = (currentIndex + 1) % backgroundImages.length;

                // Call the fadeOut function after the fadeIn animation ends
                fadeOut();
            }
        });

        fadeIn.start();
    }

    private void fadeOut() {


        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(backgroundImageView, "alpha", maxFAD, minFAD);
        fadeOut.setDuration(fadeOutDuration);

        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                changeBackgroundImageAfterDelay();
            }
        });

        fadeOut.start();
    }




}