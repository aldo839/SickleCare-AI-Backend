package com.sicklecare.api.config;

import com.sicklecare.api.services.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AdminService adminService){

        return args -> {
            adminService.initRootAccount();
        };
    }
}
