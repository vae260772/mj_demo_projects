package com.asjgqp.pjnfrujuet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class Game extends AppCompatActivity {

    private gameview gameview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        Point point =new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameview = new gameview(this,point.x,point.y);

        setContentView(gameview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameview.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameview.resumes();
    }


}
