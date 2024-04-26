package me.a632079.ctalk.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

/**
 * @className: BasePo
 * @description: BasePo - 基础实体类
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class BasePo {
    @MongoId
    private Long id;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    private Long createUid;

    private Long updateUid;

    private boolean isDeleted;

    @JsonIgnore
    public String getDocumentName() {
        return "";
    }
}
