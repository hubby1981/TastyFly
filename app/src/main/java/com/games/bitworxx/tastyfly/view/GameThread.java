package com.games.bitworxx.tastyfly.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import com.games.bitworxx.tastyfly.backgrounds.ColorTapet;
import com.games.bitworxx.tastyfly.helper.GameConst;

/**
 * Created by WEIS on 23.07.2015.
 */
public class GameThread extends Thread {

    private SurfaceHolder MyHolder;
    private BaseView MyView;
    private boolean Running=false;
    private Paint Back = new Paint();
    private long frame1=0;
    private long frame2=0;
    private long frame11=0;
    private long frame22=0;
    private Bitmap BackBit=null;
    public boolean SignalToUseBit=false;
    private Thread BackT=null;
    public GameThread(BaseView view)
    {
        super();
        MyHolder=view.getHolder();
        MyView=view;
        Running=true;
        this.setPriority(MAX_PRIORITY);
        Back.setStyle(Paint.Style.FILL);
        Back.setColor(Color.WHITE);
    }

    @Override
    public void start()
    {
        Running=true;
        super.start();

    }

    public void stopThis()
    {
        Running=false;
    }

    private void generateCanvasToBit(Rect bounds)
    {
        Bitmap back = Bitmap.createBitmap(bounds.width(),bounds.height(), Bitmap.Config.ARGB_4444);

        Canvas c = new Canvas(back);
        MyView.drawSurface(c);

        BackBit=null;
        BackBit=back;
    }

    public  void run()
    {
        Canvas c=null;
        while(Running)
        {
            c = null;
            try
            {
                c = MyHolder.lockCanvas();
                synchronized (MyHolder)
                {
                    if (c != null)
                    {
                        frame11=SystemClock.elapsedRealtime();
                        c.drawRect(c.getClipBounds(), Back);
                        MyView.action();

                         if(BackBit==null)
                         {
                             generateCanvasToBit(c.getClipBounds());
                         }
                            if(BackT==null)
                            {
                                BackT=new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        generateCanvasToBit(MyView.getBounds());
                                        BackT=null;
                                    }
                                });
                                BackT.setPriority(MAX_PRIORITY);
                                BackT.start();
                            }

                        c.drawBitmap(BackBit,0,0,null);

                        frame22= SystemClock.elapsedRealtime();
/*
                        if(frame2>0) {
                            Paint f = new Paint();
                                    f.setTextSize(30);
                            f.setColor(Color.WHITE);
                            f.setStyle(Paint.Style.FILL);
                            c.drawText(String.valueOf(frame2 - frame1)+ " / "+String.valueOf(frame22-frame11), 30, 30, f);
                        }*/
                    }
                }
                try {
                    frame1=SystemClock.elapsedRealtime();

                    sleep(20*GameConst.FRAME_RATE);
                    frame2= SystemClock.elapsedRealtime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            catch(Exception ie)
            {
            }
            finally
            {
                // do this in a finally so that if an exception is thrown
                // we don't leave the Surface in an inconsistent state
                if (c != null)
                {
                    MyHolder.unlockCanvasAndPost(c);
                }
            }


        }
    }
}
