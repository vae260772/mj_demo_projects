package com.asjgqp.pjnfrujuet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.asjgqp.pjnfrujuet.gameview.ScreenRatioX;
import static com.asjgqp.pjnfrujuet.gameview.ScreenRatioY;

public class Bullet {
    int x,y,width,hight;
    Bitmap bullet;

    Bullet(Resources res){
        bullet= BitmapFactory.decodeResource(res,R.drawable.bullet1);

        width=bullet.getWidth();
        hight=bullet.getHeight();

        width/=4;
        hight/=4;

        width=  (int) (width*ScreenRatioX);
        hight=(int) (hight*ScreenRatioY);

        bullet=Bitmap.createScaledBitmap(bullet,width,hight,false);
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+hight);
    }
}
