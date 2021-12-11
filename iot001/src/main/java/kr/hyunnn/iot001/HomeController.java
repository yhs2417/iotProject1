package kr.hyunnn.iot001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hyunnn.iot001.mqtt.mqttMain;

@Controller
public class HomeController {

	//mqtt는 메인으로 가야할듯.. 서버 올라갈때 한번 연결되야지
	// "/"로 접속할때마다 여러개 만드는건 아니잖아.
	// 웹소켓 연결끈기면 mqtt도 끈기게?
	@Autowired
	private mqttMain mqttMain;
	
	private String sub_topic    = "temperatureSensor";
 	private String content      = "Message from MqttPublishSample";
    private String serverURI    = "tcp://192.168.219.161:1883";
    private String clientId     = "102030HClient";
    private String pub_topic    = "led";
    private Thread subscribeThread;

  
	@GetMapping("/")
	public String indexPage(Model model)
	{
		
	    
		subscribeThread = mqttMain.init(serverURI, clientId, sub_topic);
		subscribeThread.start();
    	//mqtt.sendMessage(pub_topic, "1");
    	
    	//mqtt.cleanUp();
		return "index";
	}
}
