/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-11
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.test;

import java.util.UUID;

/**
 * <p>NAME: UUIDTest</p>
 * @author Administrator
 * @date 2020-03-11 11:03:58
 * @version 1.0
 */
public class UUIDTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
}
