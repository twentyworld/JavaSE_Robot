package com.example.temperRubbish;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by temper on 2017/7/23,下午10:59.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class PathDetection {


    //是否按照一定姿势（这里我们只是实现了直线和圆周运动，其他待开发。。。主要是不会）运动
    public  int isOrganized(List<EnemyTank> path){
        if(isLineOrganized(path))
            return 1;
        else if (isRoundOrganized(path))
            return 2;
        else return 0;
    }

    private boolean isLineOrganized(List<EnemyTank> path){

        int length = path.size();
        for(int i =length-1;i>0&&i+10>length;i--){

        }
        return false;
    }

    private boolean isRoundOrganized(List<EnemyTank> path){
        return false;
    }

    private boolean isStable(List<EnemyTank> path){

        int length = path.size();
        for(int i = length-1;i>0&&i+10>length;i--){
            if(!path.get(i).equals(path.get(i-1)))
                return false;
        }
        return true;
    }
    private boolean getLine(List<EnemyTank> path){
        int length = path.size();
        //if()

        return false;
    }
    private Point2D.Double calculateLine(Coordination c1, Coordination c2){

        if(c1.getX()==c2.getX()){}
        return new Point2D.Double(2,3);
    }

    private double calculateDistance(Coordination coordination1,Coordination coordination2){
        return Math.sqrt(Math.pow(coordination2.getX()-coordination1.getX(),2)+
            Math.pow(coordination1.getY()-coordination2.getY(),2));
    }

}
