package com.asjgqp.pjnfrujuet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.asjgqp.pjnfrujuet.gameview.ScreenRatioX;
import static com.asjgqp.pjnfrujuet.gameview.ScreenRatioY;

public class Bot {
    public int speed=20;
    public boolean wasShot=true;
    int x=0,y,width,height,botcounter=1;
    Bitmap bot ;
    Bot (Resources res){
        bot= BitmapFactory.decodeResource(res,R.drawable.bot2);

        width=bot.getWidth();
        height=bot.getHeight();

        width/=5;
        height/=5;

        width= (int) (width*ScreenRatioX);
        height= (int) (height*ScreenRatioY);

        bot = Bitmap.createScaledBitmap(bot,width,height,false);
        y= -height;

    }
    Bitmap getBot(){
        if(botcounter==1){
            botcounter++;
            return bot;
        }
        return bot;
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
