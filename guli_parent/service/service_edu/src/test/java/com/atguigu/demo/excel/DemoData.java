package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.security.PrivateKey;

/**
 * @author ly
 * @create 2022-07-11-9:45
 */
@Data
public class DemoData {
    //设置excel表头名称
    @ExcelProperty("学生编号")
    private Integer sno;
    @ExcelProperty("学生姓名")
    private String snamne;
}
