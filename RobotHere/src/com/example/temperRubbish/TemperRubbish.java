package com.example.temperRubbish;

import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by temper on 2017/7/19.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class TemperRubbish extends AdvancedRobot {
    //int heading;
    EnemyTank enemyTank = new EnemyTank();
    private double bulletSpeed;
    private double bulletEnergy;
    long time;
    double movement = Double.POSITIVE_INFINITY;

    private Coordination maxCoordination;

    //here is about the pattern match
    /**
     * path。是通过记录所有的点，然后查找规律
     * 1.今天是7.25，目前已经能做到固定点，直线运动，
     *              圆周运动（但是一般子弹速度较慢，等到了，人家一般撞墙后，变圆心了，很尴尬，除非碰巧遇到了正好在区域内画圆的）
     *              圆周运动需要优化的是，虽然可以找到点，但是，遇到变圆心，浪费很多子弹，在考虑，是不是对于这种，找到周期规律再决定如何打。
     *              而且这个预测直线和圆周的过程最大的特点是，真的需要大量运算，都怪数值分析学得不好，感觉需要补补血了。
     * 2.终极目标是查找周期规律或者差值周期规律，查找周期规律的话，可以做到，大师差值规律比较复杂，还在找数学公式。
     */
    List<EnemyTank> path = new ArrayList<>();


    @Override
    public void run(){
        setColor();
        setTurnRadarRight(400);
        while(true){
            System.out.println("scan");
            //System.out.println("scan");
            scan();
            if(getDistanceRemaining()==0){
                setAhead(movement = -movement);
                setTurnRightRadians(Math.PI/1.5);
            }
        }
    }
    //*********************************************************************************************************
    //set the color.
    private void setColor(){
        setColors(Color.RED,Color.RED,Color.RED);
    }
    //*****************************************************************************************************************
    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        enemyTank.generate(event,this);
        //setRadarHeadAt();
        if(getEnergy()>1)
            setFire(getPower(enemyTank.getDistance()));

        System.out.println("track");
        trackHim();
    }

    //*****************************************************************************************************************
    //radar ---->robot code tutorials

    public void trackHim() {
        double RadarOffset;
        //网上获取到的运算公式 https://wenku.baidu.com/view/41fb6a8908a1284ac850437f.html
        RadarOffset = Utils.normalRelativeAngle(enemyTank.getAbsoluteBearingRadians() - getRadarHeadingRadians());
        System.out.println("point1");
        setTurnRadarRightRadians(RadarOffset * 1.2);
    }

    /**
     * 跟踪地方坦克的位置。
     */
    private void setRadarHeadAt(){
        setTurnGunRightRadians((enemyTank.getAbsoluteBearingRadians()-getRadarHeadingRadians())*1.5);
    }
    //cal the power
    public int getPower(double distance){
        int power = Math.min(Math.min(4,400/(int)distance),(int)getEnergy()/3);
        bulletEnergy = power;
        bulletSpeed = Rules.getBulletSpeed(power);
        return power;
    }

    public Coordination getMaxCoordination() {
        return maxCoordination;
    }

    public void setMaxCoordination(Coordination maxCoordination) {
        this.maxCoordination = maxCoordination;
    }


    @Override
    public void onDeath(DeathEvent event) {
        super.onDeath(event);
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
        super.onBulletHit(event);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        super.onHitByBullet(event);
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        super.onHitRobot(event);
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        super.onHitWall(event);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        super.onRobotDeath(event);
    }

}
