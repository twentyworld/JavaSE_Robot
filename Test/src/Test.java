import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by w on 2017/7/14.
 */
public class Test {
    public static void main(String[] args){
        System.out.println("this----------------");
        List<String> list = new ArrayList<>();
        list.add("qwe");
        list.add("wer");
        list.remove(1);
        System.out.println(Arrays.asList(list.toArray()).toString());


        System.out.println(Math.atan(1));
        //System.out.println(Math.sin(Math.atan(/1.732)));

        System.out.println(Math.asin(1));


        Integer i = 5;
        //int j = 5;
        Integer j = 5;
        System.out.println(i==j);
        Integer i1 = 500;
        Integer j1 = 500;
        System.out.println(i1==j1);
    }

    //根据目前已知的信息，目前所在，角度，速度，自身子弹的速度
    // 可以求出的是在t秒之后，子弹和enmey都会到达某一个点，这求出的是t时间。这样可以省去很多的数学计算的严谨性带来的多向性。
    //v1==> enemy vic,v2 the bullet.
//    public static double getPoints(Coordination coor, double degree, double v1, double v2){
//
//        double distance = Math.sqrt(coor.getX()*coor.getX()+coor.getY()*coor.getY());
//
//        double radianDegree = Math.toRadians(degree);
//        //cal the degree by vector  -->this vector.
//
//        //根据向量求夹角
//        //that is (cos1*cos2+sin1*sin2)/(1*1+2*2)=cos degree
//        double cosDegree = (Math.cos(coor.getX()/distance)*Math.cos(radianDegree)+
//                Math.sin(coor.getY()/distance)*Math.sin(radianDegree))/Math.sqrt(1+1);
//
//        //求解二元一次方程。。。。。。。麻烦的过分了。。。。。
//        double a = Math.sqrt(v1*v1-v2*v2);
//        double b = 2*v1*distance*cosDegree;
//        double c = distance*distance;
//        //就实际情况而言，-的应该是负数，一般不会是正数，如果是，方程有问题。这里只要+的，
//        double t1 = (b+Math.sqrt(b*b+4*a*c))/(2*a);
//        return t1;
//
//    }
}
