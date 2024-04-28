package me.a632079.ctalk.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @className: GroupMember
 * @description: GroupMember - 群组成员
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Document("GroupMember")
public class GroupMember extends BasePo {
    private Long   gid;
    private Long   uid;
    private String alias;
    private String role;
}
