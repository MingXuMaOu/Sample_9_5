package com.example.sample_9_5;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author: liuming
 * @date: 2022/4/7
 */
public class BNPoint {
    static final float RADIS = 80;
    float x;
    float y;
    int color[];
    int countl;

    public BNPoint(float x,float y,int color[],int countl){
        this.x = x;
        this.y = y;
        this.color = color;
        this.countl = countl;
    }

    public void setLocation(float x,float y){
        this.x = x;
        this.y = y;
    }

    public void drawSelf(Paint p, Paint p1, Canvas c){
        p.setARGB(180,color[1],color[2],color[3]);
        c.drawCircle(x,y,RADIS,p);
        p.setARGB(150,color[1],color[2],color[3]);
        c.drawCircle(x,y,RADIS - 10,p);
        c.drawCircle(x,y,RADIS - 18,p1);
        c.drawText(countl + 1 + "",x,y - 100,p1);
    }
}
