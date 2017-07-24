package com.example.temperRubbish;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by temper on 2017/7/23,下午10:59.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class PathDetection {

    //如果是直线运动，初始化这两个变量
    int a,b;
    //如果是圆周运动，初始化这两个变量
    Coordination coordination;
    double radius;

    //是否按照一定姿势（这里我们只是实现了直线和圆周运动，其他待开发。。。主要是不会）运动
    public  int isOrganized(List<EnemyTank> path){
        if(isLineOrganized(path))
            return 1;
        else if (isRoundOrganized(path))
            return 2;
        else return 0;
    }

    //判断是否做得是直线运动。sp：这里要做出v不为0的假设，v是0的话，是stable
    //单独列出v是0的原因，v要作为分母。
    private boolean isLineOrganized(List<EnemyTank> path){

        int length = path.size();
        for(int i =length-1;i>0&&i+10>length;i--){

        }
        return false;
    }

    //判断是否做得是圆周运动
    private boolean isRoundOrganized(List<EnemyTank> path){
        return false;
    }

    //判断是否固定一点不动。
    private boolean isStable(List<EnemyTank> path){

        int length = path.size();
        for(int i = length-1;i>0&&i+10>length;i--){
            if(!path.get(i).equals(path.get(i-1)))
                return false;
        }
        return true;
    }

    //给定x，给出y。
    private boolean getLine(List<EnemyTank> path){
        int length = path.size();
        //if()

        return false;
    }
    //计算两点生成的直线，这里是简单的根据两点固定一直线。如果有时间，考虑用最小二乘法，拟合直线。
    //返回值的Point2D.Double 中的x表示的直线a，y->b.
    private Point2D.Double calculateLine(Coordination c1, Coordination c2){

        if(c1.getX()==c2.getX()){}
        return new Point2D.Double(2,3);
    }

    //计算两点之间的距离。
    private double calculateDistance(Coordination coordination1,Coordination coordination2){
        return Math.sqrt(Math.pow(coordination2.getX()-coordination1.getX(),2)+
            Math.pow(coordination1.getY()-coordination2.getY(),2));
    }

    //计算出向量，传入的是两个点的坐标。
    private Point2D.Double calculateVector(Coordination coordination1,Coordination coordination2){
        return new Point2D.Double(coordination2.getX()-coordination1.getX(),
                coordination2.getY()-coordination1.getY());
    }

    //计算向量之间的角度，传入的是两个向量的坐标
    private double calculateVectorRadian(Point2D.Double point1, Point2D.Double point2) {
        double cosDegree = (point1.getX() * point2.getX() + point1.getY() * point2.getY()) /
                (Math.sqrt(Math.pow(point1.getX(), 2) + Math.pow(point1.getY(), 2)) * Math.sqrt(Math.pow(point2.getX(), 2) + Math.pow(point2.getY(), 2)));
        double radian = Math.acos(cosDegree);
        return constrainRadian(radian);
    }
    //约束角度，约束到-PI到PI之间
    public double constrainRadian(double radian){
        if(radian<-Math.PI)
            radian +=Math.PI*2;
        if (radian>Math.PI)
            radian -=Math.PI*2;
        return radian;
    }


}
