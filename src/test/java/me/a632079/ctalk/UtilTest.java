package me.a632079.ctalk;

import cn.hutool.core.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.util.MessageUtil;
import org.junit.jupiter.api.Test;

/**
 * @className: UtilTest
 * @description: UtilTest - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
public class UtilTest {

    @Test
    public void hashUtilTest() {
        Long a = 12313134L;
        Long b = 12312219033L;

        System.out.println(MessageUtil.hash(a, b));
        System.out.println(MessageUtil.hash(b, a));
    }
}
