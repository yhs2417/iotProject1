package kr.hyunnn.iot001.mqtt;

 

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.hyunnn.iot001.webSocket.WebSocketHandler;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
@Component
public class mqttMain implements MqttCallback{
	private Logger logger = LoggerFactory.getLogger(mqttMain.class);
	private MqttClient client;
    private MqttConnectOptions option;
    private Thread subscribeThread;
    private  boolean subscribeThreadLifeFlag = true;
    private  boolean subscribeThreadWorkingFlag = false;
    
    MqttMessage message = new MqttMessage();
    private JSONParser parser = new JSONParser();
    private JSONObject jsonObj;
    private String recvMsg;
	private Object obj;
	private double humidity;
	private double temperature;
 
	private WebSocketHandler webSocketHandler;
	private MqttRecordsService mqttRecordsService;

	@Autowired
	private mqttMain(WebSocketHandler webSocketHandler, MqttRecordsService mqttRecordsService) {
		this.webSocketHandler = webSocketHandler;
		this.mqttRecordsService = mqttRecordsService;
	}
	
	public void setsubscribeThreadLifeFlag(boolean lifeFlag) {
		subscribeThreadLifeFlag = lifeFlag;
	}
	
    public void init(String serverURI, String clientId, String topic) {
    	logger.info("mqtt init() 진입");  	
		subscribeThread = new Thread(new Runnable() {
			
			@Override
			public void run(){
				try {
					logger.info("mqtt sub 멀티스레드 진입");
					option = new MqttConnectOptions();
					option.setCleanSession(true);
					// option.setKeepAliveInterval(30);
					//option.setUserName(userName);
					client = new MqttClient(serverURI, clientId);
					client.setCallback(mqttMain.this);
					client.connect(option);
					Thread.sleep(2000);  
					
					client.subscribe(topic);
					
					while(subscribeThreadLifeFlag == true) {
						if (subscribeThreadWorkingFlag == true) {
							logger.info("WorkingFlag ON");
						} else {
							Thread.yield();
						}
					}
					logger.info("mqtt 멀티스레드 종료");

				} catch (MqttException e) {
					 logger.error("", e);
					 throw new RuntimeException(e);
				} catch (InterruptedException e) {
					logger.error("", e);  	
				 
				}				
			}  //run()
		}, "MqttMainTread");//subscribeThread
	subscribeThread.start();	
    }

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.info("Mqtt 메세지 도착");
		subscribeThreadWorkingFlag = true;

		//Yield()로 실행 대기에 있다가 메세지 도착 이벤트를 잡을수 있을까?
		if (topic.equals("temperatureSensor")) {
			 
			try {
		        recvMsg = new String(message.getPayload());
		       
		        obj = parser.parse(recvMsg);
		        jsonObj = (JSONObject) obj;
		
		        temperature = (double) jsonObj.get("temperature");
		        humidity = (double) jsonObj.get("humidity");
		        //ystem.out.println("topic=" + topic + "temp" + temperature + "hum" + humidity);
		        //logger.info("mqtt msg 도착 온도=" + temperature);
		        //sendTo browser
		        webSocketHandler.sendMsg(recvMsg);
		        
		        //db insert
		        mqttRecordsService.insertMqqtDatas(humidity, temperature);
			
			} catch (Exception e) {
				logger.error("", e);  	
				 
			}
		}
		subscribeThreadWorkingFlag = false;

	}
    public int sendMessage(String topic, String msg) {
		try {
	    	
			message.setPayload(msg.getBytes());   
			client.publish(topic, message);   
			 
		} catch (MqttException e) {
			logger.error("", e);  	
    		return 0;
		}
		return 1;
	}
    
    public void cleanUp() {
		 try {
			 if (subscribeThread != null) {
				 setsubscribeThreadLifeFlag(false);
				 subscribeThread.join();
			 }
 		 } catch (InterruptedException e) {
			 logger.error("", e);  

		 } 
	}
	@Override
	public void connectionLost(Throwable cause) {
		 try {
			 logger.info("mqtt connectionLost");
			 if (subscribeThread != null) {
				 setsubscribeThreadLifeFlag(false);
			 	subscribeThread.join();
			 }
			 client.close();
		 } catch (MqttException e) {
				logger.error("", e);  	
		 } catch (InterruptedException e) {
			 logger.error("", e);  
		 } 
		
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	
	}
}
