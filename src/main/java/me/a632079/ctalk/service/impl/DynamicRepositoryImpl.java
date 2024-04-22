package me.a632079.ctalk.service.impl;

import lombok.RequiredArgsConstructor;
import me.a632079.ctalk.po.BasePo;
import me.a632079.ctalk.service.DynamicRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: DynamicRepositoryImpl
 * @description: DynamicRepositoryImpl - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@Service
@RequiredArgsConstructor
public class DynamicRepositoryImpl<T extends BasePo> implements DynamicRepository<T> {

    private final MongoTemplate template;

    @Override
    public boolean add(T data) {
        try {
            template.insert(data, data.getDocumentName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addAll(List<T> dataList) {
        try {
            template.insert(dataList, dataList.get(0).getDocumentName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
