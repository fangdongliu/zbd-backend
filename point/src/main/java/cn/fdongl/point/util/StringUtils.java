package cn.fdongl.point.util;

public class StringUtils {

    public static boolean isDigit(String str) {
        try {
            Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
