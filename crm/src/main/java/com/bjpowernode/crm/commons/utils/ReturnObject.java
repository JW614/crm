/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-06
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.commons.utils;

/**
 * <p>NAME: ReturnObject</p>
 * @author Administrator
 * @date 2020-03-06 10:03:27
 * @version 1.0
 */
public class ReturnObject {
    private String code;//0--失败，1--成功
    private String message;
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
