package me.a632079.ctalk.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: WebSocketConfig
 * @description: WebSocketConfig - websocket服务器
 * @version: v1.0.0
 * @author: haoduor
 */


@Data
@Configuration
public class WebSocketConfig {

    @Value("${ws.server.host}")
    private String host;

    @Value("${ws.server.port}")
    private int port;

    @Bean
    public SocketIOServer server() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);

        //这个listener可以用来进行身份验证
        config.setAuthorizationListener(data -> {
            // http://host:${}?token=xxxxxxx
            // 例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
            String token = data.getSingleUrlParam("token");
            // 校验token的合法性，实际业务需要校验token是否过期等等，参考 spring-boot-demo-rbac-security 里的 JwtUtil
            // 如果认证不通过会返回一个 Socket.EVENT_CONNECT_ERROR 事件

            return true;
        });

        return new SocketIOServer(config);
    }

    // 只有为websocket配置了注解扫描器,
    // 对应的注解扫描器才能正常调用@OnConnect, @OnDisconnect, @OnEvent, 等方法
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
