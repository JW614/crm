/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-07
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.utils.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>NAME: DictionaryTypeController</p>
 * @author Administrator
 * @date 2020-03-07 11:29:04
 * @version 1.0
 */
@Controller
public class DictionaryTypeController {

    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("/settings/dictionary/type/index.do")
    public String index(Model model){
        //查询数据字典类型列表
        List<DicType> dicTypeList=dicTypeService.queryAllDicType();

        model.addAttribute("dicTypeList",dicTypeList);

        return "settings/dictionary/type/index";
    }

    @RequestMapping("settings/dictionary/type/saveCreateDicType.do")
    public @ResponseBody Object saveCreateDicType(DicType dicType){
        ReturnObject returnObject=new ReturnObject();
        try {
            //保存数据
            int ret = dicTypeService.saveCreateDicType(dicType);
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

    @RequestMapping("/settings/dictionary/type/toSave.do")
    public String toSave(){
        return "settings/dictionary/type/save";
    }

    @RequestMapping("/settings/dictionary/type/checkCode.do")
    public @ResponseBody Object checkCode(String code){
        //根据code查询记录
        DicType dicType=dicTypeService.queryDicTypeByCode(code);

        //根据查询结果生成响应信息
        ReturnObject returnObject=new ReturnObject();
        if(dicType==null){
            returnObject.setCode(Constants.JSON_RETURN_SUCCESS);
        }else{
            returnObject.setCode(Constants.JSON_RETURN_FAIL);
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/toEditDicType.do")
    public String toEditDicType(String code,Model model){
        //调用service层方法查询记录
        DicType dicType=dicTypeService.queryDicTypeByCode(code);
        //把记录保存到model中
        model.addAttribute("dicType",dicType);
        //请求转发
        return "settings/dictionary/type/edit";
    }

    @RequestMapping("/settings/dictionary/type/saveEditDicType.do")
    public @ResponseBody Object saveEditDicType(DicType dicType){
        ReturnObject returnObject=new ReturnObject();

        try {
            //调用service层方法保存数据
            int ret = dicTypeService.saveEditDicType(dicType);

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

    @RequestMapping("/settings/dictionary/type/deleteDicTypeByCodes.do")
    public @ResponseBody Object deleteDicTypeByCodes(String[] code){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，删除数据
            int ret = dicTypeService.deleteDicTypeByCodes(code);
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
