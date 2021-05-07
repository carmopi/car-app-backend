package com.carmen.app.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	    info = @Info(
	        title = "carAPP",
	        version = "0.0.1",
	        description = "Car API",
	        license = @License(name = "Apache 2.0"),
	        contact = @Contact(name = "Carmen Piñera Moreno", email = "cpineram@everis.com")
	    )
)
@ApplicationPath("/")
public class JAXRSConfig extends Application {

}
