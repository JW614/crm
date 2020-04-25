/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-20
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>NAME: ActivityRemarkController</p>
 * @author Administrator
 * @date 2020-03-20 09:35:17
 * @version 1.0
 */
@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark remark, HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        //封装参数
        remark.setEditFlag("0");
        remark.setId(UUIDUtils.getUUID());
        remark.setCreateTime(DateUtils.formatDateTime(new Date()));
        remark.setCreateBy(user.getId());

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，保存数据
            int ret = activityRemarkService.saveCreateActivityRemark(remark);
            if(ret>0){
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
                returnObject.setData(remark);
            }else{
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public @ResponseBody Object saveEditActivityRemark(ActivityRemark remark,HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        //封装参数
        remark.setEditFlag("1");
        remark.setEditTime(DateUtils.formatDateTime(new Date()));
        remark.setEditBy(user.getId());

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，修改数据
            int ret = activityRemarkService.saveEditActivityRemark(remark);
            if(ret>0){
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
                returnObject.setData(remark);
            }else{
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody Object deleteActivityRemarkById(String id){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，删除数据
            int ret = activityRemarkService.deleteActivityRemarkById(id);
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
}
