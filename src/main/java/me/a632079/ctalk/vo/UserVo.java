package me.a632079.ctalk.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @className: UserVo
 * @description: UserVo - 用户
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class UserVo {
    private Long Id;

    private String nickName;

    @Indexed(unique = true)
    private String email;

    private String avatar;

    private boolean verify;

    private String token;
}
