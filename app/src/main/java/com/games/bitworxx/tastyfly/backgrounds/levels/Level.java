package com.games.bitworxx.tastyfly.backgrounds.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.backgrounds.ColorTapet;
import com.games.bitworxx.tastyfly.characters.BaseCharacter;
import com.games.bitworxx.tastyfly.characters.Spider;
import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;
import com.games.bitworxx.tastyfly.view.GameView;
import com.games.bitworxx.tastyfly.view.Views;

import java.util.ArrayList;

/**
 * Created by WEIS on 20.07.2015.
 */
public abstract class Level {
    public  Rect DisplayBounds;
    protected  Rect MaceBounds;
    public int POS=0;
    Bitmap Draw=null;

    public Paint Mace=null;
    ArrayList<Rect> Maces = new ArrayList<>();
    protected ArrayList<Rect> MaceRects = new ArrayList<>();
    public ArrayList<Spider> Spiders=new ArrayList<>();
    public Level(Rect bounds,int pos)
    {
        DisplayBounds=bounds;
        POS = pos;
        MaceBounds=new Rect(DisplayBounds.left,DisplayBounds.top+(DisplayBounds.height()/GameConst.SIZE),DisplayBounds.right,DisplayBounds.bottom-(DisplayBounds.height()/GameConst.SIZE));
        getGrid();
        setup();
    }

    public  void getSpiderFromRect(Rect rect,int down,int max)
    {
        rect=translate(rect);
        if(RandomRange.getRandom(1,max)==max)
        {
            rect=new Rect(rect.left,rect.top,rect.right-rect.width()/4,rect.bottom-rect.height()/4);
            down=RandomRange.getRandom(down / 2, down * 4);
        }
        Spider spider = new Spider(rect.left+rect.width()/4,rect.bottom+rect.height(),rect,false);
        spider.Size=rect.height()/2;
        spider.OWN_DOWN=down;
        if(RandomRange.getRandom(1,max)==max)
            Spiders.add(spider);
    }

    public abstract void setup();
    public abstract void draw(Canvas canvas);

    public Rect translate(Rect r)
    {
        return new Rect(r.left+POS,r.top,r.right+POS,r.bottom);
    }

    public boolean checkLevelChar(BaseCharacter character)
    {
        boolean result = false;

        for(Rect r :MaceRects)
        {
            if(!result)
            {
                result=character.isInRect(translate(r));
                if(result) ((GameView)Views.GAME_VIEW).DEAD_BY="you`re mud on a block";
            }
        }

        for(Spider s :Spiders)
        {
            if(!result)
            {
                result=character.isInRect(new Rect(s.X,s.Y,s.X+s.Size,s.Y+s.Size));
                if(result) ((GameView)Views.GAME_VIEW).DEAD_BY="you`re tasty for a spider";
            }
        }




        return result;

    }


    public Bitmap getDraw()
    {
        if(Draw!=null)
            return  Draw;
        if(DisplayBounds!=null)
        {
            Draw=Bitmap.createBitmap(DisplayBounds.width(),DisplayBounds.height(), Bitmap.Config.ARGB_4444);
            Canvas canvas=new Canvas(Draw);
            drawBack(canvas);
            draw(canvas);
            canvas=null;
        }
        return Draw;
    }

    protected void drawBack(Canvas canvas)
    {
        int size= GameConst.SIZE;


        ColorTapet.drawOnRect(canvas, DisplayBounds, size, false);
        drawMace(canvas, size, DisplayBounds);

    }

    protected ArrayList<Rect> getGrid()
    {
        if(Maces.size()==0)
        {
            Maces = RectHandler.getGrid(8,8,MaceBounds);
        }
        return Maces;
    }

    protected void drawMace(Canvas canvas,int size,Rect bounds)
    {
        Mace = new Paint();
        Mace.setStyle(Paint.Style.FILL);
        Mace.setColor(GameConst.MACE_COLOR);



        for(Rect r : MaceRects)
            canvas.drawRect(r,Mace);

    }

    public void release()
    {
        if(Draw!=null)
            Draw.recycle();
        Draw=null;
        DisplayBounds=null;
        MaceBounds=null;
        MaceRects.clear();
        MaceRects=null;
        Mace=null;
        Spiders.clear();

    }
}
