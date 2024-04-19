package me.a632079.ctalk.po;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

/**
 * @className: BasePo
 * @description: BasePo - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class BasePo {
    @MongoId
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUid;

    private Long updateUid;

    private boolean isDeleted;
}
