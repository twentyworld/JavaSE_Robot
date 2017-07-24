package com.example.temperRubbish;

import robocode.AdvancedRobot;

import java.util.List;

/**
 * Created by teemper on 2017/7/24, 20:19.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class RoundPathDetection extends PathDetection{
    //如果是圆周运动，初始化这两个变量
    Coordination coordination;
    double radius;


    @Override
    public Coordination predictPath(List<EnemyTank> path, AdvancedRobot robot) {
        return super.predictPath(path, robot);
    }

    //判断是否做得是圆周运动
    public boolean isRoundOrganized(List<EnemyTank> path){
        return false;
    }

}
