package me.vast.common.util;


/**
 * 计量单位工具
 */
public class UnitUtils {

    public static String getDistanceText(long distance) {
        String distanceText = "";
        double dis ;
        if (distance > 1000) {
            dis = Math.round(distance / 100d) / 10d;
            distanceText = dis + "公里";
        } else if (distance > 0 && distance < 1000) {
            distanceText = distance + "米";
        }
        return distanceText;
    }

}
