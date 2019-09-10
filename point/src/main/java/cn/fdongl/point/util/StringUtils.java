package cn.fdongl.point.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @param
 * @author hl
 * @return
 * @date 2019/9/9 21:59
 **/
public class StringUtils {

    public static boolean isDigit(String str) {
        try {
            Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取字符串第一个数字出现的下标
     *
     * @param str 软件学院2016级版培养方案-3-培养标准实现矩阵2016级
     * @return int
     * @author zm
     * @date 2019/9/9 22:23
     **/
    public static int getFirstNumericIndex(String str) {
        Pattern p = Pattern.compile("([0-9].{0,0})");
        Matcher m = p.matcher(str);
        m.find();
        return str.indexOf(m.group(1));
    }

    /**
     * 获取 培养标准实现矩阵 中的 年级
     *
     * @param cultivateMatrixName 软件学院2016级版培养方案-3-培养标准实现矩阵2016级
     * @return java.lang.String
     * @author zm
     * @date 2019/9/9 22:31
     **/
    public static String getGrade(String cultivateMatrixName) {
        Pattern p = Pattern.compile("([0-9].{3,3})");
        Matcher m = p.matcher(cultivateMatrixName);
        m.find();
        return m.group(1);
    }

    /**
     * 获取 培养标准实现矩阵 中的 学院
     *
     * @param cultivateMatrixName 软件学院2016级版培养方案-3-培养标准实现矩阵2016级
     * @return java.lang.String
     * @author zm
     * @date 2019/9/9 22:25
     **/
    public static String getDepartment(String cultivateMatrixName) {
        return cultivateMatrixName.substring(0, cultivateMatrixName.indexOf(
                StringUtils.getGrade(cultivateMatrixName)));
    }

    /**
     * 根据培养矩阵excel中的标题获取专业名称
     *
     * @param cultivateMatrixTitle 北京理工大学软件工程专业毕业要求指标点支撑课程关联矩阵
     * @return java.lang.String
     * @author zm
     * @date 2019/9/9 22:40
     **/
    public static String getMajority(String cultivateMatrixTitle) {
        return cultivateMatrixTitle.substring(
                cultivateMatrixTitle.indexOf("北京理工大学") + 6,
                cultivateMatrixTitle.indexOf("专业"));
    }

    /*@Test
    public void tt(){
        System.out.println(StringUtils.getFirstNumericIndex("软件学院2016级版培养方案-3-培养标准实现矩阵2016级"));

        System.out.println(StringUtils.getGrade("软件学院2016级版培养方案-3-培养标准实现矩阵2016级"));

        System.out.println(StringUtils.getDepartment("软件学院2016级版培养方案-3-培养标准实现矩阵2016级"));

        System.out.println(StringUtils.getMajority("北京理工大学软件工程专业毕业要求指标点支撑课程关联矩阵"));
    }*/
}
