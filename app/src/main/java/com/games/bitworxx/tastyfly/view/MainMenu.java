package com.games.bitworxx.tastyfly.view;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.games.bitworxx.tastyfly.GameActivity;
import com.games.bitworxx.tastyfly.MainActivity;
import com.games.bitworxx.tastyfly.backgrounds.ColorTapet;
import com.games.bitworxx.tastyfly.characters.BaseCharacter;
import com.games.bitworxx.tastyfly.characters.Bee;
import com.games.bitworxx.tastyfly.characters.Butter;
import com.games.bitworxx.tastyfly.characters.Fly;
import com.games.bitworxx.tastyfly.characters.Lady;
import com.games.bitworxx.tastyfly.characters.Locust;
import com.games.bitworxx.tastyfly.characters.Spider;
import com.games.bitworxx.tastyfly.characters.Spirit;
import com.games.bitworxx.tastyfly.helper.FontRectPainter;
import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.RectHandler;
import com.games.bitworxx.tastyfly.helper.TXT;
import com.games.bitworxx.tastyfly.helper.TriangleHelper;

import java.util.ArrayList;

/**
 * Created by WEIS on 22.07.2015.
 */
public class MainMenu extends BaseView {

    private Spider Spider1;

    private int TheChar;
    private int MaxChar=6;
    private Rect ClickPlay;
    private Rect ClickSelect1;
    private Rect ClickSelect2;
    private Rect ClickBuy;
    private Rect ClickRate;
    private Rect ClickHelp;

    private Rect ClickClose;

    public MainMenu(Context context, AttributeSet attrs) {
        super(context, attrs);     TheChar=   loadChar();        changeChar();

    }

    @Override
    public void drawSurface(Canvas canvas) {
        drawIt(canvas);
    }

    @Override
    public void action() {

    }

    @Override
    protected void register() {
        if(Views.MAIN_VIEW==null)
        {
            Views.MAIN_VIEW=this;
        }

    }

private void changeChar()
{
    if(TheChar==1)
        GameConst.MyChar=new Fly();
    if(TheChar==2)
        GameConst.MyChar=new Locust();
    if(TheChar==3)
        GameConst.MyChar=new Bee();
    if(TheChar==4)
        GameConst.MyChar=new Butter();
    if(TheChar==5)
        GameConst.MyChar=new Spirit();
    if(TheChar==6)
        GameConst.MyChar=new Lady();
    saveChar(TheChar);
}

   public void drawIt(Canvas canvas)
    {
        ColorTapet.drawOnRect2(canvas, getBounds(), GameConst.SIZE, false, 230, 200, 130);

        String title = "Tasty";
        String title2="Flyer";
        String best = "Best: "+String.valueOf(readBest());
        float oldSize= GameConst.FONT.getTextSize();
        GameConst.FONT.setColor(GameConst.MACE_COLOR);

        ArrayList<Rect> main =  RectHandler.getGrid(8, 1, getBounds());



        Rect net = new Rect(getCombined(main.get(3)).left,getCombined(main.get(1)).top,getCombined(main.get(3)).left+main.get(3).height()/3,getCombined(main.get(1)).top+main.get(3).height()/3);

        if(Spider1==null)
            Spider1=new Spider(getCombined(main.get(3)).left,getCombined(main.get(3)).top,net,true);

        Spider1.Size=main.get(3).height()/3;



        Paint paintButton = new Paint();
        paintButton.setColor(Color.argb(200, 180, 210, 180));

        paintButton.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint paintStrokeButton=new Paint();
        paintStrokeButton.setStyle(Paint.Style.STROKE);

        paintStrokeButton.setStrokeWidth(4);
        paintStrokeButton.setColor(GameConst.MACE_COLOR);

        ClickPlay = getCombinedSmall(main.get(3));

        canvas.drawRect(ClickPlay, paintButton);
        canvas.drawRect(ClickPlay, paintStrokeButton);



        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, title, main.get(0), 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT,canvas,title2, main.get(1),0);

        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, best, getCombined(main.get(2)), 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "SELECT Flyer", getCombined(main.get(4)), 0);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, GameConst.MyChar.Name, getCombined(main.get(5)), 1);
        ArrayList<Rect> select = RectHandler.getGrid(1,7,main.get(5));

        Rect charMini = RectHandler.combineRects(select.get(2),select.get(3));
        GameConst.MyChar.Size = charMini.height()/3;
        GameConst.MyChar.IsAnimated=false;
        GameConst.MyChar.X=(int)charMini.exactCenterX()+GameConst.MyChar.Size/2;
        GameConst.MyChar.Y=(int)charMini.exactCenterY()+GameConst.MyChar.Size/2;



        ClickSelect1=select.get(1);
        ClickSelect2=select.get(5);

        ClickSelect1=new Rect(ClickSelect1.left,ClickSelect1.top+ClickSelect1.height()/2,ClickSelect1.right,ClickSelect1.bottom);
        ClickSelect2=new Rect(ClickSelect2.left,ClickSelect2.top+ClickSelect2.height()/2,ClickSelect2.right,ClickSelect2.bottom);

        canvas.drawRect(ClickSelect1,paintButton);
        canvas.drawRect(ClickSelect1,paintStrokeButton);

        canvas.drawRect(ClickSelect2,paintButton);
        canvas.drawRect(ClickSelect2, paintStrokeButton);



        GameConst.MyChar.onDraw(canvas);
        if(GameConst.MyChar.isLocked())
        {
            ArrayList<Rect> chars = RectHandler.getGrid(3,1,RectHandler.combineRects(select.get(2),select.get(4)));
            Rect charLocked = RectHandler.combineRects(chars.get(0),chars.get(1));

            GameConst.FONT.setColor(GameConst.EYE_COLOR);

            Paint locked = new Paint();
            locked.setColor(Color.argb(200,200,0,0));
            locked.setStyle(Paint.Style.FILL);

            canvas.drawRect(charLocked, locked);
            canvas.drawRect(charLocked, paintStrokeButton);

            FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "LOCKED", charLocked, 0);
            GameConst.FONT.setColor(GameConst.MACE_COLOR);

            ClickBuy = getCombinedMiddle(main.get(6));

            canvas.drawRect(ClickBuy, paintButton);
            canvas.drawRect(ClickBuy, paintStrokeButton);
            FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Buy unlock", ClickBuy, 0);

        }

        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Play", ClickPlay, 0);
        Rect button = getCombinedMiddle(main.get(7));

        ArrayList<Rect> buttons = RectHandler.getGrid(1,2,button);
        ClickRate=buttons.get(0);
        ClickClose=buttons.get(1);
        int ww = ClickRate.width()/4;
        ClickRate.right-=ww;
        ClickClose.right+=ww;
        ClickClose.left+=ww;
        ClickRate.left-=ww;
        canvas.drawRect( ClickRate, paintButton);
        canvas.drawRect( ClickRate, paintStrokeButton);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Help", ClickRate, 0);
        canvas.drawRect( ClickClose, paintButton);
        canvas.drawRect( ClickClose, paintStrokeButton);
        FontRectPainter.drawtextOnCanvasCenter(GameConst.FONT, canvas, "Close", ClickClose, 0);


        GameConst.FONT.setColor(GameConst.EYE_COLOR);
        GameConst.FONT.setTextSize(oldSize);






        paintStrokeButton.setStyle(Paint.Style.FILL);
        canvas.drawPath(TriangleHelper.getTriangleLeft(RectHandler.getToRectF(ClickSelect1)), paintStrokeButton);
        canvas.drawPath(TriangleHelper.getTriangleRight(RectHandler.getToRectF(ClickSelect2)), paintStrokeButton);



        Spider1.onDraw(canvas);


    }

    private Rect getCombined(Rect old)
    {
        ArrayList<Rect> rects = RectHandler.getGrid(1,8,old);
        return RectHandler.combineRects(rects.get(1),rects.get(6));
    }
    private Rect getCombinedMiddle(Rect old)
    {
        ArrayList<Rect> rects = RectHandler.getGrid(1,8,old);
        Rect middle =  RectHandler.combineRects(rects.get(2),rects.get(5));

        rects = RectHandler.getGrid(3,1,middle);

        return RectHandler.combineRects(rects.get(0), rects.get(1));
    }
    private Rect getCombinedSmall(Rect old)
    {
        ArrayList<Rect> rects = RectHandler.getGrid(1,8,old);
        return RectHandler.combineRects(rects.get(3),rects.get(4));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(ClickPlay.contains((int)event.getX(),(int)event.getY()))
            {
                if(GameConst.MyChar.isLocked())
                {
                    TheChar=1;
                    changeChar();
                }
                MainActivity.Start.run();
            }
            if(ClickSelect1.contains((int)event.getX(),(int)event.getY()))
            {
                TheChar--;
                if(TheChar==0)TheChar=MaxChar;
                changeChar();
                invalidate();
            }
            if(ClickSelect2.contains((int)event.getX(),(int)event.getY()))
        {
            TheChar++;
            if(TheChar>MaxChar)TheChar=1;
            changeChar();
            invalidate();
        }

            if(ClickClose.contains((int)event.getX(),(int)event.getY()))
            {
                System.exit(0);
            }
        }
        return true;
    }





}
