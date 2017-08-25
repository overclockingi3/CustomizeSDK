package com.overc_i3.mars.customizesdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.overc_i3.mars.util.SDCardUtils.isSDCardEnable;
import static com.overc_i3.mars.util.Tips.show;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show(this, String.valueOf(isSDCardEnable()), 1);
    }
}
