package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueSerive {
    List<DicValue> queryAllDicValue();

    int saveCreateDicValue(DicValue dicValue);

    DicValue queryDicValueForDetailById(String id);

    int saveEditDicValue(DicValue dicValue);

    int deleteDicValueByIds(String[] ids);
}
