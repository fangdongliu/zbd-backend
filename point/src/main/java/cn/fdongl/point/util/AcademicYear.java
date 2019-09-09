package cn.fdongl.point.util;

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
}
