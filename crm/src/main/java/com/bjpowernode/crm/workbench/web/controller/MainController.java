/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-13
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>NAME: MainController</p>
 * @author Administrator
 * @date 2020-03-13 09:41:13
 * @version 1.0
 */
@Controller
public class MainController {
    @RequestMapping("/workbench/main/index.do")
    public String index(){
        return "workbench/main/index";
    }
}
