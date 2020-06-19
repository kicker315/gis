package com.zydcc.zrdc.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * =======================================
 * 项目
 * Create by ningsikai 2020/6/19:8:37 AM
 * ========================================
 */
@Entity
public class Project implements Parcelable {
    @Id
    private Long projectId;
    private String projectName;
    private String projectMan;
    private String projectProgress;
    private Date startTime;
    private Date lastEditTime;
    private String url;
    private int featureNum;
    private int doneFeatureNum;
    private String coordinate;
    @Transient
    private List<Map<Layer, Integer>> layerList;
    @Generated(hash = 1114602875)
    public Project(Long projectId, String projectName, String projectMan,
            String projectProgress, Date startTime, Date lastEditTime, String url,
            int featureNum, int doneFeatureNum, String coordinate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectMan = projectMan;
        this.projectProgress = projectProgress;
        this.startTime = startTime;
        this.lastEditTime = lastEditTime;
        this.url = url;
        this.featureNum = featureNum;
        this.doneFeatureNum = doneFeatureNum;
        this.coordinate = coordinate;
    }
    @Generated(hash = 1767516619)
    public Project() {
    }
    public Long getProjectId() {
        return this.projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectMan() {
        return this.projectMan;
    }
    public void setProjectMan(String projectMan) {
        this.projectMan = projectMan;
    }
    public String getProjectProgress() {
        return this.projectProgress;
    }
    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }
    public Date getStartTime() {
        return this.startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getLastEditTime() {
        return this.lastEditTime;
    }
    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getFeatureNum() {
        return this.featureNum;
    }
    public void setFeatureNum(int featureNum) {
        this.featureNum = featureNum;
    }
    public int getDoneFeatureNum() {
        return this.doneFeatureNum;
    }
    public void setDoneFeatureNum(int doneFeatureNum) {
        this.doneFeatureNum = doneFeatureNum;
    }
    public String getCoordinate() {
        return this.coordinate;
    }
    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setLayerList(List<Map<Layer, Integer>> layerList) {
        this.layerList = layerList;
    }

    public List<Map<Layer, Integer>> getLayerList() {
        return layerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.projectId);
        dest.writeString(this.projectName);
        dest.writeString(this.projectMan);
        dest.writeString(this.projectProgress);
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeLong(this.lastEditTime != null ? this.lastEditTime.getTime() : -1);
        dest.writeString(this.url);
        dest.writeInt(this.featureNum);
        dest.writeInt(this.doneFeatureNum);
        dest.writeString(this.coordinate);
    }

    protected Project(Parcel in) {
        this.projectId = (Long) in.readValue(Long.class.getClassLoader());
        this.projectName = in.readString();
        this.projectMan = in.readString();
        this.projectProgress = in.readString();
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpLastEditTime = in.readLong();
        this.lastEditTime = tmpLastEditTime == -1 ? null : new Date(tmpLastEditTime);
        this.url = in.readString();
        this.featureNum = in.readInt();
        this.doneFeatureNum = in.readInt();
        this.coordinate = in.readString();
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
