package de.fraunhofer.DigiTales2Go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * The type DigiTales2go application.
 *
 * @author Markus Walter
 * Based on the PE2 (TIK University of Stuttgart) example project by Justus Bogner provided under MIT License.
 */
@SpringBootApplication

public class DigiTales2GoApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DigiTales2GoApplication.class, args);
    }

    /**
     * Cors configurer web mvc configurer.
     *
     * @return the web mvc configurer
     */
// enable cross-origin resource sharing (CORS)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // allow CORS requests for all resources and HTTP methods from all origins
                registry.addMapping("/**")
                        .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE")
                        .allowedOrigins("*");

            }
        };
    }
}
