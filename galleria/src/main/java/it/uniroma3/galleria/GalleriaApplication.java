package it.uniroma3.galleria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GalleriaApplication //extends SpringBootServletInitializer 
{

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GalleriaApplication.class);
    }*/
    
	public static void main(String[] args) {
		SpringApplication.run(GalleriaApplication.class, args);
	}
}
