package com.games.bitworxx.tastyfly.backgrounds.levels;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.bitworxx.tastyfly.helper.RandomRange;
import com.games.bitworxx.tastyfly.helper.RectHandler;

/**
 * Created by WEIS on 21.07.2015.
 */
public class Level3 extends Level {
    public Level3(Rect bounds, int pos) {
        super(bounds, pos);
    }

    @Override
    public void setup() {
        MaceRects.clear();

        MaceRects.add(RectHandler.combineRects(Maces.get(0), Maces.get(9)));
        MaceRects.add(RectHandler.combineRects(Maces.get(2),Maces.get(4)));
        MaceRects.add(RectHandler.combineRects(Maces.get(5), Maces.get(14)));
        MaceRects.add(RectHandler.combineRects(Maces.get(7),Maces.get(7)));
        MaceRects.add(RectHandler.combineRects(Maces.get(48),Maces.get(57)));
        MaceRects.add(RectHandler.combineRects(Maces.get(34),Maces.get(58)));
        MaceRects.add(RectHandler.combineRects(Maces.get(27),Maces.get(59)));
        MaceRects.add(RectHandler.combineRects(Maces.get(36),Maces.get(60)));
        MaceRects.add(RectHandler.combineRects(Maces.get(53),Maces.get(63)));


        Spiders.clear();
        getSpiderFromRect(Maces.get(7), RandomRange.getRandom(20,30),1);
        getSpiderFromRect(Maces.get(8), RandomRange.getRandom(40,80),2);
        getSpiderFromRect(Maces.get(14), RandomRange.getRandom(20,40),1);

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
