package com.fastcampus.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
@RequiredArgsConstructor
public class FlowApplication implements ApplicationListener<ApplicationReadyEvent> {

	public static void main(String[] args) {
		SpringApplication.run(FlowApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

	}
}
