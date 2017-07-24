package com.example.temperRubbish;

import java.awt.geom.Point2D;

/**
 * Created by teemper on 2017/7/24, 20:14.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class Utils {

    public static double PI = Math.PI;
    //计算两点之间的距离。
    public static double calculateDistance(Coordination coordination1,Coordination coordination2){
        return Math.sqrt(Math.pow(coordination2.getX()-coordination1.getX(),2)+
                Math.pow(coordination1.getY()-coordination2.getY(),2));
    }
    //计算两点之间的绝对bearing.


    //计算出向量，传入的是两个点的坐标。
    public static Point2D.Double calculateVector(Coordination coordination1, Coordination coordination2){
        return new Point2D.Double(coordination2.getX()-coordination1.getX(),
                coordination2.getY()-coordination1.getY());
    }

    //计算向量之间的角度，传入的是两个向量的坐标
    public static double calculateVectorRadian(Point2D.Double point1, Point2D.Double point2) {
        double cosDegree = (point1.getX() * point2.getX() + point1.getY() * point2.getY()) /
                (Math.sqrt(Math.pow(point1.getX(), 2) + Math.pow(point1.getY(), 2)) * Math.sqrt(Math.pow(point2.getX(), 2) + Math.pow(point2.getY(), 2)));
        double radian = Math.acos(cosDegree);
        //约束角度，约束到-PI到PI之间===============》这样基本可以把误差缩小。
        return constrainRadianFromNegativePItoPI(radian);
    }

    //约束角度，约束到-PI到PI之间.=====》主要是为了cosA的角度问题的规约。也可以看做是规约成bearing角度
    public static double constrainRadianFromNegativePItoPI(double radian){
        if(radian<-Math.PI)
            radian +=Math.PI*2;
        if (radian>Math.PI)
            radian -=Math.PI*2;
        return radian;
    }
    /**                                  ^
     *                                   |
     *                                   |
     *                                   |
     *                               360 | 0
     *          ^----------------------->|----------------------->
     *          |                        |     绝对角度           |
     *          |                        |                       |
     *          |              <-------- |<--------              |
     *          |              |         | 象限角  |              |
     *          |      PI----->|         |        |<------0      |
     * ---------------------------------------------------------------------->
     *          |     -PI----->|         |        |<------0    |
     *          |              |         |        |              |
     *          |              --------->|-------->              |
     *          |                        |                       |
     *           <----------------------V|<----------------------V
     *                                   |
     *                                   |
     *                                   |
     *                                   |
     *                                   |
     */

    //转换象限角度到绝对角度。radian角度
    public static double translateQuadrantToHeadingRadian(double radian){
        //先规约到-PI到PI
        radian = constrainRadianFromNegativePItoPI(radian);
        //translate，有点懵逼，测试一下，应该是没错的。
        if      (radian == -PI) radian = 3*PI/2;
        else if (radian>-PI &&radian<-PI/2) radian = PI-radian;
        else if (radian ==-PI/2) radian = PI;
        else if (radian>-PI/2&&radian<0) radian = PI/2-radian;
        else if (radian == 0) radian =PI/2;
        else if (radian>0&&radian<PI/2) radian = PI/2-radian;
        else if (radian==PI/2) radian = 0;
        else if (radian>PI/2&&radian<PI) radian = 5*PI/2-radian;
        else if (radian ==PI) radian = 3*PI/2;
        return radian;
    }

}
