/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-07
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>NAME: DicTypeService</p>
 * @author Administrator
 * @date 2020-03-07 11:59:25
 * @version 1.0
 */
public interface DicTypeService {
    List<DicType> queryAllDicType();

    int saveCreateDicType(DicType dicType);

    DicType queryDicTypeByCode(String code);

    int saveEditDicType(DicType dicType);

    int deleteDicTypeByCodes(String[] codes);
}
