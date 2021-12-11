package kr.hyunnn.iot001.mqtt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	private WebSocketHandler webSocketHandler;
	
	public WebSocketConfig(WebSocketHandler webSocketHandler) {
		super();
		this.webSocketHandler = webSocketHandler;
	}

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/websocket")
         		.setAllowedOrigins("*")
         		.withSockJS();  
		registry.addHandler(webSocketHandler, "/websocket")
         		.setAllowedOrigins("*");  
		
	}

}
