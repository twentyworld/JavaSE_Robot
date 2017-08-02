package com.example.good;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Created by temper on 2017/7/23,下午3:45.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class TestTank extends AdvancedRobot {

    @Override
    public void run() {

        while(true){
            setFire(1);

            turnGunLeft(360);
            execute();
        }
    }

}
