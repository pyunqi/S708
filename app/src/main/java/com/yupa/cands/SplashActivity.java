package com.yupa.cands;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.yupa.cands.utils.ShowMessage;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RelativeLayout rlFull = findViewById(R.id.rlFull);
        rlFull.setBackground(getResources().getDrawable(R.drawable.back, null));
        SplashThread st = new SplashThread();
        st.start();
    }

    public class SplashThread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Start the MainActivity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    // Close this activity
                }
            });

        }
    }
}
