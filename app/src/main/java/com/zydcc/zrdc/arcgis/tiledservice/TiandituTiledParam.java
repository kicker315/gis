package com.zydcc.zrdc.arcgis.tiledservice;

import com.zydcc.zrdc.arcgis.GisMapConfig;

import java.util.Random;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/2:9:48 AM
 * ========================================
 */
public class TiandituTiledParam extends BaseTiledParam {
    private static final double[] RES   = {0.703125, 0.3515625, 0.17578125, 0.087890625, 0.0439453125, 0.02197265625, 0.010986328125, 0.0054931640625, 0.00274658203125, 0.001373291015625,
            0.0006866455078125, 0.00034332275390625, 0.000171661376953125, 8.58306884765629E-05, 4.29153442382814E-05, 2.14576721191407E-05, 1.07288360595703E-05, 5.36441802978516E-06};
    private static final double[] SCALE = {295829355.4545656E8, 147748799.285417, 73874399.6427087, 36937199.8213544, 18468599.9106772, 9234299.95533859, 4617149.97766929, 2308574.98883465,
            1154287.49441732, 577143.747208662, 288571.873604331, 144285.936802165, 72142.9684010827, 36071.4842005414, 18035.7421002707, 9017.87105013534, 4508.93552506767, 2256.998866688275};

    private static final double[] ORIGN_POINT = {-180, 90};
    private static final double[] FULL_EXTENT = {-180, -90, 180, 90};
    private static final int      WKID        = 4326;

    public TiandituTiledParam(GisMapConfig gisMapConfig) {
        super(gisMapConfig);
    }

    @Override
    public double[] getRes() {
        return RES;
    }

    @Override
    public double[] getScale() {
        return SCALE;
    }

    @Override
    public double getInitScale() {
        return gisMapConfig.getInitScale() == 0 ? 76185.66237111654 : gisMapConfig.getInitScale();
    }

    @Override
    public double[] getCenterPoint() {
        return gisMapConfig.getCenterPoint() == null ? new double[]{117.024967066, 36.6827847272} : gisMapConfig.getCenterPoint();
    }

    @Override
    public double[] getFullExtent() {
        return gisMapConfig.getFullExtent() == null ? FULL_EXTENT : gisMapConfig.getFullExtent();
    }

    @Override
    public double[] getOrignPoint() {
        return ORIGN_POINT;
    }

    @Override
    public int getWkid() {
        return WKID;
    }

    @Override
    public String getUrl(int level, int col, int row, BaseTiledType tiledType) {
        StringBuilder url = new StringBuilder("http://t");
        Random random = new Random();
        int subdomain = (random.nextInt(6) + 1);
        url.append(subdomain);
        switch (tiledType) {
            case VEC_C:
                url.append(".tianditu.com/DataServer?T=vec_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level).append("&tk=").append("b5ca85bce62f83611e5b4135f4fe1bc5");
                break;
            case CVA_C:
                url.append(".tianditu.com/DataServer?T=cva_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level).append("&tk=").append("b5ca85bce62f83611e5b4135f4fe1bc5");
                break;
            case CIA_C:
                url.append(".tianditu.com/DataServer?T=cia_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level).append("&tk=").append("b5ca85bce62f83611e5b4135f4fe1bc5");
                break;
            case IMG_C:
                url.append(".tianditu.com/DataServer?T=img_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level).append("&tk=").append("b5ca85bce62f83611e5b4135f4fe1bc5");
                break;
            default:
                return null;
        }
        return url.toString();
    }

    @Override
    public BaseTiledLayer[] getVecBaseTileLayer() {
        BaseTiledLayer baseLayer[] = new BaseTiledLayer[2];
        baseLayer[0] = BaseTiledLayer.createLayer(this, BaseTiledType.VEC_C);
        baseLayer[1] = BaseTiledLayer.createLayer(this, BaseTiledType.CVA_C);
        return baseLayer;
    }

    @Override
    public BaseTiledLayer[] getImgBaseTileLayer() {
        BaseTiledLayer baseLayer[] = new BaseTiledLayer[2];
        baseLayer[0] = BaseTiledLayer.createLayer(this, BaseTiledType.IMG_C);
        baseLayer[1] = BaseTiledLayer.createLayer(this, BaseTiledType.CIA_C);
        return baseLayer;
    }
}

