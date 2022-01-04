package kr.hyunnn.iot001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import kr.hyunnn.iot001.mqtt.mqttMain;

@Component
public class AppStartEvent implements CommandLineRunner{

	private mqttMain mqtt;
	
	@Value("${mqttServerPath}")
	private String mqttServerPath;
	
	@Autowired
    public AppStartEvent(mqttMain mqtt) {
		super();
		this.mqtt = mqtt;
		 
	}
	@Override
    public void run(String... args) {
		System.out.println("--------" + mqttServerPath);
		mqtt.init(mqttServerPath, "102030HClient", "temperatureSensor");
    }
}
