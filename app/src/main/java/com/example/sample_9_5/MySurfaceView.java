package com.example.sample_9_5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Set;

/**
 * @author: liuming
 * @date: 2022/4/7
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    MainActivity mActivity;
    Paint mPaint;
    Paint mPaint1;
    HashMap<Integer,BNPoint> hm = new HashMap<>();
    static int countl = 0;
    BNPoint bp;


    public MySurfaceView(MainActivity activity) {
        super(activity);
        mActivity = activity;
        this.getHolder().addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setTextSize(35);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint1.setColor(Color.WHITE);
        mPaint1.setStrokeWidth(5);
        mPaint1.setStyle(Paint.Style.STROKE);

        Set<Integer> ks = hm.keySet();
        for (Integer i : ks) {
            bp = hm.get(i);
            bp.drawSelf(mPaint,mPaint1,canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int id = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >>> MotionEvent.ACTION_POINTER_ID_SHIFT;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                hm.put(id,new BNPoint(event.getX(id), event.getY(id),getColor(),countl++));
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //辅点down
                if(id<event.getPointerCount()-1)
                {
                    //将编号往后顺（相当于给点排序）
                    HashMap<Integer,BNPoint> hmTemp=new HashMap<Integer,BNPoint>();
                    Set<Integer> ks=hm.keySet();//获取HashMap对象hm键的集合
                    for(int i:ks)
                    {//遍历触控点Map,对点进行排序
                        if(i<id)
                        {//当前触控点大于i
                            hmTemp.put(i, hm.get(i));//点保持不变
                        }
                        else
                        {//当前触控点小于等于i
                            hmTemp.put(i+1, hm.get(i));//点向后移一位
                        }
                    }
                    hm=hmTemp;//重新为hm赋值
                }
                //向Map中记录一个新点
                hm.put(id, new BNPoint(event.getX(id),event.getY(id),getColor(),countl++));
                break;
            case MotionEvent.ACTION_MOVE: //主/辅点move
                //不论主/辅点Move都更新其位置
                Set<Integer> ks=hm.keySet();//获取HashMap对象hm键的集合
                for(int i:ks)
                {//遍历触控点Map，更新其位置
                    hm.get(i).setLocation(event.getX(i), event.getY(i));//更新点的位置
                }
                break;
            case MotionEvent.ACTION_UP: //主点up
                //在本应用中主点UP则只需要清空Map即可，在其他一些应用中需要操作的
                //则取出Map中唯一剩下的点操作即可
                hm.clear();//清空hm
                countl=0;//计数器为0
                break;
            case MotionEvent.ACTION_POINTER_UP: //辅点up
                hm.remove(id);//从Map中删除对应id的辅点
                //将编号往前顺，不空着
                HashMap<Integer,BNPoint> hmTemp=new HashMap<Integer,BNPoint>();
                ks=hm.keySet();//获取HashMap对象hm键的集合
                for(int i:ks)
                {//遍历触控点Map，将编号往前顺，不空着
                    if(i>id)
                    {//当前触控点小于i
                        hmTemp.put(i-1, hm.get(i));//点向前移一位
                    }
                    else
                    {//当前触控点大于等于i
                        hmTemp.put(i, hm.get(i));//点位置不变
                    }
                }
                hm=hmTemp;//重新为hm赋值
                break;
        }
        repaint();
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        repaint();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    private int[] getColor(){
        int[] result = new int[4];
        result[0] = (int) (Math.random() * 255);
        result[1] = (int) (Math.random() * 255);
        result[2] = (int) (Math.random() * 255);
        result[3] = (int) (Math.random() * 255);

        return  result;
    }

    @SuppressLint("WrongCall")
    private void repaint(){
        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        try{
            synchronized (holder){
                onDraw(canvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
