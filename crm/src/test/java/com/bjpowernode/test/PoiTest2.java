/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-17
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>NAME: PoiTest2</p>
 * @author Administrator
 * @date 2020-03-17 11:30:23
 * @version 1.0
 */
public class PoiTest2 {
    public static void main(String[] args) throws IOException {
        //创建HSSFWorkbbok对象，对应一个excel文件
        InputStream is=new FileInputStream("E:\\teaching\\section4\\1920\\course\\testDir\\市场活动.xls");
        HSSFWorkbook workbook=new HSSFWorkbook(is);
        //获取第一页
        HSSFSheet sheet=workbook.getSheetAt(0);
        //获取行
        HSSFRow row=null;
        HSSFCell cell=null;
        for(int i=0;i<=sheet.getLastRowNum();i++){
            row=sheet.getRow(i);

            for(int j=0;j<row.getLastCellNum();j++){
                cell=row.getCell(j);

                System.out.print(getValueFromCell(cell)+" ");//每一列的数据打完，用空格隔开
            }
            System.out.println();//每一行打完之后，换行
        }

    }

    public static String getValueFromCell(HSSFCell cell){
        String ret="";
        if(cell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
            ret="";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            ret=cell.getBooleanCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_ERROR){
            ret="";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            ret=cell.getCellFormula();
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            ret=cell.getNumericCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            ret=cell.getStringCellValue();
        }
        /*switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_BLANK:
                ret="";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret=cell.getBooleanCellValue()+"";
        }*/
        return ret;
    }
}
