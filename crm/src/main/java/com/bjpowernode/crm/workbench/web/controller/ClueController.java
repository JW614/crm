/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-21
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>NAME: ClueController</p>
 * @author Administrator
 * @date 2020-03-21 11:52:58
 * @version 1.0
 */
@Controller
public class ClueController {

    @Autowired
    private ClueService clueService;

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，保存数据
            int ret = clueService.saveCreateClue(clue);
            if(ret>0){
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
            }else{
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }

        return returnObject;
    }
}
