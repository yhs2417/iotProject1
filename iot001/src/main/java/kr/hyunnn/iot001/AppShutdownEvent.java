package kr.hyunnn.iot001;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import kr.hyunnn.iot001.mqtt.mqttMain;

@Component
public class AppShutdownEvent implements ApplicationListener<ContextClosedEvent>{

	@Override
    public void onApplicationEvent(ContextClosedEvent event) {
         mqttMain.cleanUp();
         
    }
}
