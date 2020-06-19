package com.zydcc.zrdc.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/19:8:49 AM
 * ========================================
 */
@Entity
public class ProjectFile {
    @Id
    private Long id;
    private Long projectId;
    private String featureId;
    private String fileName;
    private String fileURL;
    private int fileType;
    private Date createTime;
    private float lon;
    private float lat;
    private float xzb;
    private float yzb;
    private float fwj;
    private long timeLength;
    @Generated(hash = 2138857091)
    public ProjectFile(Long id, Long projectId, String featureId, String fileName,
            String fileURL, int fileType, Date createTime, float lon, float lat,
            float xzb, float yzb, float fwj, long timeLength) {
        this.id = id;
        this.projectId = projectId;
        this.featureId = featureId;
        this.fileName = fileName;
        this.fileURL = fileURL;
        this.fileType = fileType;
        this.createTime = createTime;
        this.lon = lon;
        this.lat = lat;
        this.xzb = xzb;
        this.yzb = yzb;
        this.fwj = fwj;
        this.timeLength = timeLength;
    }
    @Generated(hash = 1404974898)
    public ProjectFile() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProjectId() {
        return this.projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getFeatureId() {
        return this.featureId;
    }
    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileURL() {
        return this.fileURL;
    }
    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
    public int getFileType() {
        return this.fileType;
    }
    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public float getLon() {
        return this.lon;
    }
    public void setLon(float lon) {
        this.lon = lon;
    }
    public float getLat() {
        return this.lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getXzb() {
        return this.xzb;
    }
    public void setXzb(float xzb) {
        this.xzb = xzb;
    }
    public float getYzb() {
        return this.yzb;
    }
    public void setYzb(float yzb) {
        this.yzb = yzb;
    }
    public float getFwj() {
        return this.fwj;
    }
    public void setFwj(float fwj) {
        this.fwj = fwj;
    }
    public long getTimeLength() {
        return this.timeLength;
    }
    public void setTimeLength(long timeLength) {
        this.timeLength = timeLength;
    }
}
