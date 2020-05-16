package com.planjut.mqttbroker.view_start;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.planjut.mqttbroker.LoginActivity;
import com.planjut.mqttbroker.R;

public class SplashActivity extends AppCompatActivity{

    private static int SPLASH_TIME_OUT = 4000;
    ProgressBar progressBar;
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgressDrawable(progressDrawable);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }

        }, SPLASH_TIME_OUT);

//        progressBar = findViewById(R.id.proBar);
//        textView = findViewById(R.id.txtV);

//        progressBar.setMax(100);
//        progressBar.setScaleY(3f);
//
//        progressAnimation();
    }

//    public void progressAnimation(){
//        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
//        anim.setDuration(4000);
//        progressBar.setAnimation(anim);
//    }

}

