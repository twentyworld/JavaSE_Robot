package com.example.temperRubbish;

import robocode.AdvancedRobot;

import java.util.List;

/**
 * Created by teemper on 2017/7/24, 20:25.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class StablePathDetection extends PathDetection{

    Coordination point;
    //判断是否固定不动。

    @Override
    public Coordination predictPath(List<EnemyTank> path, AdvancedRobot robot) {
        return super.predictPath(path, robot);
    }

    public boolean isStable(List<EnemyTank> path){
        int length = path.size();
        for(int i = length-1;i>=0&&i+4>length;i--){
            if(!path.get(i).equals(path.get(i-1)))
                return false;
        }
        this.point = new Coordination(path.get(length-1).getCoordination().getX(),path.get(length-1).getCoordination().getY());

        return true;
    }

}
