package kr.hyunnn.iot001.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.hyunnn.iot001.webSocket.WebSocketHandler;

import org.eclipse.paho.client.mqttv3.MqttClient;

@Component
public class mqttMain implements MqttCallback{
	
	private MqttClient client;
    private MqttConnectOptions option;
    private Thread subscribeThread;
    private boolean subscribeThreadLifeFlag = true;

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
	/* single 
	private static mqttMain mqtt;

	
	public static mqttMain getInstance() {
		if (mqtt == null) {
			mqtt = new mqttMain(new WebSocketHandler(), mqttRecordsService);
		}
		return mqtt;
	}

	
	public static void deleteInstance() {
		mqtt = null;
	}
	*/
	public void setsubscribeThreadLifeFlag(boolean lifeFlag) {
		this.subscribeThreadLifeFlag = lifeFlag;
	}
	
    public void init(String serverURI, String clientId, String topic) {
		  	
		subscribeThread = new Thread(new Runnable() {
			
			@Override
			public void run(){
				try {
					System.out.println("멀티스레드 진입");
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
						
					}
				} catch (MqttException e) {
					 throw new RuntimeException(e);
				} catch (InterruptedException e) {
				 
					e.printStackTrace();
				}				
			}
		});
	subscribeThread.start();	
     
    }

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		 
		if (topic.equals("temperatureSensor")) {
			 
			try {
		        recvMsg = new String(message.getPayload());
		       
		        obj = parser.parse(recvMsg);
		        jsonObj = (JSONObject) obj;
		
		        temperature = (double) jsonObj.get("temperature");
		        humidity = (double) jsonObj.get("humidity");
		        System.out.println("topic=" + topic + "temp" + temperature + "hum" + humidity);
		        
		        //sendTo browser
		        webSocketHandler.sendMsg(recvMsg);
		        
		        //db insert
		        mqttRecordsService.insertMqqtDatas(humidity, temperature);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    public int sendMessage(String topic, String msg) {
		try {
	    	
			message.setPayload(msg.getBytes());   
			client.publish(topic, message);   
			 
		} catch (MqttException e) {
		    
            System.out.println("msg "+e.getMessage());
           
    		e.printStackTrace();
    		return 0;
		}
		return 1;
	}
    
    public void cleanUp() {
		 try {
			 setsubscribeThreadLifeFlag(false);
			 Thread.sleep(1000);  
			 client.disconnect();
			 client.close();
			 System.out.println("Mqqt cleanup");
		 } catch (MqttException e) {
			 e.printStackTrace();
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }
	}
    
	@Override
	public void connectionLost(Throwable cause) {
		 try {
			 setsubscribeThreadLifeFlag(false);
			 System.out.println("disconnected");
			 client.close();
		 } catch (MqttException e) {
			 e.printStackTrace();
		 }
		
	}


	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	 
		
	}
}
