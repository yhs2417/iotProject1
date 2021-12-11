package kr.hyunnn.iot001.mqtt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.NoArgsConstructor;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> webSessions = new ArrayList<>();
	
 
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.out.println("ws connected");
    
    	webSessions.add(session);   
    }

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
    	System.out.println("ws msg=" + payload);

         
    }   
	public void sendMsg(String msg) throws Exception {
		System.out.println("in sendmsg");
		for(WebSocketSession session: webSessions) {
			if (session == null ) continue;
			
			session.sendMessage(new TextMessage(msg));
		}
	}
 
	
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	System.out.println("ws disconnected");
    	 
    	webSessions.remove(session);
    }
}
