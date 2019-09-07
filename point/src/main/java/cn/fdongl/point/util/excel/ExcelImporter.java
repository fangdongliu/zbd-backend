package cn.fdongl.point.util.excel;

import com.glf.jxframe.common.utils.DateUtils;
import com.glf.jxframe.common.utils.excel.annotation.MapToField;
import com.glf.jxframe.modules.lab.entity.functionlab.SysDict;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.management.openmbean.InvalidKeyException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelImporter  {

//    public static <E> List<E> getListFromExcel(Class c,Workbook workbook,int headerLine, Map<String,String>fieldMap,List<String>schema) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
//
//        List<E> entitys = new ArrayList<>();
//        Sheet sheet = workbook.getSheetAt(0);
//
//        List<Method> methods = new ArrayList<>();
//        List<Class> classes = new ArrayList<>();
//
//        for(Cell cell:sheet.getRow(headerLine)){
//
//            String dest = fieldMap.get(cell.getStringCellValue());
//
//            if(dest != null){
//                Field field = c.getDeclaredField(dest);
//                schema.add(cell.getStringCellValue());
//
//                StringBuffer sb = new StringBuffer();
//                sb.append("set");
//                sb.append(dest.substring(0, 1).toUpperCase());
//                sb.append(dest.substring(1));
//                classes.add(field.getType());
//                methods.add(c.getMethod(sb.toString(),field.getType()));
//            }
//            else{
//                throw new InvalidKeyException("无效的列"+cell.getStringCellValue());
//            }
//
//        }
//
//        for(int i=headerLine+1;i<=sheet.getLastRowNum();i++){
//            int j=0;
//
//            E entity = (E) c.newInstance();
//            for(Cell cell:sheet.getRow(i)){
//
//                Class/**/type = classes.get(j);
//
//                switch(cell.getCellType()){
//                    case Cell.CELL_TYPE_BOOLEAN: {
//                        boolean cellValue = cell.getBooleanCellValue();
//                        if (type == Integer.class) {
//                            methods.get(j).invoke(entity, cellValue == true?1:0);
//                        } else if (type == Long.class) {
//                            methods.get(j).invoke(entity, cellValue == true?1:0);
//                        } else if (type == Double.class) {
//                            methods.get(j).invoke(entity, cellValue == true?1:0);
//                        } else if (type == Float.class) {
//                            methods.get(j).invoke(entity, cellValue == true?1:0);
//                        } else {
//                            methods.get(j).invoke(entity, cellValue == true?"true":"false");
//                        }
//                        break;
//                    }
//                    case Cell.CELL_TYPE_ERROR: {
//                        byte b = cell.getErrorCellValue();
//                        if (type == Integer.class) {
//                            methods.get(j).invoke(entity, Integer.valueOf(b));
//                        } else if (type == Long.class) {
//                            methods.get(j).invoke(entity, Long.valueOf(b));
//                        } else if (type == Double.class) {
//                            methods.get(j).invoke(entity, Double.valueOf(b));
//                        } else if (type == Float.class) {
//                            methods.get(j).invoke(entity, Float.valueOf(b));
//                        } else {
//                            methods.get(j).invoke(entity, Byte.valueOf(b).toString());
//                        }
//                        break;
//                    }
//                    case Cell.CELL_TYPE_FORMULA:{
//                        String s = cell.getCellFormula();
//                        if (type == Integer.class) {
//                            methods.get(j).invoke(entity, Integer.valueOf(s));
//                        } else if (type == Long.class) {
//                            methods.get(j).invoke(entity, Long.valueOf(s));
//                        } else if (type == Double.class) {
//                            methods.get(j).invoke(entity, Double.valueOf(s));
//                        } else if (type == Float.class) {
//                            methods.get(j).invoke(entity, Float.valueOf(s));
//                        } else if(type==Date.class){
//                            methods.get(j).invoke(entity, DateUtil.getJavaDate(Double.valueOf(s)));
//                        } else {
//                            methods.get(j).invoke(entity, s);
//                        }
//                        break;
//                    }
//                    case Cell.CELL_TYPE_STRING: {
//                        String s = cell.getStringCellValue();
//                        if (type == Integer.class) {
//                            methods.get(j).invoke(entity, Integer.valueOf(s));
//                        } else if (type == Long.class) {
//                            methods.get(j).invoke(entity, Long.valueOf(s));
//                        } else if (type == Double.class) {
//                            methods.get(j).invoke(entity, Double.valueOf(s));
//                        } else if (type == Float.class) {
//                            methods.get(j).invoke(entity, Float.valueOf(s));
//                        } else if(type==Date.class){
//                            methods.get(j).invoke(entity, DateUtil.getJavaDate(Double.valueOf(s)));
//                        } else {
//                            methods.get(j).invoke(entity, s);
//                        }
//                        break;
//                    }
//                    case Cell.CELL_TYPE_NUMERIC:{
//                        double d = cell.getNumericCellValue();
//                        if (type == Integer.class) {
//                            methods.get(j).invoke(entity, Integer.valueOf((int)d));
//                        } else if (type == Long.class) {
//                            methods.get(j).invoke(entity, Long.valueOf((long)d));
//                        } else if (type == Double.class) {
//                            methods.get(j).invoke(entity, Double.valueOf(d));
//                        } else if (type == Float.class) {
//                            methods.get(j).invoke(entity, Float.valueOf((float)d));
//                        } else if(type==Date.class){
//                            methods.get(j).invoke(entity, DateUtil.getJavaDate(Double.valueOf(d)));
//                        } else {
//                            methods.get(j).invoke(entity, new Double(d).toString());
//                        }
//                        break;
//                    }
//                    case Cell.CELL_TYPE_BLANK:{
//                        methods.get(j).invoke(entity,null);
//                        break;
//                    }
//
//                }
//
//                j++;
//            }
//            entitys.add(entity);
//        }
//
//        return entitys;
//    }

    public static <E> E getEntityFromExcel(Class<E> c, MultipartFile file, int headerLine, int rowNum) throws Exception{
        Workbook workbook = getWorkbookFromFile(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(headerLine);

        Map<String,Integer>indexMap = new HashMap<>();

        {
            int index = 0;

            for (Cell cell : header) {
                indexMap.put(getCellValue(cell).toString(), index++);
            }
        }

        Boolean[] requireds=new Boolean[indexMap.size()];
        Method[] methods = new Method[indexMap.size()];
        Field[] reIndexedFields = new Field[indexMap.size()];

        Field[] fields = c.getDeclaredFields();//获取声明了的变量

        for (Field f: fields){
            MapToField annotation = f.getAnnotation(MapToField.class);//返回指定类型的注释
            if(annotation != null){//注释不为空说明该属性在表头中
                Integer index = indexMap.get(annotation.title());//获取该属性对应的是第几列
                if(index == null){
                    continue;
                }
                StringBuffer sb = new StringBuffer();
                sb.append("set");
                sb.append(f.getName().substring(0, 1).toUpperCase());
                sb.append(f.getName().substring(1));
                methods[index]=c.getMethod(sb.toString(),f.getType());
                requireds[index]=annotation.required();
                reIndexedFields[index]=f;
            }
        }

        E e = c.newInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        Row row = sheet.getRow(rowNum);

        HashMap<Object,Integer> map=new HashMap<>();
        map.put(String.class,Cell.CELL_TYPE_STRING);
        map.put(Boolean.class,Cell.CELL_TYPE_BOOLEAN);
        map.put(Double.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Float.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Integer.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Long.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Date.class,Cell.CELL_TYPE_NUMERIC);

        for(int index = 0; index < methods.length;index++){

//            Cell cell = row.getCell(index);
//
//            Method method = methods[index];
//            if(method == null)
//                continue;
//
//            Object value = getCellValue(cell);
//
            Method method = methods[index];
            if(method == null)
                continue;

            Field f = reIndexedFields[index];

            Cell cell = row.getCell(index);

            //根据类中的类型进行读取
            if(cell==null)
                continue;
            cell.setCellType(map.get(f.getType()));


            Object value = getCellValue(cell);


            if(value==null) {
                if(requireds[index]==true)
                    throw new NullPointerException("Row-" + rowNum + ",Col-" + index + "不得为空");
                else
                    continue;
            }
//            Field f = reIndexedFields[index];

            if(f.getType() == String.class){
                if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String s = value.toString();
                    if(s.endsWith(".0")){
                        s = s.substring(0,s.length()-2);
                    }
                    method.invoke(e,s);
                }
                else{
                    method.invoke(e,value.toString());
                }
            }
            else if(f.getType()==Double.class){
                method.invoke(e,Double.valueOf(value.toString()));
            }
            else if(f.getType()==Float.class){
                method.invoke(e,Double.valueOf(value.toString()).floatValue());
            }
            else if(f.getType()==Integer.class){
                method.invoke(e,Double.valueOf(value.toString()).intValue());
            }
            else if(f.getType()==Long.class){
                method.invoke(e,Double.valueOf(value.toString()).longValue());
            }
            else if(f.getType()==Date.class){
                if(value.getClass()==Double.class){
                    method.invoke(e,DateUtil.getJavaDate((Double) value));
                }
                else{
                    method.invoke(e,sdf.parse(value.toString()));
                }
            }
        }
        return e;
    }

    public static <E> List<E> getListFromExcel(Class<E>cls, MultipartFile file, int headerLine) throws Exception {

        Workbook workbook = getWorkbookFromFile(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(headerLine);

        Map<String,Integer>indexMap = new HashMap<>();

        {
            int index = 0;

            for (Cell cell : header) {
                indexMap.put(getCellValue(cell).toString(), index++);
            }
        }

        Boolean[] requireds=new Boolean[indexMap.size()];
        Method[] methods = new Method[indexMap.size()];
        Field[] reIndexedFields = new Field[indexMap.size()];

        Field[] fields = cls.getDeclaredFields();//获取声明了的变量

        for (Field f: fields){
            MapToField annotation = f.getAnnotation(MapToField.class);//返回指定类型的注释
            if(annotation != null){//注释不为空说明该属性在表头中
                Integer index = indexMap.get(annotation.title());//获取该属性对应的是第几列
                if(index == null){
                    continue;
                }
                StringBuffer sb = new StringBuffer();
                sb.append("set");
                sb.append(f.getName().substring(0, 1).toUpperCase());
                sb.append(f.getName().substring(1));
                methods[index]=cls.getMethod(sb.toString(),f.getType());
                requireds[index]=annotation.required();
                reIndexedFields[index]=f;
            }
        }

        List<E> result = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        for (int i=headerLine+1; i <= sheet.getLastRowNum() ; i++){

            E e = cls.newInstance();

            Row row = sheet.getRow(i);

            for(int index = 0; index < methods.length;index++){

                Cell cell = row.getCell(index);
               // cell.setCellType(Cell.CELL_TYPE_STRING);

                Method method = methods[index];
                if(method == null)
                    continue;

                Object value = getCellValue(cell);
//                String value=cell.getStringCellValue();

                if(value==null) {
                    if(requireds[index]==true)
                        throw new NullPointerException("Row-" + i + ",Col-" + index + "不得为空");
                    else
                        continue;
                }
                Field f = reIndexedFields[index];

                if(f.getType() == String.class){
                    if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                        String s = value.toString();
                        if(s.endsWith(".0")){
                            s = s.substring(0,s.length()-2);
                        }
                        method.invoke(e,s);
                    }
                    else{
                        method.invoke(e,value.toString());
                    }
                }
                else if(f.getType()==Double.class){
                    method.invoke(e,Double.valueOf(value.toString()));
                }
                else if(f.getType()==Float.class){
                    method.invoke(e,Double.valueOf(value.toString()).floatValue());
                }
                else if(f.getType()==Integer.class){
                    method.invoke(e,Double.valueOf(value.toString()).intValue());
                }
                else if(f.getType()==Long.class){
                    method.invoke(e,Double.valueOf(value.toString()).longValue());
                }
                else if(f.getType()==Date.class){
                    if(value.getClass()==Double.class){
                        method.invoke(e,DateUtil.getJavaDate((Double) value));
                    }
                    else{
                        method.invoke(e,sdf.parse(value.toString()));
                    }
                }
            }
            result.add(e);

        }
        return result;
    }


    public static <E> List<E> getListFromExcel1(Class<E>cls, MultipartFile file, int headerLine) throws Exception {

        Workbook workbook = getWorkbookFromFile(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(headerLine);

        Map<String,Integer>indexMap = new HashMap<>();

        {
            int index = 0;

            for (Cell cell : header) {
                indexMap.put(getCellValue(cell).toString(), index++);
            }
        }

        Boolean[] requireds=new Boolean[indexMap.size()];
        Method[] methods = new Method[indexMap.size()];
        Field[] reIndexedFields = new Field[indexMap.size()];

        Field[] fields = cls.getDeclaredFields();//获取声明了的变量

        for (Field f: fields){
            MapToField annotation = f.getAnnotation(MapToField.class);//返回指定类型的注释
            if(annotation != null){//注释不为空说明该属性在表头中
                Integer index = indexMap.get(annotation.title());//获取该属性对应的是第几列
                if(index == null){
                    continue;
                }
                StringBuffer sb = new StringBuffer();
                sb.append("set");
                sb.append(f.getName().substring(0, 1).toUpperCase());
                sb.append(f.getName().substring(1));
                methods[index]=cls.getMethod(sb.toString(),f.getType());
                requireds[index]=annotation.required();
                reIndexedFields[index]=f;
            }
        }

        List<E> result = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        HashMap<Object,Integer> map=new HashMap<>();
        map.put(String.class,Cell.CELL_TYPE_STRING);
        map.put(Boolean.class,Cell.CELL_TYPE_BOOLEAN);
        map.put(Double.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Float.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Integer.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Long.class,Cell.CELL_TYPE_NUMERIC);
        map.put(Date.class,Cell.CELL_TYPE_STRING);


        for (int i=headerLine+1; i <= sheet.getLastRowNum() ; i++){

            E e = cls.newInstance();

            Row row = sheet.getRow(i);

            for(int index = 0; index < methods.length;index++){


                Method method = methods[index];
                if(method == null)
                    continue;

                Field f = reIndexedFields[index];

                Cell cell = row.getCell(index);
                // cell.setCellType(Cell.CELL_TYPE_STRING);
                //根据类中的类型进行读取
                if(cell==null)
                    continue;
                cell.setCellType(map.get(f.getType()));


                Object value = getCellValue(cell);
//                String value=cell.getStringCellValue();

                if(value==null) {
                    if(requireds[index]==true)
                        throw new NullPointerException("Row-" + i + ",Col-" + index + "不得为空");
                    else
                        continue;
                }


                if(f.getType() == String.class){
                    if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                        String s = value.toString();
                        if(s.endsWith(".0")){
                            s = s.substring(0,s.length()-2);
                        }
                        method.invoke(e,s);
                    }
                    else{
                        method.invoke(e,value.toString());
                    }
                }
                else if(f.getType()==Double.class){
                    method.invoke(e,Double.valueOf(value.toString()));
                }
                else if(f.getType()==Float.class){
                    method.invoke(e,Double.valueOf(value.toString()).floatValue());
                }
                else if(f.getType()==Integer.class){
                    method.invoke(e,Double.valueOf(value.toString()).intValue());
                }
                else if(f.getType()==Long.class){
                    method.invoke(e,Double.valueOf(value.toString()).longValue());
                }
                else if(f.getType()==Date.class){
                      if(value.getClass()==Double.class){
                     method.invoke(e,DateUtil.getJavaDate((Double) value));
                    }
                     else{
                    method.invoke(e,sdf.parse(value.toString()));
                    }
                }
            }
            result.add(e);

        }
        return result;
    }

//    public static <E> List<E> getListFromExcel(Class<E>cls, File file, int headerLine) throws Exception {
//
//        Workbook workbook = getWorkbookFromFile(file);
//        Sheet sheet = workbook.getSheetAt(0);
//        Row header = sheet.getRow(headerLine);
//
//        Map<String,Integer>indexMap = new HashMap<>();
//
//        {
//            int index = 0;
//
//            for (Cell cell : header) {
//                indexMap.put(getCellValue(cell).toString(), index++);
//            }
//        }
//
//        Boolean[] requireds=new Boolean[indexMap.size()];
//        Method[] methods = new Method[indexMap.size()];
//        Field[] reIndexedFields = new Field[indexMap.size()];
//
//        Field[] fields = cls.getDeclaredFields();
//
//        for (Field f: fields){
//            MapToField annotation = f.getAnnotation(MapToField.class);
//            if(annotation != null){
//                Integer index = indexMap.get(annotation.title());
//                if(index == null){
//                    continue;
//                }
//                StringBuffer sb = new StringBuffer();
//                sb.append("set");
//                sb.append(f.getName().substring(0, 1).toUpperCase());
//                sb.append(f.getName().substring(1));
//                methods[index]=cls.getMethod(sb.toString(),f.getType());
//                requireds[index]=annotation.required();
//                reIndexedFields[index]=f;
//            }
//        }
//
//        List<E> result = new ArrayList<>();
//
//        for (int i=headerLine+1; i <= sheet.getLastRowNum() ; i++){
//
//            E e = cls.newInstance();
//
//            Row row = sheet.getRow(i);
//
//            for(int index = 0; index < methods.length;index++){
//
//                Cell cell = row.getCell(index);
//
//                Method method = methods[index];
//                if(method == null)
//                    continue;
//
//                Object value = getCellValue(cell);
//
//                if(value==null) {
//                    if(requireds[index]==true)
//                        throw new NullPointerException("Row-" + i + ",Col-" + index + "不得为空");
//                    else
//                        method.invoke(e,null);
//                }
//                Field f = reIndexedFields[index];
//
//                if(f.getType() == String.class){
//                    if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
//                        String s = value.toString();
//                        if(s.endsWith(".0")){
//                            s = s.substring(0,s.length()-2);
//                        }
//                        method.invoke(e,s);
//                    }
//                    else{
//                        method.invoke(e,value.toString());
//                    }
//                }
//                else if(f.getType()==Double.class){
//                    method.invoke(e,Double.valueOf(value.toString()));
//                }
//                else if(f.getType()==Float.class){
//                    method.invoke(e,Double.valueOf(value.toString()).floatValue());
//                }
//                else if(f.getType()==Integer.class){
//                    method.invoke(e,Double.valueOf(value.toString()).intValue());
//                }
//                else if(f.getType()==Long.class){
//                    method.invoke(e,Double.valueOf(value.toString()).longValue());
//                }
//                else if(f.getType()==Date.class){
//                    if(value.getClass()==Double.class){
//                        method.invoke(e,DateUtil.getJavaDate((Double) value));
//                    }
//                    else{
//                        method.invoke(e,DateUtil.getJavaDate(DateUtil.convertTime(value.toString())));
//                    }
//                }
//            }
//            result.add(e);
//
//        }
//        return result;
//    }

    public static Object getCellValue(Cell cell){
        try{
            if (cell != null){
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        return cell.getStringCellValue();
                    case Cell.CELL_TYPE_BOOLEAN:
                        return cell.getBooleanCellValue();
                    case Cell.CELL_TYPE_FORMULA:
                        return cell.getCellFormula();
                    case Cell.CELL_TYPE_ERROR:
                        return cell.getErrorCellValue();
                    case Cell.CELL_TYPE_BLANK:
                        return null;
                    case Cell.CELL_TYPE_NUMERIC:
                        return cell.getNumericCellValue();
                }
            }
        }catch (Exception e) {}
        return null;
    }

    public static Workbook getWorkbookFromFile(File file) throws Exception {
        String type = file.getName().substring(file.getName().lastIndexOf(".")+1).toLowerCase();
        Workbook workbook=null;
        if(type.equals("xls")){
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        else if(type.equals("xlsx")){
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
        else{
            throw new Exception("无理取闹的文件类型");
        }
        return workbook;
    }

    public static Workbook getWorkbookFromFile(MultipartFile file) throws Exception {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
        Workbook workbook=null;
        if(type.equals("xls")){
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        else if(type.equals("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        else{
            throw new Exception("无理取闹的文件类型");
        }
        return workbook;
    }

    /**
      a great test
     */

//    public static void main(String[] args) {
//
//        try {
//            List<TestClass> list = ExcelImporter.getListFromExcel(TestClass.class,new File("target/hh.xlsx"),0);
//            System.out.println("hhh");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("111");
////
////
////
////        File f = new File("target/hh.xlsx");
////        try {
////            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(f));
////
////           Cell cell =  workbook.getSheetAt(0).getRow(2).getCell(2);
////            String s = cell.getStringCellValue();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//    }

}

//class TestClass{
//
//    @MapToField(title="你好")
//    private String hh;
//
//    @MapToField(title="时间")
//    private Date t;
//
//    public String getHh() {
//        return hh;
//    }
//
//    public void setHh(String hh) {
//        this.hh = hh;
//    }
//
//    public Date getT() {
//        return t;
//    }
//
//    public void setT(Date t) {
//        this.t = t;
//    }
//}