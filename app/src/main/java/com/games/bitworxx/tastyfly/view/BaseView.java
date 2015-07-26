package com.games.bitworxx.tastyfly.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.TXT;

/**
 * Created by WEIS on 20.07.2015.
 */
public abstract class BaseView extends SurfaceView implements SurfaceHolder.Callback {


    protected GameThread MyGame=null;
    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        register();

        getHolder().addCallback(this);


    }

    public abstract void drawSurface(Canvas canvas);


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        MyGame=new GameThread(this);
        MyGame.start();

    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        MyGame.stopThis();
    }

    public abstract void action();

    protected abstract void register();
    protected Activity getContextA()
    {
        return (Activity) getContext();
    }


    protected SharedPreferences getPref()
    {
        return Views.MAIN_VIEW.getContextA().getSharedPreferences(TXT.KEY_GLOBAL, Context.MODE_PRIVATE);
    }
    protected Rect getBounds()
    {
        return new Rect(0,0,getWidth(),getHeight());
    }


    public int readBest()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_BEST+String.valueOf( GameConst.MyChar.getCode()), -1);
        return best==-1?0:best;
    }


    public void saveBest(int best)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_BEST+String.valueOf( GameConst.MyChar.getCode()), best);
        edit.commit();
    }

    public int readHigh()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_HIGH, -1);
        return best==-1?0:best;
    }


    public void saveHigh(int best)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_HIGH, best);
        edit.commit();
    }

    public int loadChar()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_CHAR, -1);
        return best==-1?1:best;
    }


    public void saveChar(int best)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_CHAR, best);
        edit.commit();
    }


    public int readBest(int code)
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_BEST+String.valueOf( code), -1);
        return best==-1?0:best;
    }
}
