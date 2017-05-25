package com.cdemo.customview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CustomRoundProgressBar customRoundProgressBar;
    private int maxProgress = 100;
    int currentProgress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customRoundProgressBar = (CustomRoundProgressBar) findViewById(R.id.customprogressbar);
        customRoundProgressBar.setMaxProgress(maxProgress);
        mHandler.sendEmptyMessage(0x11);
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x11:
                    currentProgress++;
                    customRoundProgressBar.setCurrentProgress(currentProgress);
                    sendEmptyMessageDelayed(0x11, 200);
                    if (currentProgress >= maxProgress) {
                        currentProgress = maxProgress;
                        removeMessages(0x11);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
