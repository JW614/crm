/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-05
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.utils.ReturnObject;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>NAME: UserController</p>
 * @author Administrator
 * @date 2020-03-05 10:29:23
 * @version 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        //查询用户
        User user=userService.queryUserByActPwd(map);

        ReturnObject returnObject=new ReturnObject();

        //处理结果
        if(user==null){
            //账号或密码错误
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
            returnObject.setMessage("账号或密码错误");
        }else{
            //账号是否过期
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String now=sdf.format(new Date());

            String ip=request.getRemoteAddr();

            if(now.compareTo(user.getExpireTime())>0){
                //登录失败，账号已过期
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
                returnObject.setMessage("账号已过期");
            }else if(!user.getAllowIps().contains(ip)){
                //登录失败 ip受限
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
                returnObject.setMessage("ip受限");
            }else if("0".equals(user.getLockState())){
                //登录失败，用户状态被锁定
                returnObject.setCode(Constants.JSON_RETURN_FAIL);
                returnObject.setMessage("用户状态被锁定");
            }else{
                //登录成功
                returnObject.setCode(Constants.JSON_RETURN_SUCCESS);

                //把user保存到session中
                session.setAttribute(Constants.SESSION_USER,user);

                //记住密码
                if("true".equals(isRemPwd)){
                    //把loginAct和loginPwd以cookie形式写到浏览器
                    Cookie c1=new Cookie("loginAct",loginAct);
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //把loginAct和loginPwd删除
                    Cookie c1=new Cookie("loginAct",loginAct);
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }

        return returnObject;
    }

    @RequestMapping("settings/qx/user/logout.do")
    public String logout(HttpSession session,HttpServletResponse response){
        //销毁session
        session.invalidate();

        //清空cookie
        Cookie c1=new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2=new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);

        return "redirect:/";
    }
}
