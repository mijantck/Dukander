package com.mrsoftit.dukander;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.WHITE)
                .withFooterText("দোকানদার অ্যাপ্লিকেশনে স্বাগতম")
                .withLogo(R.mipmap.ic_launcher_foreground);

        if(mAuth.getCurrentUser()!=null){
             config.withTargetActivity(PinViewActivity.class);
        }else {
            config.withTargetActivity(GlobleProductListActivity.class);
        }
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);



    }
}
