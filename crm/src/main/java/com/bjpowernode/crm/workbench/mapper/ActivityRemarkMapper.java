package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Thu Mar 19 09:40:35 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Thu Mar 19 09:40:35 CST 2020
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Thu Mar 19 09:40:35 CST 2020
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Thu Mar 19 09:40:35 CST 2020
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Thu Mar 19 09:40:35 CST 2020
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Thu Mar 19 09:40:35 CST 2020
     */
    int updateByPrimaryKey(ActivityRemark record);

    /**
     * 根据acitvityId查询该市场活动下所有的备注
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectActivityRemarkByActivityId(String activityId);

    /**
     * 根据activityId[] 删除这些市场活动下所有的备注
     * @param activityIds
     * @return
     */
    int deleteActivityRemarkByActivityIds(String[] activityIds);

    /**
     * 添加创建市场活动备注
     * @param remark
     * @return
     */
    int insertActivityRemark(ActivityRemark remark);

    /**
     * 保存修改的市场活动备注
     * @param remark
     * @return
     */
    int updateActivityRemark(ActivityRemark remark);

    /**
     * 根据id删除市场活动备注
     * @param id
     * @return
     */
    int deleteActivityRemarkById(String id);
}