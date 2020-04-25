/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-10
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.utils.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/**
 * <p>NAME: DictionaryValueController</p>
 * @author Administrator
 * @date 2020-03-10 11:46:45
 * @version 1.0
 */
@Controller
public class DictionaryValueController {

    @Autowired
    private DicValueSerive dicValueSerive;

    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("/settings/dictionary/value/index.do")
    public String index(Model model){
        //调用service层方法，查询数据
        List<DicValue> dicValueList=dicValueSerive.queryAllDicValue();

        model.addAttribute("dicValueList",dicValueList);

        return "settings/dictionary/value/index";
    }

    @RequestMapping("/settings/dictionary/value/toSaveDicValue.do")
    public String toSaveDicValue(Model model){
        //查询所有的数据字典类型列表
        List<DicType> dicTypeList=dicTypeService.queryAllDicType();

        //把数据保存到model中
        model.addAttribute("dicTypeList",dicTypeList);

        //请求转发
        return "settings/dictionary/value/save";
    }

    @RequestMapping("/settings/dictionary/value/saveCreateDicValue.do")
    public @ResponseBody Object saveCreateDicValue(DicValue dicValue){
        //封装参数
        dicValue.setId(UUIDUtils.getUUID());

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法保存数据
            int ret = dicValueSerive.saveCreateDicValue(dicValue);
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

    @RequestMapping("/settings/dictionary/value/toEditDicValue.do")
    public String toEditDicValue(String id,Model model){
        //调用service层方法，查询记录
        DicValue dicValue=dicValueSerive.queryDicValueForDetailById(id);

        //把数据保存到model中
        model.addAttribute("dicValue",dicValue);

        return "settings/dictionary/value/edit";
    }

    @RequestMapping("/settings/dictionary/value/saveEditDicValue.do")
    public @ResponseBody Object saveEditDicValue(DicValue dicValue){

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，修改数据
            int ret = dicValueSerive.saveEditDicValue(dicValue);

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

    @RequestMapping("/settings/dictionary/value/deleteDicValueByIds.do")
    public @ResponseBody Object deleteDicValueByIds(String[] id){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，删除数据
            int ret = dicValueSerive.deleteDicValueByIds(id);
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
