package kr.hyunnn.iot001.mqtt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttRecordsController {

	@GetMapping("/graph")
	public void selectAllMqqtDatas() {
		
	}
}
