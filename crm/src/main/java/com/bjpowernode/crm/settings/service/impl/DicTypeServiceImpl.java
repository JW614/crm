/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-07
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>NAME: DicTypeServiceImpl</p>
 * @author Administrator
 * @date 2020-03-07 12:00:10
 * @version 1.0
 */
@Service("dicTypeService")
public class DicTypeServiceImpl implements DicTypeService {

    @Autowired
    private DicTypeMapper dicTypeMapper;

    @Override
    public List<DicType> queryAllDicType() {
        return dicTypeMapper.selectAllDicType();
    }

    @Override
    public int saveCreateDicType(DicType dicType) {
        return dicTypeMapper.insertDicType(dicType);
    }

    @Override
    public DicType queryDicTypeByCode(String code) {
        return dicTypeMapper.selectDicTypeByCode(code);
    }

    @Override
    public int saveEditDicType(DicType dicType) {
        return dicTypeMapper.updateDicType(dicType);
    }

    @Override
    public int deleteDicTypeByCodes(String[] codes) {
        return dicTypeMapper.deleteDicTypeByCodes(codes);
    }
}
