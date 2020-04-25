/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-05
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>NAME: IndexController</p>
 * @author Administrator
 * @date 2020-03-05 10:06:38
 * @version 1.0
 */
@Controller
public class IndexController {//http://localhost:8080/crm/
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
