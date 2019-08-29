package com.seatig.domain;

/**
 * @version V1.0
 * @className: Monitor
 * @description: 监管者信息
 * @author: glenn
 * @create: 2019-04-12 22:16
 **/
public class Monitor {
    private int id;
    private String name;
    private String nodeName;
    private int contractNum;
    private String contractTotalAmount;
    private String date;

    public Monitor(int id, String name, String nodeName, int contractNum, String contractTotalAmount, String date) {
        this.id = id;
        this.name = name;
        this.nodeName = nodeName;
        this.contractNum = contractNum;
        this.contractTotalAmount = contractTotalAmount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", contractNum=" + contractNum +
                ", contractTotalAmount=" + contractTotalAmount +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getContractNum() {
        return contractNum;
    }

    public void setContractNum(int contractNum) {
        this.contractNum = contractNum;
    }

    public String getContractTotalAmount() {
        return contractTotalAmount;
    }

    public void setContractTotalAmount(String contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Monitor() {
    }
}
