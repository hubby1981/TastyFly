package com.games.bitworxx.tastyfly;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.games.bitworxx.tastyfly.view.GameView;
import com.games.bitworxx.tastyfly.view.Views;


public class GameActivity extends Activity {
    public  static Runnable Update;

public Runnable Run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Update=new Runnable() {
            @Override
            public void run() {
                update();
            }
        };
        setContentView(R.layout.activity_game);


    }

    @Override
    public void onBackPressed() {
        System.gc();
        ((GameView) Views.GAME_VIEW).finishEx();
        return;
    }


    private void update()
    {
        if(Run == null) {
            Run = new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.view).invalidate();

                }
            };
        }
        runOnUiThread(Run);
    }

}
