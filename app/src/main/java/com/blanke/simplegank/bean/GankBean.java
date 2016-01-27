package com.blanke.simplegank.bean;

import java.util.Date;

/**
 * Created by Blanke on 16-1-19.
 */
public class GankBean {

    /**
     * who : mthli
     * publishedAt : 2016-01-18T04:58:00.925Z
     * desc : 动态生成三角形背景的库
     * type : Android
     * url : https://github.com/manolovn/trianglify
     * used : true
     * objectId : 569b94fa60b219d10180629e
     * createdAt : 2016-01-17T13:19:54.369Z
     * updatedAt : 2016-01-18T04:58:01.657Z
     */

    private String who;
    private String publishedAt;
    private String desc;
    private String type;
    private String url;
    private boolean used;
    private String objectId;
    private Date createdAt;
    private Date updatedAt;

    private boolean isImage = false;//是否是图片

    public void setWho(String who) {
        this.who = who;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isImage() {
        if (isImage) {
            return true;
        }
        if (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif")) {
            isImage = true;
        }
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getWho() {
        return who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getObjectId() {
        return objectId;
    }


    @Override
    public String toString() {
        return "GankBean{" +
                "who='" + who + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", objectId='" + objectId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
