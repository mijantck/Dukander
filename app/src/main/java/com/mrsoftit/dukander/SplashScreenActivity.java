package com.mrsoftit.dukander;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#11CFC5"))
                .withAfterLogoText("দোকানদার অ্যাপ্লিকেশনে স্বাগতম")
                .withLogo(R.mipmap.ic_launcher_foreground);


        View easySplashScreen = config.create();
        setContentView(easySplashScreen);



    }
}
