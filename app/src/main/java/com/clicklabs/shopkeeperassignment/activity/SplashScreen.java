package com.clicklabs.shopkeeperassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.utils.CommonData;


public class SplashScreen extends BaseActivity {


    ImageView imageView ;
    LinearLayout linearlayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView)findViewById(R.id.iv_splash_logo);
        linearlayout = (LinearLayout)findViewById(R.id.layout_linear_splash);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 680);
        animation.scaleCurrentDuration(1);
        animation.setDuration(3000);
        animation.setFillAfter(false);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new MyAnimationListener());
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent intent;
                if (CommonData.getAppUser(getApplicationContext()) == null) {
                    intent = new Intent(SplashScreen.this, SignUp.class);

                } else {
                    intent = new Intent(SplashScreen.this, DisplayCustomers.class);

                }
                startActivity(intent);
                finish();
            }
        }, 4 * 1000);

    }
    public class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            imageView.clearAnimation();
            Log.v("TAG", String.valueOf(linearlayout.getWidth()));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getWidth(), imageView.getHeight());
            lp.gravity = Gravity.BOTTOM ;
            lp.setMargins(0, 0, 0, 100);
            imageView.setLayoutParams(lp);
            AlphaAnimation blinkAnimation = new AlphaAnimation(1,0);
            blinkAnimation.setDuration(100);
            Log.v("TAGimagemarginbottom", String.valueOf(lp.bottomMargin));
            imageView.setAnimation(blinkAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}

