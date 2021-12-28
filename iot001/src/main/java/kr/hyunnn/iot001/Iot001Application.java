package kr.hyunnn.iot001;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
 
import kr.hyunnn.iot001.mqtt.mqttMain;

@SpringBootApplication
public class Iot001Application {
 
	public static void main(String[] args) {
		try {
 			ConfigurableApplicationContext appContext = SpringApplication.run(Iot001Application.class, args);
			System.out.println("----1---");
 			mqttMain mqtt = appContext.getBean(mqttMain.class);
 			System.out.println("----2---");
			//("mqqtServer IP, ClientId, subscribeTopic")
			mqtt.init("tcp://3.13.219.168:1883", "102030HClient", "temperatureSensor");
		} catch (Exception e) {
			e.printStackTrace();
 		}

	}
 
}
