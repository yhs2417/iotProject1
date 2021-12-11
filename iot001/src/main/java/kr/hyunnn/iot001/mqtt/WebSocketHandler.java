package kr.hyunnn.iot001.mqtt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.NoArgsConstructor;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        list.add(session);

       
    }

	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
        
        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }   

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

       
        list.remove(session);
    }


}
