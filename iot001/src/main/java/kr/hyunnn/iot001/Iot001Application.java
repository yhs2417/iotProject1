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
		try {
 			SpringApplication.run(Iot001Application.class, args);
 			 
 			//mqttMain mqtt = appContext.getBean(mqttMain.class);
 
			//("mqqtServer IP, ClientId, subscribeTopic")
//			mqtt.init("tcp://3.13.219.168:1883", "102030HClient", "temperatureSensor");
			//mqtt.init("tcp://10.0.2.220:1883", "102030HClient", "temperatureSensor");

		} catch (Exception e) {
 			e.printStackTrace();
 		}

	}
 
}
