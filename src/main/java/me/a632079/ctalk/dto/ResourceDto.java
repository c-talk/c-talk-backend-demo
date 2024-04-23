package me.a632079.ctalk.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.po.ResourcePo;

@EqualsAndHashCode(callSuper = false)
@Data
public class ResourceDto extends ResourcePo {
    private byte[] data;
}
