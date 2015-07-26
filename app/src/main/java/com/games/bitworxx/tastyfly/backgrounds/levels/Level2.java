package com.games.bitworxx.tastyfly.backgrounds.levels;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;

/**
 * Created by WEIS on 21.07.2015.
 */
public class Level2 extends Level {
    public Level2(Rect bounds, int pos) {
        super(bounds, pos);
    }

    @Override
    public void setup() {
        MaceRects.clear();

        MaceRects.add(RectHandler.combineRects(Maces.get(0),Maces.get(9)));
        MaceRects.add(RectHandler.combineRects(Maces.get(2), Maces.get(27)));
        MaceRects.add(RectHandler.combineRects(Maces.get(4),Maces.get(45)));
        MaceRects.add(RectHandler.combineRects(Maces.get(6),Maces.get(31)));

        Spiders.clear();
        getSpiderFromRect(Maces.get(9), RandomRange.getRandom(20,40),2);
        getSpiderFromRect(Maces.get(22), RandomRange.getRandom(30,60),1);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
