package me.a632079.ctalk.service;

import me.a632079.ctalk.po.BasePo;

import java.util.List;

/**
 * @className: DynamicRepository
 * @description: DynamicRepository - 动态mongo document名称
 * @version: v1.0.0
 * @author: haoduor
 */


public interface DynamicRepository<T extends BasePo> {
    boolean add(T data);
    boolean addAll(List<T> dataList);
}
