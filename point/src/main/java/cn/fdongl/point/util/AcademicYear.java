package cn.fdongl.point.util;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

public class AcademicYear {

    public static String getStartYear() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int flag = 0;
        if (month < 7 && month >= 2) {
            year = year - 1;
        }
        return String.valueOf(year);

    }

    //获取当前年份
    public static String getNowYear() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 获取当前学期
     *
     * @author zm
     * @return java.lang.String
     * @date 2019/9/11 15:53
     **/
    public static String getNowSemester() {
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int beforeYear = nowYear - 1;
        int nextYear = nowYear + 1;
        String tmpSemester;

        if (now.get(Calendar.MONTH) + 1 <= 8) {
            tmpSemester = beforeYear + "-" + nowYear + "-2";
        } else {
            tmpSemester = nowYear + "-" + nextYear + "-1";
        }
        return tmpSemester;
    }
}
