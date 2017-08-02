package com.example.temperRubbish.pathDetection;

import com.example.temperRubbish.Coordination;
import com.example.temperRubbish.EnemyTank;
import com.example.temperRubbish.PathDetection;
import com.example.temperRubbish.TemperRubbish;
import com.example.temperRubbish.Util.TemperUtils;

import java.util.List;

/**
 * Created by teemper on 2017/8/2, 22:54.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class RegularPathDetection extends PathDetection {


    @Override
    public Coordination predictPath(List<EnemyTank> path, TemperRubbish robot) {
        int length = path.size();
        int flash = 0;
        for(int i = length-4;i>1;i--){
            if(path.get(length-1).getCoordination().similar(path.get(i).getCoordination())&&
                    path.get(length-2).getCoordination().similar(path.get(i-1).getCoordination())&&
                    path.get(length-3).getCoordination().similar(path.get(i-2).getCoordination())
                    ){ flash = i;break;
            }
        }

        double mostMatchedTime =200;
        int mostMatchedFlash = 0;
        for(int i = flash;i<length;i++){
            double distance = TemperUtils.calculateDistance(new Coordination(robot.getX(),robot.getY()), path.get(i).getCoordination());
            double speed = 20-3* (robot.getPower(distance));
            //时间差
            double delay =Math.abs(distance/speed-(path.get(i).getTime()-path.get(flash).getTime()));
            if(mostMatchedTime>delay){
                mostMatchedTime=delay;
                mostMatchedFlash = i;
                if (delay<0.005) return path.get(i).getCoordination();
            }
        }
        if (mostMatchedTime<0.2)
            return path.get(mostMatchedFlash).getCoordination();
        return new Coordination(1000,1000);
    }
    public static boolean isRegualrOrganized(List<EnemyTank> path){
        if(path.size()<6) return false;
        int length = path.size();
        for(int i = length-5;i>0;i--){
            if(path.get(length-1).getCoordination().similar(path.get(i+2).getCoordination())&&
                path.get(length-2).getCoordination().similar(path.get(i+1).getCoordination())&&
                path.get(length-3).getCoordination().similar(path.get(i).getCoordination())
                ){
                System.out.println("是规则的");
                return true;
            }
        }
        return false;
    }


}
