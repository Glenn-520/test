package com.seatig.domain;

/**
 * @version V1.0
 * @className: Market
 * @description: 市场指标，5个指数
 * @author: glenn
 * @create: 2019-04-17 13:46
 **/
public class Market {
    private int id;
    private String aut;
    private String autClient;
    private String ttv;
    private String ttvClient;
    private String ttvClientHistorical;

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", aut='" + aut + '\'' +
                ", autClient='" + autClient + '\'' +
                ", ttv='" + ttv + '\'' +
                ", ttvClient='" + ttvClient + '\'' +
                ", ttvClientHistorical='" + ttvClientHistorical + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAut() {
        return aut;
    }

    public void setAut(String aut) {
        this.aut = aut;
    }

    public String getAutClient() {
        return autClient;
    }

    public void setAutClient(String autClient) {
        this.autClient = autClient;
    }

    public String getTtv() {
        return ttv;
    }

    public void setTtv(String ttv) {
        this.ttv = ttv;
    }

    public String getTtvClient() {
        return ttvClient;
    }

    public void setTtvClient(String ttvClient) {
        this.ttvClient = ttvClient;
    }

    public String getTtvClientHistorical() {
        return ttvClientHistorical;
    }

    public void setTtvClientHistorical(String ttvClientHistorical) {
        this.ttvClientHistorical = ttvClientHistorical;
    }

    public Market(int id, String aut, String autClient, String ttv, String ttvClient, String ttvClientHistorical) {
        this.id = id;
        this.aut = aut;
        this.autClient = autClient;
        this.ttv = ttv;
        this.ttvClient = ttvClient;
        this.ttvClientHistorical = ttvClientHistorical;
    }

    public Market() {
    }
}
