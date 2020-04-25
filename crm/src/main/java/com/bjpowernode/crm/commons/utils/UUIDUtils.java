/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-11
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.commons.utils;

import java.util.UUID;

/**
 * <p>NAME: UUIDUtils</p>
 * @author Administrator
 * @date 2020-03-11 11:08:06
 * @version 1.0
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
