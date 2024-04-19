package me.a632079.ctalk.init;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @className: ServerRunner
 * @description: ServerRunner - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@Slf4j
@Component
public class ServerRunner implements CommandLineRunner {
    @Autowired
    private SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {

        log.info("开启websocket 服务器");
        server.startAsync().get();
        log.info("websocket 服务器 开启成功");
    }
}
