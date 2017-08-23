package com.example.temperRubbish.Util;


import com.example.temperRubbish.entity.Coordination;
import java.awt.geom.Point2D;

/**
 * Created by temper on 2017/7/26,下午1:22.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class TemperUtils {

    public static double PI = Math.PI;

    public static double WIDTH = 800;
    public static double HEIGHT = 600;


    /**
     * 计算两点之间的距离。
     * @param coordination1
     * @param coordination2
     * @return double
     */
    public static double calculateDistance(Coordination coordination1, Coordination coordination2){
        return Math.sqrt(Math.pow(coordination2.getX()-coordination1.getX(),2)+
                Math.pow(coordination1.getY()-coordination2.getY(),2));
    }

    /**
     * 计算出向量，传入的是两个点的坐标。
     * @param coordination1, 原坐标。坐标原点
     * @param coordination2, 要检查的坐标
     * @return 向量
     */
    //计算出向量，传入的是两个点的坐标。
    public static Point2D.Double calculateVector(Coordination coordination1, Coordination coordination2){
        return new Point2D.Double(coordination2.getX()-coordination1.getX(),
                coordination2.getY()-coordination1.getY());
    }

//    public static double calculateRadian(Coordination coordination1, Coordination coordination2){
//        Point2D point = calculateVector(coordination1,coordination2);
//
//
//    }
    /**
     * 计算出向量的角度。
     * 结果在0到2pi之间
     */
    public static double calculateVectorIntersectionRadian(Point2D.Double point){
        double radian = Math.acos(point.x/Math.sqrt(Math.pow(point.x,2)+Math.pow(point.y,2)));
        if(point.y<0)
            radian = PI*2-radian;
        return radian;
    }

    /**
     * 计算向量之间的角度，传入的是两个向量的坐标
     * @param point1
     * @param point2
     * @return
     */
    public static double calculateVectorRadian(Point2D.Double point1, Point2D.Double point2) {
        double cosDegree = (point1.getX() * point2.getX() + point1.getY() * point2.getY()) /
                (Math.sqrt(Math.pow(point1.getX(), 2) + Math.pow(point1.getY(), 2))
                        * Math.sqrt(Math.pow(point2.getX(), 2) + Math.pow(point2.getY(), 2)));
        double radian = Math.acos(cosDegree);
        //约束角度，约束到-PI到PI之间===============》这样基本可以把误差缩小。
        return constrainRadianFromNegativePItoPI(radian);
    }

    /**
     * 约束角度，约束到-PI到PI之间.=====》主要是为了cosA的角度问题的规约。
     * @param radian
     * @return
     */
    public static double constrainRadianFromNegativePItoPI(double radian){

        if(radian<=-Math.PI)
            radian +=Math.PI*2;
        if (radian>Math.PI)
            radian -=Math.PI*2;
        return radian;
    }
    /**
     * 约束角度，约束到0到2PI之间.=====》主要是为了cosA的角度问题的规约。
     * @param radian
     * @return
     */
    public static double constrainRadianFromZeroToDoublePI(double radian){

        if(radian<0)
            radian +=Math.PI*2;
        if (radian>Math.PI*2)
            radian -=Math.PI*2;
        return radian;
    }



    /**                                  ^                                                    <br/>
     *                                   |                                                    <br/>
     *                                   |                                                    <br/>
     *                                   |                                                    <br/>
     *                               360 | 0                                                  <br/>
     *          ^----------------------->|----------------------->                            <br/>
     *          |                        |     绝对角度           |                            <br/>
     *          |                        |                       |                            <br/>
     *          |              <-------- |<--------              |                            <br/>
     *          |              |         | 象限角度|              |                            <br/>
     *          |      PI----->|         |        |<------0      |                            <br/>
     * ---------------------------------------------------------------------->                <br/>
     *          |     -PI----->|         |        |<------0      |                            <br/>
     *          |              |         |        |              |                            <br/>
     *          |              --------->|-------->              |                            <br/>
     *          |                        |                       |                            <br/>
     *           <-----------------------|<----------------------V                            <br/>
     *                                   |                                                    <br/>
     *                                   |                                                    <br/>
     *                                   |                                                    <br/>
     *                                   |                                                    <br/>
     *                                   |                                                    <br/>
     *
     *<br/>
     */
    //转换象限角度到绝对角度。radian角度
    public static double translateQuadrantToHeadingRadian(double radian){
        //先规约到-PI到PI
        radian = constrainRadianFromNegativePItoPI(radian);
        //translate，有点懵逼，测试一下，应该是没错的。        ------------->测试通过。
        if      (radian == -PI) radian = 3*PI/2;
        else if (radian>-PI &&radian<-PI/2) radian = PI/2-radian;
        else if (radian ==-PI/2) radian = PI;
        else if (radian>-PI/2&&radian<0) radian = PI/2-radian;
        else if (radian == 0) radian =PI/2;
        else if (radian>0&&radian<PI/2) radian = PI/2-radian;
        else if (radian==PI/2) radian = 0;
        else if (radian>PI/2&&radian<PI) radian = 5*PI/2-radian;
        else if (radian ==PI) radian = 3*PI/2;
        return radian;
    }

    /**
     * 检查点是否还在区域内部<br/>
     * test if the coordination is useful or in the area;<br/>
     * Coordination1 ->the coordination, Coordination2 ->the area ->the top right corner<br/>
     */
    public static boolean isUnderArea(Coordination coordination1){
        if (coordination1 == null)
            return false;
        if(coordination1.getX()<0||coordination1.getY()<0)
            return false;
        if(coordination1.getX()>WIDTH||coordination1.getY()>HEIGHT)
            return false;
        return true;
    }

}
