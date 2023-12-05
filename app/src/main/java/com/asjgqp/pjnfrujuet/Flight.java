package com.asjgqp.pjnfrujuet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.asjgqp.pjnfrujuet.gameview.ScreenRatioX;
import static com.asjgqp.pjnfrujuet.gameview.ScreenRatioY;

public class Flight {
    public boolean isGoingUp=false;
    public int toShoot=0;
    int x,y,width,height,wingcounter=0,shootcounter=0;
    Bitmap flight1,flight2,shoot1,shoot2,shoot3,shoot4,shoot5,dead;
    private com.asjgqp.pjnfrujuet.gameview gameview;

    Flight(com.asjgqp.pjnfrujuet.gameview gameview, int screenY, Resources res){
        this.gameview=gameview;

        flight1= BitmapFactory.decodeResource(res,R.drawable.fly1);
        flight2= BitmapFactory.decodeResource(res,R.drawable.fly2);
        width=flight1.getWidth();
        height=flight1.getHeight();

        width /=4;
        height /=4;

        width = (int) (width*ScreenRatioX);
        height = (int) (height* ScreenRatioY);

        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        shoot1=BitmapFactory.decodeResource(res,R.drawable.shoot1);
        shoot2=BitmapFactory.decodeResource(res,R.drawable.shoot2);
        shoot3=BitmapFactory.decodeResource(res,R.drawable.shoot3);
        shoot4=BitmapFactory.decodeResource(res,R.drawable.shoot4);
        shoot5=BitmapFactory.decodeResource(res,R.drawable.shoot5);

        shoot1=Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2=Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3=Bitmap.createScaledBitmap(shoot3,width,height,false);
        shoot4=Bitmap.createScaledBitmap(shoot4,width,height,false);
        shoot5=Bitmap.createScaledBitmap(shoot5,width,height,false);

        dead =BitmapFactory.decodeResource(res,R.drawable.dead1);
        dead=Bitmap.createScaledBitmap(dead,width,height,false);

        y = screenY/2;
        x = (int)(64*ScreenRatioX);

    }

    Bitmap getflight(){

        if(toShoot!=0){
            if(shootcounter==1){
                shootcounter++;
                return shoot1;
            }
            if(shootcounter==2){
                shootcounter++;
                return shoot2;
            }
            if(shootcounter==3){
                shootcounter++;
                return shoot3;
            }
            if(shootcounter==4){
                shootcounter++;
                return shoot4;
            }

            shootcounter=1;
            toShoot--;
            gameview.newBullet();

            return shoot5;

        }

        if(wingcounter==0){
            wingcounter++  ;
            return flight1;
        }
        wingcounter--;

        return flight2;

    }

    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getdead(){
        return dead;
    }

}

