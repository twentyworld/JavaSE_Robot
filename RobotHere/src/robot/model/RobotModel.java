//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package robot.model;

public class RobotModel {
    public long time;
    public String name;
    public double velocity;
    public double energy;
    public double x;
    public double y;
    public double distance;
    public RobotModel pre = this;
    public RobotModel next = this;

    public RobotModel() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RobotModel)) {
            return false;
        } else {
            RobotModel current = (RobotModel)obj;
            return this.time == current.time;
        }
    }
}
