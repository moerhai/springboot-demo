package com.mohai.one.app.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;

@ComponentScan(value = {"com.mohai.one"})
@SpringBootApplication
public class AppStarterApplication implements CommandLineRunner {

    // 定义成静态的和最终的，可以节省空间，提高读取速度。
    private static final Logger LOG = LoggerFactory.getLogger(AppStarterApplication.class);

    @Value("${server.port:8080}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(AppStarterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        LOG.debug("\n=========================================================="
                + "\n The server is started successfully!\t"
                + "\n Access address: http://{}:{}\t"
                + "\n API address: http://{}:{}/{}\t"
                + "\n==========================================================",
                 address.getHostAddress(),port,
                 address.getHostAddress(),port,"swagger-ui.html");
    }
}