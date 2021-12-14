package kr.hyunnn.iot001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kr.hyunnn.iot001.mqtt.mqttMain;

@SpringBootApplication
public class Iot001Application {
 
	public static void main(String[] args) {
		try {
			ConfigurableApplicationContext appContext = SpringApplication.run(Iot001Application.class, args);
			mqttMain mqtt = appContext.getBean(mqttMain.class);
			
			//("mqqtServer IP, ClientId, subscribeTopic")
			mqtt.init("tcp://192.168.219.161:1883", "102030HClient", "temperatureSensor");
		} catch (Exception e) {
		 
			e.printStackTrace();
		}

	}
 
}
