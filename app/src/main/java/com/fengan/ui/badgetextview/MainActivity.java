package com.fengan.ui.badgetextview;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ui.fengan.com.badgetextview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BadgeTitleView textview = findViewById(R.id.badgeTitleView);
        textview.setTitle(getResources().getString(R.string.test_content));
    }
}
