package com.blanke.simplegank.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.socks.library.KLog;

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
    private Date publishedAt;
    private String desc;
    private String type;
    private String url;
    private boolean used;
    private String _id;
    private Date createdAt;

    private boolean isImage = false;//是否是图片

    public void setWho(String who) {
        this.who = who;
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


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getWho() {
        return who;
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

    public String getUrlName() {
        String[] temp = url.split("/");
        KLog.d(temp[temp.length - 1]);
        return temp[temp.length - 1];
    }

    public boolean isUsed() {
        return used;
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
                ", objectId='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public GankBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.who);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this._id);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeByte(this.isImage ? (byte) 1 : (byte) 0);
    }

    protected GankBean(Parcel in) {
        this.who = in.readString();
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
        this.desc = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this._id = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.isImage = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GankBean> CREATOR = new Parcelable.Creator<GankBean>() {
        @Override
        public GankBean createFromParcel(Parcel source) {
            return new GankBean(source);
        }

        @Override
        public GankBean[] newArray(int size) {
            return new GankBean[size];
        }
    };
}
