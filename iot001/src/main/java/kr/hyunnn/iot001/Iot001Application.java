package kr.hyunnn.iot001;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
 
import kr.hyunnn.iot001.mqtt.mqttMain;

@SpringBootApplication
public class Iot001Application {
 
	private static Logger main_logger = LoggerFactory.getLogger(Iot001Application.class);

	public static void main(String[] args) {
		try {
 			ConfigurableApplicationContext appContext = SpringApplication.run(Iot001Application.class, args);
 			main_logger.info("-------111------");
 			mqttMain mqtt = appContext.getBean(mqttMain.class);
 			main_logger.info("-------2222------");

			//("mqqtServer IP, ClientId, subscribeTopic")
//			mqtt.init("tcp://3.13.219.168:1883", "102030HClient", "temperatureSensor");
			mqtt.init("tcp://10.0.2.220:1883", "102030HClient", "temperatureSensor");

		} catch (Exception e) {
			main_logger.error("",e);
			e.printStackTrace();
 		}

	}
 
}
