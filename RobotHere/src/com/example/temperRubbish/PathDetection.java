package com.example.temperRubbish;

import robocode.AdvancedRobot;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by temper on 2017/7/23,下午10:59.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class PathDetection {



    //是否按照一定姿势（这里我们只是实现了直线[此处包括不动]和圆周运动，其他待开发。。。主要是不会）运动

    public  int isOrganized(List<EnemyTank> path){
        if(new StablePathDetection().isStable(path))
            return 1;
        else if(new LinePathDetection().isLineOrganized(path))
            return 2;
        else if (new RoundPathDetection().isRoundOrganized(path))
            return 3;

        else return 0;
    }

    public Coordination predictPath(List<EnemyTank> path, AdvancedRobot robot){
        return null;
    }







}
