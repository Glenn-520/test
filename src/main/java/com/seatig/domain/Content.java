package com.seatig.domain;

import java.util.Date;

public class Content {
    private int id;
    private String imgUrl;
    private String imgTitle;
    private String imgTxt;
    private int imgUser;
    private Date imgDate;

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgTitle='" + imgTitle + '\'' +
                ", imgTxt='" + imgTxt + '\'' +
                ", imgUser=" + imgUser +
                ", imgDate=" + imgDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getImgTxt() {
        return imgTxt;
    }

    public void setImgTxt(String imgTxt) {
        this.imgTxt = imgTxt;
    }

    public int getImgUser() {
        return imgUser;
    }

    public void setImgUser(int imgUser) {
        this.imgUser = imgUser;
    }

    public Date getImgDate() {
        return imgDate;
    }

    public void setImgDate(Date imgDate) {
        this.imgDate = imgDate;
    }

    public Content(int id, String imgUrl, String imgTitle, String imgTxt, int imgUser, Date imgDate) {

        this.id = id;
        this.imgUrl = imgUrl;
        this.imgTitle = imgTitle;
        this.imgTxt = imgTxt;
        this.imgUser = imgUser;
        this.imgDate = imgDate;
    }

    public Content() {

    }
}
