package com.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.core.DI.ComponentClass;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CoreApplication.class, args);

		applicationContext.getBean("componentClass", ComponentClass.class).display();
	}

}
