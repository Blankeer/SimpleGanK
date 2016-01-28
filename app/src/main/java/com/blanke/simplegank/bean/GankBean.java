package com.blanke.simplegank.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Blanke on 16-1-19.
 */
public class GankBean implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.who);
        dest.writeString(this.publishedAt);
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(used ? (byte) 1 : (byte) 0);
        dest.writeString(this.objectId);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeByte(isImage ? (byte) 1 : (byte) 0);
    }

    public GankBean() {
    }

    protected GankBean(Parcel in) {
        this.who = in.readString();
        this.publishedAt = in.readString();
        this.desc = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.objectId = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.isImage = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GankBean> CREATOR = new Parcelable.Creator<GankBean>() {
        public GankBean createFromParcel(Parcel source) {
            return new GankBean(source);
        }

        public GankBean[] newArray(int size) {
            return new GankBean[size];
        }
    };
}
