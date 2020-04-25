/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-10
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicValueSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>NAME: DicValueServiceImpl</p>
 * @author Administrator
 * @date 2020-03-10 11:45:35
 * @version 1.0
 */
@Service("dicValueSerive")
public class DicValueServiceImpl implements DicValueSerive {

    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> queryAllDicValue() {
        return dicValueMapper.selectAllDicValue();
    }

    @Override
    public int saveCreateDicValue(DicValue dicValue) {
        return dicValueMapper.insertDicValue(dicValue);
    }

    @Override
    public DicValue queryDicValueForDetailById(String id) {
        return dicValueMapper.selectDicValueForDetailById(id);
    }

    @Override
    public int saveEditDicValue(DicValue dicValue) {
        return dicValueMapper.updateDicValue(dicValue);
    }

    @Override
    public int deleteDicValueByIds(String[] ids) {
        return dicValueMapper.deleteDicValueByIds(ids);
    }
}
