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

    /**
     * 无非就是矩阵运算。求解三元一次方程，确定圆的半径和圆心。
     * 但是很傻比的是，存在误差值。
     * @param path
     * @return
     */
    public static boolean isRoundOrganized(List<EnemyTank> path){
        if(path.size()<4)
            return false;

        return false;
    }


    //克莱姆法则搞定他之
    private double[]  cramer(double[][] vecter){
        for (double[] aVecter : vecter) if (vecter.length + 1 != aVecter.length) return new double[vecter.length];

        prints(generateDeterminant(vecter,vecter[0].length-1));
        double[][] determinant = generateDeterminant(vecter,vecter[0].length-1);

        double cramerD = calculateCramer(determinant);
        System.out.println(cramerD);
        if(cramerD==0) return new double[vecter.length];

        double[] result = new double[vecter.length];
        for(int i = 0;i<result.length;i++){
            prints(generateDeterminant(vecter,i));
            result[i] = calculateCramer(generateDeterminant(vecter,i))/cramerD;
        }
        return result;

    }
    //这里由于怕麻烦，直接写了三阶的行列式。以后有时间扩充（就是不扩充了，谁爱弄谁弄去）。
    private double calculateCramer(double[][] vecter){
        for (double[] aVecter : vecter) if (vecter.length != aVecter.length &&vecter.length!=3) return 0;

        return vecter[0][0]*vecter[1][1]*vecter[2][2]+
                vecter[1][0]*vecter[2][1]*vecter[0][2]+
                vecter[0][1]*vecter[1][2]*vecter[2][0]-
                vecter[0][2]*vecter[1][1]*vecter[2][0]-
                vecter[0][1]*vecter[1][0]*vecter[2][2]-
                vecter[1][2]*vecter[2][1]*vecter[0][0];
    }
    //调整行列式，提供给卡莱姆发作运算用。
    private double[][] generateDeterminant(double[][] vecter,int replace){
        for (double[] aVecter : vecter) if (vecter.length + 1 != aVecter.length) return new double[vecter.length][vecter.length];
        if(replace<0||replace>vecter[0].length-1)return new double[vecter.length][vecter.length];

        double[][] determinant = new double[vecter.length][vecter.length];
        for(int i =0;i<vecter.length;i++)
            System.arraycopy(vecter[i], 0, determinant[i], 0, vecter.length);
        if(replace==vecter[0].length-1)
            return determinant;
        else{
            for(int i = 0;i<determinant.length;i++)
                determinant[i][replace] = vecter[i][vecter[i].length-1];
            return determinant;
        }

    }

    /**
     * 传入的是多维数组，也就是多元一次方程的矩阵形式，传出的是结果。
     * 这个是消元法，菜鸡解法。还有个克莱姆法则
     * 我好像做出了克莱姆法则，你终将被无视。遗留方法？
     * @param vecter
     * @return
     */
    private double[]  process(double[][] vecter){
        double[] result = new double[vecter.length];

        for(int i =0;i<vecter.length-1;i++){
            if(vecter[i][i] ==0)continue;
            for(int j = i+1;j<vecter.length;j++){
                double factor = vecter[j][i]/vecter[i][i];
                for(int k = 0;k<vecter[j].length;k++){
                    vecter[j][k] = vecter[j][k]-vecter[i][k]*factor;
                    if(Math.abs(vecter[j][k])<0.00001)  vecter[j][k] = 0;
                }
            }
        }
        for(int i = vecter.length-1;i>=0;i--){
            double sum = 0;
            for(int j = vecter[i].length-2;j>i;j--)
                sum+= result[j]*vecter[i][j];
            result[i] = (vecter[i][vecter[i].length-1]-sum)/vecter[i][i];
        }
        return result;
    }
    //打印多维数组，测试用
    private void prints(double[][] vecter){
        System.out.println("[");
        for(int i =0;i<vecter.length;i++){
            System.out.print("  [  ");
            for(int j =0;j<vecter[i].length;j++){
                System.out.print(vecter[i][j]+"  ");
            }
            System.out.print("],");
            System.out.println();
        }
        System.out.println("]");
    }
    //克莱姆法则求解行列式。
}
