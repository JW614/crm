/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-12
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.domain.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>NAME: ActivityServiceImpl</p>
 * @author Administrator
 * @date 2020-03-12 11:58:23
 * @version 1.0
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public PaginationVO<Activity> queryActivityForPageByCondition(Map<String, Object> map) {
        //调用mapper层方法，查询数据
        List<Activity> activityList=activityMapper.selectActivityForPageByCondition(map);
        int totalRows=activityMapper.selectCountOfActivityByCondition(map);
        //把数据封装成vo对象
        PaginationVO<Activity> vo=new PaginationVO<>();
        vo.setDataList(activityList);
        vo.setTotalRows(totalRows);

        return vo;
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        //首先删除这些市场活动下所有的备注
        activityRemarkMapper.deleteActivityRemarkByActivityIds(ids);
        //删除备注
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public List<Activity> queryAllActivity() {
        return activityMapper.selectAllActivity();
    }

    @Override
    public List<Activity> queryActivityByIds(String[] ids) {
        return activityMapper.selectActivityByIds(ids);
    }

    @Override
    public int saveCreateActivityByList(List<Activity> list) {
        return activityMapper.insertActivityByList(list);
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }
}
