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

    double firePower = 1;

    @Override
    public void run() {
        // TODO Auto-generated method stub

        System.out.println("this is running, where are you printing at? i can't find you!!");
        setColor();
        // let the gun and radar independent from the body
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);


        while(true) Execution();
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

        //this is a way to note the enemy.
        double bearing = (getHeading() + event.getBearing()) % 360;
        double distance = event.getDistance();
        bearing = Math.toRadians(bearing);

        double genyX = getX() + Math.sin(bearing) * distance;
        double genyY = getY() + Math.cos(bearing) * distance;
        enemy.getPoint().setX(genyX);
        enemy.getPoint().setY(genyY);
        System.out.println(genyX+" "+genyY);

        enemy.setDistance(event.getDistance());
        enemy.setCtime(event.getTime());
        enemy.setSpeed(event.getVelocity());
        enemy.setHead(event.getHeading());

    }


    public void setColor() {
        setAllColors(Color.YELLOW);
    }

    public void doScanner(){
        int degree = 2;

        setTurnRadarLeft(degree);
        System.out.println("yes,find it");

       // fire(1);
    }


    //this is the real alg about the robot;
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
    void Execution() {
        System.out.println("this is new execution");

        /*
        you should always scanning other robot at any time
         */

        RoundMovement();
        //setTurnGunLeftRadians(30);
        //fire(1);
        doScanner();
        doGun();
        execute();



    }




    void doGun()

    {


        //calculate the time of the bullet flying
        long time = getTime() + (int) (enemy.getDistance() / (20 - (3 * firePower)));

        //where this is to show how to calculate the the degree of the gun

        double degree = AdjustDegreeByDot(enemy.guessX(time)-getX(), enemy.guessY(time)-getY());
        //setTurnGunLeftRadians(degree-);  //调整相对角度到2PI内


    }

    public double AdjustDegreeByDot(double x, double y){

        double degree;

        if(x == y&y == 0)
            return 0;
        if(x==0){
            if(y>0) return Math.PI/2;
            if(y<0) return Math.PI/2*3;
        }
        else if(y==0){
            if(x>0) return 0;
            if(x<0) return Math.PI;
        }

        degree = Math.atan(x/y);

        return degree;
    }

    //how to move
    public void RoundMovement() {

        setAhead(30);
        setTurnLeft(30);
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
