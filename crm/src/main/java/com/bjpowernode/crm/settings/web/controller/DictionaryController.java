/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-07
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>NAME: DictionaryController</p>
 * @author Administrator
 * @date 2020-03-07 11:20:43
 * @version 1.0
 */
@Controller
public class DictionaryController {
    @RequestMapping("/settings/dictionary/index.do")
    public String index(){
        return "settings/dictionary/index";
    }
}
