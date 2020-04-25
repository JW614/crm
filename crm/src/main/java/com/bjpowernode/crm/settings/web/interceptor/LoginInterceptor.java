/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-07
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.web.interceptor;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>NAME: LoginInterceptor</p>
 * @author Administrator
 * @date 2020-03-07 10:35:31
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //登录验证
        HttpSession session=httpServletRequest.getSession();
        User user=(User)session.getAttribute(Constants.SESSION_USER);
        if(user==null){
            //重定向
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
