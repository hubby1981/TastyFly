package com.games.bitworxx.tastyfly.characters;

/**
 * Created by WEIS on 24.07.2015.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;

import java.util.ArrayList;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.helper.GameConst;
import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;
import com.games.bitworxx.tastyfly.view.Views;

import java.util.ArrayList;

/**
 * Created by WEIS on 20.07.2015.
 */
public class Bee extends BaseCharacter {
    @Override
    public void setup() {
        BackColor= GameConst.BACK_CHAR1_NORMAL;
        FlyColor= GameConst.BACK_CHAR1_FLY;
        Name="Wanna Bee";
        StoreProduct="buychar2";
    }

    @Override
    public BaseCharacter getCopy() {
        return isLocked()? new Fly():new Bee();
    }

    @Override
    public int getMouth() {
        return 4;
    }

    @Override
    public boolean isLocked() {

        if(Views.MAIN_VIEW.readBest(2)>100)
            return false;
        return true;
    }

    @Override
    public void onDrawOther(Canvas canvas, Paint back) {
        ArrayList<Rect> rects1 = RectHandler.getGrid(16, 1, getBody());
        back.setColor(FlyColor);

        ArrayList<Rect> rects = RectHandler.getGrid(1, 16, rects1.get(10));
        canvas.drawRect(RectHandler.combineRects(rects.get(0), rects.get(15)), back);
        rects = RectHandler.getGrid(1,16,rects1.get(12));
        canvas.drawRect(RectHandler.combineRects(rects.get(0), rects.get(15)), back);
        rects = RectHandler.getGrid(1,16,rects1.get(14));
        canvas.drawRect(RectHandler.combineRects(rects.get(0), rects.get(15)), back);
        back.setColor(BackColor);
    }

    @Override
    protected  void drawEye(Rect body, Canvas canvas, Paint back, int x1, int w1) {
        ArrayList<Rect> rects1 = RectHandler.getGrid(16, 1, body);





        canvas.drawRect(body, back);

        back.setColor(GameConst.EYE_COLOR);
        ArrayList<Rect> rects=RectHandler.getGrid(1,16,rects1.get(3));
        canvas.drawRect(RectHandler.combineRects(rects.get(13), rects.get(14)), back);
        rects=RectHandler.getGrid(1,16,rects1.get(5));
        canvas.drawRect(RectHandler.combineRects(rects.get(10), rects.get(11)), back);
        rects=RectHandler.getGrid(1,16,rects1.get(4));
        canvas.drawRect(RectHandler.combineRects(rects.get(13), rects.get(14)), back);
        rects=RectHandler.getGrid(1,16,rects1.get(6));
        canvas.drawRect(RectHandler.combineRects(rects.get(10),rects.get(11)), back);
        rects=RectHandler.getGrid(1, 16, rects1.get(7));
        canvas.drawRect(RectHandler.combineRects(rects.get(13),rects.get(15)), back);


    }

    @Override
    public Rect getFlyRect1(Rect body) {


        int w = body.width()/3;
        int l = body.left;
        int t = body.top+w;
        return new Rect(l-w,t,l,t+w);
    }

    @Override
    public Rect getFlyRect2(Rect body) {

        int w = body.width()/3;
        int l = body.left+w;
        int t = body.top;
        return new Rect(l,t-w,l+w,t);
    }

    @Override
    public Rect getFlyRect3(Rect body) {


        int w = body.width()/3;
        int l = body.left;
        int t = body.top+w;
        return new Rect(l-w,t,l,t+w);
    }

    @Override
    public Rect getFlyRect4(Rect body) {

        int w = body.width()/3;
        int l = body.left+w;
        int t = body.top;
        return new Rect(l,t-w,l+w,t);
    }

    @Override
    public float getSinkRate() {
        return (float)3.2;
    }

    @Override
    public float getPowerRate() {
        return (float)1.6;
    }


    @Override
    public void animatePosition() {

        int x=4;
        int y=12;
        X+= RandomRange.getRandom(-x, y);
        Y+=RandomRange.getRandom(-x,y);
    }

    @Override
    public int getCode() {
        return 3;
    }
}
