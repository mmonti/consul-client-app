package com.dreamworks.consul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class ConsulClientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulClientAppApplication.class, args);
	}

	@Value("${message:default}")
	private String message;

	@RequestMapping(value = "/message", method = GET)
	public String message() {
		return message;
	}
}
