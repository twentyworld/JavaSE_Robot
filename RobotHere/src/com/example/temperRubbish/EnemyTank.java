package com.example.temperRubbish;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Created by temper on 2017/7/19.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class EnemyTank {
    private double headingRadians;
    //地方坦克的相对角度bearing角度，但是这个比较扯淡的是，跟自己的heading角度有关。
    private double bearingRadians;
    private double absoluteBearingRadians;
    private double distance;
    private double time;
    private Coordination coordination;
    private double velocity;
    private double energy;

    //got everything done
    void generate(ScannedRobotEvent event, AdvancedRobot self){
        headingRadians = event.getHeadingRadians();
        bearingRadians = event.getBearingRadians();
        //网上获取到的运算公式 https://wenku.baidu.com/view/41fb6a8908a1284ac850437f.html
        absoluteBearingRadians = bearingRadians+self.getHeadingRadians();
        absoluteBearingRadians = robocode.util.Utils.normalRelativeAngle(absoluteBearingRadians);
        distance = event.getDistance();
        time = self.getTime();
        coordination = new Coordination(self.getX()+distance*Math.sin(absoluteBearingRadians),
                self.getY()+distance*Math.cos(absoluteBearingRadians));
        velocity = event.getVelocity();
        energy = event.getEnergy();
    }

    //to find if it is back to the same place and heading

    boolean isStable(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EnemyTank enemyTank = (EnemyTank) object;

        if (Double.compare(enemyTank.headingRadians, headingRadians) != 0) return false;
        if (Double.compare(enemyTank.velocity, velocity) != 0) return false;
        return coordination.equals(enemyTank.coordination);
    }

    @Override
    public String toString() {
        return "EnemyTank{" +
                "headingRadians=" + headingRadians +
                ", bearingRadians=" + bearingRadians +
                ", absoluteBearingRadians=" + absoluteBearingRadians +
                ", distance=" + distance +
                ", coordination=" + coordination +
                ", velocity=" + velocity +
                ", energy=" + energy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnemyTank enemyTank = (EnemyTank) o;

        if (Double.compare(enemyTank.headingRadians, headingRadians) != 0) return false;
        if (Double.compare(enemyTank.velocity, velocity) != 0) return false;
        return coordination.equals(enemyTank.coordination);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(headingRadians);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + coordination.hashCode();
        temp = Double.doubleToLongBits(velocity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }



    public double getHeadingRadians() {
        return headingRadians;
    }

    public void setHeadingRadians(double headingRadians) {
        this.headingRadians = headingRadians;
    }

    public double getBearingRadians() {
        return bearingRadians;
    }

    public void setBearingRadians(double bearingRadians) {
        this.bearingRadians = bearingRadians;
    }

    public double getAbsoluteBearingRadians() {
        return absoluteBearingRadians;
    }

    public void setAbsoluteBearingRadians(double absoluteBearingRadians) {
        this.absoluteBearingRadians = absoluteBearingRadians;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Coordination getCoordination() {
        return coordination;
    }

    public void setCoordination(Coordination coordination) {
        this.coordination = coordination;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
}
