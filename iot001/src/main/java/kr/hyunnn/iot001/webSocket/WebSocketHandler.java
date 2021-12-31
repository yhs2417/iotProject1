package kr.hyunnn.iot001.webSocket;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.hyunnn.iot001.mqtt.MqttRecordsService;
import kr.hyunnn.iot001.mqtt.mqttMain;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

	private static List<WebSocketSession> webSessions = new ArrayList<>();
	private MqttRecordsService mqttRecordsService;
	
	@Autowired
    public WebSocketHandler(MqttRecordsService mqttRecordsService) {
		super();
		this.mqttRecordsService = mqttRecordsService;
	}

	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("socket established");
    
    	webSessions.add(session);   
    }

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
    	//System.out.println("ws msg=" + payload);
		logger.info("ws msg=" + payload);

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
		//System.out.println("in sendmsg");
		//logger.info("websocket sendmsg");
	}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	logger.info("socket disconnected");
    	 
    	webSessions.remove(session);
    }
}
