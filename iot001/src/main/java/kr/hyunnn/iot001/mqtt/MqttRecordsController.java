package kr.hyunnn.iot001.mqtt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MqttRecordsController {

	private MqttRecordsService mqttRecordsService;
	private mqttMain mqtt;
	
	@Autowired
	public MqttRecordsController(MqttRecordsService mqttRecordsService, mqttMain mqtt) {
		 
		this.mqttRecordsService = mqttRecordsService;
		this.mqtt = mqtt;
	}
	@GetMapping("/")
	public ModelAndView indexPage()
	{
		//mqtt.init("tcp://10.0.2.220:1883", "102030HClient", "temperatureSensor");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index.html");
		mav.addObject("graphDatas", mqttRecordsService.selectAllMqqtDatas());
		return mav;
	}
	@PostMapping("/graph")
	public List<Object> selectMqqtDatasByDate(@RequestBody MqttRecordsRequestVO vo) {
	
		return mqttRecordsService.selectMqqtDatasByDate(vo);
	}
	@GetMapping("/skills")
	public ModelAndView skillsPage()
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("skills.html");
 		return mav;
	}
}
