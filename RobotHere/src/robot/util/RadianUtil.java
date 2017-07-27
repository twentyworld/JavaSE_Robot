//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package robot.util;

public class RadianUtil {
    public RadianUtil() {
    }

    public static double getRadian(double x, double y) {
        if (x == 0.0D && y == 0.0D) {
            return 0.0D;
        } else {
            double distance = Math.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D));
            double sin = y / distance;
            double radian = Math.asin(Math.abs(sin));
            if (x < 0.0D || y < 0.0D) {
                if (x < 0.0D && y >= 0.0D) {
                    radian = 3.141592653589793D - radian;
                } else if (x < 0.0D && y < 0.0D) {
                    radian += 3.141592653589793D;
                } else {
                    radian = 6.283185307179586D - radian;
                }
            }

            return radian * 180.0D / 3.141592653589793D;
        }
    }

    public static double getRadianPI(double r) {
        return r * 3.141592653589793D / 180.0D;
    }

    public static double getVelocity(double cX, double cY, double dX, double dY, long t) {
        double distance = Math.sqrt(Math.pow(cX - dX, 2.0D) + Math.pow(cY - dY, 2.0D));
        return t != 0L ? distance / (double)t : 0.0D;
    }

    public static double getRadianInSystem(double radian) {
        return (360.0D - (radian - 90.0D)) % 360.0D;
    }

    public static double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D));
    }
}
