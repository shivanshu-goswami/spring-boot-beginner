package com.demo.first;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

//CommandLineRunner is a type of functional interface which is used when we want some
//code to run at the spring boot application startup
//defn : this interface indicates that a bean should run when it is contained within
// a spring application. Mutliple commandLineRunner can be defined within the same
//application context and can be ordered using the Ordered interface or @Order annotation
@Component
public class AppStarterRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application has started using CommandLineRunner");
    }
}
