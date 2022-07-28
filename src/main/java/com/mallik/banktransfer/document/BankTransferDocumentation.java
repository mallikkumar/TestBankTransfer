package com.mallik.banktransfer.document;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * BankTransferDocumentation class enables to expose this application and its Restful services to generate the Swagger 2.0 document endpoint
 * @author Mallik Kumar
 *
 */
@SpringBootApplication
@EnableSwagger2
public class BankTransferDocumentation {
	@Bean
	public Docket bankTransferApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().
				apis(RequestHandlerSelectors.basePackage("com.nw.banktransfer.controller")).build();
	}
}
