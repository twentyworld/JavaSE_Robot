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
    double PI = Math.PI;

    @Override
    public void run() {
        out.println("this is running, where are you printing at? i can't find you!!");
        setColor();
        enemy = new Enemy();
        enemy.setDistance(40000);
        // let the gun and radar independent from the body
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        turnRadarLeft(360);

        while (true) {
            RoundMovement();
            //Scan();
            doScanner();
            doGun();
            fire(1);
            execute();
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        turnLeft(180);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {

        //this is a way to note the enemy.
        double bearing = event.getBearing();
        double distance = event.getDistance();
        bearing = Math.toRadians(bearing);

        double genyX = getX() + Math.sin(bearing) * distance;
        double genyY = getY() + Math.cos(bearing) * distance;
        enemy.setPoint(new Coordination(genyX - getX(), genyY - getY()));
        out.println(genyX + " " + genyY);

        enemy.setDistance(event.getDistance());
        enemy.setCtime(event.getTime());
        enemy.setSpeed(event.getVelocity());
        enemy.setHead(event.getHeading());


        //these all due to save the enemy information


    }


    public void setColor() {
        setAllColors(Color.YELLOW);
    }

    public void Scan() {
        double radarGo;
        if (getTime() - enemy.getCtime()>4) {
            radarGo = 360;
        } else {
            radarGo = getRadarHeading() - AdjustDegreeByDot(enemy.getPoint().getX(), enemy.getPoint().getY());
            if (radarGo < 0)
                radarGo -= 15;
            else
                radarGo += 15;
        }
        //setTurnRadarLeftRadians(NormaliseBearing(radarOffset)); //左转调整转动角度到PI内
        setTurnRadarLeft(radarGo);
    }
    /*
	 * generally it could separate into two steps, fire and move.
	 *
	 * 1.fire means you find the enemy, then you fire.
	 * it could be difficult when you can not assume that the enemy just stand there waiting you hit him,
	 * he will go, to another place, it means that you should always find the way he goes.
	 *
	 * 2.move means you move, enemy could not hit you.
	 * generally it has several ways to move, what i am doing here is mix them into one.
	 * so that, when 被射中。，change the way, changing, then find the best one. and trying to be better.
	 *
	 *  and these two step always stay do not depend on others.
	 */


    void doGun() {

        //calculate the time of the bullet flying
        //long time = getTime() + (int) (enemy.getDistance() / (20 - (3 * firePower)));
        double time = getTime(enemy.getPoint(), enemy.getHead(), enemy.getSpeed(), (20 - (3 * firePower)));
        //where this is to show how to calculate the the degree of the gun

        Coordination coordination = enemy.guessPoint(time);

        double degree = AdjustDegreeByDot(coordination.getX(), coordination.getY());
        setTurnGunRight(degree);

    }


    public double AdjustDegreeByDot(double x, double y) {

        double degree;

        if (x == y & y == 0)
            return 0;
        if (x == 0) {
            if (y > 0) return 90;
            if (y < 0) return 279;
        } else if (y == 0) {
            if (x > 0) return 0;
            if (x < 0) return 180;
        }

        degree = Math.atan(Math.abs(x / y)) / Math.PI * 180;

        if (x > 0 && y > 0) return 90 - degree;
        if (x > 0 && y < 0) return 90 + degree;
        if (x < 0 && y > 0) return 270 + degree;
        if (x < 0 && y < 0) return 270 - degree;
        return degree;
    }


    //根据目前已知的信息，目前所在，角度，速度，自身子弹的速度
    // 可以求出的是在t秒之后，子弹和enmey都会到达某一个点，这求出的是t时间。这样可以省去很多的数学计算的严谨性带来的多向性。
    //v1==> enemy vic,v2 the bullet.
    public double getTime(Coordination coor, double degree, double v1, double v2) {

        double distance = Math.sqrt(coor.getX() * coor.getX() + coor.getY() * coor.getY());

        double radianDegree = Math.toRadians(degree);
        //cal the degree by vector  -->this vector.

        //根据向量求夹角
        //that is (cos1*cos2+sin1*sin2)/(1*1+2*2)=cos degree
        double cosDegree = (Math.cos(coor.getX() / distance) * Math.cos(radianDegree) +
                Math.sin(coor.getY() / distance) * Math.sin(radianDegree)) / Math.sqrt(1 + 1);

        //求解二元一次方程。。。。。。。麻烦的过分了。。。。。
        double a = Math.sqrt(v1 * v1 - v2 * v2);
        double b = 2 * v1 * distance * cosDegree;
        double c = distance * distance;
        //就实际情况而言，-的应该是负数，一般不会是正数，如果是，方程有问题。这里只要+的，
        double t1 = (b + Math.sqrt(b * b + 4 * a * c)) / (2 * a);
        return t1;

    }

    //how to move
    public void RoundMovement() {
        setAhead(10000);
        setTurnRightRadians(enemy.getBearing() + (Math.PI / 2));
    }

    //GET a random degree
    public void RandomTurning() {
        Random random = new Random();
        int degree = random.nextInt(360);
        if (degree > 180)
            turnLeft(degree - 180);
        else turnRight(degree);
    }

    //get a random going length
    public double RandomHeading() {
        Random random = new Random();
        double length = random.nextInt(500);
        return Math.sqrt(length);
    }

    //this is a new test for git





    void doScanner()

    {

        double radarOffset;  //雷达偏移量

        if (getTime() - enemy.getCtime() > 4) //???why来回扫了4个回合都没扫到意味失去了目标，再全扫一遍

        {

            //if we haven't seen anybody for a bit....

            radarOffset = 360;      //rotate the radar to find a target

        } else

        {

            //next is the amount we need to rotate the radar by to scan where the target is now

            //通过扫描决定雷达旋转的弧度，"见基本原理方向剖析及目标锁定www.robochina.org".雷达弧度-敌人角度得到两者相差为旋转值

            radarOffset = getRadarHeadingRadians() - absbearing(getX(), getY(), enemy.getPoint().getX(), enemy.getPoint().getY());

            //this adds or subtracts small amounts from the bearing for the radar to produce the wobbling

            //and make sure we don't lose the target

            //在得到的角度中加或减一点角度，让雷达很小的范围内摆而不失去目标

            if (radarOffset < 0)

                radarOffset -= PI / 8;  //(0.375)

            else

                radarOffset += PI / 8;

        }

        //turn the radar

        setTurnRadarLeftRadians(NormaliseBearing(radarOffset)); //左转调整转动角度到PI内

    }


    public double absbearing(double x1, double y1, double x2, double y2)

    {

        double xo = x2 - x1;

        double yo = y2 - y1;

        double h = getrange(x1, y1, x2, y2);

        if (xo > 0 && yo > 0)

        {

            //反正弦定义，对边除斜边得弧度.以robocode中的绝对方向系及坐标系参照

            //x,y为正右上角为0-90,x正y负右下角为90-180,x,y负左下角180-270,x负，y正右上角270-360

            //此处要理解robocode中的绝对角度是上为0,下为180，如以中心为点划分象限则得到下面的结果

            return Math.asin(xo / h);

        }

        if (xo > 0 && yo < 0)

        {

            return Math.PI - Math.asin(xo / h); //x为正,y为负第二象限角

        }

        if (xo < 0 && yo < 0)

        {

            return Math.PI + Math.asin(-xo / h); //第三象限内180+角度

        }

        if (xo < 0 && yo > 0)

        {

            return 2.0 * Math.PI - Math.asin(-xo / h); //四象限360-角度

        }

        return 0;

    }

    double NormaliseBearing(double ang)

    {

        if (ang > PI)

            ang -= 2 * PI;

        if (ang < -PI)

            ang += 2 * PI;

        return ang;

    }

    public double getrange(double x1, double y1, double x2, double y2)

    {

        double xo = x2 - x1;

        double yo = y2 - y1;

        double h = Math.sqrt(xo * xo + yo * yo);

        return h;

    }
}
