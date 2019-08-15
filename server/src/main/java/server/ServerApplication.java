package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

/**
 * Main Server Class.
 */
@SpringBootApplication
@WebAppConfiguration
public class ServerApplication {

    /**
     * Main method for the server application.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        //Running the HTTP/HTTPS Server
        SpringApplication.run(ServerApplication.class, args);
    }

    /**git
     * Making the RestControlClassTest work, needs Bean.
     * @return returns a new RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
