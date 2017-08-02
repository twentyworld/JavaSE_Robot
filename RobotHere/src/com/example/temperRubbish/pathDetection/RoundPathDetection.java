package com.example.temperRubbish.pathDetection;

import com.example.temperRubbish.Coordination;
import com.example.temperRubbish.EnemyTank;
import com.example.temperRubbish.PathDetection;
import com.example.temperRubbish.TemperRubbish;
import com.example.temperRubbish.Util.TemperUtils;

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
    private static Coordination coordination = new Coordination();
    private static double radius = 0;


    @Override
    public Coordination predictPath(List<EnemyTank> path, TemperRubbish robot) {
        return getCoordinationByTime(path,calculatePredictTank(path,robot));
    }

    private double calculatePredictTank(List<EnemyTank> path, TemperRubbish temper){
        if (path.size()<5) return -1;
        if (path.get(path.size()-1).getVelocity()==0) return 0;
        double mostMatchedTime = 200.00d;
        Coordination goalCoordination;
        double time = 0;
        for(double t = 0.5; t < 150; t=t+0.2) {
            goalCoordination = getCoordinationByTime(path,t);
            if(!TemperUtils.isUnderArea(goalCoordination)) return -1;
            //System.out.println(t);
            double distance = TemperUtils.calculateDistance(new Coordination(temper.getX(),temper.getY()),goalCoordination);
            //System.out.println(distance+"距离");
            double speed = 20-3* (temper.getPower(distance));
            //System.out.println(Math.abs(distance/speed-t)+"预测时间差"+t+"前行时间");
            if(mostMatchedTime>(Math.abs(distance/speed-t))){
                mostMatchedTime=(Math.abs(distance/speed-t));
                time = t;
            }
            //mostMatchedTime = mostMatchedTime<(Math.abs(distance/speed-t))?(Math.abs(distance/speed-t)):mostMatchedTime;
            //System.out.println(mostMatchedTime);
            if(mostMatchedTime<0.005) return time;
        }
        //System.out.println(time+"返回的时间点。");
        if (mostMatchedTime<0.1) return time;
        return -1;
    }

    //给定一个时间，返回一个圆周运动情况下的点。
    private Coordination getCoordinationByTime(List<EnemyTank> path, double time) {
        if (path.size() < 5) return null;
        if (time<=0) return null;
        //Coordination[] enemyTanksCoordination = new Coordination[3];
        //double[] result = cramer(generateDeterminant(enemyTanksCoordination));
        EnemyTank start = path.get(path.size()-5);
        EnemyTank end = path.get(path.size()-1) ;

        double radianEnd=TemperUtils.calculateVectorIntersectionRadian(TemperUtils.calculateVector(coordination,end.getCoordination()));
        double radianStart=TemperUtils.calculateVectorIntersectionRadian(TemperUtils.calculateVector(coordination,start.getCoordination()));

        double predictRadian = (radianEnd-radianStart)*time/(end.getTime()-start.getTime());
        return new Coordination(coordination.getX()+radius*Math.cos(predictRadian),coordination.getY()+radius*Math.sin(predictRadian));
    }

    //判断是否做得是圆周运动

    /**
     * 无非就是矩阵运算。求解三元一次方程，确定圆的半径和圆心。<br/>
     * 但是很傻比的是，存在误差值。<br/>
     *
     * @param path-->就是记录的所有的地方坦克的信息
     * @return boolean
     */
    public static boolean isRoundOrganized(List<EnemyTank> path) {
        if (path.size() < 5) return false;

        for (int i = 0; i < 2; i++) {
            Coordination[] enemyTanksCoordination1 = new Coordination[3];
            Coordination[] enemyTanksCoordination2 = new Coordination[3];
            for (int j = 0; j < 3; j++) {
                enemyTanksCoordination1[j] = path.get(path.size() - 1 - i - (2 - j)).getCoordination();
                enemyTanksCoordination2[j] = path.get(path.size() - 2 - i - (2 - j)).getCoordination();
            }
            double[] result1 = cramer(generateDeterminant(enemyTanksCoordination1));
            double[] result2 = cramer(generateDeterminant(enemyTanksCoordination2));

            if (calculateRadius(result1) <= 0 || calculateRadius(result2) <= 0) return false;
            if (!isCircleValuable(result1, result2)) return false;
            if(!new Coordination(-result1[0]/2,-result1[1]/2).similar(coordination)){
                coordination = new Coordination(-result1[0]/2,-result1[1]/2);
                radius = Math.sqrt(calculateRadius(result1));
            }
        }
        System.out.println("圆周运动是的。");
        return true;
    }

    /**
     * 计算方程中的半径的平方。<br/>
     * 半径的平方可能为负数，说明圆不存在。
     *
     * @param result 数组
     * @return radius
     */
    private static double calculateRadius(double[] result) {
        return Math.sqrt(-result[2] + (result[0] * result[0] + result[1] * result[1]) / 4);
    }

    private static boolean isCircleValuable(double[] result1, double[] result2) {
        if (!isCircleUseful(result1) || !isCircleUseful(result2)) return false;
        //半径距离差距是否过大
//        System.out.println("**************************************************************************************");
//        System.out.println(Math.abs(calculateRadius(result1)-calculateRadius(result2))/(calculateRadius(result1)+calculateRadius(result2))>0.002);
//        System.out.println(calculateRadius(result1));
//        System.out.println(calculateRadius(result2));
//        System.out.println(Math.abs(calculateRadius(result1)-calculateRadius(result2))/(calculateRadius(result1)+calculateRadius(result2)));
        if (Math.abs(calculateRadius(result1) - calculateRadius(result2)) / (calculateRadius(result1) + calculateRadius(result2))
                > 0.00002)
            return false;
        //圆心差距是否过大
//        System.out.println(new Coordination(-result1[0]/2,-result1[1]/2));
//        System.out.println(new Coordination(-result2[0]/2,-result2[1]/2));
        double CircleCenterDistance = TemperUtils.calculateDistance(
                new Coordination(-result1[0] / 2, -result1[1] / 2),
                new Coordination(-result2[0] / 2, -result2[1] / 2));
//        System.out.println(CircleCenterDistance/(calculateRadius(result1)+calculateRadius(result2)));
//        System.out.println(CircleCenterDistance/(calculateRadius(result1)+calculateRadius(result2))>0.005);
        return !(CircleCenterDistance / (calculateRadius(result1) + calculateRadius(result2)) > 0.00005);

    }

    /**
     * 查看圆是否合法。查看圆心是否偏离过远，半径的平方是否大于零。
     *
     * @param result that going with happiness
     * @return 圆心
     */
    private static boolean isCircleUseful(double[] result) {
        double x = -result[0] / 2;
        double y = -result[1] / 2;

        if (x < -1200 || x > 2100) return false;
        if (y < -1000 || y > 1700) return false;
        if ((-result[2] + (result[0] * result[0] + result[1] * result[1]) / 4) <= 0) return false;
        return true;
    }

    //根据三个点生成行列式。
    private static double[][] generateDeterminant(Coordination[] path) {
        if (path.length!=3) return new double[3][4];

        double[][] determinant = new double[3][4];
        for (int i = 0; i < path.length; i++) {
            determinant[i][0] = path[i].getX();
            determinant[i][1] = path[i].getY();
            determinant[i][2] = 1;
            determinant[i][3] = -(Math.pow(path[i].getX(), 2) + Math.pow(path[i].getY(), 2));
        }
        return determinant;
    }


    //克莱姆法则搞定他之

    /**
     * 标准的克莱姆法则，给我一个[m][m+1]的矩阵，返给你一个多元一次方程的解。解是个数组。对应的是x0,x1,x2.............<br/>
     * 这里面，如果有多个解或者无解，返回的是一个return new double[vecter.length]。就是都是0的数组。<br/>
     *
     * @param vecter
     * @return
     */
    private static double[] cramer(double[][] vecter) {
        for (double[] aVecter : vecter) if (vecter.length + 1!=aVecter.length) return new double[vecter.length];

        //prints(generateDeterminant(vecter,vecter[0].length-1));
        double[][] determinant = generateDeterminant(vecter, vecter[0].length - 1);

        double cramerD = calculateCramer(determinant);
        //System.out.println(cramerD);
        if (cramerD==0) return new double[vecter.length];

        double[] result = new double[vecter.length];
        for (int i = 0; i < result.length; i++) {
            //prints(generateDeterminant(vecter,i));
            result[i] = calculateCramer(generateDeterminant(vecter, i)) / cramerD;
        }
        return result;

    }

    /**
     * 把行列式直接转换成数值。也就是计算行列式的值。<br/>
     * 传入的是行列式，传出的是一个double值。<br/>
     *
     * @param vecter
     * @return
     */
    //这里由于怕麻烦，直接写了三阶的行列式。以后有时间扩充（就是不扩充了，谁爱弄谁弄去）。
    private static double calculateCramer(double[][] vecter) {
        for (double[] aVecter : vecter) if (vecter.length!=aVecter.length && vecter.length!=3) return 0;

        return vecter[0][0] * vecter[1][1] * vecter[2][2] +
                vecter[1][0] * vecter[2][1] * vecter[0][2] +
                vecter[0][1] * vecter[1][2] * vecter[2][0] -
                vecter[0][2] * vecter[1][1] * vecter[2][0] -
                vecter[0][1] * vecter[1][0] * vecter[2][2] -
                vecter[1][2] * vecter[2][1] * vecter[0][0];
    }

    /**
     * 调整行列式，提供给卡莱姆发作运算用。<br/>
     * 多元一次方程中的解，是要进行多次的列的替换的，这个方法就是干这个的。<br/>
     * 传入的是[n][n+1]的矩阵，返回的是一个[n][n]的行列式。<br/>
     *
     * @param vecter
     * @param replace
     * @return
     */

    private static double[][] generateDeterminant(double[][] vecter, int replace) {
        for (double[] aVecter : vecter)
            if (vecter.length + 1!=aVecter.length) return new double[vecter.length][vecter.length];
        if (replace < 0 || replace > vecter[0].length - 1) return new double[vecter.length][vecter.length];

        double[][] determinant = new double[vecter.length][vecter.length];
        for (int i = 0; i < vecter.length; i++)
            System.arraycopy(vecter[i], 0, determinant[i], 0, vecter.length);
        if (replace==vecter[0].length - 1)
            return determinant;
        else {
            for (int i = 0; i < determinant.length; i++)
                determinant[i][replace] = vecter[i][vecter[i].length - 1];
            return determinant;
        }

    }

    public static double getX() {
        return coordination.getX();
    }

    public static void setX(double x) {
        coordination.setX(x);
    }

    public static double getY() {
        return coordination.getY();
    }

    public static void setY(double y) {
        coordination.setY(y);
    }

    public static boolean similar(Object o) {
        return coordination.similar(o);
    }

    /**
     * 传入的是多维数组，也就是多元一次方程的矩阵形式，传出的是结果。<br/>
     * 这个是消元法，菜鸡解法。还有个克莱姆法则<br/>
     * 我好像做出了克莱姆法则，你终将被无视。遗留方法？<br/>
     *
     * @param vecter
     * @return
     */
    private double[] process(double[][] vecter) {
        double[] result = new double[vecter.length];

        for (int i = 0; i < vecter.length - 1; i++) {
            if (vecter[i][i]==0) continue;
            for (int j = i + 1; j < vecter.length; j++) {
                double factor = vecter[j][i] / vecter[i][i];
                for (int k = 0; k < vecter[j].length; k++) {
                    vecter[j][k] = vecter[j][k] - vecter[i][k] * factor;
                    if (Math.abs(vecter[j][k]) < 0.00001) vecter[j][k] = 0;
                }
            }
        }
        for (int i = vecter.length - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = vecter[i].length - 2; j > i; j--)
                sum += result[j] * vecter[i][j];
            result[i] = (vecter[i][vecter[i].length - 1] - sum) / vecter[i][i];
        }
        return result;
    }

    //打印多维数组，测试用
    private static void prints(double[][] vecter) {
        System.out.println("[");
        for (int i = 0; i < vecter.length; i++) {
            System.out.print("  [  ");
            for (int j = 0; j < vecter[i].length; j++) {
                System.out.print(vecter[i][j] + "  ");
            }
            System.out.print("],");
            System.out.println();
        }
        System.out.println("]");
    }
}