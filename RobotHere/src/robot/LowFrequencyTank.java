//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package robot;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robot.model.Destination;
import robot.model.Point;
import robot.model.RobotModel;
import robot.model.RobotTrace;
import robot.util.RadianUtil;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class LowFrequencyTank extends AdvancedRobot {
    private Map<String, RobotTrace> traceMap = new HashMap();
    private boolean front = true;
    private double bodyLength = 0.0D;
    private double maxBattleWidth = 0.0D;
    private double maxBattleHeight = 0.0D;

    public LowFrequencyTank() {
    }

    public void run() {
        this.maxBattleWidth = this.getBattleFieldWidth();
        this.maxBattleHeight = this.getBattleFieldHeight();
        if (this.bodyLength == 0.0D) {
            this.bodyLength = RadianUtil.getDistance(this.getWidth(), this.getHeight());
        }

        this.setAdjustGunForRobotTurn(false);
        this.setAdjustRadarForGunTurn(true);
        this.setAdjustRadarForRobotTurn(false);
        this.setColors(Color.BLACK, Color.BLUE, Color.CYAN);

        while(true) {
            this.execute();
            this.setTurnRadarLeftRadians(1.0D / 0.0);
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        String name = event.getName();
        this.out.println("Name=" + name);
        RobotTrace trace = (RobotTrace)this.traceMap.get(name);
        if (trace == null) {
            trace = new RobotTrace(0.0D, 0.0D, this.maxBattleWidth, this.maxBattleHeight);
            this.traceMap.put(name, trace);
        }

        double angle = Math.toRadians((this.getHeading() + event.getBearing()) % 360.0D);
        double scannedX = this.getX() + Math.sin(angle) * event.getDistance();
        double scannedY = this.getY() + Math.cos(angle) * event.getDistance();
        RobotModel model = new RobotModel();
        model.time = event.getTime();
        model.velocity = event.getVelocity();
        model.x = scannedX;
        model.y = scannedY;
        model.energy = event.getEnergy();
        model.distance = event.getDistance();
        trace.add(model);
        double gunHeading = this.getGunHeading();
        double cX = this.getX();
        double cY = this.getY();
        Destination des = trace.predict();
        double offset = 0.0D;
        double distance;
        if (des.x != -1234.0D) {
            distance = RadianUtil.getRadianInSystem(RadianUtil.getRadian(cX - des.x, cY - des.y));
            offset = distance - gunHeading + 540.0D;
        } else {
            distance = RadianUtil.getRadianInSystem(RadianUtil.getRadian(cX - scannedX, cY - scannedY));
            offset = distance - gunHeading + 540.0D;
        }

        distance = RadianUtil.getDistance(cX - scannedX, cY - scannedY);
        double bulletEnergy = (1.0D - distance / RadianUtil.getDistance(this.maxBattleWidth, this.maxBattleHeight)) * 3.0D;
        offset %= 360.0D;
        if (offset < 180.0D) {
            this.turnGunRight(offset);
        } else {
            this.turnGunLeft(360.0D - offset);
        }

        this.fire(bulletEnergy);
        this.escape();
        System.out.println("Name:" + event.getName() + ", distance=" + RadianUtil.getDistance(scannedX - cX, scannedY - cY) + ", fire:" + bulletEnergy);
    }

    public void onRobotDeath(RobotDeathEvent event) {
        RobotTrace trace = (RobotTrace)this.traceMap.get(event.getName());
        if (trace != null) {
            trace.isDeath = true;
        }

    }

    private void escape() {
        Map<Integer, Double> blockMap = new HashMap();
        Map<Integer, Double> distanceMap = new HashMap();

        for(int i = 1; i <= 9; ++i) {
            blockMap.put(i, 0.0D);
            distanceMap.put(i, 0.0D);
        }

        RobotModel nearby = null;
        Iterator var5 = this.traceMap.entrySet().iterator();

        while(true) {
            RobotModel model;
            do {
                RobotTrace trace;
                int i;
                do {
                    Entry entry;
                    if (!var5.hasNext()) {
                        var5 = blockMap.entrySet().iterator();

                        int cy;
                        while(var5.hasNext()) {
                            entry = (Entry)var5.next();
                            double sum = 0.0D;
                            int cx = ((Integer)entry.getKey()).intValue() % 3;
                            cy = (((Integer)entry.getKey()).intValue() - 1) / 3 + 1;
                            if (cx == 0) {
                                cx = 3;
                            }

                            for(i = 1; i <= 9; ++i) {
                                int x = i % 3;
                                int y = (i - 1) / 3 + 1;
                                if (x == 0) {
                                    x = 3;
                                }

                                double energy = ((Double)blockMap.get(i)).doubleValue();
                                sum += energy * RadianUtil.getDistance((double)(cx - x), (double)(cy - y));
                                if (i == ((Integer)entry.getKey()).intValue()) {
                                    ;
                                }
                            }

                            distanceMap.put((Integer)entry.getKey(), sum);
                        }

                        double maxEnergy = -5000.0D;
                        int block = 0;
                        int currentBlock = this.getBlockNum(this.getX(), this.getY());
                        Iterator var53 = distanceMap.entrySet().iterator();

                        while(var53.hasNext()) {
                            Entry<Integer, Double> entrys = (Entry)var53.next();
                            if (((Integer)entrys.getKey()).intValue() != 5 && ((Integer)entrys.getKey()).intValue() != currentBlock && maxEnergy < ((Double)entrys.getValue()).doubleValue()) {
                                maxEnergy = ((Double)entrys.getValue()).doubleValue();
                                block = ((Integer)entrys.getKey()).intValue();
                            }
                        }

                        Random rand = new Random();
                        cy = block % 3;
                        if (cy == 0) {
                            cy = 3;
                        }

                        double x = this.getX();
                        double y = this.getY();
                        int yP = (block - 1) / 3;
                        double xDes = rand.nextDouble() * this.maxBattleWidth / 3.0D + (double)(cy - 1) * this.maxBattleWidth / 3.0D;
                        double yDes = rand.nextDouble() * this.maxBattleHeight / 3.0D + (double)yP * this.maxBattleHeight / 3.0D;
                        if (xDes < 0.0D) {
                            xDes = 40.0D;
                        } else if (xDes > this.maxBattleWidth) {
                            xDes = this.maxBattleWidth - 40.0D;
                        }

                        if (yDes < 0.0D) {
                            yDes = 40.0D;
                        } else if (yDes > this.maxBattleHeight) {
                            yDes = this.maxBattleHeight - 40.0D;
                        }

                        double spaceOffset = 0.0D;
                        double a = 0.0D;
                        double b = 0.0D;
                        double a2;
                        double b2;
                        if (xDes != x) {
                            a2 = (yDes - y) / (xDes - x);
                            b2 = yDes - a2 * xDes;
                            spaceOffset = nearby.y - (a2 * nearby.x + b2);
                            a = a2;
                            b = -1.0D;
                        } else {
                            spaceOffset = nearby.x - x;
                            a = 1.0D;
                            b = 0.0D;
                        }

                        if (Math.abs(spaceOffset) < this.bodyLength) {
                            if (nearby.distance < this.bodyLength * 3.0D) {
                                a2 = 0.0D;
                                b2 = 0.0D;
                                double c2 = 0.0D;
                                if (a == 0.0D) {
                                    a2 = 1.0D;
                                    b2 = 0.0D;
                                    c2 = -1.0D * x;
                                } else {
                                    a2 = b / a;
                                    b2 = -1.0D;
                                    c2 = y - b * x / a;
                                }

                                if (b2 == 0.0D) {
                                    this.moveTo(x, y, x, yDes);
                                } else if (a2 == 0.0D) {
                                    this.moveTo(x, y, xDes, y);
                                } else {
                                    double y1 = -1.0D * c2 / b2;
                                    double y2 = -1.0D * a2 * this.maxBattleWidth / b2 - c2 / b2;
                                    double x3 = -1.0D * c2 / a2;
                                    double x4 = -1.0D * b2 * this.maxBattleHeight / a2 - c2 / a2;
                                    Point p1 = new Point(0.0D, y1);
                                    Point p2 = new Point(this.maxBattleWidth, y2);
                                    Point p3 = new Point(x3, 0.0D);
                                    Point p4 = new Point(x4, this.maxBattleHeight);
                                    List<Point> pointList = new ArrayList();
                                    pointList.add(p1);
                                    pointList.add(p2);
                                    pointList.add(p3);
                                    pointList.add(p4);
                                    Point farP = this.getFarPoint(pointList, x, y);
                                    if (farP == null) {
                                        System.out.println("It's impossible!");
                                    } else {
                                        this.moveTo(x, y, farP.x, farP.y);
                                    }
                                }

                                this.moveTo(this.getX(), this.getY(), xDes, yDes);
                            } else if (spaceOffset < 0.0D) {
                                this.moveTo(x, y, x, yDes);
                                this.moveTo(this.getX(), this.getY(), xDes, this.getY());
                            } else {
                                this.moveTo(x, y, xDes, y);
                                this.moveTo(this.getX(), this.getY(), this.getX(), yDes);
                            }
                        } else {
                            this.moveTo(this.getX(), this.getY(), xDes, yDes);
                        }

                        return;
                    }

                    entry = (Entry)var5.next();
                    trace = (RobotTrace)entry.getValue();
                    model = trace.current;
                } while(trace.isDeath);

                Double x = model.x;
                Double y = model.y;
                i = this.getBlockNum(x, y);
                Double energy = (Double)blockMap.get(i);
                energy = energy.doubleValue() + model.energy;
                blockMap.put(i, energy);
            } while(nearby != null && nearby.distance <= model.distance);

            nearby = model;
        }
    }

    private void moveTo(double x, double y, double xDes, double yDes) {
        double heading = this.getHeading();
        double radian = RadianUtil.getRadianInSystem(RadianUtil.getRadian(xDes - x, yDes - y));
        double offset = radian - heading + 360.0D;
        offset %= 360.0D;
        double distance = RadianUtil.getDistance(xDes - x, yDes - y);
        if (offset < 180.0D) {
            if (offset > 90.0D) {
                offset = 180.0D - offset;
                this.turnLeft(offset);
                this.back(distance);
                this.front = false;
            } else {
                this.turnRight(offset);
                this.ahead(distance);
                this.front = true;
            }
        } else {
            offset = 360.0D - offset;
            if (offset > 90.0D) {
                offset = 180.0D - offset;
                this.turnRight(offset);
                this.back(distance);
                this.front = false;
            } else {
                this.turnLeft(offset);
                this.ahead(distance);
                this.front = true;
            }
        }

    }

    private Point getFarPoint(List<Point> list, double x, double y) {
        double distance = 0.0D;
        Point ret = null;
        Iterator var10 = list.iterator();

        while(var10.hasNext()) {
            Point point = (Point)var10.next();
            if (point.x >= 0.0D && point.x <= this.maxBattleWidth && point.y >= 0.0D && point.y <= this.maxBattleHeight) {
                double cDis = RadianUtil.getDistance(x - point.x, y - point.y);
                if (cDis > distance) {
                    distance = cDis;
                    ret = point;
                }
            }
        }

        return ret;
    }

    public void onHitRobot(HitRobotEvent event) {
        double angle = Math.toRadians((this.getHeading() + event.getBearing()) % 360.0D);
        this.go2fire(angle);
        System.out.println("Hit Fire! Max power!");
        double fixedDistance = 200.0D;
        if (this.front) {
            this.back(fixedDistance);
        } else {
            this.ahead(fixedDistance);
        }

        this.escape();
    }

    private int getBlockNum(Double x, Double y) {
        int base = 1;
        int multiply = 0;

        if(x < maxBattleWidth / 3) {

            base = 1;

        } else if(x < maxBattleWidth * 2 / 3) {

            base = 2;

        } else {

            base = 3;

        }

        if(y < maxBattleHeight / 3) {

            multiply = 0;

        } else if(y < maxBattleHeight * 2 / 3) {

            multiply = 1;

        } else {

            multiply = 2;

        }

        int cb = base + 3 * multiply;

        return cb;

    }

    private void go2fire(double radian) {
        double gunHeading = this.getGunHeading();
        double offset = Math.toDegrees(radian) - gunHeading + 360.0D;
        offset %= 360.0D;
        if (offset < 180.0D) {
            this.turnGunRight(offset);
        } else {
            this.turnGunLeft(360.0D - offset);
        }

        this.fire(3.0D);
        this.fire(3.0D);
        this.fire(3.0D);
    }
}
