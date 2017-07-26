package test;

import com.example.temperRubbish.Util.TemperUtils;

import static org.junit.Assert.*;

/**
 * Created by teemper on 2017/7/24, 21:34.
 *
 * @auther Zed.
 * copy as you like, but with these words.
 * from win.
 */

public class UtilsTest {
    //private static Utils util = new Utils();
    @org.junit.Test
    public void calculateDistance() throws Exception {

    }

    @org.junit.Test
    public void calculateVector() throws Exception {
    }

    @org.junit.Test
    public void calculateVectorRadian() throws Exception {
    }

    @org.junit.Test
    public void constrainRadianFromNegativePItoPI() throws Exception {
    }

    @org.junit.Test
    public void translateQuadrantToHeadingRadian() throws Exception {
    assertEquals(Math.PI,TemperUtils.translateQuadrantToHeadingRadian(-Math.PI/2));
    }


}
