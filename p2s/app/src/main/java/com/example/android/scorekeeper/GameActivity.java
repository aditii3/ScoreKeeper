package com.example.android.scorekeeper;

import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
//    declaring variables
    ImageView imgIdol1,imgIdol2;
    ImageView imgPlay2,imgPlay1;
    Button b1Goal,b2Goal,b1Touch,b2Touch,b1Extra,b2Extra,b1Safety,b2Safety,b1Foul,b2Foul,resetButton;
    CountDownTimer c,b;
    TextView timer;
    TextView teamA,teamB;
    String nameA,nameB;
    int score1,score2,foul1,foul2=0;
    int dwn1,dwn2,extra1,extra2,goal1,goal2=0;

    @Override
    protected void onPause() {
        super.onPause();
        c.cancel();
        b.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        getting extras from intent
        Bundle bundle = getIntent().getExtras();
        final long time = bundle.getLong("TIME");
        nameA=bundle.getString("NAME_TEAM_1");
        nameB=bundle.getString("NAME_TEAM_2");


//          setting resource id's
        imgIdol1 = findViewById(R.id.idol_1); imgIdol2=findViewById(R.id.idol_2); imgPlay2 = findViewById(R.id.play_2);imgPlay1=findViewById(R.id.play_1);
        b1Goal=findViewById(R.id.btn_goal_1);b2Goal=findViewById(R.id.btn_goal_2);b1Touch=findViewById(R.id.btn_touch_1);b2Touch=findViewById(R.id.btn_touch_2);b1Extra=findViewById(R.id.btn_extra_1);b2Extra=findViewById(R.id.btn_extra_2);b1Foul=findViewById(R.id.btn_foul_1);b2Foul=findViewById(R.id.btn_foul_2);b1Safety=findViewById(R.id.btn_safety_1);b2Safety=findViewById(R.id.btn_safety_2);
        resetButton=findViewById(R.id.reset_button);
        timer = (TextView)findViewById(R.id.timer);
        teamA=findViewById(R.id.score_team_1);teamB=findViewById(R.id.score_team_2);
        setTimer(time*60000);
        updateA();
        updateB();
        updates(time);

        //setting Listeners on buttons
        b1Goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerA();goal1+=1;score1+=3;updateA();
            }
        });
         b1Touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerA();dwn1+=1;score1+=6;updateA();
            }
        });
        b1Safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerA();score1+=2;updateA();
            }
        });
        b1Extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerA();extra1+=1;score1+=1;updateA();
            }
        });
        b1Foul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerA();foul1+=1;updateA();
            }
        });
        b2Goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerB();goal2+=1;score2+=3;updateB();

            }
        });
        b2Touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerB();dwn2+=1;score2+=6;updateB();

            }
        });
        b2Safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerB();score2+=2;updateB();

            }
        });
        b2Extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerB();extra2+=1;score2+=1;updateB();

            }
        });
        b2Foul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerB();foul2+=1;updateB();

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.cancel();b.cancel();
                setTimer(time*60000);
                score2=score1=foul2=foul1=extra1=extra2=dwn1=dwn2=0;
                updateB();updateA();
            }
        });

    }

    //updating score board
    private void updateA() {
        Resources r = getResources();teamA.setText(String.format(r.getString(R.string.update_score),nameA,score1,foul1,dwn1,extra1,goal1));}
    private void updateB(){ Resources r = getResources();teamB.setText(String.format(r.getString(R.string.update_score),nameB,score2,foul2,dwn2,extra2,goal2));}

    private void changePlayerA() {
         new CountDownTimer(600, 500) {

                    public void onTick(long millisUntilFinished) {
                        imgIdol1.setVisibility(View.INVISIBLE);
                        imgPlay1.setVisibility(View.VISIBLE);

                    }

                    public void onFinish() {
                        imgIdol1.setVisibility(View.VISIBLE);
                        imgPlay1.setVisibility(View.INVISIBLE);
                    }

                }.start();
    }
    private void changePlayerB(){
        new CountDownTimer(600, 500) {

            public void onTick(long millisUntilFinished) {
                imgIdol2.setVisibility(View.INVISIBLE);
                imgPlay2.setVisibility(View.VISIBLE);

            }

            public void onFinish() {
                imgIdol2.setVisibility(View.VISIBLE);
                imgPlay2.setVisibility(View.INVISIBLE);
            }

        }.start();
    }

    private void setTimer(long l) {
        c=new CountDownTimer(l, 1000) {

            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "Time Remaining %02d min: %02d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                timer.setText(text);

            }

            public void onFinish() {
                timer.setText("done!");
                b.cancel();
                Intent i = new Intent(GameActivity.this,ResultActivity.class);
                Bundle bundle = new Bundle();
                if(score1>score2){
                    bundle.putInt("WINNING_SCORE",score1);
                    bundle.putString("WINNING_TEAM",nameA);
                }
                else if(score2==score1){
                    bundle.putInt("WINNING_SCORE",score1);
                    bundle.putString("WINNING_TEAM","Match draw!!");
                }
                else{
                    bundle.putInt("WINNING_SCORE",score2);
                    bundle.putString("WINNING_TEAM",nameB);
                }
                i.putExtras(bundle);
                startActivity(i);

            }

        }.start();
    }
    private void updates(long time){
        b = new CountDownTimer(time*60000,15000){

            @Override
            public void onTick(long millisUntilFinished) {
                if(score1-score2==1){Toast.makeText(GameActivity.this,"Buck up "+nameB+" ONE MORE TO GO!!!",Toast.LENGTH_SHORT).show();}
                else if(score2-score1==1){Toast.makeText(GameActivity.this,"Buck up "+nameA+" ONE MORE TO GO!!!",Toast.LENGTH_SHORT).show();}
                else if(score1==score2){(Toast.makeText(GameActivity.this,"GAME IS ON!!",Toast.LENGTH_SHORT)).show();}
                else if(score1>score2){Toast.makeText(GameActivity.this,nameA+" ahead by "+(score1-score2),Toast.LENGTH_SHORT).show();}
                else{Toast.makeText(GameActivity.this,nameB+" ahead by "+(score2-score1),Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}