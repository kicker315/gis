package com.zydcc.arcgis.utils;

/**
 * =======================================
 * 过滤器接口
 * Create by ningsikai 2020/6/4:4:59 PM
 * ========================================
 */
@FunctionalInterface
public interface Filter<T> {
    /**
     * 是否接受对象
     * @param t 检查的对象
     * @return 是否接受对象
     */
    boolean accept(T t);
}