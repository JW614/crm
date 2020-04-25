/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-19
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>NAME: ActivityRemarkServiceImpl</p>
 * @author Administrator
 * @date 2020-03-19 10:03:00
 * @version 1.0
 */
@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    /**
     * 根据activityId查询该市场活动下所有的备注
     */
    @Override
    public List<ActivityRemark> queryActivityRemarkByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkByActivityId(activityId);
    }

    @Override
    public int saveCreateActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.insertActivityRemark(remark);
    }

    @Override
    public int saveEditActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.updateActivityRemark(remark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }
}
