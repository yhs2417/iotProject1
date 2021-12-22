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
	
	
	@Autowired
	public MqttRecordsController(MqttRecordsService mqttRecordsService) {
		 
		this.mqttRecordsService = mqttRecordsService;
	}
	
	@GetMapping("/")
	public ModelAndView indexPage()
	{
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
