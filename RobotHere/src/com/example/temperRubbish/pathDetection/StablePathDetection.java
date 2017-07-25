package com.example.temperRubbish.pathDetection;

import com.example.temperRubbish.Coordination;
import com.example.temperRubbish.EnemyTank;
import com.example.temperRubbish.PathDetection;
import com.example.temperRubbish.TemperRubbish;
import robocode.AdvancedRobot;

import java.util.List;

/**
 * Created by teemper on 2017/7/24, 20:25.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class StablePathDetection extends PathDetection {

    Coordination point;


    @Override
    public  Coordination predictPath(List<EnemyTank> path, TemperRubbish robot) {
        int length = path.size();
        this.point = new Coordination(path.get(length-1).getCoordination().getX(),path.get(length-1).getCoordination().getY());
        return this.point;

    }

    //判断是否固定不动。
    public static boolean isStable(List<EnemyTank> path){
        int length = path.size();
        for(int i = length-1;i>=0&&i+4>length;i--){
            if(!path.get(i).equals(path.get(i-1)))
                return false;
        }
        return true;
    }


}
