package com.example.workspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WorkspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkspaceApplication.class, args);
	}

}
