package com.asjgqp.pjnfrujuet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class gameview extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying,isGameover=false;
    private int screenX,screenY,score=0;
    public static float ScreenRatioX ,ScreenRatioY;
    private Paint paint;
    //private Paint textpaint;
    private SharedPreferences prefs;
    private Bot[] bots;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private Flight flight;
    private Game activity;
    private Background back1,back2;

    public gameview(Game activity, int screenX, int screenY) {
        super(activity);

        this.activity=activity;

        prefs = activity.getSharedPreferences("game",Context.MODE_PRIVATE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            AudioAttributes audioAttributes = new  AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        }else
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        sound=soundPool.load(activity,R.raw.laser,1);

        this.screenX=screenX;
        this.screenY=screenY;

        ScreenRatioX=1970f/screenX;
        ScreenRatioY=1080f/screenY;

        back1 = new Background(screenX,screenY,getResources());
        back2 = new Background(screenX,screenY,getResources());

        flight=new Flight(this, screenY, getResources());

        bullets=new ArrayList<>();



        back2.x =screenX;

        paint=new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
        //textpaint.setColor(Color.YELLOW);
        //textpaint.setTextSize(50);

        bots = new Bot[4];
        for(int i =0;i<4;i++){
            Bot bot = new Bot(getResources());
            bots[i]=bot;
        }
        random=new Random();
    }

    @Override
    public void run() {

        while(isPlaying) {



            update();
            draw();
            sleep();
        }

    }

    private void update() {

        back1.x -= 20*ScreenRatioX;
        back2.x -= 20*ScreenRatioX;

        if(back1.x + back1.background.getWidth()<0){

            back1.x=screenX;

        }
        if(back2.x + back2.background.getWidth()<0){

            back2.x=screenX;

        }
        if(flight.isGoingUp) flight.y-=40*ScreenRatioY;
        else   flight.y+=20*ScreenRatioY;

        if(flight.y<0) flight.y=0;

        if(flight.y>screenY-flight.height) flight.y=screenY-flight.height;

        List<Bullet> trash=new ArrayList<>();

        for(Bullet bullet : bullets){
            if(bullet.x >screenX)
                trash.add(bullet);

            bullet.x+=50*ScreenRatioX;
            for(Bot bot:bots){
                if(Rect.intersects(bot.getCollisionShape(),bullet.getCollisionShape())){
                    score++;
                    bot.x=-500;
                    bullet.x=screenX+500;
                    bot.wasShot=true;
                }
            }
        }

        for(Bullet bullet:trash){
            bullets.remove(bullet);
        }
        for(Bot bot:bots){

            bot.x-=bot.speed;
            if(bot.x+bot.width<0){

                if(!bot.wasShot){
                    isGameover=true;
                    return;
                }

                int bound = (int) (30*ScreenRatioX);
                bot.speed=random.nextInt(bound);

                if(bot.speed<10*ScreenRatioX){
                    bot.speed= (int) (20*ScreenRatioX);

                bot.x=screenX;
                bot.y=random.nextInt(screenY-bot.height);
                bot.wasShot=false;
                }


            }

            if(Rect.intersects(bot.getCollisionShape(),flight.getCollisionShape())){
                isGameover=true;
                return;
            }
        }


    }
    private void draw() {


        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(back1.background,back1.x,back1.y,paint);
            canvas.drawBitmap(back2.background,back2.x,back2.y,paint);

            for(Bot bot:bots){
                canvas.drawBitmap(bot.getBot(),bot.x,bot.y,paint);

            canvas.drawText(score + "",screenX/2,164,paint );

            if(isGameover){
                isPlaying=false;
                canvas.drawBitmap(flight.getdead(),flight.x,flight.y,paint);
                //canvas.drawText("Game Over",screenX/4,screenY/3, paint);
                //canvas.drawText("Your Score "+score,screenX/4,screenY/2, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighscore();
                waitbeforeexiting();

                return;
            }


            }

            canvas.drawBitmap(flight.getflight(),flight.x,flight.y,paint);

            for(Bullet bullet : bullets){
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);



        }

    }

    private void waitbeforeexiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighscore() {
        if(prefs.getInt("highscore",0)<score){
            SharedPreferences.Editor editor =prefs.edit();
            editor.putInt("highscore",score);
            editor.apply();
        }
    }

    private void sleep() {

        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void resumes(){

        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<screenX/2 ){
                    flight.isGoingUp = true;
                }
                break;


            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if(event.getX() >=screenX/2)
                    flight.toShoot++;
                break;

        }

        return true;
    }

    public void newBullet() {
        if(!prefs.getBoolean("isMute",false))
            soundPool.play(sound,1,1,0,0,1);


        Bullet bullet = new Bullet(getResources());
        bullet.x=flight.x+flight.width;
        bullet.y=flight.y+(flight.height/2);
        bullets.add(bullet);
    }
}
    

    

