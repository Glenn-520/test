package com.seatig.domain;

import java.util.List;

/**
 * @version V1.0
 * @className: MonitorInfo
 * @description: 监管者前端返回格式
 * @author: glenn
 * @create: 2019-04-12 22:32
 **/
public class MonitorInfo {
    private int total;
    private List<Monitor> rows;

    @Override
    public String toString() {
        return "MonitorInfo{" +
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

    public List<Monitor> getRows() {
        return rows;
    }

    public void setRows(List<Monitor> rows) {
        this.rows = rows;
    }

    public MonitorInfo(int total, List<Monitor> rows) {
        this.total = total;
        this.rows = rows;
    }

    public MonitorInfo() {
    }
}
