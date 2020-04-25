/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-06
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>NAME: WorkbenchController</p>
 * @author Administrator
 * @date 2020-03-06 10:36:36
 * @version 1.0
 */
@Controller
public class WorkbenchController {
    @RequestMapping("/workbench/toIndex.do")
    public String index(){
        return "workbench/index";
    }
}
