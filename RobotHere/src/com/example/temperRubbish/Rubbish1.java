package com.example.temperRubbish;

import com.example.temperRubbish.Util.TemperUtils;
import com.example.temperRubbish.entity.Coordination;
import com.example.temperRubbish.entity.EnemyTank;
import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by temper on 2017/7/19.<br/>
 * copy as you like, but with these word.<br/>
 * at last, The forza horizon 3 is really fun, buy is made, looking forward to driving together in the hurricane.<br/>
 *
 * 参数的调整对结果的影响极其大，需要分解算法，然后针对不同的预测模式
 */
public class Rubbish1 extends TemperRubbish {

    private EnemyTank enemyTank ;
    private double bulletSpeed;
    private double bulletEnergy;
    private static double movement ;

    private boolean isEnemyShot = false;
    private boolean isGoHitWall = false;
    //move
    private static final double BASE_MOVEMENT = 100;
    private static final double BASE_TURN = Math.PI / 3;
    private static double BASE_HEADING ;
    //static Coordination maxCoordination = new Coordination(TemperUtils.WIDTH, TemperUtils.HEIGHT);

    //here is about the pattern match
    /**
     * path。是通过记录所有的点，然后查找规律<br/>
     * 1.今天是7.25，目前已经能做到固定点，直线运动，<br/>
     *              圆周运动（但是一般子弹速度较慢，等到了，人家一般撞墙后，变圆心了，很尴尬，除非碰巧遇到了正好在区域内画圆的）<br/>
     *              圆周运动需要优化的是，虽然可以找到点，但是，遇到变圆心，浪费很多子弹，在考虑，是不是对于这种，找到周期规律再决定如何打。<br/>
     *              而且这个预测直线和圆周的过程最大的特点是，真的需要大量运算，都怪数值分析学得不好，感觉需要补补血了。<br/>
     * 2.终极目标是查找周期规律或者差值周期规律，查找周期规律的话，可以做到，但是差值规律比较复杂，还在找数学公式。<br/>
     */
    private static List<EnemyTank> path = new ArrayList<>();


    @Override
    public void run(){
        setAdjustGunForRobotTurn (true);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn (true);
        setColor();
        movement = 120;
        setTurnRadarRight(400);

        do{
            execute();
            if(path.size()>2&&path.get(path.size()-1).getEnergy()!=path.get(path.size()-2).getEnergy()){
                System.out.println("发射了子弹");
                isEnemyShot = true;
            }
            //尝试性圆周摆动。但凡敌方坦克发射子弹，就开始运动。以期待达到最佳效果。
            if (isEnemyShot&&!isGoHitWall){
                setMove();
                if (getDistanceRemaining()==0)isEnemyShot = false;
            }
        }while (true);
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
        System.out.println("track");
        trackEnemyTank();
        record (enemyTank);
        System.out.println(path.size());
        double gunTurn ;
        Coordination predictCoordination = null;
        PathDetection detection = null;
        if((detection = PathDetection.getPathDetection (path)) != null)
            predictCoordination = detection.predictPath (path,this);

        else{
            predictCoordination = enemyTank.getCoordination();
            System.out.println("什么都没检测到！");
        }

        if(TemperUtils.isUnderArea(predictCoordination)){
            gunTurn = getGunRadianByPrediction(predictCoordination);
            if(getEnergy()>2)
                setFire(getPower(enemyTank.getDistance()));
            setTurnGunRightRadians(gunTurn);
        }
    }

    private double getGunRadianByPrediction(Coordination predictCoordination){
        double gunTurn;
        Point2D.Double predictVector = TemperUtils.calculateVector (new Coordination (getX (),getY ()),predictCoordination);
        double radian = TemperUtils.calculateVectorIntersectionRadian(predictVector);
        radian = TemperUtils.constrainRadianFromNegativePItoPI(radian);
        radian = TemperUtils.translateQuadrantToHeadingRadian(radian);
        gunTurn = radian - getGunHeadingRadians ();
        return gunTurn;
    }


    //*****************************************************************************************************************

    /**
     * 定义了如何走
     */
    private void setMove(){
        if (path.size()<2)
            return;
        //获取双方的坦克形成的夹角。0-2pi
        double enemyRadian = TemperUtils.calculateVectorIntersectionRadian(
                TemperUtils.calculateVector(new Coordination(getX(),getY()),path.get(path.size()-1).getCoordination()));
        double headingEnemyRadian = TemperUtils.translateQuadrantToHeadingRadian(enemyRadian);

        //获取tank和原点之间形成的夹角。0-2pi
        double enemyRadianTemp = TemperUtils.calculateVectorIntersectionRadian(
                TemperUtils.calculateVector(new Coordination(getX(),getY()),new Coordination(400,300)));
        double headingEnemyRadianTemp = TemperUtils.translateQuadrantToHeadingRadian(enemyRadianTemp);
        double figureRadian;
        if (Math.abs(headingEnemyRadianTemp-(headingEnemyRadian-Math.PI/2))<Math.PI/2)
            figureRadian = headingEnemyRadian-Math.PI/2;
        else
            figureRadian = headingEnemyRadian+Math.PI/2;

        double turnRadian = TemperUtils.constrainRadianFromZeroToDoublePI(figureRadian-getHeadingRadians());

        if(turnRadian<Math.PI)
            setTurnRightRadians(turnRadian);
        else
            setTurnLeftRadians(turnRadian);
        setAhead(BASE_MOVEMENT);

        System.out.println("在执行子弹规避算法");
    }

    /**
     * 跟踪地方坦克的位置。
     */
    private void trackEnemyTank() {
        double RadarOffset;
        //网上获取到的运算公式 https://wenku.baidu.com/view/41fb6a8908a1284ac850437f.html
        RadarOffset = Utils.normalRelativeAngle(enemyTank.getAbsoluteBearingRadians() - getRadarHeadingRadians());
        //System.out.println("point1");
        setTurnRadarRightRadians(RadarOffset * 1.2);
    }

    /**
     * 记录最近的1000步的点。后期优化点的长度，这里有个remove。感觉会是最大的败笔。超过1000步数的话，每一次都复制一遍。
     * @param enemyTank
     */
    private void record(EnemyTank enemyTank) {
        int maxPathLength = 1000;
        if(path.size ()>= maxPathLength)
            path.remove (0);
        path.add (enemyTank);
    }
    //**********************打赢stable的终极利器，调整power值。stable爆炸消耗能量************************
    //calculate the power
    public double getPower(double distance){
        double power = Math.min(Math.min(2.5,1000/distance),getEnergy()/3);
        bulletEnergy = power;
        bulletSpeed = Rules.getBulletSpeed(power);
        return power;
    }

    /**
     * 保持一直停留在安全区域，这里设置的是四面各留100码作为缓冲。
     * 如果过警戒线，就专项以保证一直停留在安全区域内。返回的是radian,返回的是向右转的角度。
     */
    @Override
    public void onDeath(DeathEvent event) {
        setTurnRadarRight(400);
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
        movement = -movement;
    }
    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        //path = new ArrayList<>();
    }
    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        setTurnRadarRight(400);
    }
}
