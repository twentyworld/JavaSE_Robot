package com.example.temperRubbish;

import com.example.temperRubbish.pathDetection.LinePathDetection;
import com.example.temperRubbish.pathDetection.RoundPathDetection;
import com.example.temperRubbish.pathDetection.StablePathDetection;
import robocode.AdvancedRobot;

import java.util.List;

/**
 * Created by temper on 2017/7/23,下午10:59.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public abstract class PathDetection {


    //是否按照一定姿势（这里我们只是实现了直线[此处包括不动]和圆周运动，其他待开发。。。主要是不会）运动
    public  boolean isOrganized(List<EnemyTank> path){
        if (getPathDetection(path) == null)
            return false;
        return true;
    }

    public abstract Coordination predictPath(List<EnemyTank> path, TemperRubbish robot);

    public PathDetection getPathDetection(List<EnemyTank> path){
        if (StablePathDetection.isStable(path))
            return new StablePathDetection();
        else if (LinePathDetection.isLineOrganized(path))
            return new LinePathDetection();
        else if (RoundPathDetection.isRoundOrganized(path))
            return new RoundPathDetection();
        else return null;
    }






}
