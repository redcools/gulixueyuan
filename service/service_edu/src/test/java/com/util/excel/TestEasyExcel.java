package com.util.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String fileName = "F:\\write.xlsx";
//        EasyExcel.write(fileName,DamoData.class).sheet("学生表").doWrite(show());
        EasyExcel.read(fileName,DamoData.class,new ExcelListener()).doReadAll();



    }

    private static List<DamoData> show(){
        List<DamoData> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            DamoData damoData = new DamoData();
            damoData.setSno(i);
            damoData.setSname("sdf"+i);
            list.add(damoData);

        }
        return list;
    }
}
