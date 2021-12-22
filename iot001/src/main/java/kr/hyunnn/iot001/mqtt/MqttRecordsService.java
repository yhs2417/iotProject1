package kr.hyunnn.iot001.mqtt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		
		//insertCount++;
		
		//if (insertCount == 86400) {
		mqttRecordsEntity = new MqttRecordsEntity(humidity, temperature);
	     
		iMqttRecordsRepository.save(mqttRecordsEntity);
		//insertCount = 0;
		//}
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
				rowDataList = new ArrayList<>();
				rowDataList.add(i.getInsertTime());
				rowDataList.add(i.getHumidity());
				rowDataList.add(i.getTemperature());
				
				responseList.add(rowDataList); 
			}
		}
		return responseList;
	}
	public List<Object> selectMqqtDatasByDate(MqttRecordsRequestVO vo) {
		
		//LocalDate startDate = LocalDate.parse(dates[0], DateTimeFormatter.ISO_DATE);
		//LocalDate endDate = LocalDate.parse(dates[1], DateTimeFormatter.ISO_DATE);
		LocalDate startDate = vo.getStartDate();
		LocalDate endDate = vo.getEndDate();	
		responseList = new ArrayList<>();
		rowDataList = new ArrayList<>();
	
		rowDataList.add("時間");
		rowDataList.add("湿度");
		rowDataList.add("温度");

		responseList.add(rowDataList) ;
		
		if (iMqttRecordsRepository.count() != 0 ) {

			for (MqttRecordsEntity i : iMqttRecordsRepository.findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqual(startDate, endDate)) {
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
