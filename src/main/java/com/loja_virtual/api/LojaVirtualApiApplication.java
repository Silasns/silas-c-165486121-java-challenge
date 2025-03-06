package com.loja_virtual.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Api Loja Virtual", version="1", description = "Api para gerenciamento de uma loja virtual"))
@SpringBootApplication
public class LojaVirtualApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApiApplication.class, args);
	}

}
