package me.a632079.ctalk;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@EnableNacosDiscovery
@EnableNacosConfig
@NacosPropertySource(dataId = "c-talk-demo", type = ConfigType.YAML, autoRefreshed = true)
@SpringBootApplication
public class CTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CTalkApplication.class, args);
	}

}
