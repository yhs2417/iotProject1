package kr.hyunnn.iot001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import kr.hyunnn.iot001.mqtt.MqttRecordsService;
import kr.hyunnn.iot001.mqtt.mqttMain;

@Component
public class AppShutdownEvent implements ApplicationListener<ContextClosedEvent>{

	private mqttMain mqtt;
	
	@Autowired
    public AppShutdownEvent(mqttMain mqtt) {
		super();
		this.mqtt = mqtt;
	}
	@Override
    public void onApplicationEvent(ContextClosedEvent event) {
		//mqtt.cleanUp();
         
    }
}
