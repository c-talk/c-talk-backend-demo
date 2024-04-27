package me.a632079.ctalk.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
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
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder ->
                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
                                          .serializerByType(Long.TYPE, ToStringSerializer.instance)
                                          .serializerByType(BigDecimal.class, ToStringSerializer.instance);
    }

}
