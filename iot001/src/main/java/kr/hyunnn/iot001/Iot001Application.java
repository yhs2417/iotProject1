package kr.hyunnn.iot001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class Iot001Application {

	public static void main(String[] args) {
		SpringApplication.run(Iot001Application.class, args);
	}
 
}
