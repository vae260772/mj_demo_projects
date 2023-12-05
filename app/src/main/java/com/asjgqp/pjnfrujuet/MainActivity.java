package com.asjgqp.pjnfrujuet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(MainActivity.this,Game.class));


            }
        });
        TextView highscoretext = findViewById(R.id.high_score);

        final SharedPreferences prefs = getSharedPreferences("game",MODE_PRIVATE);
        highscoretext.setText("highscore:"+prefs.getInt("highscore",0));

        isMute = prefs.getBoolean("isMute",false);

        final ImageView volumectrl = findViewById(R.id.mute);

        if(isMute){
            volumectrl.setImageResource(R.drawable.ic_volume_off_black_24dp);

        }else
            volumectrl.setImageResource(R.drawable.ic_volume_up_black_24dp);

        volumectrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isMute = !isMute;
                if(isMute){
                    volumectrl.setImageResource(R.drawable.ic_volume_off_black_24dp);

                }else
                    volumectrl.setImageResource(R.drawable.ic_volume_up_black_24dp);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute",isMute);
                editor.apply();

            }
        });
    }
}

