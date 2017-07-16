package com.example.good;

/**
 * Created by w on 2017/7/15.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */

/*

* This class holds scan data so that we can remember where enemies were

* and what they were doing when we last scanned then.

* You could make a hashtable (with the name of the enemy bot as key)

* or a vector of these so that you can remember where all of your enemies are

* in relation to you.

* This class also holds the guessX and guessY methods. These return where our targeting

* system thinks they will be if they travel in a straight line at the same speed

* as they are travelling now.  You just need to pass the time at which you want to know

* where they will be.

* 保存我们扫描到的目标的所有有用数据，也可用hashtable，vector方法处理所有和我们有关的目标数据(用于群战)

* 中间的guessX,guessY方法是针对做直线均速运动机器人一个策略

*/
class Enemy {
/*

     * ok, we should really be using accessors and mutators here,

     * (i.e getName() and setName()) but life's too short.

     */

    String name;

    public double bearing;

    public double head;

    public long ctime; //game time that the scan was produced

    public double speed;

    public double x,y;

    public double distance;

    public double guessX(long when)

    {

        //以扫描时和子弹到达的时间差 ＊ 最大速度=距离, 再用对手的坐标加上移动坐标得到敌人移动后的坐标

        long diff = when - ctime;

        return x+Math.sin(head)*speed*diff; //目标移动后的坐标

    }

    public double guessY(long when)

    {

        long diff = when - ctime;

        return y+Math.cos(head)*speed*diff;

    }
}
