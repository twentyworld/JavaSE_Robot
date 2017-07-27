//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package robot;

import robocode.*;
import robot.model.Destination;
import robot.model.Point;
import robot.model.RobotModel;
import robot.model.RobotTrace;
import robot.util.RadianUtil;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class MichaelStableRobot extends AdvancedRobot {
    private Map<String, RobotTrace> traceMap = new HashMap();
    private boolean front = true;
    private double bodyLength = 0.0D;
    private double maxBattleWidth = 0.0D;
    private double maxBattleHeight = 0.0D;
    private int preBlock = 0;
    private int desBlock = 0;
    private long lastHittedTime = 0L;
    private Point desPoint = null;

    public MichaelStableRobot() {
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
            this.setTurnRadarLeftRadians(1.0D / 0.0);
            this.escape();
            this.execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        String name = event.getName();
        RobotTrace trace = (RobotTrace)this.traceMap.get(name);
        if (trace == null) {
            trace = new RobotTrace(0.0D, 0.0D, this.maxBattleWidth, this.maxBattleHeight);
            this.traceMap.put(name, trace);
        }

        double angle = Math.toRadians((this.getHeading() + event.getBearing()) % 360.0D);
        double cX = this.getX();
        double cY = this.getY();
        double scannedX = cX + Math.sin(angle) * event.getDistance();
        double scannedY = cY + Math.cos(angle) * event.getDistance();
        RobotModel model = new RobotModel();
        model.time = event.getTime();
        model.velocity = event.getVelocity();
        model.x = scannedX;
        model.y = scannedY;
        model.energy = event.getEnergy();
        model.distance = event.getDistance();
        model.name = name;
        trace.add(model);
        Destination des = trace.predict();
        double radian = 0.0D;
        if (des.x != -1234.0D) {
            radian = RadianUtil.getRadianInSystem(RadianUtil.getRadian(cX - des.x, cY - des.y));
        } else {
            radian = RadianUtil.getRadianInSystem(RadianUtil.getRadian(cX - scannedX, cY - scannedY));
        }

        this.go2fire(radian, 540.0D);
    }

    public void onRobotDeath(RobotDeathEvent event) {
        this.traceMap.remove(event.getName());
    }

    public void onBulletHit(BulletHitEvent event) {
        long currentTime = event.getTime();
        long gap = currentTime - this.lastHittedTime;
        System.out.println("############### Hit by bullet, time=" + currentTime + ", through time=" + gap + " ###############");
        this.lastHittedTime = currentTime;
        if (gap < 30L) {
            List<Integer> intersection = getIntersection(getNearbyList(this.preBlock), getNearbyList(this.desBlock));
            Map<Integer, Double> blockMap = this.getBlockStatusMap();
            Map<Integer, Double> distanceMap = this.getDistanceMap(blockMap);
            Double max = -1.0D;
            int block = 0;
            Iterator var12 = intersection.iterator();

            while(var12.hasNext()) {
                Integer i = (Integer)var12.next();
                Double currentDistance = (Double)distanceMap.get(i);
                if (block == 0) {
                    block = i.intValue();
                }

                if (currentDistance.doubleValue() > max.doubleValue()) {
                    max = currentDistance;
                    block = i.intValue();
                }
            }

            double x = this.getX();
            double y = this.getY();
            int currentBlock = this.getBlockNum(x, y);
            this.preBlock = currentBlock;
            this.desBlock = block;
            Point desP = this.generateDesination(block);
            this.checkBarrierAndMove(x, y, desP);
            this.printCurrentStatus(blockMap, distanceMap, block, x, y, currentBlock, desP);
        }

    }

    private void printCurrentStatus(Map<Integer, Double> blockMap, Map<Integer, Double> distanceMap, int block, double x, double y, int currentBlock, Point desP) {
        System.out.println(blockMap);
        System.out.println(distanceMap);
        System.out.println("Des block=" + block + ", current block=" + currentBlock);
        System.out.println("Current " + x + ", " + y + ", Destination " + desP.x + ", " + desP.y);
    }

    private void escape() {
        Map<Integer, Double> blockMap = this.getBlockStatusMap();
        Map<Integer, Double> distanceMap = this.getDistanceMap(blockMap);
        double maxEnergy = -5000.0D;
        int block = 0;
        double x = this.getX();
        double y = this.getY();
        int currentBlock = this.getBlockNum(x, y);
        Iterator var12 = distanceMap.entrySet().iterator();

        while(true) {
            Entry entry;
            do {
                do {
                    do {
                        if (!var12.hasNext()) {
                            this.preBlock = currentBlock;
                            this.desBlock = block;
                            Point desP = this.generateDesination(block);
                            this.checkBarrierAndMove(x, y, desP);
                            this.printCurrentStatus(blockMap, distanceMap, block, x, y, currentBlock, desP);
                            return;
                        }

                        entry = (Entry)var12.next();
                    } while(((Integer)entry.getKey()).intValue() == 5);
                } while(((Integer)entry.getKey()).intValue() == currentBlock);
            } while(this.getOthers() <= 2 && ((Integer)entry.getKey()).intValue() == this.preBlock);

            if (maxEnergy < ((Double)entry.getValue()).doubleValue()) {
                maxEnergy = ((Double)entry.getValue()).doubleValue();
                block = ((Integer)entry.getKey()).intValue();
            }
        }
    }

    private Map<Integer, Double> getBlockStatusMap() {
        Map<Integer, Double> blockMap = new HashMap();

        for(int i = 1; i <= 9; ++i) {
            blockMap.put(i, 0.0D);
        }

        Iterator var3 = this.traceMap.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, RobotTrace> entry = (Entry)var3.next();
            RobotTrace trace = (RobotTrace)entry.getValue();
            RobotModel model = trace.current;
            if (!trace.isDeath && model != null) {
                Double x = model.x;
                Double y = model.y;
                int cb = this.getBlockNum(x, y);
                Double energy = (Double)blockMap.get(cb);
                energy = energy.doubleValue() + model.energy;
                blockMap.put(cb, energy);
            }
        }

        return blockMap;
    }

    private RobotModel getNearby() {
        RobotModel nearby = null;
        Iterator var3 = this.traceMap.entrySet().iterator();

        while(true) {
            RobotTrace trace;
            RobotModel model;
            do {
                do {
                    if (!var3.hasNext()) {
                        return nearby;
                    }

                    Entry<String, RobotTrace> entry = (Entry)var3.next();
                    trace = (RobotTrace)entry.getValue();
                    model = trace.current;
                } while(trace.isDeath);
            } while(nearby != null && nearby.distance <= model.distance);

            nearby = model;
        }
    }

    private Map<Integer, Double> getDistanceMap(Map<Integer, Double> blockMap) {
        Map<Integer, Double> distanceMap = new HashMap();

        for(int i = 1; i <= 9; ++i) {
            distanceMap.put(i, 0.0D);
        }

        Iterator var4 = blockMap.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<Integer, Double> entry = (Entry)var4.next();
            double sum = 0.0D;
            int cx = ((Integer)entry.getKey()).intValue() % 3;
            int cy = (((Integer)entry.getKey()).intValue() - 1) / 3 + 1;
            if (cx == 0) {
                cx = 3;
            }

            for(int i = 1; i <= 9; ++i) {
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

        return distanceMap;
    }

    private void checkBarrierAndMove(double x, double y, Point desP) {
        RobotModel nearby = this.getNearby();
        double xDes = desP.x;
        double yDes = desP.y;
        double spaceOffset = this.bodyLength * 2.0D;
        double a = 0.0D;
        double b = 0.0D;
        if (nearby != null) {
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

            System.out.println("Nearby name:" + nearby.name + ", the distance is " + nearby.distance);
            this.go2fire(x, y, nearby.x, nearby.y, 540.0D);
            if (Math.abs(spaceOffset) < this.bodyLength && nearby.distance < 100.0D) {
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
                        this.moveTo(x, y, x, yDes, true);
                    } else if (a2 == 0.0D) {
                        this.moveTo(x, y, xDes, y, true);
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
                            this.moveTo(x, y, farP.x, farP.y, true);
                        }
                    }

                    this.moveTo(this.getX(), this.getY(), xDes, yDes, true);
                } else if (spaceOffset < 0.0D) {
                    this.moveTo(x, y, x, yDes, true);
                    this.moveTo(this.getX(), this.getY(), xDes, this.getY(), true);
                } else {
                    this.moveTo(x, y, xDes, y, true);
                    this.moveTo(this.getX(), this.getY(), this.getX(), yDes, true);
                }
            } else {
                this.moveTo(this.getX(), this.getY(), xDes, yDes, true);
            }

        } else {
            this.turnRadarLeft(360.0D);
        }
    }

    private void moveTo(double x, double y, double xDes, double yDes, boolean realtime) {
        double heading = this.getHeading();
        double radian = RadianUtil.getRadianInSystem(RadianUtil.getRadian(xDes - x, yDes - y));
        double offset = radian - heading + 360.0D;
        offset %= 360.0D;
        double distance = RadianUtil.getDistance(xDes - x, yDes - y);
        if (offset < 180.0D) {
            if (offset > 90.0D) {
                offset = 180.0D - offset;
                if (realtime) {
                    this.setTurnLeft(offset);
                    this.setBack(distance);
                } else {
                    this.turnLeft(offset);
                    this.back(distance);
                }

                this.front = false;
            } else {
                if (realtime) {
                    this.setTurnRight(offset);
                    this.setAhead(distance);
                } else {
                    this.turnRight(offset);
                    this.ahead(distance);
                }

                this.front = true;
            }
        } else {
            offset = 360.0D - offset;
            if (offset > 90.0D) {
                offset = 180.0D - offset;
                if (realtime) {
                    this.setTurnRight(offset);
                    this.setBack(distance);
                } else {
                    this.turnRight(offset);
                    this.back(distance);
                }

                this.front = false;
            } else {
                if (realtime) {
                    this.setTurnLeft(offset);
                    this.setAhead(distance);
                } else {
                    this.turnLeft(offset);
                    this.ahead(distance);
                }

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

    private Point generateDesination(int block) {
        Random rand = new Random();
        int xP = block % 3;
        if (xP == 0) {
            xP = 3;
        }

        int yP = (block - 1) / 3;
        double xDes = -1.0D;
        double yDes = -1.0D;
        double range = 1.0D;
        int rest = this.getOthers();
        if (rest != 0) {
            range = 2.0D / (3.0D * (double)rest);
        }

        if (xP == 3) {
            xDes = this.maxBattleWidth - rand.nextDouble() * this.maxBattleWidth * range / 3.0D;
        } else if (xP == 2) {
            xDes = rand.nextDouble() * this.maxBattleWidth / 3.0D + this.maxBattleWidth / 3.0D;
        } else {
            xDes = rand.nextDouble() * this.maxBattleWidth * range / 3.0D;
        }

        if (yP == 0) {
            yDes = rand.nextDouble() * this.maxBattleHeight * range / 3.0D;
        } else if (yP == 1) {
            yDes = rand.nextDouble() * this.maxBattleHeight / 3.0D + this.maxBattleHeight / 3.0D;
        } else {
            yDes = this.maxBattleHeight - rand.nextDouble() * this.maxBattleHeight * range / 3.0D;
        }

        System.out.println("current range=" + range + ", des x=" + xDes + ", des y=" + yDes);
        if (xDes < this.bodyLength / 2.0D) {
            xDes = this.bodyLength;
        } else if (xDes > this.maxBattleWidth - this.bodyLength / 2.0D) {
            xDes = this.maxBattleWidth - this.bodyLength;
        }

        if (yDes < this.bodyLength / 2.0D) {
            yDes = this.bodyLength;
        } else if (yDes > this.maxBattleHeight - this.bodyLength / 2.0D) {
            yDes = this.maxBattleHeight - this.bodyLength;
        }

        Point currentDesPoint = new Point(xDes, yDes);
        double x = this.getX();
        double y = this.getY();
        if (this.desPoint != null) {
            double distance = RadianUtil.getDistance(x - this.desPoint.x, y - this.desPoint.y);
            double distance2 = RadianUtil.getDistance(xDes - this.desPoint.x, yDes - this.desPoint.y);
            if (distance > 100.0D && distance2 > 100.0D) {
                return this.desPoint;
            }
        }

        this.desPoint = currentDesPoint;
        return currentDesPoint;
    }

    private static List<Integer> getNearbyList(int num) {
        List<Integer> ret = new ArrayList();
        if (num % 3 != 1 && isValidBlock(num - 1) && (num - 1) / 3 != 1) {
            ret.add(num - 1);
        }

        if (num % 3 != 0 && isValidBlock(num + 1) && (num - 1) / 3 != 1) {
            ret.add(num + 1);
        }

        if (num % 2 != 0) {
            if (isValidBlock(num + 3)) {
                ret.add(num + 3);
            }

            if (isValidBlock(num - 3)) {
                ret.add(num - 3);
            }
        } else if (num % 4 == 0) {
            if (isValidBlock(num - 2)) {
                ret.add(num - 2);
            }

            if (isValidBlock(num - 4)) {
                ret.add(num - 4);
            }

            if (isValidBlock(num + 4)) {
                ret.add(num + 4);
            }
        } else {
            if (isValidBlock(num + 2)) {
                ret.add(num + 2);
            }

            if (isValidBlock(num - 4)) {
                ret.add(num - 4);
            }

            if (isValidBlock(num + 4)) {
                ret.add(num + 4);
            }
        }

        if (num == 5) {
            ret.clear();

            for(int i = 1; i <= 9; ++i) {
                if (i != 5) {
                    ret.add(i);
                }
            }
        }

        return ret;
    }

    private static List<Integer> getIntersection(List<Integer> set1, List<Integer> set2) {
        List<Integer> ret = new ArrayList();
        Iterator var4 = set1.iterator();

        while(var4.hasNext()) {
            Integer integer = (Integer)var4.next();
            if (set2.contains(integer)) {
                ret.add(integer);
            }
        }

        return ret;
    }

    private static boolean isValidBlock(int block) {
        return block >= 1 && block <= 9;
    }

    public void onHitRobot(HitRobotEvent event) {
        double angle = Math.toRadians((this.getHeading() + event.getBearing()) % 360.0D);
        this.go2fire(angle);
        this.setFire(3.0D);
        this.setFire(3.0D);
        this.setFire(3.0D);
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
        this.go2fire(Math.toDegrees(radian), 360.0D);
    }

    private void go2fire(double degree, double increase) {
        double gunHeading = this.getGunHeading();
        double offset = degree - gunHeading + increase;
        offset %= 360.0D;
        if (offset < 180.0D) {
            this.setTurnGunRight(offset);
        } else {
            this.setTurnGunLeft(360.0D - offset);
        }

        double energy = this.getEnergy();
        this.setFire(3.0D - (20.0D - energy) / 6.0D);
    }

    private void go2fire(double x, double y, double desX, double desY, double increase) {
        double radian = RadianUtil.getRadianInSystem(RadianUtil.getRadian(x - desX, y - desY));
        this.go2fire(radian, 540.0D);
    }

    public static void main(String[] args) {
        for(int i = 1; i <= 9; ++i) {
            List<Integer> list = getNearbyList(i);
            System.out.print(i + "=");
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Integer integer = (Integer)var4.next();
                System.out.print(integer + " ");
            }

            System.out.println();
        }

    }
}
