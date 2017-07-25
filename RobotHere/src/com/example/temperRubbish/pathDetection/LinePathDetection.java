package com.example.temperRubbish.pathDetection;

import com.example.temperRubbish.*;
import robocode.AdvancedRobot;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by teemper on 2017/7/24, 20:19.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class LinePathDetection extends PathDetection {
    int a,b,c;


    @Override
    public Coordination predictPath(List<EnemyTank> path, TemperRubbish robot) {
        return getCoordinationByTime(path,calculatePredictTank(path,robot));
    }

    //判断是否做得是直线运动。sp：这里要做出v不为0的假设，v是0的话，是stable
    //单独列出v是0的原因，v要作为分母。
    public static boolean isLineOrganized(List<EnemyTank> path){
        int length = path.size();
        if(length<4)
            return false;
        for(int i =length-1;i>1&&i+5>length;i--){
            if(path.get(i).getVelocity()==0)
                return false;
            if(Utils.calculateVectorRadian(
                    Utils.calculateVector(path.get(length-1).getCoordination(),path.get(i-1).getCoordination()),
                    Utils.calculateVector(path.get(length-1).getCoordination(),path.get(i-2).getCoordination()))
                    >0.01)
                return false;
        }
        System.out.println("检测到是直线"+"is a line!");
        return true;
    }

    //计算经过多长时间，在什么位置，子弹和敌方坦克相遇。
    public int calculatePredictTank(List<EnemyTank> path, TemperRubbish temper){
        if (isLineOrganized(path))
            return -1;
        if (path.get(path.size()-1).getVelocity()==0)
            return 0;

        double requiredTime;
        double mostMatchedTime = Integer.MAX_VALUE;
        Coordination goalCoordination;
        int time = 0;
        for(int  t = 1; t < 150; t++) {
            goalCoordination = getCoordinationByTime(path,t);
            if(!Utils.isUnderArea(goalCoordination,temper.getMaxCoordination()))
                continue;
            double distance = Utils.calculateDistance(new Coordination(temper.getX(),temper.getY()),goalCoordination);
            double speed = 20-3* (temper.getPower(distance));
            requiredTime = distance/speed;

            if(mostMatchedTime<requiredTime){
                mostMatchedTime = requiredTime;
                time = t;
                if(mostMatchedTime<0.05) return time;
            }
        }

        if (mostMatchedTime<0.3) return time;
        return -1;
    }

    //给定一个时间，返回一个直线运动情况下的点。
    public Coordination getCoordinationByTime(List<EnemyTank> path,int time){
        EnemyTank startTank = path.get(path.size()-4);
        EnemyTank latestTank = path.get(path.size()-1);
        Coordination goalCoordination = new Coordination();

        double x = time*(latestTank.getCoordination().getX()-startTank.getCoordination().getX())
                /(latestTank.getTime()-startTank.getTime())
                +latestTank.getCoordination().getX();
        goalCoordination.setX(x);
        double y = time*(latestTank.getCoordination().getY()-startTank.getCoordination().getY())
                /(latestTank.getTime()-startTank.getTime())
                +latestTank.getCoordination().getY();
        goalCoordination.setY(y);
        return goalCoordination;
    }




    //当前我们已知是一条直线的情况下，可以获取一条直线获取关于时间t的函数，返回的是目标tank所在的地点。



//    //给定x，给出y。
//    private boolean getLine(List<EnemyTank> path){
//        int length = path.size();
//        //if()
//
//        return false;
//    }
//    //计算两点生成的直线，这里是简单的根据两点固定一直线。如果有时间，考虑用最小二乘法，拟合直线。
//    //返回值的Point2D.Double 中的x表示的直线a，y->b.
//    private Point2D.Double calculateLine(Coordination c1, Coordination c2){
//
//        if(c1.getX()==c2.getX()){}
//        return new Point2D.Double(0,0);
//    }

}
