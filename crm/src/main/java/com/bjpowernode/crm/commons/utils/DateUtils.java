/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-13
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>NAME: DateUtils</p>
 * @author Administrator
 * @date 2020-03-13 11:07:20
 * @version 1.0
 */
public class DateUtils {
    public static String formatDateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date());
    }

    public static String getNowTimeStr(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(new Date());
    }
}
