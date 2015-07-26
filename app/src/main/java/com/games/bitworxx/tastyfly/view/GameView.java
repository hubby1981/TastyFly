package com.games.bitworxx.tastyfly.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.games.bitworxx.tastyfly.GameActivity;
import com.games.bitworxx.tastyfly.MainActivity;
import com.games.bitworxx.tastyfly.R;
import com.games.bitworxx.tastyfly.backgrounds.ColorTapet;
import com.games.bitworxx.tastyfly.backgrounds.levels.Level;
import com.games.bitworxx.tastyfly.backgrounds.levels.Level1;
import com.games.bitworxx.tastyfly.backgrounds.levels.Level2;
import com.games.bitworxx.tastyfly.backgrounds.levels.Level3;
import com.games.bitworxx.tastyfly.backgrounds.levels.Level4;
import com.games.bitworxx.tastyfly.backgrounds.levels.Level5;
import com.games.bitworxx.tastyfly.characters.BaseCharacter;
import com.games.bitworxx.tastyfly.characters.Locust;
import com.games.bitworxx.tastyfly.characters.Spider;
import com.games.bitworxx.tastyfly.helper.FontRectPainter;
import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;
import com.games.bitworxx.tastyfly.helper.TriangleHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WEIS on 17.07.2015.
 */
public class GameView extends BaseView {


    public static float FACTOR=(float)8.0;


    private Timer START=null;
    private boolean Alt=false;
    Level Story0 =null;
    Level Story1 =null;
    Level Story2 =null;

    RectF ClickLeft;
    RectF ClickRight;
    RectF ClickMiddle;
    public long TimeStart=0l;
    public long TimeEnd=0l;
    Rect UP = null;
    Rect DOWN=null ;
    BaseCharacter ThisChar=GameConst.MyChar.getCopy();
    Paint Mace=null;
    public boolean GAME_OVER=false;
    public int FLYX=0;
    public int FLYY=0;
    public int GAME_COUNT=0;
    public int LAST_GAME_COUNT=0;
    public boolean Collison=true;
    public int POWER=50;
    public long MAX_COUNT=GameConst.PEROID;
    public int SECONDS=4;
Canvas OLD;
    private Rect Retry;
    private Rect Quit;

    private long LAST_1=0;
    private long LAST_2=0;
    public String DEAD_BY="";
    public boolean IS_START=false;
    Rect Tapet1;
    Rect Tapet2;
public Thread Invalid=null;
    ArrayList<Point> Points=new ArrayList<>();
private int StartCount=125;
private Runnable Check=new Runnable() {
    @Override
    public void run() {
        checkLevel();
    }
};
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

ColorTapet.setRandom();
start();
    }

    @Override
    public void drawSurface(Canvas canvas) {
        drawIt(canvas);
    }

    @Override
    public void action() {
        if(StartCount>0)
        {
            Collison=false;
            StartCount--;
        }

        if(StartCount==0)
        {
            Collison=true;
        }
        if(!GAME_OVER &&!IS_START)
            move(FACTOR*GameConst.FRAME_RATE);
    }

    private void start()
    {
        GAME_OVER=false;
        START=new Timer();
        START.schedule(new TimerTask() {
            @Override
            public void run() {

                IS_START = true;
                SECONDS--;
                ColorTapet.setRandom();
                if (SECONDS == 0) {

                    IS_START = false;
                    GAME_OVER = false;
                    SECONDS = 4;


                    initGame();
                }
                }

        }, 0, 1000);
    }



    @Override
    protected void register() {
        Views.GAME_VIEW=this;
    }

    private String getTime()
    {
        long time = TimeEnd-TimeStart;
        time/=1000;
        String result="00:00";

        if(time>60)
        {
            long mm= time%60;
            long m = (time-mm)/60;
            if(m<10&&mm<10)
            {
                result="0"+String.valueOf(m)+":0"+String.valueOf(mm);
            }
            else if(m<10&&mm>10)
            {

                    result="0"+String.valueOf(m)+":"+String.valueOf(mm);

            }
            else
            {
               if(mm<10)
               {
                   result=String.valueOf(m)+":0"+String.valueOf(mm);
               }
                else
               {
                   result=String.valueOf(m)+":"+String.valueOf(mm);

               }
            }
        }
        else
        {
            if(time<10)
            {
                result="00:0"+String.valueOf(time);
            }
            else
            {
                result="00:"+String.valueOf(time);
            }
        }
        return result;
    }

    private void drawBorders(Canvas canvas)
    {
        Mace = new Paint();
        Mace.setStyle(Paint.Style.FILL);

        Rect bounds = getBounds();



        Mace.setColor(Color.argb(255,50,50,50));

        int seed=6;
        UP =new Rect(bounds.left,bounds.top, bounds.right,bounds.top+bounds.height()/seed);
        seed+=1;
        DOWN =new Rect(bounds.left,bounds.bottom-bounds.height()/seed, bounds.right,bounds.bottom);


        canvas.drawRect(UP, Mace);
        canvas.drawRect(DOWN, Mace);

        GameConst.FONT.setTextSize(getBounds().height() / 10);
        String text = String.valueOf(GAME_COUNT);
        int ww = (int)GameConst.FONT.getTextSize()*text.length();
        ww/=2;

        if(!GAME_OVER)
            canvas.drawText(text, UP.centerX()-ww, 10 + GameConst.FONT.getTextSize(), GameConst.FONT);

        float w = getHeight() / GameConst.SIZE;


        float max=(float)1.6;

        ClickLeft = new RectF(getLeft() + w / max, (getBottom()+w/2) - w * max, getLeft() + w * max, (getBottom()+w/2) - w / max);
        ClickRight = new RectF(getRight() - w * max,(getBottom()+w/2) - w * max, getRight() - w / max, (getBottom()+w/2) - w / max);


        float left1 = DOWN.centerX()-w;
        ClickMiddle = new RectF(left1 + w / max, (getBottom()+w/2) - w * max, left1 + w * max, (getBottom()+w/2) - w / max);

        Bitmap iconChar =BitmapFactory.decodeResource(getResources(), R.drawable.bolt);



        Paint circle = new Paint();
        circle.setColor(Color.argb(200, 150, 150, 150));
        circle.setStyle(Paint.Style.FILL);


        Paint power = new Paint();
        power.setColor(Color.argb(225, 200, 170, 0));
        power.setStyle(Paint.Style.FILL_AND_STROKE);

        power.setAntiAlias(true);
        power.setStrokeWidth(bounds.width() / 80);

        if(!GAME_OVER) {
            if (!IS_START)
                canvas.drawArc(ClickMiddle, 0, POWER, true, power);
            canvas.drawArc(ClickMiddle, 0, 360, true, circle);

            if (FLYX == 1)
                canvas.drawArc(ClickLeft, 0, 360, true, power);
            else
                canvas.drawArc(ClickLeft, 0, 360, true, circle);
            if (FLYY == 1)
                canvas.drawArc(ClickRight, 0, 360, true, power);
            else
                canvas.drawArc(ClickRight, 0, 360, true, circle);


            RectF middle2 = new RectF(ClickMiddle.left + ClickMiddle.width() / 6, ClickMiddle.top + ClickMiddle.height() / 6, ClickMiddle.right - ClickMiddle.width() / 6, ClickMiddle.bottom - ClickMiddle.height() / 6);
            canvas.drawBitmap(iconChar, null, middle2, null);

            Path p = TriangleHelper.getTriangleLeft(ClickLeft);
            canvas.drawPath(p, Mace);
            p = TriangleHelper.getTriangleDown(ClickRight);
            canvas.drawPath(p, Mace);
        }
    }

    public void drawOver(Canvas canvas)
    {
        Paint back1 = new Paint();

        back1.setColor(Color.argb(75, 50, 50, 50));
        back1.setStyle(Paint.Style.FILL);
        //canvas.drawRect(getBounds(), back1);
        int alpha=200;
        Paint back3 = new Paint();

        back3.setColor(Color.argb(alpha, 150, 150, 150));
        back3.setStyle(Paint.Style.FILL);

        Paint back2 = new Paint();

        back2.setColor(Color.argb(alpha, 100, 100, 100));
        back2.setStyle(Paint.Style.FILL);

        Paint back5 = new Paint();

        back5.setColor(Color.argb(alpha,150,150,150));
        back5.setStyle(Paint.Style.FILL);

        Paint back4 = new Paint();

        back4.setColor(Color.argb(alpha,100,100,100));
        back4.setStyle(Paint.Style.FILL);


        Paint back6 = new Paint();

        back6.setColor(Color.argb(255,50,150,150));
        back6.setStyle(Paint.Style.FILL);


        Paint back21 = new Paint();

        back21.setColor(Color.argb(255, 150, 50, 50));
        back21.setStyle(Paint.Style.FILL);

        Paint back31 = new Paint();

        back31.setColor(Color.argb(255, 50, 150, 50));
        back31.setStyle(Paint.Style.FILL);

        ArrayList<Rect> main =  RectHandler.getGrid(8, 1, getBounds());

        String title="Game Over";
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, title, main.get(0), 0);


        canvas.drawRect(main.get(2), back2);
        canvas.drawRect(main.get(3), back3);
        canvas.drawRect(main.get(4), back4);


        canvas.drawRect(main.get(5),back5);

        canvas.drawRect(main.get(1),back6);

        ArrayList<Rect> rects2 = RectHandler.getGrid(1,8,main.get(2));
        ArrayList<Rect> rects3 = RectHandler.getGrid(1,8,main.get(3));
        ArrayList<Rect> rects4 = RectHandler.getGrid(1, 8, main.get(6));
        ArrayList<Rect> rects5 = RectHandler.getGrid(1, 8, main.get(4));
        ArrayList<Rect> rects6 = RectHandler.getGrid(1, 8, main.get(5));


        Rect score = RectHandler.combineRects(rects2.get(0), rects2.get(3));
        Rect best = RectHandler.combineRects(rects3.get(0), rects3.get(3));
        Rect score2 = RectHandler.combineRects(rects2.get(6), rects2.get(6));
        Rect best2 = RectHandler.combineRects(rects3.get(6),rects3.get(6));
        Retry = RectHandler.combineRects(rects4.get(0),rects4.get(3));
        Quit = RectHandler.combineRects(rects4.get(4),rects4.get(7));
        Rect high = RectHandler.combineRects(rects5.get(0), rects5.get(3));
        Rect high2 = RectHandler.combineRects(rects5.get(6), rects5.get(6));
        Rect time = RectHandler.combineRects(rects6.get(0), rects6.get(3));
        Rect time2 = RectHandler.combineRects(rects6.get(5), rects6.get(7));

        String scoreValue = String.valueOf(GAME_COUNT)+" ";
        String bestValue = String.valueOf(readBest())+" ";
        String highValue = String.valueOf(readHigh())+" ";

        String timeValue =getTime()+" ";


        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Game:", score, 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Best:", best, 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "high:", high, 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Time:", time, 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, DEAD_BY, main.get(1), 0);
        FontRectPainter.drawtextOnCanvas(GameConst.FONT,canvas,scoreValue,score2,0);
        FontRectPainter.drawtextOnCanvas(GameConst.FONT, canvas, bestValue, best2, 0);
        FontRectPainter.drawtextOnCanvas(GameConst.FONT, canvas, highValue, high2, 0);
        FontRectPainter.drawtextOnCanvas(GameConst.FONT, canvas, timeValue, time2, 0);

        canvas.drawRect(Retry, back31);
        canvas.drawRect(Quit,back21);

        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Retry", Retry, 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Close", Quit, 0);

    }


    public void onDraw(Canvas canvas)
    {

drawIt(canvas);
    }

    protected void drawIt(Canvas canvas)
    {

        Mace = new Paint();
        Mace.setStyle(Paint.Style.FILL);
        Mace.setColor(GameConst.MACE_COLOR);


        if(Story0!=null&&!GAME_OVER)
        {
            canvas.drawBitmap(Story0.getDraw(), Story0.POS, 0, null);

            if(Story0.Spiders!=null)
            {

                if(!IS_START)
                    for(Spider s:Story0.Spiders)
                    {

                        s.onDraw(canvas);
                    }


            }


        }
        if(Story1!=null)
        {

            canvas.drawBitmap(Story1.getDraw(), Story1.POS, 0, null);
            if(Story1.Spiders!=null)
            {

                if(!IS_START)
                    for(Spider s:Story1.Spiders)
                    {

                        s.onDraw(canvas);
                    }
            }
        }
        if(Story2!=null)
        {

            canvas.drawBitmap(Story2.getDraw(), Story2.POS, 0, null);
            if(Story2.Spiders!=null)
            {

                if(!IS_START)
                    for(Spider s:Story2.Spiders)
                    {

                        s.onDraw(canvas);
                    }
            }
        }


        if(IS_START)
            ColorTapet.drawOnRect2(canvas, getBounds(), GameConst.SIZE, false, ColorTapet.RED, ColorTapet.GREEN, ColorTapet.BLUE);
        drawBorders(canvas);


        if(IS_START)
        {
            MainActivity.MP_UP.start();
            String seconds = String.valueOf(SECONDS);
            if(SECONDS==3)seconds="LOAD";
            if(SECONDS==2)seconds="READY";
            if(SECONDS==1)seconds="GO";
            int ll=0-(seconds.length()*(int)GameConst.FONT.getTextSize())/2;
            canvas.drawText(seconds,getBounds().centerX()+ll,getBounds().centerY(),GameConst.FONT);
        }
        else if(!GAME_OVER) {




            ThisChar.animateSink(FLYX, FLYY);
            if (ThisChar != null) {
                Point pp=new Point(ThisChar.X, ThisChar.Y);
                if(!Points.contains(pp))
                {
                    Points.add(pp);
                    if(Points.size()>10)
                        Points.remove(0);
                }
                drawPoints(canvas);
                ThisChar.onDraw(canvas);
            }



        }
        else
        {

            if (ThisChar != null) {
                ThisChar.IsDead=true;
                ThisChar.onDraw(canvas);
                ThisChar=null;
            }


        }


        if (ThisChar != null&&!IS_START) {
            ThisChar.onDraw(canvas);
        }

        if(GAME_OVER&&!IS_START)
        {
            drawOver(canvas);

        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawPoints(Canvas canvas)
    {
        if(!GAME_OVER&&!IS_START){


            if(Points.size()>0){

                Paint point = new Paint();
                point.setStyle(Paint.Style.STROKE);
                point.setStrokeWidth(GameConst.LINE_H);
                point.setColor(Color.argb(128, RandomRange.getRandom(10, 200), RandomRange.getRandom(10, 200), RandomRange.getRandom(10, 200)));
                Path pts = new Path();
                int index=0;
                for(Point pp :Points) {
                    if(index==0)
                        pts.moveTo(pp.x,pp.y);
                    else
                        pts.lineTo(pp.x,pp.y);
                  index++;
                }
                canvas.drawPath(pts, point);
                point=null;
            }

        }
    }

    private void checkCollision() {
        if(Collison) {
            if (Story0 != null) {
                if (Story0.checkLevelChar(ThisChar)) {
                    GAME_OVER = true;
                    ThisChar.IsDead=true;
                    MainActivity.MP_PONG.start();
                }
            }
            if (Story1 != null && !GAME_OVER) {
                if (Story1.checkLevelChar(ThisChar)) {
                    GAME_OVER = true;
                    ThisChar.IsDead=true;

                    MainActivity.MP_PONG.start();
                }
            }


            if(!GAME_OVER)
            {
                if(ThisChar.isInRect(UP))
                {
                    MainActivity.MP_PONG.start();
                    GAME_OVER=true;
                }
                if(!GAME_OVER&&ThisChar.isInRect(DOWN))
                {
                    MainActivity.MP_PONG.start();
                    GAME_OVER=true;
                }
            }
        }


        if(GAME_OVER)
        {
            TimeEnd= SystemClock.elapsedRealtime();
            int best = readBest();
            if(GAME_COUNT>best)
                saveBest(GAME_COUNT);
            if(GAME_COUNT>readHigh())
                saveHigh(GAME_COUNT);
            MainActivity.MP.setVolume(0f,0f);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(IS_START)
            return true;

        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(!GAME_OVER) {
                if (ClickLeft.contains(event.getX(), event.getY())) {

                    FLYX = 1;
                    FLYY = 0;            MainActivity.MP_UP.start();

                }else if (ClickRight.contains(event.getX(), event.getY())) {
                    FLYY = 1;
                    FLYX = 0;            MainActivity.MP_UP.start();

                }
            }
            else
            {
                if(Retry!=null)
                    if (Retry.contains((int)event.getX(),(int) event.getY())) {
                       finish(true);
                    }
                if(Quit!=null)
                    if (Quit.contains((int)event.getX(),(int) event.getY())) {

                       finish(false);
                    }
            }

        }

        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            FLYX=0;
            FLYY=0;


        }

        return true;
    }

    private void finish(boolean retry)
    {
        if(START!=null)
            START.cancel();

        if(Story0!=null)
            Story0.release();
        if(Story1!=null)
            Story1.release();
        if(Story2!=null)
            Story2.release();
        getContextA().finish();
        if(retry)
            MainActivity.Start.run();
        System.gc();
    }


    private Level getNextLevel(int pos)
    {
        if(!Collison)
            return new Level1(getBounds(),pos);
        int level = RandomRange.getRandom(1,5);

        if(level==2)
            return new Level2(getBounds(),pos);
        if(level==3)
            return new Level3(getBounds(),pos);
        if(level==4)
            return new Level4(getBounds(),pos);
        if(level==5)
            return new Level5(getBounds(),pos);
        return new Level1(getBounds(),pos);
    }

    public void finishEx()
    {
        finish(false);
    }

    private void checkLevel()
    {
       if(Story0!=null)
       {
           int  x = Story0.DisplayBounds.width()+Story0.POS;
           if(x<-100)
           {
               LAST_2=SystemClock.elapsedRealtime();
               int last = Story0.POS+Story0.DisplayBounds.width();
               Story0.release();
               ColorTapet.setRandom();

               GAME_COUNT++;
               LAST_GAME_COUNT++;
               if(Story1!=null)
               {
                   Story0=Story1;

               }
               else
               {
                   Story0=getNextLevel(last);
                   Story1 =getNextLevel(last+getWidth());
                   Story2 =getNextLevel(last+getWidth()*2);
               }

               if(Story2!=null)
               {
                   Story1=Story2;
                   Story2 = getNextLevel(Story1.POS+Story1.DisplayBounds.width());
               }
               else
               {
                   Story2 = getNextLevel(Story1.POS+Story1.DisplayBounds.width());
                   Story1=Story2;
                   Story2 = getNextLevel(Story1.POS+Story1.DisplayBounds.width());
               }


           }
       }

        if(LAST_GAME_COUNT>=MAX_COUNT)
        {
            FACTOR+=GameConst.PEROID_FACTOR;
            LAST_GAME_COUNT=0;
        }

    }

    private void doSpider(Level level,int size)
    {
        if(level.Spiders!=null)
        {
            for(Spider s : level.Spiders)
            {
                s.Net=new Rect(s.Net.left-size,s.Net.top,s.Net.right-size,s.Net.bottom);
                s.X-=size;
            }
        }
    }

    public void move(float factor)
    {

        IS_START = false;
        if(RandomRange.getRandom(1,5)==3)
            POWER+=2;
        checkCollision();

        int size=(int)(2*factor);

        if(!GAME_OVER){
            if(Story0!=null)
            {
                Story0.POS-=size;
              doSpider(Story0,size);
            }
            if(Story1!=null)
            {
                Story1.POS-=size;
                doSpider(Story1,size);
            }
            if(Story2!=null)
            {
                Story2.POS-=size;
                doSpider(Story2,size);
            }

        new Thread(Check).start();}
    }

    public void initGame()
    {
        StartCount=40;
        if(START!=null)
            START.cancel();
        Points.clear();
        LAST_1=SystemClock.elapsedRealtime();
        LAST_2=SystemClock.elapsedRealtime();
        if(START!=null)
            START.cancel();
        TimeStart= SystemClock.elapsedRealtime();
        ThisChar.IsDead=false;
        MainActivity.MP.setVolume(1f,1f);
        START=null;
        GAME_COUNT=0;
        LAST_GAME_COUNT=0;
        GAME_OVER=false;
        Story0=new Level1(getBounds(),0);
        Story1=getNextLevel(getBounds().width());
        Story2=getNextLevel(getBounds().width()*2);
        POWER=0;
        SECONDS=4;
        IS_START=false;
        ThisChar.X=250;
        ThisChar.Y=getHeight()/2;
        ThisChar.Size =getHeight()/ThisChar.Size;



    }







}
