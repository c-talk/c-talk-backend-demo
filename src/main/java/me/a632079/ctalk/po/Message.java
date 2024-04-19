package me.a632079.ctalk.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.enums.MessageType;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Message extends BasePo {
    private String content;
    private MessageType type;
    private Long sender;
    private Map<String, Object> extra_fields;
}
