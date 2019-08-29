package com.seatig.domain;

/**
 * @version V1.0
 * @className: ClientTrend
 * @description: 客户趋势饼状图
 * @author: glenn
 * @create: 2019-04-12 15:51
 **/
public class ClientTrend {
    private int id;
    private int autClient;
    private int ttvClient;
    private int ttvClientHistorical;

    @Override
    public String toString() {
        return "ClientTrend{" +
                "id=" + id +
                ", autClient=" + autClient +
                ", ttvClient=" + ttvClient +
                ", ttvClientHistorical=" + ttvClientHistorical +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAutClient() {
        return autClient;
    }

    public void setAutClient(int autClient) {
        this.autClient = autClient;
    }

    public int getTtvClient() {
        return ttvClient;
    }

    public void setTtvClient(int ttvClient) {
        this.ttvClient = ttvClient;
    }

    public int getTtvClientHistorical() {
        return ttvClientHistorical;
    }

    public void setTtvClientHistorical(int ttvClientHistorical) {
        this.ttvClientHistorical = ttvClientHistorical;
    }

    public ClientTrend(int id, int autClient, int ttvClient, int ttvClientHistorical) {
        this.id = id;
        this.autClient = autClient;
        this.ttvClient = ttvClient;
        this.ttvClientHistorical = ttvClientHistorical;
    }

    public ClientTrend() {
    }
}
