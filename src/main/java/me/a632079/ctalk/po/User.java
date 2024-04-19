package me.a632079.ctalk.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @className: User
 * @description: User - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("user")
public class User {

    @MongoId
    private Long id;

    private String nickName;

    @JsonIgnore
    private String password;

    private String email;

    private String avatar;

    private boolean verify;
}
