package kr.hyunnn.iot001.mqtt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hyunnn.iot001.domain.mqtt.IMqttRecordsRepository;

import kr.hyunnn.iot001.domain.mqtt.MqttRecordsEntity;

@Service
public class MqttRecordsService {

	IMqttRecordsRepository iMqttRecordsRepository;
	 
	MqttRecordsEntity mqttRecordsEntity;
	
	private int insertCount;
	
	@Autowired
	public MqttRecordsService(IMqttRecordsRepository iMqttRecordsRepository) {
		this.iMqttRecordsRepository = iMqttRecordsRepository;
	}
	
	public void insertMqqtDatas(double humidity, double temperature) {
	   
		mqttRecordsEntity = new MqttRecordsEntity(humidity, temperature);
	     
		iMqttRecordsRepository.save(mqttRecordsEntity);
	}
	
	public List<MqttRecordsResponseVO> selectAllMqqtDatas() {
		List<MqttRecordsResponseVO> res = new ArrayList<>();
		for (MqttRecordsEntity i : iMqttRecordsRepository.findAll()) {
			res.add(new MqttRecordsResponseVO(i));
		}
		return res;
	}
}
