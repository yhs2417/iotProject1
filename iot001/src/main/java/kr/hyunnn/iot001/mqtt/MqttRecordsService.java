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
	MqttRecordsResponseVO mqttRecordsResponseVO;
	private int insertCount;
	List<Object> responseList;
	List<Object> rowDataList;
	
	@Autowired
	public MqttRecordsService(IMqttRecordsRepository iMqttRecordsRepository) {
		this.iMqttRecordsRepository = iMqttRecordsRepository;
	}
	
	public void insertMqqtDatas(double humidity, double temperature) {
	   
		mqttRecordsEntity = new MqttRecordsEntity(humidity, temperature);
	     
		iMqttRecordsRepository.save(mqttRecordsEntity);
	}
	
	public List<Object> selectAllMqqtDatas() {
		responseList = new ArrayList<>();
		rowDataList = new ArrayList<>();
		
		rowDataList.add("時間");
		rowDataList.add("湿度");
		rowDataList.add("温度");

		responseList.add(rowDataList) ;
		
		if (iMqttRecordsRepository.count() != 0 ) {

			for (MqttRecordsEntity i : iMqttRecordsRepository.findAll()) {
				//mqttRecordsResponseVO.setInsertTime(i.getUpdateTime());
				//mqttRecordsResponseVO.setHumidity(i.getHumidity());
				//mqttRecordsResponseVO.setTemperature(i.getTemperature());
				//responseList.add(mqttRecordsResponseVO);
				rowDataList = new ArrayList<>();
				rowDataList.add(i.getInsertTime());
				rowDataList.add(i.getHumidity());
				rowDataList.add(i.getTemperature());
				
				responseList.add(rowDataList);
				 
			}
		}
		return responseList;
	}
}
