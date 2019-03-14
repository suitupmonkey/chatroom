package com.suitupmonkey;

import com.suitupmonkey.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan(basePackages = "com.**.*")
public class ChatroomApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChatroomApplication.class,args);
        int port = 8081;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new SocketServer().run(port);
    }
}
