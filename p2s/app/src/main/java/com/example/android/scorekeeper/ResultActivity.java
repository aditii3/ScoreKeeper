package com.example.android.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();


        info=findViewById(R.id.info_text);

        info.setText("CONGRATS "+b.getString("WINNING_TEAM")+"\nSCORE "+b.getInt("WINNING_SCORE"));
    }
}
