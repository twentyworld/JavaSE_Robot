package com.example.temperRubbish.pathDetection;

import com.example.temperRubbish.Coordination;
import com.example.temperRubbish.EnemyTank;
import com.example.temperRubbish.PathDetection;
import com.example.temperRubbish.TemperRubbish;
import robocode.AdvancedRobot;

import java.util.List;

/**
 * Created by teemper on 2017/7/24, 20:19.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class RoundPathDetection extends PathDetection {
    //如果是圆周运动，初始化这两个变量
    Coordination coordination;
    double radius;


    @Override
    public Coordination predictPath(List<EnemyTank> path, TemperRubbish robot) {
        return new Coordination();
    }

    //判断是否做得是圆周运动
    public static boolean isRoundOrganized(List<EnemyTank> path){
        return false;
    }

}
