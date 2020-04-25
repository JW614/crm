/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-12
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.domain.PaginationVO;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>NAME: ActivityController</p>
 * @author Administrator
 * @date 2020-03-12 12:00:49
 * @version 1.0
 */
@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(){
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));//yyyy-MM-dd HH:mm:ss
        activity.setCreateBy(user.getId());

        ReturnObject returnObject=new ReturnObject();

        try {
            //调用service层方法，保存数据
            int ret = activityService.saveCreateActivity(activity);
            if(ret>0){
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
            }else{
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryAllUsers.do")
    public @ResponseBody Object queryAllUsers(){
        //调用service层方法，查询所有用户
        List<User> userList=userService.queryAllUsers();
        //[{id:xxx,name:xxx,...},{},......]
        return userList;
    }

    @RequestMapping("/workbench/activity/queryActivityForPageByCondition.do")
    public @ResponseBody Object queryActivityForPageByCondition(
            String name,String owner,String startDate,String endDate,
            @RequestParam(required = false,defaultValue = "1") int pageNo,
            @RequestParam(required = false,defaultValue = "10") int pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        //由pageNo计算出beginNo
        int beginNo=(pageNo-1)*pageSize;
        map.put("beginNo",beginNo);
        map.put("pageSize",pageSize);

        //调用service层方法，查询数据
        PaginationVO vo=activityService.queryActivityForPageByCondition(map);

        return vo;
    }

    @RequestMapping("/workbench/activity/toEditActivity.do")
    public @ResponseBody Object toEditActivity(String id){
        //调用service层方法，查询数据
        List<User> userList=userService.queryAllUsers();
        Activity activity=activityService.queryActivityById(id);
        //根据查询结果，生成响应信息
        Map<String,Object> map=new HashMap<>();
        map.put("userList",userList);
        map.put("activity",activity);
        return map;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        //封装参数
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtils.formatDateTime(new Date()));

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，修改数据
            int ret = activityService.saveEditActivity(activity);
            if(ret>0){
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
            }else{
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivity.do")
    public @ResponseBody Object deleteActivity(String[] id){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，删除数据
            int ret = activityService.deleteActivityByIds(id);
            if(ret>0){
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
            }else{
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/exportActivity.do")
    public void exportActivity(HttpServletResponse response) throws IOException {
        //调用service方法，查询所有的市场活动
        List<Activity> activityList=activityService.queryAllActivity();
        //根据查询结果，生成excel文件
        //1.创建HSSFWorkbook对象，对应一个excel文件
        HSSFWorkbook workbook=new HSSFWorkbook();
        //2.使用workbook创建HSSFSheet对象，对应一页
        HSSFSheet sheet=workbook.createSheet("市场活动列表");
        //3.使用sheet创建HSSFRow对象，对应一行
        HSSFRow row=sheet.createRow(0);
        //4.使用row创建HSSFCell对象，对应列
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("名称");
        cell=row.createCell(2);
        cell.setCellValue("所有者");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");

        HSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //5.创建10个HSSFRow对象，对应10行数据
        if(activityList!=null&&activityList.size()>0){
            Activity activity=null;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);

                row=sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getName());
                cell=row.createCell(2);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
            }
        }

        //把生成的excel文件输出到浏览器
        response.setContentType("application/octet-stream;charset=utf-8");
        /*
         *默认情况下，浏览器接收到响应信息之后，处理方式都是直接打开，只有打不开的情况下才会弹出文件下载窗口；
         * 可以这是响应头信息Content-Disposition使浏览器接收到响应信息之后，直接弹出文件下载窗口，即使能打开也不会打开。
         */
        response.addHeader("Content-Disposition","attachment;filename=ActivityList"+DateUtils.getNowTimeStr()+".xls");
        OutputStream out=response.getOutputStream();

        //把workbook生成一个excel文件
        //OutputStream os=new FileOutputStream("E:\\teaching\\section4\\1920\\course\\testDir\\activity.xls");
        workbook.write(out);
        //os.close();
        workbook.close();

        //数据：内存：输出到客户端


        //读已经生成excel文件
        //InputStream is=new FileInputStream("E:\\teaching\\section4\\1920\\course\\testDir\\activity.xls");
        //通过out输出到浏览器

               /// out.write(is.read());
    }

    @RequestMapping("/workbench/activity/exportSelectedActivity.do")
    public void exportSelectedActivity(String[] id,HttpServletResponse response) throws IOException {
        //调用service层方法，查询数据
        List<Activity> activityList=activityService.queryActivityByIds(id);

        //根据查询结果，生成excel文件
        //1.创建HSSFWorkbook对象，对应一个excel文件
        HSSFWorkbook workbook=new HSSFWorkbook();
        //2.使用workbook创建HSSFSheet对象，对应一页
        HSSFSheet sheet=workbook.createSheet("市场活动列表");
        //3.使用sheet创建HSSFRow对象，对应一行
        HSSFRow row=sheet.createRow(0);
        //4.使用row创建HSSFCell对象，对应列
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("名称");
        cell=row.createCell(2);
        cell.setCellValue("所有者");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");

        HSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //5.创建10个HSSFRow对象，对应10行数据
        if(activityList!=null&&activityList.size()>0){
            Activity activity=null;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);

                row=sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getName());
                cell=row.createCell(2);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
            }
        }

        //把生成的excel文件输出到浏览器
        response.setContentType("application/octet-stream;charset=utf-8");
        /*
         *默认情况下，浏览器接收到响应信息之后，处理方式都是直接打开，只有打不开的情况下才会弹出文件下载窗口；
         * 可以这是响应头信息Content-Disposition使浏览器接收到响应信息之后，直接弹出文件下载窗口，即使能打开也不会打开。
         */
        response.addHeader("Content-Disposition","attachment;filename=ActivityList"+DateUtils.getNowTimeStr()+".xls");
        OutputStream out=response.getOutputStream();

        //把workbook生成一个excel文件
        //OutputStream os=new FileOutputStream("E:\\teaching\\section4\\1920\\course\\testDir\\activity.xls");
        workbook.write(out);
        //os.close();
        workbook.close();
    }

    @RequestMapping("/workbench/activity/upload.do")
    public @ResponseBody Object upload(MultipartFile myFile,String username) throws IOException {
        System.out.println("username="+username);


        File file=new File("E:\\teaching\\section4\\1920\\course\\testDir",myFile.getOriginalFilename());
        myFile.transferTo(file);

        Map<String,Object> map=new HashMap<>();
        map.put("code","1");
        return map;
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        Map<String,Object> map=new HashMap<>();

        try {
            InputStream is = activityFile.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            //获取第一页
            HSSFSheet sheet = workbook.getSheetAt(0);
            //获取行
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> list = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                activity = new Activity();

                activity.setCreateBy(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setId(UUIDUtils.getUUID());
                activity.setDescription(getValueFromCell(row.getCell(4)));
                activity.setCost(getValueFromCell(row.getCell(3)));
                activity.setEndDate(getValueFromCell(row.getCell(2)));
                activity.setStartDate(getValueFromCell(row.getCell(1)));
                activity.setName(getValueFromCell(row.getCell(0)));
                activity.setOwner(user.getId());

                list.add(activity);
            }

            if(list.size()>0){
                //调用service层方法，保存数据
                int ret = activityService.saveCreateActivityByList(list);

                if(ret>0){
                    map.put("code",Constants.JSON_RETURN_SUCCESS);
                    map.put("count",ret);
                }else{
                    map.put("code",Constants.JSON_RETURN_FAIL);
                }
            }else{
                map.put("code",Constants.JSON_RETURN_SUCCESS);
                map.put("count",0);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",Constants.JSON_RETURN_FAIL);
        }

        return map;
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

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id, Model model){
        //调用service层方法，查询数据
        Activity activity=activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkByActivityId(id);
        //把数据保存到model中
        model.addAttribute("activity",activity);
        model.addAttribute("activityRemarkList",activityRemarkList);
        //请求转发
        return "workbench/activity/detail";
    }
}
