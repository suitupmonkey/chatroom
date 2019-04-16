package com.suitupmonkey;

import com.suitupmonkey.common.socket.SocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.suitupmonkey.**.*")
@MapperScan("com.suitupmonkey.**.dao")
public class ChatroomApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChatroomApplication.class,args);
        int port = 8082;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new SocketServer().run(port);
    }
}
