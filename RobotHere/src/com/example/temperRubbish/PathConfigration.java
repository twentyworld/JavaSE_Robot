package com.example.temperRubbish;

import com.example.temperRubbish.Util.TemperUtils;

/**
 * Created by teemper on 2017/7/27, 20:40.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */
public class PathConfigration {

    static double lowestXaxis = 30;
    static double lowestYaxis = 30;
    static double largestXaxis = TemperUtils.WIDTH-50;
    static double highestYaxis = TemperUtils.HEIGHT-40;

    public static double configPath(Coordination coordination, TemperRubbish rubbish){
        if(coordination.getX()>lowestXaxis&&coordination.getX()<largestXaxis&&coordination.getY()>lowestYaxis&&coordination.getY()<highestYaxis)
            return rubbish.getHeadingRadians();
        else
            return TemperUtils.constrainRadianFromZeroToDoublePI(rubbish.getHeadingRadians()+Math.PI);
    }
}
