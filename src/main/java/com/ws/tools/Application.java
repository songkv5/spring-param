package com.ws.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:12
 */
@SpringBootApplication(scanBasePackages = "com")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
