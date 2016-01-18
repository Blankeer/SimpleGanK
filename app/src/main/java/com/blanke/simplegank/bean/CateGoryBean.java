package com.blanke.simplegank.bean;

/**
 * Created by blanke on 16-1-18.
 * 分类：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all |随机
 */
public class CateGoryBean implements Comparable<CateGoryBean> {
    private int order;//排序
    private String name;
    private String type;//url的type
    private int id;
    private String iconName;

    public CateGoryBean(int id, String name, int order, String type) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.type = type;
    }

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
}
