/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-12
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.commons.domain.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * <p>NAME: ActivityService</p>
 * @author Administrator
 * @date 2020-03-12 11:57:47
 * @version 1.0
 */
public interface ActivityService {
    int saveCreateActivity(Activity activity);

    PaginationVO<Activity> queryActivityForPageByCondition(Map<String,Object> map);

    Activity queryActivityById(String id);

    int saveEditActivity(Activity activity);

    int deleteActivityByIds(String[] ids);

    List<Activity> queryAllActivity();

    List<Activity> queryActivityByIds(String[] ids);

    int saveCreateActivityByList(List<Activity> list);

    Activity queryActivityForDetailById(String id);
}
