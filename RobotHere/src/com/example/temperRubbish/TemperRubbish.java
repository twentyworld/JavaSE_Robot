package com.example.temperRubbish;

import robocode.*;

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
    double bulletEnergy;
    long time;
    double movement = Double.POSITIVE_INFINITY;

    Coordination maxCoordination;

    //here is about the pattern match----------->comes from the website.
    List<EnemyTank> path = new ArrayList<>();


    @Override
    public void run(){
        setColor();
        while(true){
            scan();
            if(getDistanceRemaining()<5){
                setAhead(movement = -movement);
                setTurnRadarRightRadians(Math.PI/1.5);
            }

        }
    }
    //*********************************************************************************************************
    //set the color.
    private void setColor(){
        setColors(Color.RED,Color.RED,Color.RED);
    }

    //*****************************************************************************************************************
    //*****************************************************************************************************************

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

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        enemyTank.generate(event,this);
        setRadarHeadAt();
        if(getEnergy()>1)
            setFire(getPower());
    }
    //*****************************************************************************************************************
    //*****************************************************************************************************************
    //*****************************************************************************************************************



    //radar ---->robot code tutorials
    private void setRadarHeadAt(){
        setTurnGunRightRadians((enemyTank.getAbsoluteBearingRadians()-getRadarHeadingRadians())*1.5);
    }
    //cal the power
    private int getPower(){
        int power = Math.min(Math.min(4,400/(int)enemyTank.getDistance()),(int)getEnergy()/3);
        bulletEnergy = power;
        bulletSpeed = Rules.getBulletSpeed(power);
        return power;
    }



}
