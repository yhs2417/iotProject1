package kr.hyunnn.iot001;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
 
import kr.hyunnn.iot001.mqtt.mqttMain;

@SpringBootApplication
public class Iot001Application {
 
	public static void main(String[] args) {
		
 		SpringApplication.run(Iot001Application.class, args);
	}
 
}
