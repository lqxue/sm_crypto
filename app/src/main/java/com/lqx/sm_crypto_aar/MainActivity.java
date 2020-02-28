package com.lqx.sm_crypto_aar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import sm_crypto.Sm_crypto;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv_content);
        String s = Sm_crypto.c_GenerateKey();
        Log.e("haha",s);
    }
}
