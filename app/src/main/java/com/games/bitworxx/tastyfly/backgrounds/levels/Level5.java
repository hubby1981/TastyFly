package com.games.bitworxx.tastyfly.backgrounds.levels;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;

/**
 * Created by WEIS on 21.07.2015.
 */
public class Level5 extends Level {
    public Level5(Rect bounds, int pos) {
        super(bounds, pos);
    }

    @Override
    public void setup() {
        MaceRects.clear();
        MaceRects.add(RectHandler.combineRects(Maces.get(1), Maces.get(33)));
        MaceRects.add(RectHandler.combineRects(Maces.get(29), Maces.get(61)));

        Spiders.clear();
        getSpiderFromRect(Maces.get(3), RandomRange.getRandom(5,30), 4);
        getSpiderFromRect(Maces.get(4), RandomRange.getRandom(4,15),4);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
