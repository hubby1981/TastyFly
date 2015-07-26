package com.games.bitworxx.tastyfly.backgrounds.levels;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;

/**
 * Created by WEIS on 21.07.2015.
 */
public class Level4 extends Level {
    public Level4(Rect bounds, int pos) {
        super(bounds, pos);
    }

    @Override
    public void setup() {
        MaceRects.clear();
        MaceRects.add(RectHandler.combineRects(Maces.get(1),Maces.get(10)));
        MaceRects.add(RectHandler.combineRects(Maces.get(18),Maces.get(18)));
        MaceRects.add(RectHandler.combineRects(Maces.get(3),Maces.get(4)));
        MaceRects.add(RectHandler.combineRects(Maces.get(5), Maces.get(14)));
        MaceRects.add(RectHandler.combineRects(Maces.get(21),Maces.get(21)));
        MaceRects.add(RectHandler.combineRects(Maces.get(43),Maces.get(60)));
        Spiders.clear();
        getSpiderFromRect(Maces.get(1), RandomRange.getRandom(30, 90),2);

        getSpiderFromRect(Maces.get(14), RandomRange.getRandom(40,50),1);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
