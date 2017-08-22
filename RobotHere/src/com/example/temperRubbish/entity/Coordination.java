package com.example.temperRubbish.entity;

import robocode.util.Utils;

/**
 * Created by temper on 2017/7/18,下午2:24.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class Coordination {
    private  double x ;
    private double y ;

    public Coordination() {
    }

    public Coordination(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordination{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordination that = (Coordination) o;

        if (!Utils.isNear(that.getX(),x)) return false;
        if (!Utils.isNear(that.getY(),y)) return false;
        return true;
    }

    public boolean similar(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordination that = (Coordination) o;

        if (Math.abs(x-that.getX())>0.0005) return false;
        if (Math.abs(y-that.getY())>0.0005) return false;
        return true;
    }



    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
