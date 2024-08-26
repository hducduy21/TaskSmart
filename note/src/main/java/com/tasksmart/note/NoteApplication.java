package com.tasksmart.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.tasksmart.sharedLibrary.*")
@ComponentScan("com.tasksmart.sharedLibrary.*")
public class NoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}
}
