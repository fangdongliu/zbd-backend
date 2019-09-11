package cn.fdongl.point.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateUtils
 * @Description TODO
 * @Author zm
 * @Date 2019/9/9 21:47
 * @Version 1.0
 **/
public class DateUtils {
    //年月日格式化
    private static SimpleDateFormat nyr = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getNowDate(){
        return new Date();
    }

    public static String nyrFormat(Date dateForTransfer){
        return nyr.format(dateForTransfer);
    }
}
