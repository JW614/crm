/**
 * @项目名：crm
 * @创建人： Administrator
 * @创建时间： 2020-03-14
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.commons.domain;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * <p>NAME: PaginationVO</p>
 * @author Administrator
 * @date 2020-03-14 10:13:04
 * @version 1.0
 */
public class PaginationVO<T> {
    private List<T> dataList;
    private int totalRows;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
