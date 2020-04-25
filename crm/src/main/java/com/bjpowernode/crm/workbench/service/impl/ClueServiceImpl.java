/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-21
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>NAME: ClueServiceImpl</p>
 * @author Administrator
 * @date 2020-03-21 11:51:22
 * @version 1.0
 */
@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }
}
