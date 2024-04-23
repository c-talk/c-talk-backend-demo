package me.a632079.ctalk.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.minio.*;
import io.minio.errors.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import me.a632079.ctalk.config.MinioConfig;
import me.a632079.ctalk.dto.ResourceDto;
import me.a632079.ctalk.po.ResourcePo;
import me.a632079.ctalk.service.ResourceService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
@Log4j2
public class MinioResourceServiceImpl implements ResourceService {

    private final String COLLECTION_NAME = "resources";

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private MongoTemplate mongoTemplate;


    public boolean existsResource(String id) {
        boolean isExist = this.mongoTemplate.collectionExists(COLLECTION_NAME);
        if (!isExist) return false;
        var po = this.mongoTemplate.findById(
            id,
            ResourcePo.class,
            COLLECTION_NAME
        );
        return po != null;
    }

    @SneakyThrows
    @Override
    public ResourceDto getResource(String resourceName) {
        MinioClient client = this.minioConfig.minioClient();
        boolean isExist = this.mongoTemplate.collectionExists(COLLECTION_NAME);
        if (!isExist) return null;

        ResourcePo resourcePo = this.mongoTemplate.findById(resourceName, ResourcePo.class, COLLECTION_NAME);
        if (resourcePo == null) return null;
        try {
            client.statObject(StatObjectArgs.builder().bucket(this.minioConfig.getBucketName()).object(resourceName).build());
        } catch (ErrorResponseException ex) {
            log.error(ex);
            return null; // not found the object.
        }
        GetObjectResponse response = client.getObject(GetObjectArgs.builder().bucket(this.minioConfig.getBucketName()).object(resourceName).build());
        ResourceDto dto = new ResourceDto();
        // TODO: modify the copyProperties
        BeanUtil.copyProperties(resourcePo, dto);
        dto.setData(
                response.readAllBytes()
        );
        return dto;
    }

    @SneakyThrows
    @Override
    public boolean removeResource(String resourceName) {
        var isExist = existsResource(resourceName);
        if (!isExist) return true;
        var client = this.minioConfig.minioClient();
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(this.minioConfig.getBucketName()).object(resourceName).build());
        } catch (ErrorResponseException ex) {
            log.error(ex);
            return false;
        }
        var criteria = Criteria.where("_id").is(resourceName);
        var query = new Query(criteria);
        var result = mongoTemplate.remove(query, COLLECTION_NAME);
        return result.getDeletedCount() > 0;
    }

    @SneakyThrows
    @Override
    public boolean addResource(ResourceDto dto) {
        var isExist = existsResource(dto.getId());
        if (!isExist) return false;
        Date now = new Date();
        dto.setCreated_at(now);
        dto.setUpdated_at(now);
        InputStream stream = new ByteArrayInputStream(dto.getData());
        var client = minioConfig.minioClient();
        try {
            client.putObject(PutObjectArgs.builder().bucket(minioConfig.getBucketName()).object(dto.getId()).contentType(dto.getMime()).stream(stream,
                    dto.getData().length,
                    -1
            ).build());
        } catch (ErrorResponseException ex) {
            log.error(ex);
            return false;
        }
        return true;
    }
}
