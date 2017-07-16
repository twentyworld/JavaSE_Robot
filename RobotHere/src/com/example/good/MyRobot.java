package com.example.good;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.util.Random;

/**
 * Created by temper on 2017/7/15.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */

public class MyRobot extends AdvancedRobot {

    Enemy enemy;



    @Override
    public void run() {
        // TODO Auto-generated method stub
        setColor();
        // let the gun and radar independent from the body
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);


        while(true) {
            execution();
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        // TODO Auto-generated method stub
       // super.onHitWall(event);


    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        // TODO Auto-generated method stub
       // super.onScannedRobot(event);
        fire(1);
    }

    public void setColor() {
        setAllColors(Color.YELLOW);
    }


    //this is the real algr about the robot;
	/*
	 * generally it could separate into two steps, fire and move.
	 *
	 * 1.fire means you find the enemy, then you fire.
	 * it could be difficult when you can not assume that the enemy just stand there waiting you hit him,
	 * he will go, to another place, it means that you should always find the way he goes.
	 *
	 * 2.move means you move, enemy could not hit you.
	 * generally it has several ways to move, what i am doing here is mix them into one.
	 *  so that, when 被射中。，change the way, changing, then find the best one. and trying to be better.
	 *
	 *
	 *  and these two step always stay do not depend on others.
	 */
    void execution() {
        System.out.println("this is new execution");

        /*
        you should always scanning other robot at any time
         */

        setTurnGunLeftRadians(30);


    }
    //how to move
    public void movement() {

    }

    //GET a random degree
    public void RandomTurning(){
        Random random = new Random();
       int degree = random.nextInt(360);
       if(degree>180)
           turnLeft(degree-180);
       else turnRight(degree);
    }

    //get a random going length
    public double RandomHeading(){
        Random random = new Random();
        double length = random.nextInt(500);
        return Math.sqrt(length);
    }

    //this is a new test for git
}
