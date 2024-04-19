package me.a632079.ctalk.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @className: JacksonConfig
 * @description: JacksonConfig - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class JacksonConfig extends SimpleModule {
    public JacksonConfig() {
        //super(JsonModuleConfig.class.getName());
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
    }
}
