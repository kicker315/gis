package com.zydcc.zrdc.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/18:2:07 PM
 * ========================================
 */
@Entity
public class Layer implements Parcelable {
    @Id
    private Long id;
    private String fillColor;
    private int isBaseMap;
    private int isEdit;
    private int isLabel;
    private int isSelect;
    private int isShow;
    private String labelColor;
    private String labelField;
    private Long layerId;
    private int layerIndex;
    private String layerName;
    private String layerStatus;
    private String layerType;
    private String layerUrl;
    private String lineColor;
    private Long projectId;
    @Generated(hash = 620622243)
    public Layer(Long id, String fillColor, int isBaseMap, int isEdit, int isLabel,
            int isSelect, int isShow, String labelColor, String labelField,
            Long layerId, int layerIndex, String layerName, String layerStatus,
            String layerType, String layerUrl, String lineColor, Long projectId) {
        this.id = id;
        this.fillColor = fillColor;
        this.isBaseMap = isBaseMap;
        this.isEdit = isEdit;
        this.isLabel = isLabel;
        this.isSelect = isSelect;
        this.isShow = isShow;
        this.labelColor = labelColor;
        this.labelField = labelField;
        this.layerId = layerId;
        this.layerIndex = layerIndex;
        this.layerName = layerName;
        this.layerStatus = layerStatus;
        this.layerType = layerType;
        this.layerUrl = layerUrl;
        this.lineColor = lineColor;
        this.projectId = projectId;
    }
    @Generated(hash = 378671819)
    public Layer() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFillColor() {
        return this.fillColor;
    }
    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }
    public int getIsBaseMap() {
        return this.isBaseMap;
    }
    public void setIsBaseMap(int isBaseMap) {
        this.isBaseMap = isBaseMap;
    }
    public int getIsEdit() {
        return this.isEdit;
    }
    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }
    public int getIsLabel() {
        return this.isLabel;
    }
    public void setIsLabel(int isLabel) {
        this.isLabel = isLabel;
    }
    public int getIsSelect() {
        return this.isSelect;
    }
    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }
    public int getIsShow() {
        return this.isShow;
    }
    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
    public String getLabelColor() {
        return this.labelColor;
    }
    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }
    public String getLabelField() {
        return this.labelField;
    }
    public void setLabelField(String labelField) {
        this.labelField = labelField;
    }
    public Long getLayerId() {
        return this.layerId;
    }
    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }
    public int getLayerIndex() {
        return this.layerIndex;
    }
    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }
    public String getLayerName() {
        return this.layerName;
    }
    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }
    public String getLayerStatus() {
        return this.layerStatus;
    }
    public void setLayerStatus(String layerStatus) {
        this.layerStatus = layerStatus;
    }
    public String getLayerType() {
        return this.layerType;
    }
    public void setLayerType(String layerType) {
        this.layerType = layerType;
    }
    public String getLayerUrl() {
        return this.layerUrl;
    }
    public void setLayerUrl(String layerUrl) {
        this.layerUrl = layerUrl;
    }
    public String getLineColor() {
        return this.lineColor;
    }
    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }
    public Long getProjectId() {
        return this.projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.fillColor);
        dest.writeInt(this.isBaseMap);
        dest.writeInt(this.isEdit);
        dest.writeInt(this.isLabel);
        dest.writeInt(this.isSelect);
        dest.writeInt(this.isShow);
        dest.writeString(this.labelColor);
        dest.writeString(this.labelField);
        dest.writeValue(this.layerId);
        dest.writeInt(this.layerIndex);
        dest.writeString(this.layerName);
        dest.writeString(this.layerStatus);
        dest.writeString(this.layerType);
        dest.writeString(this.layerUrl);
        dest.writeString(this.lineColor);
        dest.writeValue(this.projectId);
    }

    protected Layer(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.fillColor = in.readString();
        this.isBaseMap = in.readInt();
        this.isEdit = in.readInt();
        this.isLabel = in.readInt();
        this.isSelect = in.readInt();
        this.isShow = in.readInt();
        this.labelColor = in.readString();
        this.labelField = in.readString();
        this.layerId = (Long) in.readValue(Long.class.getClassLoader());
        this.layerIndex = in.readInt();
        this.layerName = in.readString();
        this.layerStatus = in.readString();
        this.layerType = in.readString();
        this.layerUrl = in.readString();
        this.lineColor = in.readString();
        this.projectId = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Layer> CREATOR = new Parcelable.Creator<Layer>() {
        @Override
        public Layer createFromParcel(Parcel source) {
            return new Layer(source);
        }

        @Override
        public Layer[] newArray(int size) {
            return new Layer[size];
        }
    };
}
