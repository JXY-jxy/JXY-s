package com.jiang.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现Excel写的操作
        //1 设置写入文件夹地址和Excel文件名称
//        String filename="D:\\write.xlsx";
//        //2 调用easyExcel里面的方法实现写操作
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());


        //实现Excel读操作
        String filename="D:\\write.xlsx";

        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }
    //创建方法返回List集合
    private static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("Lucy"+i);
            list.add(data);
        }
        return list;
    }
}
