package me.a632079.ctalk.po;

import cn.hutool.crypto.SecureUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.enums.ChatType;
import me.a632079.ctalk.enums.MessageType;
import org.springframework.data.annotation.Transient;

import java.util.Map;
import java.util.Objects;

/**
 * 私聊消息 message:chat:hash(a_id,b_id)
 * 群聊消息 message:group:g_id
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends BasePo {
    private String content;
    private MessageType type;

    @Transient
    private ChatType chatType;

    private Long receiver;
    private Long sender;

    private Map<String, Object> extra_fields;

    @Override
    public String getDocumentName() {
        if (Objects.isNull(chatType)) {
            return "";
        }

        switch (chatType) {
            case Private:
                return "message:chat:" + sort(receiver, sender);
            case Group:
                return "message:group:" + receiver;
            default:
                return "";
        }
    }

    private String sort(Long a, Long b) {
        String res = "";
        if (a > b) {
            return SecureUtil.md5(res + a + b);
        } else {
            return SecureUtil.md5(res + b + a);
        }
    }
}
