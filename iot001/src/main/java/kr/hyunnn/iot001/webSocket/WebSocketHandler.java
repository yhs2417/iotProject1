package kr.hyunnn.iot001.webSocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.hyunnn.iot001.mqtt.MqttRecordsService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> webSessions = new ArrayList<>();
	private MqttRecordsService mqttRecordsService;
	
	@Autowired
    public WebSocketHandler(MqttRecordsService mqttRecordsService) {
		super();
		this.mqttRecordsService = mqttRecordsService;
	}

	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.out.println("ws connected");
    
    	webSessions.add(session);   
    }

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
    	System.out.println("ws msg=" + payload);
    	
    	if (payload.trim().substring(0, 0).equals("D")) {
    		 
    		String[] dates = payload.replace("D", "").split("&");
    		
    		//mqttRecordsService.selectMqqtDatasByDate(dates);
    	}
      
    }   
	public void sendMsg(String msg) throws Exception {
		for(WebSocketSession session: webSessions) {
			if (session == null ) continue;
			
			session.sendMessage(new TextMessage(msg));
		}
		System.out.println("in sendmsg");
	}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	System.out.println("ws disconnected");
    	 
    	webSessions.remove(session);
    }
}
