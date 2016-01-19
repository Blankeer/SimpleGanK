package com.blanke.simplegank.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blanke on 16-1-18.
 * 分类：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all |随机
 */
public class CateGoryBean implements Comparable<CateGoryBean>, Parcelable {
    private int order;//排序
    private String name;
    private String type;
    private String path;//url
    private int id;
    private String iconName;//图标资源name
    private int iconResId;//图标资源id
    private int layoutResId;//layout布局id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CateGoryBean that = (CateGoryBean) o;

        return type.equals(that.type);

    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "CateGoryBean{" +
                "id=" + id +
                ", order=" + order +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
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

    public String getIconName() {
        return iconName;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(CateGoryBean another) {
        return order - another.order;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.order);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.path);
        dest.writeInt(this.id);
        dest.writeString(this.iconName);
        dest.writeInt(this.iconResId);
        dest.writeInt(this.layoutResId);
    }

    public CateGoryBean() {
    }

    protected CateGoryBean(Parcel in) {
        this.order = in.readInt();
        this.name = in.readString();
        this.type = in.readString();
        this.path = in.readString();
        this.id = in.readInt();
        this.iconName = in.readString();
        this.iconResId = in.readInt();
        this.layoutResId = in.readInt();
    }

    public static final Parcelable.Creator<CateGoryBean> CREATOR = new Parcelable.Creator<CateGoryBean>() {
        public CateGoryBean createFromParcel(Parcel source) {
            return new CateGoryBean(source);
        }

        public CateGoryBean[] newArray(int size) {
            return new CateGoryBean[size];
        }
    };
}
