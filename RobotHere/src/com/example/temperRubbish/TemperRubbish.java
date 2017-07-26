package com.example.temperRubbish;

import com.example.temperRubbish.Util.TemperUtils;
import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by temper on 2017/7/19.
 * copy as you like, but with these word.
 * at last, The forza horizon 3 is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class TemperRubbish extends AdvancedRobot {
    //int heading;
    EnemyTank enemyTank ;
    private double bulletSpeed;
    private double bulletEnergy;
    long time;
    double movement = Double.POSITIVE_INFINITY;

    //move
    static final double BASE_MOVEMENT = 600;
    static final double BASE_TURN = Math.PI / 1.5;
    //static double movement;

    private Coordination maxCoordination = new Coordination(TemperUtils.WIDTH, TemperUtils.HEIGHT);


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
    int maxPathLength = 1000;


    @Override
    public void run(){
        setAdjustGunForRobotTurn (true);
        //setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn (true);
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
    //*****************************************************************************************************************
    //set the color.
    private void setColor(){
        setColors(Color.RED,Color.RED,Color.RED);
    }
    //*****************************************************************************************************************
    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        enemyTank = new EnemyTank ();
        enemyTank.generate(event,this);
        //setRadarHeadAt();
        if(getEnergy()>1)
            setFire(getPower(enemyTank.getDistance()));
        System.out.println("track");
        trackEnemyTank();
        record (enemyTank);

        //预测位置

        //调整枪口位置。
        double gunTurn ;
        Coordination predictCoordination = null;
        if (PathDetection.isOrganized(path)){
            PathDetection detection = PathDetection.getPathDetection (path);
            if(detection != null)
                predictCoordination = detection.predictPath (path,this);
        }
        else
            predictCoordination = enemyTank.getCoordination();

        if(predictCoordination!=null){
            gunTurn = getGunRadianByPrediction(predictCoordination);
            setTurnGunRightRadians(gunTurn);
        }

    }
    //*****************************************************************************************************************

    public double getGunRadianByPrediction(Coordination predictCoordination){
        double gunTurn;
        System.out.println (predictCoordination+"：回传的预测路径");
        //向量
        Point2D.Double predictVector = TemperUtils.calculateVector (new Coordination (getX (),getY ()),predictCoordination);
        System.out.println (predictVector+"：回传的预测向量");
        //向量转角度
        double radian = TemperUtils.calculateVectorIntersectionAngle (predictVector);
        System.out.println (radian+"向量的现象角度");
        //转成绝对角度
        radian = TemperUtils.translateQuadrantToHeadingRadian(radian);
        System.out.println (radian+"敌方tank的绝对角度");
        gunTurn = radian - getGunHeadingRadians ();
        System.out.println(gunTurn+"旋转角度");
        return gunTurn;
    }

    /**
     * 跟踪地方坦克的位置。
     */
    public void trackEnemyTank() {
        double RadarOffset;
        //网上获取到的运算公式 https://wenku.baidu.com/view/41fb6a8908a1284ac850437f.html
        RadarOffset = Utils.normalRelativeAngle(enemyTank.getAbsoluteBearingRadians() - getRadarHeadingRadians());
        System.out.println("point1");
        setTurnRadarRightRadians(RadarOffset * 1.2);
    }

    /**
     * 记录最近的1000步的点。后期优化点的长度，这里有个remove。感觉会是最大的败笔。超过1000步数的话，每一次都复制一遍。
     * @param enemyTank
     */
    private void record(EnemyTank enemyTank) {
        if(path.size ()>=maxPathLength)
            path.remove (0);
        path.add (enemyTank);
    }
    //calculate the power
    public int getPower(double distance){
        int power = Math.min(Math.min(4,1000/(int)distance),(int)getEnergy()/3);
        bulletEnergy = power;
        bulletSpeed = Rules.getBulletSpeed(power);
        return power;
    }

    public Coordination getMaxCoordination() {
        return maxCoordination;
    }

//    public void setMaxCoordination(Coordination maxCoordination) {
//        this.maxCoordination = maxCoordination;
//    }


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
    public void onHitWall(HitWallEvent e){
        if ( Math.abs (movement) > BASE_MOVEMENT ) {
            movement = BASE_MOVEMENT;
        }
    }
    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        super.onRobotDeath(event);
    }

}
