package com.example.good;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Created by temper on 2017/7/19.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class EnemyTank {
    double headingRadians;
    double bearingRadians;
    double absoluteBearingRadians;
    double distance;
    Coordination coordination;
    double velocity;
    double energy;

    //got everything done
    void generate(ScannedRobotEvent event, AdvancedRobot self){
        headingRadians = self.getHeading();
        bearingRadians = event.getBearing();
        absoluteBearingRadians = bearingRadians+self.getHeading();
        absoluteBearingRadians = robocode.util.Utils.normalRelativeAngle(absoluteBearingRadians);
        distance = event.getDistance();
        coordination = new Coordination(self.getX()+distance*Math.sin(absoluteBearingRadians),
                self.getY()+distance*Math.cos(absoluteBearingRadians));
        velocity = event.getVelocity();
        energy = event.getEnergy();
    }

}
