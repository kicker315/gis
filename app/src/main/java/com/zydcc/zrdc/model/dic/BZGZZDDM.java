package com.zydcc.zrdc.model.dic;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/18:2:06 PM
 * ========================================
 */
@Entity(createInDb = false)
public class BZGZZDDM {
    private String DM;
    private String ID;
    private String MC;
    @Generated(hash = 1672866504)
    public BZGZZDDM(String DM, String ID, String MC) {
        this.DM = DM;
        this.ID = ID;
        this.MC = MC;
    }
    @Generated(hash = 2115928479)
    public BZGZZDDM() {
    }
    public String getDM() {
        return this.DM;
    }
    public void setDM(String DM) {
        this.DM = DM;
    }
    public String getID() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getMC() {
        return this.MC;
    }
    public void setMC(String MC) {
        this.MC = MC;
    }
}
