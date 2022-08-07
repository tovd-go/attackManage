package com.tovd.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
public class AttackManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttackManageApplication.class, args);
    }

}
