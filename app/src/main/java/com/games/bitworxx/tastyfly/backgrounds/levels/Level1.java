package com.games.bitworxx.tastyfly.backgrounds.levels;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.characters.Spider;
import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;

/**
 * Created by WEIS on 20.07.2015.
 */
public class Level1 extends Level {


    public Level1(Rect bounds,int pos) {
        super(bounds,pos);
    }

    @Override
    public void setup() {
        MaceRects.clear();

        MaceRects.add(RectHandler.combineRects(Maces.get(0), Maces.get(7)));
        MaceRects.add(RectHandler.combineRects(Maces.get(8), Maces.get(8)));
        MaceRects.add(RectHandler.combineRects(Maces.get(15),Maces.get(15)));
        MaceRects.add(RectHandler.combineRects(Maces.get(15),Maces.get(15)));
        MaceRects.add(RectHandler.combineRects(Maces.get(55),Maces.get(55)));
        MaceRects.add(RectHandler.combineRects(Maces.get(56),Maces.get(63)));

        Spiders.clear();

        getSpiderFromRect(Maces.get(6), RandomRange.getRandom(30, 40),1);
        getSpiderFromRect(Maces.get(2), RandomRange.getRandom(10,20),2);
    }


    @Override
    public void draw(Canvas canvas) {





    }


}
