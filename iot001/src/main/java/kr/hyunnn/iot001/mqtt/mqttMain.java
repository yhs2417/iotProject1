package kr.hyunnn.iot001.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.eclipse.paho.client.mqttv3.MqttClient;

public class mqttMain implements MqttCallback{
	private MqttClient client;
    private MqttConnectOptions option;
    private Thread subscribeThread;
    private boolean subscribeThreadLifeFlag = true;

    private static JSONParser parser = new JSONParser();
    private JSONObject jsonObj;
    
    private String recvMsg;
	private Object obj;
	private Double humidity;
	private Double temperature;
   
	
	public void setsubscribeThreadLifeFlag(boolean lifeFlag) {
		this.subscribeThreadLifeFlag = lifeFlag;
	}
	
    public void init(String serverURI, String clientId, String topic) {
		  	
		subscribeThread = new Thread(new Runnable() {
			
			@Override
			public void run(){
				try {
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
    
    public int sendMessage(String topic, String msg) {
		try {
	    	MqttMessage message = new MqttMessage();
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
			 subscribeThread.join();
			 client.disconnect();
			 client.close();
			 System.out.println("Mqqt cleanup");
		 } catch (MqttException e) {
			 e.printStackTrace();
		 }catch (InterruptedException e) {
			 e.printStackTrace();
		 }
	}
    
	@Override
	public void connectionLost(Throwable cause) {
		 try {
			 System.out.println("disconnected");
			 client.close();
		 } catch (MqttException e) {
			 e.printStackTrace();
		 }
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		 
		if (topic.equals("temperatureSensor")) {
			System.out.println("recvMsgCallBack");

			try {
		        recvMsg = new String(message.getPayload());
		       
		        obj = parser.parse(recvMsg);
		        jsonObj = (JSONObject) obj;
		
		        temperature = (Double) jsonObj.get("temperature");
		        humidity = (Double) jsonObj.get("humidity");
		        System.out.println("topic=" + topic + "temp" + temperature + "hum" + humidity);
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	 
		
	}
}
