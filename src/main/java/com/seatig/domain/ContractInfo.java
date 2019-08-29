package com.seatig.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quanaxy.state.GrantsState;

import java.util.List;

/**
 * @version V1.0
 * @className: ContractInfo
 * @description: 合同信息折叠表返回信息
 * @author: glenn
 * @create: 2019-04-12 17:17
 **/
public class ContractInfo {
    private int total;
    private List<GrantState> rows;

    @Override
    public String toString() {
        return "ContractInfo{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GrantState> getRows() {
        return rows;
    }

    public void setRows(List<GrantState> rows) {
        this.rows = rows;
    }

    public ContractInfo() {
    }

    public ContractInfo(int total, List<GrantState> rows) {
        this.total = total;
        this.rows = rows;
    }
}
