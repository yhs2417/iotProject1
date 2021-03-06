package kr.hyunnn.iot001.mqtt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hyunnn.iot001.domain.mqtt.IMqttRecordsRepository;

import kr.hyunnn.iot001.domain.mqtt.MqttRecordsEntity;

@Service
public class MqttRecordsService {
	private Logger logger = LoggerFactory.getLogger(MqttRecordsService.class);

	IMqttRecordsRepository iMqttRecordsRepository;
	 
	MqttRecordsEntity mqttRecordsEntity;
	MqttRecordsResponseVO mqttRecordsResponseVO;
	private int insertCount = 0;
	List<Object> responseList;
	List<Object> rowDataList;
	
	@Autowired
	public MqttRecordsService(IMqttRecordsRepository iMqttRecordsRepository) {
		this.iMqttRecordsRepository = iMqttRecordsRepository;
	}
	
	public void insertMqqtDatas(double humidity, double temperature) {
		
		insertCount = insertCount + 5;
		//if (insertCount == 20) {

		if (insertCount == 43200) { //12hour
			mqttRecordsEntity = new MqttRecordsEntity(humidity, temperature);
	     
			iMqttRecordsRepository.save(mqttRecordsEntity);
			insertCount = 0;
		}
	}
	
	public List<Object> selectAllMqqtDatas() {
		responseList = new ArrayList<>();
		rowDataList = new ArrayList<>();
		
		rowDataList.add("시간");
		rowDataList.add("습도");
		rowDataList.add("온도");

		responseList.add(rowDataList);
		
		long rows = iMqttRecordsRepository.count();
		
		if (rows != 0 ) {
			logger.info("all select rows=" + rows);
			for (MqttRecordsEntity i : iMqttRecordsRepository.findAllByOrderByInsertTimeAsc()) {
				rowDataList = new ArrayList<>();
				rowDataList.add(i.getInsertTime().format(DateTimeFormatter.ofPattern("MM-dd")));
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
	
		rowDataList.add("시간");
		rowDataList.add("습도");
		rowDataList.add("온도");

		responseList.add(rowDataList) ;
		
		if (iMqttRecordsRepository.count() != 0 ) {

			for (MqttRecordsEntity i : iMqttRecordsRepository.findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqualOrderByInsertTimeAsc(startDate, endDate)) {
				rowDataList = new ArrayList<>();
				rowDataList.add(i.getInsertTime().format(DateTimeFormatter.ofPattern("MM-dd")));

				rowDataList.add(i.getHumidity());
				rowDataList.add(i.getTemperature());
				
				responseList.add(rowDataList); 
			}
		}
		
		return responseList;
	}
}
