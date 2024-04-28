package me.a632079.ctalk.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Group extends BasePo {
    private String     name;
    private String     code;
    private String     desc;
    private String     head_pic;
    private List<Long> member_list;
    private Long       owner;
}
