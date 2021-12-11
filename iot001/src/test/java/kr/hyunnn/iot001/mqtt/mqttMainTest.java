package kr.hyunnn.iot001.mqtt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.After;
import org.junit.Before;
@SpringBootTest
public class mqttMainTest {

	mqttMain mqtt = new mqttMain();
 	String sub_topic    = "temperatureSensor";
    String content      = "Message from MqttPublishSample";
    String serverURI    = "tcp://192.168.219.161:1883";
    String clientId     = "102030HClient";
    String pub_topic = "led";
 
    @BeforeEach
    public void connect()   {
    	
    	mqtt.init(serverURI, clientId, sub_topic);
    	assertTrue(mqtt != null, "mqttMain().init() return == null");
    	System.out.println("connected");
    	
    }
    @Test
    public void SendMsgTest() {
    
    	assertTrue(mqtt.sendMessage(pub_topic, "1") == 1, "sendMessage error");
    
    }
    @Test
    public void recvMsgTest() {

    }
    
    
    @AfterEach
    public void cleanup( ) throws InterruptedException {
    	
    	mqtt.cleanUp();
    }
}
