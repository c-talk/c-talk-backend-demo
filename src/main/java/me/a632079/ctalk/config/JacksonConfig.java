package me.a632079.ctalk.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;
import org.apache.tomcat.jni.Local;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @className: JacksonConfig
 * @description: JacksonConfig - jackson格式化
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class JacksonConfig extends SimpleModule {
    public JacksonConfig() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        // this.addSerializer(LocalDateTime.class, new ToStringSerializerBase(LocalDateTime.class) {
        //
        //     @Override
        //     public String valueToString(Object o) {
        //         return null;
        //     }
        // });
    }
}
