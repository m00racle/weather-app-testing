package com.mooracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/** ENTRY 4: Creating Application.java class
 *
 * This is standard Spring Application Boot Run that will load and run config classes, scan component, and initialize run
 *
 * */

@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
