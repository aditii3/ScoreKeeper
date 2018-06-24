package com.example.android.scorekeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
     EditText nameTeam1,nameTeam2,gameTime;
     Button startGame;
     String name1,name2;
     long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTeam1=findViewById(R.id.name_team_1);
        nameTeam2=findViewById(R.id.name_team_2);
        gameTime=findViewById(R.id.game_time);
        startGame=findViewById(R.id.start_game);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=nameTeam1.getText().toString();
                name2=nameTeam2.getText().toString();
                if(gameTime.getText().toString().matches("")||name1.matches("")||name2.matches("")){
                    Toast.makeText(MainActivity.this,"Please enter valid details",Toast.LENGTH_SHORT).show();

                }else {
                    time = Integer.parseInt(gameTime.getText().toString());
                    Intent i = new Intent(MainActivity.this, GameActivity.class);
                    Bundle b = new Bundle();
                    b.putString("NAME_TEAM_1", name1);
                    b.putString("NAME_TEAM_2", name2);
                    b.putLong("TIME", time);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });

    }

}
