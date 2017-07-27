//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package robot.model;

import robot.util.RadianUtil;

public class RobotTrace {
    public static final int DISTANCE = 1;
    public static final int RADIAN = 2;
    public static final int FIX_LOCATION = 3;
    public static final int TIMES = 50;
    public String name;
    public RobotModel current;
    public boolean isRegular = false;
    public int regularType = -1;
    public int size = 0;
    public double maxWidth = 0.0D;
    public double maxHeight = 0.0D;
    public double minWidth = 0.0D;
    public double minHeight = 0.0D;
    public boolean isDeath = false;

    public RobotTrace(double minWidth, double minHeight, double maxWidth, double maxHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public void add(RobotModel m) {
        if (this.current != null) {
            RobotModel next = this.current.next;
            this.current.next = m;
            m.pre = this.current;
            m.next = next;
            next.pre = m;
            this.current = m;
            ++this.size;
        } else {
            this.current = m;
        }

    }

    public void check() {
        long time = this.current.time;
        RobotModel checkPoint = this.current;
        double radiansOffset = 0.0D;
        double radians2Offset = 0.0D;
        boolean firstRound = true;
        int radianSize = 0;
        int distanceSize = 0;

        while(checkPoint.pre.time != time) {
            double cX = checkPoint.x - checkPoint.pre.x;
            double cY = checkPoint.y - checkPoint.pre.y;
            double cR = RadianUtil.getRadian(cX, cY);
            checkPoint = checkPoint.pre;
            if (firstRound) {
                radiansOffset = cR;
                firstRound = false;
            } else {
                double rO = cR - radiansOffset;
                if (radians2Offset == 0.0D) {
                    radians2Offset = rO;
                }

                if (cR == radiansOffset) {
                    if (Math.abs(cR) == 0.3D) {
                        ++radianSize;
                        ++distanceSize;
                    } else {
                        ++distanceSize;
                    }
                } else if (radians2Offset == rO) {
                    ++radianSize;
                }

                radians2Offset = rO;
            }
        }

        if (distanceSize > radianSize) {
            this.regularType = 1;
        } else {
            this.regularType = 2;
        }

        while(this.current.time != time) {
            this.current = this.current.next;
        }

    }

    public Destination predict() {
        if (this.size % 50 == 0) {
            this.check();
        }

        double predictX = -1234.0D;
        double predictY = -1234.0D;
        long t = -1L;
        switch(this.regularType) {
        case 1:
            t = this.current.time - this.current.pre.time;
            predictX = 2.0D * this.current.x - this.current.pre.x;
            predictY = 2.0D * this.current.y - this.current.pre.y;
            break;
        case 2:
            t = this.current.time - this.current.pre.time;
            double r1 = RadianUtil.getRadian(this.current.x - this.current.pre.x, this.current.y - this.current.pre.y);
            double r2 = RadianUtil.getRadian(this.current.pre.x - this.current.pre.pre.x, this.current.pre.y - this.current.pre.pre.y);
            double offset = (2.0D * r2 - r1) % 360.0D;
            predictX = this.current.x + Math.cos(RadianUtil.getRadianPI(offset));
            predictY = this.current.y + Math.sin(RadianUtil.getRadianPI(offset));
        }

        if (predictX != -1234.0D) {
            if (predictX > this.maxWidth) {
                predictX = this.maxWidth;
            } else if (predictX < this.minWidth) {
                predictX = this.minWidth;
            }
        }

        if (predictY != -1234.0D) {
            if (predictY > this.maxHeight) {
                predictY = this.maxHeight;
            } else if (predictY < this.minHeight) {
                predictY = this.minHeight;
            }
        }

        Destination ret = new Destination();
        ret.t = t;
        ret.x = predictX;
        ret.y = predictY;
        return ret;
    }
}
