package com.example.good;

/**
 * Created by w on 2017/7/15.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */

class Enemy {
/*

     * ok, we should really be using accessors and mutators here,

     * (i.e getName() and setName()) but life's too short.

     */

    String name;

    private double bearing;

    private double head;

    private long ctime; //game time that the scan was produced

    private double speed;

    private Coordination point;

    private double distance;

    public Enemy() {

    }

    public Enemy(String name, double bearing, double head, long ctime, double speed, Coordination point, double distance) {
        this.name = name;
        this.bearing = bearing;
        this.head = head;
        this.ctime = ctime;
        this.speed = speed;
        this.point = point;
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Coordination getPoint() {
        return point;
    }

    public void setPoint(Coordination point) {
        this.point = point;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getHead() {
        return head;
    }

    public void setHead(double head) {
        this.head = head;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }



    public double guessX(long when) {
        //以扫描时和子弹到达的时间差 ＊ 最大速度=距离, 再用对手的坐标加上移动坐标得到敌人移动后的坐标
        long diff = when - ctime;
        return point.getX()+Math.sin(head)*speed*diff; //目标移动后的坐标
    }

    public double guessY(long when) {
        long diff = when - ctime;
        return point.getY()+Math.cos(head)*speed*diff;
    }
}
