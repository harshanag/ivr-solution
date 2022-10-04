package com.example.ivrsolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IvrsolutionApplication {

    private static Logger logger = LoggerFactory.getLogger(IvrsolutionApplication.class);

    public static void main(String[] args) {
        logger.info("Starting IVR Solutions KPM Records Application...");
        SpringApplication.run(IvrsolutionApplication.class, args);
    }

}
