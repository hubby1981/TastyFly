package com.games.bitworxx.tastyfly;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.TXT;
import com.games.bitworxx.tastyfly.view.GameView;
import com.games.bitworxx.tastyfly.view.Views;

import java.io.IOException;


public class MainActivity extends Activity {

    public  static Runnable Update;

public static Runnable Start;
    public GameConst GM;

    public static MediaPlayer MP=new MediaPlayer();
    public static MediaPlayer MP_UP=new MediaPlayer();
    public static MediaPlayer MP_PONG = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GM=new GameConst();


        new Thread(new Runnable() {
            @Override
            public void run() {
                doMusic();
            }
        }).start();
        Start=new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        };

        getWindowManager().getDefaultDisplay().getMetrics(GM.Metrics);
        GM.FONT.setTypeface(Typeface.createFromAsset(getAssets(), "venus.ttf"));
        Update=new Runnable() {
            @Override
            public void run() {
                update();
            }
        };

        setContentView(R.layout.activity_main);
        runOnUiThread(Update);

    }

    @Override
    public void onBackPressed() {

        MP = null;
        MP_PONG=null;
        MP_UP=null;


        System.exit(0);
        return;
    }

    private void update()
    {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.view).invalidate();
            }
        });
    }


    public void doMusic()
    {
        AssetFileDescriptor descriptor = null;
        try {
            descriptor = getAssets().openFd("intro.MP3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MP.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            descriptor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MP.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(MP!=null) {
            MP.setVolume(0f,0f);
            MP.setLooping(true);
            MP.start();
        }
         descriptor = null;
        try {
            descriptor = getAssets().openFd("up.MP3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            MP_UP.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            descriptor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MP_UP.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(MP_UP!=null)
            MP_UP.setVolume(1f,1f);


        descriptor = null;
        try {
            descriptor = getAssets().openFd("pong.MP3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MP_PONG.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            descriptor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MP_PONG.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(MP_PONG!=null)
        MP_PONG.setVolume(1f,1f);


    }

    public  void startGame()
    {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }


}
