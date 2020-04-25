/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-16
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.test;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>NAME: PoiTest</p>
 * @author Administrator
 * @date 2020-03-16 10:59:20
 * @version 1.0
 */
public class PoiTest {
    public static void main(String[] args) throws IOException {
        //1.创建HSSFWorkbook对象，对应一个excel文件
        HSSFWorkbook workbook=new HSSFWorkbook();
        //2.使用workbook创建HSSFSheet对象，对应一页
        HSSFSheet sheet=workbook.createSheet("学生列表");
        //3.使用sheet创建HSSFRow对象，对应一行
        HSSFRow row=sheet.createRow(0);
        //4.使用row创建HSSFCell对象，对应列
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("学号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("年龄");

        HSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //5.创建10个HSSFRow对象，对应10行数据
        for(int i=1;i<=10;i++){
            row=sheet.createRow(i);

            cell=row.createCell(0);
            cell.setCellValue(100+i);
            cell=row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("NAME"+i);
            cell=row.createCell(2);
            cell.setCellValue(20+i);
        }

        //把workbook生成一个excel文件
        OutputStream os=new FileOutputStream("E:\\teaching\\section4\\1920\\course\\testDir\\student.xls");
        workbook.write(os);
        os.close();
        workbook.close();
        System.out.println("===========ok==========");
    }
}
