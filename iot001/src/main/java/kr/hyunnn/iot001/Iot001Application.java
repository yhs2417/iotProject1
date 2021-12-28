package kr.hyunnn.iot001;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
 
import kr.hyunnn.iot001.mqtt.mqttMain;

@SpringBootApplication
public class Iot001Application {
	private static final Logger main_logger = LogManager.getLogger(Iot001Application.class);

	public static void main(String[] args) {
		try {
			main_logger.info("main진입");
			ConfigurableApplicationContext appContext = SpringApplication.run(Iot001Application.class, args);
			mqttMain mqtt = appContext.getBean(mqttMain.class);
			//("mqqtServer IP, ClientId, subscribeTopic")
			mqtt.init("tcp://3.13.219.168:1883", "102030HClient", "temperatureSensor");
		} catch (Exception e) {
		 
			main_logger.error(e);
		}

	}
 
}
